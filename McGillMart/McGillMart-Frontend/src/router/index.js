import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ViewProfile from '@/components/ViewProfile'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/profile/view',
      name: 'ViewProfile',
      component: ViewProfile
    }
  ]
})
