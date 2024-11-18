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

</div>
<button @click="buildRecipe" v-if="pendingRecipes.length>0">Standardize Recipe and Approve</button>
<button @click="rejectRecipe" v-if="pendingRecipes.length>0">Reject Recipe</button>
<button @click="newBlankRecipe">Create New Recipe</button>
<button @click="showNewTags = true">Create New Recipe Tags</button>



<div v-if="showNewTags">
  <h1>Add a Tag</h1>
  <h3>Existing Tags:</h3>
  <a v-for="tag in tags" :key="tag.tagId">{{ tag.tag }} </a>

  New Tags: 
  <a v-for="tag, index in newTags" :key="index">{{ tag }}</a>

  <label for="newTagField">New Tag:</label>
  <input type="text" v-model="newestTag"/>
  <button @click="addTag">Add Tag</button>
  <button @click="saveTags">Save Changes</button>
  <button @click="cancelTags">Cancel</button>

</div>



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
  <button @click="addIngredient">Add Ingredient</button>
<p></p>
<p v-for="step in recipe.recipeStepList" v-bind:key="step.stepNum">
  <label for="step">Step: </label>
  <input text id="step" v-model="step.instructions"/>
</p>
<button @click="addStep">Add Step</button>



  <div>
  <a v-for="tag in tags" :key="tag.tagId">
    <input type="checkbox" :id="tag.tagId" v-model="recipe.recipeTagList" :value="tag">
    <label :for="tag.tagId">{{ tag.tag }}</label>
  </a>
</div>
<p></p>

PHOTOS

<div v-for="pic in recipe.recipePicList" v-bind:key="pic.picUrl">
<img :src="pic.picUrl">
 <p></p>
 <label for="altText">Provide alternate text to describe this photo for the visually impared:</label>
 <input text id="altText" v-model="pic.altText"/>
 <button @click="deleteImage(pic.picUrl)">Remove this Image</button>

  </div>


<button @click="verifyRecipe">Submit Recipe</button>
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
    isNew: false,
    showNewTags: false,
    newTags: [],
    newestTag: '',
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

computed: {

  currentTags(){
    let currentTags = [];

   currentTags = this.pendingRecipes[0].tags.split(",");

    return currentTags;
  },
  currentPics(){
    let currentPics = [];
    let rawUrls = this.pendingRecipes[0].pics;

   currentPics = rawUrls.split(",");
   if(rawUrls == ""){
    return []
   }

    return currentPics;
  }



},
methods: {
  saveTags(){
    this.isLoading = true;
    if(this.newestTag != ''){
      this.newTags.push(
      { tag: this.newestTag}
    );
    };
    RecipeService.createTags(this.newTags)
      .then((response) => {
        this.getTags();
        this.showNewTags = false;
        this.newTags = [];
        this.newestTag = '';
      })
  },

  cancelTags(){
    this.newTags = [];
    this.newestTag = '';
    this.showNewTags = false;
  },


  pullPendingRecipes(){
    
   PendingRecipeService.getPendingRecipes()
    .then((response) => {
      this.pendingRecipes = response.data;
      this.showRecipeBuilder = false;
      this.isLoading = false;
    })
  },
  rejectRecipe(){
    PendingRecipeService.deletePendingRecipe(this.pendingRecipes[0].id)
    .then((response) => {
      if(response.data){
        this.pullPendingRecipes();
      }
    })
  },

  getTags(){
      RecipeService.getAllTags()
      .then(response => {
        this.tags = response.data;
       this.pullPendingRecipes();
      })
    },

    newBlankRecipe(){
      this.showRecipeBuilder = true;
      this.recipe.userId = 1;
      this.isNew = true;
    },


  buildRecipe(){
    this.showRecipeBuilder = true;
    this.recipe.attribute = this.pendingRecipes[0].attribute;
    this.recipe.description=this.pendingRecipes[0].description;
    this.recipe.userId=this.pendingRecipes[0].userId;
    this.recipe.title=this.pendingRecipes[0].title;
    if(this.currentPics != ""){
    
      this.currentPics.forEach((pic) => {
        console.log(pic);
        let thisPic = {
          picUrl: pic,
          altText: '',
          userId: this.recipe.userId,
        };
        this.recipe.recipePicList.push(thisPic);
      })
    }
  },


  addStep(){
    let stepNum = this.recipe.recipeStepList.length + 1;
    this.recipe.recipeStepList.push(
      { stepNum: stepNum,
        instructions: '',}
    );
  },

  addTag(){
    this.newTags.push(
      { tag: this.newestTag}
    );
    this.newestTag = '';
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

  verifyRecipe(isNew){
    this.isLoading = true;
RecipeService.createRecipe(this.recipe)
.then((response) => {
  if(response.status === 200){
    if(!this.isNew){
    PendingRecipeService.deletePendingRecipe(this.pendingRecipes[0].id)
    .then((response) => {
      if(response.data){
        this.isNew = false;
        this.pullPendingRecipes();
      }
    })
    }else {
      this.pullPendingRecipes();
      this.isNew = false;

    }
  }

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