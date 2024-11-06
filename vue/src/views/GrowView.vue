<template>
    <div>
<h1>Help Us Grow</h1>
<p>Thank you for your interest in contributing to our site!  Everything we do is to help share the love of food and cooking.</p>
<button @click="toggleSuggestions">{{ suggestionsMsg }}</button>
<button @click="toggleAdminApply">{{ adminMsg }}</button>
<button @click="toggleSubmitRecipe">{{submitRecipeMsg}}</button>
<button @click="toggleDonate">{{ donateMsg }}</button>

<Donate v-if="showDonate"/>
<SubmitRecipe v-if="showSubmitRecipe"/>
<AdminApply v-if="showAdmin"/>
<Suggestion v-if="showSuggestions"/>

    </div>
  </template>
  
  
  
  <script>
  import Suggestion from '../components/Suggestion.vue';
  import AdminApply from '../components/AdminApply.vue';
import { RouterLink } from 'vue-router';
import SubmitRecipe from '../components/SubmitRecipe.vue';
import Donate from '../components/Donate.vue';

  
  export default {
    name: 'GrowView',
    components: {
     Suggestion,
     AdminApply,
     SubmitRecipe,
     Donate,
  }, 
  data(){
    return {
      showSubmitRecipe: false,
      isLoading: false,
      submitRecipeMsg: 'Submit Recipe',
      showSuggestions: false,
      showAdmin: false,
      adminMsg: 'Apply to be an Admin',
      suggestionsMsg: 'Suggestions',
      donateMsg: 'Donate',
      showDonate: false,
    }
  },
  methods: {
  toggleSubmitRecipe(){
    if(this.showSubmitRecipe){
      this.setAllFalse();
    }else {
      this.setAllFalse();
      this.showSubmitRecipe = true;
      this.submitRecipeMsg = 'Cancel';
    }
  },
   
  toggleSuggestions(){
    if(this.showSuggestions){
      this.setAllFalse();
    }else {
      this.setAllFalse();
      this.showSuggestions = true;
      this.suggestionsMsg = 'Cancel';
    }
  },
  toggleDonate(){
    if(this.showDonate){
      this.setAllFalse();
    }else {
      this.setAllFalse();
      this.showDonate = true;
      this.donateMsg = 'Cancel';
    }
  },
  toggleAdminApply(){
    if(this.showAdmin){
      this.setAllFalse();
    }else {
      this.setAllFalse();
      this.showAdmin = true;
      this.adminMsg = 'Cancel';
    }
  },
  setAllFalse(){
    this.adminMsg = 'Apply to be an Admin';
    this.donateMsg = 'Donate to Site Costs';
    this.suggestionsMsg = 'Suggestions';
    this.submitRecipeMsg = 'Submit a Recipe';
      this.showSubmitRecipe = false;
      this.showAdmin = false;
      this.showSuggestions = false;
      this.showDonate = false;
  
  }
}
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>