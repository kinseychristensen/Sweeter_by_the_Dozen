<template>
  <div>
    <div id="login-full" class="login-container">
      <form class="login-form" v-on:submit.prevent="login">
        <h1 class="login-title">Please Log In</h1>

        <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
          Invalid username and password!
        </div>

        <div class="alert alert-success" role="alert" v-if="this.$route.query.registration">
          Thank you for registering, please sign in.
        </div>

     
          <label for="username" class="bold-label">Email:</label>
          <input type="text" id="username" v-model="user.username" required autofocus />
    

       
          <label for="password" class="bold-label">Password:</label>
          <input type="password" id="password" v-model="user.password" required />
      
        <div id="login-button-flex">
          <button type="button"><RouterLink v-bind:to="{ name: 'password' }" class="button-text">Reset Password</RouterLink></button>
          <button type="submit">Sign In</button>
        </div>
  

       
      </form>
    </div>
  </div>
</template>

<script>
import { RouterLink } from "vue-router";
import authService from "../services/AuthService";

export default {
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {

    
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;

          }
        });
    }
  }
};
</script>

<style scoped>


</style>