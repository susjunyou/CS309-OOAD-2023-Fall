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

    <div class="attendance-list">
      <hr class="separator">
      <h1>出勤记录</h1>
      <ul>
        <li>
          <table>
            <thead>
            <tr>
              <th>时间</th>
              <th>分数</th>
              <th>比例</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="attendance in attendances" :key="attendance.data">
              <td>{{ attendance.date }}</td>
              <td>{{ attendance.attendancesgrade }}</td>
              <td>{{ attendance.proportion }}</td>
            </tr>
            </tbody>
          </table>
        </li>
      </ul>
      <h1>作业成绩</h1>
      <ul>
        <li>
          <table>
            <thead>
            <tr>
              <th>名称</th>
              <th>分数</th>
              <th>比例</th>
              <th>备注</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="assignment in assignments" :key="assignment.title">
              <td>{{ assignment.title }}</td>
              <td>{{assignment.assignmentsgrade}}</td>
              <td>{{ assignment.proportion }}</td>
              <td>{{ assignment.gardedescription }}</td>
            </tr>
            </tbody>
          </table>
        </li>
      </ul>
      <hr class="separator">
      <h1>项目成绩</h1>
      <ul>
        <li>
          <table>
            <thead>
            <tr>
              <th>时间</th>
              <th>分数</th>
              <th>比例</th>
              <th>备注</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="project in projects" :key="project.title">
              <td>{{ project.title }}</td>
              <td>{{ project.projectsgrade }}</td>
              <td>{{ project.proportion }}</td>
              <td>{{ project.gardedescription }}</td>
            </tr>
            </tbody>
          </table>
        </li>
      </ul>
    </div>

    </div>
</template>

<script >
export default {

  data() {
    return {
      courses: [],
      attendances:[],
      assignments:[],
      projects:[]
    };
  },


  async created() {
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
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
          title: localStorage.getItem('assignmenttitle' + localStorage.getItem("currentcourse")+i),
          description: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse")+i),
          ddl: localStorage.getItem('assignmentddl' + localStorage.getItem("currentcourse")+i),
          assignmentsgrade: localStorage.getItem('assignmentgrade' + localStorage.getItem("currentcourse")+i)+"/"+localStorage.getItem('assignmentmaxScore' + localStorage.getItem('currentcourse') + i),
          proportion: localStorage.getItem('assignmentproportion' + localStorage.getItem("currentcourse")+i),
          gardedescription: localStorage.getItem('assignmentgradeDescription' + localStorage.getItem("currentcourse")+i)
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
          projectsgrade: localStorage.getItem('projectgrade' + localStorage.getItem("currentcourse")+i)+"/"+localStorage.getItem('projectmaxScore' + localStorage.getItem("currentcourse") + i),
          proportion: localStorage.getItem('projectproportion' + localStorage.getItem("currentcourse")+i),
          gardedescription: localStorage.getItem('projectgradeDescription' + localStorage.getItem("currentcourse")+i)
        });
        this.ddls =[]
        this.ddls.push({
          date: this.projects[i].ddl,
          title: this.projects[i].title,
        });
      }

      for (let i = 0; i < localStorage.getItem('attendancesLength'+localStorage.getItem("currentcourse")); i++) {
        this.attendances.push({
          date: localStorage.getItem('attendancedate' + localStorage.getItem("currentcourse")+i),
          attendancesgrade: localStorage.getItem('attendancegrade' + localStorage.getItem("currentcourse")+i)+'/'+localStorage.getItem('attendancemaxScore' + localStorage.getItem("currentcourse") + i),
          proportion:localStorage.getItem('attendanceproportion' + localStorage.getItem("currentcourse")+i)
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
.attendance-list h2 {
  margin-bottom: 0.5em;
}

.attendance-list ul {
  list-style-type: none;
  padding: 0;
}

.attendance-list li {
  background: #f3f3f3;
  border: 1px solid #ddd;
  margin-bottom: 0.5em;
  padding: 0.5em;
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

.attendance-list li {
  background: #f3f3f3;
  border: 1px solid #ddd;
  margin-bottom: 0.5em;
  padding: 0.5em;
}
.attendance-list {
  margin: 20px; /* 设置外边距 */
  padding: 20px; /* 设置内边距 */
  border: 1px solid #ccc; /* 设置边框 */
  border-radius: 8px; /* 设置边框圆角 */
  font-family: Arial, sans-serif; /* 设置字体 */
}

.attendance-list h1 {
  font-size: 1.5em; /* 调整标题字体大小 */
  margin-bottom: 1em; /* 调整标题底部间距 */
}

.attendance-list table {
  width: 100%; /* 表格宽度占满容器 */
  border-collapse: collapse; /* 边框合并 */
  margin-bottom: 1em; /* 调整表格底部间距 */
}

.attendance-list th, .attendance-list td {
  border: 1px solid #ccc; /* 设置单元格边框 */
  padding: 8px; /* 设置单元格内边距 */
  text-align: left; /* 文本左对齐 */
}

.attendance-list th {
  background-color: #f2f2f2; /* 表头背景色 */
}

.attendance-list tbody tr:nth-child(even) {
  background-color: #f9f9f9; /* 偶数行背景色 */
}
.main-container {
  position: relative;
}

.attendance-list {
  position: absolute;
  width: 80%;
  left: 200px; /* 右移 50px，根据需要调整 */
}

</style>