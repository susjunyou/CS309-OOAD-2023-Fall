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
      <el-menu-item index="2" @click="go('course')">Post</el-menu-item>
      <el-menu-item index="3" @click="go('materials')">Materials</el-menu-item>
      <el-menu-item index="4" @click="go('assignments')">Assignments</el-menu-item>
      <el-menu-item index="5" @click="go('projects')">Projects</el-menu-item>
      <el-menu-item index="7" @click="studentClick">members</el-menu-item>
      <el-menu-item index="6" @click="go('gradebook')">Gradebook</el-menu-item>
    </el-menu>
    <!--  <div>-->

    <div v-if="hasJoinedTeam" style="display: flex;">
      <div style="width: 55%; padding-left: 10px;">
        <h2 style=" padding-right: 200px; ">我的队伍信息</h2>
        <el-table :data="myTeam" style="width: 70%; padding-left: 10px; " border stripe>
          <el-table-column prop="name" label="队伍名称"></el-table-column>
          <el-table-column prop="description" label="队伍描述"></el-table-column>
          <el-table-column prop="teammembers" label="团队成员">
            <template slot-scope="scope">
              <ul>
                <li v-for="member in scope.row.teammembers" :key="member.id">
                  {{ member.name }}
                </li>
              </ul>
            </template>
          </el-table-column>
          <!-- 添加更多列来显示团队信息 -->
        </el-table>
        <div class="button-container">
          <button class="sumbitt" @click="() => leaveTeam()">离开小队</button>
          <button class="sumbitt" @click="() => submitproject()">提交project</button>
        </div>

      </div>
      <div style="width: 45%; padding-left: 10px;">
        <h2>申请人信息</h2>
        <div style="height: 300px; overflow-y: auto; border: 1px solid #ccc;">

        <el-table :data="students" style="width: 100%">
          <!-- 其他列定义 -->
          <el-table-column prop="id" label="学号"></el-table-column>
          <el-table-column prop="name" label="姓名"></el-table-column>
          <el-table-column prop="email" label="邮箱"></el-table-column>
          <el-table-column prop="major" label="专业"></el-table-column>
          <!-- 操作列 -->
          <el-table-column label="操作" width="180">
            <template v-slot="scope">
              <el-button type="success" size="small" @click="agreeApplication(scope.row.requestid)">同意</el-button>
              <el-button type="danger" size="small" @click="rejectApplication(scope.row.requestid)">拒绝</el-button>
            </template>
          </el-table-column>
        </el-table>
          </div>
        <div style="width: 100%; padding: 20px 10px;">
          <h2>邀请栏</h2>
          <div style="height: 300px; overflow-y: auto; border: 1px solid #ccc;">
            <el-table :data="studentsnotjointeam" style="width: 100%">
              <el-table-column prop="id" label="学号"></el-table-column>
              <el-table-column prop="name" label="姓名"></el-table-column>
              <el-table-column prop="email" label="邮箱"></el-table-column>
              <el-table-column prop="major" label="专业"></el-table-column>
              <!-- 可以添加更多的列来显示学生信息 -->
              <el-table-column label="操作" width="180">
                <template v-slot="scope">
                  <el-button type="success" size="small" @click="invite(scope.row)" :disabled="scope.row.invited">
                    邀请
                  </el-button>
<!--                  <span v-if="scope.row.invited" style="color: red; margin-left: 10px;">-->
<!--                       无法重复邀请-->
<!--                  </span>-->
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
    </div>


    <div v-else style="display: flex;">
      <div style="width: 55%; padding-left: 10px;">

      <h2>所有可加入的队伍</h2>
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

        <button class="sumbitt" @click="requestTeam(team)">申请加入队伍</button>
      </div>
    </div>
      </div>
      <div style="width: 45%; padding-left: 10px;">
        <div style="height: 150px; border: 1px solid #ccc;">
      <button class="sumbitt" @click="go('createTeam')">创建队伍</button>
        </div>
        <div style="height: 650px; overflow-y: auto; border: 1px solid #ccc;">
          <h2>我的被邀请记录</h2>
          <el-table :data="trueinvitslist" style="width: 100%">
            <el-table-column prop="name" label="队伍名称"></el-table-column>
            <el-table-column prop="teamdescription" label="描述"></el-table-column>
            <el-table-column prop="size" label="人数上限"></el-table-column>
            <el-table-column prop="teammembers" label="当前队员">
              <template v-slot="scope">
        <span v-for="(member, index) in scope.row.teammembers" :key="index">
          {{ member.name }}<span v-if="index < scope.row.teammembers.length - 1">, </span>
        </span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="180">
              <template v-slot="scope">
                <el-button type="success" size="small" @click="agreeinvite(scope.row.inviteid)">同意</el-button>
                <el-button type="danger" size="small" @click="rejectinvite(scope.row.inviteid)">拒绝</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        </div>
    </div>
    <div v-if="isPopupVisible" class="popup">
      <div class="popup-content">
        <p>{{ this.wenzi }}成功！</p>
        <button @click="returnToprotects" class="sumbitt">关闭</button>
      </div>
    </div>
    <div v-if="isPopupVisible2" class="popup">
      <div class="popup-content">
        <p>邀请成功！</p>
        <button @click="returntocurrent" class="sumbitt">关闭</button>
      </div>
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
      major: '',
      id :0,
      email:'',
      name:'',
      materials: [],
      teams: [],
      myValue: '',
      isPopupVisible: false, // 控制弹窗显示的布尔值
      hasJoinedTeam: false,
      myTeam:[],
      sid:0,
      saInfos: [],
      studentInfos: [],
      showSaDialog: false, // 控制SA信息对话框的显示
      showStudentDialog: false, // 控制学生信息对话框的显示
      courseDescription:'',
      wenzi:'',
      // applier:[],
      requeststudent:[],
      students:[],
      count:0,
      studentsnotjointeam:[],
      currentcourseid:0,
      currentprojectid:0,
      isPopupVisible2:false,
      invitlist:[],//邀请他的的那个学生信息
      trueinvitslist:[],//邀请他的的队伍信息
    };
  },



  async created() {
    // this.myValue=localStorage.getItem("currentprojectid")
    this.currentcourseid=localStorage.getItem("currentcourseid");
    this.currentprojectid=localStorage.getItem("currentprojectid");
    this.id = localStorage.getItem('id');
    this.name = localStorage.getItem('name');
    this.major = localStorage.getItem('major');
    this.email = localStorage.getItem('email');
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    await this.loadStudentsAndSA();
    await this.loadstudentnotjointeam();
    await this.getTeam();
    await this.loaddisplay();
    await this.loadbeinvited();
    this.myValue=localStorage.getItem("currentcourse")
    this.myValue=localStorage.getItem("currentcourse");
    this.courseDescription=localStorage.getItem("getdescriptionbyid"+localStorage.getItem("currentcourseid"));
    console.log(this.requeststudent);
  },
  methods: {
    invite(student){
      // if(student.invited==true){
      //   return
      // }
      console.log(localStorage.getItem("myteamid"));
      this.$axios.get('/team/invite',{
        params: {
          teamId: Number(localStorage.getItem("myteamid")),
          studentId: student.id,
          projectId:localStorage.getItem('currentprojectid'),
        }
      }).then(res => {
        console.log(res.data.code);
        // console.log('ddasdawdadwdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdaw');
        if(res.data.code === "0"){
          this.isPopupVisible2=true;
         student.invited=true;
        }else {
        student.invited=true;
          console.log("error")
        }
      }).catch(error => {
        console.log(error)
      })


    },
    agreeinvite(inviteid){
      this.$axios.get('/team/manageInvite',{
        params: {
          id:inviteid,
          isAccepted:true,
        }
      }).then(res => {
        console.log('dd');
        if(res.data.code === "0"){
          this.isJoinedTeam=true;
           this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
          this.loadStudentsAndSA();
          this.loadstudentnotjointeam();
         this.getTeam();
          this.loaddisplay();
           this.loadbeinvited();
        }else {
          console.log("error")
        }
      }).catch(error => {
        console.log(error)
      })
    },
    rejectinvite(inviteid){
      this.$axios.get('/team/manageInvite',{
        params: {
          id:inviteid,
          isAccepted:false,
        }
      }).then(res => {
        console.log('dd');
        if(res.data.code === "0"){
          this.loadbeinvited();
        }else {
          console.log("error")
        }
      }).catch(error => {
        console.log(error)
      })
    },
    returntocurrent(){
      this.isPopupVisible2=false;
    },
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
    async loadstudentinfos() {
      console.log(this.requeststudent.length);
      console.log(this.requeststudent);
      console.log(this.requeststudent[0]);
      for (let i = 0; i < this.requeststudent.length; i++) {
        this.$axios.get('/student/getStudent',{
          params: {
            id:this.requeststudent[i].studentid,
          }
        }).then(res => {
          console.log('ddasdawdadwdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdaw');
          if(res.data.code === "0"){
            this.students.push({
              requestid:this.requeststudent[i].id,
              id:res.data.data.id,
              name:res.data.data.name,
              major:res.data.data.major,
              email:res.data.data.email,
            })
            console.log('sss');
          }else {
            console.log("error")
          }
        }).catch(error => {
          console.log(error)
        })
      }

    },
    async loadstudentnotjointeam(){
      this.studentsnotjointeam = [];
      this.$axios.get('/team/getStudentNotJoinTeam',{
        params: {
          projectId:localStorage.getItem('currentprojectid'),
          courseId:localStorage.getItem('currentcourseid')
        }
      }).then(res => {
        // console.log('ddasdawdadwdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdawdaw');
        if(res.data.code === "0"){
          for (let i = 0; i < res.data.data.length; i++) {
            this.studentsnotjointeam.push({
              invited:false,
              id: res.data.data[i].id,
              name: res.data.data[i].name,
              major: res.data.data[i].major,
              email: res.data.data[i].email,
            })
          } console.log('sss');
        }else {
          console.log("error")
        }
      }).catch(error => {
        console.log(error)
      })
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
   async loaddisplay(){
      this.requeststudent = [];
      this.students = [];
     console.log("myteamid==============="+localStorage.getItem("myteamid"));
      this.$axios.get('/team/getRequestsJoinTeam', {
        params: {
          teamId: Number(localStorage.getItem("myteamid")),
        },
      }).then((res) => {
        console.log("res.data.code==========================="+res.data.code);
        if (res.data.code === "0") {
          for (let i = 0; i < res.data.data.length; i++) {
            this.requeststudent.push({
              id:res.data.data[i].id,
              teamid:res.data.data[i].teamId,
              studentid:res.data.data[i].studentId,
            });
          }
          console.log(this.requeststudent);
          for (let i = 0; i < this.requeststudent.length; i++) {
            this.$axios.get('/student/getStudent',{
              params: {
                id:this.requeststudent[i].studentid,
              }
            }).then(res => {
              if(res.data.code === "0"){
                this.students.push({
                  requestid:this.requeststudent[i].id,
                  id:res.data.data.id,
                  name:res.data.data.name,
                  major:res.data.data.major,
                  email:res.data.data.email,
                })
                console.log('sss');
              }else {
                console.log("error")
              }
            }).catch(error => {
              console.log(error)
            })
          }
        }
      }).catch(error => {
        console.error('Error loading course assignments:', error);
      });
    },
    async loadbeinvited() {
      try {
        this.trueinvitslist = [];
        const response = await this.$axios.get('/team/getInvites', {
          params: {
            studentId: Number(localStorage.getItem("id")),
            projectId: Number(localStorage.getItem("currentprojectid")),
          },
        });

        if (response.data.code === "0") {
          const invites = response.data.data;
          console.log(response.data.data);
          for (let i = 0; i < invites.length; i++) {
            try {
              const res1 = await this.$axios.get('/team/findTeamInfoByTeamId', {
                params: {
                  teamId: invites[i].teamId,
                }
              });
                console.log(res1.data.data);
              if (res1.data.code === "0") {
                try {
                  const res2 = await this.$axios.get('/team/findTeamMembers', {
                    params: {
                      teamId: invites[i].teamId,
                    }
                  });
                  console.log(res2.data.data);
                  if (res2.data.code === "0") {
                    this.trueinvitslist.push({
                      inviteid: invites[i].id,
                      teamid: invites[i].teamId,
                      projectid: invites[i].projectId,
                      teamdescription: res1.data.data.teamDescription,
                      name: res1.data.data.teamName,
                      size: res1.data.data.teamSize,
                      teammembers: res2.data.data,
                    });

                  } else {
                    console.log("error");
                  }
                } catch (error) {
                  console.log(error);
                }
              } else {
                console.log("error");
              }
            } catch (error) {
              console.log(error);
            }

          }
        }
      } catch (error) {
        console.error('Error loading invites:', error);
      }
      console.log(this.trueinvitslist);
    },
  async agreeApplication(requestid) {
      // 同意申请的逻辑
     console.log(requestid);

     this.$axios.get('/team/manageTeamRequest', {
        params: {
          requestId: Number(requestid),
          isAccepted: true,
            },
      }).then((res) => {
        console.log(res.data.code);
        if (res.data.code === "0") {
           this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
           this.loadStudentsAndSA();

           this.getTeam();
           this.loaddisplay();
        }
      }).catch(error => {
        console.error('Error loading course assignments:', error);
      });
    },
    async rejectApplication(requestid) {
      // 拒绝申请的逻辑
      this.$axios.get('/team/manageTeamRequest', {
        params: {
          requestId: Number(requestid),
          isAccepted: false,
        },
      }).then((res) => {
        console.log(res.data.code);
        if (res.data.code === "0") {
           this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
           this.loadStudentsAndSA();

           this.getTeam();
           this.loaddisplay();

        }
      }).catch(error => {
        console.error('Error loading course assignments:', error);
      });
    },
    requestTeam(team){
      this.$axios.get('/team/requestJoinTeam', {
        params: {
          studentId: Number(localStorage.getItem("id")),
          teamId: Number(team.id),
          projectId: Number(localStorage.getItem("currentprojectid")),
        },
      }).then((res) => {
        console.log(res.data.code);
        if (res.data.code === "0") {
          this.wenzi="申请";
          this.isPopupVisible = true;

        }
      }).catch(error => {
        console.error('Error loading course assignments:', error);
      });
    },
    returnToprotects(){
      this.isPopupVisible = false;
      this.isPopupVisible2 = false;
      this.$router.push('/projects');
    },
    leaveTeam() {
      this.$axios.get('/team/leave', {
        params: {
          studentId: Number(localStorage.getItem("id")),
          teamId: Number(localStorage.getItem("myteamid")),
        },
      }).then((res) => {
        if (res.data.code === "0") {
          this.wenzi="离开";
          this.isPopupVisible = true;

        }
      }).catch(error => {
        console.error('Error loading course assignments:', error);
      });
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
    submitproject() {
      // localStorage.setItem("currentprojectid",route.id)
      this.$router.push('projectsubmit');
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
      this.loadLocalStorageData();
      this.loadStudentsAndSA();
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
      this.teams = [];
      this.myTeam=[];
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
            if (res1.data.code === "0" && Array.isArray(res1.data.data)) {
              const isMember = res1.data.data.some(member => member.id === Number(localStorage.getItem('id')));
              if (isMember) {
                this.myTeam.push({
                  id: team.teamId,
                  name: team.teamName,
                  description: team.teamDescription,
                  leader: team.leader,
                  projectid: team.projectId,
                  teamsize: team.teamSize,
                  teammembers: res1.data.data,
                  currentmembercount: res1.data.data ? res1.data.data.length : 0,
                });
                this.hasJoinedTeam = true;
                console.log(this.myTeam);
                localStorage.setItem("myteamid", team.teamId);
              }
            }
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

.clickable-text{
  text-decoration: underline; /* 添加下划线 */
  color: blue; /* 设置为蓝色或其他突出的颜色 */
  cursor: pointer; /* 鼠标悬停时显示手形光标 */
  .clickable-text:hover {
    color: darkblue; /* 悬停时改变颜色 */
  }
}
.sumbitt {
  padding: 10px 20px;
  margin-top: 20px;
  padding: 10px 20px;
  margin-right: 20px;
  background: linear-gradient(45deg, #6dd5ed, #2193b0);
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  font-weight: bold;
  text-transform: uppercase;
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease;
  cursor: pointer;
}
.button-container {
  display: flex;
  justify-content: flex-start; /* 按钮向左对齐 */
  padding-left: 150px; /* 或者增加一些内边距来向右推 */
}
.sumbitt:hover {
  background: linear-gradient(45deg, #2193b0, #6dd5ed);
  box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.3);
}

.sumbitt:active {
  transform: translateY(2px);
}
.team-and-applier-container {
  display: flex;
}

.team-info, .applier-info {
  width: 50%;
  padding: 10px;
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