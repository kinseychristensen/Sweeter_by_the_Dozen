<template>
   
      <div v-if="isLoading">Loading...</div>
    <div v-else>
search
<form v-on:submit.prevent="getRecipesBySearch">
  <input text v-model="keyword"/>
  <button>search</button>
</form>



      <div v-for="recipe in recipes" :key="recipe.recipeId">
        <RecipeCard :recipe="recipe"/>
      </div>

    </div>
  </template>
  
  
  
  <script>
import RecipeCard from '../components/RecipeCard.vue';
import RecipeService from '../services/RecipeService';
  
  export default {
    name: 'SearchView',
    components: {
    RecipeCard
  },

  data() {
    return {
      keyword: '',
      recipes: [],
      isLoading: false,
      pageNum: 0,
      tagsList: [1, 2],
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
    getRecipesBySearch() {
      RecipeService.searchByKeywordAndTags(this.keyword, this.pageNum, this.tagsList)
        .then(response => {
          console.log('API response:', response);
          this.recipes = response.data;
          this.isLoading = false;
        })
        .catch(error => {
          this.isLoading = false;
          this.handleError(error, 'fetching');
        });
    }
  },
  created() {
    this.isLoading = true;
    this.getRecipesBySearch();
  }
}
</script>

<style scoped>
</style>
