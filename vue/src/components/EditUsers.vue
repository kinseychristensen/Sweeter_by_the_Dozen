<template>
    <div>
<h1>Edit User</h1>
<p>Search for User to Edit</p>
<label for="searchEmail">User Email:</label>
<input type="email"  id="searchEmail" v-model="searchUserEmail"/>
<button @click="findUserbyEmail">Search by Email</button>
OR 
<label for="searchId">User Id:</label>
<input type="number" id="searchId" v-model="searchUserId"/>
<button @click="findUserById">Search by Id</button>

<div v-if="showUserOptions">

  <h2>{{ foundUser.displayName }}</h2>
  Email: {{ foundUser.username }}
  Restricted: {{ foundUser.restricted }}
  Violations: {{ foundUser.violations }}
  <p v-if="foundUser.authorities[0].name == 'ROLE_ADMIN'">This User is an Admin</p>



  <button>Delete This User</button>
  <button @click="resetViolations">Reset Violations</button>
  <button v-if="foundUser.authorities[0].name == 'ROLE_USER'">Make User an Admin</button>
  <button v-if="foundUser.authorities[0].name == 'ROLE_ADMIN'">Make Admin a User</button>



</div>
{{ foundUser }}

    </div>
  </template>
  
  
  
  <script>

  import AuthService from '../services/AuthService';
  
  
  export default {
    name: 'EditUser',
    components: {
     
  }, 
  props: ['userid'],
  data() {
    return {
      isLoading: false,
      searchUserEmail: '',
      searchUserId: 0,
      foundUser: {
        id: 0,
        username: '',
        displayName: '',
        restricted: false,
        violations: 0,
      },
      showUserOptions: false,

    }
  },

  methods: {

    findUserbyEmail() {
      this.isLoading = true;
      AuthService.getId(this.searchUserEmail)
      .then((response) => {
        this.searchUserId=response.data;
        this.findUserById();
      })

    },
    findUserById() {
      this.isLoading = true;
      AuthService.getUserById(this.searchUserId)
      .then((response) => {
        this.foundUser =response.data;
        this.showUserOptions = true;
        this.isLoading = false;
      })

    },

    deleteUser(){
      const shouldDelete = confirm("Are you sure you want to permenatly delet this user and all their content?  This action cannot be undone.");
    
      if(shouldDelete){
        this.isLoading = true;
        AuthService.
      }
    
    
    
    },

    resetViolations() {
      const shouldReset = confirm("Are you sure you want to reset this user's violation count?  This will also remove a restricted status.")
      this.isLoading = true;
      let editedUser = {
        id: this.foundUser.id,
        username: this.foundUser.username,
        restricted: false,
        violations: 0,
        displayName: this.foundUser.displayName,
        avatarId: this.foundUser.avatar,
      };
      AuthService.updateUser(editedUser)
      .then((response) =>{
    if(response.data){
      this.findUserById(editedUser.id);

   }})
    }

  }, 

  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>