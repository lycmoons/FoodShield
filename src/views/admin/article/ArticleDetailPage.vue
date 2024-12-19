<script setup>
import {onMounted, reactive, ref} from "vue";
import router from "@/router/index.js";
import {post} from "@/net/index.js"
import {ElMessage, ElMessageBox} from "element-plus";
import axios from "axios";
import { useRoute } from "vue-router";


// 路由参数
const route = useRoute();
const articleId = ref(route.query.id || "未知");

// 投诉详情数据（模拟后端返回数据）
const articleDetail = ref({
  articleId: "",
  title: "",
  content: "",
  date: "",
  //creator: "",
  photo_url: "",
});

const form = reactive({
  id: "",
  title: "",
  content: "",
})

const formRef = ref()

// 提交表单
const submitForm = () => {
  form.title = editDetail.value.title;
  form.content = editDetail.value.content; 
  formRef.value.validate((valid) => {
    if (valid) {
      ElMessageBox.confirm("确认保存并上传这篇推文吗？", "提示", { 
        confirmButtonText: "确定",
        cancelButtonText: "取消", 
        type: "warning", 
      }).then(() => { 
        form.id = articleId.value
        isEditing.value = false; 
        console.log(form)
        console.log("提交的表单数据：", form);
        ElMessage.success("保存成功");
        articleDetail.value.title = editDetail.value.title; // 这里其实应该重新加载，或者直接回到上一页
        articleDetail.value.content = editDetail.value.content; 
        router.go(-1);
      }).catch(() => { 
        ElMessage.info("保存已取消"); 
      }); 
      
    } else {
      ElMessage.error("请填写完整的表单信息");
    }
  });
};

const rule = {
  title: [{ required: true, message: '请输入标题', trigger: ['blur', 'change'] }],
  content: [{ required: true, message: '请输入内容', trigger: ['blur', 'change'] }],
};

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
    title: articleDetail.value.title, 
    content: articleDetail.value.content, 
  }; 
  form.title = editDetail.value.title;
  form.content = editDetail.value.content; 
}; 

// 取消编辑 
const cancelEditing = () => { 
  isEditing.value = false; 
  editDetail.value = { 
    title: "", 
    content: "",
  }; 
};

// 删除推文
const deleteArticle = () => { 
  ElMessageBox.confirm("确认删除这篇推文吗？", "提示", { 
    confirmButtonText: "确定",
    cancelButtonText: "取消", 
    type: "warning", 
  }).then(() => { 
    // 模拟删除 
    console.log("推文已删除:", articleDetail.value.id); 
    articleDetail.value = null;
    ElMessage.success("推文已删除！"); 
    router.go(-1);
  }).catch(() => { 
    ElMessage.info("删除已取消"); 
  }); 
};

// 模拟从后端获取投诉数据
onMounted(() => {
  // 后端请求写死
  articleDetail.value = {
    id: articleId.value,
    title: "关于对食品过期问题的回答",
    date: "2024-06-15 14:32:00",
    content: "回答回答回答\n1\n1\n\n\n\n1\n1\n\n\n1\n\n\n1\n\n\n1\n\n\n1\n\n1\n",
    photo_url: "https://www.bing.com/th?id=OIP.MsJMuEfa01l7haJtKOCXEQHaFj&w=179&h=185&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2,https://www.bing.com/th?id=OIP.iqeBi3j390BfpXYkfwL7ZwHaIC&w=164&h=185&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2", // 我先用数组了，看字符串分割要前端做还是后端做
  };
});
</script>


<template>
  <el-container>
    <!-- Header -->
    <el-header style="display: flex; justify-content: space-between; align-items: center;">
      <h3>食品安全推文详情</h3>
      <el-button type="primary" @click="$router.go(-1)">返回</el-button>
    </el-header>

    <!-- Main Content -->
    <el-main style="display: flex; flex-direction: column; align-items: center;">
      <el-card style="width: 80%; margin-bottom: 20px;">
        <template #header>
          <span style="font-size: 16px; font-weight: bold;">推文基本信息</span>
        </template>
        <el-table :data="[articleDetail]" border>
          <el-table-column prop="id" label="推文ID" width="100" show-overflow-tooltip />
          <el-table-column prop="title" label="推文标题" show-overflow-tooltip />
          <!-- <el-table-column prop="creator" label="推文编者" /> -->
          <el-table-column prop="date" label="推文发表时间" width="180" show-overflow-tooltip />
        </el-table>
      </el-card>

      <el-card :data="[articleDetail]" style="width: 80%; margin-bottom: 20px; max-height: 600px; overflow-y: auto;">
        <template #header>
          <span style="font-size: 16px; font-weight: bold;">推文内容</span>
          <el-button type="danger" style="float: right; margin-right: 10px;" @click="deleteArticle">删除</el-button>
          <el-button type="primary" style="float: right; margin-right: 10px;" @click="isEditing ? cancelEditing() : startEditing()"> 
            {{ isEditing ? "取消编辑" : "编辑" }} 
          </el-button> 
          <el-button type="success" style="float: right;" @click="submitForm"  v-if="isEditing" >保存</el-button>
        </template>
        
        <div v-if="isEditing">
          <el-form ref="formRef" :model="form" :rules="rule" >
            <el-form-item label="标题" prop="title">
              <el-input v-model="editDetail.title" placeholder="请输入标题" style="margin-bottom: 10px;" /> 
            </el-form-item>
            <el-form-item label="内容" prop="content">
              <el-input v-model="editDetail.content" type="textarea" :rows="10" placeholder="请输入内容" />
            </el-form-item>
          </el-form>
          <div style="margin-top: 10px;">
            <p style="margin-bottom: 5px;">图片预览（仅展示，不可编辑）：</p>
            <el-image v-for="(url, index) in articleDetail.photo_url.split(',')" :key="index" :src="url.trim()" style="width: 200px; height: 200px; margin-right: 10px;" fit="cover" />
          </div>
        </div>
        <div v-else>
          <p style="white-space: pre-line;">{{ articleDetail.content }}</p>
          <div v-if="articleDetail.photo_url" style="margin-top: 10px;">
            <el-image v-for="(url, index) in articleDetail.photo_url.split(',')" :key="index" :src="url.trim()" style="width: 200px; height: 200px; margin-right: 10px;" fit="cover" />
          </div>
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