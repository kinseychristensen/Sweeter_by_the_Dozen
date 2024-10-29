<template>
   
      <div v-if="isLoading">Loading...</div>
    <div v-else>
search
<form v-on:submit.prevent="newSearch">
  <input text v-model="keyword"/>

  <a v-for="tag in tags" :key="tag.tagId">
    <input type="checkbox" :id="tag.tagId" v-model="tagsList" :value="tag.tagId">
    <label :for="tag.tagId">{{ tag.tag }}</label>
  </a>




<button @click="clearTags">Clear Tags</button>
  <button>search</button>
</form>


  


      <div v-for="recipe in recipes" :key="recipe.recipeId">
        <RecipeCard :recipe="recipe"/>
      </div>
<p v-if="recipes.length < 5">Sorry, there are no more recipes that meet that criteria.  Please try again.</p>
    </div>
    <button v-if="pageNum != 0" @click="prevPage">Previous </button>
   Page Number: {{ displayPageNum }} 
    <button v-if="recipes.length == 5" @click="nextPage"> Next</button>
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
      displayPageNum: 1,
      tagsList: [],
      tags: [],
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
    nextPage() {
      this.pageNum += 1;
      this.displayPageNum += 1;
      this.getRecipesBySearch();
    },
    prevPage() {
      this.pageNum -= 1;
      this.displayPageNum -= 1;
      this.getRecipesBySearch();
    },
    newSearch(){
      this.pageNum = 0;
      this.displayPageNum = 1;
      this.getRecipesBySearch();
    },
    clearTags(){
      this.tagsList = [];
    },
    getRecipesBySearch() {
      RecipeService.searchByKeywordAndTags(this.keyword, this.pageNum, this.tagsList)
        .then(response => {
          this.recipes = response.data;
          this.isLoading = false;
        })
        .catch(error => {
          this.isLoading = false;
          this.handleError(error, 'fetching');
        });
    },
    getTags(){
      RecipeService.getAllTags()
      .then(response => {
        this.tags = response.data;
        this.getRecipesBySearch();
      })
    }
  },
  created() {
    this.isLoading = true;
    this.getTags();
    
    
  }
}
</script>

<style scoped>
</style>
