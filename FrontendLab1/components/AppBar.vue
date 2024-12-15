<template>
  <v-app-bar color="primary" class="navbar d-flex align-center">
    <!-- Icono de la tienda -->
    <v-btn icon color="white" href="/">
      <v-icon>mdi-store</v-icon>
    </v-btn>

    <!-- Contenedor para empujar los botones a la derecha -->
    <div class="ml-auto d-flex align-center">
      <!-- Menú desplegable para autenticación -->
      <v-menu offset-y>
        <template v-slot:activator="{ props }">
          <v-btn text v-bind="props">
            <v-icon>mdi-account-circle</v-icon>
            <span>{{ isAuthenticated ? userName : 'Ingresar' }}</span>
          </v-btn>
        </template>
        <v-list>
          <template v-if="isAuthenticated">
            <v-list-item>
              <v-btn text href="/pedidos">Mis Pedidos</v-btn>
            </v-list-item>
            <v-list-item>
              <v-btn text href="/perfil">Mi Cuenta</v-btn>
            </v-list-item>
            <v-list-item>
              <v-btn text @click="logout">Cerrar Sesión</v-btn>
            </v-list-item>
          </template>
          <template v-else>
            <v-list-item>
              <v-btn text href="/login">Iniciar Sesión</v-btn>
            </v-list-item>
            <v-list-item>
              <v-btn text href="/register">Registro</v-btn>
            </v-list-item>
          </template>
        </v-list>
      </v-menu>

      <v-menu v-if="isAdmin">
        <v-list-item>
          <v-btn text href="/order10">Ordenes10km</v-btn>
        </v-list-item>
        <v-list-item>
          <v-btn text href="/order100">Ordenes100kmMain</v-btn>
        </v-list-item> 
      </v-menu>

      <!-- Botón del carrito de compras -->
      <v-btn icon color="white" href="/cart">
        <v-icon>mdi-cart-outline</v-icon>
        <span v-if="cartCount > 0" class="badge">{{ cartCount }}</span>
      </v-btn>

      <!-- Menú desplegable para funciones SQL -->
      <v-menu v-if="isAdmin" offset-y>
        <template v-slot:activator="{ props }">
          <v-btn text v-bind="props">
            <v-icon class="mr-2">mdi-database</v-icon>
            Consultas Admin
          </v-btn>
        </template>
        <v-list>
          <!-- Botón para encontrar el almacén más cercano -->
          <v-list-item>
            <v-btn text @click="isModalOpen4 = true">Buscar Almacén Más Cercano</v-btn>
          </v-list-item>
          <!-- Botón para ordenes enviadas en un radio de 10km de cierto almacen -->
          <v-list-item>
            <v-btn text @click="fetchVariablePriceProduct">Producto con mayor variabilidad</v-btn>
          </v-list-item>
          <v-list-item>
            <v-btn text @click="fetchVariableReport">Reporte</v-btn>
          </v-list-item>
        </v-list>
      </v-menu>
    </div> <!-- Cierre del div contenedor -->

    <!-- Popup (Modal) para ingresar la ID del usuario -->
    <v-dialog v-model="isModalOpen4" max-width="500">
      <v-card>
        <v-card-title class="text-h5">
          Ingrese la ID de un Usuario
        </v-card-title>
        <v-card-text>
          <v-text-field
            label="ID de Usuario"
            v-model="inputUserId"
            outlined
          ></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" text @click="fetchCloserStore">Buscar</v-btn>
          <v-btn color="secondary" text @click="isModalOpen4 = false">Cancelar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Popup (Modal) para ingresar la ID del usuario -->
    <v-dialog v-model="isModalOpen5" max-width="500">
      <v-card>
        <v-card-title class="text-h5">
          Ingrese la ID de un Almacen
        </v-card-title>
        <v-card-text>
          <v-text-field
            label="ID de un Almacen"
            v-model="inputStoreId"
            outlined
          ></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" text @click="fetch10KmStoreOrders">Buscar</v-btn>
          <v-btn color="secondary" text @click="isModalOpen5 = false">Cancelar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Popup (Modal) para mostrar el producto -->
    <v-dialog v-model="isModalOpen1" max-width="500">
      <v-card>
        <v-card-title class="text-h5">
          Producto con la mayor variabilidad de precios en venta en las órdenes de compra, considerando las últimas 100 órdenes
        </v-card-title>
        <v-card-text>
          <!-- Mostrar detalles del producto -->
          <div v-if="producto">
            <p><strong>Nombre:</strong> {{ producto.nombre }}</p>
            <p><strong>Descripción:</strong> {{ producto.descripcion }}</p>
            <p><strong>Precio:</strong> ${{ producto.precio }}</p>
            <p><strong>Stock:</strong> {{ producto.stock }}</p>
            <p><strong>Estado:</strong> {{ producto.estado }}</p>
          </div>
          <div v-else>
            <p>No se encontraron datos del producto.</p>
          </div>
        </v-card-text>
        <v-card-title class="text-h5">
          Historial de Precios
        </v-card-title>
        <v-data-table
          v-if="precios.length"
          :items="precios"
          :headers="priceHistoryHeaders"
        >

          <template v-slot:item.fecha="{ item }">
            {{ new Date(item.fecha).toLocaleString() }}
          </template>
        </v-data-table>
        <div v-else class="text-center mt-4">
          <p>No hay historial de precios disponible.</p>
        </div>
        <v-card-actions>
          <v-btn color="primary" text @click="isModalOpen1 = false">Cerrar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Popup (Modal) para mostrar el reporte -->
    <v-dialog v-model="isModalOpen2" max-width="500">
      <v-card>
        <v-card-title class="text-h5">
          Reporte con los usuarios que más queries de inserción, actualización o eliminación ejecutan con las respectivas consultas.
        </v-card-title>
        <v-card-text>
          <!-- Mostrar detalles del producto -->
          <div v-if="report">
            <p><strong>Id de usuario:</strong> {{ report.usuario }}</p>
            <p><strong>Inserts:</strong> {{ report.inserts }}</p>
            <p><strong>Updates:</strong> {{ report.updates }}</p>
            <p><strong>Deletes:</strong> {{ report.deletes }}</p>
            <p><strong>Total de queries:</strong> {{ report.total }}</p>
          </div>
          <div v-else>
            <p>No hay un reporte</p>
          </div>
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" text @click="isModalOpen2 = false">Cerrar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-app-bar>
</template>


<script>
import { useCart } from "@/composables/useCart.js";
import axios from "axios";

const { cartCount } = useCart();

export default {
  data() {
    return {
      abrirDrawer: false,
      userName: "",
      isAuthenticated: false, // Se inicializa en falso
      producto: null, // Producto obtenido del backend
      report: null, // Reporte obtenido del backend
      isAdmin: false,
      isModalOpen1: false,
      isModalOpen2: false,
      isModalOpen3: false,
      isModalOpen4: false,
      isStoreModalOpen: false,
      inputUserId: "",
      inputStoreId: "",
      direccionStore: "",
      store: null,
      precios: [], // Historial de precios
      priceHistoryHeaders: [
        { title: 'Fecha', value: 'fecha' },
        { title: 'Precio', value: 'precio' },
      ],
    };
  },
  computed: {
    userName() {
      return localStorage.getItem("userName") || ""; // Obtiene siempre el valor actualizado de localStorage
    }
  },
  mounted() {
    this.actualizarEstadoAutenticacion(); // Actualiza el estado autenticado
    this.userName = localStorage.getItem("userName") || ""; 
  },
  watch: {
    // Observa los cambios en la ruta para actualizar el estado de autenticación
    '$route'(to, from) {
      this.actualizarEstadoAutenticacion();
    },
  },
  methods: {
    actualizarEstadoAutenticacion() {
      // Verifica el estado de autenticación desde localStorage
      this.isAuthenticated = localStorage.getItem("isAuthenticated") === "true";
      this.isAdmin = localStorage.getItem("isAdmin") === "true";
      if (!this.isAuthenticated && this.$route.path !== "/register" && this.$route.path !== "/login") {
        this.$router.push("/login"); // Redirige a la página de inicio de sesión
      }
    },

    logout() {
      if (!confirm("¿Estás seguro de que deseas cerrar sesión?")) {
        return;
      }
      localStorage.setItem("isAuthenticated", false);
      localStorage.removeItem("userId");
      localStorage.removeItem("jwtToken");
      localStorage.removeItem("userName");
      localStorage.removeItem("cartItems");
      localStorage.removeItem("isAdmin");
      this.actualizarEstadoAutenticacion();
      alert("Sesión cerrada exitosamente");
    },

    async fetchVariableReport() {
      const API_URL = "http://localhost:8090/api/report";
      try {
        const response = await axios.get(`${API_URL}`);
        console.log("Datos recibidos:", response.data);
        this.report = response.data[0]; // Actualiza los datos del producto
        this.isModalOpen2 = true; // Abre el modal
      } catch (error) {
        console.error("Error fetching report:", error);
        alert("No se pudo obtener el reporte.");
      }
    },

    async fetchCloserStore() {
      if (!this.inputUserId) {
        alert("Por favor, ingrese un ID de usuario.");
        return;
      }
      const API_URL = "http://localhost:8090/api/almacen";
      try {
        const response = await axios.get(`${API_URL}/almacenMasCercano/${this.inputUserId}`);
        console.log("Datos recibidos:", response.data);
        alert("El almacén más cercano es: " + response.data.nombre);
        this.isStoreModalOpen = true;
        this.isModalOpen4 = false; // Cierra el modal
        this.inputUserId = ""; // Limpia el campo de entrada
        const response_dir = await axios.get(`http://localhost:8090/api/direccion/${response.data.id_direccion}`);
        this.direccionStore = response_dir.data;
      } catch (error) {
        console.error("Error al buscar almacén más cercano:", error);
        alert("No se pudo obtener el almacén más cercano.");
      }
    },

    async fetchPriceHistory() {
      const API_URL = "http://localhost:8090/api/producto";
      try {
        const response = await axios.get(`${API_URL}/getPriceHistory/${this.producto.idProducto}`);
        console.log("Datos recibidos:", response.data);
        this.precios = response.data;
      } catch (error) {
        console.error("Error fetching price history:", error);
        alert("No se pudo obtener el historial de precios.");
      }
    },

    async fetchVariablePriceProduct() {
      const API_URL = "http://localhost:8090/api/producto";
      try {
        const response = await axios.get(`${API_URL}/getVariablePriceProduct`);
        console.log("Datos recibidos:", response.data);
        this.producto = response.data; // Actualiza los datos del producto
        console.log("idProducto:", response.data.idProducto);
        this.fetchPriceHistory();
        this.isModalOpen1 = true; // Abre el modal
      } catch (error) {
        console.error("Error fetching variable price product:", error);
        alert("No se pudo obtener el producto con precio variable.");
      }
    },
  },
};
</script>

<style scoped>
.badge {
  background-color: red;
  color: white;
  border-radius: 50%;
  padding: 0 8px;
  font-size: 12px;
  position: absolute;
  top: 8px;
  right: 8px;
}

.navbar {
  display: grid;
  width: 100%;
}

.v-toolbar__content {
  display: grid;
  width: 100%;
}

.v-card-title {
  word-wrap: break-word;
  white-space: normal; 
}
</style>
