<template>
    <div>
<h1>Submit a Recipe</h1>

<div v-if="$store.state.token != ''">
  <div v-if="user.restricted"> Sorry.  Your account is restricted.  You are not able to contribute recipes. </div>
<div v-else>

<form v-on:submit.prevent="submitRecipe" id="submit-recipe-form">
  <label for="recipe-title-form">Recipe Title:</label>
  <input text id="recipe-title-form" v-model="recipe.title"/>
  <label for="recipe-description-form">Recipe Description: </label>
  <input textarea id="recipe-description-form" v-model="recipe.description"/>
  <label for="recipe-attribute-form">Attribution: (If you know the original source or creator name, please share it!)</label>
  <input text id="recipe-attribute-form" v-model="recipe.attribute"/>
  <label for="recipe-instructions-form">Ingredients and Instructions:</label>
  <input text-area id="recipe-instructions-form" v-model="recipe.recipeText"/>

  <div>
  <a v-for="tag in tags" :key="tag.tagId">
    <input type="checkbox" :id="tag.tagId" v-model="tagsList" :value="tag.tag">
    <label :for="tag.tagId">{{ tag.tag }}</label>
  </a>
</div>

<div id="submit-recipe-buttons">
<button  type="button" @click="clearTags">Clear Tags</button>


<button v-on:click="upload">{{ uploadImageMsg }}</button></div>

    <div v-if="addImage">
      <!-- Show image upload section -->
      <p>Upload Image Section</p>
      <button v-on:click="upload">Open Upload Widget</button>
      
    </div>

    <!-- Image preview or additional UI can be added here -->
    <div v-if="showPreview">
      <p>Image Uploaded: </p>
      <a v-for="pic, index in pictures" v-bind:key="index">
        <img :src="pic.picture"/> 
        </a>
      
    </div>




  <button>Submit Recipe for Review</button>

</form>
</div>
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
import AuthService from '../services/AuthService';
  
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
      currentPicture: {},
      pictures: [],
      tags: [],
      tagsList: [],
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
      user: {},
      addImage: false,  // Controls whether the image upload form is shown
      showPreview: false,
      uploadImageMsg: 'Upload Image',
    };
  },
  methods: {
    upload() {

      this.myWidget.open();  // Open the Cloudinary upload widget
      this.addImage = false;
      this.uploadImageMsg = "Upload Another Image";
      this.showPreview = true;  // Hide upload form after opening widget
    },
    cancelUpload() {
      this.addImage = false;  // Hide the upload form if the user cancels
    },
 
  getTags(){
      RecipeService.getAllTags()
      .then(response => {
        this.tags = response.data;
        this.getUser();
      })
    },

    getUser(){
 AuthService.getUser()
 .then((response) => {
  this.user = response.data;
  this.isLoading = false;
 })
},


    clearTags(){
      this.tagsList = [];
    },


    submitRecipe(){
      const shouldSubmit = confirm("Are you ready to submit this recipe?  An admin will review and format your recipe soon!");
      if(shouldSubmit){
      this.recipe.tags = this.tagsList.toString();
      this.pictures.forEach((pic) => {
        if(pic.picture != ''){
          this.recipe.pics = this.recipe.pics + pic.picture + ',';
        }
      })
      

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
        }
      },


    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems saving this recipe.';
    }
  },
  created() {
    this.isLoading = true;
    this.getTags();
    
    
  },
  mounted() {
    // Create Cloudinary widget
    this.myWidget = window.cloudinary.createUploadWidget(
      {
        cloudName: 'davmzu0ic', 
        uploadPreset: 'SweeterDozenRecipeImage',
        multiple: false,
      },
      (error, result) => {
        if (error) {
          console.error("Upload failed: ", error);
          this.handleError(error, "uploading");
        }
        if (result && result.event === "success") {

          let picUrl = result.info.url;
          this.currentPicture = {picture: picUrl};
          this.pictures.push(this.currentPicture);
          this.$store.commit('SET_NOTIFICATION', 'Image uploaded successfully!');
        }
      }
    );
  }

  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>