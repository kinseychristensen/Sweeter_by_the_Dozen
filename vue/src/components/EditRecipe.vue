<template>
<h1>Edit Existing Recipe</h1>
<label for id="recipeId">Enter the recipe id (number at end of url): </label>
<input number id="recipeId" v-model="recipeId"/>
<button @click="getRecipe">Find Recipe</button>

<div v-if="isLoading">Loading...</div>
<div v-else>
  
  <div v-if="showErrorMsg">There was not a recipe found for that id.  Please try again.</div>
  <div v-if="recipeUpdated">The recipe was successfully updated.</div>
  <div v-if="showDeletedMessage">This recipe was successfully deleted.</div>
  
  
  <div v-if="showRecipeBuilder">

<h1>Recipe Form</h1>
  <form v-on:submit.prevent="submitRecipe">

    <label for="recipe-title-form">Recipe Title:</label>
  <input text id="recipe-title-form" v-model="recipe.title"/>
  <label for="recipe-description-form">Recipe Description: </label>
  <input textarea id="recipe-description-form" v-model="recipe.description"/>
  <label for="recipe-attribute-form">Attribution:</label>
  <input text id="recipe-attribute-form" v-model="recipe.attribute"/>
<p></p>
<p>Please list all ingredient amounts as a simple fraction.  So for 2 cups write 2 /1.  For 1/4 cup write 1/ 4.  And For 2 3/4 write 11/4.</p>

<p> amount  unit  ingredient   special instructions</p>
  <p v-for="ingredient in recipe.ingredientList" v-bind:key="ingredient.ingredientNum">
    
  <input type="num" id="numerator" v-model="ingredient.amountNumerator"/>
  /
  <input type="num" id="denominator" v-model="ingredient.amountDenominator"/>
  
  <input type="text" list="units" v-model="ingredient.unitType"/>
    <datalist id="units">
      <option>cups</option>
      <option>teaspoons</option>
      <option>tablespoons</option>
      <option>ounces</option>
      <option>pounds</option>
      <option>grams</option>
      <option>mililiters</option>
      <option>liters</option>
      <option>pints</option>
      <option>cans</option>
      <option>gallons</option>
      <option>quarts</option>
      <option>package</option>
    </datalist>





    <input text id="ingredient" v-model="ingredient.ingredientText"/>
    <input text id="quantifier" v-model="ingredient.quantifier"/>
  </p>
  <button type="button" @click="addIngredient">Add Ingredient</button>
<p></p>
<p v-for="step in recipe.recipeStepList" v-bind:key="step.stepNum">
  <label for="step">Step: </label>
  <input text id="step" v-model="step.instructions"/>
</p>
<button  type="button"  @click="addStep">Add Step</button>



  <div>
  <a v-for="tag in tags" :key="tag.tagId">
    <input type="checkbox" :id="tag.tagId" v-model="recipe.recipeTagList" :value="tag">
    <label :for="tag.tagId">{{ tag.tag }}</label>
  </a>
</div>
<p></p>

PHOTOS

<div v-for="pic in recipe.recipePicList" v-bind:key="pic.picUrl">
 ADD CODE TO DISPLAY PIC:  {{ pic }}
 <p></p>
 <label for="altText">Provide alternate text to describe this photo for the visually impared:</label>
 <input text id="altText" v-model="pic.altText"/>
 <button  type="button"  @click="deleteImage(pic.picUrl)">Remove this Image</button>

  </div>


<button>Submit Recipe</button>
  </form>

  <button @click="deleteRecipe">Delete This Recipe</button>
</div>
</div>

<p></p>

  </template>
  
  
  
  <script>
  
  import RecipeService from '../services/RecipeService';
  
  export default {
    name: 'EditRecipe',
    components: {
     
  },

  data(){
    return {
      recipeId: 0,
      isLoading: false,
      originalRecipe: {},
      showRecipeBuilder: false,
      showErrorMsg: false,
      recipeUpdated: false,
      showDeletedMessage: false,
      tags: [],
      recipe: {
        title: '',
        description: '',
        userId: 0,
        attribute: '',
        recipeStepList: [{
          stepNum: 1,
          instructions: '',

          },],
          recipePicList: [],
          recipeTagList: [],
          ingredientList: [{
            amountNumerator: 0,
            amountDenominator: 1,
            ingredientNum: 1,
            quantifier: '',
            ingredientText: '',
            unitType: '',

          }],

      },
    }
  },

  methods: {
  getRecipe(){
    this.isLoading = true;
    this.recipeUpdated = false;
    this.showErrorMsg = false;
    this.showDeletedMessage = false;
    RecipeService.getRecipeDetails(this.recipeId)
    .then((response) => {
      this.originalRecipe = response.data;
      if(this.originalRecipe.recipeId == 0){
        this.showErrorMsg = true;
        this.showRecipeBuilder = false;
        this.isLoading = false;
      }else {
      this.recipe = this.originalRecipe;
      this.isLoading = false;
      this.showRecipeBuilder = true;
  }})
  },

  addStep(){
    let stepNum = this.recipe.recipeStepList.length + 1;
    this.recipe.recipeStepList.push(
      { stepNum: stepNum,
        instructions: '',}
    );
  },
  addIngredient(){
    let ingNum = this.recipe.ingredientList.length + 1;
    this.recipe.ingredientList.push({
        amountNumerator: 0,
        amountDenominator: 1,
        ingredientNum: ingNum,            
        quantifier: '',
        ingredientText: '',
        unitType: '',}

    );
  },

  deleteImage(picUrl){
  this.recipe.recipePicList = this.recipe.recipePicList.filter(pic => pic.picUrl != picUrl);

  },
  getTags(){
      RecipeService.getAllTags()
      .then(response => {
        this.tags = response.data;
       this.isLoading = false;
      })
    },

    submitRecipe(){
      this.isLoading = true;
      RecipeService.updateRecipe(this.recipe.recipeId, this.recipe)
      .then((response) =>{
      this.showRecipeBuilder = false,
      this.recipeUpdated = true;
      this.isLoading = false;
    })
    },

    deleteRecipe(){
      const shouldDelete = confirm("Are you sure you want to delete this recipe?");
      if(shouldDelete){
      RecipeService.deleteRecipe(this.recipe.recipeId)
      .then((response) => {
        this.showDeletedMessage = true;
        this.showRecipeBuilder = false;
      })
    }}


  },
  created() {
  this.isLoading = true;
  this.getTags();
}
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>