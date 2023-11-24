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

    <div>
    <div v-for="post in posts" :key="post.id" class="post">
      <h3>{{ post.title }}</h3>
      <p>{{ post.content }}</p>
      <small>作者: {{ post.author }}</small>
    </div>
  </div>
    <div>
      <v-calendar :attributes="attrs"></v-calendar>
    </div>
<!--    <p>welcome to {{myValue}}</p>-->
<!--  </div>-->
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
    };
  },
  name: 'CourseNavbar',


  async created() {
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    this.myValue=localStorage.getItem("currentcourse");
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
    logoutClick() {
      this.$router.push('/Login');
      localStorage.clear();
    },
    goTo(route) {
// 假设使用 Vue Router 进行导航
      localStorage.setItem("currentcourse",route);
      this.myValue=route;
      this.loadLocalStorageData();
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
          id: i + 1,
          content: localStorage.getItem('post' + localStorage.getItem("currentcourse")+i),
          title: localStorage.getItem('posttitle' + localStorage.getItem("currentcourse")+i),
          author: localStorage.getItem('postauthor' + localStorage.getItem("currentcourse")+i),
        });
      }
      this.materials=[];

      for (let i = 0; i < localStorage.getItem('courseMaterialLength'+localStorage.getItem("currentcourse")); i++) {
        this.materials.push({
          id: i + 1,
          name: localStorage.getItem('materialname' + localStorage.getItem("currentcourse")+i),
          description: localStorage.getItem('materialdescription' + localStorage.getItem("currentcourse")+i),
        });
      }
      this.assignments=[];
      for (let i = 0; i < localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")); i++) {
        this.assignments.push({
          id: i + 1,
          status: localStorage.getItem('assignmentname' + localStorage.getItem("currentcourse")+i),
          title: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse")+i),
          description: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse")+i),
          ddl: localStorage.getItem('assignmentddl' + localStorage.getItem("currentcourse")+i),
        });
      }
      this.projects=[];
      for (let i = 0; i < localStorage.getItem('projectsLength'+localStorage.getItem("currentcourse")); i++) {
        this.projects.push({
          id: i + 1,
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
console.log("course name="+this.myValue)
      console.log("assleng="+localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")))
      console.log("projectleng="+localStorage.getItem('projectsLength'+localStorage.getItem("currentcourse")))

    },

  },
}

</script>
<style>
.top_menu{
  background-color: black;
  border-color: yellow;
  width:100%;

}
.course-navbar {
  border: none;
  width: 200px; /* 设置导航栏宽度 */
  float: left; /* 使导航栏浮动在左侧 */
  height: 100vh; /* 设置导航栏高度与视口高度相同 */
  padding-top: 20px; /* 在顶部添加一些内边距 */
}

.ddl-highlight {
  border: 2px solid red;
  border-radius: 50%;
}
.today-highlight {
  border: 2px solid blue;
  border-radius: 50%;
}


</style>

