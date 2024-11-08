<template>
    <a v-if="newFraction != 0">
{{ newFraction }}
    </a>
  </template>
  
  
  
  <script>

  
export default {
  props: ['numerator', 'denominator'],
  name: 'BuildFraction',

data(){
  return {
    
  }
},

computed: {
    newFraction(){
        const operator = "/";

        if(this.denominator == 1){
            return this.numerator;
        }else if(this.numerator < this.denominator){
            return this.numerator + operator + this.denominator;
        }else {
            const whole = Math.floor(this.numerator / this.denominator);
            const newNumerator = this.numerator % this.denominator;
        return whole + "  " + newNumerator + operator + this.denominator;
    }
}
}
}
  </script>
  
  
  
  <style scoped>
  
  
  </style>