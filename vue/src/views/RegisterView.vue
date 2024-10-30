<template>
   <div id="register" class="register-container">
    <form class="register-form" v-on:submit.prevent="register">
      
      <h1 class="register-title">Create Account</h1>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="email" class="bold-label">EMAIL:</label>
        <input type="email" id="email" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="displayName" class="bold-label">DISPLAY NAME:</label>
        <input type="text" id="displayName" v-model="user.displayName" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password" class="bold-label">PASSWORD:</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword" class="bold-label">Confirm Password</label>
        <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
      </div>
      <button type="submit" class="btn">Create Account</button>
      <p class="login-link"><router-link v-bind:to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
    avatar: {{ user.avatarId }}
    <button @click="toggleShowAvatar">{{ avatarBtnMsg }}</button>

  
    <select v-model="user.avatarId">
      <option v-for="avatar in avatars" :key="avatar.avatarId" :value="avatar.avatarId">
        {{avatar.avatarId}}: {{ avatar.altText }}
      </option>
    </select>
    <div v-if="showAvatars">
      <a v-for="avatar in avatars" :key="avatar.avatarId" :value="avatar.avatarId">
      <img id="avatar" :src="avatar.avatarSrc"/>{{ avatar.avatarId }}: {{ avatar.altText }}</a>
  </div>
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  data() {
    return {
      user: {
        username: '',
        displayName: '',
        password: '',
        confirmPassword: '',
        role: 'user',
        avatarId: 1,
      },
        avatars: [
          { avatarId: 1,
            avatarSrc: 'src/assets/avatars/1.png',
            altText: 'Smiling red-haired girl',
        },
        { avatarId: 2,
            avatarSrc: 'src/assets/avatars/2.png',
            altText: 'Curious dark-haired girl',
        }, 
        { avatarId: 3,
            avatarSrc: 'src/assets/avatars/3.png',
            altText: 'Shy girl behind fence',
        },
        { avatarId: 4,
            avatarSrc: 'src/assets/avatars/4.png',
            altText: 'Smiling light-haired boy',
        },
        { avatarId: 5,
            avatarSrc: 'src/assets/avatars/5.png',
            altText: 'Bashful curly-haired boy',
        },
        { avatarId: 6,
            avatarSrc: 'src/assets/avatars/6.png',
            altText: 'Playful blonde-haired girl',
        },
        { avatarId: 7,
            avatarSrc: 'src/assets/avatars/7.png',
            altText: 'Smiling boy leaning on wall',
        },
        { avatarId: 8,
            avatarSrc: 'src/assets/avatars/8.png',
            altText: 'Happy girl behind tree',
        },
        { avatarId: 9,
            avatarSrc: 'src/assets/avatars/9.png',
            altText: 'Curly-haired girl behind fence',
        },
        { avatarId: 10,
            avatarSrc: 'src/assets/avatars/10.png',
            altText: 'Playful curly-haired boy',
        },
        { avatarId: 11,
            avatarSrc: 'src/assets/avatars/11.png',
            altText: 'Shy girl in window',
        },
        { avatarId: 12,
            avatarSrc: 'src/assets/avatars/12.png',
            altText: 'Child hiding behind brick wall',
        },
        { avatarId: 13,
            avatarSrc: 'src/assets/avatars/13.png',
            altText: 'Chickens in garden',
        },
        { avatarId: 14,
            avatarSrc: 'src/assets/avatars/14.png',
            altText: 'Hiker near mushroom forest',
        },
        { avatarId: 15,
            avatarSrc: 'src/assets/avatars/15.png',
            altText: 'Pale beige mushrooms',
        },
        { avatarId: 16,
            avatarSrc: 'src/assets/avatars/16.png',
            altText: 'Birds around birdbath',
        },
        { avatarId: 17,
            avatarSrc: 'src/assets/avatars/17.png',
            altText: 'Bountiful kitchen with shelf',
        },
        { avatarId: 18,
            avatarSrc: 'src/assets/avatars/18.png',
            altText: 'Shelves of preserves and jam',
        },
        { avatarId: 19,
            avatarSrc: 'src/assets/avatars/19.png',
            altText: 'Cabin with field beneath the moon',
        },
        { avatarId: 20,
            avatarSrc: 'src/assets/avatars/20.png',
            altText: 'Cat in the flowers',
        },
        { avatarId: 21,
            avatarSrc: 'src/assets/avatars/21.png',
            altText: 'Cottage surrounded by wildflowers',
        },
        { avatarId: 22,
            avatarSrc: 'src/assets/avatars/22.png',
            altText: 'Cottage with garden',
        },
        { avatarId: 23,
            avatarSrc: 'src/assets/avatars/23.png',
            altText: 'Dog with flowers',
        },
        { avatarId: 24,
            avatarSrc: 'src/assets/avatars/24.png',
            altText: 'Lone person walking towards a cottage',
        },
        { avatarId: 25,
            avatarSrc: 'src/assets/avatars/25.png',
            altText: 'Colorful vegetables',
        },
        { avatarId: 26,
            avatarSrc: 'src/assets/avatars/26.png',
            altText: 'Fresh bread by the window',
        },
        { avatarId: 27,
            avatarSrc: 'src/assets/avatars/27.png',
            altText: 'Stack of books',
        },
        { avatarId: 28,
            avatarSrc: 'src/assets/avatars/28.png',
            altText: 'A person gardening',
        },
        { avatarId: 29,
            avatarSrc: 'src/assets/avatars/29.png',
            altText: 'Mushroom cottage',
        },
        { avatarId: 30,
            avatarSrc: 'src/assets/avatars/30.png',
            altText: 'Table with bread and oranges',
        },
        { avatarId: 31,
            avatarSrc: 'src/assets/avatars/31.png',
            altText: 'Cottage with pond',
        },
        { avatarId: 32,
            avatarSrc: 'src/assets/avatars/32.png',
            altText: 'View from a kitchen window',
        },
        { avatarId: 33,
            avatarSrc: 'src/assets/avatars/33.png',
            altText: 'Basket of pink flowers',
        },
        { avatarId: 34,
            avatarSrc: 'src/assets/avatars/34.png',
            altText: 'Cottage with pink flowers',
        },
        { avatarId: 35,
            avatarSrc: 'src/assets/avatars/35.png',
            altText: 'Cozy home library',
        },
        { avatarId: 36,
            avatarSrc: 'src/assets/avatars/36.png',
            altText: 'Beekeeper and beehive',
        },
        { avatarId: 37,
            avatarSrc: 'src/assets/avatars/37.png',
            altText: 'Bicyclist on road with flowers',
        },
        { avatarId: 38,
            avatarSrc: 'src/assets/avatars/38.png',
            altText: 'Tea party table',
        },
        { avatarId: 39,
            avatarSrc: 'src/assets/avatars/39.png',
            altText: 'Cat in window',
        },
        { avatarId: 40,
            avatarSrc: 'src/assets/avatars/40.png',
            altText: 'Starry night cottage',
        }
      ],
  
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
      showAvatars: false,
      avatarBtnMsg: 'Choose Avatar',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: User email or display name is already in use.';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
    toggleShowAvatar(){
      if(this.showAvatars){
        this.showAvatars = false;
        this.avatarBtnMsg = "Select Avatar";
      }else {
        this.showAvatars = true;
        this.avatarBtnMsg = "Confirm Avatar";
      }
    },
  },
};
</script>

<style scoped>
#avatar{
  width: 100px;
  height: 100px;
  border-radius: 50%;
  border-color:antiquewhite;
  border-width: 4px;
  border-style: double;
}

</style>
