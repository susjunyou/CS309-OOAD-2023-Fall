<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshangaimima>

      <div>
        <div class="change-password-form">
          <h2>修改密码</h2>
          <form @submit.prevent="changePassword">
            <div>
              <label for="currentPassword">当前密码:</label>
              <input type="password" id="currentPassword" v-model="currentPassword" required>
            </div>
            <div>
              <label for="newPassword">新密码:</label>
              <input type="password" id="newPassword" v-model="newPassword" required>
            </div>
            <div>
              <label for="confirmPassword">确认新密码:</label>
              <input type="password" id="confirmPassword" v-model="confirmPassword" required>
            </div>
            <button type="submit">提交</button>
          </form>
          <p v-if="errorMessage" style="color: red;">{{ errorMessage }}</p>
          <p v-if="successMessage" style="color: green;">{{ successMessage }}</p>
        </div>
      </div>
    </shitshangaimima>
    <div v-if="isPopupVisible" class="popup">
      <div class="popup-content">
        <p>修改成功！</p>
        <button @click="close">关闭</button>
      </div>
    </div>
    <!-- 你的其他内容 -->
  </div>
</template>
<script setup>
import shitshangaimima from "@/components/shitshangaimima.vue";
export default {
  data() {
    return {
      ddls: [
        // ...其他DDL
      ],
      attrs: [],
      // 初始化组件数据属性
      courses: [],
      posts: [],
      assignments: [],
      projects: [],
      materials: [],
      myValue: '',
      major: '',
      id :0,
      email:'',
      name:'',
      team: {
        title: '',
        description: '',
        teamSize: 0
      },
      maxpeople:0,
      projectid:0,
      sid:0,
      saInfos: [],
      studentInfos: [],
      showSaDialog: false, // 控制SA信息对话框的显示
      showStudentDialog: false, // 控制学生信息对话框的显示
      courseDescription:'',
      isPopupVisible: false, // 控制弹窗显示的布尔值
      currentPassword: '',
      newPassword: '',
      confirmPassword: '',
      errorMessage: '',
      successMessage: '',
      technologystack:"",
      programmingskill:"",
      intendedteammate:"",
    };
  },
  async created(){
    this.id = localStorage.getItem('id');
    this.name = localStorage.getItem('name');
    this.major = localStorage.getItem('major');
    this.email = localStorage.getItem('email');
    await this.loadLocalStorageData(); // 使用 async/await 等待数据加载完成
    await this.loadStudentsAndSA();
    this.myValue=localStorage.getItem("currentcourse");
    this.courseDescription=localStorage.getItem("getdescriptionbyid"+localStorage.getItem("currentcourseid"));  },
  components: {
    shitshangaimima
  },
  methods: {
    close(){
      this.isPopupVisible = false;
      this.$router.push('/teacherhomepage')

    },
    update(){
      this.dialogVisible=true;
      this.edit.e_id = this.id;
      this.edit.e_email = this.email;
      this.edit.e_phoneNumber = localStorage.getItem('phoneNumber');
      this.edit.e_selfIntroduction = localStorage.getItem('selfIntroduction');
      this.edit.technologystack = localStorage.getItem('technologystack');
      this.edit.programmingskill = localStorage.getItem('programmingskill');
      this.edit.intendedteammate = localStorage.getItem('intendedteammate');
    },
    commitUpdate(){
      //this.id = this.edit.e_id;
      this.email = this.edit.e_email;
      localStorage.setItem('id',this.edit.e_id);
      localStorage.setItem('email',this.edit.e_email);
      localStorage.setItem('phoneNumber',this.edit.e_phoneNumber);
      localStorage.setItem('selfIntroduction',this.edit.e_selfIntroduction);
      localStorage.setItem('technologystack',this.edit.technologystack);
      localStorage.setItem('programmingskill',this.edit.programmingskill);
      localStorage.setItem('intendedteammate',this.edit.intendedteammate);
      this.dialogVisible = false;
      this.$axios.get('/student/updateStudentDetails',{
        params: {
          id:localStorage.getItem('id'),
          email:localStorage.getItem('email'),
          phoneNumber:localStorage.getItem('phoneNumber'),
          selfIntroduction:localStorage.getItem('selfIntroduction'),
          technologyStack:localStorage.getItem('technologystack'),
          programmingSkill:localStorage.getItem('programmingskill'),
          intendedTeammate:localStorage.getItem('intendedteammate'),
        }
      }).then(res => {
        console.log('dd');
        if(res.data.code === "0"){
          this.successMessage = '个人资料修改成功';
          this.errorMessage = '';
          localStorage.setItem('id',this.edit.e_id);
          localStorage.setItem('email',this.edit.e_email);
          localStorage.setItem('phoneNumber',this.edit.e_phoneNumber);
          localStorage.setItem('selfIntroduction',this.edit.e_selfIntroduction);
          localStorage.setItem('technologystack',this.edit.technologystack);
          localStorage.setItem('programmingskill',this.edit.programmingskill);
          localStorage.setItem('intendedteammate',this.edit.intendedteammate);
          console.log('sss');
          this.isPopupVisible = true;
        }else {
          console.log("error")
        }
      }).catch(error => {
        console.log(error)
      })
    },
    saClick() {
      this.showSaDialog = true;
    },
    studentClick() {
      this.$router.push('/membersofteacher');
    },
    async loadStudentsAndSA(){
      this.saInfos = [];
      this.studentInfos = [];
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

    },
    changePassword() {
      // 这里可以添加逻辑来验证密码是否符合要求
      this.successMessage = '';
      let value=this.newPassword;
      if(this.currentPassword !== localStorage.getItem('password')){
        this.errorMessage = '原密码错误';
        return;
      }
      if (this.newPassword !== this.confirmPassword) {
        this.errorMessage = '新密码和确认密码不匹配';
        return;
      }
      if(this.currentPassword === this.newPassword) {
        this.errorMessage = '新密码不能与原密码相同';
        return;
      }
      this.$axios.get('/student/updatePassword',{
        params: {
          id:localStorage.getItem('id'),
          password:this.newPassword
        }
      }).then(res => {
        if(res.data.code === "0"){
          this.successMessage = '密码修改成功';
          this.errorMessage = '';
          this.currentPassword = '';
          this.newPassword = '';
          this.confirmPassword = '';
          localStorage.setItem('password',value);
          this.isPopupVisible=true;
        }else {
          console.log("error")
        }
      }).catch(error => {
        console.log(error)
      })
      setTimeout(() => {
        this.successMessage = '';
      }, 3000); // 这里的 3000 表示消息会在 3 秒后消失，你可以根据需要修改时间
    },
    go(route) {
      this.$router.push(route+'ofteacher');
    },
  }
}

</script>

<style scoped>

</style>