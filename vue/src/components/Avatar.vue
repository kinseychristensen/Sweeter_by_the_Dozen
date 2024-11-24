<template>
    <div>
      <div v-if="loading">Loading...</div>
      <div v-else class="avatar-button">
        <img 
          :src="avatarPath" 
          :alt="avatarAlt" 
          id="avatar"
          
        />
        <a class="avatar-text">{{ userName }}</a>
        
      </div>
    </div>
  </template>
  
  <script>
  import { ref, onMounted, defineProps } from 'vue';
  import AuthService from '../services/AuthService';
  import { avatars } from '../AvatarsArray.js'; // Your avatar data
  
  export default {
    props: ['userId'],
  
    setup(props) {
      const userName = ref('');
      const avatarPath = ref('');
      const avatarAlt = ref('');
      const loading = ref(true);
  
      const loadUserInfo = async () => {
  loading.value = true; // Start loading
  try {
    const userInfo = await AuthService.getUserById(props.userId);
    const user = userInfo.data;

    userName.value = user.displayName; // Assuming user object has a 'name' field
    const avatar = avatars.find(avatar => avatar.avatarId === user.avatarId);

    avatarPath.value = avatar ? avatar.avatarSrc : '/assets/avatars/default.png';
    avatarAlt.value = avatar ? avatar.altText : 'Default Avatar';
  } catch (error) {
    console.error('Error fetching user info:', error);
  } finally {
    loading.value = false; // End loading
  }
};

  
      onMounted(loadUserInfo);
  
      return { userName, avatarPath, avatarAlt, loading };
    }
  };
  </script>
  
  <style scoped>

  </style>
  