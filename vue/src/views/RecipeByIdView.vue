<template>
    <div>
recipe by id
{{ recipe }}

<CommentsDisplay/>
<PhotoDisplay/>

<router-link v-bind:to="{ name: 'user', params: {userId: 1}}">GO TO  user</router-link>
    </div>
  </template>
  
  
  
  <script>
  
  import CommentsDisplay from '../components/CommentsDisplay.vue';
import PhotoDisplay from '../components/PhotoDisplay.vue';
import RecipeService from '../services/RecipeService';



  
  export default {
    name: 'RecipeByIdView',
    components: {
     CommentsDisplay,
     PhotoDisplay
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
    }


  }, 
  created() {

    this.isLoading = true;
    const recipeId = parseInt(this.$route.params.recipeId);
    this.getRecipe(recipeId);


  }
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>