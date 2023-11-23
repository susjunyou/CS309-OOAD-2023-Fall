<template>
  <el-menu mode="horizontal" class="top_menu" text-color="#fff" background-color="cornflowerblue" >
    <el-menu-item v-for="course in courses" :key="course" @click="goTo(course.title)" >
      {{ course.title }}
    </el-menu-item>
  </el-menu>
</template>

<script setup>
export default {

  data() {
    return {
      courses: [],
      materials:[],
    };
  },


  async created() {
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
  },
  methods: {
    goTo(route) {
// 假设使用 Vue Router 进行导航
      localStorage.setItem("coursename",route)
      this.$router.push( "course" );
    },
    async loadLocalStorageData() {
      await new Promise((resolve) => setTimeout(resolve, 1000)); // 模拟异步操作，这里不是必要的，只是演示用例
      for (let i = 0; i < localStorage.getItem('length'); i++) {
        this.courses.push({
          id: i + 1,
          title: localStorage.getItem('courses' + i),
        });
      }
    },

  },
}
</script>


<style scoped>

</style>