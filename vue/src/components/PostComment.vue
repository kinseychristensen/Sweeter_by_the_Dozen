<template>
  <div>
<form>
  <input text v-model="comment.comment"/>Enter your comment here.
</form>
<div v-if="showWarning">Opps!  Your comment is blank!  Please try again.</div>

Comments are meant to encourage and aid other recipe collectors, but not to spread negativity!  Please review our Code of Conduct before submitting.
<CodeConduct/>

<p></p>
<button @click="postComment">Post this Comment</button>
</div>
</template>



<script>

import CommentService from '../services/CommentService';
import CodeConduct from './CodeConduct.vue';


export default {
props: ['recipeId'],
name: 'ReportComment',
components: {
  CodeConduct
 
},
data(){
return {
  comment: {
    recipeId: 0,
    comment: '',
  },
  showWarning : false,
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
postComment() {
  this.comment.recipeId = this.recipeId;
  if(this.comment.comment == ''){
    this.showWarning = true;
  }else {
    this.showWarning = false;
CommentService.createComment(this.comment)
.then(response => {
  this.$router.go(0);

})
}}
}
}
</script>



<style scoped>


</style>