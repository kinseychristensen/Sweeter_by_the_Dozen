import axios from 'axios';

export default {

  getCommentsByRecipeId(recipeId) {
    return axios.get(`/recipe/${recipeId}/comments`)
  }, 

  createComment(comment) {
    return axios.post('/new-comment', comment)
  }, 

  reportComment(commentId) {
    return axios.put(`/comment/${commentId}`)
  }, 

  unreportComment(commentId) {
    return axios.post(`/comment/${commentId}`)
  }, 

 deleteComment(commentId) {
    return axios.delete(`/comment/${commentId}`)
  }, 
  getReportedComments() {
    return axios.get('/comments/reported')
  } 



}