import Vue from 'vue'
import Router from 'vue-router'
import ViewProfile from '@/components/ViewProfile'
import VueCookies from 'vue-cookies'
import Home from '@/components/Home'
import SignIn from '@/components/SignIn'
import Registration from '@/components/Registration'
import ViewCart from '@/components/ViewCart'
import OrderHistory from '@/components/OrderHistory.vue'
import EditProfile from '@/components/EditProfile.vue'
import Settings from '@/components/Settings'
import Review from '@/components/Review'

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
      path: '/profile/view',
      name: 'View Profile',
      component: ViewProfile,
    },
    {
      path: '/my-cart',
      name: 'View Cart',
      component: ViewCart,
    },
    {
      path: '/profile/order_history',
      name: 'Order History',
      component: OrderHistory,
    },
    {

      path: '/profile/edit',
      name: 'Edit Profile',
      component: EditProfile,
    },
    {
      path: '/review',
      name: 'Review',
      component: Review,
    }
  ]
})

