<template>
  <div id="app">
    <el-row class="header-bar">
      <el-col :span="15">
        <h1 class="header-title">Project Helper</h1>
      </el-col>
      <el-col :span="5">
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


      <el-col :span="4">
        <el-button type="text" v-popover:profilePopover class="profile-button">
          <i class="el-icon-user"></i> 个人资料
        </el-button>
        <el-popover ref="profilePopover" placement="bottom" width="200" trigger="click">
          <!-- popover内容 -->
          <div class="user-profile">
            <img src="../assets/人脸.png" alt="个人信息" class="avatar">
            <h3>姓名：{{ this.name }}</h3>
            <p>学号：{{ this.id }}</p>
            <p>邮箱：{{ this.email }}</p>
            <p>专业：{{ this.major }}</p>
            <el-menu>
              <el-menu-item index="1" @click="go('updatePassword')">修改密码</el-menu-item>
              <el-menu-item index="2" @click="update">修改个人信息</el-menu-item>
              <el-menu-item index="3" @click="logoutClick">LogOut</el-menu-item>
            </el-menu>
          </div>
        </el-popover>

      </el-col>

    </el-row>
    <el-dialog
        :visible.sync="dialogVisible"
        title="Add A New Record"
        width="40%"

    >
      <el-form
          ref="editForm"
          :model="edit"
          :rules="rules"
          label-width="auto"
          label-position="right"
          size="default"


      >
        <el-form-item label="id" prop="e_id">
          <el-input v-model="edit.e_id" disabled="disabled"/>
        </el-form-item>

        <el-form-item label="email" prop="e_email">
          <el-input v-model="edit.e_email"/>
        </el-form-item>

        <el-form-item label="phoneNumber" prop="e_phoneNumber">
          <el-input v-model="edit.e_phoneNumber"/>
        </el-form-item>

        <el-form-item label="selfIntroduction" prop="e_selfIntroduction">
          <el-input v-model="edit.e_selfIntroduction"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="commitUpdate()">Submit</el-button>
        </el-form-item>

      </el-form>
    </el-dialog>

    <el-row class="main-content">
      <el-col>
      <el-menu
          class="course-navbar"
          mode="vertical"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b">
        <el-menu-item index="1" @click="go('StudentHomePage')">Home</el-menu-item>
        <el-menu-item index="2" @click="go('post')">Post</el-menu-item>
        <el-menu-item index="3" @click="go('materials')">Materials</el-menu-item>
        <el-menu-item index="4" @click="go('assignments')">Assignments</el-menu-item>
        <el-menu-item index="5" @click="go('projects')">Projects</el-menu-item>
        <el-menu-item index="7" @click="studentClick">members</el-menu-item>
        <el-menu-item index="6" @click="go('gradebook')">Gradebook</el-menu-item>
      </el-menu>
        <div>
          <h1>{{currentCourse}}</h1>
          <div class="post">
            <h3>CourseDescription: {{currentCourseDescription}}</h3>
          </div>
        </div>
        <h2>Announcements</h2>
        <div class="assignment-container">
          <!-- ...之前的代码... -->
          <el-row :gutter="20">
            <el-col v-for="anouncement in anouncements" :key="anouncement.id" :span="6" >
              <el-card  class="assignment-card" >
                <h3>{{ anouncement.title }}</h3>
                <p>发布者：{{ anouncement.author }}</p>
              </el-card>
            </el-col>
          </el-row>
          <!-- ...之后的代码... -->

        </div>
      </el-col>
      <el-col :span="8" class="calendar">
        <v-calendar :attributes="attrs"></v-calendar>
      </el-col>


    </el-row>


  </div>
</template>




<script>

export default {

  data() {
    const e_idValidator = (rule, value, callback) => {///^[A-Za-z0-9]+/
      const re = /^[0-9]/;
      if (!value) {
        return callback(new Error('Please input id'));
      }
      if (!re.test(value)) {
        return callback(new Error('Invalid id, should only contain numbers'));
      }
      callback();
    };
    const e_phoneNumberValidator = (rule, value, callback) => {///^[A-Za-z0-9]+/
      const re = /^[0-9]+/;//(?=.*[0-9])(?=.*[a-zA-Z])
      if (!value) {
        return callback(new Error('Please input phoneNumber'));
      }
      if (!re.test(value)) {
        return callback(new Error('Invalid phoneNumber, should only contain numbers'));
      }
      callback();
    };
    return {
      // 初始化组件数据属性
      rules: {

        e_id: [
          {validator: e_idValidator, trigger: 'blur'},
          {required: true, message: 'Please input the id', trigger: 'blur'},
        ],
        email: [
          //{validator: emailValidator(), trigger: 'blur'},
          {required: true, message: 'Please input the id', trigger: 'blur'},
        ],
        e_phoneNumber: [
          {validator: e_phoneNumberValidator, trigger: 'blur'},
          {required: true, message: 'Please input the id', trigger: 'blur'},
        ],
      },
      dialogVisible: false,
      edit: {
        e_id: "",
        e_email:"",
        e_phoneNumber:"",
        e_selfIntroduction:""
      },
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
      major: '',
      id :0,
      email:'',
      name:'',
      team: {
        title: '',
        description: '',
        teamSize: 0
      },
      maxpeople:0,
      projectid:0,
      sid:0,
      saInfos: [],
      studentInfos: [],
      showSaDialog: false, // 控制SA信息对话框的显示
      showStudentDialog: false, // 控制学生信息对话框的显示
      courseDescription:'',
      isPopupVisible: false, // 控制弹窗显示的布尔值
      currentCourse:'',
      currentCourseDescription:'',
      anouncements:[],
    };
  },
  name: 'CourseNavbar',


  async created() {
    this.id = localStorage.getItem('id');
    this.name = localStorage.getItem('name');
    this.major = localStorage.getItem('major');
    this.email = localStorage.getItem('email');
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    await this.loadStudentsAndSA();
    this.myValue=localStorage.getItem("currentcourse");
    this.courseDescription=localStorage.getItem("getdescriptionbyid"+localStorage.getItem("currentcourseid"));
    const today = new Date();
    this.attrs=[];
    this.attrs = this.ddls.map(ddl => {
      const ddlDate = new Date(ddl.date);
      let contentClass = '';
      if (ddlDate < today) {
        contentClass = 'ddl-past'; // 过去的DDL
      } else if (ddlDate.toISOString().split('T')[0] === today.toISOString().split('T')[0]) {
        contentClass = 'today-highlight'; // 今天
      } else {
        contentClass = 'ddl-future'; // 将来的DDL
      }

      return {
        key: ddl.date,
        dates: ddlDate,
        highlight: {
          contentClass: contentClass,
        },
        popover: {
          label: ddl.title,
        },
      };
    });
    this.attrs.push({
      key: 'today',
      dates: today,
      highlight: {
        contentClass: 'today-highlight',
      },
      popover: {
        label: '今天',
      },
    });
  },
  methods: {
    update(){
      this.dialogVisible=true;
      this.edit.e_id = this.id;
      this.edit.e_email = this.email;
      this.edit.e_phoneNumber = localStorage.getItem('phoneNumber');
      this.edit.e_selfIntroduction = localStorage.getItem('selfIntroduction');

    },
    commitUpdate(){
      //this.id = this.edit.e_id;
      this.email = this.edit.e_email;
      localStorage.setItem('id',this.edit.e_id);
      localStorage.setItem('email',this.edit.e_email);
      localStorage.setItem('phoneNumber',this.edit.e_phoneNumber);
      localStorage.setItem('selfIntroduction',this.edit.e_selfIntroduction);
      this.dialogVisible = false;
      this.$axios.get('/student/updateStudentDetails',{
        params: {
          id:localStorage.getItem('id'),
          email:localStorage.getItem('email'),
          phoneNumber:localStorage.getItem('phoneNumber'),
          selfIntroduction:localStorage.getItem('selfIntroduction')
        }
      }).then(res => {
        console.log('dd');
        if(res.data.code === "0"){
          this.successMessage = '个人资料修改成功';
          this.errorMessage = '';
          localStorage.setItem('id',this.edit.e_id);
          localStorage.setItem('email',this.edit.e_email);
          localStorage.setItem('phoneNumber',this.edit.e_phoneNumber);
          localStorage.setItem('selfIntroduction',this.edit.e_selfIntroduction);
          console.log('sss');
        }else {
          console.log("error")
        }
      }).catch(error => {
        console.log(error)
      })
    },
    saClick() {
      this.showSaDialog = true;
    },
    studentClick() {
      this.$router.push('/members');
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
    logoutClick() {
      this.$router.push('/Login');
      localStorage.clear();
    },
    async goTo(route) {
// 假设使用 Vue Router 进行导航    goTo(route) {
// 假设使用 Vue Router 进行导航
      localStorage.setItem("currentcourseid",route.id);
      localStorage.setItem("currentcourse",route.title);
      this.myValue=route.title;
      await this.loadLocalStorageData();
      await this.loadStudentsAndSA();
      const today = new Date();
      this.attrs=[],
          this.attrs = this.ddls.map(ddl => {
            const ddlDate = new Date(ddl.date);
            let contentClass = '';

            if (ddlDate < today) {
              contentClass = 'ddl-past'; // 过去的DDL
            } else if (ddlDate.toISOString().split('T')[0] === today.toISOString().split('T')[0]) {
              contentClass = 'today-highlight'; // 今天
            } else {
              contentClass = 'ddl-future'; // 将来的DDL
            }

            return {
              key: ddl.date,
              dates: ddlDate,
              highlight: {
                contentClass: contentClass,
              },
              popover: {
                label: ddl.title,
              },
            };
          });
      this.attrs.push({
        key: 'today',
        dates: today,
        highlight: {
          contentClass: 'today-highlight',
        },
        popover: {
          label: '今天',
        },
      });

    },
    go(route) {
      this.$router.push(route);
    },

    async loadLocalStorageData() {
      await new Promise((resolve) => setTimeout(resolve, 10)); // 模拟异步操作，这里不是必要的，只是演示用例
      this.currentCourse=localStorage.getItem("currentcourse");
      this.currentCourseDescription=localStorage.getItem('courseDescription'+localStorage.getItem('currentcourseid'));
      this.courses=[];
      for (let i = 0; i < localStorage.getItem('length'); i++) {
        this.courses.push({
          id: localStorage.getItem('coursesid' + i),
          title: localStorage.getItem('courses' + i),
          description: localStorage.getItem('courseDescription' + i),
          code: localStorage.getItem('coursecode' +i),
        });
      }
      this.anouncements=[];
      this.$axios.get('/course/posts', {
        params: {
          courseId: localStorage.getItem('currentcourseid'),
        }
      }).then((res) => {
        if (res.data.code === "0") {
          this.posts=[];
          localStorage.setItem('coursePostLength'+localStorage.getItem('currentcourse'),res.data.data.length)
          for(let i = localStorage.getItem('coursePostLength'+localStorage.getItem('currentcourse'))-1;i>=0;i--) {
            localStorage.setItem('postid' + localStorage.getItem('currentcourse') + i, res.data.data[i].postId);
            localStorage.setItem('post' + localStorage.getItem('currentcourse') + i, res.data.data[i].postContent);
            localStorage.setItem('posttitle' + localStorage.getItem('currentcourse') + i, res.data.data[i].postTitle);
            localStorage.setItem('postauthor' + localStorage.getItem('currentcourse') + i, res.data.data[i].postAuthor);
            localStorage.setItem('postType'+localStorage.getItem('currentcourse') + i,res.data.data[i].postType);
            if(localStorage.getItem('postType'+localStorage.getItem('currentcourse') + i) ==='ANNOUNCEMENT'){
              this.anouncements.push({
                course: localStorage.getItem('currentcourse'),
                id: res.data.data[i].postId,
                title: res.data.data[i].postTitle,
                content: res.data.data[i].postContent,
                author: res.data.data[i].postAuthor,
              })
            }
          }
        }
      }).catch(error => {
        console.error('Error loading course posts:', error);
      });
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
      this.ddls=[];
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
.user-profile {
  text-align: center; /* 居中用户信息 */
}

.avatar {
  width: 80px; /* 头像大小 */
  height: 80px; /* 头像大小 */
  border-radius: 50%; /* 圆形头像 */
  margin-bottom: 10px; /* 头像与姓名之间的间距 */
}




/* 自定义profile按钮样式 */


.profile-button {
  color: #fff; /* 文本颜色 */
  /* 其他需要的样式 */
}


.calendar {
  width: 300px; /* 固定日历的宽度 */
}
.ddl-past {
  border: 2px solid red;
  border-radius: 50%;
}

.ddl-future {
  border: 2px solid green;
  border-radius: 50%;
}

.today-highlight {
  border: 2px solid blue;
  border-radius: 50%;
}
.post {
  text-align: left;
  border: 1px solid gainsboro;
  margin-bottom: 10px;
  padding: 10px;
  margin-left:400px;
  margin-right: 200px;

}
.assignment-container {
  margin: 20px;
  padding-left: 200px;
}

.assignment-card {
  cursor: pointer;
  transition: box-shadow .3s;
  border: 1px solid gainsboro;
  margin-top: 10px;
  width: 280px;
  height: 150px;
}

.assignment-card:hover {
  box-shadow: 0 4px 6px rgba(0,0,0,0.8);
}

</style>



