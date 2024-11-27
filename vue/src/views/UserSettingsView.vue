<template>
    <div>

 
    <div v-if="isLoading">Loading...</div>
    
    <div v-else id="settings-grid">

      <h1 id="settings-header">Your Account</h1>

<button id="admin-tools-button" v-if="user.authorities[0].name == 'ROLE_ADMIN'"><router-link v-bind:to="{ name: 'admin-tools', params: {userId: user.id} }" class="button-text" > Go To Admin Tools</router-link></button>
<button id="password-reset-button"><router-link class="button-text" v-bind:to="{ name: 'password' }"> Reset Your Password</router-link></button>


      <Avatar :userId="user.id" id="settings-avatar"/>
      <div  v-if="first" id="first">
        <div> Display Name: {{user.displayName}}</div>
        <div>Email: {{ user.username }}</div>
        <img :src="user.avatarUrl"/>
        <div v-if="user.restricted"> Warning: your account is restricted due to violations of the site's terms of use.  You may still save recipes and browse, but you are no longer allowed to post comments or submit images.  If you feel this is in error, please contact the admin team.</div>
       <div v-if="user.violations != 0 && !user.restricted">Warning: You have had {{ user.violations }} violations of the site's terms of use.  The prohibited content has been reviewed by admin and deleted from the site.  Further activity may result in your account being restricted.</div>
       <div v-if="!user.restricted && user.violations ==0">Your account is in good standing.  Thank you for your support!</div> 
      </div>
      <button @click="toggleUpdate" id="update-button">{{buttonMsg}}</button>

  
       <div v-if="update" id="update">
        <form v-on:submit.prevent="verifyPassword">
          <label for="password1"> Please enter your password to make changes:
            </label>
            <input type="password" id="password1" v-model="user.password"/>
            <p></p><p></p>
            <button>Verify</button>
        </form></div>


       <div v-if="verified" id="verified">
        <form v-on:submit.prevent="saveChanges" id="settings-form">
         
          <label for="displayname" id="displayname-label"> Display Name: </label>
          <input type="text" id="displayname" v-model="editUser.displayName"/>
          <label for="email" id="email-answer-label">  Email: </label>
          <input type="email" id="email-answer" v-model="editUser.username"/>      
    <select v-model="editUser.avatarId">
      <option v-for="avatar in avatars" :key="avatar.avatarId" :value="avatar.avatarId">
        {{avatar.avatarId}}: {{ avatar.altText }}
      </option>
    </select>
    <button>Save Changes and Return to LogIn</button>
  </form>
    <div id="avatar-display">
      <a v-for="avatar in avatars" :key="avatar.avatarId">
      <img id="avatar" :src="avatar.avatarSrc" loading="lazy" />{{ avatar.avatarId }}: {{ avatar.altText }}</a>
  </div>

           
       
       </div>
      </div>
</div>
    </template>
    
    <script>
  
    import AuthService from '../services/AuthService';
    import Avatar from '../components/Avatar.vue';
    import {avatars} from '../AvatarsArray.js';

    
    export default {
      name: 'UserSettingsView',
      components: {
        Avatar,
      },
    
    data() {
      return {
        isLoading: true,
        update: false,
        buttonMsg: 'Update',
        user: {
          username: '',
          password: '',
          displayName: '',
          restricted: false,
          violations: 0,
          id: 0,
          avatarId: 0,
          avatarUrl: '',
        },
     
        editUser: {
          displayName: '',
          username: '',
          restricted: false,
          violations: 0,
          id: 1,
          avatarId: 0,
        },
        verified: false,
        first: true,
        showAvatars: false,
      avatarBtnMsg: 'Choose Avatar',
      avatars,
      };
    },
  
    methods: {
      toggleUpdate(){
        if(this.update){
          this.update = false;
          this.buttonMsg = 'Update';
          this.verified = false;
          this.user.password = '';
          this.first = true;
        }else if(!this.update && this.verified) {
          this.update = false;
          this.buttonMsg = 'Update';
          this.verified = false;
          this.user.password = '';
          this.first = true;
          this.editUser.displayName = this.user.displayName;
          this.editUser.username = this.user.username;
          this.editUser.violations = this.user.violations;
          this.editUser.restricted = this.user.restricted;
          this.editUser.id = this.user.id;
          this.editUser.avatarId = this.user.avatarId;
        }else {
          this.update = true;
          this.first = true;
          this.buttonMsg = 'Cancel';
        }
      },

      
  

async verifyPassword() {

        AuthService.login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.verified = true;
            this.update = false;
          
}
})
        },

        async saveChanges(){
          console.log(this.editUser);
            AuthService.updateUser(this.editUser)
            .then(response => {

            if(response.status == 200) {
              this.$router.push("/logout");
            } })


        },


      handleError(error, verb) {
          if (error.response) {
            this.$store.commit('SET_NOTIFICATION',
              "Error " + verb + " deck list. Response received was '" + error.response.statusText + "'.");
          } else if (error.request) {
            this.$store.commit('SET_NOTIFICATION', "Error " + verb + " deck list. Server could not be reached.");
          } else {
            this.$store.commit('SET_NOTIFICATION', "Error " + verb + " deck list. Request could not be created.");
          }
        },
    
      async retrieveUser(){
        try {
          this.isLoading = true;
          const response = await AuthService.getUser();
          this.user = response.data;
        }catch (error) {
          this.handleError(error, 'retrieving');
        }finally {
          this.user.password = '';
          this.editUser.displayName = this.user.displayName;
          this.editUser.username = this.user.username;
          this.editUser.id = this.user.id;
          this.editUser.avatarId = this.user.avatarId;
          this.isLoading = false;
        }
        }, 
      
    
    },
    created(){
      
     this.retrieveUser();
      
    }
    }
    
    
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
