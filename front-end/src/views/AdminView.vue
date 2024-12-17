<script setup>
import router from "@/router/index.js";
import {getWithToken, removeToken} from "@/net/index.js";
import {ElMessage} from "element-plus";

function logoutSuccess(data){
  removeToken()
  ElMessage.success('退出登录成功')
  router.push("/")
}

function userLogout(){
  getWithToken('/api/auth/logout', logoutSuccess)
}

</script>

<template>

  <div>
    <el-button @click="userLogout">退出登录</el-button>
  </div>
</template>

<style scoped>

</style>