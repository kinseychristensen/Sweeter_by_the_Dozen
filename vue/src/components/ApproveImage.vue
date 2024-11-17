<template>
    <div>
<h1>Approve Pending Photos</h1>
<p>Review the photos below.  Add an alternate text caption to approved photos.  Make sure you hit 'save changes' when complete! </p>

<div v-if="pendingPics.length == 0">There are no photos to review.</div>
<div v-else>
<div v-for="pic, index in pendingPics" v-bind:key="index">
  <a v-for="recipe in recipes" v-bind:key="recipe.recipeId">
<RecipeCard v-if="recipe.recipeId === pic.recipeId" :recipe="recipe"/></a>
<img :src="pic.picUrl"/>
<a href="pic.picUrl">Original Image</a>
<label for="picAltText">Alternate text for image:</label>
<input text id="picAltText"/>
<button @click="pic.futureStatus = 'approved'">Mark Approved</button>
<button @click="pic.futureStatus = 'deleted'">Mark for Deletion</button>


<ReportPhoto :pic="pic"/>

</div>

<button @click="saveChanges">Save Changes</button>
    </div> </div>
  </template>
  
  
  
  <script>
  import PendingRecipeService from '../services/PendingRecipeService';
  import RecipeCard from '../components/RecipeCard.vue';
  import RecipeService from '../services/RecipeService';
  import ReportPhoto from './ReportPhoto.vue';
  
  export default {
    name: 'ApproveImage',
    components: {
      RecipeCard,
      ReportPhoto,
     
  },
 

  data () {
    return  {
      pendingPics: {
        recipeId: 0,
        picUrl: '',
        altText: '',
        userId: 1,
      },
      approvedPics: [],
      deletingPics: [],
      recipes: [],


    }

  },
  
  methods: {

    saveChanges() {
      this.pendingPics.forEach((pic) => {
        if(pic.futureStatus == 'approved'){
            this.approvedPics.push(pic);
        }else if (pic.futureStatus == 'deleted') {
          this.deletingPics.push(pic);
        }
      });

    PendingRecipeService.approvePendingPics(this.approvedPics)
    .then((response) => {
   
    PendingRecipeService.deletePendingPics(this.deletingPics)
    .then((response) => {
      this.getPics();
    })
  })

    },



    async getPics() {
      try {
        await this.getPendingPics();

        this.getRecipes(this.pendingPics);

      
      } catch (error) {
        this.handleError(error, 'removeComment');
      } finally {
        this.isLoading = false;
    }
  },

    async getPendingPics() {
      try {
        const response = await PendingRecipeService.getPendingPics();
        let pendingPics = response.data;
        let completedPics = [];
        pendingPics.forEach((pic) => {
          let newPic = {
          recipeId: pic.recipeId,
          picUrl: pic.picUrl,
          altText: '',
          userId: pic.userId,
          futureStatus: '',
        };
        completedPics.push(newPic);
        });
      this.pendingPics = completedPics;

      } catch (error) {
    console.error('Error fetching pendingPics:', error);
    this.handleError(error, 'getPendingPics');
  }
    
    },


    getRecipes(pendingPics){
      let recipeList = [];
    
      pendingPics.forEach((pic) => {
        recipeList.push(parseInt(pic.recipeId));
      });
      console.log(recipeList);

      let setList = new Set(recipeList);
      recipeList = [...setList];
      console.log(recipeList);

      RecipeService.getRecipesFromList(recipeList)
      .then((response) => {
        this.recipes = response.data;
        console.log('found these recipes' + this.recipes);
      })
      .catch((error) => {
      console.error('Error fetching recipes:', error);
    })
    .finally(() => {

   
    });

  },
},
  created() {
    this.isLoading = true;

    this.getPics();

  }





  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>