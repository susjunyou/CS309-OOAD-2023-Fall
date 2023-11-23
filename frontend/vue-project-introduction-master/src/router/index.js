import VueRouter from 'vue-router'
import Login from "@/views/Login";
import Register from "@/views/Register";
import Purchase from "@/views/Purchase";
import StudentHomePage from "@/views/StudentHomePage.vue";
import course from "@/views/course.vue";
import materials from "@/views/materials.vue";
import gradebook from "@/views/gradebook.vue";
import projects from "@/views/projects.vue";
import assignments from "@/views/assignments.vue";

const routes = [
    {path: '/login', component: Login},
    {path: '/register', component: Register},
    {path: '/purchase', component: Purchase},
    {path: '/StudentHomePage', component: StudentHomePage},
    {path: '/course',component: course},
    // {path: '/PersonInformation',component: PersonInformation},
    {path: '/materials',component: materials},
    {path: '/gradebook',component: gradebook},
    {path: '/projects',component: projects},
    {path: '/assignments',component: assignments},
    {path: '/', redirect: '/login'}

]

export default new VueRouter({
    mode: 'history',
    routes
})
