<template>

  <div id="capstone-app">
    <div id="nav">
      
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      <router-link v-bind:to="{ name: 'login' }" v-else>Login</router-link>

      <Login v-if="$store.state.token == ''"/>

      <Register v-if="$store.state.token == ''"/>

    </div>
    <router-view />
  </div>
</template>

<script>
import Login from './components/Login.vue';
import Register from './components/Register.vue';

  
export default {
    name: 'MyRecipesView',
    components: {
     Login,
     Register
  }
  }

</script>

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
