<template>

     
       <form  v-on:submit.prevent="checkEmail" v-if="starting">
         <label for="username" >Email Address:  </label>  <input type="text" id="username" v-model="userEmail" required/>
         <p></p>
         <button>Send Reset Code</button>
       
     </form>
     <p></p><p></p>
       <form v-on:submit.prevent="verifyCode" v-if="emailSent">
         <label for="con-code">Confirmation Code</label>
         <input type="text" id="con-code" v-model="confirmationCode" required />
      
       
         <p></p><p></p>
       <button>Verify</button>
       </form>
     
 <p></p><p></p>
       <form v-on:submit.prevent="updatePassword" v-if="confirmationPassed">
         <label for="password" >New Password:</label>
         <input type="password" id="password" v-model="user.password" required />
      
         <label for="confirmPassword" >Confirm Password:</label>
         <input type="password" id="confirmPassword" v-model="confirmPassword" required />
         <p></p><p></p>
       <button >Save New Password</button>
       </form>
       <p>{{ errorMessage }}</p>
       <button  v-if="showLogin" ><router-link id="loginlink" v-bind:to="{ name: 'login' }">Proceed to Log in.</router-link></button>
       
       
 
 </template>
 
 <script>
 import authService from '../services/AuthService';
 import emailjs from 'emailjs-com';
 
 export default {
   data() {
     return {
         userEmail: '',
         errorMessage: '',
         starting: true,
         emailSent: false,
         confirmationPassed: false,
         confirmationCode: '',
         sentCode: '',
         confirmPassword: '',
         showLogin: false,
       user: {
         id: 0,
         username: '',
         password: '',
       },
       registrationErrors: false,
       registrationErrorMsg: 'There were problems registering this user.',
       serviceId: 'service_jtr3pq8',
       templateId: 'template_qu2i4ub',
       publicKey: 'dQcG_YdFM_H1tqQP6',
     };
   },
  
   methods: {
     sendReset(){
             emailjs.send(this.serviceId, this.templateId, {
                 email: this.userEmail,
                 message: this.sentCode,
             }, this.publicKey)
             .then((response) => { 
                 console.log("Email successfully sent!", response.status, response.text)
             },
      (error) => {
         console.log('Failed to send email:', error)
      })   
         this.emailSent = true;
         this.starting = false;
     },
 
     generateCode(){
         this.sentCode = '';
         const array = new Int8Array(6);
         self.crypto.getRandomValues(array);
         for(let thisNum of array){
             if(thisNum < 0){
                 thisNum = thisNum * -1;}
                 thisNum = (thisNum % 10);
                 this.sentCode = this.sentCode + thisNum;
         } 
         this.sendReset();
     },
 
     async checkEmail (){
         this.errorMessage = "";
         try {
             const response = await authService.getId(this.userEmail);
             this.user.id= response.data;
         }catch (error) {
         this.handleError(error, 'cloning');
     }finally {
         if(this.user.id === 0){
             this.errorMessage="User not found.";
             this.emailSent = false;
         }else {
             this.generateCode();
         }
     }},
 
     verifyCode(){
         if(this.confirmationCode === this.sentCode){
             this.emailSent = false;
             this.confirmationPassed = true;   
             this.errorMessage = '';  
         }else {
             this.errorMessage = "That code does not match."
         }
        
 
     },
 
 
     async updatePassword() {
      console.log(this.user);
       if (this.user.password != this.confirmPassword) {
         this.registrationErrors = true;
         this.registrationErrorMsg = 'Password & Confirm Password do not match.';
       } else {
         try{
         const response = await authService.updatePassword(this.user)
           
             this.errorMessage = "Your password has been updated.";
             this.showLogin = true;
        
         
             }catch(error) {
             const response = error.response;
             }finally {
                this.confirmationPassed = false;
              
             }    
       }
     },
 
     clearErrors() {
       this.registrationErrors = false;
       this.registrationErrorMsg = 'There were problems saving the new password.';
     },
   },
 };
 </script>
 