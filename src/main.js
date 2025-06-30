import Vue from 'vue'
import App from './App.vue'
import router from './router'

Vue.config.productionTip = false
Vue.prototype.$ipLocation = ''

new Vue({
  router, // 注入路由
  render: h => h(App)
}).$mount('#app')
