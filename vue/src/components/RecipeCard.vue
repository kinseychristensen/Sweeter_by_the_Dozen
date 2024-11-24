<template>
    <div class="card">
  
      
      <router-link class="card-tab-first" v-bind:to="{ name: 'recipe', params: {recipeId: recipe.recipeId}}"><a class="tab-text">{{ recipe.title }}</a></router-link>
<div class="card-base">
{{ recipe.description }}

<a v-for="tag1 in recipe.recipeTagList" :key="tag1.tagId">{{ tag1.tag }} - </a>


<router-link v-bind:to="{ name: 'user', params: {userId: recipe.userId, displayName: recipe.writer}}">{{recipe.writer}}</router-link>

</div>
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