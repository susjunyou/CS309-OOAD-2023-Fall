<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshan>

      <div class="assign" style="width: 88%">
      <div class="assignment-container" >
        <!-- ...之前的代码... -->
        <el-row :gutter="20">
          <el-col v-for="project in projects" :key="project.id" :span="8" >
            <el-card  class="assignment-card" @click.native="join(project)">
              <h3>{{ project.title }}</h3>
              <a v-if="project.file.downloadUrl"
                 :href="project.file.downloadUrl"
                 :download="project.file.fileName"
                 @click.stop="handleDownload(project)">
                {{ project.file.fileName }}
              </a>                <p v-else class="placeholder">没有文件</p>
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
        <el-form-item label="作业文件" >
          <input type="file"     @change="onFileSelected"/>
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
        <el-form-item label="作业文件" >
          <input type="file"     @change="onFileSelected"/>
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
        maxPeopleInTeam:'',
        courseId: '',
      },
      isPopupVisible: false,
      file:'',
      file2:'',
      fileDownloadUrl: '',
    };
  },
  async created(){
   await this.loadLocalStorageData();
    await this.loadAllCoursesinfo();
    await this.loadLocalStorageData()
  },
  components: {
    shitshan
  },
  methods: {
    handleDownload(project){
      console.log("阻止进入"+project.title)
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
    onFileSelected(event) {
      // event.target.files 包含了用户选中的文件列表
      this.file2 = event.target.files[0]; // 保存第一个选中的文件
    },
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
      let formData=new FormData();
      formData.append('projectTitle',this.projectForm.title);
      formData.append('projectDescription',this.projectForm.description);
      formData.append('projectDeadline',formattedDate);
      formData.append('projectStatus','Started');
      formData.append('maxScore',this.projectForm.maxScore);
      formData.append('proportion',this.projectForm.proportion);
      formData.append('releaser',localStorage.getItem('id').toString());
      formData.append('releaserType','TEACHER');
      formData.append('maxPeopleInTeam',this.projectForm.maxPeopleInTeam);
      formData.append('courseId',localStorage.getItem('currentcourseid'));
      if (this.file2) {
        formData.append('file', this.file2);
      }
      try {
        const response = await this.$axios.post('/project/addProject', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });

        // 处理响应
        if (response.data.code === "0") {
          this.wenzi='发布'
          this.dialogVisible = false;
          this.isPopupVisible = true;
        } else {
          alert("上传失败: " + response.data.message);
        }
      } catch (error) {
        console.error('上传失败:', error);
        alert("上传过程中发生错误");
      }
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


      let formData=new FormData();
      formData.append('projectId',localStorage.getItem('currentprojectid'));
      formData.append('projectTitle',this.editProjectForm.title);
      formData.append('projectDescription',this.editProjectForm.description);
      formData.append('projectDeadline',formattedDate);
      formData.append('projectStatus','Started');
      formData.append('maxScore',this.editProjectForm.maxScore);
      formData.append('proportion',this.editProjectForm.proportion);
      formData.append('releaser',this.editProjectForm.releaser);
      formData.append('releaserType', this.editProjectForm.releaserType);
      formData.append('maxPeopleInTeam',this.editProjectForm.maxPeopleInTeam);
      formData.append('courseId',localStorage.getItem('currentcourseid'));
      if (this.file2) {
        formData.append('file', this.file2);
      }
      try {
        const response = await this.$axios.post('/project/updateProject', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });

        // 处理响应
        if (response.data.code === "0") {
          this.wenzi = "修改";
          this.dialogVisible2 = false; // 关闭对话框
          this.isPopupVisible = true;
        } else {
          alert("修改失败: " + response.data.message);
        }
      } catch (error) {
        console.error('修改失败:', error);
        alert("修改过程中发生错误");
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

  }
}

</script>

<style scoped>
.assignment-container{
  margin-top: 50px;
  margin-left: 0px;
  margin-right: 70px;
}

.assignment-card{
  margin-top: 20px;
  margin-left: 20px;
  margin-right: 20px;
  margin-bottom: 20px;

  background-color: #f5f5f5;
  border-radius: 15px;
  box-shadow: 0 2px 4px 0
}
.assignment-card:hover {
  box-shadow: 0 4px 6px rgba(0,0,0,0.8);
}
</style>