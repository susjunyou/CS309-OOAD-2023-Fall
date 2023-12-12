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
import personInformation from "@/views/PersonInformation.vue";
import members from "@/views/members.vue";
import post from "@/views/post.vue";
import postReply from "@/views/postReply.vue";
import teacherhomepage from "@/views/teacherhomepage.vue";
import postofteacher from "@/views/postofteacher.vue"
import projectsofteacher from "@/views/projectsofteacher.vue"
import membersofteacher from "@/views/membersofteacher.vue"
import materialsofteacher from "@/views/materialsofteacher.vue"
import courseofteacher from "@/views/courseofteacher.vue";
import assignmentsofteacher from "@/views/assignmentsofteacher.vue";
import pizuoye from "@/views/pizuoye.vue";
import judgeassignment from "@/views/judgeassignment.vue";
import piproject from "@/views/piproject.vue";
import judgeproject from "@/views/judgeproject.vue";
import teamsofteacher from "@/views/teamsofteacher.vue";
import modifyinformation from "@/views/modifyinformation.vue";
import assignteam from "@/views/assignteam.vue";
import gradebookofteacher from "@/views/gradebookofteacher.vue";
import attendancegradebookofteacher from "@/views/attendancegradebookofteacher.vue";
import assignmentgradebookofteacher from "@/views/assignmentgradebookofteacher.vue";
import projectgradebookofteacher from "@/views/projectgradebookofteacher.vue";
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
    {path: '/personInformation', component: personInformation},
    {path: '/members', component: members},
    {path: '/post', component: post},
    {path: '/postReply', component: postReply},
    {path: '/teacherhomepage',component: teacherhomepage},
    {path: '/postofteacher',component: postofteacher},
    {path: '/projectsofteacher',component: projectsofteacher},
    {path: '/membersofteacher', component: membersofteacher},
    {path: '/materialsofteacher', component: materialsofteacher},
    {path: '/courseofteacher', component: courseofteacher},
    {path: '/assignmentsofteacher', component: assignmentsofteacher},
    {path: '/pizuoye', component: pizuoye},
    {path: '/judgeassignment', component: judgeassignment},
    {path: '/piproject', component: piproject},
    {path: '/judgeproject', component: judgeproject},
    {path: '/teamsofteacher', component: teamsofteacher},
    {path: '/modifyinformation', component:modifyinformation},
    {path: '/assignteam', component: assignteam},
    {path:'/gradebookofteacher',component: gradebookofteacher},
    {path: '/attendancegradebookofteacher',component:attendancegradebookofteacher},
    {path: '/assignmentgradebookofteacher',component: assignmentgradebookofteacher},
    {path: '/projectgradebookofteacher',component: projectgradebookofteacher},
    {path: '/', redirect: '/login'}

]

export default new VueRouter({
    mode: 'history',
    routes
})
