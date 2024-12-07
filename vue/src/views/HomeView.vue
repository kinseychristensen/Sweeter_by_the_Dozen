<template>
  <div v-if="isLoading">Loading...</div>
   
  <div v-else id="home-flex">

<div id="welcome">
  <img id="mixer-angled" src="/public/assets/icons/mixer_angled.png">

<h1 id="welcome-header">Welcome to Sweeter By The Dozen Recipes!</h1>

<img id="eggs" src="/public/assets/icons/eggs.png">


<div id="welcome-text"><p>We are so glad you are here! Whether you are looking to share cherished family recipes, discover new favorites, or connect with loved ones through the joy of cooking, this is your space.

</p><p>Organize your recipes, explore handy cooking tips, and share your kitchen creations with ease. Together, let's keep traditions alive and make every meal a little sweeter.

</p><p>Happy cooking!</p></div>






</div>
<div id="random-recipe">
<RecipeCard :recipe="recipe"/>

<button @click="getRandomRecipe">Find Me Another Recipe</button>
</div>

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

