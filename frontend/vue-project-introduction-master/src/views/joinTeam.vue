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
    <!--  <div>-->

    <div v-if="hasJoinedTeam" style="display: flex;">
      <div style="width: 55%; padding-left: 10px;">


        <h2 style=" padding-right: 200px; ">我的队伍信息</h2>
        <div style="height: 200px; overflow-y: auto; ">

        <el-table :data="myTeam" style="width: 100%; padding-left: 10px; " border stripe >
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
          <el-button class="sumbitt" @click="() => editRecruitment()" :disabled="id != myTeam[0].leader">修改招募信息</el-button>
          <el-button class="sumbitt" @click="() => dabian()" >查看答辩信息</el-button>

            <el-switch
                v-model="myTeam[0].public"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="公开"
                inactive-text="隐藏"
                :disabled="id != myTeam[0].leader"
                @change="handleIsPublicChange(myTeam[0])"
                style="margin-top: 30px"
            ></el-switch>

        </div>
        </div>
        <div style="width: 100%; padding: 20px 10px;">
          <h3>未评价小组</h3>
          <div style="height: 200px; overflow-y: auto; border: 1px solid #ccc;">
            <el-table :data="unratedteams" style="width: 100%; padding-left: 10px; " border stripe >
              <el-table-column prop="name" label="队伍名称"></el-table-column>
              <el-table-column prop="description" label="队伍描述"></el-table-column>
              <el-table-column prop="teammembers" label="团队成员">
                <template slot-scope="scope">
                  <ul v-if="scope.row.public">
                    <li v-for="member in scope.row.teammembers" :key="member.id">
                      {{ member.name }}
                    </li>
                  </ul>
                  <span v-else>信息不公开</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="180">
                <template v-slot="scope">
                  <!-- 只有当用户是领导时，显示评价按钮 -->
                  <el-button v-if="isleader" type="success" size="small" @click="evaluateTeam(scope.row.id)">
                    评价
                  </el-button>
                </template>
              </el-table-column>
              <!-- 添加更多列来显示团队信息 -->
            </el-table>
          </div>
          <h3>已评价小组</h3>
          <div style="height: 200px; overflow-y: auto; border: 1px solid #ccc;">
            <el-table :data="ratedteams" style="width: 100%; padding-left: 10px; " border stripe >
              <el-table-column prop="name" label="队伍名称"></el-table-column>
              <el-table-column prop="description" label="队伍描述"></el-table-column>
              <el-table-column prop="teammembers" label="团队成员">
                <template slot-scope="scope">
                  <ul v-if="scope.row.public">
                    <li v-for="member in scope.row.teammembers" :key="member.id">
                      {{ member.name }}
                    </li>
                  </ul>
                  <span v-else>信息不公开</span>
                </template>
              </el-table-column>
              <!-- 添加更多列来显示团队信息 -->
            </el-table>
          </div>
        </div>

      </div>
      <div style="width: 45%; padding-left: 10px;">
        <h2>申请人信息</h2>
        <div style="height: 327px; overflow-y: auto; border: 1px solid #ccc;">

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
          <div style="height: 327px; overflow-y: auto; border: 1px solid #ccc;">
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
        <p>当前人数: {{ team.currentmembercount}} / 最多人数: {{ team.teamsize }}</p>
        <!-- 显示成员列表 -->
        <ul v-if="team.public">
          <li v-for="(member, index) in team.teammembers" :key="member.id">
            成员{{ index + 1 }} 姓名: {{ member.name }} id：{{ member.id }}
          </li>
        </ul>
        <p v-else>信息不公开</p>

        <el-button class="sumbitt" @click.stop="descrip(team)" v-if="team.public">查看招募信息</el-button>
        <button class="sumbitt" @click="requestTeam(team)">申请加入队伍</button>


      </div>
    </div>
      </div>
      <div style="width: 45%; padding-left: 10px;">
        <div style="height: 150px; border: 1px solid #ccc;">
      <button class="sumbitt" @click.stop="go2('createTeam')">创建队伍</button>
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
    <div v-if="isPopupVisible3" class="popup">
      <div class="popup-content">
        <p>打分成功！</p>
        <button @click="returntocurrent23" class="sumbitt">关闭</button>
      </div>
    </div>
    <el-dialog title="修改作业" :visible.sync="dialogVisible2">
      <el-form :model="editAssignmentForm">
        <el-form-item label="评价">
          <el-input  type="textarea" v-model="editAssignmentForm.comment" :rows="20"></el-input>
        </el-form-item>
        <el-form-item label="打分">
          <el-input-number v-model="editAssignmentForm.grade"></el-input-number>
        </el-form-item>

        <!-- courseId通常是选择的课程或从其他途径获得，这里假设是隐藏字段 -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取消</el-button>
        <el-button type="primary" @click.stop="commitEdit">确定</el-button>
      </div>
    </el-dialog>
    <div v-if="isPopupVisible4" class="popup">
      <div class="popup-content">
        <p>{{ this.message }}</p>
        <button @click="isPopupVisible4=false" class="sumbitt">关闭</button>
      </div>
    </div>

    <el-dialog
        title="答辩信息"
        :visible.sync="dialogVisible3"
        width="50%"
    >

      <p v-if="myTeam.length && (myTeam[0].presentationDate == null || myTeam[0].presentationDate == '' || myTeam[0].presentationDate == 'null' || myTeam[0].presentationDate == undefined)">
        答辩时间：暂未确定，请等待老师安排
      </p>
      <p v-else-if="myTeam.length">
        答辩时间：{{ myTeam[0].presentationDate }}
      </p>
      <p v-if="myTeam.length && (myTeam[0].teachername == '' || myTeam[0].teachername == null || myTeam[0].teachername == undefined || myTeam[0].teachername == 'null')">
        答辩老师：暂未确定，请等待老师安排
      </p>
      <p v-else-if="myTeam.length">
        答辩老师：{{ myTeam[0].teachername }}
      </p>
      <el-button @click="dialogVisible3 = false">返回</el-button>


    </el-dialog>

    <el-dialog
        title="修改招募信息"
        :visible.sync="recruitmentDialogVisible"
        width="50%"
    >
      <el-form :model="editRecruitmentForm">

        <el-form-item label="小队名称">
          <el-input
              type="textarea"
              v-model="editRecruitmentForm.teamName"
              :rows="1"
          ></el-input>
        </el-form-item>
        <el-form-item label="小队描述">
          <el-input
              type="textarea"
              v-model="editRecruitmentForm.teamDescription"
              :rows="4"
          ></el-input>
        </el-form-item>
        <el-form-item label="招募信息">
          <el-input
              type="textarea"
              v-model="editRecruitmentForm.recruitmentInformation"
              :rows="4"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="recruitmentDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="commitRecruitmentEdit">确认</el-button>
      </div>
    </el-dialog>
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
      isleader:false,
      wenzi:'',
      // applier:[],
      requeststudent:[],
      students:[],
      count:0,
      studentsnotjointeam:[],
      currentcourseid:0,
      currentprojectid:0,
      isPopupVisible2:false,
      isPopupVisible3:false,
      isPopupVisible4:false,
      invitlist:[],//邀请他的的那个学生信息
      trueinvitslist:[],//邀请他的的队伍信息
      technologystack:'',
      programmingskill:'',
      intendedteammate:'',
      teamname:'',
      ratedteams:[],
      unratedteams:[],
      dialogVisible2:false,
      dialogVisible3:false,
      message:'',
      recruitmentDialogVisible: false,
      editRecruitmentForm: {
        recruitmentInformation: '',
        teamName:'',
        teamDescription: '',
      },
      teamcopy:[],
      editAssignmentForm: {
        comment: '',
        grade: '',
      },
      teamddl:''
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
    console.log(this.teamddl)
    await this.loadLocalStorageData();
    await this.loadAllCoursesinfo();
    await this.loadLocalStorageData();
    await this.loadStudentsAndSA();
    await this.loadstudentnotjointeam();
    await this.getTeam();
    await this.loaddisplay();
    await this.loadbeinvited();
    await this.getunratedteams();
    await this.getratedteams();
    console.log(this.isleader)
    this.myValue=localStorage.getItem("currentcourse")
    this.myValue=localStorage.getItem("currentcourse");
    this.courseDescription=localStorage.getItem("getdescriptionbyid"+localStorage.getItem("currentcourseid"));
    console.log(this.requeststudent);
    console.log(this.id)
    console.log(this.myTeam.length)
    console.log(this.teams)
  },

  methods: {
    createDownloadUrl(base64, fileName, mimeType) {
      const blob = this.base64ToBlob(base64, mimeType);
      const downloadUrl = URL.createObjectURL(blob);
      return downloadUrl;
    },
    base64ToBlob(base64, mimeType) {
      const byteCharacters = atob(base64);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      return new Blob([byteArray], {type: mimeType});
    },
    async loadAllCoursesinfo() {
      for (let course of this.courses) {
        //加载posts
        await this.$axios.get('/course/posts', {
          params: {
            courseId: course.id
          }
        }).then((res) => {
          console.log("post"+course.title+res.data.data)
          if (res.data.code === "0") {
            localStorage.setItem('coursePostLength'+course.title,res.data.data.length)
            for (let i = 0; i < localStorage.getItem('coursePostLength'+course.title); i++) {
              localStorage.setItem('postid'+course.title+i,res.data.data[i].postId);
              localStorage.setItem('post'+course.title+i,res.data.data[i].postContent);
              localStorage.setItem('posttitle'+course.title+i,res.data.data[i].postTitle);
              localStorage.setItem('postauthor'+course.title+i,res.data.data[i].postAuthor);
              localStorage.setItem('postType'+course.title+i,res.data.data[i].postType);
              this.posts.push({
                course:course.title,
                id:res.data.data[i].postId,
                title:res.data.data[i].postTitle,
                content:res.data.data[i].postContent,
                author:res.data.data[i].postAuthor,
              })
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
          console.log("materials"+course.title+res.data.data)
          console.log(res.data)
          if (res.data.code === "0") {
            localStorage.setItem('courseMaterialLength'+course.title,res.data.data.length)
            for (let i = 0; i < localStorage.getItem('courseMaterialLength'+course.title); i++) {
              localStorage.setItem('materialid'+course.title+i,res.data.data[i].id);
              localStorage.setItem('materialname' + course.title + i, res.data.data[i].name);
              localStorage.setItem('materialdescription' + course.title + i, res.data.data[i].description);
              localStorage.setItem('materialfileid'+course.title+i,res.data.data[i].fileId);
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
          console.log("assignments"+course.title+res.data.data)

          if (res.data.code === "0") {
            localStorage.setItem('courseAssignmentLength'+course.title,res.data.data.length)
            for (let i = 0; i < localStorage.getItem('courseAssignmentLength'+course.title); i++) {
              localStorage.setItem('assignmentid'+course.title+i,res.data.data[i].id);
              localStorage.setItem('assignmentstatus'+course.title+i,res.data.data[i].assignmentStatus);
              localStorage.setItem('assignmenttitle'+course.title+i,res.data.data[i].assignmentTitle);
              localStorage.setItem('assignmentdescription'+course.title+i,res.data.data[i].assignmentDescription);
              localStorage.setItem('assignmentddl'+course.title+i,res.data.data[i].assignmentDeadline);
              localStorage.setItem('assignmentfileid'+course.title+i,res.data.data[i].fileId);
              this.ddls.push({
                date : res.data.data[i].assignmentDeadline,
                title : course.title+"   "+res.data.data[i].assignmentTitle,
              })

            }
          }else{
            localStorage.setItem('courseAssignmentLength'+course.title,0)

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
            console.log("project"+course.title+res.data.data)

            localStorage.setItem('projectsLength'+course.title,res.data.data.length)
            console.log(localStorage.getItem('projectsLength'+course.title))
            for (let i = 0; i < localStorage.getItem('projectsLength'+course.title); i++) {
              localStorage.setItem('projectid'+course.title+i,res.data.data[i].id);
              localStorage.setItem('projecttitle'+course.title+i,res.data.data[i].projectTitle);
              localStorage.setItem('projectdescription'+course.title+i,res.data.data[i].projectDescription);
              localStorage.setItem('projectstartdate'+course.title+i,res.data.data[i].projectStartDate);
              localStorage.setItem('projectddl'+course.title+i,res.data.data[i].projectDeadline);
              localStorage.setItem('projectstatus'+course.title+i,res.data.data[i].projectStatus);
              localStorage.setItem('maxpeopleinteam'+course.title+i,res.data.data[i].maxPeopleInTeam);
              localStorage.setItem('projectfileid'+course.title+i,res.data.data[i].fileId);
              localStorage.setItem('teamddl' + course.title+i,res.data.data[i].teamDeadline);
              this.ddls.push({
                date : res.data.data[i].projectDeadline,
                title : course.title+"   "+res.data.data[i].projectTitle,
              })
              console.log(res.data.data.length)
              console.log(res.data.data[i])
            }
          }else {
            localStorage.setItem('projectsLength'+course.title,0)

            console.log("error")
          }
        }).catch(error => {
          console.error('Error loading course projects:', error);
        });
        //加载attendances
        await this.$axios.get('/grade/getAttendanceGradeByCourseIdAndStudentId', {
          params: {
            courseId: course.id,
            studentId: localStorage.getItem('id')
          }
        }).then((res) => {
          console.log("grade"+course.title+res.data.data)

          if (res.data.code === "0") {
            localStorage.setItem('attendancesLength'+course.title,res.data.data.length)
            for (let i = 0; i < localStorage.getItem('attendancesLength'+course.title); i++) {
              localStorage.setItem('attendancedate'+course.title+i,res.data.data[i].attendanceDate);
              localStorage.setItem('attendanceproportion'+course.title+i,res.data.data[i].proportion);
              if (res.data.data[i].attended) {
                localStorage.setItem('attendancegrade'+course.title+i,100);
              }else {
                localStorage.setItem('attendancegrade'+course.title+i,0);
              }
              localStorage.setItem('attendancemaxScore'+course.title+i,res.data.data[i].maxScore);
            }
          }
        }).catch(error => {
          console.error('Error loading course attendances:', error);
        });
        //加载assignment成绩
        for (let i = 0; i < localStorage.getItem('courseAssignmentLength'+ course.title); i++) {
          await this.$axios.get('/grade/getAssignmentGrade', {
            params: {
              studentId: localStorage.getItem('id'),
              assignmentId: localStorage.getItem('assignmentid'+course.title+i)
            }
          }).then((res) => {
            console.log("grade2"+course.title+res.data.data)

            if (res.data.code === "0") {
              localStorage.setItem('assignmentgrade' + course.title + i, res.data.data[0].grade);
              localStorage.setItem('assignmentmaxScore' + course.title + i, res.data.data[0].maxScore);
              localStorage.setItem('assignmentproportion' + course.title + i, res.data.data[0].proportion);
              localStorage.setItem('assignmentgradeDescription' + course.title + i, res.data.data[0].gradeDescription)
            }
          }).catch(error => {
            console.error('Error loading assignment grade:', error);
          });
        }
        //加载project成绩
        for (let i = 0; i < localStorage.getItem('projectsLength'+ course.title); i++) {
          await this.$axios.get('/grade/getProjectGrade', {
            params: {
              studentId: localStorage.getItem('id'),
              projectId: localStorage.getItem('projectid'+course.title+i)
            }
          }).then((res) => {
            console.log("grade3"+course.title+res.data.data)

            if (res.data.code === "0") {
              localStorage.setItem('projectgrade' + course.title + i, res.data.data[0].grade);
              localStorage.setItem('projectmaxScore' + course.title + i, res.data.data[0].maxScore);
              localStorage.setItem('projectproportion' + course.title + i, res.data.data[0].proportion);
              localStorage.setItem('projectgradeDescription' + course.title + i, res.data.data[0].gradeDescription);
            }
          }).catch(error => {
            console.error('Error loading project grade:', error);
          });
        }
      }
    },

    async getddl(){
     const res= await this.$axios.get('/project/getProjectByCourseId',{
        params: {
          courseId:localStorage.getItem("currentcourseid"),
        },

      })
      if(res.data.code==0){
        for(let i=0;i<res.data.data.length;i++){
          if(res.data.data[i].id==localStorage.getItem("currentprojectid")){
            this.teamddl=res.data.data[i].TeamDeadline;
          }
        }
      }
    },
  async   handleIsPublicChange(myteam){
       await this.$axios.get('/team/modifyPrivacy',{
        params: {
          teamId:myteam.id,
          privacyTeam:myteam.public,
        },

      })
    console.log(this.myTeam[0].public)
    },
    dabian(){

      this.dialogVisible3=true;
    },
    editRecruitment() {
      // 加载当前招募信息到表单
      this.editRecruitmentForm.recruitmentInformation = this.myTeam[0].recruitmentInformation;
      this.editRecruitmentForm.teamName = this.teamcopy[0].teamName;
      this.editRecruitmentForm.teamDescription = this.teamcopy[0].teamDescription;
      this.recruitmentDialogVisible = true;
    },
    async commitRecruitmentEdit() {
     const res= await this.$axios.get('/team/updateTeamInfo',{
        params: {
          teamId: this.teamcopy[0].teamId,
          recruitmentInformation: this.editRecruitmentForm.recruitmentInformation,
          teamName: this.editRecruitmentForm.teamName,
          projectId:this.teamcopy[0].projectId,
          leader:this.teamcopy[0].leader,
          teamDescription:this.editRecruitmentForm.teamDescription,

          teamSize:this.teamcopy[0].teamSize,

          teamMembers:this.teamcopy[0].teamMembers,

          teacherId:this.teamcopy[0].teacherId,

          presentationDate:this.teamcopy[0].presentationDate,

        }

      })
      if(res.data.code === "0"){
        await this.getTeam();
        this.recruitmentDialogVisible = false;
        this.message='修改成功';
        this.isPopupVisible4=true;
      }
    },
    descrip(team){
      this.message=team.recruitmentInformation,

          this.isPopupVisible4=true;
    },
  async  commitEdit(){
    const res=await  this.$axios.get('/team/updateTeamPeerRevision',{
      params: {
        team1:localStorage.getItem("myteamid"),
        team2:localStorage.getItem("ratingteamid"),
        grade:this.editAssignmentForm.grade,
        comment:this.editAssignmentForm.comment,
        projectId:localStorage.getItem('currentprojectid'),
      }

    })
    if(res.data.code === "0"){

      this.dialogVisible2=false;
      this.isPopupVisible3=true;
    }
    },
  async  evaluateTeam(id){
    localStorage.setItem("ratingteamid",id);
this.dialogVisible2=true;
    },
   async getratedteams(){
     this.ratedteams=[];
    const res=await  this.$axios.get('/team/getAlreadyPeerGradeTeam',{
        params: {
          teamId: Number(localStorage.getItem("myteamid")),
          projectId:localStorage.getItem('currentprojectid'),
        }
      })
     console.log(res.data);
     if(res.data.code === "0"){
       for (let i = 0; i < res.data.data.length; i++) {
         const team = res.data.data[i];
         const res1 = await this.$axios.get('/team/findTeamMembers', {
           params: {
             teamId: team.teamId
           }
         });
         if (res1.data.code === "0") {
           this.ratedteams.push({
             id: team.teamId,
             name: team.teamName,
             description: team.teamDescription,
             leader: team.leader,
             projectid: team.projectId,
             teamsize: team.teamSize,
             teammembers: res1.data.data,
             currentmembercount: res1.data.data ? res1.data.data.length : 0,
             public: team.privacyTeam,
           });
         }
       }
     }
     console.log(this.ratedteams);
    },
    async getunratedteams(){
     this.unratedteams=[];
     console.log(localStorage.getItem("myteamid"));
     console.log(localStorage.getItem('currentprojectid'));
      const res=await  this.$axios.get('/team/getNotYetPeerGradeTeam',{
        params: {
          teamId: Number(localStorage.getItem("myteamid")),
          projectId:localStorage.getItem('currentprojectid'),
        }
      })
      console.log(res.data);

      if(res.data.code === "0"){
        for (let i = 0; i < res.data.data.length; i++) {
          const team = res.data.data[i];
          const res1 = await this.$axios.get('/team/findTeamMembers', {
            params: {
              teamId: team.teamId
            }
          });
          if (res1.data.code === "0") {
            this.unratedteams.push({
              id: team.teamId,
              name: team.teamName,
              description: team.teamDescription,
              leader: team.leader,
              projectid: team.projectId,
              teamsize: team.teamSize,
              teammembers: res1.data.data,
              currentmembercount: res1.data.data ? res1.data.data.length : 0,
              public:team.privacyTeam,
            });
          }
        }
      }
      console.log(this.unratedteams);
    },



    invite(student){
      // if(student.invited==true){
      //   return
      // }
      if (new Date() > new Date(this.teamddl)) {
        alert('已过团队加入截止日期，不能邀请加入队伍。');
        return;
      }
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
        }else {
        student.invited=true;
          console.log("error")
        }
      }).catch(error => {
        console.log(error)
      })


    },
    agreeinvite(inviteid){
      if (new Date() > new Date(this.teamddl)) {
        alert('已过团队加入截止日期，不能处理组队邀请。');
        return;
      }
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
      if (new Date() > new Date(this.teamddl)) {
        alert('已过团队加入截止日期，不能处理组队邀请。');
        return;
      }
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
   async returntocurrent23(){

    await this.getunratedteams()
     await this.getratedteams()
      this.isPopupVisible3=false;
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
    if (new Date() > new Date(this.teamddl)) {
      alert('已过团队加入截止日期，不能处理申请入队。');
      return;
    }
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
           this.loadbeinvited();
           this.loadstudentnotjointeam()
        }
      }).catch(error => {
        console.error('Error loading course assignments:', error);
      });
    },
    async rejectApplication(requestid) {
      if (new Date() > new Date(this.teamddl)) {
        alert('已过团队加入截止日期，不能处理入队请求。');
        return;
      }
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
      if (new Date() > new Date(this.teamddl)) {
        alert('已过团队加入截止日期，不能申请加入队伍。');
        return;
      }
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

    go2(route) {
      if (new Date() > new Date(this.teamddl)) {
        alert('已过团队加入截止日期，不能申请加入队伍。');
        return;
      }else{this.$router.push(route);}
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
      console.log(localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")))
      // for (let i = 0; i < localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")); i++) {
      //
      //   if(localStorage.getItem('assignmentfileid' + localStorage.getItem("currentcourse")+i)!=null&&localStorage.getItem('assignmentfileid' + localStorage.getItem("currentcourse")+i)!="null"){
      //     const response = await this.$axios.get('/course/file', {
      //       params: {
      //         id: localStorage.getItem('assignmentfileid' + localStorage.getItem("currentcourse")+i)
      //       }
      //     });
      //     if (response.data.code === "0") {
      //       this.file = response.data.data;
      //       this.fileDownloadUrl = this.createDownloadUrl(this.file.fileData, this.file.fileName, this.file.fileType);
      //       this.file.downloadUrl = this.fileDownloadUrl;
      //       this.assignments.push({
      //         id: localStorage.getItem('assignmentid' + localStorage.getItem("currentcourse")+i),
      //         status: localStorage.getItem('assignmentstatus' + localStorage.getItem("currentcourse")+i),//assignmentname
      //         title: localStorage.getItem('assignmenttitle' + localStorage.getItem("currentcourse")+i),
      //         description: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse")+i),
      //         ddl: localStorage.getItem('assignmentddl' + localStorage.getItem("currentcourse")+i),
      //         file:this.file,
      //       });
      //     }else {
      //       this.assignments.push({
      //         id: localStorage.getItem('assignmentid' + localStorage.getItem("currentcourse")+i),
      //         status: localStorage.getItem('assignmentstatus' + localStorage.getItem("currentcourse")+i),//assignmentname
      //         title: localStorage.getItem('assignmenttitle' + localStorage.getItem("currentcourse")+i),
      //         description: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse")+i),
      //         ddl: localStorage.getItem('assignmentddl' + localStorage.getItem("currentcourse")+i),
      //         file: "无文件",
      //       });
      //     }
      //   }else{
      //     this.assignments.push({
      //       id: localStorage.getItem('assignmentid' + localStorage.getItem("currentcourse")+i),
      //       status: localStorage.getItem('assignmentstatus' + localStorage.getItem("currentcourse")+i),//assignmentname
      //       title: localStorage.getItem('assignmenttitle' + localStorage.getItem("currentcourse")+i),
      //       description: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse")+i),
      //       ddl: localStorage.getItem('assignmentddl' + localStorage.getItem("currentcourse")+i),
      //       file: "无文件",
      //     });
      //   }
      //
      //   this.ddls.push({
      //     date: this.assignments[i].ddl,
      //     title: this.assignments[i].title,
      //   });
      // }
      this.projects=[];
      for (let i = 0; i < localStorage.getItem('projectsLength'+localStorage.getItem("currentcourse")); i++) {
        if(localStorage.getItem('projectid' + localStorage.getItem("currentcourse")+i)==localStorage.getItem("currentprojectid")){
          this.teamddl= localStorage.getItem('teamddl' + localStorage.getItem("currentcourse")+i);
        }



        if(localStorage.getItem('projectfileid' + localStorage.getItem("currentcourse")+i)!=null&&localStorage.getItem('projectfileid' + localStorage.getItem("currentcourse")+i)!="null"){
          const response = await this.$axios.get('/course/file', {
            params: {
              id: localStorage.getItem('projectfileid' + localStorage.getItem("currentcourse")+i)
            }
          });
          if (response.data.code === "0") {



            this.file = response.data.data;
            this.fileDownloadUrl = this.createDownloadUrl(this.file.fileData, this.file.fileName, this.file.fileType);
            this.file.downloadUrl = this.fileDownloadUrl;
            this.projects.push({
              id: localStorage.getItem('projectid' + localStorage.getItem("currentcourse")+i),
              title: localStorage.getItem('projecttitle' + localStorage.getItem("currentcourse")+i),
              description: localStorage.getItem('projectdescription' + localStorage.getItem("currentcourse")+i),
              startdate: localStorage.getItem('projectstartdate' + localStorage.getItem("currentcourse")+i),
              ddl: localStorage.getItem('projectddl' + localStorage.getItem("currentcourse")+i),
              status: localStorage.getItem('projectstatus' + localStorage.getItem("currentcourse")+i),
              maxpeopleinteam: localStorage.getItem('maxpeopleinteam' + localStorage.getItem("currentcourse")+i),
              teamddl: localStorage.getItem('teamddl' + localStorage.getItem("currentcourse")+i),
              file:this.file,
            });
          }else {
            this.projects.push({
              id: localStorage.getItem('projectid' + localStorage.getItem("currentcourse")+i),
              title: localStorage.getItem('projecttitle' + localStorage.getItem("currentcourse")+i),
              description: localStorage.getItem('projectdescription' + localStorage.getItem("currentcourse")+i),
              startdate: localStorage.getItem('projectstartdate' + localStorage.getItem("currentcourse")+i),
              ddl: localStorage.getItem('projectddl' + localStorage.getItem("currentcourse")+i),
              status: localStorage.getItem('projectstatus' + localStorage.getItem("currentcourse")+i),
              maxpeopleinteam: localStorage.getItem('maxpeopleinteam' + localStorage.getItem("currentcourse")+i),
              teamddl: localStorage.getItem('teamddl' + localStorage.getItem("currentcourse")+i),
              file: "无文件",
            });
          }
        }else{
          this.projects.push({
            id: localStorage.getItem('projectid' + localStorage.getItem("currentcourse")+i),
            title: localStorage.getItem('projecttitle' + localStorage.getItem("currentcourse")+i),
            description: localStorage.getItem('projectdescription' + localStorage.getItem("currentcourse")+i),
            startdate: localStorage.getItem('projectstartdate' + localStorage.getItem("currentcourse")+i),
            ddl: localStorage.getItem('projectddl' + localStorage.getItem("currentcourse")+i),
            status: localStorage.getItem('projectstatus' + localStorage.getItem("currentcourse")+i),
            maxpeopleinteam: localStorage.getItem('maxpeopleinteam' + localStorage.getItem("currentcourse")+i),
            teamddl: localStorage.getItem('teamddl' + localStorage.getItem("currentcourse")+i),
            file: "无文件",
          });
        }

        this.ddls.push({
          date: this.projects[i].ddl,
          title: this.projects[i].title,
        });
      }
      // console.log(this.projects[0])
      // console.log(this.projects[1])
      // console.log("course name="+this.myValue)
      // console.log("assleng="+localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")))
      // console.log("projectleng="+localStorage.getItem('projectsLength'+localStorage.getItem("currentcourse")))

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
        console.log(res.data.data)

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
            console.log(res1.data.data)

            if (res1.data.code === "0" && Array.isArray(res1.data.data)) {
              const isMember = res1.data.data.some(member => member.id === Number(localStorage.getItem('id')));
              if (isMember) {
                console.log(team.leader)
                if(team.teacherId!=0&&team.teacherId!=null&&team.teacherId!=undefined&&team.teacherId!='null'){
                  const res5 = await this.$axios.get('/teacher/getTeacherInfoById', {
                    params: {
                      id: team.teacherId,
                    }
                  });
                  console.log(res5.data);
                  if(res5.data.code==0){
                    this.teachername=res5.data.data.name;
                  }
                }
                console.log(team)

                this.myTeam.push({
                  id: team.teamId,
                  name: team.teamName,
                  description: team.teamDescription,
                  leader: team.leader,
                  projectid: team.projectId,
                  teamsize: team.teamSize,
                  teammembers: res1.data.data,
                  currentmembercount: res1.data.data ? res1.data.data.length : 0,
                  recruitmentInformation:team.recruitmentInformation,
                  presentationDate:team.presentationDate,
                  teachername:this.teachername,
                  public:team.privacyTeam,
                });
                console.log(this.teachername);
                this.teamcopy.push(team),
                this.hasJoinedTeam = true;
                console.log(isMember);
                console.log(team.leader);
                if(team.leader==localStorage.getItem('id')){
                  this.isleader=true;
                }
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
                recruitmentInformation:team.recruitmentInformation,
                public:team.privacyTeam,

              });
            }else {
              this.teams.push({
                id: team.teamId,
                name: team.teamName,
                description: team.teamDescription,
                leader: team.leader,
                projectid: team.projectId,
                teamsize: team.teamSize,
                teammembers:null,
                currentmembercount:  0,
                recruitmentInformation:team.recruitmentInformation,
                public:team.privacyTeam,

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