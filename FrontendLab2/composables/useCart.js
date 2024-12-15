import { ref, computed, watch } from 'vue';

const savedCart = JSON.parse(localStorage.getItem('cartItems')) || [];
const cartItems = ref(savedCart);

const cartCount = computed(() => cartItems.value.reduce((sum, item) => sum + item.quantity, 0));
const cartTotal = computed(() =>
  cartItems.value.reduce((sum, item) => sum + item.precio * item.quantity, 0)
);

const addToCart = (product) => {
  const existingProduct = cartItems.value.find((item) => item.idProducto === product.idProducto);
  if (existingProduct) {
    existingProduct.quantity += 1;
  } else {
    cartItems.value.push({
      idProducto: product.idProducto,
      nombre: product.nombre,
      descripcion: product.descripcion,
      precio: product.precio,
      stock: product.stock,
      quantity: 1,
    });
  }
};

const increaseQuantity = (productId) => {
  const product = cartItems.value.find((item) => item.idProducto === productId);
  if (product && product.stock-product.quantity > 0) {
    product.quantity += 1;
  }
  else{
    alert("No hay stock disponible");
  }
};

const decreaseQuantity = (productId) => {
  const product = cartItems.value.find((item) => item.idProducto === productId);
  if (product && product.quantity > 0) {
    product.quantity -= 1;
  }
  if (product.quantity === 0) {
    removeItem(productId);
  }
};

const removeItem = (productId) => {
  const productIndex = cartItems.value.findIndex((item) => item.idProducto === productId);
  if (productIndex !== -1) {
    cartItems.value.splice(productIndex, 1);
  }
};

const clearCart = () => {
  cartItems.value = [];
};

watch(
  cartItems,
  (newCart) => {
    localStorage.setItem('cartItems', JSON.stringify(newCart));
  },
  { deep: true }
);

export const useCart = () => ({
  cartItems,
  cartCount,
  cartTotal,
  addToCart,
  increaseQuantity,
  decreaseQuantity,
  removeItem,
  clearCart,
});
