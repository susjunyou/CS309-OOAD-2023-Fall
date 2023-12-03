<template>
  <div>
    <el-row class="header-bar" background-color="#545c64" text-color="#fff">
      <el-col :span="15">
        <h1 class="header-title">Project Helper</h1>
      </el-col>
      <el-col :span="9">
        <el-dropdown trigger="click">
          <span class="el-dropdown-link">
            课程列表<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item v-for="course in courses" :key="course.id" @click.native="goTo(course)">
              {{ course.title }}
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
    </el-row>


<el-row>
  <el-col :span="3">
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
    </el-col>
  <el-col :span="21">
    <div class="table-section">
      <h2 class="table-title">Teacher Information</h2>
      <el-table :data="teachers" style="width: 100%;" border stripe>
        <el-table-column prop="name" label="Name"></el-table-column>
        <el-table-column prop="tenure" label="Tenure"></el-table-column>
        <el-table-column prop="email" label="Email"></el-table-column>
        <el-table-column prop="department" label="Department"></el-table-column>
      </el-table>
    </div>

    <!-- 学习助手(SA)信息表格 -->
    <div class="table-section">
      <h2 class="table-title">SA Information</h2>
      <el-table :data="saInfos" style="width: 100%;" border stripe>
        <el-table-column prop="name" label="Name"></el-table-column>
        <el-table-column prop="email" label="Email"></el-table-column>
        <el-table-column prop="major" label="Major"></el-table-column>
      </el-table>
    </div>

    <!-- 学生信息表格 -->
    <div class="table-section">
      <h2 class="table-title">Student Information</h2>
      <el-table :data="studentInfos" style="width: 100%;" border stripe>
        <el-table-column prop="name" label="Name"></el-table-column>
        <el-table-column prop="email" label="Email"></el-table-column>
        <el-table-column prop="major" label="Major"></el-table-column>
      </el-table>
    </div>
  </el-col>
</el-row>>
  </div>
</template>

<script >
export default {

  data() {
    return {
      courses: [],
      posts: [],
      assignments: [],
      projects: [],
      materials: [],
      ddls:[],
      saInfos: [],
      studentInfos: [],
      teachers:[],
      // myValue: '',
    };
  },


  async created() {
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    await this.loadStudentsAndSA();

  },
  methods: {

    logoutClick() {
      this.$router.push('/Login');
      localStorage.clear();
    },
    goTo(route) {
// 假设使用 Vue Router 进行导航
      localStorage.setItem("currentcourse",route.title);
      localStorage.setItem("currentcourseid",route.id);
      // this.myValue=route.title;
      this.loadLocalStorageData();
      this.$router.push({ path: '/course' });
    },
    submitproject(route) {
      localStorage.setItem("currentprojectid",route.id)
      this.$router.push('projectsubmit');
    },
    join(route) {
      localStorage.setItem("currentprojectid",route.id)
      this.$router.push('joinTeam');
    },
    go1(route) {
      console.log(route.id)
      localStorage.setItem("currentprojectid",route.id);
      localStorage.setItem("currentprojectmaxpeopleinteam",route.maxpeopleinteam);
      this.$router.push('createTeam');
    },
    go(route) {
      // localStorage.setItem("currentprojectid",route)
      this.$router.push(route);
    },
    async loadStudentsAndSA(){
      this.saInfos = [];
      this.studentInfos = [];
      this.teachers = [];
      await this.$axios.get('/course/getAllSA', {
        params: {
          courseId: localStorage.getItem('currentcourseid')
        }
      }).then((res) => {
        if (res.data.code === "0") {
          for (let i = 0; i < res.data.data.length; i++) {
            this.saInfos.push({
              email: res.data.data[i].email,
              name: res.data.data[i].name,
              id: res.data.data[i].id,
              major: res.data.data[i].major,
              selfIntroduction: res.data.data[i].selfIntroduction,
            })
          }
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });
      await this.$axios.get('/course/getTeacher', {
        params: {
          courseId: localStorage.getItem('currentcourseid')
        }
      }).then((res) => {
        if (res.data.code === "0") {
          for (let i = 0; i < res.data.data.length; i++) {
            this.teachers.push({
              email: res.data.data[i].email,
              name: res.data.data[i].name,
              id: res.data.data[i].id,
              department: res.data.data[i].department,
              selfIntroduction: res.data.data[i].selfIntroduction,
              tenure: res.data.data[i].tenure,
            })
          }
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });
      //加载学生信息
      await this.$axios.get('/course/getAllStudents', {
        params: {
          courseId: localStorage.getItem('currentcourseid')
        }
      }).then((res) => {
        if (res.data.code === "0") {
          for (let i = 0; i < res.data.data.length; i++) {
            this.studentInfos.push({
              email: res.data.data[i].email,
              name: res.data.data[i].name,
              id: res.data.data[i].id,
              major: res.data.data[i].major,
              selfIntroduction: res.data.data[i].selfIntroduction,
            })
          }
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
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
        this.ddls=[];
        this.ddls.push({
          date: this.projects[i].ddl,
          title: this.projects[i].title,
        });
      }

    },  },
}
</script>


<style scoped>
.clickable-text{
  text-decoration: underline; /* 添加下划线 */
  color: blue; /* 设置为蓝色或其他突出的颜色 */
  cursor: pointer; /* 鼠标悬停时显示手形光标 */
  .clickable-text:hover {
    color: darkblue; /* 悬停时改变颜色 */
  }
}
.header-bar {
  background-color: cornflowerblue;
  color: #fff;
  line-height: 60px; /* 根据需要调整高度 */
  padding: 0 20px; /* 根据需要调整内边距 */
}
.header-title {
  text-align: right; /* 将文本对齐到右边 */
  padding-right: 100px; /* 或者您需要的任何值，以便向右移动标题 */
}
.header-bar h1 {
  margin: 0; /* 移除默认的margin */
}
.course-navbar {
   width: 200px;
   background-color: #f2f2f2;
   height: 100vh; /* 设置高度为视口的100% */
   overflow-y: auto; /* 如果内容太多可以滚动 */
 }
.table-section {
  margin-bottom: 30px; /* 表格间距 */
}

.table-title {
  font-size: 20px;
  color: #333;
  margin-bottom: 10px; /* 标题和表格之间的间距 */
}

</style>