<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshan>

      <div class="coursecontainer">

        <el-row :gutter="20">
          <el-col v-for="course in courses" :key="course.id" :span="11">
            <el-card class="course-card" @click.native="goTo(course)">
              <h3>{{ course.code }}</h3>
              <h3>{{ course.title }}</h3>
            </el-card>
          </el-col>
        </el-row>

      </div>
    </shitshan>>
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
    // await this.loadAllCoursesinfo,
    await this.loadLocalStorageData();
    await this.loadAllCoursesinfo();

  },
  components: {
    shitshan
  },
  methods: {
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
          console.log(res.data.code)
          console.log(course.id)
          if (res.data.code === "0") {
            console.log(res.data.data)
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
        // //加载attendances
        // await this.$axios.get('/grade/getAttendanceGradeByCourseIdAndStudentId', {
        //   params: {
        //     courseId: course.id,
        //     studentId: localStorage.getItem('id')
        //   }
        // }).then((res) => {
        //   if (res.data.code === "0") {
        //     localStorage.setItem('attendancesLength'+course.title,res.data.data.length)
        //     for (let i = 0; i < localStorage.getItem('attendancesLength'+course.title); i++) {
        //       localStorage.setItem('attendancedate'+course.title+i,res.data.data[i].attendanceDate);
        //       localStorage.setItem('attendanceproportion'+course.title+i,res.data.data[i].proportion);
        //       if (res.data.data[i].attended) {
        //         localStorage.setItem('attendancegrade'+course.title+i,100);
        //       }else {
        //         localStorage.setItem('attendancegrade'+course.title+i,0);
        //       }
        //       localStorage.setItem('attendancemaxScore'+course.title+i,res.data.data[i].maxScore);
        //     }
        //   }
        // }).catch(error => {
        //   console.error('Error loading course attendances:', error);
        // });
        //加载assignment成绩
        // for (let i = 0; i < localStorage.getItem('courseAssignmentLength'+ course.title); i++) {
        //   await this.$axios.get('/grade/getAssignmentGrade', {
        //     params: {
        //       studentId: localStorage.getItem('id'),
        //       assignmentId: localStorage.getItem('assignmentid'+course.title+i)
        //     }
        //   }).then((res) => {
        //     if (res.data.code === "0") {
        //       localStorage.setItem('assignmentgrade' + course.title + i, res.data.data[0].grade);
        //       localStorage.setItem('assignmentmaxScore' + course.title + i, res.data.data[0].maxScore);
        //       localStorage.setItem('assignmentproportion' + course.title + i, res.data.data[0].proportion);
        //       localStorage.setItem('assignmentgradeDescription' + course.title + i, res.data.data[0].gradeDescription)
        //     }
        //   }).catch(error => {
        //     console.error('Error loading assignment grade:', error);
        //   });
        // }
        // //加载project成绩
        // for (let i = 0; i < localStorage.getItem('projectsLength'+ course.title); i++) {
        //   await this.$axios.get('/grade/getProjectGrade', {
        //     params: {
        //       studentId: localStorage.getItem('id'),
        //       projectId: localStorage.getItem('projectid'+course.title+i)
        //     }
        //   }).then((res) => {
        //     if (res.data.code === "0") {
        //       localStorage.setItem('projectgrade' + course.title + i, res.data.data[0].grade);
        //       localStorage.setItem('projectmaxScore' + course.title + i, res.data.data[0].maxScore);
        //       localStorage.setItem('projectproportion' + course.title + i, res.data.data[0].proportion);
        //       localStorage.setItem('projectgradeDescription' + course.title + i, res.data.data[0].gradeDescription);
        //     }
        //   }).catch(error => {
        //     console.error('Error loading project grade:', error);
        //   });
        // }
      }
    },

    goTo(route) {
// 假设使用 Vue Router 进行导航    goTo(route) {
// 假设使用 Vue Router 进行导航
      localStorage.setItem("currentcourseid",route.id);
      localStorage.setItem("currentcourse",route.title);
      this.$router.push('/courseofteacher');
      this.loadLocalStorageData();
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
.coursecontainer{
  margin-top: 50px;
  margin-left: 50px;
}
.course-card{

  margin-top: 20px;
  margin-left: 20px;
  margin-right: 20px;
}
</style>