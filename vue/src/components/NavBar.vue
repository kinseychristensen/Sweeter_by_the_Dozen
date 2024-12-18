<template>
    <div id="nav-bar-container">
      <div id="top-row-flex">
   <router-link v-bind:to="{ name: 'search' }" id="search-books" class="nav-flex-item"><img src='/public/assets/nav_bar/search_icon.png' alt="stack of books labeled search" class="nav-image" loading="lazy"/> </router-link>
   <router-link v-bind:to="{ name: 'my-recipes' }"  id="my-recipes-jar" class="nav-flex-item"><img src='/public/assets/nav_bar/my_recipes_icon.png' alt='small jar labeled my recipes' class="nav-image" loading="lazy"/></router-link>       
   <router-link v-bind:to="{ name: 'grow' }" id="grow-flower" class="nav-flex-item"> <img src='/public/assets/nav_bar/help_us_grow_icon.png' alt="vase of flowers labeled help us grow" class="nav-image" loading="lazy"/></router-link>
   <router-link v-bind:to="{ name: 'settings' }" id="settings-bowl" class="nav-flex-item"> <img src='/public/assets/nav_bar/settings_icon.png' alt="stack of bowls labeled settings" class="nav-image" loading="lazy"/></router-link>  
   <router-link v-bind:to="{ name: 'tips' }" id="temps-spoon" class="nav-flex-item"> <img src='/public/assets/nav_bar/tips_and_temps_icon.png' alt="container of wooden spoons labeled tips and temps" class="nav-image" loading="lazy"/></router-link>
  </div> 
   
   <router-link v-bind:to="{ name: 'home' }" id="home-shelf"><img src='/public/assets/nav_bar/home_icon.png' alt="shelf with a hanging banner saying sweeter by the dozen" class="nav-image" loading="lazy"/></router-link>

    </div>
  </template>
  
  
  
  <script>
  
  
  export default {
    name: 'NavBar',
    components: {
     
  }
  }
  </script>
  
  
  
  <style scoped>
  .nav-image {

  cursor: pointer;
}

.nav-image:hover {
  animation: shake 1.5s infinite;
}

@keyframes shake {
  0% { transform: translate(1px, 1px) rotate(0deg); }
  10% { transform: translate(-1px, -2px) rotate(-1deg); }
  20% { transform: translate(-3px, 0px) rotate(1deg); }
  30% { transform: translate(3px, 2px) rotate(0deg); }
  40% { transform: translate(1px, -1px) rotate(1deg); }
  50% { transform: translate(-1px, 2px) rotate(-1deg); }
  60% { transform: translate(-3px, 1px) rotate(0deg); }
  70% { transform: translate(3px, 1px) rotate(-1deg); }
  80% { transform: translate(-1px, -1px) rotate(1deg); }
  90% { transform: translate(1px, 2px) rotate(0deg); }
  100% { transform: translate(1px, -2px) rotate(-1deg); }
}
  
  
  </style>