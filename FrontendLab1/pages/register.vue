<template>
  <div class="register">
    <h1 class="title text-2xl">Sign Up</h1>
    <v-form @submit.prevent="registrarUsuario" class="form">
      <v-row dense>
        <v-col cols="6">
          <v-text-field
            v-model="nombre"
            label="Nombre"
            outlined
            required
            placeholder="Nombre"
            :error="!!errors.nombre"
          ></v-text-field>
          <span v-if="errors.nombre" class="error">{{ errors.nombre }}</span>
        </v-col>

        <v-col cols="6">
          <v-text-field
            id="address-input"
            v-model="direccion"
            label="Dirección"
            outlined
            required
            placeholder="Dirección"
            :error="!!errors.direccion"
          ></v-text-field>
          <span v-if="errors.direccion" class="error">{{ errors.direccion }}</span>
        </v-col>

        <v-col cols="6">
          <v-text-field
            v-model="email"
            label="Email"
            outlined
            required
            type="email"
            placeholder="Email"
            :error="!!errors.email"
          ></v-text-field>
          <span v-if="errors.email" class="error">{{ errors.email }}</span>
        </v-col>

        <v-col cols="6">
          <v-text-field
            v-model="telefono"
            label="Teléfono"
            outlined
            required
            type="tel"
            placeholder="Teléfono"
            :error="!!errors.telefono"
          ></v-text-field>
          <span v-if="errors.telefono" class="error">{{ errors.telefono }}</span>
        </v-col>

        <v-col cols="6">
          <v-text-field
            v-model="password"
            label="Contraseña"
            outlined
            required
            type="password"
            placeholder="Contraseña"
            :error="!!errors.password"
          ></v-text-field>
          <span v-if="errors.password" class="error">{{ errors.password }}</span>
        </v-col>

        <v-col cols="6">
          <v-text-field
            v-model="passwordRepeat"
            label="Repite la contraseña"
            outlined
            required
            type="password"
            placeholder="Contraseña"
            :error="!!errors.passwordRepeat"
          ></v-text-field>
          <span v-if="errors.passwordRepeat" class="error">{{ errors.passwordRepeat }}</span>
        </v-col>
      </v-row>

      <v-btn class="form-submit" type="submit" color="primary">
        Registrarse
      </v-btn>
    </v-form>
  </div>
</template>



<script>
import axios from "axios";

export default {
  mounted() {
    this.initializeAutocomplete();
  },
  data: () => ({
    nombre: "",
    direccion: "",
    email: "",
    telefono: "",
    password: "",
    passwordRepeat: "",
    errors: {}, // Para almacenar errores
  }),
  methods: {
    validarCampos() {
      this.errors = {};

      // Validar nombre (solo letras, no vacío)
      if (!this.nombre.trim()) {
        this.errors.nombre = "El nombre no puede estar vacío.";
      } else if (/\d/.test(this.nombre)) {
        this.errors.nombre = "El nombre no puede contener números.";
      }

      // Validar dirección (no vacío)
      if (!this.direccion.trim()) {
        this.errors.direccion = "La dirección no puede estar vacía.";
      }

      // Validar email (formato y no vacío)
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!this.email.trim()) {
        this.errors.email = "El email no puede estar vacío.";
      } else if (!emailRegex.test(this.email)) {
        this.errors.email = "El email no tiene un formato válido.";
      }

      // Validar teléfono (solo números, no vacío)
      if (!this.telefono.trim()) {
        this.errors.telefono = "El teléfono no puede estar vacío.";
      } else if (!/^\d+$/.test(this.telefono)) {
        this.errors.telefono = "El teléfono solo puede contener números.";
      }

      // Validar contraseña (más de 8 caracteres, no vacío)
      if (!this.password.trim()) {
        this.errors.password = "La contraseña no puede estar vacía.";
      } else if (this.password.length < 8) {
        this.errors.password = "La contraseña debe tener al menos 8 caracteres.";
      }

      // Validar repetición de contraseña
      if (this.password !== this.passwordRepeat) {
        this.errors.passwordRepeat = "Las contraseñas no coinciden.";
      }

      // Retorna verdadero si no hay errores
      return Object.keys(this.errors).length === 0;
    },

    registrarUsuario() {
      if (!this.validarCampos()) {
        // Si hay errores, los mostramos y no hacemos el post
        return;
      }

      // Enviar datos al servidor
      axios
        .post("http://localhost:8090/api/cliente/register", {
          nombre: this.nombre,
          direccion: this.direccion,
          email: this.email,
          telefono: this.telefono,
          password: this.password,
        })
        .then((response) => {
          console.log(response);
          this.$router.push("/login");
        })
        .catch((error) => {
          alert("Error al registrar el usuario. Por favor, inténtelo de nuevo.");
          console.log(error);
        });
    },

    initializeAutocomplete() {
      const input = document.getElementById("address-input");
      const autocomplete = new google.maps.places.Autocomplete(input, {
        types: ["geocode"],
        componentRestrictions: { country: "cl" },
        fields: ["address_components", "formatted_address", "geometry"],
      });

      autocomplete.addListener("place_changed", () => {
        const place = autocomplete.getPlace();

        if (!place.geometry) {
          console.error("Place not found");
        }
        console.log("Selected Address:", place.formatted_address);
        console.log("Latitude:", place.geometry.location.lat());
        console.log("Longitude:", place.geometry.location.lng());
        this.direccion = place.formatted_address;
      });
    },
  },
};
</script>


<style lang="scss" scoped>
.register {
  padding: 2rem;
}

.title {
  text-align: center;
}

.form {
  margin: 3rem auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 50%;
  min-width: 350px;
  max-width: 100%;
  background: rgb(255, 255, 255);
  border-radius: 5px;
  padding: 40px;
  box-shadow: 0 4px 10px 4px rgba(0, 0, 0, 0.3);
}

.form-label {
  margin-top: 2rem;
  color: rgb(0, 0, 0);
  margin-bottom: 0.5rem;

  &:first-of-type {
    margin-top: 0rem;
  }
}

.form-input {
  padding: 10px 15px;
  background: none;
  background-image: none;
  border: 1px solid white;
  color: rgb(0, 0, 0);
  box-shadow: 0 4px 10px 4px rgba(0, 0, 0, 0.3);

  &:focus {
    outline: 0;
    border-color: #000000;
  }
}

.form-submit {
  background: royalblue;

  color: white;

  padding: 2rem 1;
  cursor: pointer;

  &:hover {
    background: gray;
  }
}

.error {
  color: red;
  font-size: 0.9rem;
  margin-top: 0.2rem;
}

</style>