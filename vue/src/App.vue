<template>

  <div id="body">

      
     <div id="register-login-buttons">
      <button v-if="$store.state.token != ''"><router-link class="button-text" v-bind:to="{ name: 'logout' }" >Logout</router-link></button>
      <button @click="toggleRegister"  v-if="$store.state.token == ''">Register</button>
      <button @click="toggleLogin"  v-if="$store.state.token == ''">Login</button>
      <button><Router-Link v-bind:to="{name: 'about'}" class="button-text">About Us</Router-Link></button>           
      <button ><a href="mailto:sweeter.by.the.dozen.cookbook@gmail.com" class="button-text">Contact Us</a></button>
      </div>


      <div id="register-login-display">
      <div v-if="showLogIn">
        <Login />
        <button @click="cancel">Cancel</button>
        <button @click="toggleRegister">Register for a New Account</button>
      </div>

      <div v-if="showRegister">
        <Register />
        <button @click="cancel">Cancel</button>
        <button @click="toggleLogin">Login to Your Account</button>
      </div>
    </div>



      <NavBar id="nav-bar-parent"  v-if="!showRegister && !showLogIn" /> 
    
    
    
    
      <router-view id="router-view-container"   v-if="!showRegister && !showLogIn" />
    
    
    
    
      <footer id="app-footer">
     
    </footer>
  
</div>
</template>

<script>
import Login from './components/Login.vue';
import Register from './components/Register.vue';
import NavBar from './components/NavBar.vue';
import { RouterLink } from 'vue-router';

  
export default {
  
    components: {
     Login,
     Register, 
     NavBar
  }, 
  data () {
    return  {
      showRegister : false,
      showLogIn : false,
    }
  },
  methods: {

    cancel() {
      this.showLogIn = false;
      this.showRegister = false;
    },

    toggleLogin() {
      this.showLogIn = !this.showLogIn;

      if(this.showLogIn){
        this.showRegister = false;
      }
    },   
    
    toggleRegister() {
      this.showRegister = !this.showRegister;

      if(this.showRegister){
        this.showLogIn = false;
      }
    }

  },
  created() {
    this.showLogIn = false;
    this.showRegister = false;
  }
  }

</script>

<style>

@import url('https://fonts.googleapis.com/css2?family=Caveat:wght@400..700&display=swap');


</style>