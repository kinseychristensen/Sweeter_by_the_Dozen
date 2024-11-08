<template>
    <div>
<h1>Review Flagged Comments</h1>

<p>As an admin team, we are committed to keeping Sweeter by the Dozen as a safe and friendly place.  These comments were 
  flagged by our users as potentially violating our code of ethics. 
</p>

<CodeConduct/>


<p>Please make a fair determination if each comment should be deleted or if it is an acceptable comment and the flag
  should be removed.
</p>

<div v-for="comment in flaggedComments" v-bind:key="comment.commentId">
{{comment.comment}} by {{ comment.writer }}  for this <router-link v-bind:to="{name: 'recipe', params: {recipeId: comment.recipeId}}">recipe</router-link>.

<button @click="deleteComment(comment.commentId)">This comment should be removed.</button>
<button @click="approveComment(comment.commentId)">This comment is acceptable.</button>


<div v-if="showReasons">
  Please select all reasons this comment will be removed: 

  <label v-for="reason in reasonsToRemove" :key="reason.reasonNum"><p></p>
  <input type="checkbox" :value="reason.reason" v-model="reasonsForRemoval">{{ reason.reason }}</label>
 
</div>
</div>

{{ reasonsForRemoval }}

    </div>
  </template>
  
  
  
  <script>

  import CommentService from '../services/CommentService';
  import CodeConduct from '../components/CodeConduct.vue'
import RecipeByIdView from '../views/RecipeByIdView.vue';
  
  
  export default {
    name: 'ReviewComment',
    components: {
      CodeConduct
     
  },
  props: ['userid'],

  data() {
    return {
      isLoading: false,
      flaggedComments: [],
      reasonsForRemoval: [],
      showReasons: true,
      reasonsToRemove: [
        {reason: 'Profanity', reasonNum: 1,},
        {reason: 'Sexually provocative content', reasonNum: 2,},
        {reason: 'Violence against self, others, or animals', reasonNum: 3,},
        {reason: 'Bigotry or hate speech against ethnicities, nationalities, religions, identities, etc.', reasonNum: 4,},
        {reason: 'Bullying and intolerance', reasonNum: 5,},
        {reason: 'Spam, promotion of outside content, or misinformation', reasonNum: 6,},
        {reason: 'Langauge that encourages disordered eating or critiques personal food choices', reasonNum: 7,},
      ],
      

    }
  },

  methods: {
    getFlaggedComments(){
      CommentService.getReportedComments()
      .then((response) => {
        this.flaggedComments = response.data;
        this.isLoading = false;
      })


    },

  },
  created() {
    this.isLoading = false;
    this.getFlaggedComments();
  }
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>