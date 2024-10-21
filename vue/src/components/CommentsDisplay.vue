<template>
    <div>
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