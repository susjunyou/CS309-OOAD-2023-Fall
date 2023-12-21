<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshan>

      <div class="assignments-wrapper">
        <!-- 左侧部分 -->
        <div class="left-panel">
          <!-- 已提交学生信息 -->
          <div class="submitted-students" >
            <h3>所有小组信息</h3>
            <div style="overflow-y: auto">
            <el-table
                :data="teams"
                border
                style="height: 75%;"
                @row-click="handleRowClick">
              <el-table-column
                  prop="name"
                  label="小组名称"
              ></el-table-column>
              <el-table-column
                  prop="description"
                  label="小组描述"
              ></el-table-column>
              <el-table-column label="小组成员">
                <template v-slot="{ row }">
                  <div v-for="member in row.teammembers" :key="member.id" class="team-member" style="margin-top:10px">
                    {{ member.name }} (学号：{{ member.id }})
                    <el-button type="danger" size="mini" @click="removeMember(row, member)" :disabled="row.leader === member.id">开除</el-button>
                  </div>
                  <p v-if="row.leader">组长学号：{{ row.leader }}</p>

                </template>
              </el-table-column>
              <el-table-column label="更改答辩老师/时间">
                <template v-slot="scope">
                  <el-button type="success" size="small" @click="update1(scope.row)" style="margin-left: 10px">更改答辩老师/时间</el-button>
                  <el-button type="success" size="small" @click="update5(scope.row)" style="margin-top: 15px">更改小队信息</el-button>
                </template>

              </el-table-column>
              <el-table-column label="更改小组队长">
                <template v-slot="scope">
                  <el-row :gutter="10">
                    <el-col :span="12">
                      <el-select v-model="scope.row.selectedLeaderId" placeholder="选择新队长">
                        <el-option
                            v-for="member in getNonLeaderMembers(scope.row)"
                            :key="member.id"
                            :label="member.id"
                            :value="member.id">
                        </el-option>
                      </el-select>
                    </el-col>
                    <el-col :span="12">
                      <el-button type="success" size="small" @click="update2(scope.row)">更改</el-button>
                    </el-col>
                  </el-row>
                </template>
              </el-table-column>
              <el-table-column label="删除小队">
                <template v-slot="scope">
                  <el-button type="danger" size="small" @click.prevent="update3(scope.row)">删除小队</el-button>
                </template>
              </el-table-column>

              <!-- Other columns as needed -->
            </el-table>
            </div>
            <el-button type="success" size="small" @click="update4()" class="sumbitt">添加小组</el-button>
            <el-button type="success" size="small" @click="dialogVisible3 = true" class="sumbitt">批量添加小组</el-button>

          </div>

          <!-- 未提交学生信息 -->

        </div>

        <!-- 右侧部分 -->

      </div>
    </shitshan>>
    <!-- 你的其他内容 -->
    <el-dialog :visible.sync="dialogVisible" title="更改答辩信息">
      <el-form :model="dialogForm">
        <el-form-item label="答辩老师">
          <el-select v-model="dialogForm.teacherId" placeholder="请选择老师">
            <el-option
                v-for="teacher in teachers"
                :key="teacher.id"
                :label="teacher.name"
                :value="teacher.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="答辩日期">
          <el-date-picker v-model="dialogForm.presentationDate" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
<!--        <el-form-item label="答辩时间">-->
<!--          <el-time-picker-->
<!--              v-model="dialogForm.presentationTime"-->
<!--              :picker-options="timePickerOptions"-->
<!--              placeholder="选择时间"-->
<!--              format="HH:mm"-->
<!--              value-format="HH:mm">-->
<!--          </el-time-picker>-->
<!--        </el-form-item>-->

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateDefenseInfo">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="dialogVisible2" title="更改小组信息">
      <el-form :model="dialogForm2">
        <el-form-item label="小组名称">
          <el-input v-model="dialogForm2.teamname" placeholder="请输入小组名称"></el-input>
        </el-form-item>
        <el-form-item label="小组描述">
          <el-input v-model="dialogForm2.teamdescription" type="textarea" placeholder="请输入小组描述"></el-input>
        </el-form-item>
        <el-form-item label="招募信息">
          <el-input v-model="dialogForm2.recruitmentInformation" type="textarea" placeholder="请输入小组招募信息"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible2 = false">取消</el-button>
    <el-button type="primary" @click="updateTeamInfo">确定</el-button>
  </span>
    </el-dialog>
    <el-dialog :visible.sync="dialogVisible3" title="批量添加小组">
      <el-form ref="bulkTeamForm" :model="bulkTeamForm">
        <el-form-item label="小组数量">
          <el-input-number v-model="bulkTeamForm.numberOfTeams" :min="1"></el-input-number>
        </el-form-item>
        <!-- 其他表单项 -->
        <el-form-item label="小组大小">
          <el-input-number v-model="bulkTeamForm.teamsize" :min="1" :max="maxMembersLimit"></el-input-number>
        </el-form-item>
        <el-form-item label="小组名称">
          <el-input v-model="bulkTeamForm.name"></el-input>
        </el-form-item>
        <el-form-item label="小组描述">
          <el-input type="textarea" v-model="bulkTeamForm.description"></el-input>
        </el-form-item>
        <el-form-item label="项目 ID">
          <el-input v-model="bulkTeamForm.projectid" disabled></el-input>
        </el-form-item>
      </el-form>
      <!-- 对话框的其他内容 -->
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible3 = false">取消</el-button>
    <el-button type="primary" @click="submitBulkTeams">确定</el-button>
  </span>
    </el-dialog>


    <div v-if="isPopupVisible" class="popup">
      <div class="popup-content">
        <p>{{ this.wenzi }}成功！</p>
        <button @click="returnToprotects" class="sumbitt">关闭</button>
      </div>
    </div>
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
      isPopupVisible: false,
      dialogVisible: false,
      dialogVisible2: false,
      maxMembersLimit: Number(localStorage.getItem("currentprojectmaxpeopleinteam")) , // 假设 10 是默认最大值

      dialogForm: {
        teacherId: '',
        presentationDate: '',
        presentationTime: '',
      },
      dialogForm2: {
        teamname: '',
        teamdescription: '',
        recruitmentInformation:'',
      },
      wenzi: "",
      currentteam:null,
      dialogVisible3: false, // 控制批量添加小组对话框的显示
      bulkTeamForm: { // 批量添加小组的表单数据
        teamsize: 1,
        name: '',
        description: '',
        projectid: localStorage.getItem('currentprojectid') || '',
        numberOfTeams: 1,
      },
      // timePickerOptions: {
      //   start: '00:00', // 开始时间
      //   step: '00:01', // 间隔时间
      //   end: '23:59', // 结束时间
      //   minTime: '00:00', // 最早时间
      //   maxTime: '23:59', // 最晚时间
      // },
    };
  },
  async created(){
    await this.loadLocalStorageData();
    await this.loadStudentsAndSA();
    console.log(this.teachers);
    await this.getTeam();
  },
  components: {
    shitshan
  },
  methods: {
    async submitBulkTeams() {

      for (let i = 0; i < this.bulkTeamForm.numberOfTeams; i++) {
        await this.$axios.get('/team/create', {
          params: {
            teamName: String(this.bulkTeamForm.name),
            teamDescription: String(this.bulkTeamForm.description),
            teamSize: Number(this.bulkTeamForm.teamsize),
            projectId:Number(localStorage.getItem('currentprojectid')),
          }
        }).then((res) => {
          console.log("code====================================="+res.data.code)
          if (res.data.code === "0"&&i===this.bulkTeamForm.numberOfTeams-1) {
            this.dialogVisible3 = false;
            this.isPopupVisible = true;
            this.wenzi="添加";
          }
        }).catch(error => {
          console.error('Error loading course posts:', error);
        });
      }

    },
    update4(){
      this.$router.push('/assignteam');
    },
    removeMember(team, member) {

      this.$axios.get('/team/leave', {
        params: {
          studentId: member.id,
          teamId: team.id,
        },
      }).then((res) => {
        if (res.data.code === "0") {
          this.wenzi="开除";
          this.isPopupVisible = true;
        }
      }).catch(error => {
        console.error('Error loading course assignments:', error);
      });
    },
    update1(row){
      this.dialogForm.teacherId = row.teacherId;
      this.dialogForm.presentationDate = row.presentationDate;
      this.dialogForm.presentationTime = row.presentationTime;
      localStorage.setItem('currentteamid', row.id);
      this.dialogVisible = true;
    },
    update5(row){
      this.dialogForm2.teamname = row.name;
      this.dialogForm2.teamdescription = row.description;
      this.dialogForm2.recruitmentInformation = row.recruitmentInformation;
      this.currentteam = row;
      this.dialogVisible2 = true;
    },
   async updateTeamInfo() {
      // 在这里处理对话框提交的数据
      // 例如，发送请求到后端更新小组信息
     const res1 = await this.$axios.get('/team/findTeamInfoByTeamId', {
       params: {
         teamId: this.currentteam.id,
       }
     });
     const team = res1.data.data;
     await this.$axios.get('/team/updateTeamInfo', {
        params: {
          teamId: team.teamId,
          recruitmentInformation:this.dialogForm2.recruitmentInformation,
          teamName: this.dialogForm2.teamname,
          projectId:team.projectId,
          leader:team.leader,
          teamDescription:this.dialogForm2.teamdescription,

          teamSize:team.teamSize,

          teamMembers:team.teamMembers,

          teacherId:team.teacherId,

          presentationDate:team.presentationDate,



          // teamMembers: this.currentteam.teammembers,
        }
      }).then((res) => {
        if (res.data.code === "0") {
          this.wenzi = "修改";
          this.isPopupVisible = true;
        }
      });
      this.dialogVisible2 = false;
      // 执行更新操作...
    },

    updateDefenseInfo() {
      console.log(this.dialogForm.teacherId);
        let date = new Date(this.dialogForm.presentationDate);
        let formattedDate = date.toISOString().split('T')[0]; // 转换为 YYYY-MM-DD 格式
        this.dialogForm.presentationDate = formattedDate;
      this.$axios.get('/presentation/addPresentation', {
        params: {
          teamId: localStorage.getItem('currentteamid'),
          teacherId: this.dialogForm.teacherId,
          presentationDate: this.dialogForm.presentationDate,
        }
      }).then((res) => {
        if (res.data.code === "0") {
          this.wenzi = "修改";
          this.isPopupVisible = true;
        }
      });
      // 在这里执行更新操作，例如调用 API

      // 更新后关闭弹窗
      this.dialogVisible = false;
    },
    returnToprotects(){
      this.getTeam();
      this.isPopupVisible = false;

    },
    update3(a){
      this.$axios.get('/team/delete', {
        params: {
          teamId: a.id
        }
      }).then((res) => {
        if (res.data.code === "0") {
          this.wenzi = "删除";
          this.isPopupVisible = true;
        }
      });
    },
    getNonLeaderMembers(team) {
      if (!team.teammembers) {
        return []; // 如果 teammembers 是 null 或 undefined，返回一个空数组
      }
      return team.teammembers.filter(member => member.id !== team.leader);
    },


    update2(a) {
      this.$axios.get('/team/updateTeamLeader', {
        params: {
          teamId: a.id,
          studentId: a.selectedLeaderId
        }
      }).then((res) => {
        if (res.data.code === "0") {
          this.wenzi = "修改";
          this.isPopupVisible = true;
        }
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
            console.log(res1.data);

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
                teacherid: team.teacherId,
                presentationdate: team.presentationDate,
                recruitmentInformation:team.recruitmentInformation,
                selectedLeaderId:'',
              });
            }else {
              this.teams.push({
                id: team.teamId,
                name: team.teamName,
                description: team.teamDescription,
                leader: team.leader,
                projectid: team.projectId,
                teamsize: team.teamSize,
                studentid:null,
                teammembers: null,
                currentmembercount:  0,
                teacherid: team.teacherId,
                presentationdate: team.presentationDate,
                recruitmentInformation:team.recruitmentInformation,

                selectedLeaderId:'',
              });
            }

          }
          console.log(this.teams);
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
  padding: 10px;
  margin-left: 60px;
  flex: 1;
  overflow-y: auto; /* Adds a scrollbar if content is too long */
}

.right-panel {
  padding: 10px;
  width: 85%;
  display: flex;
  flex-direction: column;
}

.submitted-students{
  flex: 1;
  overflow-y: auto; /* Adds a scrollbar if content is too long */
}



.student-submissions h3, .submitted-students h3, .unsubmitted-students h3 {
  margin-top: 0;
  padding: 10px; /* Add some padding to the h3 element */
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
.sumbitt:hover {
  background: linear-gradient(45deg, #2193b0, #6dd5ed);
  box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.3);
}

</style>