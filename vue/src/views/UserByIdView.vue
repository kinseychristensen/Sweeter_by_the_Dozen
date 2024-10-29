<template>
  <div>


<div v-if="isLoading">Loading...</div>
  <div v-else>
   
    <p></p>
    {{userName}}'s RECIPES:
    <div v-for="recipe in userRecipes" :key="recipe.recipeId">
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
   userName: '',
    userRecipes: [],
   
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
 
  getUserRecipes(userId) {
    RecipeService.getRecipesByUser(userId)
    .then(response => {
      this.userRecipes = response.data;
      this.isLoading = false;
    })
  }, 


}, 
created() {

  this.isLoading = true;
  const userId = parseInt(this.$route.params.userId);
  this.userName = this.$route.params.displayName;
  this.getUserRecipes(userId);


}
}

</script>



<style scoped>


</style>