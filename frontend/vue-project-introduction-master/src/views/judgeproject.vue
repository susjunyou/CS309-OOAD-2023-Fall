<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshan>

      <div class="grading-interface">
        <!-- 左侧区域：学生作业展示 -->
        <div class="assignment-display">
          <!-- 文本展示区域 -->
          <div class="text-submission">
            <h3>小组文本提交</h3>
            <div class="text-content">
              <!-- 这里用v-html来展示HTML内容，也可以用{{ }}来展示纯文本 -->
              <p v-if="this.content!=null">{{ this.content }}</p>
              <p v-else class="placeholder">没有文本内容</p>
            </div>
          </div>
          <div class="file-submission">
            <h3>小组文件提交</h3>
            <!-- 如果有文件URL，则显示下载链接，否则显示占位符 -->
            <a v-if="file.downloadUrl" :href="file.downloadUrl" :download="file.fileName">{{ file.fileName }}</a>
            <p v-else class="placeholder">没有文件提交</p>
          </div>
        </div>

        <!-- 右侧区域：批改表单 -->
        <div class="grading-form">
          <el-form @submit.prevent="submitGrade">
            <div class="grading-form-title">
              <el-form-item label="评分">
                <el-input-number v-model="grade" :min="0" :max="100"></el-input-number>
              </el-form-item>
            </div>
            <el-form-item label="评注" >
              <el-input
                  type="textarea"
                  v-model="comment"
                  :rows="30" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" native-type="submit" @click.prevent="submitGrade">提交评分</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </shitshan>>
    <!-- 你的其他内容 -->
    <div v-if="isPopupVisible" class="popup">
      <div class="popup-content">
        <p>提交成功！</p>
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
      file:'',
      comment: '',
      grade: null,
      content: '',
      fileDownloadUrl: '',
      isPopupVisible: false,
      teammembers: [],
    };
  },
  async created(){
    this.loadLocalStorageData();
    this.content = localStorage.getItem("currentcontent");
    this.teammembers = localStorage.getItem("currentteammembers");
    console.log(this.teammembers);
    await this.getFileData();

  },
  components: {
    shitshan
  },
  methods: {
   async submitGrade(){
      const res1 = await this.$axios.get('/team/findTeamMembers', {
        params: {
          teamId: localStorage.getItem("currentteamid"),
        }
      });
      if (res1.data.code === "0") {
        for(let i = 0; i < res1.data.data.length; i++){
          try { const response =  this.$axios.get('/grade/updateProjectGrade', {
            params: {
              studentId: res1.data.data[i].id,
              grade: this.grade,
              grade_description: this.comment,
              projectSubmissionId: localStorage.getItem("currentprojectsubmissionid"),
            }
          });if (response.data.code === "0") {
                    console.log("success");
          }

          } catch (error) {
            console.error('Error loading files:', error);
          }
        }
        this.isPopupVisible = true;
      }


    },
    returnToprotects(){
      this.isPopupVisible = false;
      this.$router.push('/projectsofteacher');
    },
    async getFileData() {
      console.log(localStorage.getItem("currentfileid"));
      if(localStorage.getItem("currentfileid")!=null&&localStorage.getItem("currentfileid")!="null"){
        try {
          const response = await this.$axios.get('/course/file', {
            params: {
              id: localStorage.getItem("currentfileid"),
            }
          });
          if (response.data.code === "0") {
            this.file = response.data.data;
            this.fileDownloadUrl = this.createDownloadUrl(this.file.fileData, this.file.fileName, this.file.fileType);
            this.file.downloadUrl = this.fileDownloadUrl;
          }
        } catch (error) {
          console.error('Error loading files:', error);
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
    // createDownloadUrl() {
    //   // 创建Blob对象，假设filedata是字符串或能被Blob构造函数接受的格式
    //   const blob = new Blob([this.file.filedata], { type: this.file.filetype });
    //   // 创建一个指向该Blob的URL
    //   this.file.downloadUrl = URL.createObjectURL(blob);
    // },
    // submitGrade(){
    //   this.$axios.get('/grade/addAssignmentGrade', {
    //     params: {
    //       studentId: Number(localStorage.getItem("currentstudentid")),
    //       assignmentId: Number(localStorage.getItem("currentassignmentid")),
    //       grade: this.grade,
    //       grade_description: this.comment,
    //     }
    //   }).then((res) => {
    //     if (res.data.code === "0") {
    //
    //     }
    //   }).catch(error => {
    //     console.error('Error loading sainfos:', error);
    //   });
    // },
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
/* 其他样式保持不变 */
.grading-interface {
  margin-left: 20px;
  width: 90%;
  display: flex;
  flex-direction: row; /* 水平布局 */
  height: 100vh; /* 设置为视口高度 */
  margin: 0; /* 移除默认边距 */
}

.placeholder {
  color: #ccc;
}

/* 调整左侧区域的样式，使其更加紧凑 */
.assignment-display {
  flex: 1; /* 占用剩余空间 */
  /* ...其他样式 */
  width: 60%;
  /* 其他样式... */
}

/* 调整右侧表单的宽度 */
.grading-form {
  width: 40%;
  height: 60vh;
  /* 其他样式... */
}
.grading-form {
  flex: 1; /* 占用剩余空间 */
  display: flex;
  flex-direction: column; /* 垂直布局 */
}
.assignment-display, .grading-form {
  width: 50%; /* Adjust the width as necessary */
  /* Other styles... */
}
.grading-form form {
  flex-grow: 1; /* 允许表单内容扩展 */
  display: flex;
  flex-direction: column; /* 垂直布局 */
  justify-content: space-between; /* 分散对齐子元素 */

}
.assignment-display > div, .grading-form > form {
  margin: 10px;
  padding: 10px;
  /* Other styles... */
}
.el-form-item {
  margin-bottom: 20px; /* 添加或调整间距 */
}
/* 调整文本和文件展示区域的内边距 */
.file-submission {
  padding: 10px;
  margin-bottom: 20px;
  border: 1px solid #ebebeb;
  height: 20vh;
  /* 其他样式... */
}
.text-submission {
  padding: 10px;
  margin-bottom: 20px;
  border: 1px solid #ebebeb;
  height: 60vh;
  /* 其他样式... */
}
.grading-form-title{
  border: 1px solid #dcdfe6; /* 设置边框颜色和宽度 */
  padding: 10px; /* 设置内边距，使边框与星星图标有些距离 */
  border-radius: 4px; /* 如果需要，可以设置边框圆角 */
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