<template>
    <div>
RECIPE CARD

<router-link v-bind:to="{ name: 'recipe', params: {recipeId: 1}}">GO TO recipe 1</router-link>
<p></p>
<router-link v-bind:to="{ name: 'user', params: {userId: 1}}">GO TO  user</router-link>

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