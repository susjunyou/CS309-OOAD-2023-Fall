<template>
  <div>
    <div class="titletext">
      <p>Project helper login</p>
    </div>



  <div class="container">
    <el-col :span="7" :offset="9">
      <div id="loginForm">
      <p style="font-size: 30px;">Login</p>
      <LoginForm :form="form"></LoginForm>

      </div>

    </el-col>

  </div>
  </div>
</template>

<script>
import LoginForm from "@/components/LoginForm";
import {mapState} from "vuex";

export default {
  name: "Login",
  components: {
    LoginForm
  },
  data() {
    return {}
  },
  methods: {},
  computed: {
    ...mapState("purchase", {
      form: state => state.form
    })
  }
}
</script>

<style>


.user-type label {
  margin-right: 10px;
}

.user-type input[type="radio"] {
  margin-right: 5px;
}
.container {
  background-image: url("../assets/login.png");
  width: 100%;
  min-height: 848.5px;
 background-size: 100% 100%;
  background-repeat: no-repeat; /* 背景图片不重复 */

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

#loginForm {
  text-align: center;
  background-color: white;
  margin-top: 170px;
  padding: 20px;
  border-radius: 10px;
  height: 350px;
  opacity: 0.85;
}






</style>