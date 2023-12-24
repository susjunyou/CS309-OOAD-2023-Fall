<template>
  <div>

    <el-row class="header-bar" background-color="#545c64" text-color="#fff">
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


    <div class="assignment-submission">
      <h1>Assignment Submission</h1>
      <div class="assignment-container">
        <el-row :gutter="20">

          <el-col span="8">
              <el-card class="assignment-card">
                <h3>分数</h3>
                <p>{{finial_grade}}</p>
              </el-card>
          </el-col>

        <el-col span="8">
            <el-card class="assignment-card">
              <h3>Due</h3>
             <p>截止日期：{{in_ddl}}</p>
            </el-card>
        </el-col>

          <el-col span="8">
            <el-card class="assignment-card">
              <h3>状态</h3>
              <p>{{status}}</p>
            </el-card>
          </el-col>
        </el-row>
      </div>
      <div>
        <h2>作业描述</h2>
        <p>{{description}}</p>
      </div>

      <div class="attendance-list">
        <hr class="separator">
        <h1>提交记录</h1>
        <ul>
          <li>
            <table>
              <thead>
              <tr>
                <th>提交时间</th>
                <th>分数</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="assignment in history" :key="assignment.assignmentDeadline">
                <td>{{ assignment.submissionDate }}</td>
                <td>{{ assignment.grade }}</td>
              </tr>
              </tbody>
            </table>
          </li>
        </ul>
      </div>


      <div class="text-submission">
        <textarea v-model="textSubmission" placeholder="Write Submission"></textarea>
      </div>
      <div class="file-upload">
        <input type="file"   @change="onFileSelected"/>
        <!--        @change="handleFileUpload"-->
      </div>
      <div class="button-container">
        <el-button class="submit" @click.prevent="submitAssignment" :disabled="this.disable_submit">Submit</el-button>
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

//import assignments from "@/views/assignments.vue";

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
      isPopupVisible: false, // 控制弹窗显示的布尔值
      materials: [],
      myValue: '',
      textSubmission: '', // 绑定文本提交的数据
      file: null, // 用于存储文件上传的数据
      in_ddl:'',
      history:[],
      description:'',
      status:'',
      finial_grade:'',
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
      major: '',
      technologystack:'',
      programmingskill:'',
      intendedteammate:'',
    };
  },


  async created() {
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    await this.getAssignmentSubmissionHistory();
    this.id = localStorage.getItem('id');
    this.name = localStorage.getItem('name');
    this.major = localStorage.getItem('major');
    this.email = localStorage.getItem('email');
    this.myValue=localStorage.getItem("currentcourse")
    this.in_ddl=localStorage.getItem("in_ddl")
    this.description=localStorage.getItem("cru_description")
    this.status=localStorage.getItem("cru_status")
    await this.loadStudentsAndSA();
    this.myValue=localStorage.getItem("currentcourse");
    this.courseDescription=localStorage.getItem("getdescriptionbyid"+localStorage.getItem("currentcourseid"));
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
    async submitAssignment() {
      // 创建一个新的日期对象
      const submitDate = new Date();
      const formattedDate = submitDate.toISOString().split('T')[0]; // 获取 yyyy-MM-dd 格式

      // 创建 FormData 对象并追加字段
      let formData = new FormData();
      formData.append('studentId', localStorage.getItem("id"));
      formData.append('assignmentId', localStorage.getItem("currentassignmentid"));
      formData.append('content', this.textSubmission);
      formData.append('submitDate', formattedDate);
      if (this.file) {
        formData.append('file', this.file);
      }

      try {
        const res = await this.$axios.post('/student/submitAssignment', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });

        console.log("Response code: " + res.data.code);
        if (res.data.code === "0") {
          this.isPopupVisible = true;
        }
      } catch (error) {
        console.error('Error submitting assignment:', error);
      }
    },

    async  getAssignmentSubmissionHistory() {

      await this.$axios.get('/submission/getAssignmentSubmissionHistory', {
        params: {
          studentId: Number(localStorage.getItem("id")),
          assignmentId: Number(localStorage.getItem("currentassignmentid")),
        }
      }).then((res) => {
        console.log("code====================================="+res.data.code)
        console.log(res.data)
         let dd = localStorage.getItem('in_ddl');
        let n_data = new Date()
        localStorage.setItem('com_as_data',(Number(dd.slice(0,4)) * 400) + (Number(dd.slice(5,7)) * 20) + (Number(dd.slice(8))))
        localStorage.setItem('com_cru_data',(n_data.getFullYear() * 400) + ((n_data.getMonth() + 1) * 20) + (n_data.getDate()))
        this.disable_submit = localStorage.getItem('com_as_data') < localStorage.getItem('com_cru_data');
        if (res.data.code === "0") {
          localStorage.setItem('history_length',res.data.data.length)
          for (let i = 0; i < localStorage.getItem('history_length'); i++) {
            let k = null;
            if (res.data.data[i].grade === null){
              k = '未评分';
            } else {
              k = res.data.data[i].grade;
            }
            if (k!= null && k > this.finial_grade){
              this.finial_grade = k;
            }
            console.log(res.data.data[i].submissionDate)
            this.history.push({
              submissionDate:res.data.data[i].submissionDate,
              grade:k,
              assignmentStatus:res.data.data[i].assignmentStatus,
            })
          }
        }
      }).catch(error => {
        console.error('Error loading course posts:', error);
      });
    },

    onFileSelected(event) {
      // event.target.files 包含了用户选中的文件列表
      this.file = event.target.files[0]; // 保存第一个选中的文件
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
      localStorage.setItem("currentcourse",route.title);
      localStorage.setItem("currentcourseid",route.id);
      this.loadLocalStorageData();
      this.loadStudentsAndSA();
      this.myValue=route.title;
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
.assignment-submission {
  display: flex;
  flex-direction: column;
  align-items: baseline;
  justify-content: initial;
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
.header-bar {
  background-color: cornflowerblue;
  color: #fff;
  line-height: 60px; /* 根据需要调整高度 */
  padding: 0 20px; /* 根据需要调整内边距 */
}
.header-title {
  text-align: right; /* 将文本对齐到右边 */
  padding-right: 100px; /* 或者您需要的任何值，以便向右移动标题 */
}
.header-bar h1 {
  margin: 0; /* 移除默认的margin */
}
.course-navbar {
  border: none;
  width: 200px; /* 设置导航栏宽度 */
  float: left; /* 使导航栏浮动在左侧 */
  height: 100vh; /* 设置导航栏高度与视口高度相同 */
  padding-top: 20px; /* 在顶部添加一些内边距 */
}

.assignment-container {
  margin: 20px;
  padding-left: 20px;
}
.assignment-card {
  cursor: pointer;
  transition: box-shadow .3s;
  border: 1px solid gainsboro;
  height: 150px;
  width: 200px;
  background-color: #00d897;

}

.assignment-card:hover {
  box-shadow: 0 4px 6px rgba(0,0,0,0.8);
}

.history{
  margin: 20px;
  position: absolute;
  padding-left: 900px;
  padding-top: 300px;
}
.history-card{
  cursor: pointer;
  transition: box-shadow .3s;
  border: 1px solid gainsboro;
  height: 150px;
  width: 300px;
  background-color: #00d804;

}


.attendance-list li {
  background: #f3f3f3;
  border: 1px solid #ddd;
  margin-bottom: 0.5em;
  padding: 0.5em;
}
.attendance-list {
  margin: 20px; /* 设置外边距 */
  padding: 20px; /* 设置内边距 */
  border: 1px solid #ccc; /* 设置边框 */
  border-radius: 8px; /* 设置边框圆角 */
  font-family: Arial, sans-serif; /* 设置字体 */
}

.attendance-list h1 {
  font-size: 1.5em; /* 调整标题字体大小 */
  margin-bottom: 1em; /* 调整标题底部间距 */
}

.attendance-list table {
  width: 100%; /* 表格宽度占满容器 */
  border-collapse: collapse; /* 边框合并 */
  margin-bottom: 1em; /* 调整表格底部间距 */
}

.attendance-list th, .attendance-list td {
  border: 1px solid #ccc; /* 设置单元格边框 */
  padding: 8px; /* 设置单元格内边距 */
  text-align: left; /* 文本左对齐 */
}

.attendance-list th {
  background-color: #f2f2f2; /* 表头背景色 */
}

.attendance-list tbody tr:nth-child(even) {
  background-color: #f9f9f9; /* 偶数行背景色 */
}
.main-container {
  position: relative;
}

.attendance-list {
  position: absolute;
  width: 30%;
  left: 1100px; /* 右移 50px，根据需要调整 */
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


.el-dropdown-link {
  cursor: pointer;
  color: #fff; /* 链接颜色 */
  font-weight: bold;
}

/* 自定义profile按钮样式 */


.profile-button {
  color: #fff; /* 文本颜色 */
  /* 其他需要的样式 */
}

</style>
