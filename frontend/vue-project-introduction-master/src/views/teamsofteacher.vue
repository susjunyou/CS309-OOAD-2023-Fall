<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshan>

      <div class="assign" style="width: 88%">
        <div class="assignment-container">
          <!-- ...之前的代码... -->
          <el-row :gutter="20">
            <el-col v-for="project in projects" :key="project.id" :span="6" >
              <el-card  class="assignment-card" @click.native="join(project)" style="min-height: 170px">
                <h3>{{ project.title }}</h3>
                <p>截止日期：{{ project.ddl }}</p>
                <p>组队截止时间：{{ project.teamddl }}</p>
              </el-card>
            </el-col>
          </el-row>
          <!-- ...之后的代码... -->

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
    };
  },
  async created(){
    await this.loadAllCoursesinfo();
    await this.loadLocalStorageData();
    await this.loadAllCoursesinfo();

  },
  components: {
    shitshan
  },
  methods: {
    join(route) {
      localStorage.setItem("currentprojectid",route.id)
      localStorage.setItem("currentprojectmaxpeopleinteam",route.maxpeopleinteam);
      this.$router.push('/modifyinformation');
    },

  async   getCoursesofteacher() {
    await  this.$axios.get('/teacher/getCourseInfo',{
        params:{
          teacherId:localStorage.getItem('id')
        }
      })
          .then((res) => {
            console.log(res.data);
            localStorage.setItem('length',res.data.data.length);
            console.log(localStorage.getItem('length'));
            for (let i = 0; i < localStorage.getItem('length'); i++) {
              console.log(res.data.data[i]);
              console.log(res.data.data[i].courseId);
              console.log(res.data.data[i].courseName);
              console.log(res.data.data[i].courseCode);
              console.log(res.data.data[i].courseDescription);

              localStorage.setItem('coursesid'+i,res.data.data[i].courseId);
              localStorage.setItem('courses'+i,res.data.data[i].courseName);
              localStorage.setItem(res.data.data[i].courseId,res.data.data[i].courseName);
              localStorage.setItem(res.data.data[i].courseName,res.data.data[i].courseId);
              localStorage.setItem('coursecode'+i,res.data.data[i].courseCode);
              localStorage.setItem('courseDescription'+res.data.data[i].courseId,res.data.data[i].courseDescription);
              localStorage.setItem('getdescriptionbyid'+res.data.data[i].courseId,res.data.data[i].courseDescription);
            }
            console.log(localStorage.getItem('courses0'));
          })
          .catch((err) => {
            console.log(err);
          });
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
              localStorage.setItem('teamddl' + course.title+i,res.data.data[i].teamDeadline);
              this.ddls.push({
                date : res.data.data[i].projectDeadline,
                title : course.title+"   "+res.data.data[i].projectTitle,
              })
              console.log(res.data.data.length)
              console.log(res.data.data[i])
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
              teamddl: localStorage.getItem('teamddl' + localStorage.getItem("currentcourse")+i),
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
              teamddl: localStorage.getItem('teamddl' + localStorage.getItem("currentcourse")+i),
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
            teamddl: localStorage.getItem('teamddl' + localStorage.getItem("currentcourse")+i),
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
  margin: 20px;
  padding-left: 10px;
}
.assignment-card{
  cursor: pointer;
  transition: box-shadow .3s;
  border: 1px solid gainsboro;
}

.assignment-card:hover {
  box-shadow: 0 4px 6px rgba(0,0,0,0.8);
}
</style>