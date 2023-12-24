<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshansa>
      <div class="attendance-list">
        <hr class="separator">
        <h1>出勤记录</h1>
        <div class="assignment-container">
          <!-- ...之前的代码... -->
          <el-row :gutter="20">
            <el-col v-for="attendance in attendances" :key="attendance.attendanceid" :span="6" >
              <el-card  class="assignment-card" @click.native="gotoattendance(attendance)">
                <h3>{{ attendance.attendanceid }}</h3>
                <p>日期：{{ attendance.attendanceDate }}</p>
              </el-card>
            </el-col>
          </el-row>
          <!-- ...之后的代码... -->

        </div>
        <hr class="separator">
        <h1>作业成绩</h1>
        <div class="assignment-container">
          <!-- ...之前的代码... -->
          <el-row :gutter="20">
            <el-col v-for="assignment in assignments" :key="assignment.id" :span="6" >
              <el-card @click.native="gotoassignment(assignment)" class="assignment-card">
                <h3>{{ assignment.title }}</h3>
                <p>截止日期：{{ assignment.ddl }}</p>
              </el-card>
            </el-col>
          </el-row>
          <!-- ...之后的代码... -->
        </div>
        <hr class="separator">
        <h1>项目成绩</h1>
        <div class="assignment-container">
          <!-- ...之前的代码... -->
          <el-row :gutter="20">
            <el-col v-for="project in projects" :key="project.id" :span="6" >
              <el-card  class="assignment-card" @click.native="gotoproject(project)">
                <h3>{{ project.title }}</h3>
                <p>截止日期：{{ project.ddl }}</p>
              </el-card>
            </el-col>
          </el-row>
          <!-- ...之后的代码... -->

        </div>
      </div>
    </shitshansa>
    <!-- 你的其他内容 -->
  </div>

</template>

<script setup>
import shitshansa from "@/components/shitshansa.vue";
export default {
  components: {
    shitshansa,
  },
  data() {
    return {
      courses: [],
      posts: [],
      materials: [],
      assignments: [],
      projects: [],
      ddls: [],
      attendances:[],
    };
  },
  async created(){
    this.loadLocalStorageData();
  },
  methods: {
    gotoattendance(attendance){
      this.$router.push({path:'/attendancegradebooksa'});
      localStorage.setItem('currentattendanceid',attendance.attendanceid);
      localStorage.setItem('currentattendanceDate',attendance.attendanceDate);
    },
    gotoassignment(assignment){
      this.$router.push({path:'/assignmentgradebooksa'});
      localStorage.setItem('currentassignmentid',assignment.id);
      localStorage.setItem('currentassignmenttitle',assignment.title);
      localStorage.setItem('currentassignmentddl',assignment.ddl);
    },
    gotoproject(project){
      this.$router.push({path:'/projectgradebooksa'});
      localStorage.setItem('currentprojectid',project.id);
      localStorage.setItem('currentprojecttitle',project.title);
      localStorage.setItem('currentprojectddl',project.ddl);
    },
    async loadLocalStorageData() {
      await new Promise((resolve) => setTimeout(resolve, 10)); // 模拟异步操作，这里不是必要的，只是演示用例
      this.courses = [];
      for (let i = 0; i < localStorage.getItem('lengthsa'); i++) {
        this.courses.push({
          id: localStorage.getItem('coursesidsa' + i),
          title: localStorage.getItem('coursessa' + i),
          description: localStorage.getItem('courseDescriptionsa' + i),
          code: localStorage.getItem('coursecodesa' + i),
        });
      }
      this.posts = [];
      for (let i = 0; i < localStorage.getItem('coursePostLength' + localStorage.getItem("currentcourse")); i++) {
        this.posts.push({
          id: localStorage.getItem('postid' + localStorage.getItem("currentcourse") + i),
          content: localStorage.getItem('post' + localStorage.getItem("currentcourse") + i),
          title: localStorage.getItem('posttitle' + localStorage.getItem("currentcourse") + i),
          author: localStorage.getItem('postauthor' + localStorage.getItem("currentcourse") + i),
        });
      }
      this.materials = [];

      for (let i = 0; i < localStorage.getItem('courseMaterialLength' + localStorage.getItem("currentcourse")); i++) {
        this.materials.push({
          id: localStorage.getItem('materialid' + localStorage.getItem("currentcourse") + i),
          name: localStorage.getItem('materialname' + localStorage.getItem("currentcourse") + i),
          description: localStorage.getItem('materialdescription' + localStorage.getItem("currentcourse") + i),
        });
      }
      this.assignments = [];
      for (let i = 0; i < localStorage.getItem('courseAssignmentLength' + localStorage.getItem("currentcourse")); i++) {
        this.assignments.push({
          id: localStorage.getItem('assignmentid' + localStorage.getItem("currentcourse") + i),
          status: localStorage.getItem('assignmentname' + localStorage.getItem("currentcourse") + i),
          title: localStorage.getItem('assignmenttitle' + localStorage.getItem("currentcourse") + i),
          description: localStorage.getItem('assignmentdescription' + localStorage.getItem("currentcourse") + i),
          ddl: localStorage.getItem('assignmentddl' + localStorage.getItem("currentcourse") + i),
        });
        this.ddls.push({
          date: this.assignments[i].ddl,
          title: this.assignments[i].title,
        });
      }
      this.projects = [];
      for (let i = 0; i < localStorage.getItem('projectsLength' + localStorage.getItem("currentcourse")); i++) {
        this.projects.push({
          id: localStorage.getItem('projectid' + localStorage.getItem("currentcourse") + i),
          title: localStorage.getItem('projecttitle' + localStorage.getItem("currentcourse") + i),
          description: localStorage.getItem('projectdescription' + localStorage.getItem("currentcourse") + i),
          startdate: localStorage.getItem('projectstartdate' + localStorage.getItem("currentcourse") + i),
          ddl: localStorage.getItem('projectddl' + localStorage.getItem("currentcourse") + i),
          status: localStorage.getItem('projectstatus' + localStorage.getItem("currentcourse") + i),
          maxpeopleinteam: localStorage.getItem('maxpeopleinteam' + localStorage.getItem("currentcourse") + i),
        });
        this.ddls.push({
          date: this.projects[i].ddl,
          title: this.projects[i].title,
        });
      }
      this.attendances=[];
      this.$axios.get('/course/attendances',{
        params:{
          courseId:localStorage.getItem('currentcourseid')
        }
      }).then((res)=>{
        if(res.data.code==="0"){
          localStorage.setItem('attendanceLength'+localStorage.getItem('currentcourse'),res.data.data.length);
          for(var i=0;i<res.data.data.length;i++){
            localStorage.setItem('attendanceid'+localStorage.getItem('currentcourse')+i,res.data.data[i].id);
            localStorage.setItem('attendanceDate'+localStorage.getItem('currentcourse')+i,res.data.data[i].attendanceDate);
            this.attendances.push({
              attendanceid:localStorage.getItem('attendanceid'+localStorage.getItem('currentcourse')+i),
              attendanceDate:localStorage.getItem('attendanceDate'+localStorage.getItem('currentcourse')+i),
            })
          }
        }
      })

      console.log(this.projects[0])
      console.log(this.projects[1])
      console.log("course name=" + this.myValue)
      console.log("assleng=" + localStorage.getItem('courseAssignmentLength' + localStorage.getItem("currentcourse")))
      console.log("projectleng=" + localStorage.getItem('projectsLength' + localStorage.getItem("currentcourse")))
    },
  }
}

</script>
<style scoped>


.attendance-list {
  margin: 20px; /* 设置外边距 */
  width: 80%;
  padding: 20px; /* 设置内边距 */
  border: 1px solid #ccc; /* 设置边框 */
  border-radius: 8px; /* 设置边框圆角 */
  font-family: Arial, sans-serif; /* 设置字体 */
}
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

</style>