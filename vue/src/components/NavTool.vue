<template>
  <div class="nav-tool">
    <router-link class="nav-button" v-bind:to="{ name: 'myDecks' }">My Decks</router-link>
    <router-link class="nav-button" v-bind:to="{ name: 'createDeck' }">Create Deck</router-link>
    <router-link class="nav-button" v-bind:to="{ name: 'searchCards' }">Search Cards</router-link>
    <router-link class="nav-button" v-bind:to="{ name: 'adminDecks' }">Admin Decks</router-link>
  </div>
</template>

<script>
export default {
  name: 'NavTool'
}
</script>

<style scoped>
.nav-tool {
  display: flex;
  flex-direction: column;
  /* width: 200px; */
}

.nav-button {
  background-color: #ffd966;
  color: black;
  text-align: center;
  margin: 10px;
  padding: 35px;
  border-radius: 25px;
}

.nav-button:hover {
  background-color: rgb(4, 157, 217, 1);
}
</style>