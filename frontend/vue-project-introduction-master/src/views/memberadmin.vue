<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshanadmin>

      <div class="coursecontainer">
        <el-row>
        <el-col :span="24">
          <div class="table-section">
            <h2 class="table-title">Teacher Information</h2>
            <el-table :data="teachers" style="width: 90%;" border stripe>
              <el-table-column prop="name" label="Name"></el-table-column>
              <el-table-column prop="tenure" label="Tenure"></el-table-column>
              <el-table-column prop="email" label="Email"></el-table-column>
              <el-table-column prop="department" label="Department"></el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button type="danger" size="mini" @click="deleteTeacher(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <!-- 学习助手(SA)信息表格 -->
          <div class="table-section">
            <h2 class="table-title">SA Information</h2>
            <el-table :data="saInfos" style="width: 90%;" border stripe>
              <el-table-column prop="name" label="Name"></el-table-column>
              <el-table-column prop="email" label="Email"></el-table-column>
              <el-table-column prop="major" label="Major"></el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button type="danger" size="mini" @click="deleteSA(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <!-- 学生信息表格 -->
          <div class="table-section">
            <h2 class="table-title">Student Information</h2>
            <el-table :data="studentInfos" style="width: 90%;" border stripe>
              <el-table-column prop="name" label="Name"></el-table-column>
              <el-table-column prop="email" label="Email"></el-table-column>
              <el-table-column prop="major" label="Major"></el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button type="danger" size="mini" @click="deleteStudent(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
        </el-row>
        <el-row >
        <el-button class="sumbitt" type="primary" @click="showTeacherDialog = true">添加老师</el-button>

        <!-- 添加学生按钮 -->
        <el-button class="sumbitt" type="primary" @click="showStudentDialog = true">添加学生</el-button>

        <!-- 添加学习助手按钮 -->
        <el-button class="sumbitt" type="primary" @click="showSADialog = true">添加SA</el-button>
        </el-row>
        <!-- 老师信息弹窗 -->
        <el-dialog title="添加老师" :visible.sync="showTeacherDialog">
          <el-table :data="teachersnotin" border stripe>
            <el-table-column prop="id" label="ID"></el-table-column>
            <el-table-column prop="name" label="Name"></el-table-column>
            <el-table-column prop="email" label="Email"></el-table-column>
            <el-table-column prop="department" label="Department"></el-table-column>
            <el-table-column prop="tenure" label="Tenure"></el-table-column>
            <el-table-column label="操作">
              <template v-slot="{row}">
                <el-button type="primary" size="small" @click="addTeacher(row)">添加</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-dialog>

        <!-- 学生信息弹窗 -->
        <el-dialog title="添加学生" :visible.sync="showStudentDialog">
          <el-table :data="studentsnotin" border stripe>
            <el-table-column prop="id" label="ID"></el-table-column>
            <el-table-column prop="name" label="Name"></el-table-column>
            <el-table-column prop="level" label="Level"></el-table-column>
            <el-table-column prop="major" label="Major"></el-table-column>
            <el-table-column prop="email" label="Email"></el-table-column>
            <el-table-column label="操作">
              <template v-slot="{row}">
                <el-button type="primary" size="small" @click="addStudent(row)">添加</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-dialog>

        <!-- SA信息弹窗 -->
        <el-dialog title="添加SA" :visible.sync="showSADialog">
          <el-table :data="sanotin" border stripe>
            <el-table-column prop="id" label="ID"></el-table-column>
            <el-table-column prop="name" label="Name"></el-table-column>
            <el-table-column prop="level" label="Level"></el-table-column>
            <el-table-column prop="major" label="Major"></el-table-column>
            <el-table-column prop="email" label="Email"></el-table-column>
            <el-table-column label="操作">
              <template v-slot="{row}">
                <el-button type="primary" size="small" @click="addSA(row)">添加</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-dialog>

        <div v-if="isPopupVisible" class="popup">
          <div class="popup-content">
            <p>{{ this.wenzi }}成功！</p>
            <button @click="returnToprotects" class="sumbitt">关闭</button>
          </div>
        </div>
      </div>
    </shitshanadmin>
    <!-- 你的其他内容 -->
  </div>
</template>
<script setup>
import shitshanadmin from "@/components/shitshanadmin.vue";
export default {
  data() {
    return {
      courses: [],
      posts: [],
      materials: [],
      assignments: [],
      projects: [],
      ddls: [],
      wenzi: '',
      saInfos: [],
      studentInfos: [],
      courseDescription:'',
      teachers: [],
      studentsnotin: [],
      sanotin: [],
      teachersnotin: [],
      showTeacherDialog: false,
      showStudentDialog: false,
      showSADialog: false,
      isPopupVisible: false, // 控制弹窗显示的布尔值
    };
  },
  async created(){
    // await this.loadAllCoursesinfo,
    await this.getallcourses();
    await this.loadLocalStorageData();
    await this.loadAllCoursesinfo(),
        await this.loadLocalStorageData();
    await this.loadStudentsAndSA();
    await this.loadnotin();


  },
  components: {
    shitshanadmin
  },
  methods: {
   async loadnotin() {
     this.studentsnotin = [];
     this.sanotin=[];
     this.teachersnotin=[];
     await this.$axios.get('/admin/getStudentNotInCourse', {
       params: {
         courseId: localStorage.getItem('currentcourseid'),
       }
     }).then((res) => {
       if (res.data.code === "0") {
          for (let i = 0; i < res.data.data.length; i++) {
            this.studentsnotin.push(res.data.data[i]);
          }
       }
     }).catch(error => {
       console.error('Error loading sainfos:', error);
     });

     await this.$axios.get('/admin/getTeacherNotInCourse', {
       params: {
         courseId: localStorage.getItem('currentcourseid'),
       }
     }).then((res) => {
       if (res.data.code === "0") {
         for (let i = 0; i < res.data.data.length; i++) {
           this.teachersnotin.push(res.data.data[i]);
         }
       }
     }).catch(error => {
       console.error('Error loading sainfos:', error);
     });

     await this.$axios.get('/admin/getSANotInCourse', {
       params: {
         courseId: localStorage.getItem('currentcourseid'),
       }
     }).then((res) => {
       if (res.data.code === "0") {
         for (let i = 0; i < res.data.data.length; i++) {
           this.sanotin.push(res.data.data[i]);
         }
       }
     }).catch(error => {
       console.error('Error loading sainfos:', error);
     });
      },



     async addTeacher(row) {
        // 在这里处理添加老师的逻辑
       await this.$axios.get('/admin/addTeacher', {
         params: {
           courseId: localStorage.getItem('currentcourseid'),
           teacherId: row.id,
         }
       }).then((res) => {
         if (res.data.code === "0") {
           this.showTeacherDialog = false;
           this.wenzi = "增加";
           this.isPopupVisible = true;
         }
       }).catch(error => {
         console.error('Error loading sainfos:', error);
       });
      },
    async addStudent(row) {
        // 在这里处理添加学生的逻辑
      await this.$axios.get('/admin/addStudent', {
        params: {
          courseId: localStorage.getItem('currentcourseid'),
          studentId: row.id,
        }
      }).then((res) => {
        if (res.data.code === "0") {
          this.showStudentDialog = false;
          this.wenzi = "增加";
          this.isPopupVisible = true;
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });
      },
    async addSA(row) {
        // 在这里处理添加SA的逻辑
      await this.$axios.get('/admin/addSA', {
        params: {
          courseId: localStorage.getItem('currentcourseid'),
          studentId: row.id,
        }
      }).then((res) => {
        if (res.data.code === "0") {
          this.showSADialog = false;
          this.wenzi = "增加";
          this.isPopupVisible = true;
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });
      },
      // ...其他方法...



    async deleteTeacher(teacher) {
      await this.$axios.get('/admin/deleteTeacher', {
        params: {
          courseId: localStorage.getItem('currentcourseid'),
          teacherId: teacher.id,
        }
      }).then((res) => {
        if (res.data.code === "0") {
          this.wenzi = "删除";
          this.isPopupVisible = true;
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });
    },
   async deleteSA(sa) {
     await this.$axios.get('/admin/deleteSA', {
       params: {
         courseId: localStorage.getItem('currentcourseid'),
         studentId: sa.id,
       }
     }).then((res) => {
       if (res.data.code === "0") {
         this.wenzi = "删除";
         this.isPopupVisible = true;
       }
     }).catch(error => {
       console.error('Error loading sainfos:', error);
     });

    },
    async deleteStudent(student) {
      await this.$axios.get('/admin/deleteStudent', {
        params: {
          courseId: localStorage.getItem('currentcourseid'),
          studentId: student.id,
        }
      }).then((res) => {
        if (res.data.code === "0") {
          this.wenzi = "删除";
          this.isPopupVisible = true;
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });
    },

    async loadStudentsAndSA(){
      this.saInfos = [];
      this.studentInfos = [];
      this.teachers = [];
      await this.$axios.get('/course/getAllSA', {
        params: {
          courseId: localStorage.getItem('currentcourseid')
        }
      }).then((res) => {
        if (res.data.code === "0") {
          for (let i = 0; i < res.data.data.length; i++) {
            this.saInfos.push({
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
      //加载学生信息
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

      await this.$axios.get('/course/getTeacher', {
        params: {
          courseId: localStorage.getItem('currentcourseid')
        }
      }).then((res) => {
        if (res.data.code === "0") {
          for (let i = 0; i < res.data.data.length; i++) {
            this.teachers.push({
              email: res.data.data[i].email,
              name: res.data.data[i].name,
              id: res.data.data[i].id,
              major: res.data.data[i].major,
              tenure: res.data.data[i].tenure,
              department: res.data.data[i].department,
              selfIntroduction: res.data.data[i].selfIntroduction,
            })
          }
        }
      }).catch(error => {
        console.error('Error loading sainfos:', error);
      });
    },

    async getallcourses() {
      const res=await  this.$axios.get('/course/getAllCourses',{
      })
      console.log(res);
      localStorage.setItem('length',res.data.code==0?res.data.data.length:0);
      console.log(localStorage.getItem('length'));

      for (let i = 0; i < localStorage.getItem('length'); i++) {

        localStorage.setItem('coursesid'+i,res.data.data[i].courseId);
        localStorage.setItem('courses'+i,res.data.data[i].courseName);
        localStorage.setItem(res.data.data[i].courseId,res.data.data[i].courseName);
        localStorage.setItem(res.data.data[i].courseName,res.data.data[i].courseId);
        localStorage.setItem('coursecode'+i,res.data.data[i].courseCode);
        localStorage.setItem('courseDescription'+res.data.data[i].courseId,res.data.data[i].courseDescription);
        localStorage.setItem('getdescriptionbyid'+res.data.data[i].courseId,res.data.data[i].courseDescription);
      }

    },
    async returnToprotects(){
     await this.loadnotin();
await this.loadStudentsAndSA();
      this.isPopupVisible = false;
    },
    async deleteCourse(course) {
      await this.$axios.get('/admin/deleteCourse', {
        params: {
          courseId: course.id
        }
      }).then((res) => {
        if (res.data.code === "0") {

          this.wenzi = "删除";
          this.isPopupVisible = true;
        }
      }).catch(error => {
        console.error('Error loading course materials:', error);
      });
      console.log("删除课程", course.title);
    },
    editCourse(course) {
      // 这里添加修改课程信息的逻辑
      console.log("修改课程信息", course.title);
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
      this.$router.push('/courseofadmin');
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
  width: 100%;
  margin-top: 50px;
  margin-left: 50px;
}
.course-card{

  margin-top: 20px;
  margin-left: 20px;
  margin-right: 20px;
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
.table-section {
  margin-left: 100px;
  margin-bottom: 30px; /* 表格间距 */
}

.table-title {
  font-size: 20px;
  color: #333;
  margin-bottom: 10px; /* 标题和表格之间的间距 */
}
</style>