<template>
    <div>
      <p></p>
      
      <router-link v-bind:to="{ name: 'recipe', params: {recipeId: recipe.recipeId}}">{{ recipe.title }}</router-link>

{{ recipe.description }}
- 
<a v-for="tag1 in recipe.recipeTagList" :key="tag1.tagId">{{ tag1.tag }} - </a>


<router-link v-bind:to="{ name: 'user', params: {userId: recipe.userId, displayName: recipe.writer}}">{{recipe.writer}}</router-link>
<p></p>
    </div>
  </template>
  
  
  
  <script>
import { RouterLink } from 'vue-router';


export default {
  props: ['recipe'],
data() {
    return {
      

    }
  }, 
 
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>