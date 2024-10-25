<template>
  <div>
<form>
  <input text v-model="comment.comment"/>Enter your comment here.
</form>
<div v-if="showWarning">Opps!  Your comment is blank!  Please try again.</div>

Comments are meant to encourage and aid other recipe collectors, but not to spread negativity!  Please make sure your comment does not include any of the following:
<ul>
<li>Profanity</li>
<li>Violence against self, others, or animals</li>
<li>Bigotry or hate speech against ethnicities, nationalities, religions, identities, etc.</li>
<li>Bullying and intolerance</li>
<li>Sexually provocative content</li>
<li>Langauge that encourages disordered eating or critiques others' food choices</li>
</ul>

If a comment you post is found to be against the site's code of ethics, the comment will be permenantly deleted and you will recieve a warning and/or restriced use of the site.
<p></p>
<button @click="postComment">Post this Comment</button>
</div>
</template>



<script>

import CommentService from '../services/CommentService';


export default {
props: ['recipeId'],
name: 'ReportComment',
components: {
 
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