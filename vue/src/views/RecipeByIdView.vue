<template>
    <div class="home-container">
recipe by id

<CommentsDisplay/>
<PhotoDisplay/>
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