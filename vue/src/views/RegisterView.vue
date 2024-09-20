<template>
   <div id="register" class="register-container">
    <form class="register-form" v-on:submit.prevent="register">
      <img src="@/assets/CroppedFlipFlashLogo.png" alt="Logo" class="form-logo" /> 
      
      <h1 class="register-title">Create Account</h1>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username" class="bold-label">USERNAME:</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
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
      <p><router-link v-bind:to="{name: 'guest'}">Want to browse first?  Check out decks built by your school!</router-link></p>
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
        password: '',
        confirmPassword: '',
        role: 'user',
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
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
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
.register-container{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 75vh;
  background-color: white;

}
.register-form{
  background: rgb(196, 196, 196);
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 400px;  
  text-align: center;
}

.register-title{
  margin-bottom: 1.5rem;
  font-size: 30px;
  text-align: center;
}
.form-input-group{
  margin-bottom: 10px;
}

label{
  display: block;
  margin-bottom: 2.5px;
}
.bold-label{
  font-weight: bold;
}

button.btn{

  width: 170px;
  padding: 2px;
  height: 30px;
  border: black;
  background-color: rgb(255, 217, 102,100);
  color: black;
  border-radius: 5px;
  font-size: 15px;
  cursor: pointer;
  transition: background-color .3s ease;
  font-weight: bold;  
}

button.btn:hover{
  background-color: yellow;
}

.login-link {
  text-align: center;
  margin-top: 20px;
}
.login-link a{
  color: blue;
}
.login-link a:hover{
  text-decoration: dashed;
}
img{
  width: 300px;
  
}

</style>
