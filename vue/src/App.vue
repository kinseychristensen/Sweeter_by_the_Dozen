<template>

  <div id="capstone-app">
    <div id="nav">
      
      <router-link v-bind:to="{ name: 'home' }" v-if="$store.state.token != ''">Home</router-link>&nbsp;&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div>
    <router-view />
  </div>
</template>

<style scoped>

#nav {
  display: flex;
  justify-content: space-between;
  background-color: #f8f9fa;
  padding: 10px;
  border-bottom: 1px solid #dee2e6;

}

#nav a {
  color: #007bff;
  text-decoration: none;
}

#nav a:hover {
  text-decoration: underline;
}
div{
  font: bold;
  font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif
}
</style>
