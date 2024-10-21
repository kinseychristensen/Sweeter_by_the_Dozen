<template>
    <div class="home-container">
comments display
<ReportComment/>
<PostComment/>
    </div>
  </template>
  
  
  
  <script>
  
  import ReportComment from './ReportComment.vue';
  import PostComment from './PostComment.vue';

  
  export default {
    name: 'CommentsDisplay',
    components: {
     ReportComment,
     PostComment
  }
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>