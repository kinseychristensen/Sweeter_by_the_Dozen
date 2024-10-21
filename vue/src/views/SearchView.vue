<template>
    <div>
search

<Tags />
<Results/>
    </div>
  </template>
  
  
  
  <script>
  import Results from '../components/Results.vue';
  import Tags from '../components/Tags.vue';

  
  export default {
    name: 'SearchView',
    components: {
     Results, 
     Tags
  }
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>