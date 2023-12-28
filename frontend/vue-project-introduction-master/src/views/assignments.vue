
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
    <div class="assignment-container">
      <!-- ...之前的代码... -->
      <el-row :gutter="20">
        <el-col v-for="assignment in assignments" :key="assignment.id" :span="6" >
          <el-card @click.native="submitassignment(assignment)" class="assignment-card" style="min-height: 200px">
            <h3>{{ assignment.title }}</h3>
            <a v-if="assignment.file.downloadUrl"
               :href="assignment.file.downloadUrl"
               :download="assignment.file.fileName"
               @click.stop="handleDownload(assignment)">
              {{ assignment.file.fileName }}
            </a>                <p v-else class="placeholder">没有文件</p>
            <p>截止日期：{{ assignment.ddl }}</p>

          </el-card>
        </el-col>
      </el-row>
      <!-- ...之后的代码... -->

    </div>
    <!--    <p>welcome to {{myValue}}</p>-->
    <!--  </div>-->
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
        technologystack:"",
        programmingskill:"",
        intendedteammate:"",
      },
      // 初始化组件数据属性
      history:[],
      courses: [],
      posts: [],
      assignments: [],
      projects: [],
      materials: [],
      myValue: '',
      ddls: [
        // ...其他DDL
      ],
      attrs: [],
      // 初始化组件数据属性
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
      technologystack:'',
      programmingskill:'',
      intendedteammate:'',
    };
  },


  async created() {
    this.id = localStorage.getItem('id');
    this.name = localStorage.getItem('name');
    this.major = localStorage.getItem('major');
    this.email = localStorage.getItem('email');
    await this.loadLocalStorageData2(); // 使用 async/await 等待数据加载完成
    await this.loadAllCoursesinfo();
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成

    this.myValue=localStorage.getItem("currentcourse")
  },
  methods: {
    async loadLocalStorageData2() {
      this.courses=[];
      for (let i = 0; i < localStorage.getItem('length'); i++) {
        this.courses.push({
          id: localStorage.getItem('coursesid' + i),
          title: localStorage.getItem('courses' + i),
          description: localStorage.getItem('courseDescription' + i),
          code: localStorage.getItem('coursecode' +i),
        });
      }
    },

    handleDownload(assignment){
      console.log("阻止进入"+assignment.title)
    },
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
              this.ddls.push({
                date : res.data.data[i].projectDeadline,
                title : course.title+"   "+res.data.data[i].projectTitle,
              })

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

    submitassignment(assignment){
      localStorage.setItem("currentassignmentid",assignment.id);
      localStorage.setItem("in_ddl",assignment.ddl);//status
      localStorage.setItem("cru_description",assignment.description);
      localStorage.setItem("cru_status",assignment.status);

      this.$router.push({ path: '/assignmentsubmit'});
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
      for (let i = 0; i < localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")); i++) {

        if(localStorage.getItem('assignmentfileid' + localStorage.getItem("currentcourse")+i)!=null&&localStorage.getItem('assignmentfileid' + localStorage.getItem("currentcourse")+i)!="null"){
          const response = await this.$axios.get('/course/file', {
            params: {
              id: localStorage.getItem('assignmentfileid' + localStorage.getItem("currentcourse")+i)
            }
          });
          if (response.data.code === "0") {
            this.file = response.data.data;
            this.fileDownloadUrl = this.createDownloadUrl(this.file.fileData, this.file.fileName, this.file.fileType);
            this.file.downloadUrl = this.fileDownloadUrl;
            this.assignments.push({
              id: localStorage.getItem('assignmentid' + localStorage.getItem("currentcourse")+i),
              status: localStorage.getItem('assignmentstatus' + localStorage.getItem("currentcourse")+i),//assignmentname
              title: localStorage.getItem('assignmenttitle' + localStorage.getItem("currentcourse")+i),
              description: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse")+i),
              ddl: localStorage.getItem('assignmentddl' + localStorage.getItem("currentcourse")+i),
              file:this.file,
            });
          }else {
            this.assignments.push({
              id: localStorage.getItem('assignmentid' + localStorage.getItem("currentcourse")+i),
              status: localStorage.getItem('assignmentstatus' + localStorage.getItem("currentcourse")+i),//assignmentname
              title: localStorage.getItem('assignmenttitle' + localStorage.getItem("currentcourse")+i),
              description: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse")+i),
              ddl: localStorage.getItem('assignmentddl' + localStorage.getItem("currentcourse")+i),
              file: "无文件",
            });
          }
        }else{
          this.assignments.push({
            id: localStorage.getItem('assignmentid' + localStorage.getItem("currentcourse")+i),
            status: localStorage.getItem('assignmentstatus' + localStorage.getItem("currentcourse")+i),//assignmentname
            title: localStorage.getItem('assignmenttitle' + localStorage.getItem("currentcourse")+i),
            description: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse")+i),
            ddl: localStorage.getItem('assignmentddl' + localStorage.getItem("currentcourse")+i),
            file: "无文件",
          });
        }

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
      // console.log(this.projects[0])
      // console.log(this.projects[1])
      // console.log("course name="+this.myValue)
      // console.log("assleng="+localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")))
      // console.log("projectleng="+localStorage.getItem('projectsLength'+localStorage.getItem("currentcourse")))

    },
  },
}
</script>


<style scoped>
.post {
  text-align: left; /* 文本靠左对齐 */
  padding-left: 10px; /* 左侧填充，确保文本不会紧贴边框 */
  border: 1px solid #ccc; /* 维持原有的边框样式 */
  margin-bottom: 20px; /* 保持原有的下边距 */
  padding: 10px; /* 保持原有的内边距 */
}

.course-navbar .menu-item {
  background-color: #545c64;
  color: #fff;
  margin-bottom: 10px; /* 菜单项之间的间隔 */
  padding: 10px; /* 菜单项的内边距 */
  cursor: pointer;
}

.course-navbar .menu-item:hover {
  background-color: #ffd04b;
  color: #545c64;
}
.course-navbar {
  border: none;
  width: 200px; /* 设置导航栏宽度 */
  float: left; /* 使导航栏浮动在左侧 */
  height: 100vh; /* 设置导航栏高度与视口高度相同 */
  padding-top: 20px; /* 在顶部添加一些内边距 */
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

.el-dropdown-link {
  cursor: pointer;
  color: #fff; /* 链接颜色 */
  font-weight: bold;
}
/* ...之前的样式... */

.assignment-container {
  margin: 20px;
  padding-left: 200px;
}

.assignment-card {
  cursor: pointer;
  transition: box-shadow .3s;
  border: 1px solid gainsboro;

}

.assignment-card:hover {
  box-shadow: 0 4px 6px rgba(0,0,0,0.8);
}


/* ...之后的样式... */


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