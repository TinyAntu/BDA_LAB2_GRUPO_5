<template>
  <div class = "login">
      <h1 class ="title text-2xl ">Login</h1>
      <v-form @submit.prevent="handleLogin" class="form">
          <v-row dense>

              <v-col cols="12">
                  <v-text-field v-model="email" label="Email" outlined required placeholder="Email"></v-text-field>
              </v-col>
              
              <v-col cols="12">
              <v-text-field v-model="password" label="Contraseña" outlined required type="password" placeholder="Contraseña"></v-text-field>
              </v-col>

          </v-row>
          <v-btn class="form-submit" type="submit" color="primary">
              Login
          </v-btn>
      </v-form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
data() {
  return {
    email: '',
    password: '',
  };
},
methods: {
  async handleLogin() {
    if (!this.email || !this.password) {
      alert('Por favor, complete todos los campos');
      return;
    }
    try {
      const response = await axios.post('http://localhost:8090/authenticate/login', {
        email: this.email,
        password: this.password
      });
      console.log("Respuesta del login", response.data);
      // Guardamos el JWT en localStorage o sessionStorage
      localStorage.setItem("jwtToken", response.data.token);

      localStorage.setItem("isAuthenticated", true);
      localStorage.setItem("userId", response.data.userId);
      localStorage.setItem("userName", response.data.name);
      localStorage.setItem("isAdmin", response.data.name === "Admin");
      
      // Redirigir al usuario a la página principal o dashboard
      this.$router.push('/');

    } catch (error) {
      console.error("Error en el login", error);
      alert('Error al iniciar sesión');
      // Manejar error (mostrar mensaje al usuario)
    }
  },
},
};
</script>

<style lang="scss" scoped>
.login {
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
margin: 1rem 0 0;
color: #ff4a96;
}
</style>