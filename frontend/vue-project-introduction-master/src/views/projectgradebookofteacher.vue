<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshan>
      <div class="attendance-list">
        <hr class="separator">
        <h1>{{this.currentprojecttitle}}</h1>
        <h2>{{this.currentprojectddl}}</h2>
        <div class="file-upload">
          <input type="file"   @change="onFileSelected"/>
          <!--        @change="handleFileUpload"-->
          <el-button class="submit" @click.prevent="submitGrade" :disabled="this.disable_submit">SubmitGrade</el-button>
        </div>
        <ul>
          <li>
            <table>
              <thead>
              <tr>
                <th>名字</th>
                <th>分数</th>
                <th>比例</th>
                <th>备注</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="projectgrade in projectgrades" :key="projectgrade.grade">
                <td>{{ projectgrade.name }}</td>
                <td>{{ projectgrade.grade }}</td>
                <td>{{ projectgrade.proportion }}</td>
                <td>{{ projectgrade.gradeDescription}}</td>
              </tr>
              </tbody>
            </table>
          </li>
        </ul>
        <div id="main123" style="width: 100%; height:400px"></div>
      </div>
    </shitshan>
    <!-- 你的其他内容 -->
    <div v-if="isPopupVisible" class="popup">
      <div class="popup-content">
        <p>提交成功！</p>
        <button @click="returnToassignments">关闭</button>
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
      currentprojectid: '',
      currentprojecttitle: '',
      currentprojectddl: '',
      studentInfos:[],
      projectgrades:[],
      num60:'',
      num70:'',
      num80:'',
      num90:'',
      num100:'',
      file:null,
      isPopupVisible:false,
    };
  },
  async created(){
    this.currentprojectid=localStorage.getItem('currentprojectid');
    this.currentprojecttitle=localStorage.getItem('currentprojecttitle');
    this.currentprojectddl=localStorage.getItem('currentprojectddl');
    await this.loadLocalStorageData();
    await this.getClass();
  },
  components: {
    shitshan
  },
  methods: {
    onFileSelected(event) {
      // event.target.files 包含了用户选中的文件列表
      this.file = event.target.files[0]; // 保存第一个选中的文件
    },
    submitGrade(){
      let formData = new FormData();
      formData.append('projectId',this.currentprojectid);
      formData.append('file',this.file);
      this.$axios.post('/grade/uploadProjectGradeCSV',formData,{
        headers:{
          'Content-Type': 'multipart/form-data'
        }
      }).then(res=>{
        if(res.data.code==="0"){
          this.isPopupVisible=true;
        }
      })
    },
    returnToassignments(){
      this.isPopupVisible=false;
      this.$router.push('/gradebookofteacher')
    },
    // getClass(){
    //   // var echarts=require('echarts');
    //   // var myChart = echarts.init(document.getElementById('main123'));
    //   var option={
    //     title:{
    //       text:'成绩',
    //       x:'center',
    //     },
    //     legend:{
    //       orient:"vertical",
    //       top:'20',
    //       right:'100',
    //       data:['人数'],
    //     },
    //     tooltip:{},
    //     xAxis:{
    //       data:['<60','60~69','70~79','80~89','>=90'],
    //       axisTick:{
    //         alignWithLabel:true,
    //         show:true,
    //         interval:0
    //       }
    //     },
    //     yAxis:{},
    //     series:[{
    //       name:'人数',
    //       type:'bar',
    //       data:[this.num60,this.num70,this.num80,this.num90,this.num100],
    //       label:{
    //         show: true,
    //         position:'outside',
    //         color: 'black'
    //       }
    //     }],
    //     itemStyle:{
    //       color: 'black'
    //     }
    //   };
    //   // myChart.setOption(option);
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
      // console.log(this.projects[0])
      // console.log(this.projects[1])
      // console.log("course name="+this.myValue)
      // console.log("assleng="+localStorage.getItem('courseAssignmentLength'+localStorage.getItem("currentcourse")))
      // console.log("projectleng="+localStorage.getItem('projectsLength'+localStorage.getItem("currentcourse")))
      this.studentInfos=[];
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
      this.projectgrades=[];
      this.num60=0;
      this.num70=0;
      this.num80=0;
      this.num90=0;
      this.num100=0;
      for(let i = 0; i < this.studentInfos.length; i++) {
        await this.$axios.get('/grade/getProjectGrade',{
          params:{
            studentId:this.studentInfos[i].id,
            projectId:localStorage.getItem('currentprojectid'),
          }
        }).then((res)=>{
          if(res.data.code==="0"){
            this.projectgrades.push({
              name:this.studentInfos[i].name,
              grade:res.data.data.grade+'/'+res.data.data.maxScore,
              gradeDescription:res.data.data.gradeDescription,
              proportion:res.data.data.proportion,
            })
            if(res.data.data.grade<60){
              this.num60+=1;
            }else if(res.data.data.grade>=60 && res.data.data.grade<70){
              this.num70+=1;
            }else if(res.data.data.grade>=70 && res.data.data.grade<80){
              this.num80+=1;
            }else if(res.data.data.grade>=80 && res.data.data.grade<90){
              this.num90+=1;
            }else if(res.data.data.grade>=90){
              this.num100+=1;
            }
          }
        }).catch(err=>{
          console.error(err)
        })
      }
    },

  }
}

</script>

<style scoped>
.attendance-list li {
  background: #f3f3f3;
  border: 1px solid #ddd;
  margin-bottom: 0.5em;
  padding: 0.5em;
}
.attendance-list {
  margin: 20px; /* 设置外边距 */
  padding: 20px; /* 设置内边距 */
  border: 1px solid #ccc; /* 设置边框 */
  border-radius: 8px; /* 设置边框圆角 */
  font-family: Arial, sans-serif; /* 设置字体 */
}

.attendance-list h1 {
  font-size: 1.5em; /* 调整标题字体大小 */
  margin-bottom: 1em; /* 调整标题底部间距 */
}

.attendance-list table {
  width: 100%; /* 表格宽度占满容器 */
  border-collapse: collapse; /* 边框合并 */
  margin-bottom: 1em; /* 调整表格底部间距 */
}

.attendance-list th, .attendance-list td {
  border: 1px solid #ccc; /* 设置单元格边框 */
  padding: 8px; /* 设置单元格内边距 */
  text-align: left; /* 文本左对齐 */
}

.attendance-list th {
  background-color: #f2f2f2; /* 表头背景色 */
}

.attendance-list tbody tr:nth-child(even) {
  background-color: #f9f9f9; /* 偶数行背景色 */
}


.attendance-list {
  position: absolute;
  width: 80%;
  left: 200px; /* 右移 50px，根据需要调整 */
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