import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import MyRecipesView from '../views/MyRecipesView.vue';
import UserSettingsView from '../views/UserSettingsView.vue';
import SearchView from '../views/SearchView.vue';
import RecipeByIdView from '../views/RecipeByIdView.vue';
import GrowView from '../views/GrowView.vue';
import AdminToolsView from '../views/AdminToolsView.vue';
import UpdatePasswordView from '../views/UpdatePasswordView.vue';



const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    
  }, 
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView,
    
  },
  
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    
  },
  {
    path: "/search",
    name: "search",
    component: SearchView,
    
  },
  {
    path: "/password-reset",
    name: "password",
    component: UpdatePasswordView,
    
  },
  {
    path: "/help-us-grow",
    name: "grow",
    component: GrowView,
    
  },
  {
    path: "/recipe/:recipeId",
    name: "recipe",
    component: RecipeByIdView,
    
  },
  
  {
    path: "/my-recipes",
    name: "my-recipes",
    component: MyRecipesView,
    meta: {
      requiresAuth: true
    }
    
  },
  {
    path: "/settings",
    name: "settings",
    component: UserSettingsView,
    meta: {
      requiresAuth: true
    }
    
  },
  {
    path: "/admin-tools",
    name: "admin-tools",
    component: AdminToolsView,
    meta: {
      requiresAuth: true
    }
    
  },

  
  
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;