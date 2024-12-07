<template>
    <div>
      <SubmitPhoto v-if="$store.state.token != ''" :recipeId="recipeId"/>
<div v-if="photos.length == 0">There are no images for this recipe yet.</div>

<div v-else id="photo-grid">

  
<button @click="prevPhoto" id="prev-photo">Previous Photo</button>

<img id="recipe-photo" :src="photos[currentImgIndex].picUrl" :altText="photos[currentImgIndex].altText"/>
<p id="photo-caption">{{ photos[currentImgIndex].altText }}</p>
<button @click="nextPhoto" id="next-photo">Next Photo</button>
</div>

   </div>
  </template>
  
  
  
  <script>
  import SubmitPhoto from './SubmitPhoto.vue';

  
  export default {
    name: 'PhotoDisplay',
    components: {
     SubmitPhoto
  }, 
  props: ['photos', 'recipeId'],
  
  data() {
    return {
      currentImgIndex: 0,
      isLoading: false,
      

    };
  },
    methods: {
      nextPhoto() {
        this.currentImgIndex += 1;
        if(this.currentImgIndex == this.photos.length){
          this.currentImgIndex = 0;
        }
      },

      prevPhoto() {
        this.currentImgIndex -= 1;
        if(this.currentImgIndex < 0){
          this.currentImgIndex = this.photos.length -1;
        }
      }
  }
}
  </script>
  
  
  
  <style scoped>
  
  
  </style>