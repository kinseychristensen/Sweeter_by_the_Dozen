<template>
    <div>
<h1>Submit a Recipe</h1>

<div v-if="$store.state.token != ''">


<form v-on:submit.prevent="submitRecipe">
  <label for="recipe-title-form">Recipe Title:</label>
  <input text id="recipe-title-form" v-model="recipe.title"/>
  <label for="recipe-description-form">Recipe Description: </label>
  <input textarea id="recipe-description-form" v-model="recipe.description"/>
  <label for="recipe-attribute-form">Attribution:</label>
  <input text id="recipe-attribute-form" v-model="recipe.attribute"/>
  <label for="recipe-instructions-form">Ingredients and Instructions:</label>
  <input text-area id="recipe-instructions-form" v-model="recipe.recipeText"/>




  <div>
  <a v-for="tag in tags" :key="tag.tagId">
    <input type="checkbox" :id="tag.tagId" v-model="tagsList" :value="tag.tag">
    <label :for="tag.tagId">{{ tag.tag }}</label>
  </a>
</div>







  <button>Submit Recipe for Review</button>

  <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>


</form>
<button>Add a Photo</button>

<button @click="clearTags">Clear Tags</button>
<div v-if="submitted">Thank you for sharing your recipe!  An admin will format and upload your recipe to our database.  </div>
</div>
<div v-else>You must log in to submit a recipe!
<button><router-link v-bind:to="{ name: 'register' }">Register</router-link></button>
<button><router-link v-bind:to="{ name: 'login' }">Log In</router-link></button>
</div>
    </div>
   
  </template>
  
  
  
  <script>
  import RecipeService from '../services/RecipeService';
  import PendingRecipeService from '../services/PendingRecipeService';
import { RouterLink } from 'vue-router';
  
  export default {
    name: 'SubmitRecipe',
    components: {
     
  },
  data() {
    return {
      recipe: {
        title: '',
        description: '',
        attribute: '',
        pics: '',
        tags: '',
        recipeText: '',
      },
      submitted: false,
      pictures: [],
      tags: [],
      tagsList: [],
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    }
  },
  methods: {
  getTags(){
      RecipeService.getAllTags()
      .then(response => {
        this.tags = response.data;
        this.isLoading = false;
      })
    },

    clearTags(){
      this.tagsList = [];
    },


    submitRecipe(){
      this.recipe.tags = this.tagsList.toString();
      this.recipe.pics = this.pictures.toString();
        console.log(this.recipe);

        PendingRecipeService.createPendingRecipe(this.recipe)
        .then (response => {
          this.submitted = true;
      
        }
      )
      .catch((error) => {
        const response = error.response;
        this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Please try again.';
            }
          });
      },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems saving this recipe.';
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