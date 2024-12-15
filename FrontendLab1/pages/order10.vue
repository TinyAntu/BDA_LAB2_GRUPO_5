<template>
    <div class="container mx-auto px-4">
      <h1 class="text-3xl font-bold mb-8">Ordenes</h1>
  
      <!-- Campo para seleccionar o ingresar idAlmacen -->
      <div class="mb-6">
        <label for="idAlmacen" class="text-lg font-semibold">Buscar por Almacén</label>
        <div class="flex items-center">
          <!-- Campo de búsqueda -->
          <input
            v-model="searchAlmacenId"
            type="text"
            id="idAlmacen"
            class="border border-gray-300 rounded-lg px-4 py-2"
            placeholder="Ingrese ID del Almacén"
          />
          <!-- Icono de lupa para buscar -->
          <v-icon
            @click="fetchOrdersByStore"
            class="cursor-pointer ml-2 text-blue-500"
          >
            mdi-magnify
          </v-icon>
        </div>
      </div>
  
      <!-- Mostrar las órdenes -->
      <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-6">
        <div
          v-for="order in orders"
          :key="order.idOrden"
          class="border border-gray-300 rounded-lg shadow-md p-4"
        >
          <h2 class="text-xl font-semibold mb-2">Orden #{{ order.idOrden }}</h2>
          <hr class="my-2 border-gray-400">
  
          <p class="text-xl font-semibold mb-2">Fecha de pedido:</p>
          <h2 class="text-xl font-semibold mb-2">{{ formatDate(order.fechaOrden) }}</h2>
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
          <button @click="openModal(order.idOrden)" class="bg-blue-500 text-white px-4 py-2 rounded">
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
  import { ref } from 'vue';
  import axios from 'axios';
  import { useRouter } from 'vue-router';
  
  const orders = ref([]);
  const currentPage = ref(1);
  const totalPages = ref(1);
  const searchAlmacenId = ref('');
  const API_URL = 'http://localhost:8090/api/orden'; // URL del backend Orden
  const API_URL_2 = 'http://localhost:8090/api/detalleorden'; // URL del backend de DetalleOrden
  const API_URL_3 = 'http://localhost:8090/api/producto'; // URL del backend de Producto

  // Estado del modal
    const isModalOpen = ref(false);
    const orderDetails = ref(null);

  // Abrir el modal
    const openModal = async (idOrden) => {
        try {
            const response = await axios.get(`${API_URL_2}/getByOrderId/${idOrden}`);

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
  
  // Método para formatear la fecha
  const formatDate = (date) => {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return new Date(date).toLocaleDateString('es-ES', options);
  };
  
  // Obtener color del estado de la orden
  const getStateColor = (estado) => {
    switch (estado) {
      case 'Pendiente':
        return 'text-yellow-500';
      case 'Completada':
        return 'text-green-500';
      case 'Cancelada':
        return 'text-red-500';
      default:
        return 'text-gray-500';
    }
  };
  
  // Función para obtener las órdenes filtradas por `idAlmacen`
  const fetchOrdersByStore = () => {
    if (searchAlmacenId.value) {

      axios.get(`${API_URL}/filtrarOrdenesPorAlmacen/${searchAlmacenId.value}`)
        .then((response) => {
          orders.value = response.data.orders;
          totalPages.value = response.data.totalPages;
        })
        .catch((error) => {
          console.error(error);
        });
    }
  };
</script>
  
  