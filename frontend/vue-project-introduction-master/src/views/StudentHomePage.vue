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
        <el-input placeholder="搜索课程"></el-input>
        <el-menu mode="horizontal" class="top_menu">

          <el-menu-item index="2" @click="goTo('home')">首页</el-menu-item>
          <el-menu-item index="3" @click="goTo('courses')">课程</el-menu-item>
          <el-menu-item index="4" @click="goTo('assignments')">作业</el-menu-item>
          <el-menu-item index="5" @click="goTo('grades')">成绩</el-menu-item>
          <el-menu-item index="6" @click="goTo('messages')">消息</el-menu-item>
          <el-menu-item index="7" @click="goTo('settings')">设置</el-menu-item>
          <el-menu-item index="8" @click="goTo('help')">帮助</el-menu-item>
          <el-menu-item index="9" @click="personinformation">
            个人资料
          </el-menu-item>
          <el-menu-item index="10" @click="logoutClick">
            LogOut
          </el-menu-item>
        </el-menu>
      </el-aside>


      <!-- 右侧内容区 -->
      <el-main>
        <div class="rili2">
        <v-calendar :attributes="attrs"></v-calendar>
      </div>
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
      ddls: [
        // ...其他DDL
      ],
      attrs: [],
    };
  },
  methods: {
    async loadAllCoursesinfo() {
      for (let course of this.courses) {

        //加载posts
        await this.$axios.get('/course/posts', {
          params: {
            courseId: course.id
          }
        }).then((res) => {
          if (res.data.code === "0") {
            localStorage.setItem('coursePostLength'+course.title,res.data.data.length)
            for (let i = 0; i < localStorage.getItem('coursePostLength'+course.title); i++) {
              localStorage.setItem('postid'+course.title+i,res.data.data[i].postId);
              localStorage.setItem('post'+course.title+i,res.data.data[i].postContent);
              localStorage.setItem('posttitle'+course.title+i,res.data.data[i].postTitle);
              localStorage.setItem('postauthor'+course.title+i,res.data.data[i].postAuthor);
            }
          }
        }).catch(error => {
          console.error('Error loading course posts:', error);
        });
        //加载材料
        await this.$axios.get('/course/materials', {
          params: {
            courseId: course.id
          }
        }).then((res) => {
          if (res.data.code === "0") {
            localStorage.setItem('courseMaterialLength'+course.title,res.data.data.length)
            for (let i = 0; i < localStorage.getItem('courseMaterialLength'+course.title); i++) {
              localStorage.setItem('materialid'+course.title+i,res.data.data[i].materialId);
              localStorage.setItem('materialname' + course.title + i, res.data.data[i].materialName);
              localStorage.setItem('materialdescription' + course.title + i, res.data.data[i].materialDescription);
            }
          }
        }).catch(error => {
          console.error('Error loading course materials:', error);
        });
      //加载assignments
        await this.$axios.get('/course/assignments', {
          params: {
            courseId: course.id
          }
        }).then((res) => {
          if (res.data.code === "0") {
            localStorage.setItem('courseAssignmentLength'+course.title,res.data.data.length)
            for (let i = 0; i < localStorage.getItem('courseAssignmentLength'+course.title); i++) {
              localStorage.setItem('assignmentid'+course.title+i,res.data.data[i].assignmentId);
              localStorage.setItem('assignmentstatus'+course.title+i,res.data.data[i].assignmentStatus);
              localStorage.setItem('assignmenttitle'+course.title+i,res.data.data[i].assignmentTitle);
              localStorage.setItem('assignmentdescription'+course.title+i,res.data.data[i].assignmentDescription);
              localStorage.setItem('assignmentddl'+course.title+i,res.data.data[i].assignmentDeadline);
              this.ddls.push({
                  date : res.data.data[i].assignmentDeadline,
                title : course.title+"   "+res.data.data[i].assignmentTitle,
              })
            }
          }
        }).catch(error => {
          console.error('Error loading course assignments:', error);
        });
        //加载projects
        await this.$axios.get('/course/projects', {
          params: {
            courseId: course.id
          }
        }).then((res) => {
          if (res.data.code === "0") {
            localStorage.setItem('projectsLength'+course.title,res.data.data.length)
            for (let i = 0; i < localStorage.getItem('projectsLength'+course.title); i++) {
              localStorage.setItem('projectid'+course.title+i,res.data.data[i].projectId);
              localStorage.setItem('projecttitle'+course.title+i,res.data.data[i].projectTitle);
              localStorage.setItem('projectdescription'+course.title+i,res.data.data[i].projectDescription);
              localStorage.setItem('projectstartdate'+course.title+i,res.data.data[i].projectStartDate);
              localStorage.setItem('projectddl'+course.title+i,res.data.data[i].projectDeadline);
              localStorage.setItem('projectstatus'+course.title+i,res.data.data[i].projectStatus);
              localStorage.setItem('maxpeopleinteam'+course.title+i,res.data.data[i].maxPeopleInTeam);
              this.ddls.push({
                date : res.data.data[i].projectDeadline,
                title : course.title+"   "+res.data.data[i].projectTitle,
              })
            }
          }
        }).catch(error => {
          console.error('Error loading course projects:', error);
        });
        //加载attendances
        await this.$axios.get('/course/attendances', {
          params: {
            courseId: course.id
          }
        }).then((res) => {
          if (res.data.code === "0") {
            localStorage.setItem('attendancesLength'+course.title,res.data.data.length)
            for (let i = 0; i < localStorage.getItem('attendancesLength'+course.title); i++) {
              localStorage.setItem('attendancedate'+course.title+i,res.data.data[i].attendanceDate);
            }
          }
        }).catch(error => {
          console.error('Error loading course attendances:', error);
        });





      }
    },

    goTo(route) {
// 假设使用 Vue Router 进行导航
localStorage.setItem('currentcourse',route.title);
      this.$router.push( "course" );
    },
    logoutClick() {
      this.$router.push('/Login');
      localStorage.clear();
    },
    personinformation() {
      this.$router.push('/PersonInformation');
    },
    async loadLocalStorageData() {
      await new Promise((resolve) => setTimeout(resolve, 10)); // 模拟异步操作，这里不是必要的，只是演示用例

      for (let i = 0; i < localStorage.getItem('length'); i++) {
        this.courses.push({
          id: localStorage.getItem('coursesid' + i),
          title: localStorage.getItem('courses' + i),
        });
      }
    },

  },
  async created() {
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    await this.loadAllCoursesinfo();
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
};
</script>

<style>
.top_menu{
  background-color: black;
  border-color: yellow;
  width:100%;

}
/* 在这里添加一些基础样式 */
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
}

el-aside {
  background-color: #f2f2f2;
}

el-main {
  background-color: cornflowerblue;
}

el-button{
  color: cornflowerblue;
  background-color: cornflowerblue;
}
.ddl-highlight {
  border: 2px solid red;
  border-radius: 50%;
}
.today-highlight {
  border: 2px solid blue;
  border-radius: 50%;
}
.rili2{
}
</style>

