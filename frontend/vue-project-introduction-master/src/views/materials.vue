<template>
  <div>
  <el-menu mode="horizontal" class="top_menu" text-color="#fff" background-color="cornflowerblue" >
    <el-menu-item v-for="course in courses" :key="course" @click="goTo(course.title)" >
      {{ course.title }}
    </el-menu-item>
  </el-menu>

  <div>
    <div v-for="material in materials" :key="material.id" class="material">
      <h3>{{ material.name }}</h3>
      <p>{{ material.description }}</p>
    </div>
  </div>
  </div>

</template>

<script setup>
export default {

  data() {
    return {
      // 初始化组件数据属性
      courses: [],
      posts: [],
      assignments: [],
      projects: [],
      materials: [],
      myValue: '',    };
  },


  async created() {
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    this.myValue=localStorage.getItem("currentcourse")
  },
  methods: {
    goTo(route) {
// 假设使用 Vue Router 进行导航
      localStorage.setItem("currentcourse",route);
      this.myValue=route;
      this.loadLocalStorageData();
      this.$router.push({ path: '/course' });
    },
    async loadLocalStorageData() {
      await new Promise((resolve) => setTimeout(resolve, 1000)); // 模拟异步操作，这里不是必要的，只是演示用例
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

      for (let i = 0; i < localStorage.getItem('courseMaterialsLength'+localStorage.getItem("currentcourse")); i++) {
        this.materials.push({
          id: i + 1,
          name: localStorage.getItem('materialname' + localStorage.getItem("currentcourse")+i),
          description: localStorage.getItem('materialdescription' + localStorage.getItem("currentcourse")+i),
        });
      }
      this.assignments=[];
      for (let i = 0; i < localStorage.getItem('courseAssignmentsLength'+localStorage.getItem("currentcourse")); i++) {
        this.assignments.push({
          id: i + 1,
          status: localStorage.getItem('assignmentname' + localStorage.getItem("currentcourse")+i),
          title: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse")+i),
          description: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse")+i),
          ddl: localStorage.getItem('assignmentddl' + localStorage.getItem("currentcourse")+i),
        });
      }
      this.projects=[];
      for (let i = 0; i < localStorage.getItem('courseProjectsLength'+localStorage.getItem("currentcourse")); i++) {
        this.projects.push({
          id: i + 1,
          title: localStorage.getItem('projecttitle' + localStorage.getItem("currentcourse")+i),
          description: localStorage.getItem('projectdescription' + localStorage.getItem("currentcourse")+i),
          startdate: localStorage.getItem('projectstartdate' + localStorage.getItem("currentcourse")+i),
          ddl: localStorage.getItem('projectddl' + localStorage.getItem("currentcourse")+i),
          status: localStorage.getItem('projectstatus' + localStorage.getItem("currentcourse")+i),
          maxpeopleinteam: localStorage.getItem('maxpeopleinteam' + localStorage.getItem("currentcourse")+i),
        });
      }


    },

  },
}
</script>


<style scoped>

</style>