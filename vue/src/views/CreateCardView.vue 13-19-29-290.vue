<template>
   

    <div id="create-card">
    
    <div v-if="!addImage">
      <button v-on:click="addImage = true">Add Image</button>
    </div>
   
    <div class="uploadImage" v-if="addImage"> 
      <button class= "move" v-on:click="upload">Upload Image</button><br>
      <button class= "move" v-on:click="addImage = false">Cancel</button>
    </div>
    
 
      
        </div>
</template>

<script>
import NavTool from '@/components/NavTool.vue';
import CardServices from '@/services/CardServices.js';
import Logo from '../components/Logo.vue';
import DeckServices from '../services/DeckServices';
import DeckIcon from '../components/DeckIcon.vue';



export default {
  name: 'CardView',

  components: {

    NavTool,
    Logo,
  
},
    
    data () {
      return {
          newCard: {
            userId: "",
            frontQuestion: "", 
            backAnswer: "", 
            cardImg: "",
            tags: [],
          },
          tagsAsString: "",
          deckToAddTo: {},
          decks: [],
          isLoading: false,
          errorMessage: "",
          addImage: false,
          
    };
  },
    
    methods: {

      upload() {
        this.myWidget.open();
        this.addImage = false;
      },
      handleError(error, verb) {
      if (error.response) {
        this.$store.commit('SET_NOTIFICATION',
          "Error " + verb + " CardView. Response received was '" + error.response.statusText + "'.");
        } else if (error.request) {
        this.$store.commit('SET_NOTIFICATION', "Error " + verb + " card list. Server could not be reached.");
        } else {
        this.$store.commit('SET_NOTIFICATION', "Error " + verb + " card list. Request could not be created.");
        }
      },

  
    resetForm (){
      this.newCard = {
        userId: "",
        frontQuestion: "", 
        backAnswer: "", 
        cardImg: "",
        tags: [],
        };
      
       },


      createCard() {
      this.isLoading = true;
      if(this.tagsAsString != ""){
        this.newCard.tags = this.tagsAsString.split(",");}

        if (!this.validateForm()) {
          this.isLoading = false;
         return;
        }

      CardServices.createNewCard(this.newCard)
        .then(response => {
          if(response.status === 201) {
            this.newCard.cardId = response.data;
            window.alert('Card Added!');
           this.addCardToDeck();
          

          }
        
          }).catch(error => {
            this.handleError(error, 'adding');
          });
        },
    },
    
    created() { 
      this.retrieveDecks();
      
  
  },
  mounted() {
       this.myWidget = window.cloudinary.createUploadWidget(
      {
        // Insert your cloud name and presets here, 
        // see the documentation
        cloudName: 'dvxtx3qq6', 
        uploadPreset: 'fqofg0ln'
      }, 
      (error, result) => { 
        if (!error && result && result.event === "success") { 
          console.log('Done! Here is the image info: ', result.info); 
          console.log("Image URL: " + result.info.url);
          this.newCard.cardImg = result.info.url;
        }
      }
    );

  }


}
 
</script>

<style scoped>

</style>