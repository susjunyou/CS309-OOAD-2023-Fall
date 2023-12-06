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

        <el-form-item label="technologystack" prop="technologystack">
          <el-input v-model="edit.technologystack"/>
        </el-form-item>

        <el-form-item label="programmingskill" prop="programmingskill">
          <el-input v-model="edit.programmingskill"/>
        </el-form-item>

        <el-form-item label="intendedteammate" prop="intendedteammate">
          <el-input v-model="edit.intendedteammate"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="commitUpdate()">Submit</el-button>
        </el-form-item>

      </el-form>
    </el-dialog>

    <el-row class="main-content">

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
          <h1>题目: {{this.currenttitle}}</h1>
          <h4>作者: {{this.currentauthor}}</h4>
          <h3>内容: {{this.currentcontent}}</h3>
          <el-col :span="17" class="posts-container">
            <div v-for="reply in replys" :key="reply.replyContent" class="post">
              <h3>{{ reply.authorType }} 回答者: {{ reply.replyAuthor }}</h3>
              <p>{{ reply.replyContent }}</p>
            </div>

          </el-col>
<!--          <div v-for="reply in replys" :key="reply.replyContent">-->
<!--            <h3>回答{{ reply.replyContent }}</h3>-->
<!--          </div>-->
          <h4>你的回答：</h4>
          <input type="text" v-model="yourreplyContent">
          <el-button type="primary" @click="submitReply()">提交</el-button>
        </div>
    </el-row>
    <div v-if="isPopupVisible" class="popup">
      <div class="popup-content">
        <p>提交成功！</p>
        <button @click="returnTocourse">关闭</button>
      </div>
    </div>

    <el-dialog title="SA信息" :visible.sync="showSaDialog" width="60%">
      <div v-for="sa in saInfos" :key="sa.id" class="sa-info">
        <h3>{{ sa.name }}</h3>
        <p>邮箱: {{ sa.email }}</p>
        <p>专业: {{ sa.major }}</p>
        <p>自我介绍: {{ sa.selfIntroduction }}</p>
      </div>
    </el-dialog>

    <!-- 学生信息对话框 -->
    <el-dialog title="学生信息" :visible.sync="showStudentDialog" width="60%">
      <div v-for="student in studentInfos" :key="student.id" class="student-info">
        <h3>{{ student.name }}</h3>
        <p>邮箱: {{ student.email }}</p>
        <p>专业: {{ student.major }}</p>
        <p>自我介绍: {{ student.selfIntroduction }}</p>
      </div>
    </el-dialog>

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
        e_selfIntroduction:"",
        technologystack:"",
        programmingskill:"",
        intendedteammate:"",
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
      major: '',
      id :0,
      email:'',
      name:'',
      projects: [],
      materials: [],
      myValue: '',
      saInfos: [],
      studentInfos: [],
      showSaDialog: false, // 控制SA信息对话框的显示
      showStudentDialog: false, // 控制学生信息对话框的显示
      courseDescription:'',
      currenttitle: '',
      currentauthor: '',
      currentcontent:  '',
      replys:[],
      currentpostid:'',
      yourReplyContent:'',
      isPopupVisible:false,
      technologystack:"",
      programmingskill:"",
      intendedteammate:"",
    };
  },
  name: 'CourseNavbar',

  async created() {
    this.id = localStorage.getItem('id');
    this.name = localStorage.getItem('name');
    this.major = localStorage.getItem('major');
    // this.phoneNumber = localStorage.getItem('phoneNumber');
    this.currentpostid=localStorage.getItem('currentpostid');
    this.currentauthor=localStorage.getItem('currentauthor');
    this.currenttitle=localStorage.getItem('currenttitle');
    this.currentcontent=localStorage.getItem('currentcontent');
    this.email = localStorage.getItem('email');
    await this.loadpostreply();
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    await this.loadStudentsAndSA();
    this.myValue=localStorage.getItem("currentcourse");
    this.courseDescription=localStorage.getItem("getdescriptionbyid"+localStorage.getItem("currentcourseid"));
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
    submitReply(){
      const currentpostid=localStorage.getItem('currentpostid');
      const yourreplycontent=this.yourreplyContent;
      const submitDate = new Date();
      const formattedDate = submitDate.toISOString().split('T')[0]; // 获取 yyyy-MM-dd 格式
      this.$axios.get('/post/releaseReply', {
        params: {
          replyAuthor: localStorage.getItem('id'),
          authorType: localStorage.getItem('userType'),
          replyContent: yourreplycontent,
          postId: currentpostid,
          replyTime: formattedDate,
        }
      }).then(res => {
        if (res.data.code === "0") {
          this.yourreplyContent = "";
          this.isPopupVisible=true;
          this.$axios.get('/post/getReplyByPostId',{
            params: {
              postId:this.currentpostid,
            }
          }).then((res) => {
            if (res.data.code === "0") {
              this.replys=[];
              localStorage.setItem('replylength'+this.currentpostid,res.data.data.length);
              for(let i=res.data.data.length-1;i>=0;i--){
                localStorage.setItem('replyContent'+this.currentpostid+i, res.data.data[i].replyContent);
                localStorage.setItem('replyAuthor'+this.currentpostid+i, res.data.data[i].replyAuthor);
                localStorage.setItem('replyDate'+this.currentpostid+i, res.data.data[i].replyDate);
                localStorage.setItem('authorType'+this.currentpostid+i, res.data.data[i].authorType);
                this.replys.push({
                  replyContent:localStorage.getItem('replyContent'+this.currentpostid+i),
                  replyAuthor:localStorage.getItem('replyAuthor'+this.currentpostid+i),
                  replyDate:localStorage.getItem('replyDate'+this.currentpostid+i),
                  authorType:localStorage.getItem('authorType'+this.currentpostid+i),
                });
              }
            }
          }).catch(error => {
            console.error('Error loading sainfos:', error);
          });
        }
      }).catch(err => {
        console.log(err);
      })
    },
    returnTocourse(){
      this.isPopupVisible = false;
      this.$router.push('/postReply');
    },


    update(){
      this.dialogVisible=true;
      this.edit.e_id = this.id;
      this.edit.e_email = this.email;
      this.edit.e_phoneNumber = localStorage.getItem('phoneNumber');
      this.edit.e_selfIntroduction = localStorage.getItem('selfIntroduction');
      this.edit.technologystack = localStorage.getItem('technologystack');
      this.edit.programmingskill = localStorage.getItem('programmingskill');
      this.edit.intendedteammate = localStorage.getItem('intendedteammate');
    },
    commitUpdate(){
      //this.id = this.edit.e_id;
      this.email = this.edit.e_email;
      localStorage.setItem('id',this.edit.e_id);
      localStorage.setItem('email',this.edit.e_email);
      localStorage.setItem('phoneNumber',this.edit.e_phoneNumber);
      localStorage.setItem('selfIntroduction',this.edit.e_selfIntroduction);
      localStorage.setItem('technologystack',this.edit.technologystack);
      localStorage.setItem('programmingskill',this.edit.programmingskill);
      localStorage.setItem('intendedteammate',this.edit.intendedteammate);
      this.dialogVisible = false;
      this.$axios.get('/student/updateStudentDetails',{
        params: {
          id:localStorage.getItem('id'),
          email:localStorage.getItem('email'),
          phoneNumber:localStorage.getItem('phoneNumber'),
          selfIntroduction:localStorage.getItem('selfIntroduction'),
          technologyStack:localStorage.getItem('technologystack'),
          programmingSkill:localStorage.getItem('programmingskill'),
          intendedTeammate:localStorage.getItem('intendedteammate'),
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
          localStorage.setItem('technologystack',this.edit.technologystack);
          localStorage.setItem('programmingskill',this.edit.programmingskill);
          localStorage.setItem('intendedteammate',this.edit.intendedteammate);
          console.log('sss');
          this.isPopupVisible = true;
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
    logoutClick() {
      this.$router.push('/Login');
      localStorage.clear();
    },
    goTo(route) {
// 假设使用 Vue Router 进行导航    goTo(route) {
// 假设使用 Vue Router 进行导航
      localStorage.setItem("currentcourseid",route.id);
      localStorage.setItem("currentcourse",route.title);
      this.myValue=route.title;
      this.loadLocalStorageData();
      this.loadStudentsAndSA();
      this.$router.push({ path: '/course' });
    },
    go(route) {

      this.$router.push(route);
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
    async loadpostreply(){
      this.replys=[];
      await new Promise((resolve) => setTimeout(resolve, 10)); // 模拟异步操作，这里不是必要的，只是演示用例
      this.$axios.get('/post/getReplyByPostId',{
        params: {
          postId:this.currentpostid,
        }
      }).then((res) => {
        if (res.data.code === "0") {
          localStorage.setItem('replylength'+this.currentpostid,res.data.data.length);
          for(let i=res.data.data.length-1;i>=0;i--){
            localStorage.setItem('replyContent'+this.currentpostid+i, res.data.data[i].replyContent);
            localStorage.setItem('replyAuthor'+this.currentpostid+i, res.data.data[i].replyAuthor);
            localStorage.setItem('replyDate'+this.currentpostid+i, res.data.data[i].replyDate);
            localStorage.setItem('authorType'+this.currentpostid+i, res.data.data[i].authorType);
            this.replys.push({
              replyContent:localStorage.getItem('replyContent'+this.currentpostid+i),
              replyAuthor:localStorage.getItem('replyAuthor'+this.currentpostid+i),
              replyDate:localStorage.getItem('replyDate'+this.currentpostid+i),
              authorType:localStorage.getItem('authorType'+this.currentpostid+i),
            });
          }
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });
    },
    async loadLocalStorageData() {
      await new Promise((resolve) => setTimeout(resolve, 10)); // 模拟异步操作，这里不是必要的，只是演示用例
      this.courses=[];
      for (let i = 0; i < localStorage.getItem('length'); i++) {
        this.courses.push({
          id: localStorage.getItem('coursesid' + i),
          title: localStorage.getItem('courses' + i),
          description: localStorage.getItem('courseDescription' + i),
          code: localStorage.getItem('coursecode' +i),
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
      // this.replys=[];
      // for (let i = localStorage.getItem('replylength'+this.currentpostid); i >=0 ; i--) {
      //     this.replys.push({
      //       replyContent:localStorage.getItem('replyContent'+this.currentpostid+i),
      //       replyAuthor:localStorage.getItem('replyAuthor'+this.currentpostid+i),
      //       replyDate:localStorage.getItem('replyDate'+this.currentpostid+i),
      //       authorType:localStorage.getItem('authorType'+this.currentpostid+i),
      //     });
      // }
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
  overflow-y: auto; /* 如果内容太多可以滚动 */
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

.posts-container {
  margin-right: 1000px; /* Adjust the margin as needed */
  margin-left: 200px;
  padding: 10px;
  overflow: auto;
  width: 80%; /* 或者设置你想要的宽度 */
}
</style>



