<template>
    <v-footer color="primary" class="pa-4">
      <v-container color="primary">
        <!-- Primera fila: Enlaces útiles -->
        <v-row color="primary">
          <v-col cols="12" md="4" color="primary" >
            <h3 class="text-white text-uppercase">Enlaces útiles</h3>
            <v-list color="primary" style="background: none !important; color: white;">
              <v-list-item
                href="/privacidad"
              >
                <v-icon left >mdi-lock</v-icon>
                <v-list-item-title >Política de Privacidad</v-list-item-title>
              </v-list-item>
              <v-list-item
                href="/terminos"
              >
                <v-icon left >mdi-file-document-outline</v-icon>
                <v-list-item-title >Términos y Condiciones</v-list-item-title>
              </v-list-item>
              <v-list-item
                href="/faq"
              >
                <v-icon left >mdi-help-circle-outline</v-icon>
                <v-list-item-title >Preguntas Frecuentes</v-list-item-title>
              </v-list-item>
              <v-list-item
                href="/contacto"
              >
                <v-icon left >mdi-email-outline</v-icon>
                <v-list-item-title >Contáctanos</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-col>
  
          <!-- Segunda fila: Redes sociales -->
          <v-col cols="12" md="4" class="text-center">
            <h3 class="text-white text-uppercase">Síguenos</h3>
            <div class="d-flex justify-center">
              <v-btn icon href="https://www.facebook.com" class="mx-2">
                <v-icon size="36" color="black">mdi-facebook</v-icon>
              </v-btn>
              <v-btn icon href="https://www.instagram.com" class="mx-2">
                <v-icon size="36" color="black">mdi-instagram</v-icon>
              </v-btn>
              <v-btn icon href="https://www.twitter.com" class="mx-2">
                <v-icon size="36" color="black">mdi-twitter</v-icon>
              </v-btn>
            </div>
          </v-col>
  
          <!-- Tercera fila: Repositorio -->
          <v-col cols="12" md="4" class="text-center">
            <h3 class="text-white text-uppercase">Repositorio</h3>
            <v-btn text class="mx-2" href="https://github.com/jose-marin-garcia/BDA_LAB1.git">
              <v-icon left>mdi-github</v-icon>
              Repositorio del Proyecto
            </v-btn>
          </v-col>
        </v-row>
  
        <!-- Fila inferior: Derechos reservados -->
        <v-row>
          <v-col class="text-center">
            <small class="text-white">&copy; 2024 E-commerce. Todos los derechos reservados.</small>
          </v-col>
        </v-row>
      </v-container>
    </v-footer>
  </template>
  
  <script setup>
  </script>
  
  <style scoped>
  
  </style>
  