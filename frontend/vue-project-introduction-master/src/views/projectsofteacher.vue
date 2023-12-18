<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshan>

      <div class="assign" style="width: 88%">
      <div class="assignment-container">
        <!-- ...之前的代码... -->
        <el-row :gutter="20">
          <el-col v-for="project in projects" :key="project.id" :span="6" >
            <el-card  class="assignment-card" @click.native="join(project)">
              <h3>{{ project.title }}</h3>
              <p>截止日期：{{ project.ddl }}</p>
              <el-button type="danger" size="small" @click.stop="deleteProject(project)">删除课程</el-button>
              <el-button type="primary" size="small" @click.stop="editProject(project)">修改信息</el-button>
            </el-card>
          </el-col>
        </el-row>
        <!-- ...之后的代码... -->

      </div>
      <div class="publish-button-container">
        <el-button class="sumbitt" @click="publishProject">发布Project</el-button>
      </div>
  </div>

    </shitshan>
    <!-- 你的其他内容 -->
    <el-dialog title="发布Project" :visible.sync="dialogVisible">
      <el-form :model="projectForm">
        <el-form-item label="Project标题">
          <el-input v-model="projectForm.title"></el-input>
        </el-form-item>
        <el-form-item label="Project描述">
          <el-input type="textarea" v-model="projectForm.description"></el-input>
        </el-form-item>
        <el-form-item label="截止日期">
          <el-date-picker v-model="projectForm.deadline" type="date" placeholder="选择日期" :disabled-date="disabledDate"></el-date-picker>
        </el-form-item>
        <el-form-item label="最高分数">
          <el-input-number v-model="projectForm.maxScore"></el-input-number>
        </el-form-item>
        <el-form-item label="占比">
          <el-input-number v-model="projectForm.proportion" :min="0" :max="100" step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="开始日期">
          <el-date-picker v-model="projectForm.projectStartDate" type="date" placeholder="选择日期" :disabled-date="disabledDate"></el-date-picker>
        </el-form-item>
        <el-form-item label="队伍最大人数">
          <el-input-number v-model="projectForm.maxPeopleInTeam" :min="1" :max="100" step="1"></el-input-number>
        </el-form-item>
        <!-- courseId通常是选择的课程或从其他途径获得，这里假设是隐藏字段 -->
        <el-input type="hidden" v-model="projectForm.courseId"></el-input>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click.prevent="addProject">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改Project" :visible.sync="dialogVisible2">
      <el-form :model="editProjectForm">
        <el-form-item label="Project标题">
          <el-input v-model="editProjectForm.title"></el-input>
        </el-form-item>
        <el-form-item label="Project描述">
          <el-input type="textarea" v-model="editProjectForm.description"></el-input>
        </el-form-item>
        <el-form-item label="截止日期">
          <el-date-picker v-model="editProjectForm.deadline" type="date" placeholder="选择日期" :disabled-date="disabledDate"></el-date-picker>
        </el-form-item>
        <el-form-item label="最高分数">
          <el-input-number v-model="editProjectForm.maxScore"></el-input-number>
        </el-form-item>
        <el-form-item label="占比">
          <el-input-number v-model="editProjectForm.proportion" :min="0" :max="100" step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="开始日期">
          <el-date-picker v-model="editProjectForm.projectStartDate" type="date" placeholder="选择日期" :disabled-date="disabledDate"></el-date-picker>
        </el-form-item>
        <el-form-item label="队伍最大人数">
          <el-input-number v-model="editProjectForm.maxPeopleInTeam" :min="1" :max="100" step="1"></el-input-number>
        </el-form-item>
        <!-- courseId通常是选择的课程或从其他途径获得，这里假设是隐藏字段 -->
        <el-input type="hidden" v-model="editProjectForm.courseId"></el-input>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click.stop="commitEdit">确定</el-button>
      </div>
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
      dialogVisible: false,
      dialogVisible2:false,
      wenzi:'',
      projectForm: {
        title: '',
        description: '',
        deadline: '',
        status: '',
        maxScore: '',
        proportion: '',
        releaser: '',
        releaserType: '',
        projectStartDate:'',
        maxPeopleInTeam:'',
        courseId: '',
      },
      editProjectForm:{
        title: '',
        description: '',
        deadline: '',
        status: '',
        maxScore: '',
        proportion: '',
        releaser: '',
        releaserType: '',
        projectStartDate:'',
        maxPeopleInTeam:'',
        courseId: '',
      },
      isPopupVisible: false,
    };
  },
  async created(){
    this.loadLocalStorageData();
    await this.loadAllCoursesinfo();
    await this.loadLocalStorageData()
  },
  components: {
    shitshan
  },
  methods: {

    publishProject(){
      this.dialogVisible = true;
    },
    async loadAllCoursesinfo() {
      for (let course of this.courses) {
        //加载posts
        await this.$axios.get('/course/posts', {
          params: {
            courseId: course.id
          }
        }).then((res) => {
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
          if (res.data.code === "0") {
            localStorage.setItem('courseMaterialLength'+course.title,res.data.data.length)
            for (let i = 0; i < localStorage.getItem('courseMaterialLength'+course.title); i++) {
              localStorage.setItem('materialid'+course.title+i,res.data.data[i].materialId);
              localStorage.setItem('materialname' + course.title + i, res.data.data[i].materialName);
              localStorage.setItem('materialdescription' + course.title + i, res.data.data[i].materialDescription);
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
          console.log(res.data)
          console.log(course.id)
          if (res.data.code === "0") {
            console.log(res.data.data)
            localStorage.setItem('courseAssignmentLength'+course.title,(res.data.data==null||res.data.data=='null')?0:res.data.data.length)
            console.log(localStorage.getItem('courseAssignmentLength'+course.title))
            for (let i = 0; i < localStorage.getItem('courseAssignmentLength'+course.title); i++) {
              localStorage.setItem('assignmentid'+course.title+i,res.data.data[i].id);
              localStorage.setItem('assignmentstatus'+course.title+i,res.data.data[i].assignmentStatus);/////////////////////////////////////////////////////////////////////////////
              localStorage.setItem('assignmenttitle'+course.title+i,res.data.data[i].assignmentTitle);
              localStorage.setItem('assignmentdescription'+course.title+i,res.data.data[i].assignmentDescription);
              localStorage.setItem('assignmentddl'+course.title+i,res.data.data[i].assignmentDeadline);
              this.ddls.push({
                date : res.data.data[i].assignmentDeadline,
                title : course.title+"   "+res.data.data[i].assignmentTitle,
              })

            }
          }else     localStorage.setItem('courseAssignmentLength'+course.title,0)

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
          }
        }).catch(error => {
          console.error('Error loading course projects:', error);
        });

      }
    },

    async returnToprotects(){
      await this.loadLocalStorageData()
      await this.loadAllCoursesinfo()
      await this.loadLocalStorageData()
      this.dialogVisible = false;
      this.dialogVisible2 = false;
      this.isPopupVisible = false;
    },
    submitassignment(project){
      localStorage.setItem("currentprojectid",project.id);
      localStorage.setItem("in_ddl",project.ddl);//status
      localStorage.setItem("cru_description",project.description);
      localStorage.setItem("cru_status",project.status);

      this.$router.push({ path: '/piproject'});
    },
    async addProject(){///问题：后端这个releaser是个string，而给的东西是个int
      let date = new Date(this.projectForm.deadline);
      let formattedDate = date.toISOString().split('T')[0]; // 转换为 YYYY-MM-DD 格式
      let date2 = new Date(this.projectForm.projectStartDate);
      let formattedDate2 = date2.toISOString().split('T')[0]; // 转换为 YYYY-MM-DD 格式
      console.log(localStorage.getItem('id'))
      await this.$axios.get('/project/addProject', {
        params: {
          projectTitle: this.projectForm.title,
          projectDescription: this.projectForm.description,
          projectDeadline: formattedDate,
          projectStatus: 'Started',
          maxScore: this.projectForm.maxScore,
          proportion: this.projectForm.proportion,
          releaser: localStorage.getItem('id').toString(),
          releaserType: 'TEACHER',
          projectStartDate:formattedDate2,
          maxPeopleInTeam:this.projectForm.maxPeopleInTeam,
          courseId: localStorage.getItem('currentcourseid'),
        }
      }).then((res) => {
        console.log(res);
        if (res.data.code === "0") {
          this.wenzi='发布'
          this.dialogVisible = false;
          this.isPopupVisible = true;

        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });
    },
////////
    async deleteProject(project) {
      console.log('indelete1')
      await this.$axios.get('/project/deleteProject', {//这里有问题，在后端接口那边是delete mapping，改了，但现在还是不行
        params: {
          projectId: project.id
        }
      }).then((res) => {
        console.log('indelete2')
        console.log(res)//删除失败
        if (res.data.code === "0") {

          this.wenzi = "删除";
          console.log('indelete3')
          this.isPopupVisible = true;
        }
      }).catch(error => {
        console.error('Error loading course materials:', error);
      });
      console.log("删除project", project.title);
    },

    editProject(project) {

      // 打开编辑对话框并填充表单数据
      this.dialogVisible2 = true;//在这里出问题
      // console.log(this.dialogVisible2)
      // console.log('设置可视化')
      // console.log(project)
      localStorage.setItem('currentprojectid',project.id);
      localStorage.setItem('currentprojecttitle',project.title);
      // console.log('ceshi:'+localStorage.getItem('status'+project.title))
      this.editProjectForm.title = project.title;
      this.editProjectForm. description = project.description;
      this.editProjectForm.deadline = project.deadline;
      this.editProjectForm.status = project.status;
      this.editProjectForm. maxScore = project.maxScore;
      this.editProjectForm.proportion = project.proportion;
      this.editProjectForm.releaser = localStorage.getItem('id').toString();
      this.editProjectForm. releaserType = 'TEACHER';
      this.editProjectForm. projectStartDate = project.projectStartDate;
      this.editProjectForm.maxPeopleInTeam = project.maxPeopleInTeam;
      this.editProjectForm. courseId = localStorage.getItem('currentcourseid');
      // console.log(this.editProjectForm.status )
      // console.log(this.editProjectForm.releaser )
      // console.log(this.editProjectForm.releaserType )

    },


    async commitEdit(){
      //updateAssignmentByAssignmentId
      //console.log('this.form:'+this.editProjectForm.deadline)
      let date = new Date(this.editProjectForm.deadline);
      //console.log('date:'+date)
      let formattedDate = date.toISOString().split('T')[0]; // 转换为 YYYY-MM-DD 格式
      //console.log('format:'+formattedDate)
      //let tit = localStorage.getItem('currentassignmenttitle')
      //console.log('tit:'+tit)
      const res=await  this.$axios.get('/project/updateProject', {
        params: {
          projectId: localStorage.getItem('currentprojectid'),
          projectTitle: this.editProjectForm.title,
          projectDescription: this.editProjectForm.description,
          projectDeadline: formattedDate,
          projectStatus: this.editProjectForm.status,
          maxScore: this.editProjectForm.maxScore,
          proportion: this.editProjectForm.proportion,
          releaser: this.editProjectForm.releaser,
          releaserType: this.editProjectForm.releaserType,
          projectStartDate:this.editProjectForm.projectStartDate,
          maxPeopleInTeam:this.editProjectForm.maxPeopleInTeam,
          courseId: localStorage.getItem('currentcourseid'),
        }
      })
      if (res.data.code === "0") {
        this.wenzi = "修改";
        this.dialogVisible2 = false; // 关闭对话框
        this.isPopupVisible = true;
      }
    },
    ////////////
    join(route) {
      localStorage.setItem("currentprojectid",route.id)
      localStorage.setItem("currentprojectmaxpeopleinteam",route.maxpeopleinteam);
      this.$router.push('piproject');
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
.assignment-container {
  margin: 20px;
  padding-left: 200px;
  width: 100%;
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