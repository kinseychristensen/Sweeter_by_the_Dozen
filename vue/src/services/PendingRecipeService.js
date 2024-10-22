import axios from 'axios';

export default {

  createPendingRecipe(recipe) {
    return axios.post('/new-recipe/pending', recipe)
  }, 

  getPendingRecipes() {
    return axios.get('/pending-recipes')
  }, 

  deletePendingRecipe(recipeId){
    return axios.delete(`/pending-recipe/${recipeId}`)
  },

  getPendingRecipesByUser(){
    return axios.get('/my-pending-recipes')
  },

  getPendingPics(){
    return axios.get('/pending-pics')
  },

  approvePendingPics(pics) {
    return axios.put('pending-pics', pics)
  },

  deletePendingPics(pics){
    return axios.put('/pending-pics/delete', pics)
  },

  submitPhoto(recipeId, picUrl){
    return axios.put(`/submit-photo/${recipeId}/${picUrl}`)
  }





}