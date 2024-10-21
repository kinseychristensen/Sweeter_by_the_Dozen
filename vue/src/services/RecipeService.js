import axios from 'axios';

export default {

 getRecipesByUser (userId){
    return axios.get(`/${userId}/recipes`)
 },

  createRecipe(recipe){
    return axios.post('/recipes/new', recipe)
 },
 getRecipeDetails (recipeId){
    return axios.get(`/recipe/${recipeId}`)
 },
getNumOfRecipes(){
    return axios.get('/recipes/number')
 },
 getRandomRecipe(){
    return axios.get('/recipes/random')
 },

saveRecipe (recipeId){
    return axios.post(`/recipe/${recipeId}/save`)
 },
getSavedRecipes (){
    return axios.get('/recipes/my-saves')
 },
getRecipesByTag (tagId, offset){ 
    return axios.get(`/recipes/search-tag/${tagId}/page/${offset}`)
 },
getAllTags(){
    return axios.get('/tags')
 },
deleteRecipe(recipeId){
    return axios.delete(`/recipe/${recipeId}`)
 },
removeFromSaves (recipeId){
    return axios.delete(`/recipe/${recipeId}/save`)
 },
 searchByKeyword (keyword, pageNum){
    return axios.get(`/search/${keyword}/page/${pageNum}`)
 },

}