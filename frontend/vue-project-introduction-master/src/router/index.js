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
import adminhomepage from "@/views/adminhomepage.vue";
import courseofadmin from "@/views/courseofadmin.vue";
import memberadmin from "@/views/memberadmin.vue";
import attendancegradebookofteacher from "@/views/attendancegradebookofteacher.vue";
import assignmentgradebookofteacher from "@/views/assignmentgradebookofteacher.vue";
import projectgradebookofteacher from "@/views/projectgradebookofteacher.vue";
import sacourse from "@/views/sacourse.vue";
import updatePasswordofteacher from "@/views/updatePasswordofteacher.vue";
import anouncementofteacher from "@/views/anouncementofteacher.vue";
import assignmentssa from "@/views/assignmentssa.vue";
import assignteamsa from "@/views/assignteamsa.vue";
import attendancegradebooksa from "@/views/attendancegradebooksa.vue";
import gradebooksa from "@/views/gradebooksa.vue";
import judgeassignmentsa from "@/views/judgeassignmentsa.vue";
import judgeprojectsa from "@/views/judgeprojectsa.vue";
import materialssa from "@/views/materialssa.vue";
import memberssa from "@/views/memberssa.vue";
import modifyinformationsa from "@/views/modifyinformationsa.vue";
import piprojectsa from "@/views/piprojectsa.vue";
import pizuoyesa from "@/views/pizuoyesa.vue";
import postsa from "@/views/postsa.vue";
import projectssa from "@/views/projectssa.vue";
import projectgradebooksa from "@/views/projectgradebooksa.vue";
import teamssa from "@/views/teamssa.vue";
import anouncement from "@/views/anouncement.vue";
import middle from "@/views/middle.vue";
import postreplyofteacher from "@/views/postreplyofteacher.vue";
import anouncementsa from "@/views/anouncementsa.vue";
import postreplysa from "@/views/postreplysa.vue";
import middle2 from "@/views/middle2.vue";
import assignmentgradebooksa from "@/views/assignmentgradebooksa.vue"
import registerphone from "@/views/registerphone.vue";
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
    {path: '/adminhomepage', component: adminhomepage},
    {path: '/courseofadmin', component:courseofadmin},
    {path:'/memberadmin',component: memberadmin},
    {path: '/attendancegradebookofteacher',component:attendancegradebookofteacher},
    {path: '/assignmentgradebookofteacher',component: assignmentgradebookofteacher},
    {path: '/projectgradebookofteacher',component: projectgradebookofteacher},
    {path:'/sacourse',component: sacourse},
    {path: '/updatePasswordofteacher', component: updatePasswordofteacher},
    {path: '/anouncementofteacher',component: anouncementofteacher},
    {path: '/assignmentssa',component: assignmentssa},
    {path: '/assignteamsa',component: assignteamsa},
    {path: '/attendancegradebooksa',component: attendancegradebooksa},
    {path: '/gradebooksa',component: gradebooksa},
    {path: '/judgeassignmentsa',component: judgeassignmentsa},
    {path: '/judgeprojectsa',component: judgeprojectsa},
    {path: '/materialssa',component: materialssa},
    {path: '/memberssa',component: memberssa},
    {path: '/modifyinformationsa',component: modifyinformationsa},
    {path: '/piprojectsa',component: piprojectsa},
    {path: '/pizuoyesa',component: pizuoyesa},
    {path: '/postsa',component: postsa},
    {path: '/projectssa',component: projectssa},
    {path: '/projectgradebooksa',component: projectgradebooksa},
    {path: '/teamssa',component: teamssa},
    {path: '/anouncement',component: anouncement},
    {path: '/middle',component: middle},
    {path: '/postreplyofteacher',component: postreplyofteacher},
    {path: '/anouncementsa',component: anouncementsa},
    {path: '/postreplysa',component: postreplysa},
    {path: '/assignmentgradebooksa',component: assignmentgradebooksa},
    {path: '/middle2',component: middle2},
    {path: '/registerphone',component: registerphone},
    {path: '/', redirect: '/login'}

]

export default new VueRouter({
    mode: 'history',
    routes
})
