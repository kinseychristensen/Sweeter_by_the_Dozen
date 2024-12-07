<template>
    <div id="register">
     
   
       <h1 id="register-title">Create Account</h1>
       <div role="alert" v-if="registrationErrors" id="error-msg">
         {{ registrationErrorMsg }}
         <button @click="showFirst = true; showThird=false;" id="start-over-button">Start Over</button>
       </div>
       
       <div class="form-input-group" v-if="showFirst" id="first-register">
         <label for="email" class="bold-label">Your Email: </label>
         <input type="email" id="email" v-model="user.username" required autofocus />
         <label for="password" class="bold-label">Password: </label>
         <input type="password" id="password" v-model="user.password" required />
         <label for="confirmPassword" class="bold-label">Confirm Password: </label>
         <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
         <p v-if="this.user.password != this.user.confirmPassword">Passwords must match.</p>
         <p v-if="showErrorMsg">Opps!  We already have an account under that email.  Please continue to Log in.</p>
         <button v-else type="button" @click="checkEmail">Continue</button>
       </div>
      
      
       <div class="form-input-group" v-if="showSecond" id="second-register">
        <div id="register-second-form"> 
          <p>Choose an Avatar Icon and Display Name.  These may be changed later.</p>
  
        <label for="displayName" class="bold-label">Your Display Name:</label>
        <p></p>
        <input type="text" id="displayName" v-model="user.displayName" required />
        <p></p>
        <label for="register-avatar-select">Avatar:</label>
  
     <select v-model="user.avatarId" id="register-avatar-select">
       <option v-for="avatar in avatars" :key="avatar.avatarId" :value="avatar.avatarId">
         {{avatar.avatarId}}: {{ avatar.altText }}
       </option>
     </select>
    
     <button type="button" @click="showSecond = false; showThird = true">Continue</button>
    
    </div>
    <div id="avatar-register" class="avatar-display">
        <a v-for="avatar in avatars" :key="avatar.avatarId" class="avatar-individual-flex">
        <img class="avatar" :src="avatar.avatarSrc" loading="lazy" />#{{ avatar.avatarId }}</a>
    </div>
  </div>
  
       <div v-if="showThird" id="third-register">
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
              this.$router.push('/login');

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
  

  
  };
  </script>
  
  
  