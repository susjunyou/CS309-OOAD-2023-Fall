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
      <el-button type="primary" @click="loginClick">Login</el-button>
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
      _this.isLoginClick = true;
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
        } else {
          console.log("error")
        }
      })
    },
    registerClick() {
      this.$router.push('/register')
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

