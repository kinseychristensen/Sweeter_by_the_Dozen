import axios from 'axios';

export default {

  login(user) {
    return axios.post('/login', user)
  },

  register(user) {
    return axios.post('/register', user)
  }, 
  
  updateUser(user) {
    return axios.put('/update-user', user)
  },

  updatePassword(user) {
    return axios.put('/update-password', user)
  },

  makeAdmin( userId) {
    return axios.put(`/user/${userId}/make-admin`)
  },
  removeAdmin( userId) {
    return axios.put(`/user/${userId}/remove-admin`)
  },

  getId(username) {
    return axios.get(`/get-user/username/${username}`)
  },

  getUser(){
    return axios.get('/get-user/principal')
  },

  getUserById(userId){
    return axios.get(`/get-user/id/${userId}`)
  },

  deleteUser(userId){
    return axios.delete(`/user/${userId}/delete`)
  }

}
