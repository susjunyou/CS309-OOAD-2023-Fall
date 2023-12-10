<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshan>

      <div class="assignments-wrapper">
        <!-- 左侧部分 -->
        <div class="left-panel">
          <!-- 已提交学生信息 -->
          <div class="submitted-students">
            <h3>已提交学生信息</h3>
            <el-table
                :data="ungradedstudents"
                border
                style="height: 50%;"
                @row-click="handleRowClick">
              <el-table-column
                  prop="name"
                  label="学生姓名"
              ></el-table-column>
              <el-table-column
                  prop="email"
                  label="学生邮箱"
              ></el-table-column>
              <el-table-column
                  prop="major"
                  label="学生专业"
              ></el-table-column>
              <el-table-column
                  prop="status"
                  label="批改状态"
              ></el-table-column>
              <!-- Other columns as needed -->
            </el-table>
          </div>

          <!-- 未提交学生信息 -->
          <div class="unsubmitted-students">
            <h3>未提交学生信息</h3>
            <el-table
                :data="unsubmittedStudents"
                border
                style="height: 50%;">
              <el-table-column
                  prop="name"
                  label="学生姓名"
              ></el-table-column>
              <el-table-column
                  prop="email"
                  label="学生邮箱"
              ></el-table-column>
              <el-table-column
                  prop="major"
                  label="学生专业"
              ></el-table-column>

              <!-- Other columns as needed -->
            </el-table>
          </div>
        </div>

        <!-- 右侧部分 -->
        <div class="student-submissions" v-if="selectedStudentId">
          <h3>选中学生提交记录</h3>
          <el-table
              :data="selectedStudentSubmissions"
              border
              style="height: 100%;">
            <el-table-column
                prop="studentname"
                label="姓名"
            ></el-table-column>
            <el-table-column
                prop="studentemail"
                label="邮箱"
            ></el-table-column>
            <el-table-column
                prop="submissionDate"
                label="提交日期"
            ></el-table-column>

            <el-table-column
                prop="grade"
                label="分数"
            ></el-table-column>
            <el-table-column label="操作" width="180">
              <template v-slot="scope">
                <el-button
                    type="success"
                    size="small"
                    @click="pigai(scope.row)"
                >
                  批改
                </el-button>
              </template>
            </el-table-column>
            <!-- Other columns as needed -->
          </el-table>
        </div>
      </div>
    </shitshan>>
    <!-- 你的其他内容 -->
  </div>
</template>
<script setup>
import shitshan from "@/components/shitshan.vue";
export default {
  data() {
    return {
      courses: [],
      posts: [],
      materials: [],
      assignments: [],
      projects: [],
      ddls: [],
      ungradedstudents: [], // 尚未批改的作业列表
      selectedStudentSubmissions: [], // 选中学生的提交记录
      unsubmittedStudents: [], // 未提交作业的学生列表
      selectedStudentId: null ,// 当前选中的学生ID
      saInfos: [],
      studentInfos: [],
      teachers: [],

    };
  },
  async created(){
   await this.loadLocalStorageData();
   await this.loadStudentsAndSA();
   await this.loadungradedAssignments();
  },
  components: {
    shitshan
  },
  methods: {
    pigai(a){
      localStorage.setItem("currentstudentid", a.studentid);
      localStorage.setItem("currentassignmentsubmissionid", a.id);
      localStorage.setItem("currentcontent", a.content);
      this.$router.push({ path: '/judgeassignment'});
    },
    async loadungradedAssignments() {
      // 假设 ungradedAssignments 是存储未批改作业的数组
      this.ungradedstudents = [];
      this.unsubmittedStudents = [];
      for (let i = 0; i < this.studentInfos.length; i++) {
        await this.$axios.get('/submission/getAssignmentSubmissionHistory', {
          params: {
            studentId: this.studentInfos[i].id,
            assignmentId: Number(localStorage.getItem("currentassignmentid")),
          }
        }).then((res) => {
          if(res.data.data&&res.data.data.length>0){
            const submissions = res.data.data;
            const allUngraded = submissions.every(sub => sub.grade === 0 || sub.grade === null);
            if(allUngraded && submissions.length > 0) {

              this.ungradedstudents.push({
                id: this.studentInfos[i].id,
                name: this.studentInfos[i].name,
                major: this.studentInfos[i].major,
                email: this.studentInfos[i].email,
                status: '未批改',
              });
            }else {
              this.ungradedstudents.push({
                id: this.studentInfos[i].id,
                name: this.studentInfos[i].name,
                major: this.studentInfos[i].major,
                email: this.studentInfos[i].email,
                status: '已批改',
              });
            }
          }else{
            this.unsubmittedStudents.push(this.studentInfos[i]);
          }

        }).catch(error => {
          console.error('Error loading ungraded assignments:', error);
        });
      }
    },
    handleRowClick(row) {
      // 处理行点击事件，加载选中学生的提交记录
      this.selectedStudentId = row.id;
      this.selectedStudentSubmissions= [];
      this.$axios.get('/submission/getAssignmentSubmissionHistory', {
        params: {
          studentId: row.id,
          assignmentId: Number(localStorage.getItem("currentassignmentid")),
        }
      }).then((res) => {
        if (res.data.code === "0") {
          for (let i = 0; i < res.data.data.length; i++) {
            this.selectedStudentSubmissions.push({
              studentid:row.id,
              studentemail:row.email,
              studentname:row.name,
              submissionTitle:res.data.data[i].submissionTitle,
              submissionDate:res.data.data[i].submissionDate,
              grade:res.data.data[i].grade==null?'未批阅':res.data.data[i].grade,
              id:res.data.data[i].id,
              content:res.data.data[i].content,
              fileid:res.data.data[i].fileId,
            })
          }
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });
    },
    // loadStudentSubmissions(studentId) {
    //   // 根据 studentId 加载提交记录
    //   // 假设有一个方法 fetchStudentSubmissions 返回数据
    //   this.selectedStudentSubmissions = fetchStudentSubmissions(studentId);
    // },
    // selectStudent(studentId) {
    //   this.selectedStudentId = studentId;
    //   // 加载该学生的提交记录...
    //   this.loadSubmissions(studentId);
    // },
    // loadSubmissions(studentId) {
    //   // 假设有一个方法可以加载特定学生的提交记录
    //   // 更新selectedStudentSubmissions...
    // },
    async loadStudentsAndSA(){
      this.saInfos = [];
      this.studentInfos = [];
      this.teachers = [];
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

  }
}

</script>

<style>
.assignments-wrapper {
  display: flex;
  width: 100%;
  height: 100vh; /* Use the full height of the viewport */
  margin: 0; /* Remove default margins */
}

.left-panel {
  width: 50%;
  display: flex;
  flex-direction: column;
}

.submitted-students, .unsubmitted-students {
  flex: 1;
  overflow-y: auto; /* Adds a scrollbar if content is too long */
}

.student-submissions {
  width: 50%;
  overflow-y: auto; /* Adds a scrollbar if content is too long */
}

.student-submissions h3, .submitted-students h3, .unsubmitted-students h3 {
  margin-top: 0;
  padding: 10px; /* Add some padding to the h3 element */
}


</style>
