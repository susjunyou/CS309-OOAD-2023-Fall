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


    <el-button type="primary" class="custom-button" @click="showPostForm"  >发表</el-button>
      <div class="card-container" style="display: flex; flex-wrap: wrap">
        <!-- ...之前的代码... -->
        <el-row :gutter="20">
          <el-col v-for="post in posts" :key="post.id" :span="24" >
            <el-card  class="post-card" @click.native="showReplyForm(post)" >
              <h3>{{ post.title }}</h3>
              <p>发布者：{{ post.author }}</p>
              <p>{{ post.content }}</p>
            </el-card>
          </el-col>
        </el-row>
        <!-- ...之后的代码... -->

      </div>
<!--    <div>-->
<!--    <el-col :span="17" class="posts-container">-->
<!--    <div v-for="post in posts" :key="post.id" >-->
<!--      <h3>{{ post.title }} 发布者: {{ post.author }}</h3>-->
<!--      <p>{{ post.content }}</p>-->
<!--    </div>-->
<!--    </el-col>-->
<!--    </div>-->
      <!--      发布post-->
      <el-dialog
          :visible.sync="showForm"
          title="release post"
          width="50%"
      >
        <el-table :data="formData" style="width: 100%">
          <el-table-column label="标题" prop="content"></el-table-column>
        </el-table>
        <el-input
            v-model="formData.title"
            placeholder="输入标题"
            clearable
        ></el-input>
        <el-table :data="formData" style="width: 100%">
          <el-table-column label="内容" prop="content"></el-table-column>
        </el-table>
        <el-input
            v-model="formData.content"
            placeholder="输入内容"
            type="textarea"
            :rows="10"
            resize="none"
        ></el-input>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="cancelForm">取消</el-button>
      </el-dialog>

      <!--      回复post-->
      <el-dialog
          :visible.sync="showreplyForm"
          title="release post"
          width="50%"
      >
        <el-table :data="replyFormData" style="width: 100%">
          <el-table-column label="标题" prop="content"></el-table-column>
        </el-table>
        <el-input
            v-model="replyFormData.title"
            placeholder="输入标题"
            clearable
            disabled="disabled"
        ></el-input>
        <el-table :data="replyFormData" style="width: 100%">
          <el-table-column label="内容" prop="content"></el-table-column>
        </el-table>
        <el-input
            v-model="replyFormData.content"
            placeholder="输入内容"
            type="textarea"
            :rows="10"
            resize="none"
            disabled="disabled"
        ></el-input>
        <el-table :data="replyFormData" style="width: 100%">
          <el-table-column label="回复" prop="reply"></el-table-column>
        </el-table>
        <el-input
            v-model="replyFormData.reply"
            placeholder="输入回复"
            type="textarea"
            :rows="10"
            resize="none"
        ></el-input>
        <el-button type="primary" @click="submitreplyForm">提交</el-button>
        <el-button @click="cancelreplyForm">取消</el-button>
      </el-dialog>



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
      projects: [],
      materials: [],
      myValue: '',
      saInfos: [],
      studentInfos: [],
      showSaDialog: false, // 控制SA信息对话框的显示
      showStudentDialog: false, // 控制学生信息对话框的显示
      courseDescription:'',
      showForm: false, // 控制表单显示与隐藏
      showreplyForm: false, // ��制发表表单显示与隐藏

      formData: {
        title: '',
        content: '' // 表单数据
      },
      replyFormData: {
        title: '',
        content: '',
        reply: '',
      },
      isPopupVisible: false,
      technologystack:"",
      programmingskill:"",
      intendedteammate:"",
      major: '',
      id :0,
      email:'',
      name:'',
    };

  },
  name: 'CourseNavbar',


  async created() {
    this.id = localStorage.getItem('id');
    this.email = localStorage.getItem('email');
    this.name = localStorage.getItem('name');
    this.major = localStorage.getItem('major');
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
    showReplyForm(post){
      this.$axios.get('/post/getReplyByPostId',{
        params: {
          postId:post.id,
        }
      }).then((res) => {
        if (res.data.code === "0") {
          localStorage.setItem('replylength'+post.id,res.data.data.length);
          for(let i=0;i<res.data.data.length;i++){
            localStorage.setItem('replyContent'+post.id+i, res.data.data[i].replyContent);
            localStorage.setItem('replyAuthor'+post.id+i, res.data.data[i].replyAuthor);
            localStorage.setItem('replyDate'+post.id+i, res.data.data[i].replyDate);
            localStorage.setItem('authorType'+post.id+i, res.data.data[i].authorType);
          }
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });
      localStorage.setItem('currentpostid',post.id);
      localStorage.setItem('currentauthor',post.author);
      localStorage.setItem('currenttitle',post.title);
      localStorage.setItem('currentcontent',post.content);
      this.$router.push({path:'/postReply',
      query:{
        postid:post.id,
        title:post.title,
        content:post.content,
        author:post.author,
      }})

    },
    submitreplyForm(){
      this.showreplyForm=false;
    },
    cancelreplyForm(){
      this.showreplyForm=false;
    },
    showPostForm() {
      this.showForm = true; // 点击发表按钮显示表单
    },
    async submitForm() {
      const submitDate = new Date();
      const formattedDate = submitDate.toISOString().split('T')[0]; // 获取 yyyy-MM-dd 格式
      const textContent = this.formData.content;
      const texttitle=this.formData.title;

      // const postInfo = {
      //   postTitle: texttitle,
      //   postContent: textContent,
      //   time: formattedDate,
      //   postAuthor:localStorage.getItem('id'),
      //   authorType:localStorage.getItem('userType'),
      //   courseId:localStorage.getItem('currentcourseid'),
      // };
      // console.log(postInfo)
      this.$axios.get('/post/releasePost', {
        params:{
          postTitle: texttitle,
          postContent: textContent,
          time: formattedDate,
          postAuthor:localStorage.getItem('id'),
          authorType:localStorage.getItem('userType'),
          courseId:localStorage.getItem('currentcourseid'),
          postType: 'QUESTION'
        }
      }).then(resp => {
        console.log(resp.data)
        if (resp.data.code === "0") {
          this.isPopupVisible=true;
          this.showForm = false;
          this.formData.content='';
          this.formData.title='';
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
              if(localStorage.getItem('postType'+localStorage.getItem('currentcourse') + i) ==='QUESTION'){
                this.posts.push({
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

        }
      }).catch(err => {
        console.log(err);
      })
      // 处理提交表单的操作，这里可以将数据提交至后端或其他操作
      // 提交后的逻辑处理完成后隐藏表单
      this.showForm = false;
      // 这里可以在提交后清空表单数据，如果需要的话
      // this.formData.content = '';
    },
    cancelForm() {
      // 点击取消按钮隐藏表单
      this.showForm = false;
      // 这里可以在取消操作时清空表单数据，如果需要的话
      // this.formData.content = '';
    },
    returnTocourse(){
      this.isPopupVisible = false;
      this.$router.push('/post');
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
      for (let i = localStorage.getItem('coursePostLength'+localStorage.getItem("currentcourse"))-1; i >=0; i--) {
        if (localStorage.getItem('postType'+localStorage.getItem('currentcourse') + i) === 'QUESTION'){
          this.posts.push({
            id: localStorage.getItem('postid' + localStorage.getItem("currentcourse")+i),
            content: localStorage.getItem('post' + localStorage.getItem("currentcourse")+i),
            title: localStorage.getItem('posttitle' + localStorage.getItem("currentcourse")+i),
            author: localStorage.getItem('postauthor' + localStorage.getItem("currentcourse")+i),
          });
        }
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
.assignment-container {
  margin: 20px;
  padding-left: 200px;
}

.assignment-card {
  cursor: pointer;
  transition: box-shadow .3s;
  border: 1px solid gainsboro;
  margin-top: 10px;
  width: 350px;
  height: 150px;

}

.assignment-card:hover {
  box-shadow: 0 4px 6px rgba(0,0,0,0.8);
}
.custom-button {
  background-color: #3498db; /* 更改背景色 */
  color: #ffffff; /* 更改文字颜色 */
  margin-top: 15px;
  /* 可以添加其他样式，如边框、圆角、阴影等 */
}
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

.card-container{
  flex-wrap: wrap;
  display: flex;
  margin: 20px;
  padding-left: 200px;
  //border: 1px solid red;
  height: auto;
}
.post-card{
  cursor: pointer;
  transition: box-shadow .3s;
  //border: 1px solid gainsboro;
  //margin-top: 10px;
  width: 1200px;
  height: 150px;
  border:1px solid #000;
}
.post-card:hover {
  box-shadow: 0 4px 6px rgba(0,0,0,0.8);
}
</style>


