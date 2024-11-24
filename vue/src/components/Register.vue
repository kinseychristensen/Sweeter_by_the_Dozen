<template>
  <div id="register" class="register-container">
   
 
     <h1 class="register-title">Create Account</h1>
     <div role="alert" v-if="registrationErrors">
       {{ registrationErrorMsg }}
       <button @click="showFirst = true; showThird=false;">Start Over</button>
     </div>
     <div class="form-input-group" v-if="showFirst">
       <label for="email" class="bold-label">Your Email: </label>
       <input type="email" id="email" v-model="user.username" required autofocus />
       <label for="password" class="bold-label">Password: </label>
       <input type="password" id="password" v-model="user.password" required />
       <label for="confirmPassword" class="bold-label">Confirm Password: </label>
       <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
       <p v-if="showErrorMsg">Opps!  We already have an account under that email.  Please continue to Log in.</p>
       <p v-if="this.user.password != this.user.confirmPassword">Passwords must match.</p>
       <button type="button" @click="checkEmail">Continue</button>
     </div>
    
    
     <div class="form-input-group" v-if="showSecond">
      Choose an Avatar Icon and Display Name:

      These may be changed later.

      <label for="displayName" class="bold-label">Your Displayed Username:</label>
      <input type="text" id="displayName" v-model="user.displayName" required />
 <p></p>
   <select v-model="user.avatarId">
     <option v-for="avatar in avatars" :key="avatar.avatarId" :value="avatar.avatarId">
       {{avatar.avatarId}}: {{ avatar.altText }}
     </option>
   </select>
   <p></p>
   <button type="button" @click="showSecond = false; showThird = true">Continue</button>
   <p></p>
     <a v-for="avatar in avatars" :key="avatar.avatarId">
     <img id="avatar" :src="avatar.avatarSrc" loading="lazy"/>{{ avatar.avatarId }}: {{ avatar.altText }}</a>
     </div>

     <div v-if="showThird">
      <CodeConduct />
      Do you agree to the terms of our Code of Conduct? 
      <button type="button" @click="register">Agree and Create Account</button>

     </div>


     
 </div>
 

</template>

<script>
import authService from '../services/AuthService';
import { avatars } from '../AvatarsArray.js';
import CodeConduct from '../components/CodeConduct.vue';



export default {
 components: {
   CodeConduct
 },
 data() {
   return {
    showFirst: true,
    showSecond: false,
    showThird: false,
    showErrorMsg: false,
     user: {
       username: '',
       displayName: '',
       password: '',
       confirmPassword: '',
       role: 'user',
       avatarId: 1,
      
     },
        
     registrationErrors: false,
     registrationErrorMsg: 'There were problems registering this user.',
     showAvatars: false,
     avatarBtnMsg: 'Choose Avatar',
     avatars,
   };
 },
 methods: {

checkEmail() {
  authService.getId(this.user.username)
  .then((response) => {
    console.log(response.data);
    if(response.data == 0){
      this.showFirst = false;
      this.showSecond = true;
    } else {
      this.showErrorMsg = true;
    }})
},


 refreshAndNavigate(targetRoute){ 
    const currentUrl = window.location.href.split('?')[0];
    const targetUrl = `${currentUrl}?redirect=${encodeURIComponent(targetRoute)}`;
    window.location.href = targetUrl;
},


   register() {
     if (this.user.password != this.user.confirmPassword) {
       this.registrationErrors = true;
       this.registrationErrorMsg = 'Password & Confirm Password do not match.';
     } else {
       const shouldCreate = confirm("Do you agree to abide by the Code of Conduct on this site?");
       if(shouldCreate){
       authService
         .register(this.user)
         .then((response) => {
           if (response.status == 201) {
            this.refreshAndNavigate('/login');
          //  window.location.reload();
           }
         })
         .catch((error) => {
           const response = error.response;
           this.registrationErrors = true;
           if (response.status === 400) {
             this.registrationErrorMsg = 'Bad Request: User email or display name is already in use.';
           }
         });
     }}
   },


   clearErrors() {
     this.registrationErrors = false;
     this.registrationErrorMsg = 'There were problems registering this user.';
   },
  
 },

 mounted() {
    const urlParams = new URLSearchParams(window.location.search);
    const redirectRoute = urlParams.get('redirect');
    
    // Check for the redirect parameter and navigate to the specified route
    if (redirectRoute) {
      this.$router.push(redirectRoute);  // Navigate to the login page or other specified route
      // Optionally clear the query parameter to avoid infinite redirects
      history.replaceState(null, '', window.location.pathname);
    }
  },

};
</script>

<style scoped>
#avatar{
 width: 100px;
 height: 100px;
 border-radius: 50%;
 border-color:antiquewhite;
 border-width: 4px;
 border-style: double;
}

</style>
