<template>
  <div class="home-container">
   home

   <router-link v-bind:to="{ name: 'my-recipes' }">my recipes</router-link>     
   <router-link v-bind:to="{ name: 'settings' }"> user settings</router-link>


  </div>
</template>



<script>
import { RouterLink } from 'vue-router';





export default {
  name: 'HomeView',
  components: {
   
   
}
}
</script>



<style scoped>


</style>

