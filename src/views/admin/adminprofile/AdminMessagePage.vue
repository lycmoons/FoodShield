<script setup>
import {onMounted, reactive} from "vue";
import {getWithToken} from "@/net/index.js";

function getAllMessage(){
  // getWithToken('/api/message/all-message', (data) => {
  //   for(const item of data){
  //     form.message.push({
  //       id: item.id,
  //       content: item.content,
  //       date: (new Date(item.date)).toLocaleString()
  //     })
  //   }
  // })
}

onMounted(() => {
  // getAllMessage()
})

let form = reactive({
  message: []
})
</script>

<template>
  <el-container style="height: 100%; width: 100%">
    <el-header style="display: flex; justify-content: space-between; align-items: center;">
      <h3>管理员信息中心</h3>
    </el-header>
        
    <el-card>
      <div class="message-list">
        <div class="message-item" v-for="msg in form.message" :key="msg.id">
          <p class="message-content">{{ msg.content }}</p>
          <p class="message-date">{{ msg.date || "日期未设定" }}</p>
        </div>
      </div>
    </el-card>
  </el-container>
</template>

<style scoped>
.el-header {
  background-color: #f5f5f5;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: bold;
  padding: 0 20px;
  margin-bottom: 10px;
}

.message-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 90%;
}

.message-item {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.message-content {
  font-size: 16px;
  font-weight: bold;
  margin: 0 0 5px 0;
}

.message-date {
  font-size: 14px;
  color: #666;
  margin: 0;
}
</style>