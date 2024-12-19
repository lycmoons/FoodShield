<script setup>
import {reactive, ref} from "vue";
// import { UserFilled, Document, Message, PieChart, SwitchButton } from '@element-plus/icons-vue';
import router from "@/router/index.js";
import {post} from "@/net/index.js"
import { ElLoading, ElMessage } from "element-plus";
import axios from "axios";
    

// 数据报告 
const form = reactive({
  title: "",
  content: "",
  date: new Date().toLocaleString(), // 默认时间
}); 
const formRef = ref();
const reportFileName = ref(""); 
const loading = ref(false); 
const isEditing = ref(false); 
const originalTitle = ref(""); 
const originalContent = ref("");

const rule = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }],
};

// 生成数据报告 
const generateReport = async () => { 
  loading.value = true; 
  reportFileName.value = ""; // 清空之前的文件名 
  form.title = ""; // 清空之前的标题 
  form.content = ""; // 清空之前的内容 

  // 模拟生成报告 
  setTimeout(() => { 
    const generatedTitle = "食品安全数据报告"; 
    const generatedContent = "这是一个食品安全数据报告的内容。"; 
    const generatedFileName = "食品安全数据报告.txt"; 

    form.title = generatedTitle; 
    form.content = generatedContent; 
    reportFileName.value = generatedFileName;
    originalTitle.value = generatedTitle; 
    originalContent.value = generatedContent; 
    loading.value = false; 
  }, 2000); // 模拟加载时间 
};

// 下载报告 
const downloadReport = () => { 
  const blob = new Blob([reportContent.value], { type: "text/plain;charset=utf-8" }); 
  const link = document.createElement("a"); 
  link.href = URL.createObjectURL(blob); 
  link.download = reportFileName.value; 
  link.click(); 
};

// 编辑报告
const editReport = () => { 
  isEditing.value = true; 
}; 

// 取消编辑
const cancelEdit = () => { 
  isEditing.value = false; 
  form.title = originalTitle.value; 
  form.content = originalContent.value;
}; 

// 保存编辑 
const saveEdit = () => { 
  originalTitle.value = form.title;
  originalContent.value = form.content; 
  isEditing.value = false; 
  ElMessage.success("数据报告已保存！"); 
};

// 清空内容
const clearReport = () => { 
  form.title = ""; 
  form.content = "";
  reportFileName.value = ""; 
};

// 发布报告 
const publishReport = () => { 
  formRef.value.validate((valid) => {
    if (valid) {
      const formData = new FormData();
      formData.append("title", form.title);
      formData.append("content", form.content);
      formData.append("date", form.date);
      ElMessage.success("数据报告已发布！"); 
    } else {
      ElMessage.error("请填写完整的表单信息");
    }
  });
};
</script>
    
<template>
  <el-container style="height: 100%; width: 100%">
    <el-header style="display: flex; justify-content: space-between; align-items: center;">
      <h3>食品安全报告生成与发布</h3>
    </el-header>

    <el-main>
      <el-card style="width: 100%; margin-bottom: 20px;">
        <template #header>
          <span style="font-size: 16px; font-weight: bold;">生成食品安全数据报告</span>
        </template>
        <el-button type="primary" @click="generateReport" :loading="loading">生成报告</el-button>
        <div v-if="reportFileName" style="margin-top: 10px;"> 
          <p>{{ reportFileName }}</p>
          <el-button type="success" @click="downloadReport">下载</el-button>
        </div>
      </el-card>
      <el-form ref="formRef" :model="form" label-position="top" :rules="rule">
        <el-card style="width: 100%; margin-bottom: 20px;">
          <template #header>
            <span style="font-size: 16px; font-weight: bold;">报告标题</span>
          </template>
          <div v-if="isEditing">
            <el-form-item label="报告标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入报告标题" style="margin-bottom: 10px;" />
            </el-form-item>
          </div>
          <p v-else>
            <el-form-item label="报告标题" prop="title">
              {{ form.title }}
            </el-form-item>
          </p>
        </el-card>
        
        <el-card style="width: 100%; margin-bottom: 20px;">
          <template #header>
            <span style="font-size: 16px; font-weight: bold;">报告内容</span>
          </template>
          <div v-if="isEditing">
            <el-form-item label="报告内容" prop="content">
              <el-input v-model="form.content" type="textarea" :rows="10" placeholder="请输入报告内容" />
            </el-form-item>
          </div>
          <p v-else>
            <el-form-item label="报告内容" prop="content">
              {{ form.content }}
            </el-form-item>
          </p>
        </el-card>
        
        <el-card style="width: 100%; margin-bottom: 20px; ">
          <el-container style="display: flex; justify-content: space-between;">
            <el-button type="info" @click="editReport" v-if="!isEditing">编辑</el-button>
            <el-button type="info" @click="cancelEdit" v-if="isEditing">取消编辑</el-button>
            <el-button type="success" @click="saveEdit" v-if="isEditing">保存</el-button>
            <el-button type="primary" @click="publishReport" v-if="!isEditing">发布</el-button>
            <el-button type="warning" @click="clearReport">清空</el-button>
          </el-container>
        </el-card>
      </el-form>
    </el-main>
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
}

.el-main {
  padding: 20px;
}

.el-card {
  margin: 10px auto;
  max-width: 1000px;
  box-shadow: 0 4px 12px rgba(53, 4, 4, 0.1);
  background-color: #f9fafc;
}
</style>