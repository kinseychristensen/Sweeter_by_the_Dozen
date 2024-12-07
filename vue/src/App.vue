<template>


<div id="body-grid">
      
     <div id="register-login-buttons">
      <button v-if="$store.state.token != ''"><router-link class="button-text" v-bind:to="{ name: 'logout' }" >Logout</router-link></button>
      <button v-if="$store.state.token == ''"><router-link class="button-text" v-bind:to="{ name: 'register' }">Register</router-link></button>
      <button v-if="$store.state.token == ''"><router-link class="button-text" v-bind:to="{ name: 'login' }" >Login</router-link></button>
      <button><Router-Link v-bind:to="{name: 'about'}" class="button-text">About Us</Router-Link></button>           
      <button ><a href="mailto:sweeter.by.the.dozen.cookbook@gmail.com" class="button-text">Contact Us</a></button>
      </div>


      <NavBar id="nav-bar-parent" /> 
    
    
    
    
      <router-view id="router-view-container" />
    
    
    
    
      <footer id="app-footer">
     
    </footer>
  </div>

</template>

<script>
import NavBar from './components/NavBar.vue';
import { RouterLink } from 'vue-router';

  
export default {
  
    components: {

     NavBar
  }, 
  data () {
    return  {
      
    }
  },
  methods: {

  }}

</script>

<style>

@import url('https://fonts.googleapis.com/css2?family=Caveat:wght@400..700&display=swap');


</style>