<template>
  <div class="container mx-auto px-4">
    <h1 class="text-3xl font-bold mb-8">Carrito de compras</h1>
    <div v-if="cartItems.length > 0" class="bg-white rounded-lg shadow-md p-6">
      <div v-for="item in cartItems" :key="item.idProducto" class="cart-item">
        <p>{{ item.nombre }} - ${{ item.precio }}</p>

        <div class="quantity-controls">
          <v-btn icon @click="decreaseQuantity(item.idProducto)">
            <v-icon>mdi-minus</v-icon>
          </v-btn>
          <span>{{ item.quantity }}</span>
          <v-btn icon @click="increaseQuantity(item.idProducto)">
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </div>

        <p>Subtotal: ${{ item.precio * item.quantity }}</p>
      </div>
      <div class="mt-6 flex justify-between items-center">
        <v-btn color="primary" @click="clearCart">Vaciar carrito</v-btn>
      </div>
      <div class="mt-6 flex justify-between items-center">
        <span class="text-xl font-bold">Total: ${{ cartTotal }}</span>
        <v-btn color="primary" @click="checkout">Pagar</v-btn>
      </div>
    </div>
    <div v-else class="text-center py-12">
      <p class="text-xl text-gray-600">No has agregado ningún producto en el carrito</p>
    </div>
  </div>
</template>

<script setup>
import { useCart } from '@/composables/useCart'
import axios from 'axios'

const { cartItems, cartTotal, increaseQuantity, decreaseQuantity, clearCart } = useCart()
const API_URL = 'http://localhost:8090/api'

const total = computed(() => {
  return cartItems.value.reduce((sum, item) => sum + (item.product.price * item.quantity), 0)
})

const checkout = async () => {
  cartItems.value.map(item => {
    console.log(item.idProducto);
  })
  try {
    // Crear el objeto para enviar
    const ordenPagoRequest = {
      detalles: cartItems.value.map(item => ({
        idProducto: item.idProducto,
        cantidad: item.quantity,
        precioUnitario: item.precio || null,
      })),
      orden: {
        idCliente: localStorage.getItem("userId"),
        fechaOrden: new Date(),
        estado: 'pendiente',
        total: null,
      }
    };

    // Realizar la solicitud al backend
    await axios.post(`${API_URL}/orden/pagar`, ordenPagoRequest);

    clearCart(); // Vacía el carrito tras el éxito
    alert('Orden enviada exitosamente.');
  } catch (error) {
    console.error('Error al enviar la orden:', error);
    alert('Hubo un problema al procesar tu orden.');
  }
};


</script>