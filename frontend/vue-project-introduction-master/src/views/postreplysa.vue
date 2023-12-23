<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshansa>


      <div class="announcement">
        <div class="announcement-header">
          <h1 class="announcement-title">题目: {{ this.currenttitle }}</h1>
          <h4 class="announcement-author">作者: {{ currentauthor }}</h4>
        </div>
        <hr class="divider"> <!-- 添加分隔线 -->
        <div class="announcement-content">
          <h3>内容:</h3>
          <p>{{ currentcontent }}</p>
        </div>
        <hr class="divider"> <!-- 添加分隔线 -->
        <!--        <el-col :span="17" class="posts-container">-->
        <div v-for="reply in replys" :key="reply.replyContent" class="post">
          <h3>{{ reply.authorType }} 回答者: {{ reply.authorname }}</h3>
          <p>{{ reply.replyContent }}</p>
        </div>
        <!--        </el-col>-->
        <h4>你的回答：</h4>
        <input type="text" v-model="yourreplyContent">
        <el-button type="primary" @click="submitReply()">提交</el-button>


      </div>
    </shitshansa>
    <div v-if="isPopupVisible" class="popup">
      <div class="popup-content">
        <p>提交成功！</p>
        <button @click="returnTocourse">关闭</button>
      </div>
    </div>
    <!-- 你的其他内容 -->
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
      currenttitle: '',
      currentauthor: '',
      currentcontent:  '',
      replys:[],
      currentpostid:'',
      yourReplyContent:'',
      isPopupVisible:false,
    };
  },
  async created(){
    this.currentpostid=localStorage.getItem('currentpostid');
    this.currentauthor=localStorage.getItem('currentauthor');
    this.currenttitle=localStorage.getItem('currenttitle');
    this.currentcontent=localStorage.getItem('currentcontent');
    this.loadpostreply();
    this.loadLocalStorageData();
  },
  components: {
    shitshansa
  },
  methods: {
    submitReply(){
      const currentpostid=localStorage.getItem('currentpostid');
      const yourreplycontent=this.yourreplyContent;
      const submitDate = new Date();
      const formattedDate = submitDate.toISOString().split('T')[0]; // 获取 yyyy-MM-dd 格式
      this.$axios.get('/post/releaseReply', {
        params: {
          replyAuthor: localStorage.getItem('id'),
          authorType: localStorage.getItem('userType'),
          replyContent: yourreplycontent,
          postId: currentpostid,
          replyTime: formattedDate,
        }
      }).then(res => {
        if (res.data.code === "0") {
          this.yourreplyContent = "";
          this.isPopupVisible=true;
          this.$axios.get('/post/getReplyByPostId',{
            params: {
              postId:this.currentpostid,
            }
          }).then((res) => {
            if (res.data.code === "0") {
              this.replys=[];
              localStorage.setItem('replylength'+this.currentpostid,res.data.data.length);
              for(let i=res.data.data.length-1;i>=0;i--){
                localStorage.setItem('replyContent'+this.currentpostid+i, res.data.data[i].replyContent);
                localStorage.setItem('replyAuthor'+this.currentpostid+i, res.data.data[i].replyAuthor);
                localStorage.setItem('replyDate'+this.currentpostid+i, res.data.data[i].replyDate);
                localStorage.setItem('authorType'+this.currentpostid+i, res.data.data[i].authorType);
                if (res.data.data[i].authorType==='TEACHER'){
                  this.$axios.get('/teacher/getTeacherInfoById',{
                    params:{
                      id:res.data.data[i].replyAuthor,
                    }
                  }).then(response=>{
                    if(response.data.code==="0"){
                      this.replys.push({
                        authorType:res.data.data[i].authorType,
                        replyContent: res.data.data[i].replyContent,
                        author: res.data.data[i].replyAuthor,
                        authorname:response.data.data.name,
                      })
                    }
                  })
                }else if(res.data.data[i].authorType==='STUDENT'){
                  this.$axios.get('/student/getStudent',{
                    params:{
                      id:res.data.data[i].replyAuthor,
                    }
                  }).then(response=>{
                    if(response.data.code==="0"){
                      this.replys.push({
                        authorType:res.data.data[i].authorType,
                        replyContent: res.data.data[i].replyContent,
                        author: res.data.data[i].replyAuthor,
                        authorname:response.data.data.name,
                      })
                    }
                  })
                }
              }
            }
          }).catch(error => {
            console.error('Error loading sainfos:', error);
          });
        }
      }).catch(err => {
        console.log(err);
      })
    },
    returnTocourse(){
      this.isPopupVisible = false;
      this.$router.push('/postreplysa');
    },
    async loadpostreply(){
      this.replys=[];
      await new Promise((resolve) => setTimeout(resolve, 10)); // 模拟异步操作，这里不是必要的，只是演示用例
      this.$axios.get('/post/getReplyByPostId',{
        params: {
          postId:this.currentpostid,
        }
      }).then((res) => {
        if (res.data.code === "0") {
          localStorage.setItem('replylength'+this.currentpostid,res.data.data.length);
          for(let i=res.data.data.length-1;i>=0;i--){
            localStorage.setItem('replyContent'+this.currentpostid+i, res.data.data[i].replyContent);
            localStorage.setItem('replyAuthor'+this.currentpostid+i, res.data.data[i].replyAuthor);
            localStorage.setItem('replyDate'+this.currentpostid+i, res.data.data[i].replyDate);
            localStorage.setItem('authorType'+this.currentpostid+i, res.data.data[i].authorType);
            if (res.data.data[i].authorType==='TEACHER'){
              this.$axios.get('/teacher/getTeacherInfoById',{
                params:{
                  id:res.data.data[i].replyAuthor,
                }
              }).then(response=>{
                if(response.data.code==="0"){
                  this.replys.push({
                    authorType:res.data.data[i].authorType,
                    replyContent: res.data.data[i].replyContent,
                    author: res.data.data[i].replyAuthor,
                    authorname:response.data.data.name,
                  })
                }
              })
            }else if(res.data.data[i].authorType==='STUDENT'){
              this.$axios.get('/student/getStudent',{
                params:{
                  id:res.data.data[i].replyAuthor,
                }
              }).then(response=>{
                if(response.data.code==="0"){
                  this.replys.push({
                    authorType:res.data.data[i].authorType,
                    replyContent: res.data.data[i].replyContent,
                    author: res.data.data[i].replyAuthor,
                    authorname:response.data.data.name,
                  })
                }
              })
            }
          }
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });
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
.announcement {
  font-family: Arial, sans-serif; /* 设置字体 */
  width: 1400px;
  margin: 0 auto; /* 水平居中 */
  margin-top: 10px;
  text-align: left;
  padding: 20px;
  border: 2px solid #ccc; /* 边框 */
  border-radius: 20px; /* 圆角 */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 阴影效果 */
}

.announcement-title {

  font-size: 38px; /* 题目字体大小 */
  margin-bottom: 10px; /* 底部间距 */
}

.announcement-author {
  font-style: italic; /* 作者名斜体显示 */
  font-size: 20px;
  margin-bottom: 15px; /* 底部间距 */
}

.announcement-content h3 {
  font-size: 32px; /* 内容标题字体大小 */
  margin-bottom: 5px; /* 底部间距 */
}

/* 修改内容样式 */
.announcement-content p {
  line-height: 3; /* 行高 */

}

.divider {
  border-top: 2px solid #ccc; /* 分隔线样式 */
  margin: 20px 0; /* 上下边距 */
}
</style>