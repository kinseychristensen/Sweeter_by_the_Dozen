<template>
    <div>
user settings

<router-link v-bind:to="{ name: 'admin-tools' }"> go to admin tools</router-link>
<router-link v-bind:to="{ name: 'password' }"> reset passwords</router-link>
</div>

   {{ editUser }}

   {{ user }}
 
    <div v-if="isLoading">Loading...</div>
    
    <div v-else >
      <Avatar :userId="user.id"/>
      <div  v-if="first">
        <p></p><p></p>
        <div> Display Name: {{user.displayName}}</div>
        <div>Email: {{ user.username }}</div>
        <img :src="user.avatarUrl"/>
        <div v-if="user.restricted"> Warning: your account is restricted due to violations of the site's terms of use.  You may still save recipes and browse, but you are no longer allowed to post comments or submit images.  If you feel this is in error, please contact the admin team.</div>
       <div v-if="user.flaggedComments != 0 && !user.restricted">Warning: You have had {{ user.flaggedComments }} comments that have been reported as violating the site's terms of use.  The comments have been reviewed by admin and deleted from the site.  Further activity may result in your account being restricted.</div>
       <div v-if="!user.restricted && user.flaggedComments ==0">Your account is in good standing.  Thank you for your support!</div> 
       <button @click="toggleUpdate">{{buttonMsg}}</button>
       <p></p><p></p>
      </div>
 
    <p></p><p></p>
       <div v-if="update">
        <form v-on:submit.prevent="verifyPassword">
          <label for="password1"> Please enter your password to make changes:
            </label>
            <input type="password" id="password1" v-model="user.password"/>
            <p></p><p></p>
            <button>Verify</button>
        </form></div>
      
        <p></p><p></p>

       <div v-if="verified">
        <form v-on:submit.prevent="saveChanges">
         
          <label for="displayname" id="displayname-label"> First Name: </label>
          <input type="text" id="displayname" v-model="editUser.displayName"/>
          <label for="email" id="email-answer-label">  Email: </label>
          <input type="email" id="email-answer" v-model="editUser.username"/>

          New Avatar: {{ editUser.avatarId }}
            
            <button>Save Changes</button>
        </form>
       </div>
      </div>

    </template>
    
    <script>
  
    import AuthService from '../services/AuthService';
    import Avatar from '../components/Avatar.vue';

    
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
          flaggedComments: 0,
          id: 0,
          avatarId: 0,
          avatarUrl: '',
        },
     
        editUser: {
          displayName: '',
          username: '',
          restricted: false,
          flaggedComments: 0,
          id: 1,
          avatarId: 0,
        },
        verified: false,
        first: true,
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
          this.editUser.flaggedComments = this.user.flaggedComments;
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