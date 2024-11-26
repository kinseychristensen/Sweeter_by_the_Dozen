<template>
  <div v-if="user.restricted"> Sorry.  Your account is restricted.  You are not able to upload images. </div>
  <div v-else>
   
    <div class="uploadImage" v-if="$store.state.token != ''">
      <button v-on:click="upload" id="upload-photo-button">Upload Image</button><br>
    </div>

    <div v-if="addImage">
      <!-- Show image upload section -->
      <p>Upload Image Section</p>
      <button v-on:click="upload">Open Upload Widget</button>
      
    </div>

    <!-- Image preview or additional UI can be added here -->
    <div v-if="showPreview" id="submitting-photo-grid">
      <h3>Image Uploaded: </h3>
      <img :src="pic.picUrl" alt="Uploaded Image" id="submitting-photo"/>
      <p id="submitting-warning">All photos will be reviewed by an admin before posting.  Please only submit images that follow our Code of Conduct.</p>
      <button v-on:click="showPreview = false">Cancel</button>
      <button @click="saveImage">Submit Picture</button>
    </div>
  </div>

</template>

<script>

import AuthService from '../services/AuthService';
import PendingRecipeService from '../services/PendingRecipeService';


export default {
  props: ['recipeId'],
  data() {
    return {
      user: {},
      pic: {
        picUrl: '',
        recipeId: 0,
        userId: 0,
        altText: '',
      },
      addImage: false,  // Controls whether the image upload form is shown
      showPreview: false,
    };
  },
  methods: {
    upload() {
      this.myWidget.open();  // Open the Cloudinary upload widget
      this.addImage = false;
      this.showPreview = true;  // Hide upload form after opening widget
    },
    cancelUpload() {
      this.addImage = false;  // Hide the upload form if the user cancels
    },
    handleError(error, verb) {
      if (error.response) {
        this.$store.commit('SET_NOTIFICATION', 
          "Error " + verb + " CardView. Response received was '" + error.response.statusText + "'."
        );
      } else if (error.request) {
        this.$store.commit('SET_NOTIFICATION', 
          "Error " + verb + " card list. Server could not be reached."
        );
      } else {
        this.$store.commit('SET_NOTIFICATION', 
          "Error " + verb + " card list. Request could not be created."
        );
      }
    },

    getUser(){
 AuthService.getUser()
 .then((response) => {
  this.user = response.data;
  this.pic.userId = this.user.id;
  this.pic.recipeId = this.recipeId;
 }).catch((error) => {
    console.error('Error fetching user data:', error);
  });
}, 

saveImage() {
  console.log(this.pic);
  PendingRecipeService.submitPhoto(this.pic)
  .then((response) => {
    this.$router.go(0);
  }).catch((error) => {
    console.error('Error submitting photo.  ', error);
  });
}


  },

  created() {
    this.isLoading = false,
    this.getUser();
    this.pic.recipeId = this.recipeId;
  },

  mounted() {
    // Create Cloudinary widget
    this.myWidget = window.cloudinary.createUploadWidget(
      {
        cloudName: 'davmzu0ic', 
        uploadPreset: 'SweeterDozenRecipeImage',
        multiple: false,
      },
      (error, result) => {
        if (error) {
          console.error("Upload failed: ", error);
          this.handleError(error, "uploading");
        }
        if (result && result.event === "success") {
          console.log("Done! Here is the image info: ", result.info);
          this.pic.picUrl = result.info.url;
          this.$store.commit('SET_NOTIFICATION', 'Image uploaded successfully!');
        }
      }
    );
  }
};
</script>

<style scoped>

</style>
