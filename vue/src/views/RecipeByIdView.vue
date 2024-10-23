<template>
    <div>
<h1>Recipe by Id Page</h1>
{{recipe.title}}
{{ recipe.description }}
<button @click="nowShowRecipe">show recipe</button>
<button @click="nowShowComments">show comments</button>
<button @click="nowShowPhotos">show photos</button>

<div v-if="showRecipe">

  ingredients: {{ recipe.ingredientList }}
  <p></p>
  steps: {{ recipe.recipeStepList }}

  <div v-if="isAuthenticated">
<SaveRecipe :recipe="recipe"/>
</div>
<div v-else>you must sign in to save a recipe</div>

<router-link v-bind:to="{ name: 'user', params: {userId: 1}}">go to recipe writer's page</router-link>
    </div>



<div v-if="showComments">
<CommentsDisplay/></div>
<div v-if="showPhotos">
<PhotoDisplay/>
</div>
</div>
  </template>
  
  
  
  <script>
  
  import CommentsDisplay from '../components/CommentsDisplay.vue';
import PhotoDisplay from '../components/PhotoDisplay.vue';
import RecipeService from '../services/RecipeService';
import SaveRecipe from '../components/SaveRecipe.vue';




  
  export default {
    name: 'RecipeByIdView',
    components: {
     CommentsDisplay,
     PhotoDisplay,
     SaveRecipe
  },
  data() {
    return {
      recipe: {},
      isLoading: false,
      showComments: false,
      showPhotos: false,
      showRecipe: true,

    }
  }, 
  computed: {
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
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
    getRecipe(recipeId) {
      RecipeService.getRecipeDetails(recipeId)
      .then(response => {
        this.recipe = response.data;
        this.isLoading = false;
      })
    },
    nowShowPhotos() {
      this.showPhotos = true;
      this.showComments = false;
      this.showRecipe = false;
    },
    nowShowComments() {
      this.showPhotos = false;
      this.showComments = true;
      this.showRecipe = false;
    },
    nowShowRecipe() {
      this.showPhotos = false;
      this.showComments = false;
      this.showRecipe = true;
    },


  }, 
  created() {

    this.isLoading = true;
    this.showRecipe = true;
    const recipeId = parseInt(this.$route.params.recipeId);
    this.getRecipe(recipeId);


  }
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>