<template>
  <div>

    <el-menu mode="horizontal" class="top_menu" text-color="#fff" background-color="cornflowerblue" >
      <el-menu-item v-for="course in courses" :key="course" @click="goTo(course)" >
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
    <div class="center-container">
    <div class="form-container">
      <form >
        <div class="form-group">
          <input type="text" v-model="team.title" placeholder="团队标题" class="form-control" />
        </div>
        <div class="form-group">
          <input type="text" v-model="team.description" placeholder="团队描述" class="form-control" />
        </div>
        <div class="form-group">
          <select v-model="team.teamSize" class="form-control">
            <option disabled value="0">请选择团队大小</option>
            <option v-for="number in maxpeople" :key="number" :value="number">{{ number }}</option>
          </select>
        </div>
        <button type="submit" class="submit-btn" @click.prevent="createTeam">创建团队</button>
      </form>
    </div>
    </div>
    <div v-if="isPopupVisible" class="popup">
      <div class="popup-content">
        <p>团队创建成功！</p>
        <button @click="returnToprotects">关闭</button>
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
      team: {
        title: '',
        description: '',
        teamSize: 0
      },
      maxpeople:0,
      projectid:0,
      sid:0,
      isPopupVisible: false, // 控制弹窗显示的布尔值
    };
  },


  async created() {
    this.maxpeople = Number(localStorage.getItem("currentprojectmaxpeopleinteam"));
    this.projectid=localStorage.getItem("currentprojectid");
    this.sid=localStorage.getItem("id");
    this.myValue=localStorage.getItem("currentcourse")
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成

  },
  methods: {
    returnToprotects(){
       this.isPopupVisible = false;
      this.$router.push('/projects');
    },
  async  createTeam() {
    console.log(this.team);
    console.log("projectid="+this.projectid);
    console.log(this.sid);
    await this.$axios.get('/team/create', {
        params: {
          teamName: String(this.team.title),
          teamDescription: String(this.team.description),
          teamSize: Number(this.team.teamSize),
          projectId:Number(this.projectid),
          leader:Number(this.sid),
        }
      }).then((res) => {
        console.log("code====================================="+res.data.code)
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
      localStorage.setItem("currentcourse",route.title);
      localStorage.setItem("currentcourseid",route.id);
      this.myValue=route.title;
      this.$router.push({ path: '/course' });
    },
    go(route) {

      this.$router.push(route);
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
        // this.ddls.push({
        //   date: this.projects[i].ddl,
        //   title: this.projects[i].title,
        // });
      }
      console.log("course name="+this.myValue)
      console.log("assleng="+localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")))
      console.log("projectleng="+localStorage.getItem('projectsLength'+localStorage.getItem("currentcourse")))

    },

  },
}
</script>


<style scoped>
.center-container {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  height: 100vh; /* 使容器占满整个视口高度 */
}

.form-container {
  max-width: 700px;
  margin: 0 auto;
  padding: 200px;
  background-color: #f5f5f5; /* 浅灰色背景 */
  box-shadow: 0 4px 8px rgba(0.2, 0.5, 0.5, 0.3); /* 可选：为表单添加阴影效果 */
}

.form-group {
  margin-bottom: 15px;
}

.form-control {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.submit-btn {
  padding: 10px 15px;
  font-size: 16px;
  color: white;
  background-color: cornflowerblue;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
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
.submit-btn:hover {
  background-color: royalblue;
}
</style>