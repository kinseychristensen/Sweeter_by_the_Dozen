<template>
    <div>
<h1>Approve Recipes</h1>

<p>There are {{ pendingRecipes.length }} recipes to approve.</p>

<div v-if="pendingRecipes.length> 0">


  Title: {{ pendingRecipes[0].title }}<p></p>
  Description: {{ pendingRecipes[0].description }}<p></p>
  Attribution: {{ pendingRecipes[0].attribute }}<p></p>
  Recipe: {{ pendingRecipes[0].recipeText }}<p></p>
<p></p>
  Tags:
  <p v-for="tag in currentTags" v-bind:key="tag">{{ tag }}</p>
  <p></p>
  Pics:
  <p v-for="pic in currentPics" v-bind:key="pic">{{ pic }}</p>

</div>
<button @click="buildRecipe">Standardize Recipe and Approve</button>
<button @click="rejectRecipe">Reject Recipe</button>
<div v-if="showRecipeBuilder">
  <h1>Recipe Form</h1>
  <form v-on:submit.prevent="saveRecipe">

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
    
  <input type="num" id="numerator" v-model="ingredient.amountNumberator"/>
  /
  <input type="num" id="denominator" v-model="ingredient.amountDenominator"/>
  <input text id="unitType" v-model="ingredient.unitType"/>
    <input text id="ingredient" v-model="ingredient.ingredientText"/>
    <input text id="quantifier" v-model="ingredient.quantifier"/>
  </p>
  <button @click="addIngredient">Add Ingredient</button>
<p></p>
<p v-for="step in recipe.recipeStepList" v-bind:key="step.stepNum">
  <label for="step">Step: </label>
  <input text id="step" v-model="step.step"/>
</p>
<button @click="addStep">Add Step</button>



  <div>
  <a v-for="tag in tags" :key="tag.tagId">
    <input type="checkbox" :id="tag.tagId" v-model="tagsList" :value="tag.tag">
    <label :for="tag.tagId">{{ tag.tag }}</label>
  </a>
</div> 

{{ recipe }}











  </form>
</div>


<p></p>
-----------
    </div>
  </template>
  
  
  
  <script>
  import PendingRecipeService from '../services/PendingRecipeService';
  import RecipeService from '../services/RecipeService';
  
  export default {
    name: 'ApproveRecipe',
    components: {
     
  },
  props: ['userid'],

data() {
  return {
    pendingRecipes: [],
    isLoading: false,
    showRecipeBuilder: false,
    recipe: {
        title: '',
        description: '',
        userId: 0,
        attribute: '',
        recipeStepList: [{
          stepNum: 1,
          instructions: '',

          },],
          recipePicList: [{
            picUrl: '',
            altText: '',

          }],
          recipeTagList: [{
            tagId: 0,
            tag: '',

          }],
          ingredientList: [{
            amountNumberator: 0,
            amountDenominator: 0,
            ingredientNum: 1,
            quantifier: '',
            ingredientText: '',
            unitType: '',

          }],

      },

  }
},

computed: {

  currentTags(){
    let currentTags = [];

   currentTags = this.pendingRecipes[0].tags.split(",");

    return currentTags;
  },
  currentPics(){
    let currentPics = [];

   currentPics = this.pendingRecipes[0].pics.split(",");

    return currentPics;
  }



},
methods: {
  pullPendingRecipes(){
    
   PendingRecipeService.getPendingRecipes()
    .then((response) => {
      this.pendingRecipes = response.data;
      this.isLoading = false;
    })
  },

  buildRecipe(){
    this.showRecipeBuilder = true;
    this.recipe.attribute = this.pendingRecipes[0].attribute;
    this.recipe.description=this.pendingRecipes[0].description;
    this.recipe.userId=this.pendingRecipes[0].userId;
    this.recipe.title=this.pendingRecipes[0].title;
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
        amountNumberator: 0,
        amountDenominator: 0,
        ingredientNum: ingNum,            
        quantifier: '',
        ingredientText: '',
        unitType: '',}

    );
  },

}, 
  
created() {
  this.isLoading = true;
  this.pullPendingRecipes();
}










  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>