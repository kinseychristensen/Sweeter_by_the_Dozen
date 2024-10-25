<template>
    <div class="home-container">
photo display

{{ photos }}

<SubmitPhoto/>
    </div>
  </template>
  
  
  
  <script>
  import SubmitPhoto from './SubmitPhoto.vue';

  
  export default {
    name: 'PhotoDisplay',
    components: {
     SubmitPhoto
  }, 
  props: ['photos'],
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>