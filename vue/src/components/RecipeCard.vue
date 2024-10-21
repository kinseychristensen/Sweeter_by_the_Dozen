<template>
    <div class="home-container">
RECIPE CARD

<router-link v-bind:to="{ name: 'recipe', params: {recipeId: 1}}">GO TO recipe 1</router-link>
<router-link v-bind:to="{ name: 'recipe', params: {recipeId: 2}}">GO TO recipe 2</router-link>

    </div>
  </template>
  
  
  
  <script>
import { RouterLink } from 'vue-router';

  
  
  export default {
    name: 'SearchView',
    components: {
     
  }
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>