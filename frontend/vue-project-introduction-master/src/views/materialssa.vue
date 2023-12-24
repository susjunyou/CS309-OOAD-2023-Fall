<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshansa>


      <div class="assignment-containerassign" style="width: 88%">
        <div class="assignment-container">
          <!-- ...之前的代码... -->
          <el-row :gutter="20">
            <el-col v-for="material in materials" :key="material.id" :span="6" >
              <el-card class="assignment-card" style="min-height: 180px">
                <h3>{{ material.name }}</h3>
                <p>{{ material.description }}</p>
                <a v-if="material.file.downloadUrl" :href="material.file.downloadUrl" :download="material.file.fileName">{{ material.file.fileName }}</a>
                <p v-else class="placeholder">没有文件</p>

              </el-card>
            </el-col>
          </el-row>
          <!-- ...之后的代码... -->

        </div>
        <div class="publish-button-container">
          <el-button class="sumbitt" @click="publishmaterial">上传材料</el-button>
        </div>
        <el-dialog title="上传材料" :visible.sync="dialogVisible">
          <el-form :model="form" ref="uploadForm" label-width="120px">
            <el-form-item label="名称" required>
              <el-input v-model="form.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="描述" required>
              <el-input type="textarea" v-model="form.description"></el-input>
            </el-form-item>
            <el-form-item label="文件" required>
              <input type="file"     @change="onFileSelected"/>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </span>
        </el-dialog>
      </div>


    </shitshansa>>
    <!-- 你的其他内容 -->
    <div v-if="isPopupVisible" class="popup">
      <div class="popup-content">
        <p>{{this.wenzi}}成功！</p>
        <button @click="returnToassignments">关闭</button>
      </div>
    </div>
  </div>
</template>
<script setup>
import shitshansa from "@/components/shitshansa.vue";
export default {
  data() {
    return {
      courses: [],
      posts: [],
      materials: [],
      assignments: [],
      projects: [],
      ddls: [],
      wenzi:'',
      file:'',
      fileDownloadUrl:'',
      dialogVisible: false,
      form: {
        name: '',
        description: '',
        file:''
      },
      uploadfile: null,
      isPopupVisible: false,
    };
  },
  async created(){
    await  this.loadLocalStorageData();
    await this.loadAllCoursesinfo();
    await this.loadLocalStorageData();
  },
  components: {
    shitshansa
  },
  methods: {
    async  returnToassignments(){
      await  this.loadLocalStorageData();
      await this.loadAllCoursesinfo();
      await this.loadLocalStorageData();
      this.isPopupVisible = false;
      this.dialogVisible=false;
    },
    publishmaterial(){
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
              localStorage.setItem('assignmentstatus'+course.title+i,res.data.data[i].assignmentStatus);/////////////////////////////////////////////////////////////////////////////
              localStorage.setItem('assignmenttitle'+course.title+i,res.data.data[i].assignmentTitle);
              localStorage.setItem('assignmentdescription'+course.title+i,res.data.data[i].assignmentDescription);
              localStorage.setItem('assignmentddl'+course.title+i,res.data.data[i].assignmentDeadline);
              this.ddls.push({
                date : res.data.data[i].assignmentDeadline,
                title : course.title+"   "+res.data.data[i].assignmentTitle,
              })

            }
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
        if(localStorage.getItem('materialfileid' + localStorage.getItem("currentcourse")+i)!=null&&localStorage.getItem('materialfileid' + localStorage.getItem("currentcourse")+i)!="null"){
          const response = await this.$axios.get('/course/file', {
            params: {
              id: localStorage.getItem('materialfileid' + localStorage.getItem("currentcourse")+i)
            }
          });
          if (response.data.code === "0") {
            this.file = response.data.data;
            this.fileDownloadUrl = this.createDownloadUrl(this.file.fileData, this.file.fileName, this.file.fileType);
            this.file.downloadUrl = this.fileDownloadUrl;
            this.materials.push({
              id:localStorage.getItem('materialid' + localStorage.getItem("currentcourse")+i),
              name: localStorage.getItem('materialname' + localStorage.getItem("currentcourse")+i),
              description: localStorage.getItem('materialdescription' + localStorage.getItem("currentcourse")+i),
              file:this.file,
            });
          }else {
            this.materials.push({
              id:localStorage.getItem('materialid' + localStorage.getItem("currentcourse")+i),
              name: localStorage.getItem('materialname' + localStorage.getItem("currentcourse")+i),
              description: localStorage.getItem('materialdescription' + localStorage.getItem("currentcourse")+i),
              file: "无文件",
            });
          }
        }else{
          this.materials.push({
            id:localStorage.getItem('materialid' + localStorage.getItem("currentcourse")+i),
            name: localStorage.getItem('materialname' + localStorage.getItem("currentcourse")+i),
            description: localStorage.getItem('materialdescription' + localStorage.getItem("currentcourse")+i),
            file: "无文件",
          });
        }
        console.log(this.materials[i])
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
    onFileSelected(event) {
      // event.target.files 包含了用户选中的文件列表
      this.form.file = event.target.files[0]; // 保存第一个选中的文件
    },
    async submitForm(){
      let formData = new FormData();
      formData.append('courseId',localStorage.getItem('currentcourseid'));
      formData.append('name', this.form.name);
      formData.append('description', this.form.description);
      if (this.form.file) {
        formData.append('file', this.form.file);
      }
      try {
        const response = await this.$axios.post('/course/addMaterial', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });

        // 处理响应
        if (response.data.code === "0") {
          this.dialogVisible = false;
          this.wenzi='上传'
          this.isPopupVisible=true;
        } else {
          alert("上传失败: " + response.data.message);
        }
      } catch (error) {
        console.error('上传失败:', error);
        alert("上传过程中发生错误");
      }
    },
  }
}


</script>

<style scoped>

.assignment-container{
//margin-top: 50px;
//margin-left: 0px;
//margin-right: 70px;
  margin: 20px;
  padding-left: 10px;

}
.assignment-card {
  cursor: pointer;
  transition: box-shadow .3s;
  border: 1px solid gainsboro;

}
.assignment-card:hover {
  box-shadow: 0 4px 6px rgba(0,0,0,0.8);
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
</style>