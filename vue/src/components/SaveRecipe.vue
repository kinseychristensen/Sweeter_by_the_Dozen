<template>

    <div v-if="isLoading">is loading...</div>
    <div v-else>
        <div v-if="isSaved">
            <button  class="save-button" @click="unsaveRecipe"><img src="/assets/icons/red_heart.png" alt="preserved" class="save-icon"></button>
        </div>
        <div v-else>
<button class="save-button" @click="saveRecipe"><img src="/assets/icons/gray_heart.png" alt="save-me" class="save-icon"></button>

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
  .save-button {
  all: unset; /* Reset button styles */
  cursor: pointer;
}

.save-button img:hover {
  animation: shake 1s infinite;
}

@keyframes shake {
  0% { transform: translate(1px, 1px) rotate(0deg); }
  10% { transform: translate(-1px, -2px) rotate(-1deg); }
  20% { transform: translate(-3px, 0px) rotate(1deg); }
  30% { transform: translate(3px, 2px) rotate(0deg); }
  40% { transform: translate(1px, -1px) rotate(1deg); }
  50% { transform: translate(-1px, 2px) rotate(-1deg); }
  60% { transform: translate(-3px, 1px) rotate(0deg); }
  70% { transform: translate(3px, 1px) rotate(-1deg); }
  80% { transform: translate(-1px, -1px) rotate(1deg); }
  90% { transform: translate(1px, 2px) rotate(0deg); }
  100% { transform: translate(1px, -2px) rotate(-1deg); }
}
  
  </style>