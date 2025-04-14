import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false

//导入element ui
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI)

//导入vuex
// import store from './store/store';

//引入路由
import router from '@/router/router'


//引入animate.css
import animated from "animate.css"
//Vue使用动画库
Vue.use(animated);


//导入axios
import axios from 'axios'
//挂载一个自定义变量
Vue.prototype.$http = axios


//导入vue_resource
import VueResource from 'vue-resource'
//vue使用vue_resource插件
Vue.use(VueResource);


new Vue({
  router,//挂载路由
  // store, //store对象挂载到vm实例中
  render: h => h(App),
}).$mount('#app')

