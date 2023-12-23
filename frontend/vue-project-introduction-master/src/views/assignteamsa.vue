<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshansa>

      <div class="team-creation-wrapper">
        <!-- 左侧填写表单区域 -->
        <div class="form-section">
          <el-form ref="teamForm" :model="teamForm" :rules="rules" label-width="100px">
            <el-form-item label="小组名称">
              <el-input v-model="teamForm.name"></el-input>
            </el-form-item>
            <el-form-item label="小组描述">
              <el-input type="textarea" v-model="teamForm.description" :rows="20"></el-input>
            </el-form-item>
            <el-form-item label="最大人数">
              <el-input-number v-model="teamForm.maxMembers" :min="1" :max="maxMembersLimit"></el-input-number>
            </el-form-item>

            <el-form-item label="项目 ID">
              <el-input v-model="teamForm.projectId" disabled></el-input>
            </el-form-item>

            <el-form-item label="队长 ID" prop="leaderId" required>
              <el-select v-model="teamForm.leaderId" placeholder="请选择" required>
                <el-option v-for="id in selectedStudents" :key="id" :label="id" :value="id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click.prevent="submitTeam">提交</el-button>
            </el-form-item>
          </el-form>
        </div>
        <!-- 右侧学生列表区域 -->
        <div class="students-section">
          <h3>未加入小队的成员</h3>
          <el-table :data="studentsnotjointeam" style="width: 100%">
            <el-table-column type="index"></el-table-column>
            <el-table-column label="选择">
              <template v-slot="{ row }">
                <el-checkbox v-model="selectedStudents" :label="row.id"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column prop="name" label="姓名"></el-table-column>
            <el-table-column prop="major" label="专业"></el-table-column>
            <el-table-column prop="email" label="邮箱"></el-table-column>

          </el-table>
        </div>
      </div>
    </shitshansa>>
    <!-- 你的其他内容 -->
    <div v-if="isPopupVisible" class="popup">
      <div class="popup-content">
        <p>创建成功！</p>
        <button @click="returnToprotects" class="sumbitt">关闭</button>
      </div>
    </div>

  </div>

</template>
<script setup>
import shitshansa from "@/components/shitshansa.vue";
export default {
  data() {
    return {
      rules: {
        leaderId: [
          { required: true, message: '请选择队长', trigger: 'change' }
        ],},
      courses: [],
      posts: [],
      materials: [],
      assignments: [],
      projects: [],
      ddls: [],
      studentsnotjointeam:[],
      maxMembersLimit: Number(localStorage.getItem("currentprojectmaxpeopleinteam")) , // 假设 10 是默认最大值

      teamForm: {
        name: '',
        description: '',
        maxMembers: 1,
        projectId: localStorage.getItem('currentprojectid') || '',
        leaderId: '',
      },
      selectedStudents: [],
      isPopupVisible: false,
    };
  },
  async created(){
    this.loadLocalStorageData();
    this.loadstudentnotjointeam();

  },
  components: {
    shitshansa
  },
  methods: {
    prepareTeamMembers() {
      return this.selectedStudents.map(studentId => {
        return this.studentsnotjointeam.find(student => student.studentId === studentId);
      });
    },
    checkStudentCount() {
      if (this.selectedStudents.length > this.teamForm.maxMembers) {
        this.$message.error('已达到最大成员数');
        this.selectedStudents = this.selectedStudents.slice(0, this.teamForm.maxMembers);
      }
    },


    returnToprotects(){
      this.isPopupVisible = false;
      this.$router.push('/modifyinformationsa');
    },





    async submitTeam() {
      this.$refs.teamForm.validate(async (valid) => {
        if (valid) {
          const createRes = await this.$axios.get('/team/create', {
            params: {
              teamName: String(this.teamForm.name),
              teamDescription: String(this.teamForm.description),
              maxMembers: Number(this.teamForm.maxMembers),
              projectId: Number(localStorage.getItem('currentprojectid')),
              leader: Number(this.teamForm.leaderId),
              teamSize: this.teamForm.maxMembers,
            }
          });


          const findRes = await this.$axios.get('/team/findTeamInfoByProjectId', {
            params: {
              projectId: localStorage.getItem("currentprojectid")
            }
          });
          console.log(findRes.data.data);
          if (findRes.data.code === "0") {
            localStorage.setItem(localStorage.getItem("currentcourse") + " " + localStorage.getItem("currentprojectid") + " " + "teamcount", findRes.data.data.length);
            // this.teamcount = res.data.data.length;
            for (let i = 0; i < findRes.data.data.length; i++) {
              const team = findRes.data.data[i];
              console.log(this.teamForm.leaderId);
              console.log(findRes.data.data[i].leader);
              if (findRes.data.data[i].leader === this.teamForm.leaderId) {
                console.log("yes1");
                for (let j = 0; j < this.selectedStudents.length; j++) {
                  console.log("yes2");

                  if (this.selectedStudents[j] != team.leader) {
                    console.log("yes3");
                    await this.$axios.get('/team/join', {
                      params: {
                        studentId: this.selectedStudents[j],
                        teamId: Number(team.teamId),
                        projectId: Number(team.projectId),
                        teamSize: team.teamSize,
                        leader: Number(team.leader),
                      },
                    })
                  }
                }
              }


            }
          }


          if (createRes.data.code === "0") {
            this.isPopupVisible = true;
          }
        }
      },);
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
            this.studentsnotjointeam.push(
                res.data.data[i]
            )
          } console.log('sss');
        }else {
          console.log("error")
        }
      }).catch(error => {
        console.log(error)
      })
    },
    async loadLocalStorageData() {
      await new Promise((resolve) => setTimeout(resolve, 10)); // 模拟异步操作，这里不是必要的，只是演示用例
      this.courses=[];
      for (let i = 0; i < localStorage.getItem('lengthsa'); i++) {
        this.courses.push({
          id: localStorage.getItem('coursesidsa' + i),
          title: localStorage.getItem('coursessa' + i),
          description: localStorage.getItem('courseDescriptionsa' + i),
          code: localStorage.getItem('coursecodesa' +i),
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
.team-creation-wrapper {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.form-section {
  width: 50%;
  padding: 20px;
}

.students-section {
  width: 45%;
  padding: 20px;
}

</style>