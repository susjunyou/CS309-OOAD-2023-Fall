<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshan>

      <div class="assign" style="width: 88%">
        <div class="assignment-container">
          <!-- ...之前的代码... -->
          <el-row :gutter="20">
            <el-col v-for="assignment in assignments" :key="assignment.id" :span="8" >
              <el-card @click.native="submitassignment(assignment)" class="assignment-card">
                <h3>{{ assignment.title }}</h3>
                <p>截止日期：{{ assignment.ddl }}</p>
                <el-button type="danger" size="small" @click.stop="deleteAssignment11(assignment)">删除课程</el-button>
                <el-button type="primary" size="small" @click.stop="editAssignment(assignment)">修改信息</el-button>
              </el-card>
            </el-col>
          </el-row>
          <!-- ...之后的代码... -->

        </div>
        <div class="publish-button-container">
          <el-button class="sumbitt" @click="publishAssignment">发布作业</el-button>
        </div>
      </div>
    </shitshan>
    <!-- 你的其他内容 -->
    <el-dialog title="发布作业" :visible.sync="dialogVisible">
      <el-form :model="assignmentForm">
        <el-form-item label="作业标题">
          <el-input v-model="assignmentForm.title"></el-input>
        </el-form-item>
        <el-form-item label="作业描述">
          <el-input type="textarea" v-model="assignmentForm.description"></el-input>
        </el-form-item>
        <el-form-item label="截止日期">
          <el-date-picker v-model="assignmentForm.deadline" type="date" placeholder="选择日期" :disabled-date="disabledDate"></el-date-picker>
        </el-form-item>
        <el-form-item label="最高分数">
          <el-input-number v-model="assignmentForm.maxScore"></el-input-number>
        </el-form-item>
        <el-form-item label="占比">
          <el-input-number v-model="assignmentForm.proportion" :min="0" :max="100" step="0.01"></el-input-number>
        </el-form-item>
        <!-- courseId通常是选择的课程或从其他途径获得，这里假设是隐藏字段 -->
        <el-input type="hidden" v-model="assignmentForm.courseId"></el-input>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click.prevent="addAssignment">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改作业" :visible.sync="dialogVisible2">
      <el-form :model="editAssignmentForm">
        <el-form-item label="作业标题">
          <el-input v-model="editAssignmentForm.title"></el-input>
        </el-form-item>
        <el-form-item label="作业描述">
          <el-input type="textarea" v-model="editAssignmentForm.description"></el-input>
        </el-form-item>
        <el-form-item label="截止日期">
          <el-date-picker v-model="editAssignmentForm.deadline" type="date" placeholder="选择日期" :disabled-date="disabledDate"></el-date-picker>
        </el-form-item>
        <el-form-item label="最高分数">
          <el-input-number v-model="editAssignmentForm.maxScore"></el-input-number>
        </el-form-item>
        <el-form-item label="占比">
          <el-input-number v-model="editAssignmentForm.proportion" :min="0" :max="100" step="0.01"></el-input-number>
        </el-form-item>
        <!-- courseId通常是选择的课程或从其他途径获得，这里假设是隐藏字段 -->
        <el-input type="hidden" v-model="editAssignmentForm.courseId"></el-input>
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
      wenzi:'',
      dialogVisible: false,
      dialogVisible2:false,
      assignmentForm: {
        title: '',
        description: '',
        deadline: '',
        status: '',
        maxScore: '',
        proportion: '',
        releaser: '',
        releaserType: '',
        courseId: '',
      },
      editAssignmentForm:{
        title: '',
        description: '',
        deadline: '',
        status: '',
        maxScore: '',
        proportion: '',
        releaser: '',
        releaserType: '',
        courseId: '',
      },
      isPopupVisible: false,
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

    publishAssignment(){
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
    submitassignment(assignment){
      localStorage.setItem("currentassignmentid",assignment.id);
      localStorage.setItem("in_ddl",assignment.ddl);//status
      localStorage.setItem("cru_description",assignment.description);
      localStorage.setItem("cru_status",assignment.status);

      this.$router.push({ path: '/pizuoye'});
    },
    async addAssignment(){
      console.log(localStorage.getItem('id'))
      let date = new Date(this.assignmentForm.deadline);
      let formattedDate = date.toISOString().split('T')[0]; // 转换为 YYYY-MM-DD 格式
      await this.$axios.get('/assignment/addAssignment', {
        params: {
          assignmentTitle: this.assignmentForm.title,
          assignmentDescription: this.assignmentForm.description,
          assignmentDeadline: formattedDate,
          assignmentStatus: 'Started',
          maxScore: this.assignmentForm.maxScore,
          proportion: this.assignmentForm.proportion,
          releaser: localStorage.getItem('id'),
          releaserType: 'TEACHER',
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
    async deleteAssignment11(assignment) {
      console.log('indelete1')
      await this.$axios.get('/assignment/deleteAssignmentByAssignmentId', {//这里有问题，在后端接口那边是delete mapping，改了，但现在还是不行
        params: {
          assignmentId: assignment.id
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
      console.log("删除课程", assignment.title);
    },

    editAssignment(assignment) {

      // 打开编辑对话框并填充表单数据
      this.dialogVisible2 = true;//在这里出问题
      console.log(this.dialogVisible2)
      console.log('设置可视化')
      console.log(assignment)
      localStorage.setItem('currentassignmentid',assignment.id);
      localStorage.setItem('currentassignmenttitle',assignment.title);
      console.log('ceshi:'+localStorage.getItem('status'+assignment.title))
      this.editAssignmentForm.title = assignment.title;
      this.editAssignmentForm. description = assignment.description;
      this.editAssignmentForm.deadline = assignment.deadline;
      this.editAssignmentForm.status = assignment.status;
      this.editAssignmentForm. maxScore = assignment.maxScore;
      this.editAssignmentForm.proportion = assignment.proportion;
      this.editAssignmentForm.releaser = localStorage.getItem('id');
      this.editAssignmentForm. releaserType = 'TEACHER';
      this.editAssignmentForm. courseId = localStorage.getItem('currentcourseid');
console.log(this.editAssignmentForm.status )
      console.log(this.editAssignmentForm.releaser )
      console.log(this.editAssignmentForm.releaserType )

    },


    async commitEdit(){
      //updateAssignmentByAssignmentId
      console.log('this.form:'+this.editAssignmentForm.deadline)
      let date = new Date(this.editAssignmentForm.deadline);
      console.log('date:'+date)
      let formattedDate = date.toISOString().split('T')[0]; // 转换为 YYYY-MM-DD 格式
      console.log('format:'+formattedDate)
      let tit = localStorage.getItem('currentassignmenttitle')
      console.log('tit:'+tit)
      const res=await  this.$axios.get('/assignment/updateAssignmentByAssignmentId', {
        params: {
          assignmentId: localStorage.getItem('currentassignmentid'),
          assignmentTitle: this.editAssignmentForm.title,
          assignmentDescription: this.editAssignmentForm.description,
          assignmentDeadline: formattedDate,
          assignmentStatus: this.editAssignmentForm.status,
          maxScore: this.editAssignmentForm.maxScore,
          proportion: this.editAssignmentForm.proportion,
          releaser: this.editAssignmentForm.releaser,
          releaserType: this.editAssignmentForm.releaserType,
          courseId: localStorage.getItem('currentcourseid'),
        }
      })
      if (res.data.code === "0") {
        this.wenzi = "修改";
        this.dialogVisible2 = false; // 关闭对话框
        this.isPopupVisible = true;
      }
    },
    ///////////

    disabledDate(time) {
      const today = new Date(new Date().setHours(0, 0, 0, 0)); // 今天日期设置为午夜开始
      return time.getTime() < today.getTime(); // 禁止选择今天之前的日期
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
      for (let i = 0; i < localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")); i++) {
        this.assignments.push({
          id: localStorage.getItem('assignmentid' + localStorage.getItem("currentcourse")+i),
          status: localStorage.getItem('assignmentstatus' + localStorage.getItem("currentcourse")+i),//assignmentname
          title: localStorage.getItem('assignmenttitle' + localStorage.getItem("currentcourse")+i),
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
.coursecontainer{
  margin-top: 50px;
  margin-left: 50px;
}
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
.publish-button-container {
  margin-top: 20px;
  text-align: center;
  margin-left: 100px;
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