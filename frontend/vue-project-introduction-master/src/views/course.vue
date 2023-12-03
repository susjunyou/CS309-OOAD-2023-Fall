<template>
  <div id="app">
    <el-row class="header-bar">
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
        </el-dropdown>      </el-col>
    </el-row>

    <el-row class="main-content">
      <el-aside :span="3" class="course-navbar" style="width: 205px">

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
          <el-menu-item index="7" @click="studentClick">查看members信息</el-menu-item>
          <el-menu-item index="8" @click="go('gradebook')">Gradebook</el-menu-item>
          <el-menu-item index="9" @click="logoutClick">LogOut</el-menu-item>
        </el-menu>


      </el-aside>

      <el-col :span="17" class="posts-container">
        <div v-for="post in posts" :key="post.id" class="post">
          <h3>{{ post.title }} 作者: {{ post.author }}</h3>
          <p>{{ post.content }}</p>
        </div>
      </el-col>

      <el-col :span="4" class="calendar-container">
        <div class="calendar-container">
          <v-calendar :attributes="attrs"></v-calendar>

          <div class="course-description">
            <h3>课程描述</h3>
            <p>{{ this.courseDescription }}</p>
          </div>
        </div>

      </el-col>
    </el-row>
    <el-dialog title="SA信息" :visible.sync="showSaDialog" width="60%">
      <div v-for="sa in saInfos" :key="sa.id" class="sa-info">
        <h3>{{ sa.name }}</h3>
        <p>邮箱: {{ sa.email }}</p>
        <p>专业: {{ sa.major }}</p>
        <p>自我介绍: {{ sa.selfIntroduction }}</p>
      </div>
    </el-dialog>

    <!-- 学生信息对话框 -->
    <el-dialog title="学生信息" :visible.sync="showStudentDialog" width="60%">
      <div v-for="student in studentInfos" :key="student.id" class="student-info">
        <h3>{{ student.name }}</h3>
        <p>邮箱: {{ student.email }}</p>
        <p>专业: {{ student.major }}</p>
        <p>自我介绍: {{ student.selfIntroduction }}</p>
      </div>
    </el-dialog>

  </div>
</template>




<script>

export default {

  data() {
    return {
      // 假设每个DDL是一个对象，包含日期和标题
      ddls: [
        // ...其他DDL
      ],
      attrs: [],
      // 初始化组件数据属性
      courses: [],
      posts: [],
      assignments: [],
      projects: [],
      materials: [],
      myValue: '',
      saInfos: [],
      studentInfos: [],
      showSaDialog: false, // 控制SA信息对话框的显示
      showStudentDialog: false, // 控制学生信息对话框的显示
      courseDescription:'',
    };
  },
  name: 'CourseNavbar',


  async created() {
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    await this.loadStudentsAndSA();
    this.myValue=localStorage.getItem("currentcourse");
    this.courseDescription=localStorage.getItem("getdescriptionbyid"+localStorage.getItem("currentcourseid"));
    this.attrs = this.ddls.map(ddl => ({
      key: ddl.date,
      dates: new Date(ddl.date),
      highlight: {
        contentClass: 'ddl-highlight', // 应用于内容的CSS类
      },
      popover: {
        label: ddl.title, // 弹出显示的信息
      },
    }));
    this.attrs.push({
      key: 'today',
      highlight: {
        contentClass: 'today-highlight', // 应用于当前日期的CSS类
      },
      dates: new Date(), // 当前日期
      popover: {
        label: 'Today', // 在这里添加你想要显示的文本
      },
    });
  },
  methods: {
    saClick() {
      this.showSaDialog = true;
    },
    studentClick() {
      this.$router.push('/members');
    },
    logoutClick() {
      this.$router.push('/Login');
      localStorage.clear();
    },
    goTo(route) {
// 假设使用 Vue Router 进行导航    goTo(route) {
// 假设使用 Vue Router 进行导航
      localStorage.setItem("currentcourseid",route.id);
      localStorage.setItem("currentcourse",route.title);
      this.myValue=route.title;
      this.loadLocalStorageData();
      this.loadStudentsAndSA();
    },
    go(route) {

      this.$router.push(route);
    },
    async loadStudentsAndSA(){
      this.saInfos = [];
      this.studentInfos = [];
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
          id: localStorage.getItem('coursesid' + i),
          title: localStorage.getItem('courses' + i),
          description: localStorage.getItem('courseDescription' + i),
          code: localStorage.getItem('coursecode' +i),
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
        this.ddls.push({
          date: this.assignments[i].ddl,
          title: this.assignments[i].title,
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
        this.ddls.push({
          date: this.projects[i].ddl,
          title: this.projects[i].title,
        });
      }
      console.log(this.projects[0])
      console.log(this.projects[1])
      console.log("course name="+this.myValue)
      console.log("assleng="+localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")))
      console.log("projectleng="+localStorage.getItem('projectsLength'+localStorage.getItem("currentcourse")))

    },

  },
}

</script>
<style scoped>


.sa-info {
  padding: 10px;
  border: 1px solid #ddd;
  margin-top: 20px;
}
.student-info {
  padding: 10px;
  border: 1px solid #ddd;
  margin-top: 20px;
}

.ddl-highlight {
  border: 2px solid red;
  border-radius: 50%;
}
.today-highlight {
  border: 2px solid blue;
  border-radius: 50%;
}


/* 为帖子添加样式 */

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
}

.header-bar {
  background-color: cornflowerblue;
  color: white;
  line-height: 60px;
  padding: 0 20px;
}

.main-content {
  display: flex;
}

.course-navbar {
  width: 200px;
  background-color: #f2f2f2;
  height: 100vh; /* 设置高度为视口的100% */
  overflow-y: auto; /* 如果内容太多可以滚动 */
}

.posts-container {
  margin-right: 10px; /* Adjust the margin as needed */
  padding: 10px;
  overflow: auto;
}

.post {
  text-align: left;
  border: 1px solid gainsboro;
  margin-bottom: 10px;
  padding: 10px;
}

.calendar-container {
  padding: 20px;
}
.course-description {
  margin-top: 20px; /* 与日历的间距 */
  padding: 10px;
  border: 1px solid #ccc; /* 描述框的边框 */
  background-color: #f9f9f9; /* 轻微背景颜色区分 */
}
</style>


