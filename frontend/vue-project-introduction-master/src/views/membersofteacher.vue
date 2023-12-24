


<script setup>
import shitshan from "@/components/shitshan.vue";
export default {

  data() {


    return {
      // 初始化组件数据属性

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
      teachers: [],
      isPopupVisible: false, // 控制弹窗显示的布尔值
      technologystack:"",
      programmingskill:"",
      intendedteammate:"",
    };

  },


  async created() {

    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    await this.loadStudentsAndSA();
    this.courseDescription=localStorage.getItem("getdescriptionbyid"+localStorage.getItem("currentcourseid"));

  },
  methods: {
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
              technologystack: res.data.data[i].technologyStack,
              programmingskill: res.data.data[i].programmingSkill,
              intendedteammate: res.data.data[i].intendedTeammate,
            })
          }
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });

      await this.$axios.get('/course/getTeacher', {
        params: {
          courseId: localStorage.getItem('currentcourseid')
        }
      }).then((res) => {
        if (res.data.code === "0") {
          for (let i = 0; i < res.data.data.length; i++) {
            this.teachers.push({
              email: res.data.data[i].email,
              name: res.data.data[i].name,
              id: res.data.data[i].id,
              major: res.data.data[i].major,
              tenure: res.data.data[i].tenure,
              department: res.data.data[i].department,
              selfIntroduction: res.data.data[i].selfIntroduction,
            })
          }
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });
    },

    go1(route) {
      console.log(route.id)
      localStorage.setItem("currentprojectid",route.id);
      localStorage.setItem("currentprojectmaxpeopleinteam",route.maxpeopleinteam);
      this.$router.push('createTeam');
    },
    go(route) {
      // localStorage.setItem("currentprojectid",route)
      this.$router.push(route);
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
        this.ddls=[];
        this.ddls.push({
          date: this.projects[i].ddl,
          title: this.projects[i].title,
        });
      }

    },  },
  components: {
    shitshan
  }
}


</script>

<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshan>
<el-col :span="21">
        <div class="table-section"  >
          <h2 class="table-title">Teacher Information</h2>
          <el-table :data="teachers" style="width: 90%;" border stripe>
            <el-table-column prop="name" label="Name"></el-table-column>
            <el-table-column prop="tenure" label="Tenure"></el-table-column>
            <el-table-column prop="email" label="Email"></el-table-column>
            <el-table-column prop="department" label="Department"></el-table-column>
          </el-table>
        </div>

        <!-- 学习助手(SA)信息表格 -->
        <div class="table-section" >
          <h2 class="table-title">SA Information</h2>
          <el-table :data="saInfos" style="width: 90%;" border stripe>
            <el-table-column prop="name" label="Name"></el-table-column>
            <el-table-column prop="email" label="Email"></el-table-column>
            <el-table-column prop="major" label="Major"></el-table-column>
          </el-table>
        </div>

        <!-- 学生信息表格 -->
        <div class="table-section" >
          <h2 class="table-title">Student Information</h2>
          <el-table :data="studentInfos" style="width: 90%;" border stripe>
            <el-table-column prop="name" label="Name"></el-table-column>
            <el-table-column prop="email" label="Email"></el-table-column>
            <el-table-column prop="major" label="Major"></el-table-column>
            <el-table-column prop="technologystack" label="TechnologyStack"></el-table-column>
            <el-table-column prop="programmingskill" label="ProgrammingSkill"></el-table-column>
            <el-table-column prop="intendedteammate" label="IntendedTeammate"></el-table-column>
          </el-table>
        </div>

</el-col>

    </shitshan>
    <!-- 你的其他内容 -->
  </div>
</template>


<style scoped>
.table-section {
  margin-left: 100px;
  margin-bottom: 30px; /* 表格间距 */
}
.table-title {
  font-size: 20px;
  color: #333;
  margin-bottom: 10px; /* 标题和表格之间的间距 */
}
shitshan {
  display: flex;
  flex-direction: column; /* 这将使得子元素垂直排列 */
}

</style>