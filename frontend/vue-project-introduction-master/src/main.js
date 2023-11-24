import Vue from 'vue'
import VCalendar from 'v-calendar';
import App from './App.vue'

import Vuex from "vuex";
import VueRouter from "vue-router";

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';

import router from '@/router'
import store from '@/store'
import axios from "axios";
Vue.use(VCalendar);
Vue.config.productionTip = false
Vue.use(Vuex)
Vue.use(VueRouter)
Vue.use(ElementUI)
const axiosInstance = axios.create({
    baseURL: 'http://localhost:8082', // 这里写你后端接口的地址
    timeout: 5000 // 设置超时时间
});


// 将 Axios 实例绑定到 Vue 实例的原型上，这样在组件中就可以直接使用 this.$axios 访问到
Vue.prototype.$axios = axiosInstance;
new Vue({
    el: '#app',
    store: store,
    router,
    render: h => h(App),
    components: {
        App
    }
}).$mount('#app')
