<template>
    <div>


<div v-if="isLoading">Loading...</div>
    <div v-else>
      SAVED RECIPES:
      <div v-for="recipe in savedRecipes" :key="recipe.recipeId">
        <RecipeCard :recipe="recipe"/>
      </div>  
      <p></p>
      MY RECIPES:
      <div v-for="recipe in myRecipes" :key="recipe.recipeId">
        <RecipeCard :recipe="recipe"/>
      </div> 
    </div>
    </div>
  </template>
  
  
  
  <script>
 

  import RecipeService from '../services/RecipeService';
  import RecipeCard from '../components/RecipeCard.vue';
  
  export default {
    name: 'MyRecipesView',
    components: {
    RecipeCard
  },
  data() {
    return {
      savedRecipes: [],
      myRecipes: [],
     
      isLoading: false,

      

    }
  }, 
  methods: {

    handleError(error, verb) {
      if (error.response) {
        this.$store.commit('SET_NOTIFICATION', `Error ${verb} deck. Response received was ${error.response.statusText}.`);
      } else if (error.request) {
        this.$store.commit('SET_NOTIFICATION', `Error ${verb} deck. Server could not be reached.`);
      } else {
        this.$store.commit('SET_NOTIFICATION', `Error ${verb} deck. Request could not be created.`);
      }
    },
    getSavedRecipes() {
      RecipeService.getSavedRecipes()
      .then(response => {
        this.savedRecipes = response.data;
        this.getMyRecipes();
      })
    }, 
    getMyRecipes() {
      RecipeService.getRecipesForPrincipal()
      .then(response => {
        this.myRecipes = response.data;
        this.isLoading = false;
      })
    }


  }, 
  created() {

    this.isLoading = true;
    this.getSavedRecipes();


  }
  }
  
  </script>
  
  
  
  <style scoped>
  
  
  </style>