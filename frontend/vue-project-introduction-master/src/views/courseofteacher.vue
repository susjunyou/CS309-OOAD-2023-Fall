<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshan>

      <div >
        <h1 style="padding-left: 100px">{{currentCourse}}</h1>
        <div class="post" style="padding-right: 200px">
          <h3 style="right: 500px">CourseDescription: {{currentCourseDescription}}</h3>
        </div>
        <h2>Announcements</h2>
        <el-button type="primary" class="custom-button" @click="showPostForm  ">发布通知</el-button>
        <div class="assignment-container" style="padding-left: 200px">
          <!-- ...之前的代码... -->
          <el-row :gutter="20">
            <el-col v-for="anouncement in anouncements" :key="anouncement.id" :span="5" >
              <el-card  class="assignment-card" @click.native="showanouncement(anouncement)">
                <h3>{{ anouncement.title }}</h3>
                <p>发布者：{{ anouncement.authorname }}</p>
              </el-card>
            </el-col>
          </el-row>
          <!-- ...之后的代码... -->
        </div>

        <el-dialog
            :visible.sync="showForm"
            title="发布通知"
            width="50%"
        >
          <el-table :data="formData" style="width: 100%">
            <el-table-column label="标题" prop="content"></el-table-column>
          </el-table>
          <el-input
              v-model="formData.title"
              placeholder="输入标题"
              clearable
          ></el-input>
          <el-table :data="formData" style="width: 100%">
            <el-table-column label="内容" prop="content"></el-table-column>
          </el-table>
          <el-input
              v-model="formData.content"
              placeholder="输入内容"
              type="textarea"
              :rows="10"
              resize="none"
          ></el-input>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="cancelForm">取消</el-button>
        </el-dialog>

      </div>

      <div v-if="isPopupVisible" class="popup">
        <div class="popup-content">
          <p>提交成功！</p>
          <button @click="returnTocourse">关闭</button>
        </div>
      </div>
    </shitshan>
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
      currentCourse:'',
      currentCourseDescription: '',
      anouncements:'',
      showForm:false,
      formData: {
        title: '',
        content: '' // 表单数据
      },
      isPopupVisible:false,
    };
  },
  async created(){
    this.loadLocalStorageData();
    this.currentCourse=localStorage.getItem("currentcourse");
    this.currentCourseDescription=localStorage.getItem('courseDescription'+localStorage.getItem('currentcourseid'));
  },
  components: {
    shitshan
  },
  methods: {
    showanouncement(anouncement){
      localStorage.setItem("anouncementtitle",anouncement.title)
      localStorage.setItem("anouncementauthorname",anouncement.authorname)
      localStorage.setItem("anouncementcontent",anouncement.content)
      this.$router.push({path:'/anouncementofteacher'})
    },
    async submitForm() {
      const submitDate = new Date();
      const formattedDate = submitDate.toISOString().split('T')[0]; // 获取 yyyy-MM-dd 格式
      const textContent = this.formData.content;
      const texttitle=this.formData.title;
      // const postInfo = {
      //   postTitle: texttitle,
      //   postContent: textContent,
      //   time: formattedDate,
      //   postAuthor:localStorage.getItem('id'),
      //   authorType:localStorage.getItem('userType'),
      //   courseId:localStorage.getItem('currentcourseid'),
      // };
      // console.log(postInfo)
      this.$axios.get('/post/releasePost', {
        params:{
          postTitle: texttitle,
          postContent: textContent,
          time: formattedDate,
          postAuthor:localStorage.getItem('id'),
          authorType:localStorage.getItem('userType'),
          courseId:localStorage.getItem('currentcourseid'),
          postType: 'ANNOUNCEMENT'
        }
      }).then(resp => {
        console.log(resp.data)
        if (resp.data.code === "0") {
          this.isPopupVisible=true;
          this.showForm = false;
          this.formData.content='';
          this.formData.title='';
          this.$axios.get('/course/posts', {
            params: {
              courseId: localStorage.getItem('currentcourseid'),
            }
          }).then((res) => {
            if (res.data.code === "0") {
              this.anouncements=[];
              localStorage.setItem('coursePostLength'+localStorage.getItem('currentcourse'),res.data.data.length)
              for(let i = localStorage.getItem('coursePostLength'+localStorage.getItem('currentcourse'))-1;i>=0;i--) {
                localStorage.setItem('postid' + localStorage.getItem('currentcourse') + i, res.data.data[i].postId);
                localStorage.setItem('post' + localStorage.getItem('currentcourse') + i, res.data.data[i].postContent);
                localStorage.setItem('posttitle' + localStorage.getItem('currentcourse') + i, res.data.data[i].postTitle);
                localStorage.setItem('postauthor' + localStorage.getItem('currentcourse') + i, res.data.data[i].postAuthor);
                localStorage.setItem('postType'+localStorage.getItem('currentcourse') + i,res.data.data[i].postType);
                if(localStorage.getItem('postType'+localStorage.getItem('currentcourse') + i) ==='ANNOUNCEMENT'){
                  if (res.data.data[i].authorType==='TEACHER'){
                    this.$axios.get('/teacher/getTeacherInfoById',{
                      params:{
                        id:res.data.data[i].postAuthor,
                      }
                    }).then(response=>{
                      if(response.data.code==="0"){
                        this.anouncements.push({
                          course: localStorage.getItem('currentcourse'),
                          id: res.data.data[i].postId,
                          title: res.data.data[i].postTitle,
                          content: res.data.data[i].postContent,
                          author: res.data.data[i].postAuthor,
                          authorname:response.data.data.name,
                        })
                      }
                    })
                  }else if(res.data.data[i].authorType==='STUDENT'){
                    this.$axios.get('/student/getStudent',{
                      params:{
                        id:res.data.data[i].postAuthor,
                      }
                    }).then(response=>{
                      if(response.data.code==="0"){
                        this.anouncements.push({
                          course: localStorage.getItem('currentcourse'),
                          id: res.data.data[i].postId,
                          title: res.data.data[i].postTitle,
                          content: res.data.data[i].postContent,
                          author: res.data.data[i].postAuthor,
                          authorname:response.data.data.name,
                        })
                      }
                    })
                  }
                }
              }
            }
          }).catch(error => {
            console.error('Error loading course posts:', error);
          });

        }
      }).catch(err => {
        console.log(err);
      })
      // 处理提交表单的操作，这里可以将数据提交至后端或其他操作
      // 提交后的逻辑处理完成后隐藏表单
      this.showForm = false;
      // 这里可以在提交后清空表单数据，如果需要的话
      // this.formData.content = '';
    },
    cancelForm() {
      // 点击取消按钮隐藏表单
      this.showForm = false;
      // 这里可以在取消操作时清空表单数据，如果需要的话
      // this.formData.content = '';
    },
    returnTocourse(){
      this.isPopupVisible = false;
      this.$router.push('/courseofteacher');
    },
    showPostForm() {
      this.showForm = true; // 点击发表按钮显示表单
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
      this.anouncements=[];
      this.$axios.get('/course/posts', {
        params: {
          courseId: localStorage.getItem('currentcourseid'),
        }
      }).then((res) => {
        if (res.data.code === "0") {
          this.posts=[];
          localStorage.setItem('coursePostLength'+localStorage.getItem('currentcourse'),res.data.data.length)
          for(let i = localStorage.getItem('coursePostLength'+localStorage.getItem('currentcourse'))-1;i>=0;i--) {
            localStorage.setItem('postid' + localStorage.getItem('currentcourse') + i, res.data.data[i].postId);
            localStorage.setItem('post' + localStorage.getItem('currentcourse') + i, res.data.data[i].postContent);
            localStorage.setItem('posttitle' + localStorage.getItem('currentcourse') + i, res.data.data[i].postTitle);
            localStorage.setItem('postauthor' + localStorage.getItem('currentcourse') + i, res.data.data[i].postAuthor);
            localStorage.setItem('postType'+localStorage.getItem('currentcourse') + i,res.data.data[i].postType);
            if(localStorage.getItem('postType'+localStorage.getItem('currentcourse') + i) ==='ANNOUNCEMENT'){
              if (res.data.data[i].authorType==='TEACHER'){
                this.$axios.get('/teacher/getTeacherInfoById',{
                  params:{
                    id:res.data.data[i].postAuthor,
                  }
                }).then(response=>{
                  if(response.data.code==="0"){
                    this.anouncements.push({
                      course: localStorage.getItem('currentcourse'),
                      id: res.data.data[i].postId,
                      title: res.data.data[i].postTitle,
                      content: res.data.data[i].postContent,
                      author: res.data.data[i].postAuthor,
                      authorname:response.data.data.name,
                    })
                  }
                })
              }else if(res.data.data[i].authorType==='STUDENT'){
                this.$axios.get('/student/getStudent',{
                  params:{
                    id:res.data.data[i].postAuthor,
                  }
                }).then(response=>{
                  if(response.data.code==="0"){
                    this.anouncements.push({
                      course: localStorage.getItem('currentcourse'),
                      id: res.data.data[i].postId,
                      title: res.data.data[i].postTitle,
                      content: res.data.data[i].postContent,
                      author: res.data.data[i].postAuthor,
                      authorname:response.data.data.name,
                    })
                  }
                })
              }
            }
          }
        }
      }).catch(error => {
        console.error('Error loading course posts:', error);
      });
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

.post {
  text-align: left;
  border: 1px solid gainsboro;
  margin-bottom: 10px;
  padding: 10px;
  margin-left:400px;
  margin-right: 200px;
  //position: absolute;
  //right: 800px;

}

.assignment-container {
  //margin: 20px;
  padding-left: 20px;
}

.assignment-card {
  cursor: pointer;
  transition: box-shadow .3s;
  border: 1px solid gainsboro;
  margin-top: 10px;
  width: 250px;
  height: 150px;
}

.assignment-card:hover {
  box-shadow: 0 4px 6px rgba(0,0,0,0.8);
}
.header{
  padding-left: 200px;
  position: absolute;
  top: 0;
  left: 0;
  right: 200px;
  bottom: 0;
}
</style>