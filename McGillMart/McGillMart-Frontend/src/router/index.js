import Vue from 'vue'
import Router from 'vue-router'
import ViewProfile from '@/components/ViewProfile'
import VueCookies from 'vue-cookies'
import Home from '@/components/Home'
import SignIn from '@/components/SignIn'
import Registration from '@/components/Registration'
import ViewCart from '@/components/ViewCart'

Vue.use(Router);
Vue.use(VueCookies);

export default new Router({
  routes: [
    {
      path: "/",
      name: "Home",
      component: Home,
    },
    {
      path: "/login",
      name: "McGillMart Login page",
      component: SignIn,
    },
    {
      path: "/registration",
      name: "Registration",
      component: Registration,
    },
    {
      path: '/my-profile',
      name: 'View Profile',
      component: ViewProfile,
    },
    {
      path: '/my-cart',
      name: 'View Cart',
      component: ViewCart
    }
  ]
})

