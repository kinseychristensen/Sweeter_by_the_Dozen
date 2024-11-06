<template>
  <div>
   home

<RecipeCard :recipe="recipe"/>

<button @click="getRandomRecipe">Find Me Another Recipe</button>



  </div>
</template>



<script>
import { RouterLink } from 'vue-router';
import RecipeService from '../services/RecipeService';
import RecipeCard from '../components/RecipeCard.vue';






export default {
  name: 'HomeView',
  components: {
    RecipeCard,

   
},
data() {
  return {
    isLoading: true,
    recipe: {},

  }
},
methods: {
  getRandomRecipe() {
      RecipeService.getRandomRecipe()
      .then(response => {
        this.recipe = response.data;
        this.isLoading = false;
      })
    },
},
created() {

this.isLoading = true;
this.getRandomRecipe();


}
}




</script>



<style scoped>




</style>

