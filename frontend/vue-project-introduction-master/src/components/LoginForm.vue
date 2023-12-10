<template>
  <el-form ref="form" :model="form" label-width="80px" :rules="rules">
    <el-form-item label="Name" prop="username">
      <el-input v-model="form.username" ></el-input>
      <p class="login" v-if="isLoginClick && this.isUsernameEmpty">Empty Username!</p>
    </el-form-item>
    <el-form-item label="Password" prop="password">
      <el-input v-model="form.password" type="password" ></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="loginClick ">Login</el-button>
      <el-button @click="registerClick">Register</el-button>
    </el-form-item>
    <div class="user-type">
      <label>User Type:</label>
      <input type="radio" id="student" value="student" v-model="form.userType">
      <label for="student">Student</label>

      <input type="radio" id="teacher" value="teacher" v-model="form.userType">
      <label for="teacher">Teacher</label>

      <input type="radio" id="admin" value="admin" v-model="form.userType">
      <label for="admin">Administrator</label>
    </div>
    <p class="login" v-if="isLoginClick" style="color: red">Wrong Password or Valid Username</p>

  </el-form>
</template>

<script>

import {mapState} from "vuex";

export default {
  name: "LoginForm",
  props: ['form'],
  data() {
    const UserNameValidator = (rule, value, callback) => {
      const re = /^[0-9]+$/;
      if (!value) {
        return callback(new Error('Please input userName'));
      }
      if (!re.test(value)) {
        return callback(new Error('Invalid userName'));
      }
      callback();
    };
    return {
      isLoginClick: false,
      rules: {
        username: [
          {validator: UserNameValidator, trigger: 'blur'},
          {required: true, trigger: true}
        ],
        password: [
          { required: true, message: 'Please input password', trigger: 'blur' },
        ],

      },
    }
  },
  methods: {
    loginClick() {
      let _this = this;
      // _this.isLoginClick = true;
      console.log(_this.form.userType);
      if (_this.form.userType ==='admin') {
        _this.$axios.get('/login/admin', {
          params: {
            account: _this.form.username,
            password: _this.form.password
          }
        }).then(response => {
          console.log(_this.form.username)
          console.log(_this.form.password)
          console.log(response)
          if (response.data.code === "0") {
            console.log(response.data.data);
            this.$router.push('/StudentHomePage');
            return false
          } else {
            this.isLoginClick = true;

            console.log("error")
            return true
          }
        })

      }else if (_this.form.userType ==='student') {
        _this.$axios.get('/login/student', {
          params: {
            account: _this.form.username,
            password: _this.form.password
          }
        }).then(response => {
          console.log(_this.form.username)
          console.log(_this.form.password)
          console.log(response)
          if (response.data.code === "0") {
            console.log(response.data.data);
            localStorage.setItem('phoneNumber', response.data.data.phoneNumber);
            localStorage.setItem('major', response.data.data.major);
            localStorage.setItem('id', response.data.data.id);//student id
            localStorage.setItem('password',response.data.data.password);
            localStorage.setItem('email',response.data.data.email);
            localStorage.setItem('name',response.data.data.name);
            localStorage.setItem('userType',response.data.data.userType);
            localStorage.setItem('selfIntroduction',response.data.data.selfIntroduction);
            localStorage.setItem('technologystack',response.data.data.technologyStack);
            localStorage.setItem('programmingskill',response.data.data.programmingSkill);
            localStorage.setItem('intendedteammate',response.data.data.intendedTeammate);
            console.log(localStorage.getItem('id'));
            console.log(localStorage.getItem('major'));
            console.log(localStorage.getItem('phoneNumber'));

            this.getCourses();
            this.$router.push('/StudentHomePage');
          } else {
            this.isLoginClick = true;
            console.log("error")
          }

        })
      }else if (_this.form.userType === 'teacher') {
        _this.$axios.get('/login/teacher', {
          params: {
            account: _this.form.username,
            password: _this.form.password
          }
        }).then(response => {
          console.log(_this.form.username)
          console.log(_this.form.password)
          console.log(response)
          if (response.data.code === "0") {
            console.log(response.data.data);
            localStorage.setItem('account', _this.form.username);
            localStorage.setItem('password', _this.form.password);
            localStorage.setItem('phoneNumber', response.data.data.phoneNumber);
            localStorage.setItem('id', response.data.data.id);//student id
            localStorage.setItem('password',response.data.data.password);
            localStorage.setItem('email',response.data.data.email);
            localStorage.setItem('name',response.data.data.name);
            localStorage.setItem('userType',response.data.data.userType);
            localStorage.setItem('selfIntroduction',response.data.data.selfIntroduction);
            localStorage.setItem('department',response.data.data.department);
            localStorage.setItem('tenure',response.data.data.tenure);
            this.getCoursesofteacher();
            this.$router.push('/teacherhomepage');
          } else {
            this.isLoginClick = true;
            console.log("error")
          }
        })
      }
    },
    registerClick() {
      this.$router.push('/register')
    },
    getCourses() {
      this.$axios.get('/student/getCourseInfo',{
        params:{
          studentId:localStorage.getItem('id')
        }
      })
          .then((res) => {
            console.log(res.data);
            localStorage.setItem('length',res.data.data.length);
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
            console.log(localStorage.getItem('courses0'));
          })
          .catch((err) => {
            console.log(err);
          });
    },
    getCoursesofteacher() {
      this.$axios.get('/teacher/getCourseInfo',{
        params:{
          teacherId:localStorage.getItem('id')
        }
      })
          .then((res) => {
            console.log(res.data);
            localStorage.setItem('length',res.data.data.length);
            console.log(localStorage.getItem('length'));
            for (let i = 0; i < localStorage.getItem('length'); i++) {
              localStorage.setItem('coursesidt'+i,res.data.data[i].courseId);
              localStorage.setItem('coursest'+i,res.data.data[i].courseName);
              localStorage.setItem(res.data.data[i].courseId,res.data.data[i].courseName);
              localStorage.setItem(res.data.data[i].courseName,res.data.data[i].courseId);
              localStorage.setItem('coursecodet'+i,res.data.data[i].courseCode);
              localStorage.setItem('courseDescriptiont'+res.data.data[i].courseId,res.data.data[i].courseDescription);
              localStorage.setItem('getdescriptionbyidt'+res.data.data[i].courseId,res.data.data[i].courseDescription);
            }
            console.log(localStorage.getItem('courses0'));
          })
          .catch((err) => {
            console.log(err);
          });
    }

  },
  computed: {
    ...mapState("purchase", {
      isWrongPassword: state => !state.accountValid
    }),
    isUsernameEmpty() {
      return this.form.username === null
    }
  },


}
</script>

