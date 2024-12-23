<script setup>
import {onMounted, reactive, ref} from "vue";
import router from "@/router/index.js";
import {get, post, postWithMultipart} from "@/net/index.js"
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

const cancelEditing = () => {
  isEditing.value = false;
};

// 处理图片选择
const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    form.image = file;
  }
};

// 模拟从后端获取投诉数据
onMounted(() => {
  fetchData()
});

const startEditing = () => { 
  isEditing.value = true
}

const submitForm = () => {
  if (form.image === null){
    ElMessage.warning('请选择食品报告的图片')
  }
  else {
    const formData = new FormData()
    formData.append('image', form.image)

    postWithMultipart('/api/report/update-report', formData, ()=>{
      ElMessage.success('食品报告更新成功')
      cancelEditing()
      fetchData()
      form.image = null

    })
  }
};


const fetchData = () => {

  get('/api/report/get-report',(data)=>{
    reportDetail.value = data
  })
};
</script>

<template>
  <el-container>
    <!-- Header -->
    <el-header style="display: flex; justify-content: space-between; align-items: center;">
      <h3>食品安全数据报告详情</h3>
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
            </el-button-container>
          </div>
        </template>
        <el-container v-if="!isEditing" style="display: flex; justify-content: center;">
          <div v-if="reportDetail.photo_url" style="margin-top: 10px;">
            <img :src="reportDetail.photo_url" alt="图片" style="width: 200px; height: 200px; margin-right: 10px;" />
          </div>
        </el-container>
        <el-container v-else>
          <el-form ref="formRef" :model="form" label-width="120px" style="width: 60%; margin: 0 auto;">
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