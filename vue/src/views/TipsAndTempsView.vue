<template>
  <div id="tips-grid">
  <div id="tips-tabs">
  
  
  <button id="showTempsBtn" @click="toggleTemps" :class="{'active-tab-tip': showTemps}" class="tip-tab-button">Cooking Temperatures</button>
  <button id="showConvertBtn" @click="toggleConvert" :class="{'active-tab-tip': showConvert}" class="tip-tab-button">Measurement Conversion</button>
  <button id="showLeftoversBtn" @click="toggleLeftovers" :class="{'active-tab-tip': showLeftovers}" class="tip-tab-button">Ideas for Leftovers</button>
  <button id="showSafetyVidsBtn" @click="toggleSafety"  :class="{'active-tab-tip': showSafetyVids}" class="tip-tab-button">Kitchen Safety Videos</button>
  <button id="showSubstitutionsBtn" @click="toggleSub" :class="{'active-tab-tip': showSub}" class="tip-tab-button">Common Substitutions</button>
  
  
  </div>
  
  
  <div id="tips_temps_flex">
  
  
  <Temps id="temps" v-if="showTemps"/>
  <Leftovers id="leftovers" v-if="showLeftovers"/>
  <Substitutions id="subs" v-if="showSub"/>
  <SafetyVids id="safetyVids" v-if="showSafetyVids"/>
  <div id="convert" v-if="showConvert">
  
  
   <form
   action="https://www.google.com/search"
   method="get"
   target="_blank"
  >
   <label for="conversion-query">What do you want to convert?</label>
   <input
     type="text"
     name="q"
     id="conversion-query"
     placeholder="e.g., 1 cup to ml"
     value="1 cup to ml"
   />
   <button type="submit">Convert</button>
  </form>
  <img src="/assets/icons/conversion.png"/>
  
  
  </div>
  
  
  
  
  </div>
  
  
  
  
  
  
     </div>
   </template>
   
    <script>
   import Temps from '../components/Temps.vue';
   import Substitutions from '../components/Substitutions.vue';
   import SafetyVids from '../components/SafetyVids.vue';
   import Leftovers from '../components/Leftovers.vue';
  
  
  
  
    export default {
     name: 'TipsAndTempsView',
     components: {
       Temps,
       Substitutions,
       SafetyVids,
       Leftovers,
     
   },
   data() {
     return {
       showConvert: false,
       showLeftovers: false,
       showSafetyVids: false,
       showSub: false,
       showTemps: true,
     }
   },
  
  
  methods: {
  
  
  setAllFalse(){
   this.showConvert = false;
   this.showLeftovers = false;
   this.showSafetyVids = false;
   this.showSub = false;
   this.showTemps = false;
  },
  
  
  toggleConvert(){
     this.setAllFalse();
     this.showConvert = true;
  },
  toggleLeftovers(){
     this.setAllFalse();
     this.showLeftovers = true; 
  },
  toggleTemps(){
     this.setAllFalse();
     this.showTemps = true;
  },
  toggleSafety(){
     this.setAllFalse();
     this.showSafetyVids = true;
  },
  toggleSub(){
     this.setAllFalse();
     this.showSub = true;
  },
  
  
  
  
  },
  
  
  
  
   }
   </script>
   
    <style scoped>
   
   </style>
  