<template>
    <v-container>
      <v-row>
        <v-col v-if="cliente" cols="12" md="6">
          <v-card>
            <v-card-title>Mi Cuenta</v-card-title>
            <v-card-subtitle>Detalles del Cliente</v-card-subtitle>
            <v-card-text>
              <v-list>
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title>Nombre:</v-list-item-title>
                    <v-list-item-subtitle>{{ cliente.nombre }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
  
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title>Dirección:</v-list-item-title>
                    <v-list-item-subtitle>{{ formattedAddress }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
  
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title>Email:</v-list-item-title>
                    <v-list-item-subtitle>{{ cliente.email }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
  
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title>Teléfono:</v-list-item-title>
                    <v-list-item-subtitle>{{ cliente.telefono }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script>
  import axios from 'axios';
  import { id } from 'vuetify/locale';
  
  export default {
    data() {
      return {
        cliente: null, // Guardará los datos del cliente
        formattedAddress: null,
      };
    },
    async mounted() {
      await this.obtenerDatosCliente();  // Obtiene los datos del cliente al montar el componente
    },
    methods: {
      async obtenerDatosCliente() {
        const idCliente = localStorage.getItem('userId'); // Obtener el ID desde localStorage
        console.log(idCliente);
        if (idCliente) {
          try {
            const response = await axios.get(`http://localhost:8090/api/cliente/${idCliente}`); // Realizar la solicitud GET
            this.cliente = response.data;  // Guardar los datos del cliente
            const formattedAddress = await axios.get(`http://localhost:8090/api/direccion/formattedAddress/${idCliente}`);
            this.formattedAddress = formattedAddress.data;
          } catch (error) {
            console.error("Error al obtener los datos del cliente", error);
            this.$router.push('/login');  // Si hay error, redirigir al login
          }
        } else {
          this.$router.push('/login');  // Si no hay ID en localStorage, redirigir al login
        }
      },
    },
  };
  </script>
  