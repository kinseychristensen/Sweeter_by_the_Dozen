<template>
    <div class="lil-card">
  
      <span class="lil-space"></span>
      <router-link class="lil-card-tab" v-bind:to="{ name: 'recipe', params: {recipeId: recipe.recipeId}}"><a class="tab-text tab-text-small">{{ recipe.title }}</a></router-link>
<router-link class="lil-card-base" v-bind:to="{ name: 'recipe', params: {recipeId: recipe.recipeId}}">
{{ recipe.description }}


<a class="recipe-writer-lil">- {{recipe.writer}}</a>

<div class="card-tags" v-if="recipe.recipeTagList.length >0"> <p></p>~ <a v-for="tag1 in recipe.recipeTagList" :key="tag1.tagId">{{ tag1.tag }}  ~  </a>
</div>


</router-link>
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