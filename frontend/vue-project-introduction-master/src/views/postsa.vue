


<script setup>
import shitshan from "@/components/shitshan.vue";
export default {
  components: {
    shitshan
  }
}

</script>

<template>
  <div>
    <!-- 你的其他内容 -->
    <shitshan>
      <div class="assignment-container">
        <!-- ...之前的代码... -->
        <el-row :gutter="20">
          <el-col v-for="assignment in assignments" :key="assignment.id" :span="6" >
            <el-card @click.native="submitassignment(assignment)" class="assignment-card">
              <h3>{{ assignment.title }}</h3>
              <p>截止日期：{{ assignment.ddl }}</p>
            </el-card>
          </el-col>
        </el-row>
        <!-- ...之后的代码... -->

      </div>
      <div>
        <p>asdihawdb</p>
      </div>
    </shitshan>>
    <!-- 你的其他内容 -->
  </div>
</template>


<style scoped>

</style>