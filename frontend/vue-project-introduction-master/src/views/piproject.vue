<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshan>

      <div class="assignments-wrapper">
        <!-- 左侧部分 -->
        <div class="left-panel">
          <!-- 已提交学生信息 -->
          <div class="submitted-students">
            <h3>已提交小组信息</h3>
            <el-table
                :data="ungradedteams"
                border
                style="height: 50%;"
                @row-click="handleRowClick">
              <el-table-column
                  prop="name"
                  label="小组名称"
              ></el-table-column>
              <el-table-column
                  prop="description"
                  label="小组描述"
              ></el-table-column>
              <el-table-column
                  label="小组成员"
              >
                <template v-slot="{ row }">
          <span v-for="member in row.teammembers" :key="member.id">
            {{ member.name }} ({{ member.id }}),
          </span>
                </template>
              </el-table-column>
              <el-table-column
                  prop="status"
                  label="批改状态"
              ></el-table-column>
              <!-- Other columns as needed -->
            </el-table>
          </div>

          <!-- 未提交学生信息 -->
          <div class="unsubmitted-students">
            <h3>未提交小组信息</h3>
            <el-table
                :data="unsubmittedTeams"
                border
                style="height: 50%;">
              <el-table-column
                  prop="name"
                  label="小组名称"
              ></el-table-column>
              <el-table-column
                  prop="description"
                  label="小组描述"
              ></el-table-column>
              <el-table-column
                  label="小组成员"
              >
                <template v-slot="{ row }">
          <span v-for="member in row.teammembers" :key="member.id">
            {{ member.name }} ({{ member.id }}),
          </span>
                </template>
              </el-table-column>
              <!-- Other columns as needed -->
            </el-table>
          </div>
        </div>

        <!-- 右侧部分 -->
        <div class="student-submissions" v-if="selectedTeamId">
          <h3>选中小组提交记录</h3>
          <el-table
              :data="selectedTeamSubmissions"
              border
              style="height: 80%;">
            <el-table-column
                prop="name"
                label="小队名称"
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
          <el-button type="success" @click="search">查看互评成绩</el-button>
        </div>
      </div>
    </shitshan>
    <el-dialog
        title="互评成绩"
        :visible.sync="showDialog"
        width="50%">
      <el-table v-if="corate!=null&&corate.length > 0&&corate!=undefined&&corate!='null'" :data="corate" style="width: 100%">
        <el-table-column prop="id" label="小组名字"></el-table-column>
        <el-table-column prop="comment" label="评论"></el-table-column>
        <el-table-column prop="grade" label="分数"></el-table-column>
      </el-table>
      <p v-else style="color: red;">{{ noCorateMessage }}</p>
    </el-dialog>
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
      ungradedteams: [], // 尚未批改的作业列表
      gradedTeams: [], // 已批改的作业列表
      selectedTeamSubmissions: [], // 选中学生的提交记录
      unsubmittedTeams: [], // 未提交作业的学生列表
      selectedTeamId: null ,// 当前选中的学生ID
      saInfos: [],
      studentInfos: [],
      teachers: [],
      teams: [],
      corate:[],
      showDialog: false, // 控制弹窗的显示
      noCorateMessage: '', // 用于存储提示信息
    };
  },
  async created(){
   await this.loadLocalStorageData();
   await this.loadStudentsAndSA();
   await this.getTeam();
  },
  components: {
    shitshan
  },
  methods: {
   async search(){
const res=await this.$axios.get('/team/getOtherTeamGrade', {
        params: {
          teamId: this.selectedTeamId,
          projectId: Number(localStorage.getItem("currentprojectid")),
        },
      })
     console.log(res.data)

     if(res.data.code==="0"){
      this.corate=[]
for (let i=0;i<res.data.data.length;i++){
  const res1 = await this.$axios.get('/team/findTeamInfoByTeamId', {
    params: {
      teamId: res.data.data[i].team2,
    }
  });

  this.corate.push({
    grade:res.data.data[i].grade,
    comment:res.data.data[i].comment,
    id:res1.data.data.teamName
  })
}
       this.showDialog = true;
      this.noCorateMessage = '';
      console.log(this.corate)
      }else{
        this.noCorateMessage = '没有互评成绩';
        console.log(this.noCorateMessage)
       console.log(this.corate.length)
      }
   },
    pigai(a){
      localStorage.setItem("currentteamid", a.teamid);
      localStorage.setItem("currentteammembers", a.teammembers);
      console.log(a.teammembers);
      localStorage.setItem("currentprojectsubmissionid", a.id);
      localStorage.setItem("currentcontent", a.content);
      localStorage.setItem("currentfileid", a.fileid);
      this.$router.push({ path: '/judgeproject'});
    },
    handleRowClick(row) {
      // 处理行点击事件，加载选中学生的提交记录
      this.selectedTeamId = row.id;
      this.selectedTeamSubmissions= [];
      this.$axios.get('/submission/getProjectSubmissionHistory', {
        params: {
          studentId: row.studentid,
          projectId: Number(localStorage.getItem("currentprojectid")),
        }
      }).then((res) => {
        if (res.data.code === "0") {
          for (let i = 0; i < res.data.data.length; i++) {
            this.selectedTeamSubmissions.push({
              teamid:row.id,
              name:row.name,
              teammembers:row.teammembers,
              // submissionTitle:res.data.data[i].projectTitle,
              submissionDate:res.data.data[i].submissionDate,
              grade:res.data.data[i].grade==null?'未批阅':res.data.data[i].grade,
              id:res.data.data[i].projectSubmissionId,
              content:res.data.data[i].content,
              fileid:res.data.data[i].fileId,
            })
          }
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });
    },

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
    async getTeam() {
      this.ungradedteams = [];
      this.teams = [];
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
            if (res1.data.code === "0") {
              this.teams.push({
                id: team.teamId,
                name: team.teamName,
                description: team.teamDescription,
                leader: team.leader,
                projectid: team.projectId,
                teamsize: team.teamSize,
                studentid:res1.data.data[0].id,
                teammembers: res1.data.data,
                currentmembercount: res1.data.data ? res1.data.data.length : 0,
              });
            }

          const res2=  await this.$axios.get('/submission/getProjectSubmissionHistory', {
              params: {
                studentId: this.teams[i].studentid,
                projectId: Number(localStorage.getItem("currentprojectid")),
              }
            })
            if(res2.data.code === "0"){

              if(res2.data.data&&res2.data.data.length>0){
                const submissions = res2.data.data;
                const allUngraded = submissions.every(sub => sub.grade === 0 || sub.grade === null);
                if(allUngraded && submissions.length > 0) {

                  this.ungradedteams.push(this.teams[i]);
                  this.ungradedteams[this.ungradedteams.length - 1].status = '尚未批改';
                }else {
                  this.ungradedteams.push(this.teams[i]);
                  this.ungradedteams[this.ungradedteams.length - 1].status = '已批改';
                }
              }else{
                this.unsubmittedTeams.push(this.teams[i]);
              }

            }




















          }
        }
      } catch (error) {
        console.error('Error loading team info or members:', error);
      }
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

<style scoped>
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