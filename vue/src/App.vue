<template>

  <div>
    <div id="nav">
      
     
      <button v-if="$store.state.token != ''"><router-link v-bind:to="{ name: 'logout' }" >Logout</router-link></button>
      <div v-else>
      <button @click="toggleRegister">Register</button>
      <button @click="toggleLogin">Login</button>
      </div>

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
    <div v-if="!showRegister && !showLogIn">
      <NavBar/>
    <router-view /><p></p></div>
    <footer>
      <Router-Link v-bind:to="{name: 'about'}">About Us</Router-Link>    |       
      <a href="mailto:sweeter.by.the.dozen.cookbook@gmail.com">Contact Us</a>
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

<style scoped>


</style>
