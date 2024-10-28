import Vue from 'vue'
import Router from 'vue-router'
import VueCookies from 'vue-cookies'
import Home from '@/components/Home'
import SignIn from '@/components/SignIn'
import Registration from '@/components/Registration'
import Settings from '@/components/Settings'

Vue.use(Router);
Vue.use(VueCookies);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    }
  ]
})

