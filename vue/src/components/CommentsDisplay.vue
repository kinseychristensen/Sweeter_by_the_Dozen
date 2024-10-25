<template>
    <div>



<div v-for="comment in comments" :key="comment.commentId">
<div v-if="comment.reported">this comment has been flagged for review.</div>  {{ comment.comment }}
  {{ comment.writer }}
{{ comment.reported }}
<p></p>
<button @click="toggleReport">Report</button>
<div v-if="showReport">
  <ReportComment :commentId="comment.commentId"/>
<button @click="toggleReport">Cancel</button></div>
</div>
<p></p>

<button @click="togglePost">Post a Comment</button>
<div v-if="showReport">
  Comments are meant to encourage and aid other recipe collectors, but not to spread negativity!  Do you want to report this comment for any of the following reasons:
<ul>
  <li>Profanity</li>
  <li>Violence against self, others, or animals</li>
  <li>Bigotry or hate speech against ethnicities, nationalities, religions, identities, etc.</li>
  <li>Bullying and intolerance</li>
  <li>Sexually provocative content</li>
  <li>Langauge that encourages disordered eating or critiques others' food choices</li>
</ul>

Once a comment is reported, an admin will review the comment and if the comment is found to be against the site's code of ethics, the comment will be permenantly deleted and the original poster will recieve a warning and/or restriced use of the site.
<p></p>
</div>




<div v-if="showPost">
  <PostComment :recipeId="recipeId"/>
  <button @click="togglePost">Cancel</button>
</div>

    </div>
  </template>
  
  
  
  <script>
  
  import ReportComment from './ReportComment.vue';
  import PostComment from './PostComment.vue';
  import CommentService from '../services/CommentService';


  
  export default {
    props: ['recipeId'],
    name: 'CommentsDisplay',
    components: {
     ReportComment,
     PostComment
  },
  data(){
    return {
      comments: [],
      showReport: false,
      showPost: false,
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
}

  },
created() {

this.isLoading = true;
this.getComments(this.recipeId);


}
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>