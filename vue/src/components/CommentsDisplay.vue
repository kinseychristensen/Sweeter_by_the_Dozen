<template>
<div>

  <div v-if="comments.length == 0">There are no comments yet for this recipe.</div>
<div v-for="comment in comments" :key="comment.commentId">
<div v-if="comment.reported">this comment has been flagged for review.</div> 

<h3>{{ comment.comment }}</h3>

<Avatar :userId="comment.userId"/>
<p></p>
<button type="button" @click="toggleReport">Report</button>
<button type="button" @click="deleteComment(comment.commentId)" v-if="comment.userId == user.id">Delete My Comment</button>
<div v-if="showReport">
  <ReportComment :commentId="comment.commentId"/>
<button type="button" @click="toggleReport">Cancel</button></div>
</div>
<p></p>

<button type="button" @click="togglePost">Post a Comment</button>
<div v-if="showReport">
  Comments are meant to encourage and aid other recipe collectors, but not to spread negativity!  
<CodeConduct/>


Once a comment is reported, an admin will review the comment and if the comment is found to be against the site's code of ethics, the comment will be permenantly deleted and the original poster will recieve a warning and/or restriced use of the site.
<p></p>
</div>




<div v-if="showPost">
  <div v-if="$store.state.token != ''">
    <div v-if="user.restricted">Your account is restricted.  You are unable to post comments.</div>
    <div v-else>
  <PostComment :recipeId="recipeId"/>
  <button @click="togglePost">Cancel</button></div>
  </div><div v-else>You must be logged in to post a comment.</div>

</div>

    </div>
  </template>
  
  
  
  <script>
  
  import ReportComment from './ReportComment.vue';
  import PostComment from './PostComment.vue';
  import CommentService from '../services/CommentService';
  import Avatar from './Avatar.vue';
  import CodeConduct from './CodeConduct.vue';
  import AuthService from '../services/AuthService';


  
  export default {
    props: ['recipeId'],
    name: 'CommentsDisplay',
    components: {
     ReportComment,
     PostComment,
     Avatar,
     CodeConduct,
  },
  data(){
    return {
      comments: [],
      showReport: false,
      showPost: false,
      user: {},
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
getComments(recipeId) {
  CommentService.getCommentsByRecipeId(this.recipeId)
  .then(response => {
    this.comments = response.data;
    this.isLoading = false;
  })
},
togglePost(){
  this.showPost = !this.showPost;
  
},

toggleReport() {
  this.showReport = !this.showReport;
},

deleteComment(commentId){
  const shouldDelete = confirm("Are you sure you want to delete your comment?");

  if(shouldDelete){
CommentService.deleteComment(commentId)
.then((response) => {
  if(response.data){
    this.isLoading = false;
    this.$router.go(0);
  }
})}
},


getUser(){
 AuthService.getUser()
 .then((response) => {
  this.user = response.data;
 })
}
  },

created() {

this.isLoading = true;
this.getComments(this.recipeId);
this.getUser();


}
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>