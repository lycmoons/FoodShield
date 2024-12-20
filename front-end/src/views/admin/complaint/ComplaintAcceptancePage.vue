<script setup>
import {onMounted, ref} from "vue";
// import { UserFilled, Document, Message, PieChart, SwitchButton } from '@element-plus/icons-vue';
import router from "@/router/index.js";
import {post} from "@/net/index.js"
import {ElMessage} from "element-plus";
import axios from "axios";
import { useRoute } from "vue-router";

// 路由参数
const route = useRoute();
const complaintId = ref(route.query.id || "未知");

// 投诉详情数据（模拟后端返回数据）
const complaintDetail = ref({
  id: "",
  title: "",
  accepted: "",
  complaintTime: "",
  lastProcessTime: "",
  content: "",
  photo_url: "",
});

// 处理反馈数据
const feedback = ref({
  details: "",
  status: "",
});

// 状态选项
const statusOptions = [
  { label: "未处理", value: "未处理" },
  { label: "已处理", value: "已处理" },
];

// 模拟从后端获取投诉数据
onMounted(() => {
  // 后端请求写死
  complaintDetail.value = {
    id: complaintId.value,
    title: "食品变质投诉",
    accepted: "已处理", 
    complaintTime: "2024-06-15 14:32:00",
    lastProcessTime: "2024-06-16 10:00:00",
    content: "我购买的食品已经过期且有变质的迹象，请核查。\n\n👇\n👇\n👇\n👇\n 👇",
    photo_url: "https://th.bing.com/th/id/OIP.AahSfEGcU8871YBSRGX93QHaFj?w=245&h=184&c=7&r=0&o=5&pid=1.7,https://th.bing.com/th?id=OIP.2VHrNFuZT2aXhwSLXQd4fAHaE8&w=306&h=204&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2"
  };
});

// 发送反馈
const submitFeedback = () => {
  if (!feedback.value.details || !feedback.value.status) {
    ElMessage.warning("请填写反馈详情并选择处理状态！");
    return;
  }
  // 模拟提交反馈到后端
  console.log("提交反馈：", {
    complaintId: complaintDetail.value.id,
    details: feedback.value.details,
    status: feedback.value.status,
  });

  ElMessage.success("反馈已成功提交！");
};

// 分割字符串获得图片URL数组
const getPhotoUrls = (photoUrlString) => { 
  return photoUrlString.split(',').map(url => url.trim()); 
};

// 增加图片 
const addPhoto = (event) => { 
  const file = event.target.files[0]; 
  if (file) { 
    const reader = new FileReader(); 
    reader.onload = (e) => { 
      complaintDetail.value.photo_url += `,
      ${e.target.result}`; 
    };
    reader.readAsDataURL(file); 
  } 
};

// 删除图片
const deletePhoto = (url) => { 
  const urls = getPhotoUrls(complaintDetail.value.photo_url);
  const index = urls.indexOf(url); 
  if (index > -1) { 
    urls.splice(index, 1); 
  } 
  complaintDetail.value.photo_url = urls.join(','); 
};
</script>

<template>
  <el-container>
    <!-- Header -->
    <el-header style="display: flex; justify-content: space-between; align-items: center;">
      <h3>投诉详情</h3>
      <el-button type="primary" @click="$router.go(-1)">返回</el-button>
    </el-header>

    <el-main style="display: flex; flex-direction: column; align-items: center;">
      <!-- Basic Info -->
      <el-card style="width: 80%; margin-bottom: 20px;">
        <template #header>
          <span style="font-size: 16px; font-weight: bold;">投诉基本信息</span>
        </template>
        <el-table :data="[complaintDetail]" border>
          <el-table-column prop="id" label="投诉ID" />
          <el-table-column prop="title" label="标题" />
          <el-table-column prop="accepted" label="状态" />
          <el-table-column prop="complaintTime" label="投诉时间" />
          <el-table-column prop="lastProcessTime" label="最后处理时间" />
        </el-table>
      </el-card>

      <el-card :data="[complaintDetail]" style="width: 80%; margin-bottom: 20px; max-height: 400px; overflow-y: auto;">
        <template #header>
          <span style="font-size: 16px; font-weight: bold;">投诉内容</span>
        </template>
        <p style="white-space: pre-line;">{{ complaintDetail.content }}</p>
        <div v-if="complaintDetail.photo_url" style="margin-top: 10px;">
          <el-image v-for="(url, index) in getPhotoUrls(complaintDetail.photo_url)" :key="index" :src="url" 
                    style="width: 200px; height: 200px; margin-right: 10px; cursor: pointer;" fit="cover" 
                    @click="() => $router.push(url)" />
        </div>
      </el-card>

      <el-card style="width: 80%;">
        <template #header>
          <span style="font-size: 16px; font-weight: bold;">处理反馈</span>
        </template>
        <el-form label-width="100px">
          <el-form-item label="反馈详情">
            <el-input v-model="feedback.details" type="textarea" placeholder="请输入处理反馈的具体内容" :rows="4" />
          </el-form-item>
          <el-form-item label="处理状态">
            <el-select v-model="feedback.status" placeholder="请选择处理状态" style="width: 100%;">
              <el-option
                v-for="option in statusOptions" :key="option.value" :label="option.label" :value="option.value" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitFeedback">提交反馈</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-main>
  </el-container>
</template>

<style scoped>
.el-header {
  background-color: #f5f5f5;
  font-size: 18px;
  font-weight: bold;
  padding: 0 20px;
}
.el-main {
  background-color: #ffffff;
  padding: 20px;
}
.el-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>
