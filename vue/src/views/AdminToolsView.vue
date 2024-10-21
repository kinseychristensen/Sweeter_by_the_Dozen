<template>
    <div>
Admin tools

<ConfirmationCode />
<ApproveImage />
<ApproveRecipe />
<ReviewComments />
<EditUsers />
    </div>
  </template>
  
  
  
  <script>
  
  import ApproveImage from '../components/ApproveImage.vue';
  import ApproveRecipe from '../components/ApproveRecipe.vue';
  import ReviewComments from '../components/ReviewComments.vue';
  import EditUsers from '../components/EditUsers.vue';
  import ConfirmationCode from '../components/ConfirmationCode.vue';

  
  export default {
    name: 'AdminToolsView',
    components: {
     ApproveImage,
     ApproveRecipe,
     ReviewComments,
     EditUsers,
     ConfirmationCode
  }
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>