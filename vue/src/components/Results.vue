<template>
    <div class="home-container">
Results

        <RecipeCard/>
        <RecipeCard/>
    </div>
  </template>
  
  
  
  <script>
  import RecipeCard from '../components/RecipeCard.vue';

  
  
  export default {
    name: 'Results',
    components: {
     RecipeCard
  }
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>