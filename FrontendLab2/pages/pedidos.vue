<template>
  <div class="container mx-auto px-4">
    <h1 class="text-3xl font-bold mb-8">Pedidos</h1>

    <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-6">
      <div
        v-for="order in Order"
        :key="order.id_orden"
        class="border border-gray-300 rounded-lg shadow-md p-4"
      >
        <h2 class="text-xl font-semibold mb-2">Orden #{{ order.id_orden }}</h2>
        <hr class="my-2 border-gray-400">

        <p class="text-xl font-semibold mb-2">Fecha de pedido:</p>
        <h2 class="text-xl font-semibold mb-2">{{ formatDate(order.fecha_orden) }}</h2>
        <hr class="my-2 border-gray-400">

        <div class="flex flex-row">
          <p class="text-xl font-semibold mb-2 mr-4">Estado:</p>
          <span :class="['font-medium', getStateColor(order.estado)]">
            {{ order.estado }}
          </span>
        </div>
        <hr class="my-2 border-gray-400">

        <div class="flex flex-row">
          <p class="text-xl font-semibold mb-2 mr-4">Costo Total:</p>
          <span class="text-xl font-bold">${{ order.total }}</span>
        </div>
        <hr class="my-2 border-gray-400">
        <!-- Botón para abrir el modal -->
        <button @click="openModal(order.id_orden)" class="bg-blue-500 text-white px-4 py-2 rounded">
          Detalle de la Orden
        </button>
      </div>
    </div>

    <!-- Paginación -->
    <v-pagination
      v-model="currentPage"
      :length="totalPages"
      color="primary"
      class="mt-6"
    />

    <!-- Modal con los detalles de la orden -->
    <div v-if="isModalOpen" class="fixed inset-0 bg-gray-600 bg-opacity-50 flex justify-center items-center z-50">
      <div class="bg-white p-6 rounded-lg w-1/2">
        <h2 class="text-xl font-semibold mb-4">Detalles de la Orden</h2>
        <hr class="border-gray-300 mb-4">
        <div v-if="orderDetails && orderDetails.length > 0" class="max-h-96 overflow-y-auto">
          <div v-for="(detail, index) in orderDetails" :key="index" class="mb-4 border-b pb-2">
            <div class="flex justify-between relative">
              <p class="flex-1"><strong>Producto:</strong> {{ detail.nombre }}</p>
              <p class="absolute left-1/2 transform -translate-x-1/2"><strong>Cantidad:</strong> {{ detail.cantidad }}</p>
              <p class="absolute left-[70%] text-left"><strong>Precio Unitario:</strong> ${{ detail.precioUnitario }}</p>
            </div>
          </div>
        </div>
        <div v-else>
          <p>No se encontraron detalles para esta orden.</p>
        </div>
        <button @click="closeModal" class="bg-red-500 text-white px-4 py-2 rounded mt-4">
          Cerrar
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import axios from 'axios';

const Order = ref([]); // Almacena las órdenes
const currentPage = ref(1); // Página actual
const totalPages = ref(1); // Número total de páginas
const API_URL = 'http://localhost:8090/api/orden'; // URL del backend Orden
const API_URL_2 = 'http://localhost:8090/api/detalleorden'; // URL del backend de DetalleOrden
const API_URL_3 = 'http://localhost:8090/api/producto'; // URL del backend de Producto

// Estado del modal
const isModalOpen = ref(false);
const orderDetails = ref(null);

// Abrir el modal
const openModal = async (id_orden) => {
  try {
    const response = await axios.get(`${API_URL_2}/getByOrderId/${id_orden}`);

    const orderDetailsWithNames = await Promise.all(
      response.data.map(async (orderDetail) => {
        try {
          const productResponse = await axios.get(`${API_URL_3}/getName/${orderDetail.idProducto}`);
          return {
            ...orderDetail,
            nombre: productResponse.data,
          };
        } catch (error) {
          console.error('Error fetching product name:', error);
          return {
            ...orderDetail,
            nombre: 'Producto no encontrado',
          };
        }
      })
    );
    orderDetails.value = orderDetailsWithNames; // Guarda DetalleOrden
    isModalOpen.value = true; // Abre el popup
  } catch (error) {
    console.error('Error fetching order details:', error);
  }
};


const closeModal = () => {
  isModalOpen.value = false;
  orderDetails.value = null; 
};

// Función para obtener las órdenes del cliente
const fetchOrders = async () => {
  const limit = 8; 
  const offset = (currentPage.value - 1) * limit;
  const id_cliente = localStorage.getItem('userId'); // Obtener el ID del cliente desde localStorage

  if (!id_cliente) {
    console.error('ID del cliente no encontrado en localStorage');
    return;
  }

  try {
    
    const response = await axios.get(`${API_URL}/${id_cliente}/${limit}/${offset}`);
    console.log('Respuesta del servidor:', response.data); 

    
    Order.value = response.data;
    totalPages.value = Math.ceil(response.data.length / limit);
  } catch (error) {
    console.error('Error fetching orders:', error);
  }
};

// Formatear la fecha en el formato deseado
const formatDate = (dateString) => {
  const date = new Date(dateString);
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  return `${day}/${month}/${year} ${hours}:${minutes}`;
};

// Llamar la función cuando se monte el componente
onMounted(fetchOrders);

// Actualizar las órdenes cuando se cambie la página
watch(currentPage, fetchOrders);

const getStateColor = (estado) => {
  if (estado === "pendiente") return 'text-red-500'
  if (estado === "pagada") return 'text-orange-500'
  return 'text-green-500'
}


</script>


