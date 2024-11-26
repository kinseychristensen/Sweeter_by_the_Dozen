<template>
    <div>



<button @click="reportComment" class="icon-button"><img src="/public/assets/icons/gray_flag.png" class="report-icon"/></button>
</div>
  </template>
  
  
  
  <script>

  import CommentService from '../services/CommentService';

  
export default {
  props: ['commentId'],
  name: 'ReportComment',
  components: {
   
},
data(){
  return {
    
  }
},

methods: {

handleError(error, verb) {
if (error.response) {
  this.$store.commit('SET_NOTIFICATION', `Error ${verb} deck. Response received was ${error.response.statusText}.`);
} else if (error.request) {
  this.$store.commit('SET_NOTIFICATION', `Error ${verb} deck. Server could not be reached.`);
} else {
  this.$store.commit('SET_NOTIFICATION', `Error ${verb} deck. Request could not be created.`);
}
},
reportComment() {
const shouldReport = confirm('Are you sure you want to report this comment for review?  This cannot be undone.');
if(shouldReport){
CommentService.reportComment(this.commentId)
.then(response => {
  
  this.isLoading = false;
})
}}
}
}
  </script>
  
  
  
  <style scoped>
  
  
  </style>