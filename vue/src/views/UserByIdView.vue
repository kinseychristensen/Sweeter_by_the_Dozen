<template>
  <div>


<div v-if="isLoading">Loading...</div>
  <div v-else id="user-page-grid">

   <Avatar :userId="userId" id="user-page-avatar"/>
   <div id="user-page-recipes">
    <div v-for="recipe in userRecipes" :key="recipe.recipeId">
      <RecipeCard :recipe="recipe"/>
    </div>
    </div> 
  </div>
  </div>

</template>



<script>


import RecipeService from '../services/RecipeService';
import RecipeCard from '../components/RecipeCard.vue';
import Avatar from '../components/Avatar.vue';



export default {
  name: 'MyRecipesView',
  components: {
  RecipeCard,
  Avatar
},
data() {
  return {
   userName: '',
   userId: 0,
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
  this.userId = userId;
  this.userName = this.$route.params.displayName;
  this.getUserRecipes(userId);


}
}

</script>



<style scoped>


</style>