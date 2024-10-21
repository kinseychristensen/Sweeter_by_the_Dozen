<template>
    <div>
user settings

<router-link v-bind:to="{ name: 'admin-tools' }"> go to admin tools</router-link>
<router-link v-bind:to="{ name: 'password' }"> reset passwords</router-link>
</div>
  </template>
  
  
  
  <script>
import { RouterLink } from 'vue-router';


  
  
  export default {
    name: 'UserSettingsView',
    components: {
     
  }
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>