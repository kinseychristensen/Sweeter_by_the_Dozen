<template>
    <div>
recipe by id

<CommentsDisplay/>
<PhotoDisplay/>

<router-link v-bind:to="{ name: 'user', params: {userId: 1}}">GO TO  user</router-link>
    </div>
  </template>
  
  
  
  <script>
  
  import CommentsDisplay from '../components/CommentsDisplay.vue';
import PhotoDisplay from '../components/PhotoDisplay.vue';



  
  export default {
    name: 'RecipeByIdView',
    components: {
     CommentsDisplay,
     PhotoDisplay
  }
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>