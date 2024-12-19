<script setup>
import {onMounted, reactive, ref} from "vue";
import router from "@/router/index.js";
import {post} from "@/net/index.js"
import {ElMessage, ElMessageBox} from "element-plus";
import axios from "axios";
import { useRouter } from "vue-router";


// 投诉详情数据（模拟后端返回数据）
const reportDetail = ref({
  photo_url: "",
});

const form = reactive({
  image: null,
})

const formRef = ref(null);
const isEditing = ref(false);

// 删除数据报告
const deleteReport = () => { 
  ElMessageBox.confirm("确认删除这篇报告吗？", "提示", { 
    confirmButtonText: "确定",
    cancelButtonText: "取消", 
    type: "warning", 
  }).then(() => { 
    // 模拟删除 
    reportDetail.value.photo_url = null; // 删除数据后清空报告详情
    ElMessage.success("报告已删除！"); 
    router.go(-1); // 返回上一个页面 
  }).catch(() => { 
    ElMessage.info("删除已取消"); 
  }); 
};

// 模拟从后端获取投诉数据
onMounted(() => {
  fetchData()
});

const startEditing = () => { 
  isEditing.value = true
}

const submitForm = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      const formData = new FormData();
      formData.append("image", form.image);
      isEditing.value = false;
      axios.post('/api/food', formData)
        .then(response => {
          ElMessage.success('食品信息已提交！');
          fetchData() // 更新页面
        })
        .catch(error => {
          ElMessage.error('提交失败，请重试！');
          reportDetail.value.photo_url = ""
          fetchData()
        });
    } else {
      ElMessage.error('请填写完整的表单信息');
    }
  });
};

// 处理图片选择
const handleFileChange = (event) => {
  form.image = event.target.files;
}

const cancelEditing = () => {
  isEditing.value = false;
};

// 删除信息 
const deleteInfo = () => { 
  ElMessageBox.confirm('确认删除这份报告吗？', '提示', { 
    confirmButtonText: '确定', 
    cancelButtonText: '取消', 
    type: 'warning', 
  }).then(() => { 
    ElMessage.success("信息已删除！"); 
    fetchData(); // 刷新
  }).catch(() => { 
    ElMessage.info("删除已取消");
  }); 
};

const fetchData = () => { 
  reportDetail.value = {
    photo_url: "https://th.bing.com/th/id/R.f5cceae4d5b934e75d32f17f46752d60?rik=A%2fg%2bviCX5rERQQ&riu=http%3a%2f%2fpic2.nipic.com%2f20090409%2f2406232_105320007_2.jpg&ehk=Xe7s%2bbbTTIPTyCnszWDwYIdBf7OF77ERlAfIkmvgick%3d&risl=&pid=ImgRaw&r=0",
  };
};
</script>

<template>
  <el-container>
    <!-- Header -->
    <el-header style="display: flex; justify-content: space-between; align-items: center;">
      <h3>食品安全数据报告详情</h3>
      <el-button type="primary" @click="$router.go(-1)">返回</el-button>
    </el-header>

    <el-main style="display: flex; flex-direction: column; align-items: center;">
      <el-card style="width: 80%; margin-bottom: 20px;">
        <template #header>
          <div  style="display: flex; justify-content: space-between;">
            <span style="font-size: 16px; font-weight: bold;">数据报告展示</span>
            <el-button-container>
              <el-button type="info" @click="startEditing" v-show="!isEditing">编辑</el-button>
              <el-button type="info" @click="cancelEditing" v-show="isEditing">取消编辑</el-button>
              <el-button type="success" @click="submitForm" v-show="isEditing">保存</el-button>
              <el-button type="danger" @click="deleteInfo">删除</el-button>
            </el-button-container>
          </div>
        </template>
        <el-container v-if="!isEditing" style="display: flex; justify-content: center;">
          <div v-if="reportDetail.photo_url" style="margin-top: 10px;">
            <img :src="reportDetail.photo_url" alt="图片" style="width: 200px; height: 200px; margin-right: 10px;" />
          </div>
        </el-container>
        <el-container v-else>
          <el-form ref="formRef" :model="form" :rules="rules" label-width="120px" style="width: 60%; margin: 0 auto;">
            <el-form-item label="报告图片" prop="image">
              <input type="file" @change="handleFileChange" accept="image/*" class="file-input" />
            </el-form-item>
          </el-form>
        </el-container>
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

.input,
.textarea,
.file-input {
  width: 95%;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
</style>