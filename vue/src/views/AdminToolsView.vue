<template>
    <div>
<h1>Admin Page</h1>
 code: 
{{ sentCode }}
<form v-on:submit.prevent="verifyCode" v-if="emailSent">

  <p>We have emailed you a confirmation code.  You must enter the code to access the admin tools.</p>
    <label for="con-code">Confirmation Code</label>
    <input type="text" id="con-code" v-model="confirmationCode" required />
 
  
    <p></p><p></p>
  <button>Verify</button>
  </form>
<div v-if="confirmationPassed">
<ApproveImage :userId="user.id" />
<ApproveRecipe :userId="user.id"/>
<ReviewComments :userId="user.id"/>
<EditUsers :userId="user.id"/>
    </div>
  </div>
  </template>
  
  
  
  <script>
  
  import ApproveImage from '../components/ApproveImage.vue';
  import ApproveRecipe from '../components/ApproveRecipe.vue';
  import ReviewComments from '../components/ReviewComments.vue';
  import EditUsers from '../components/EditUsers.vue';
  import AuthService from '../services/AuthService';
  import emailjs from 'emailjs-com';


  
  export default {
    name: 'AdminToolsView',
    components: {
     ApproveImage,
     ApproveRecipe,
     ReviewComments,
     EditUsers,

  },
  data(){
    return {
      userId: 0,
      emailSent: false,
      confirmationCode: '',
      sentCode: '',
      confirmationPassed: false,
      user: {
        id: 0,
        username: '',
        displayName: '',
      },
      serviceId: 'service_jtr3pq8',
  templateId: 'template_qu2i4ub',
  publicKey: 'dQcG_YdFM_H1tqQP6',
      
    }
  },
  methods: {
  sendCode(){
        emailjs.send(this.serviceId, this.templateId, {
            email: this.user.username,
            message: this.sentCode,
        }, this.publicKey)
        .then((response) => { 
            console.log("Email successfully sent!", response.status, response.text)
        },
 (error) => {
    console.log('Failed to send email:', error)
 })   
    this.emailSent = true;
    
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
    this.sendCode();
},

async getUser (){
    this.errorMessage = "";
    try {
        const response = await AuthService.getUserById(this.userId);
        this.user = response.data;
    }catch (error) {
    this.handleError(error, 'fetching');
}finally {
        this.generateCode();
    
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
  },
created() {

this.isLoading = true;
const userId = parseInt(this.$route.params.userId);
this.userId = userId;
this.getUser();
}

};
</script>


  
  
  
  <style scoped>
  
  
  </style>