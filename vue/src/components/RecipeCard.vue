<template>
    <div>
      <p></p>
      
      <router-link v-bind:to="{ name: 'recipe', params: {recipeId: recipe.recipeId}}">{{ recipe.title }}</router-link>

{{ recipe.description }}

<div v-for="tag1 in recipe.recipeTagList" :key="tag1.tagId">{{ tag1.tag }}</div>


<router-link v-bind:to="{ name: 'user', params: {userId: recipe.userId}}">{{recipe.writer}}</router-link>
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