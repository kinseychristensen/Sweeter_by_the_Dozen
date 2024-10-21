<template>

  <div>
    <div id="nav">
      
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      <div v-else>
      <button @click="toggleRegister">Register</button>
      <button @click="toggleLogin">Login</button>
      </div>

      <div v-if="showLogIn">
        <Login />
        <button @click="toggleLogin">Cancel</button>
        <button @click="toggleRegister">Register</button>
      </div>

      <div v-if="showRegister">
        <Register />
        <button @click="toggleRegister">Cancel</button>
        <button @click="toggleLogin">Login</button>
      </div>
      
    </div>
    <div>
      <NavBar/>
    </div>
    <router-view />
  </div>
</template>

<script>
import Login from './components/Login.vue';
import Register from './components/Register.vue';
import NavBar from './components/NavBar.vue';

  
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

  }
  }

</script>

<style scoped>


</style>
