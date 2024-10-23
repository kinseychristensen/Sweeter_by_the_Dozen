<template>
  <div>
    <h2>Results</h2>
    <div v-if="isLoading">Loading...</div>
    <div v-else>
      <div v-for="recipe in recipes" :key="recipe.recipeId">
        <RecipeCard :recipe="recipe"/>
      </div>
    </div>
  </div>
  
</template>

<script>
import RecipeCard from '../components/RecipeCard.vue';
import RecipeService from '../services/RecipeService';

export default {
  name: 'Results',
  components: {
    RecipeCard
  },
  props: ['keyword'],

  data() {
    return {
      recipes: [],
      isLoading: false,
      pageNum: 2,
      tagsList: [],
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
      RecipeService.searchByKeyword(this.keyword, this.pageNum)
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
