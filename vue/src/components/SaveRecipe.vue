<template>

    <div v-if="isLoading">is loading...</div>
    <div v-else>
        <div v-if="isSaved">
            <button  @click="unsaveRecipe"><img src="/assets/icons/perserved.png" alt="preserved" class="save-icon"></button>
        </div>
        <div v-else>
<button  @click="saveRecipe"><img src="/assets/icons/save_me.png" alt="save-me" class="save-icon"></button>

    </div> 

</div>
  </template>
  
  
  
  <script>
  import RecipeService from '../services/RecipeService';
  
  export default {
   
  props: ['recipe'],

  data() {
    return {
    didSave: false,
    savedRecipes: [],
    isSaved: false,
    isLoading: false,
    }

  },
  methods: {
    handleError(error, verb) {
      if (error.response) {
        this.$store.commit('SET_NOTIFICATION', `Error ${verb} save recipe. Response received was ${error.response.statusText}.`);
      } else if (error.request) {
        this.$store.commit('SET_NOTIFICATION', `Error ${verb} save recipe. Server could not be reached.`);
      } else {
        this.$store.commit('SET_NOTIFICATION', `Error ${verb} save recipe. Request could not be created.`);
      }
    },
    
    async isRecipeSaved() {
        try{
            this.isLoading = true;
     const response = await RecipeService.getSavedRecipes();
    this.savedRecipes = response.data;
          
    
        }catch(error) {
          this.handleError(error, 'fetching');
        }finally {
        
            this.savedRecipes.forEach((rec) => {
               
        if(rec.recipeId == this.recipe.recipeId){                
            this.isSaved = true;
            }
          })
            this.isLoading = false;
        }
    },
    saveRecipe() {
        this.isLoading = true;
        RecipeService.saveRecipe(this.recipe.recipeId)
        .then(response => {
          this.isSaved = response.data;
          this.isLoading = false;
        })
        .catch(error => {
          this.isLoading = false;
          this.handleError(error, 'fetching');
        }); 
    }, 
    unsaveRecipe() {
        this.isLoading = true;
        RecipeService.removeFromSaves(this.recipe.recipeId)
        .then(response => {
          this.isSaved = !response.data;
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
    this.isRecipeSaved();
  }

  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>