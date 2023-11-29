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
import createTeam from "@/views/createTeam.vue";
import joinTeam from "@/views/joinTeam.vue";
import projectsubmit from "@/views/projectsubmit.vue";
import assignmentsubmit from "@/views/assignmentsubmit.vue";
import updatePassword from "@/views/updatePassword.vue";

const routes = [
    {path: '/login', component: Login},
    {path: '/register', component: Register},
    {path: '/purchase', component: Purchase},
    {path: '/StudentHomePage', component: StudentHomePage},
    {path: '/course',component: course},
    {path: '/materials',component: materials},
    {path: '/gradebook',component: gradebook},
    {path: '/projects',component: projects},
    {path: '/assignments',component: assignments},
    {path: '/createTeam',component: createTeam},
    {path: '/joinTeam',component: joinTeam},
    {path: '/assignmentsubmit',component: assignmentsubmit},
    {path: '/projectsubmit', component: projectsubmit},
    {path: '/updatePassword', component: updatePassword},
    {path: '/', redirect: '/login'}

]

export default new VueRouter({
    mode: 'history',
    routes
})
