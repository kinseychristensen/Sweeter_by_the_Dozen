import axios from 'axios';

export default {

getRecipeDetails(recipeId){
    return axios.get(`/recipe/${recipeId}`)
 },
 getRecipesByUser(userId){
    return axios.get(`/${userId}/recipes`)
 },
 getRecipesForPrincipal(){
   return axios.get(`/my-recipes`)
},

  createRecipe(recipe){
    return axios.post('/recipes/new', recipe)
 },
getNumOfRecipes(){
    return axios.get('/recipes/number')
 },
 getRandomRecipe(){
    return axios.get('/recipes/random')
 },

saveRecipe(recipeId){
    return axios.post(`/recipe/${recipeId}/save`)
 },
getSavedRecipes(){
    return axios.get('/recipes/my-saves')
 },
getAllTags(){
    return axios.get('/tags')
 },
deleteRecipe(recipeId){
    return axios.delete(`/recipe/${recipeId}`)
 },
removeFromSaves(recipeId){
    return axios.delete(`/recipe/${recipeId}/save`)
 },
 searchByKeyword(keyword, pageNum){
    return axios.get(`/search/=${keyword}/page/${pageNum}`)
 },
 searchByKeywordAndTags(keyword, pageNum, tags){
    return axios.put(`/search/=${keyword}/page/${pageNum}`, tags)
 },

 updateRecipe(recipeId, recipe){
   return axios.put(`/recipe/${recipeId}/update`, recipe)
 }

}