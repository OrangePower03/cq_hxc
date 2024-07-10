import {createRouter, createWebHistory} from 'vue-router';
import ProductList from '../views/ProductList.vue';
import Shop from '../views/Shop.vue';
import UserProfile from '../views/UserProfile.vue';
import Cart from '../views/Cart.vue';
import Login from "../views/Login.vue";
import AuthLayout from "../layouts/AuthLayout.vue";

const routes = [
  {
    path: '/',
    component: AuthLayout,
    children: [
      { path: '', component: Login },
      { path: 'login', component: Login }
    ]
  },
  {
    path: '/',
    component: () => import('../layouts/MainLayout.vue'),  // 创建MainLayout
    children: [
      { path: 'products', component: ProductList },
      { path: 'shop', component: Shop },
      { path: 'profile', component: UserProfile },
      { path: 'cart', component: Cart }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});
router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem('token'); // 这里假设用localStorage存储token
  if (to.path !== '/' && to.path !== '/login' && !isAuthenticated) {
    next('/login');
  } else {
    next();
  }
});

export default router;
