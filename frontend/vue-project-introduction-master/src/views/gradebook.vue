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

    <div class="attendance-list">
      <hr class="separator">
      <h1>出勤记录</h1>
      <ul>
        <li>
          <table>
            <thead>
            <tr>
              <th>时间</th>
              <th>分数</th>
              <th>比例</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="attendance in attendances" :key="attendance.data">
              <td>{{ attendance.date }}</td>
              <td>{{ attendance.attendancesgrade }}</td>
              <td>{{ attendance.proportion }}</td>
            </tr>
            </tbody>
          </table>
        </li>
      </ul>
      <hr class="separator">
      <h1>作业成绩</h1>
      <ul>
        <li>
          <table>
            <thead>
            <tr>
              <th>名称</th>
              <th>分数</th>
              <th>比例</th>
              <th>备注</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="assignment in assignments" :key="assignment.title">
              <td>{{ assignment.title }}</td>
              <td>{{assignment.assignmentsgrade}}</td>
              <td>{{ assignment.proportion }}</td>
              <td>{{ assignment.gardedescription }}</td>
            </tr>
            </tbody>
          </table>
        </li>
      </ul>
      <hr class="separator">
      <h1>项目成绩</h1>
      <ul>
        <li>
          <table>
            <thead>
            <tr>
              <th>时间</th>
              <th>分数</th>
              <th>比例</th>
              <th>备注</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="project in projects" :key="project.title">
              <td>{{ project.title }}</td>
              <td>{{ project.projectsgrade }}</td>
              <td>{{ project.proportion }}</td>
              <td>{{ project.gardedescription }}</td>
            </tr>
            </tbody>
          </table>
        </li>
      </ul>
      <div id="main123" style="width: 100%; height:400px"></div>
      <h1>底部</h1>
    </div>


  </div>
</template>

<script >
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
        e_selfIntroduction:"",
        technologystack:'',
        programmingskill:'',
        intendedteammate:'',
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
      attendances:[],
      technologystack:'',
      programmingskill:'',
      intendedteammate:'',
      attendancegrade:'',
      assignmentgrade:'',
      projectgrade:'',
    };
  },

  async created() {
    this.id = localStorage.getItem('id');
    this.name = localStorage.getItem('name');
    this.major = localStorage.getItem('major');
    this.email = localStorage.getItem('email');
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    await this.loadStudentsAndSA();
    await this.getClass();
    this.myValue=localStorage.getItem("currentcourse");
    this.courseDescription=localStorage.getItem("getdescriptionbyid"+localStorage.getItem("currentcourseid"));  },
  methods: {
    getClass(){
      // var echarts=require('echarts');
      // var myChart = echarts.init(document.getElementById('main123'));
      // var option={
      //   color:['red','yellow','blue','black'],
      //   title:{
      //     text:'成绩',
      //     x:'center',
      //   },
      //   legend:{
      //     orient:"vertical",
      //     bottom:"bottom",
      //     data:['attendance','assignment','project'],
      //   },
      //   tooltip:{},
      //   series:[{
      //     type:'pie',
      //     data:[{
      //       value:this.attendancegrade,
      //       name:"attendance",
      //     },{
      //       value:this.assignmentgrade,
      //       name:"assignment",
      //     },{
      //       value:this.projectgrade,
      //       name:"project",
      //     },
      //     ],
      //     label:{
      //       fontSize:"16"
      //     },
      //   }],
      // };
      // myChart.setOption(option);
    },
    logoutClick() {
      this.$router.push('/Login');
      localStorage.clear();
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
    goTo(route) {
// 假设使用 Vue Router 进行导航
      localStorage.setItem("currentcourse",route.title);
      localStorage.setItem("currentcourseid",route.id);
      this.loadLocalStorageData();
      this.loadStudentsAndSA();
      this.$router.push({ path: '/course' });
    },
    go(route) {

      this.$router.push(route);
    },
    async loadLocalStorageData() {
      await new Promise((resolve) => setTimeout(resolve, 10)); // 模拟异步操作，这里不是必要的，只是演示用例
      //加载attendances
      await this.$axios.get('/grade/getAttendanceGradeByCourseIdAndStudentId', {
        params: {
          courseId: localStorage.getItem('currentcourseid'),
          studentId: localStorage.getItem('id')
        }
      }).then((res) => {
        if (res.data.code === "0") {
          localStorage.setItem('attendancesLength'+localStorage.getItem('currentcourse'),res.data.data.length)
          for (let i = 0; i < localStorage.getItem('attendancesLength'+localStorage.getItem('currentcourse')); i++) {
            localStorage.setItem('attendancedate'+localStorage.getItem('currentcourse')+i,res.data.data[i].attendanceDate);
            localStorage.setItem('attendanceproportion'+localStorage.getItem('currentcourse')+i,res.data.data[i].proportion);
            if (res.data.data[i].attended) {
              localStorage.setItem('attendancegrade'+localStorage.getItem('currentcourse')+i,100);
            }else {
              localStorage.setItem('attendancegrade'+localStorage.getItem('currentcourse')+i,0);
            }
            localStorage.setItem('attendancemaxScore'+localStorage.getItem('currentcourse')+i,res.data.data[i].maxScore);
          }
        }
      }).catch(error => {
        console.error('Error loading course attendances:', error);
      });
      //加载assignment成绩
      for (let i = 0; i < localStorage.getItem('courseAssignmentLength'+ localStorage.getItem('currentcourse')); i++) {
        await this.$axios.get('/grade/getAssignmentGrade', {
          params: {
            studentId: localStorage.getItem('id'),
            assignmentId: localStorage.getItem('assignmentid'+localStorage.getItem('currentcourse')+i)
          }
        }).then((res) => {
          if (res.data.code === "0") {
            localStorage.setItem('assignmentgrade' + localStorage.getItem('currentcourse') + i, res.data.data.grade);
            localStorage.setItem('assignmentmaxScore' + localStorage.getItem('currentcourse') + i, res.data.data.maxScore);
            localStorage.setItem('assignmentproportion' + localStorage.getItem('currentcourse') + i, res.data.data.proportion);
            localStorage.setItem('assignmentgradeDescription' + localStorage.getItem('currentcourse') + i, res.data.data.gradeDescription)
          }
        }).catch(error => {
          console.error('Error loading assignment grade:', error);
        });
      }
      //加载project成绩
      for (let i = 0; i < localStorage.getItem('projectsLength'+ localStorage.getItem('currentcourse')); i++) {
        await this.$axios.get('/grade/getProjectGrade', {
          params: {
            studentId: localStorage.getItem('id'),
            projectId: localStorage.getItem('projectid'+localStorage.getItem('currentcourse')+i)
          }
        }).then((res) => {
          if (res.data.code === "0") {
            localStorage.setItem('projectgrade' + localStorage.getItem('currentcourse') + i, res.data.data.grade);
            localStorage.setItem('projectmaxScore' + localStorage.getItem('currentcourse') + i, res.data.data.maxScore);
            localStorage.setItem('projectproportion' + localStorage.getItem('currentcourse') + i, res.data.data.proportion);
            localStorage.setItem('projectgradeDescription' + localStorage.getItem('currentcourse') + i, res.data.data.gradeDescription);
          }
        }).catch(error => {
          console.error('Error loading project grade:', error);
        });
      }
      this.courses=[];
      for (let i = 0; i < localStorage.getItem('length'); i++) {
        this.courses.push({
          id: localStorage.getItem('courseid'+i),
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
      this.assignmentgrade=0;
      for (let i = 0; i < localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")); i++) {
        if (localStorage.getItem('assignmentgrade'+localStorage.getItem("currentcourse")+i)!=null){
          this.assignments.push({
            id: localStorage.getItem('assignmentid' + localStorage.getItem("currentcourse")+i),
            status: localStorage.getItem('assignmentname' + localStorage.getItem("currentcourse")+i),
            title: localStorage.getItem('assignmenttitle' + localStorage.getItem("currentcourse")+i),
            description: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse")+i),
            ddl: localStorage.getItem('assignmentddl' + localStorage.getItem("currentcourse")+i),
            assignmentsgrade: localStorage.getItem('assignmentgrade' + localStorage.getItem("currentcourse")+i)+"/"+localStorage.getItem('assignmentmaxScore' + localStorage.getItem('currentcourse') + i),
            proportion: localStorage.getItem('assignmentproportion' + localStorage.getItem("currentcourse")+i),
            gardedescription: localStorage.getItem('assignmentgradeDescription' + localStorage.getItem("currentcourse")+i)
          });
          this.assignmentgrade= Number(localStorage.getItem('assignmentgrade'+localStorage.getItem('currentcourse')+i));
        }
      }
      this.projects=[];
      this.projectgrade=0;
      for (let i = 0; i < localStorage.getItem('projectsLength'+localStorage.getItem("currentcourse")); i++) {
        if(localStorage.getItem('projectgrade'+localStorage.getItem("currentcourse")+i)!=null){
          this.projects.push({
            id: localStorage.getItem('projectid' + localStorage.getItem("currentcourse")+i),
            title: localStorage.getItem('projecttitle' + localStorage.getItem("currentcourse")+i),
            description: localStorage.getItem('projectdescription' + localStorage.getItem("currentcourse")+i),
            startdate: localStorage.getItem('projectstartdate' + localStorage.getItem("currentcourse")+i),
            ddl: localStorage.getItem('projectddl' + localStorage.getItem("currentcourse")+i),
            status: localStorage.getItem('projectstatus' + localStorage.getItem("currentcourse")+i),
            maxpeopleinteam: localStorage.getItem('maxpeopleinteam' + localStorage.getItem("currentcourse")+i),
            projectsgrade: localStorage.getItem('projectgrade' + localStorage.getItem("currentcourse")+i)+"/"+localStorage.getItem('projectmaxScore' + localStorage.getItem("currentcourse") + i),
            proportion: localStorage.getItem('projectproportion' + localStorage.getItem("currentcourse")+i),
            gardedescription: localStorage.getItem('projectgradeDescription' + localStorage.getItem("currentcourse")+i)
          });
          this.projectgrade+=Number(localStorage.getItem('projectgrade'+localStorage.getItem("currentcourse")+i));
        }
        this.ddls =[]
        this.ddls.push({
          date: this.projects[i].ddl,
          title: this.projects[i].title,
        });
      }
      this.attendances=[];
      this.attendancegrade=0;
      for (let i = 0; i < localStorage.getItem('attendancesLength'+localStorage.getItem("currentcourse")); i++) {
        this.attendances.push({
          date: localStorage.getItem('attendancedate' + localStorage.getItem("currentcourse")+i),
          attendancesgrade: localStorage.getItem('attendancegrade' + localStorage.getItem("currentcourse")+i)+'/'+localStorage.getItem('attendancemaxScore' + localStorage.getItem("currentcourse") + i),
          proportion:localStorage.getItem('attendanceproportion' + localStorage.getItem("currentcourse")+i)
        })
        this.attendancegrade+=Number(localStorage.getItem('attendancegrade' + localStorage.getItem("currentcourse")+i));
      }

      console.log("course name="+this.myValue)
      console.log("assleng="+localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")))
      console.log("projectleng="+localStorage.getItem('projectsLength'+localStorage.getItem("currentcourse")))

    },
  },
}
</script>


<style scoped>
.attendance-list h2 {
  margin-bottom: 0.5em;
}

.attendance-list ul {
  list-style-type: none;
  padding: 0;
}

.attendance-list li {
  background: #f3f3f3;
  border: 1px solid #ddd;
  margin-bottom: 0.5em;
  padding: 0.5em;
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
  width: 80%;
  left: 200px; /* 右移 50px，根据需要调整 */
}
.course-navbar {
  border: none;
  width: 200px; /* 设置导航栏宽度 */
  float: left; /* 使导航栏浮动在左侧 */
  height: 100vh; /* 设置导航栏高度与视口高度相同 */
  padding-top: 20px; /* 在顶部添加一些内边距 */
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
</style>