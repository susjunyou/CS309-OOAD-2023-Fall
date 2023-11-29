<template>
  <div>
    <el-menu mode="horizontal" class="top_menu" text-color="#fff" background-color="cornflowerblue" >
      <el-menu-item v-for="course in courses" :key="course" @click="goTo(course.title)" >
        {{ course.title }}
      </el-menu-item>
    </el-menu>



    <el-menu
        class="course-navbar"
        mode="vertical"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b">
      <el-menu-item index="1" @click="go('StudentHomePage')">Home</el-menu-item>
      <el-menu-item index="2" @click="go('course')">Post</el-menu-item>
      <el-menu-item index="3" @click="go('materials')">Materials</el-menu-item>
      <el-menu-item index="4" @click="go('assignments')">Assignments</el-menu-item>
      <el-menu-item index="5" @click="go('projects')">Projects</el-menu-item>
      <el-menu-item index="6" @click="go('gradebook')">Gradebook</el-menu-item>
      <el-menu-item index="7" @click="logoutClick">LogOut</el-menu-item>
    </el-menu>
    <!--  <div>-->

      <!-- 表单和输入字段 -->
      <input v-model="email" type="email" placeholder="Email">
      <input v-model="phoneNumber" type="text" placeholder="PhoneNumber">
      <textarea v-model="selfIntroduction" placeholder="SelfIntroduction"></textarea>
      <button @click.prevent="updateStudentDetails">Update Details</button>
    <div v-if="isPopupVisible" class="popup">
      <div class="popup-content">
        <p>修改成功！</p>
        <button @click="returnTohomepage">关闭</button>
      </div>
    </div>

  </div>
</template>

<script >
export default {

  data() {
    return {
      // 初始化组件数据属性
      courses: [],
      posts: [],
      assignments: [],
      projects: [],
      materials: [],
      myValue: '',
      id: null,
      email: '',
      phoneNumber: '',
      selfIntroduction: '',
      isPopupVisible: false, // 控制弹窗显示的布尔值

    };
  },


  async created() {
    this.id=localStorage.getItem('id');
    // this.email=localStorage.getItem('email');
    this.phoneNumber=localStorage.getItem('phoneNumber');

    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    await this.loadinfo();
    this.myValue=localStorage.getItem("currentcourse")
    this.email=localStorage.getItem('email');
    this.selfIntroduction=localStorage.getItem('selfIntroduction');
  },
  methods: {
    returnTohomepage(){
      this.isPopupVisible = false;
      this.$router.push('/StudentHomePage');
    },
    async updateStudentDetails(){
      await this.$axios.get('/student/updateStudentDetails', {
        params: {
          id:Number(this.id),
          email:String(this.email),
          phoneNumber:String(this.phoneNumber),
          selfIntroduction:String(this.selfIntroduction),
        }
      }).then((res) => {
        if (res.data.code === "0") {
          this.isPopupVisible = true;
        }
      }).catch(error => {
        console.error('Error loading course posts:', error);
      });
    },
    logoutClick() {
      this.$router.push('/Login');
      localStorage.clear();
    },
    goTo(route) {
// 假设使用 Vue Router 进行导航
      localStorage.setItem("currentcourse",route);
      this.myValue=route;
      this.loadLocalStorageData();
      this.$router.push({ path: '/course' });
    },
    go(route) {

      this.$router.push(route);
    },


    async loadinfo() {
      await new Promise((resolve) => setTimeout(resolve, 10)); // 模拟异步操作，这里不是必要的，只是演示用例
      await this.$axios.get('/student/getStudent', {
        params: {
          id:Number(localStorage.getItem('id')),
        }
      }).then((res) => {
        if (res.data.code === "0") {
localStorage.setItem('email',res.data.data.email);
console.log(res.data.data.email);
localStorage.setItem('selfIntroduction',res.data.data.selfIntroduction);
console.log(res.data.data.selfIntroduction);
        }
      }).catch(error => {
        console.error('Error loading student info', error);
      });
    },



    async loadLocalStorageData() {
      await new Promise((resolve) => setTimeout(resolve, 10)); // 模拟异步操作，这里不是必要的，只是演示用例
      this.courses=[];
      for (let i = 0; i < localStorage.getItem('length'); i++) {
        this.courses.push({
          id: i + 1,
          title: localStorage.getItem('courses' + i),
        });
      }
      this.posts=[];
      for (let i = 0; i < localStorage.getItem('coursePostLength'+localStorage.getItem("currentcourse")); i++) {
        this.posts.push({
          id: localStorage.getItem('postid' + localStorage.getItem("currentcourse")+i),
          content: localStorage.getItem('post' + localStorage.getItem("currentcourse")+i),
          title: localStorage.getItem('posttitle' + localStorage.getItem("currentcourse")+i),
          author: localStorage.getItem('postauthor' + localStorage.getItem("currentcourse")+i),
        });
      }
      this.materials=[];

      for (let i = 0; i < localStorage.getItem('courseMaterialLength'+localStorage.getItem("currentcourse")); i++) {
        this.materials.push({
          id:localStorage.getItem('materialid' + localStorage.getItem("currentcourse")+i),
          name: localStorage.getItem('materialname' + localStorage.getItem("currentcourse")+i),
          description: localStorage.getItem('materialdescription' + localStorage.getItem("currentcourse")+i),
        });
      }
      this.assignments=[];
      for (let i = 0; i < localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")); i++) {
        this.assignments.push({
          id: localStorage.getItem('assignmentid' + localStorage.getItem("currentcourse")+i),
          status: localStorage.getItem('assignmentname' + localStorage.getItem("currentcourse")+i),
          title: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse")+i),
          description: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse")+i),
          ddl: localStorage.getItem('assignmentddl' + localStorage.getItem("currentcourse")+i),
        });
      }
      this.projects=[];
      for (let i = 0; i < localStorage.getItem('projectsLength'+localStorage.getItem("currentcourse")); i++) {
        this.projects.push({
          id: localStorage.getItem('projectid' + localStorage.getItem("currentcourse")+i),
          title: localStorage.getItem('projecttitle' + localStorage.getItem("currentcourse")+i),
          description: localStorage.getItem('projectdescription' + localStorage.getItem("currentcourse")+i),
          startdate: localStorage.getItem('projectstartdate' + localStorage.getItem("currentcourse")+i),
          ddl: localStorage.getItem('projectddl' + localStorage.getItem("currentcourse")+i),
          status: localStorage.getItem('projectstatus' + localStorage.getItem("currentcourse")+i),
          maxpeopleinteam: localStorage.getItem('maxpeopleinteam' + localStorage.getItem("currentcourse")+i),
        });

      }
      console.log("course name="+this.myValue)
      console.log("assleng="+localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")))
      console.log("projectleng="+localStorage.getItem('projectsLength'+localStorage.getItem("currentcourse")))

    },
  },
}
</script>


<style scoped>
.popup {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.popup-content {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}
</style>