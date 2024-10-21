<template>
  <div class = 'flexed'>
  <div id="login" class = login-container>
    <form class="login-form" v-on:submit.prevent="login">
     
      <h1 class="login-title">Please Log In </h1>
      <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div class="alert alert-success" role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username" class="bold-label">EMAIL:</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password" class="bold-label">PASSWORD:</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button class="btn btn-primary" type="submit">SIGN IN</button>
      <p class="register-link">
      <router-link v-bind:to="{ name: 'register' }">Need an account? Sign up.</router-link></p>
      <p class="register-link">
          <router-link v-bind:to="{ name: 'password' }">Reset Password</router-link></p>
        
      
    </form>
  </div>
</div>
  
</template>

<script>
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