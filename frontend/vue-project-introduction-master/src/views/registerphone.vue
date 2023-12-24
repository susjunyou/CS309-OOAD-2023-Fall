<template >
  <div>
    <div class="titletext">
      <p>Project helper register</p>
    </div>
    <div class="abcd">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" class="demo-ruleForm">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>

        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>



        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="form.password"></el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input type="password" v-model="form.confirmPassword"></el-input>
        </el-form-item>

        <el-form-item label="用户类型" prop="userType">
          <input type="radio" id="student" value="STUDENT" v-model="form.userType">
          <label for="student">学生</label>

          <input type="radio" id="teacher" value="TEACHER" v-model="form.userType">
          <label for="teacher">老师</label>

        </el-form-item>

        <el-form-item label="性别" prop="sex">
          <input type="radio" id="nan" value="true" v-model="form.sex">
          <label for="student">男</label>

          <input type="radio" id="nv" value="false" v-model="form.sex">
          <label for="teacher">女</label>
        </el-form-item>
        <el-form-item v-if="form.userType === 'TEACHER'" label="部门" prop="department" :rules="departmentRules">
          <el-select v-model="form.department" placeholder="请选择">
            <el-option
                v-for="department in departments"
                :key="department.value"
                :label="department.label"
                :value="department.value">
            </el-option>
          </el-select>
        </el-form-item>


        <el-form-item>
          <el-button type="primary" @click.prevent="submitForm()">注册</el-button>
          <el-button @click="resetForm('form')">重置</el-button>
          <el-button @click="retunn()">返回</el-button>

        </el-form-item>

      </el-form>
    </div>
    <div v-if="isPopupVisible" class="popup">
      <div class="popup-content">
        <p>注册成功！</p>
        <button @click="returnToprotects" class="sumbitt">关闭</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        username: '',
        phone: '',
        password: '',
        confirmPassword: '',
        userType: '',
        sex: '',
        name:'',
        department:''
      },
      departments: [
        { label: 'Computer Science', value: 'COMPUTER_SCIENCE' },
        { label: 'Electrical Engineering', value: 'ELECTRICAL_ENGINEERING' },
        { label: 'Mechanical Engineering', value: 'MECHANICAL_ENGINEERING' },
        { label: 'Chemistry', value: 'CHEMISTRY' },
        { label: 'Biology', value: 'BIOLOGY' },
        { label: 'Mathematics', value: 'MATHEMATICS' },
        { label: 'Physics', value: 'PHYSICS' },
        { label: 'Geography', value: 'GEOGRAPHY' }
        // 添加其他部门选项
      ],
      isPopupVisible: false,
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { message: '请输入有效的手机号码', trigger: 'blur' }
        ],

        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: this.validatePass, trigger: 'blur' }
        ],
        userType: [
          { required: true, message: 'Please choose userType', trigger: 'blur' },
        ],
        sex: [
          { required: true, message: 'Please choose sex', trigger: 'blur' },
        ],
        name: [
          { required: true, message: 'Please input youer name', trigger: 'blur' },
        ],
      }
    };
  },
  computed: {
    departmentRules() {
      return [
        {
          required: this.form.userType === 'TEACHER',
          message: 'Please input department',
          trigger: 'blur'
        },
      ];
    }
  },

  methods: {
    retunn(){
      this.$router.push('/login');
    },
    returnToprotects(){
      this.isPopupVisible = false;
      this.$router.push('/login');
    },
    submitForm() {
      if(this.form.userType=="STUDENT"){

        console.log(this.form.username)
        console.log(this.form.password)
        console.log(this.form.userType)
        console.log(this.form.sex)
        this.$axios.get('/register/registerStudent', {
          params: {
            name: this.form.name,
            account: this.form.username,
            password: this.form.password,
            userType: this.form.userType,
            sex: this.form.sex,
            phoneNumber:this.form.phone,

          }
        }).then(response => {
          console.log(this.form.username)
          console.log(this.form.password)
          console.log(response)
          if (response.data.code === "0") {
            console.log(response.data.data);
            this.isPopupVisible = true;
          } else {
            console.log("error")
          }

        })
      }else{
        this.$axios.get('/register/registerTeacher', {
          params: {
            name: this.form.name,
            account: this.form.username,
            password: this.form.password,
            userType: this.form.userType,
            sex: this.form.sex,
            department: this.form.department,
            phoneNumber:this.form.phone,
          }
        }).then(response => {
          console.log(this.form.username)
          console.log(this.form.password)
          console.log(response)
          if (response.data.code === "0") {
            console.log(response.data.data);
            this.isPopupVisible = true;
          } else {
            console.log("error")
          }

        })
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    validatePass(rule, value, callback) {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    }
  }
};
</script>

<style>
/* 可根据需要添加样式 */
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
.titletext {
  font-size: 20px;
  background-image: url("../assets/schoolHUI.png");
  background-size: 18% 100%;
  background-repeat: no-repeat;
  border-bottom-right-radius: 4px;
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
  border-top-right-radius: 4px;
  list-style-type: none;

  height: 50px;
  margin: 0;
  padding: 0;
  overflow: hidden;
  color: yellow;
  text-align: center;
  background-color: #2bb7b3;
}
.abcd {
  margin-top: 300px; /* 在头部栏和这个div之间添加间隔 */

  border: 1px solid #dcdfe6; /* 添加边框 */
  border-radius: 8px; /* 圆角边框 */
  padding: 100px; /* 内部内容和边框之间的间隔 */
  max-width: 600px; /* 最大宽度限制 */
  margin: 20px auto; /* 水平居中并添加顶部间隔 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  background-color: white; /* 背景色 */
}
</style>
