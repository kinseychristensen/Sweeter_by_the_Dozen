<template>
    <div>
      <p></p>
{{ recipe.title }}
<router-link v-bind:to="{ name: 'recipe', params: {recipeId: recipe.recipeId}}">GO TO recipe</router-link>

<router-link v-bind:to="{ name: 'user', params: {userId: recipe.userId}}">GO TO  user</router-link>
<p></p>
    </div>
  </template>
  
  
  
  <script>
import { RouterLink } from 'vue-router';
import RecipeService from '../services/RecipeService';

export default {
  props: ['recipe'],
data() {
    return {
      
      isLoading: false,
      showComments: false,
      showPhotos: false,
      showRecipe: true,

    }
  }, 
 
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>