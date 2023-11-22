<template>
  <div id="app">
    <!-- 顶部导航条 -->
    <el-menu mode="horizontal" class="top_menu">
      <el-menu-item index="1">SUSTech Blackboard</el-menu-item>
      <el-menu-item index="2" @click="goTo('home')">首页</el-menu-item>
      <el-menu-item index="3" @click="goTo('courses')">课程</el-menu-item>
      <el-menu-item index="4" @click="goTo('assignments')">作业</el-menu-item>
      <el-menu-item index="5" @click="goTo('grades')">成绩</el-menu-item>
      <el-menu-item index="6" @click="goTo('messages')">消息</el-menu-item>
      <el-menu-item index="7" @click="goTo('settings')">设置</el-menu-item>
      <el-menu-item index="8" @click="goTo('help')">帮助</el-menu-item>
    </el-menu>

    <!-- 主内容区 -->
    <el-container class="class_lists">
      <!-- 侧边栏：课程列表 -->
      <el-aside width="300px">
        <el-input placeholder="搜索课程"></el-input>
        <el-menu>
          <el-menu-item v-for="course in courses" :key="course.id">
            {{ course.title }}
          </el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 右侧内容区 -->
      <el-main>
        <!-- 右侧内容区的其它内容 -->
      </el-main>
    </el-container>
  </div>
</template>
<script>
export default {
  name: 'StudentHomepage',
  data() {
    return {
      courses: [],
    };
  },
  methods: {
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
  async created() {
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成

  },
};
</script>

<style>
.top_menu{
  background-color: #2bb7b3;
  border-color: yellow;
}
/* 在这里添加一些基础样式 */
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
}

el-aside {
  background-color: #f2f2f2;
}

el-main {
  background-color: #fff;
}
</style>
