<script setup>
import {onMounted, ref} from "vue";
// import { UserFilled, Document, Message, PieChart, SwitchButton } from '@element-plus/icons-vue';
import router from "@/router/index.js";
import {post} from "@/net/index.js"
import {ElMessage, ElMessageBox} from "element-plus";
import axios from "axios";
import { useRoute } from "vue-router";


// 路由参数
const route = useRoute();
const reportId = ref(route.query.id || "未知");

// 投诉详情数据（模拟后端返回数据）
const reportDetail = ref({
  reportId: "",
  title: "",
  content: "",
  date: "",
  creator: "",
  images: [],
});

// 编辑模式开关 
const isEditing = ref(false);
// 编辑内容数据
const editDetail = ref({ 
  title: "", 
  content: "", 
});
// 启动编辑模式 
const startEditing = () => { 
  isEditing.value = true; 
  editDetail.value = { 
    title: reportDetail.value.title, 
    content: reportDetail.value.content, 
  }; 
}; 
// 取消编辑 
const cancelEditing = () => { 
  isEditing.value = false; 
  editDetail.value = { 
    title: "", 
    content: "", 
  }; 
};
// 保存编辑 
const saveEdit = () => { 
  reportDetail.value.title = editDetail.value.title;
  reportDetail.value.content = editDetail.value.content; 
  isEditing.value = false; ElMessage.success("数据报告已保存！"); 
}; 
// 删除数据报告
const deleteReport = () => { 
  console.log(123123123)
  ElMessageBox.confirm("确认删除这篇报告吗？", "提示", { 
    confirmButtonText: "确定",
    cancelButtonText: "取消", 
    type: "warning", 
  }).then(() => { 
    // 模拟删除 
    reportDetail.value = null; // 删除数据后清空报告详情
    ElMessage.success("报告已删除！"); 
    router.go(-1); // 返回上一个页面 
  }).catch(() => { 
    ElMessage.info("删除已取消"); 
  }); 
};

// 模拟从后端获取投诉数据
onMounted(() => {
  reportDetail.value = {
    id: reportId.value,
    title: "2024年11月校园食品安全数据报告",
    date: "2024-06-15 14:32:00",
    content: "苹果：5.0\n",
    //images: ["https://example.com/image1.jpg", "https://example.com/image2.jpg"] // 我先用数组了，看字符串分割要前端做还是后端做
  };
});

</script>


<template>
  <el-container>
    <!-- Header -->
    <el-header style="display: flex; justify-content: space-between; align-items: center;">
      <h3>食品安全数据报告详情</h3>
      <el-button type="primary" @click="$router.go(-1)">返回</el-button>
    </el-header>

    <!-- Main Content -->
    <el-main style="display: flex; flex-direction: column; align-items: center;">
      <!-- Basic Info -->
      <el-card style="width: 80%; margin-bottom: 20px;">
        <template #header>
          <span style="font-size: 16px; font-weight: bold;">数据报告基本信息</span>
        </template>
        <el-table :data="[reportDetail]" border>
          <el-table-column prop="id" label="报告ID" width="100" show-overflow-tooltip />
          <el-table-column prop="title" label="报告标题" show-overflow-tooltip />
          <!-- <el-table-column prop="creator" label="报告编者" /> -->
          <el-table-column prop="date" label="报告发布时间" width="180" show-overflow-tooltip />
        </el-table>
      </el-card>

      <!-- Detail Content -->
      <el-card :data="[reportDetail]" style="width: 80%; margin-bottom: 20px; max-height: 600px; overflow-y: auto;">
        <template #header>
          <span style="font-size: 16px; font-weight: bold;">报告内容</span>
          <el-button type="danger" style="float: right;" @click="deleteReport">删除</el-button>
          <el-button type="primary" style="float: right; margin-right: 10px;" @click="isEditing ? cancelEditing() : startEditing()"> 
            {{ isEditing ? "取消编辑" : "编辑" }} 
          </el-button> 
          <el-button v-if="isEditing" type="success" style="float: right; " @click="saveEdit">保存</el-button>
        </template>
        <div v-if="isEditing"> 
          <el-input v-model="editDetail.title" placeholder="请输入标题" style="margin-bottom: 10px;" /> 
          <el-input v-model="editDetail.content" type="textarea" :rows="10" placeholder="请输入内容" /> 
        </div>
        <div v-else>
        <p style="white-space: pre-line;">{{ reportDetail.content }}</p>
          <!-- <div v-if="reportDetail.images.length > 0" style="margin-top: 10px;">
            <el-image v-for="(img, index) in reportDetail.images" :key="index" :src="img" style="width: 200px; height: 200px; margin-right: 10px;" fit="cover" />
          </div> -->
        </div>
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