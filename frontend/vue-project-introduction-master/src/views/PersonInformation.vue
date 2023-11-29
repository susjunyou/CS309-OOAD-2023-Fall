<template>
  <div id="app">

    <el-menu mode="horizontal" class="top_menu" text-color="#fff" background-color="cornflowerblue" >
      <el-menu-item v-for="course in courses" :key="course" @click="goTo(course)" >
        {{ course.title }}
      </el-menu-item>
    </el-menu>




    <!-- 主内容区 -->
    <el-container class="class_lists">
      <!-- 侧边栏：课程列表 -->
      <el-aside width="120px">
        <el-menu mode="horizontal" class="top_menu">
          <el-menu-item index="2" @click="go('StudentHomePage')">Home</el-menu-item>
          <el-menu-item index="7" @click="goTo('settings')">Set</el-menu-item>
          <el-menu-item index="8" @click="goTo('help')">Help</el-menu-item>
          <el-menu-item index="9" @click="go('PersonInformation')">PI</el-menu-item>
          <el-menu-item index="10" @click="logoutClick">LogOut</el-menu-item>
        </el-menu>
      </el-aside>


      <!-- 右侧内容区 -->
      <el-main>
        <!-- 使用flex布局 -->
        <div class="content-wrapper">
          <!-- 帖子列表 -->
          <div class="posts-wrapper">
            <div v-for="post in posts" :key="post.id" class="post">

              <h2>课程：{{ post.course }}</h2>
              <h3>{{ post.title }}</h3>
              <p>{{ post.content }}</p>
              <small>作者: {{ post.author }}</small>
            </div>
          </div>

          <!-- 日历 -->
          <div class="rili2">
            <v-calendar :attributes="attrs"></v-calendar>
          </div>
        </div>
      </el-main>

    </el-container>

  </div>
</template>

<script setup>
export default {

  data() {
    return {
      courses: [],
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
      await new Promise((resolve) => setTimeout(resolve, 10)); // 模拟异步操作，这里不是必要的，只是演示用例
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