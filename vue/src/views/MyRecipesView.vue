<template>
    <div>


<div v-if="isLoading">Loading...</div>
    <div v-else>
      <div v-if="isAuthenticated" id="recipes-page-flex">

      <div id="saved-recipes">
      <img src="/public/assets/icons/blue_banner_saved_recipes.png" alt="saved recipes" class="recipe-header-banner"/>
      <div v-for="recipe in savedRecipes" :key="recipe.recipeId">
        <RecipeCard :recipe="recipe"/>
      </div>  
    </div>
<div id="my-recipes">
  <img src="/public/assets/icons/blue_banner_my_recipes.png" alt="my recipes" class="recipe-header-banner"/>
      <div v-for="recipe in myRecipes" :key="recipe.recipeId">
        <RecipeCard :recipe="recipe"/>
      </div> 
    </div>
  </div>
  <div v-else id="not-signed-in"> You must be signed in to view your recipes.</div>
    </div></div>
  </template>
  
  
  
  <script>
 
  import { mapGetters } from 'vuex/dist/vuex.cjs.js';
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

  computed: {
    ...mapGetters(['isAuthenticated']) // Maps `isAuthenticated` to a computed property
  
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
      if(this.isAuthenticated){
      RecipeService.getSavedRecipes()
      .then(response => {
        this.savedRecipes = response.data;
        this.getMyRecipes();
      })}else {
        this.isLoading = false;
      }
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