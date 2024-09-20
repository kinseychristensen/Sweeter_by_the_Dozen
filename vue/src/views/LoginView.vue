<template>
  <div class = 'flexed'>
  <div id="login" class = login-container>
    <form class="login-form" v-on:submit.prevent="login">
      <img src="@/assets/CroppedFlipFlashLogo.png" alt="Logo" class="form-logo" />
      <h1 class="login-title">Please Log In </h1>
      <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div class="alert alert-success" role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username" class="bold-label">USERNAME:</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password" class="bold-label">PASSWORD:</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button class="btn btn-primary" type="submit">SIGN IN</button>
      <p class="register-link">
      <router-link v-bind:to="{ name: 'register' }">Need an account? Sign up.</router-link></p>
      <p><router-link v-bind:to="{name: 'guest'}">Want to browse first?  Check out decks built by your school!</router-link></p>
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
.login-container{
  display: flex;
  justify-content: center;
  align-items: center;
  height: 75vh;
  background-color: white;

}
.login-form{
  background: rgb(196, 196, 196);
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 400px;  
  text-align: center;
}

.login-title{
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
  margin-left: auto;
  width: 170px;
  padding: 2px;
  height: 30px;
  border: black;
  background-color: rgb(241, 214, 11);
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

.register-link {
  text-align: center;
  margin-top: 20px;
}
.register-link a{
  color: blue;
}
.register-link a:hover{
  text-decoration: dashed;
}
img{
  width: 300px;
  
}


</style>