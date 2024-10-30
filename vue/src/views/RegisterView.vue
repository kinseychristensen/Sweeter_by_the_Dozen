<template>
   <div id="register" class="register-container">
    <form class="register-form" v-on:submit.prevent="register">
      
      <h1 class="register-title">Create Account</h1>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username" class="bold-label">EMAIL:</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="displayName" class="bold-label">DISPLAY NAME:</label>
        <input type="text" id="displayName" v-model="user.displayName" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password" class="bold-label">PASSWORD:</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword" class="bold-label">Confirm Password</label>
        <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
      </div>
      <button type="submit" class="btn">Create Account</button>
      <p class="login-link"><router-link v-bind:to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
    avatar: {{ user.avatarId }}
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  data() {
    return {
      user: {
        username: '',
        displayName: '',
        password: '',
        confirmPassword: '',
        role: 'user',
        avatarId: 1,
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: User email or display name is already in use.';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>


</style>
