<template>
    <div v-if="isLoading">Loading...</div>
      <div v-else>
        <div v-if="!showReport"> 
            <button @click="prepareReport">Report this Photo</button>
        </div>
        <div v-else>
            <button @click="showReport = false">Cancel</button>
  <h1>Report Photo</h1>
  
  <p>As an admin team, we are committed to keeping Sweeter by the Dozen as a safe and friendly place.  We should take care to ensure that we are not allowing
    content that is in violation of our code of conduct.
  </p>
  
  <CodeConduct/>
  
  
  <p>
    Please select all reasons this photo violated our code of conduct: </p> 
  
    <label v-for="reason in reasonsToRemove" :key="reason.reasonNum"><p></p>
    <input type="checkbox" :value="reason.reason" v-model="reasonsForRemoval">{{ reason.reason }}</label>
  <p></p>
    The user will recieve an email regarding this violation and if they have had two previous violations, their account will be restricted.
   <p></p>
   <button @click="removeComment">Issue Warning and Delete Photo</button>
  
  <p></p>

  </div>

  
</div>
    </template>
    
    
    
    <script>
  

    import PendingRecipeService from '../services/PendingRecipeService';
    import CodeConduct from '../components/CodeConduct.vue'
  import AuthService from '../services/AuthService';
  import emailjs from 'emailjs-com';
    
    
    export default {
      name: 'ReportPhoto',
      components: {
        CodeConduct
       
    },
    props: ['pic'],
  
    data() {
      return {
        isLoading: false,
        showReport: false,
        user: {},
        violations: [
        ],
        reasonsForRemoval: [],
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
    
  
      getUser(){
        AuthService.getUserById(this.pic.userId)
        .then((response) => {
          this.user = response.data;
          this.isLoading = false;
        })      
      },
  
   
    async deletePendingPhoto() {
      console.log('got to delete pending Photo');
      try {
        await PendingRecipeService.deletePendingPics([this.pic]);
        console.log('photo deleted successfully');
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
        violations: this.user.violations,
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

    prepareReport(){
        this.isLoading = false;
        this.showReport = true;
        this.getUser();
    },

  
    async removeComment() {
      const shouldRemove = confirm("Are you sure you want to mark this photo as violating the terms of use?");
      if(shouldRemove){
      this.isLoading = true;
  
      try {
        // Step 1: Delete comment
        await this.deletePendingPhoto();
  
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
}
}
  
  
    
    </script>
    
    
    
    <style scoped>
    
    
    </style>