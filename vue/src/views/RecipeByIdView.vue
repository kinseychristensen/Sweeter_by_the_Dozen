<template>
    <div>
<h1>Recipe by Id Page</h1>
{{recipe.title}}
{{ recipe.description }}
<button @click="nowShowRecipe">show recipe</button>
<button @click="nowShowComments">show comments</button>
<button @click="nowShowPhotos">show photos</button>

<div v-if="showRecipe">

  <div v-for=" ing in recipe.ingredientList" :key="ing.ingredientNum">
   <BuildFraction :numerator="ing.amountNumerator" :denominator="ing.amountDenominator"/> {{ ing.unitType }} {{ ing.quantifier }} {{ ing.ingredientText }} 


  </div>
  <p></p>
 
  <ol>
    <li v-for="step in recipe.recipeStepList" :key="step.stepNum">{{ step.instructions }}</li>
  </ol>


  <div v-if="isAuthenticated">
<SaveRecipe :recipe="recipe"/>
</div>
<div v-else>you must sign in to save a recipe</div>

<router-link v-bind:to="{ name: 'user', params: {userId: 1}}">go to recipe writer's page</router-link>
    </div>



<div v-if="showComments">
<CommentsDisplay :recipeId="recipe.recipeId"/></div>
<div v-if="showPhotos">
<PhotoDisplay :photos="recipe.recipePicList"/>
</div>
</div>


  </template>
  
  
  
  <script>
  
  import CommentsDisplay from '../components/CommentsDisplay.vue';
import PhotoDisplay from '../components/PhotoDisplay.vue';
import RecipeService from '../services/RecipeService';
import SaveRecipe from '../components/SaveRecipe.vue';
import BuildFraction from '../components/BuildFraction.vue';





  
  export default {
    name: 'RecipeByIdView',
    components: {
     CommentsDisplay,
     PhotoDisplay,
     SaveRecipe,
     BuildFraction
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
    },
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