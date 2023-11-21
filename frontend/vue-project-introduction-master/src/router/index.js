import VueRouter from 'vue-router'
import Login from "@/views/Login";
import Register from "@/views/Register";
import Purchase from "@/views/Purchase";
import StudentHomePage from "@/views/StudentHomePage.vue";

const routes = [
    {path: '/login', component: Login},
    {path: '/register', component: Register},
    {path: '/purchase', component: Purchase},
    {path: '/StudentHomePage', component: StudentHomePage},
    {path: '/', redirect: '/login'}
]

export default new VueRouter({
    mode: 'history',
    routes
})
