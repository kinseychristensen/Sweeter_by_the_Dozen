<template>
    <div class="home-container">
grow page
<Suggestion />
<AdminApply />

    </div>
  </template>
  
  
  
  <script>
  import Suggestion from '../components/Suggestion.vue';
  import AdminApply from '../components/AdminApply.vue';

  
  export default {
    name: 'GrowView',
    components: {
     Suggestion,
     AdminApply
  }
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>