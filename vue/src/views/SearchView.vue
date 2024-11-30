<template>
   
      <div v-if="isLoading">Loading...</div>
    <div v-else id="search-flex">
<h1 id="search-title">Search</h1>
<form v-on:submit.prevent="newSearch" id="search-form">
  
  
<div class="tags-flex">
  <a v-for="tag in tags" :key="tag.tagId">
    <input type="checkbox" :id="tag.tagId" v-model="tagsList" :value="tag.tagId">
    <label :for="tag.tagId">{{ tag.tag }}</label>
  </a></div>



  <div id="search-bar-flex">
  <input text v-model="keyword" id="search-bar"/>

  <button id="search-button">Search</button>
  <button @click="clearTags" class="tag-button">Clear Tags</button></div>
</form>


  

<div id="search-results">
      <div v-for="recipe in recipes" :key="recipe.recipeId">
        <RecipeCard :recipe="recipe"/>
      </div>
<p v-if="recipes.length < 24">Sorry, there are no more recipes that meet that criteria.  Please try again.</p>
    </div>
  <div>
  <div id="page-buttons">
    <button v-if="pageNum != 0" @click="prevPage" id="prev-button">Previous </button>
   <a class="tab-text" id="page-num">Page Number: {{ displayPageNum }} </a>
    <button v-if="recipes.length >= 24" @click="nextPage" id="next-button"> Next</button>
    </div></div></div>
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
