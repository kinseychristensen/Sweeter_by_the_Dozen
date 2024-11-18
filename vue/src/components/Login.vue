<template>
  <div>
    <div id="login" class="login-container">
      <form class="login-form" v-on:submit.prevent="login">
        <h1 class="login-title">Please Log In</h1>

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

        <button type="submit">SIGN IN</button>

        <button type="button" @click="routeToReset">Reset Password</button>
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
    
    routeToReset() {
     
      localStorage.setItem('resetPasswordRequested', 'true');
      this.$router.push('/password-reset');
      window.location.reload();
    },

  
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status === 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);

            // Check if there's a redirect URL
            const urlParams = new URLSearchParams(window.location.search);
            const redirectRoute = urlParams.get('redirect');

            if (redirectRoute) {
              this.$router.push(decodeURIComponent(redirectRoute));
            } else {
              window.location.reload();
            }
          }
        })
        .catch(error => {
          const response = error.response;
          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  },

  mounted() {
    // Check if there's a redirect query parameter when loading the login page
    const urlParams = new URLSearchParams(window.location.search);
    const redirectRoute = urlParams.get('redirect');

    // If the page was accessed with a redirect query, store it for later use
    if (redirectRoute) {
      this.$store.commit('SET_REDIRECT_ROUTE', redirectRoute);  // Optional: store it in Vuex for global access
    }

    // Check if the reset password flag is set in localStorage after reload
    if (localStorage.getItem('resetPasswordRequested') === 'true') {
      // Navigate to /password-reset page
      this.$router.push('/password-reset');

      // Remove the flag from localStorage to prevent infinite redirect
      localStorage.removeItem('resetPasswordRequested');
    }
  }
};
</script>

<style scoped>
/* Your styling for the login page goes here */
</style>

