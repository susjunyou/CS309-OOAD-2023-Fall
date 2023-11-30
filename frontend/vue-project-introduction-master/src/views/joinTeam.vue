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
    <div class="team-grid">
      <div class="team-card" v-for="team in teams" :key="team.id">
        <h3>{{ team.name }}</h3>
        <p>{{ team.description }}</p>
        <p>当前人数: {{ team.teammembers.length }} / 最多人数: {{ team.teamsize }}</p>
        <!-- 显示成员列表 -->
        <ul>
          <li v-for="(member, index) in team.teammembers" :key="member.id">
            成员{{ index + 1 }}     姓名: {{ member.name }}     id：{{member.id}}
          </li>
        </ul>

        <button @click.prevent="joinTeam(team)">加入队伍</button>
      </div>

    </div>
    <div v-if="isPopupVisible" class="popup">
      <div class="popup-content">
        <p>加入成功！</p>
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
      teams: [],
      myValue: '',
      isPopupVisible: false, // 控制弹窗显示的布尔值

    };
  },


  async created() {
    // this.myValue=localStorage.getItem("currentprojectid")

    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    await this.getTeam();
    this.myValue=localStorage.getItem("currentcourse")
  },
  methods: {
    returnToprotects(){
      this.isPopupVisible = false;
      this.$router.push('/projects');
    },
    joinTeam(team) {
      // console.log("Number(localStorage.getItem(\"id\"))="+Number(localStorage.getItem("id")));
      // console.log("JSON.parse(localStorage.getItem('teaminfo'+teamId+localStorage.getItem(\"currentcourse\")))="+JSON.parse(localStorage.getItem('teaminfo'+teamId+localStorage.getItem("currentcourse"))))
      this.$axios.get('/team/join', {
        params: {
          studentId: Number(localStorage.getItem("id")),
          teamId: Number(team.id),
          projectId:Number(team.projectid),
          teamSize: team.teamsize,
          teamcount:0,
        },
      }).then((res) => {
        if (res.data.code === "0") {
          this.isPopupVisible = true;
        }
      }).catch(error => {
        console.error('Error loading course assignments:', error);
      });
    // console.log("Joining team with ID:", teamId);
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
          id: localStorage.getItem('coursesid' + i),
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

      }


      console.log("course name="+this.myValue)
      console.log("assleng="+localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")))
      console.log("projectleng="+localStorage.getItem('projectsLength'+localStorage.getItem("currentcourse")))

    },
    // async getMember() {
    //   await this.$axios.get('/team/findTeamMembers', {
    //     params: {
    //       projectId: localStorage.getItem("currentprojectid")
    //     },
    //
    //   }).then((res) => {
    //     if (res.data.code === "0") {
    //       // localStorage.setItem('courseprojectteamLength'+this.myValue.title,res.d)
    //       for (let i = 0; i < this.teamcount; i++) {
    //         this.teams.push({
    //
    //         });
    //         // 将对象转换为JSON字符串并存储
    //         // localStorage.setItem('teaminfo' + res.data.data[i].teamId + " " + localStorage.getItem("currentprojectid"), JSON.stringify(res.data.data[i]));
    //         // console.log(localStorage.getItem('teaminfo' + res.data.data[i].teamId + " " + localStorage.getItem("currentprojectid")));
    //       }
    //
    //     }
    //   }).catch(error => {
    //     console.error('Error loading course assignments:', error);
    //   });
    // },

    async getTeam() {
      console.log("projectid="+localStorage.getItem("currentprojectid"));
      try {
        const res = await this.$axios.get('/team/findTeamInfoByProjectId', {
          params: {
            projectId: localStorage.getItem("currentprojectid")
          }
        });
        if (res.data.code === "0") {
          localStorage.setItem(localStorage.getItem("currentcourse")+" "+localStorage.getItem("currentprojectid")+" "+"teamcount", res.data.data.length);
          this.teamcount = res.data.data.length;
          for (let i = 0; i < res.data.data.length; i++) {
            const team = res.data.data[i];
            const res1 = await this.$axios.get('/team/findTeamMembers', {
              params: {
                teamId: team.teamId
              }
            });
            if (res1.data.code === "0") {
              this.teams.push({
                id: team.teamId,
                name: team.teamName,
                description: team.teamDescription,
                leader: team.leader,
                projectid: team.projectId,
                teamsize: team.teamSize,
                teammembers: res1.data.data,
                currentmembercount: res1.data.data ? res1.data.data.length : 0,
              });
            }
          }
        }
      } catch (error) {
        console.error('Error loading team info or members:', error);
      }
    }

},
}
</script>


<style scoped>
.team-grid {
  display: flex;
  flex-wrap: wrap; /* 允许项目换行 */
  gap: 20px; /* 设置团队卡片之间的间隔 */
  justify-content: center; /* 使卡片在容器中居中对齐 */
}

.team-card {
  flex: 0 1 300px; /* 卡片的基础大小为300px，但可以根据需要伸缩 */
  border: 1px solid #ccc; /* 边框颜色 */
  border-radius: 10px; /* 边框圆角 */
  padding: 20px; /* 内边距 */
  background-color: #f5f5f5; /* 背景颜色 */
  text-align: center; /* 文本居中对齐 */
}

.team-card button {
  padding: 10px 20px;
  margin-top: 10px; /* 在按钮上方添加间隔 */
  cursor: pointer; /* 鼠标悬停时显示手形光标 */
  border: none;
  border-radius: 5px;
  background-color: #00b4d8; /* 按钮背景颜色 */
  color: white;
}

.team-card button:hover {
  background-color: #0096c7; /* 鼠标悬停时按钮的背景颜色 */
}
.team-card p {
  margin: 10px 0; /* 在段落之间添加一些垂直间隔 */
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
</style>