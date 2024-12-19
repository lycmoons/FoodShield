<script setup>
import { onMounted, reactive, ref } from "vue";
import router from "@/router/index.js";
import { ElMessage, ElMessageBox } from "element-plus";
import { useRoute } from "vue-router";

// 路由参数
const route = useRoute();
const knowledgeId = ref(route.query.id || "未知");

// 知识库文章详情数据（模拟后端返回数据）
const knowledgeDetail = ref({
  knowledgeId: "",
  title: "",
  category: "",
  content: "",
  date: "",
  photo_url: "",
});

// 表单数据
const form = reactive({
  title: "",
  category: "",
  content: "",
});

const formRef = ref();

// 提交表单
const submitForm = () => {
  form.title = editDetail.value.title;
  form.content = editDetail.value.content;
  form.category = editDetail.value.category;
  formRef.value.validate((valid) => {
    if (valid) {
      ElMessageBox.confirm("确认保存并上传这篇推文吗？", "提示", { 
        confirmButtonText: "确定",
        cancelButtonText: "取消", 
        type: "warning", 
      }).then(() => { 
        form.id = knowledgeId.value
        isEditing.value = false; 
        console.log(form)
        console.log("提交的表单数据：", form);
        ElMessage.success("保存成功");
        knowledgeDetail.value.title = editDetail.value.title; // 这里其实应该重新加载，或者直接回到上一页
        knowledgeDetail.value.content = editDetail.value.content; 
        knowledgeDetail.value.category = editDetail.value.category;
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
  category: [{ required: true, message: '请输入类别', trigger: ['blur', 'change'] }],
};

// 示例分类数据
const categories = ref(["分类1", "分类2", "分类3"]);

// 编辑模式开关
const isEditing = ref(false);

// 编辑内容数据
const editDetail = ref({
  title: "",
  category: "",
  content: "",
});

// 启动编辑模式 
const startEditing = () => { 
  isEditing.value = true; 
  editDetail.value = { 
    title: knowledgeDetail.value.title,
    category: knowledgeDetail.value.category,
    content: knowledgeDetail.value.content,
  }; 
  form.title = editDetail.value.title;
  form.content = editDetail.value.content; 
  form.category = editDetail.value.category;
}; 

// 取消编辑
const cancelEditing = () => {
  isEditing.value = false;
  editDetail.value = {
    title: "",
    category: "",
    content: "",
  };
};

// 删除知识库文章
const deleteKnowledge = () => {
  ElMessageBox.confirm("确认删除这篇文章吗？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "danger",
  }).then(() => {
    // 模拟删除
    knowledgeDetail.value = null; // 删除数据后清空知识库文章详情
    ElMessage.success("文章已删除！");
    router.go(-1); // 返回上一个页面
  }).catch(() => {
    ElMessage.info("删除已取消");
  });
};

// 模拟从后端获取知识库文章数据
onMounted(() => {
  // 后端请求写死
  knowledgeDetail.value = {
    knowledgeId: knowledgeId.value,
    title: "食品安全许可证相关知识",
    category: "分类1",
    date: "2024-06-15 14:32:00",
    content: "食品安全许可证相关知识\n1\n1\n\n\n\n1\n1\n\n\n1\n\n\n1\n\n\n1\n\n\n1\n\n1\n",
    photo_url: "https://th.bing.com/th/id/R.f5cceae4d5b934e75d32f17f46752d60?rik=A%2fg%2bviCX5rERQQ&riu=http%3a%2f%2fpic2.nipic.com%2f20090409%2f2406232_105320007_2.jpg&ehk=Xe7s%2bbbTTIPTyCnszWDwYIdBf7OF77ERlAfIkmvgick%3d&risl=&pid=ImgRaw&r=0,https://th.bing.com/th/id/OIP.iqeBi3j390BfpXYkfwL7ZwHaIC?rs=1&pid=ImgDetMain", // 示例图片地址
  };
});
</script>

<template>
  <el-container>
    <!-- Header -->
    <el-header style="display: flex; justify-content: space-between; align-items: center;">
      <h3>食品安全知识库详情</h3>
      <el-button type="primary" @click="$router.go(-1)">返回</el-button>
    </el-header>

    <!-- Main Content -->
    <el-main style="display: flex; flex-direction: column; align-items: center;">
      <!-- 基本信息 -->
      <el-card style="width: 80%; margin-bottom: 20px;">
        <template #header>
          <span style="font-size: 16px; font-weight: bold;">知识库文章基本信息</span>
        </template>
        <el-table :data="[knowledgeDetail]" border>
          <el-table-column prop="knowledgeId" label="文章ID" width="100" show-overflow-tooltip />
          <el-table-column prop="title" label="文章标题" show-overflow-tooltip />
          <el-table-column prop="category" label="文章类别" width="180" show-overflow-tooltip />
          <el-table-column prop="date" label="文章发表时间" width="180" show-overflow-tooltip />
        </el-table>
      </el-card>

      <!-- 文章内容 -->
      <el-card style="width: 80%; margin-bottom: 20px; max-height: 600px; overflow-y: auto;">
        <template #header>
          <span style="font-size: 16px; font-weight: bold;">知识库文章内容</span>
          <el-button type="danger" style="float: right; margin-right: 10px;" @click="deleteKnowledge">删除</el-button>
          <el-button type="info" style="float: right; margin-right: 10px;" @click="isEditing ? cancelEditing() : startEditing()"> 
            {{ isEditing ? "取消编辑" : "编辑" }} 
          </el-button> 
          <el-button v-if="isEditing" type="success" style="float: right;" @click="submitForm">保存</el-button>
        </template>
        <!-- 文章内容部分 -->
        <el-form ref="formRef" :model="form" label-position="top" :rules="rule">
          <div v-if="isEditing"> 
            <el-form ref="formRef" :model="form" :rules="rule" >
              <el-form-item label="标题" prop="title">
                <el-input v-model="editDetail.title" placeholder="请输入标题" style="margin-bottom: 10px;" /> 
              </el-form-item>
              <el-form-item label="分类" prop="category">
                <el-select v-model="editDetail.category" placeholder="请选择分类">
                  <el-option v-for="category in categories" :key="category" :label="category" :value="category" /> 
                </el-select> 
              </el-form-item>
              <el-form-item label="内容" prop="content">
                <el-input v-model="editDetail.content" type="textarea" :rows="10" placeholder="请输入内容" /> 
              </el-form-item>
            </el-form>
            <div style="margin-top: 10px;">
              <p style="margin-bottom: 5px;">图片预览（仅展示，不可编辑）：</p>
              <el-image v-for="(url, index) in knowledgeDetail.photo_url.split(',')" :key="index" :src="url.trim()" style="width: 200px; height: 200px; margin-right: 10px;" fit="cover" />
            </div>
          </div>
          <div v-else>
            <p style="white-space: pre-line;">{{ knowledgeDetail.content }}</p>
            <div v-if="knowledgeDetail.photo_url" style="margin-top: 10px;">
              <el-image v-for="(url, index) in knowledgeDetail.photo_url.split(',')" :key="index" :src="url.trim()" style="width: 200px; height: 200px; margin-right: 10px;" fit="cover" />
            </div>
          </div>
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
