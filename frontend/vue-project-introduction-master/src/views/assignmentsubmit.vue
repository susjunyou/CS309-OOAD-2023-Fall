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

    <div class="assignment-submission">
      <h1>Assignment Submission</h1>
      <div class="text-submission">
        <textarea v-model="textSubmission" placeholder="Write Submission"></textarea>
      </div>
      <div class="file-upload">
        <input type="file"  />
<!--        @change="handleFileUpload"-->
      </div>
      <div class="button-container">
        <button class="submit" @click.prevent="submitAssignment">Submit</button>
        <button class="cancel" @click="cancel">Cancel</button>
      </div>

    </div>
    <!--    <p>welcome to {{myValue}}</p>-->
    <!--  </div>-->
    <div v-if="isPopupVisible" class="popup">
      <div class="popup-content">
        <p>提交成功！</p>
        <button @click="returnToassignments">关闭</button>
      </div>
    </div>
  </div>
</template>
<script >

export default {

  data() {
    return {
      // 初始化组件数据属性
      ddls: [
        // ...其他DDL
      ],
      attrs: [],
      // 初始化组件数据属性
      courses: [],
      posts: [],
      assignments: [],
      projects: [],
      isPopupVisible: false, // 控制弹窗显示的布尔值
      materials: [],
      myValue: '',
      textSubmission: '', // 绑定文本提交的数据
      file: null, // 用于存储文件上传的数据
    };
  },


  async created() {
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    this.myValue=localStorage.getItem("currentcourse")
  },
  methods: {




    async  submitAssignment() {
      // 创建一个新的日期对象
      const submitDate = new Date();
      const formattedDate = submitDate.toISOString().split('T')[0]; // 获取 yyyy-MM-dd 格式
      await this.$axios.get('/student/submitAssignment', {
        params: {
          studentId: Number(localStorage.getItem("id")),
          assignmentId: localStorage.getItem("currentassignmentid"),
          content: this.textSubmission,
          submitDate: formattedDate,
        }
      }).then((res) => {
        console.log("code====================================="+res.data.code)
        if (res.data.code === "0") {
          this.isPopupVisible = true;
        }
      }).catch(error => {
        console.error('Error loading course posts:', error);
      });
    },
    logoutClick() {
      this.$router.push('/Login');
      localStorage.clear();
    },
    cancel() {
this.$router.push('/assignments');
},
    goTo(route) {
// 假设使用 Vue Router 进行导航
      localStorage.setItem("currentcourse",route);
      this.myValue=route;
      this.loadLocalStorageData();
      this.$router.push({ path: '/course' });
    },
    go(route) {

      this.$router.push(route);
    },
    returnToassignments(){
      this.isPopupVisible = false;
      this.$router.push('/assignments');
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
      console.log("course name="+this.myValue)
      console.log("assleng="+localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")))
      console.log("projectleng="+localStorage.getItem('projectsLength'+localStorage.getItem("currentcourse")))

    },
  },
}
</script>


<style scoped>
.assignment-submission {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #f7f7f7;
  padding: 20px;
  box-sizing: border-box;
}

h1 {
  margin-bottom: 20px;
}

.text-submission {
  padding-right: 0; /* 减少右侧内边距 */
}

.text-submission textarea {
  width: 600px; /* 保持宽度为100% */
  min-height: 400px; /* 最小高度 */
  padding: 20px; /* 适当的内边距 */
  margin-bottom: 20px; /* 下边距 */
  margin-left: auto; /* 左边距自动，这将使元素居中 */
  margin-right: auto; /* 右边距自动 */
  border: 1px solid #ccc; /* 边框 */
  border-radius: 4px; /* 圆角 */
  resize: vertical; /* 允许垂直调整大小 */
}


.file-upload input {
  margin-bottom: 20px;
}

/* 提交按钮的样式 */


.button-container {
  display: flex;
  justify-content: space-around; /* 在按钮之间提供空间 */
  padding: 20px; /* 容器内边距 */
}

.button-container button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
  flex-grow: 1; /* 按钮占据可用空间 */
}

.button-container button.submit {
  background-color: #3498db; /* 蓝色背景 */
  color: white;
  margin-right: 10px; /* 在按钮之间提供空间 */
}

.button-container button.cancel {
  background-color: #e74c3c; /* 红色背景 */
  color: white;
  margin-left: 10px; /* 在按钮之间提供空间 */
}

.button-container button:hover {
  filter: brightness(90%);
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
