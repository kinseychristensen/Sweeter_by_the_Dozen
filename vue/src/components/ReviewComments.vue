<template>
  <div v-if="isLoading">Loading...</div>
    <div v-else>
<h1>Review Flagged Comments</h1>

<p>As an admin team, we are committed to keeping Sweeter by the Dozen as a safe and friendly place.  These comments were 
  flagged by our users as potentially violating our code of ethics. 
</p>

<CodeConduct/>


<p>Please make a fair determination if each comment should be deleted or if it is an acceptable comment and the flag
  should be removed.
</p>

<div v-for="comment in violations" v-bind:key="comment.commentId">
{{comment.comment}} by {{ comment.writer }}  for this <router-link v-bind:to="{name: 'recipe', params: {recipeId: comment.recipeId}}">recipe</router-link>.

<button @click="reviewComment(comment.commentId, comment.userId)">This comment should be removed.</button>
<button @click="unflagComment(comment.commentId)">This comment is acceptable.</button>


<div v-if="comment.commentId == deletingCommentId">
  Please select all reasons this comment will be removed: 

  <label v-for="reason in reasonsToRemove" :key="reason.reasonNum"><p></p>
  <input type="checkbox" :value="reason.reason" v-model="reasonsForRemoval">{{ reason.reason }}</label>
<p></p>
  The user will recieve an email regarding this deletion and if they have had two previous strikes, their account will be restricted.
 <p></p>
 <button @click="removeComment">Issue Warning and Delete Comment</button>

<p></p>
{{ deletingCommentId }}
</div>
{{ violations }}
</div>
_______<p></p>

{{ user }}

    </div>
  </template>
  
  
  
  <script>

  import CommentService from '../services/CommentService';
  import CodeConduct from '../components/CodeConduct.vue'
import AuthService from '../services/AuthService';
import emailjs from 'emailjs-com';
  
  
  export default {
    name: 'ReviewComment',
    components: {
      CodeConduct
     
  },
  props: ['userid'],

  data() {
    return {
      isLoading: false,
      user: {},
      violations: [
      ],
      reasonsForRemoval: [],
     deletingCommentId: 0,
      reasonsToRemove: [
        {reason: 'Profanity', reasonNum: 1,},
        {reason: 'Sexually provocative content', reasonNum: 2,},
        {reason: 'Violence against self, others, or animals', reasonNum: 3,},
        {reason: 'Bigotry or hate speech against ethnicities, nationalities, religions, identities, etc.', reasonNum: 4,},
        {reason: 'Bullying and intolerance', reasonNum: 5,},
        {reason: 'Spam, promotion of outside content, or misinformation', reasonNum: 6,},
        {reason: 'Langauge that encourages disordered eating or critiques personal food choices', reasonNum: 7,},
      ],
     
      serviceId: 'service_jtr3pq8',
  templateId: 'template_md47hhs',
  publicKey: 'dQcG_YdFM_H1tqQP6',
      

    }
  },

  methods: {
    getviolations(){
      CommentService.getReportedComments()
      .then((response) => {
        this.violations = response.data;
        this.isLoading = false;
      })
    },

    unflagComment(commentId){
      const shouldApprove = confirm("Are you sure you want to mark this comment as acceptable?");
      if(shouldApprove){
      CommentService.unreportComment(commentId)
      .then((response) => {
        this.getviolations();
      })}
    },

    getUser(userId){
      AuthService.getUserById(userId)
      .then((response) => {
        this.user = response.data;
      })      
    },

    reviewComment(commentId, userId){
      this.deletingCommentId = commentId; //isolates comment
      this.getUser(userId);   //gets user details for comment writer
  
    },
 
  async deleteCommentById() {
    console.log('got to delete comment');
    try {
      await CommentService.deleteComment(this.deletingCommentId);
      console.log('Comment deleted successfully');
    } catch (error) {
      this.handleError(error, 'deleteCommentById');
    }
    console.log('finished delete comment');
  },

  async updateUserStatus() {
    console.log('got to update user');
    try {
      this.user.violations += 1;

      if (this.user.violations >= 3) {
        this.user.restricted = true; // Restrict user if they hit three strikes
      }
      let user = {
      id: this.user.id,
      username: this.user.username,
      restricted: this.user.restricted,
      displayName: this.user.displayName,
      violations: this.user.violations + 1,
      avatarId: this.avatarId,
    };
    console.log(user);

      await AuthService.updateUser(user);
      console.log('User status updated successfully');
    } catch (error) {
      this.handleError(error, 'updateUserStatus');
    }
    console.log('finished update user');
  },

  async sendWarningEmail() {
    console.log('got to send email');
    try {
      await emailjs.send(this.serviceId, this.templateId, {
        user_email: this.user.username,
        reasons: this.reasonsForRemoval,
        numViolations: this.user.violations,
      }, this.publicKey);
      console.log("Email successfully sent!");
    } catch (error) {
      console.log('Failed to send email:', error);
    }
  },

  async removeComment() {
    const shouldRemove = confirm("Are you sure you want to mark this comment as violating the terms of use?");
    if(shouldRemove){
    this.isLoading = true;

    try {
      // Step 1: Delete comment
      await this.deleteCommentById(this.deletingCommentId);

      // Step 2: Update user status
      await this.updateUserStatus();

      // Step 3: Send warning email
      await this.sendWarningEmail();

    } catch (error) {
      this.handleError(error, 'removeComment');
    } finally {
      console.log('end, loading set to false');
      this.user = {};
      this.deletingCommentId = 0;
      this.getviolations();
      this.isLoading = false; // Ensure isLoading is reset
    }
  }
}



  },
  created() {
    this.isLoading = false;
    this.getviolations();
  }
  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>