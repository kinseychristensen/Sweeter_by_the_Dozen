<template>
  
<div class="card">

<button @click="nowShowRecipe" :class="{'active-tab': showRecipe}" class="card-tab-first tab-text">{{recipe.title}}</button>
<button @click="nowShowComments"  :class="{'active-tab': showComments}" class="card-tab-second tab-text">-Comments-</button>
<button @click="nowShowPhotos"  :class="{'active-tab': showPhotos}" class="card-tab-third tab-text">-Photos-</button>
<a class="spacing"></a>
<div class="card-base">



<div v-if="showRecipe" class="recipe-grid">


  <div class="ingredient-list"><div class="ingredient" v-for=" ing in recipe.ingredientList" :key="ing.ingredientNum">
   <BuildFraction :numerator="ing.amountNumerator" :denominator="ing.amountDenominator"/> {{ ing.unitType }} {{ ing.quantifier }} {{ ing.ingredientText }} 

  </div>
  </div>
 
  <ol class="step-list">
    <li v-for="step in recipe.recipeStepList" :key="step.stepNum">{{ step.instructions }}</li>
  </ol>


  <div v-if="isAuthenticated" class="save-recipe">
<SaveRecipe :recipe="recipe"/>
</div>


<router-link  class="recipe-writer" v-if="recipe.userId" v-bind:to="{ name: 'user', params: {userId: recipe.userId, displayName: recipe.writer}}">
  <Avatar :userId="recipe.userId"/> </router-link>


      <p class="description">{{ recipe.description }}</p>
      <p class="attribution">Attribution: {{ recipe.attribution }}</p>
    </div>

<div v-if="showComments">
<CommentsDisplay :recipeId="recipe.recipeId"/></div>
<div v-if="showPhotos">
<PhotoDisplay :photos="recipe.recipePicList" :recipeId="recipe.recipeId"/>
</div>
</div>
</div>


  </template>
  
  
  
  <script>
  
  import CommentsDisplay from '../components/CommentsDisplay.vue';
import PhotoDisplay from '../components/PhotoDisplay.vue';
import RecipeService from '../services/RecipeService';
import SaveRecipe from '../components/SaveRecipe.vue';
import BuildFraction from '../components/BuildFraction.vue';
import Avatar from '../components/Avatar.vue';





  
  export default {
    name: 'RecipeByIdView',
    components: {
     CommentsDisplay,
     PhotoDisplay,
     SaveRecipe,
     BuildFraction,
     Avatar
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