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



  <button @click="deleteUser">Delete This User</button>
  <button @click="resetViolations">Reset Violations</button>
  <button v-if="foundUser.authorities[0].name == 'ROLE_USER'" @click="makeUserAdmin">Make User an Admin</button>
  <button v-if="foundUser.authorities[0].name == 'ROLE_ADMIN'" @click="makeAdminUser">Remove Admin privleges</button>


</div>
<div v-else>No user found.</div>


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
      this.showUserOptions = false;
      AuthService.getId(this.searchUserEmail)
      .then((response) => {
        this.searchUserId=response.data;
        this.findUserById();
      })

    },
    findUserById() {
      this.isLoading = true;
      this.showUserOptions = false;
      AuthService.getUserById(this.searchUserId)
      .then((response) => {
        this.foundUser =response.data;
        this.showUserOptions = true;
        this.isLoading = false;
      })

    },

    deleteUser(){
      const shouldDelete = confirm("Are you sure you want to permanently delet this user and all their content?  This action cannot be undone.");
    
      if(shouldDelete){
        this.isLoading = true;
        AuthService.deleteUser(this.foundUser.id)
        .then((response) => {
          if(response.data){
            console.log("User was deleted.");
            this.showUserOptions = false;
            this.foundUser = {};
            this.isLoading = false;
          }
        })
        
       
      }
    
    
    
    },

    resetViolations() {
      const shouldReset = confirm("Are you sure you want to reset this user's violation count?  This will also remove a restricted status.")
      if(shouldReset){
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

   }})}
    },

    makeUserAdmin() {
      const shouldAdmin = confirm("Are you sure you want to set this user as an admin?");
      if(shouldAdmin){
        this.isLoading = true;
        AuthService.makeAdmin(this.foundUser.id)
        .then((response) => {
          this.findUserById();
        })
      }
    },

  makeAdminUser(){
    const shouldAdmin = confirm("Are you sure you want to remove admin privleges for this user?");
      if(shouldAdmin){
        this.isLoading = true;
        AuthService.removeAdmin(this.foundUser.id)
        .then((response) => {
          this.findUserById();
        })
      }
    }
  }

  }
  </script>
  
  
  
  <style scoped>
  
  
  </style>