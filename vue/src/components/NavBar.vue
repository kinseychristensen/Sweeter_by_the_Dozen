<template>
    <div>

        <router-link v-bind:to="{ name: 'my-recipes' }">my recipes</router-link>     
   <router-link v-bind:to="{ name: 'settings' }"> user settings</router-link>  
   <router-link v-bind:to="{ name: 'search' }"> search</router-link>
   <router-link v-bind:to="{ name: 'grow' }"> grow our site</router-link>
   <router-link v-bind:to="{ name: 'home' }"> Home</router-link>


    </div>
  </template>
  
  
  
  <script>
  
  
  export default {
    name: 'NavBar',
    components: {
     
  }
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>