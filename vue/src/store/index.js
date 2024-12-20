import { createStore as _createStore } from 'vuex';
import axios from 'axios';

const NOTIFICATION_TIMEOUT = 6000;

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      
    },
    mutations: {


      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      },
      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
      },
      SET_NOTIFICATION(state, notification){
        if(state.notification){
          this.commit('CLEAR_NOTIFICATION');
        }
        if (typeof notification === 'string'){
          notification = {
            message: notification,
            type: 'error',
            timeout: NOTIFICATION_TIMEOUT
          }
        } else {
          notification.type = notification.type || 'error';
          notification.timeout = notification.timeout || NOTIFICATION_TIMEOUT;
        }
        state.notification = notification;
        notification.timer = window.setTimeout(() => {
          this.commit('CLEAR_NOTIFICATION')
        }, notification.timeout);
      },
      CLEAR_NOTIFICATION(state) {
        if(state.notification && state.notification.timer){
          window.clearTimeout(state.notification.timer);
        }
        state.notification = null;
      },

      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        axios.defaults.headers.common = {};
      },
    },
    getters: {
      isAuthenticated: state => !!state.token,  // Checks if a token exists
    },

});
  return store;
}
