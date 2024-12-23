<script setup>
import { reactive, ref } from "vue";
import { Close } from '@element-plus/icons-vue';
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import axios from "axios";
import router from "@/router/index.js";
import {postWithMultipart} from "@/net/index.js";

// 表单数据
const form = reactive({
  name: "",
  date: "",
  manufacturer: "",
  batch_num: "",
  image: null,
  materialSafety: "",
  productionSafety: "",
  processingSafety: "",
  transportationSafety: "",
});

// 表单校验规则
const rules = {
  name: [{ required: true, message: '请输入食品名称', trigger: 'blur' }],
  date: [{ required: true, message: '请输入日期', trigger: 'blur' }],
  manufacturer: [{ required: true, message: '请输入制造商', trigger: 'blur' }],
  batch_num: [{ required: true, message: '请输入批次号', trigger: 'blur' }],
  image: [{ required: true, message: '请上传图片', trigger: 'change' }],
  materialSafety: [{ required: true, message: '请输入原料安全评级', trigger: 'blur' }],
  productionSafety: [{ required: true, message: '请输入生产安全评级', trigger: 'blur' }],
  processingSafety: [{ required: true, message: '请输入加工安全评级', trigger: 'blur' }],
  transportationSafety: [{ required: true, message: '请输入运输安全评级', trigger: 'blur' }],
};

const formRef = ref();

// 状态选项
const statusOptions = [
  { label: "high-level", value: "high-level" },
  { label: "low-level", value: "low-level" },
];

// 提交表单
const submitForm = () => {

  formRef.value.validate((valid) => {
    if (valid){
      if (form.materialSafety===''||form.productionSafety===''||form.processingSafety===''||form.transportationSafety===''){
        ElMessage.warning('请完善表单内容')
      }
      else {
        const formData = new FormData()
        formData.append('name', form.name)
        formData.append('date', form.date.toLocaleString())
        formData.append('manufacturer', form.manufacturer)
        formData.append('batch_num', form.batch_num)
        formData.append('materialSafety', form.materialSafety)
        formData.append('productionSafety', form.productionSafety)
        formData.append('processingSafety', form.processingSafety)
        formData.append('transportationSafety', form.transportationSafety)
        formData.append('image', form.image)

        postWithMultipart('/api/food/add-food', formData, ()=>{
          ElMessage.success('食品信息录入成功')
          router.push('/admin/foodinfoManage/foodinfoList')
        })
      }
    }
    else {
      ElMessage.warning('请完善表单内容')
    }
  })
};

// 重置表单
const resetForm = () => {
  if (formRef.value) {
    formRef.value.resetFields();
    // 手动清空文件输入框的值
    const fileInput = document.querySelector('.file-input');
    if (fileInput) {
      fileInput.value = '';  // 清空文件输入框
    }
    ElMessage.success("表单已重置");
  } else {
    ElMessage.error("表单未绑定");
  }
};

// 处理图片选择
const handleFileChange = (event) => {
  const file = event.target.files[0]
  if (file) {
    form.image = file
  }
}
</script>

<template>
  <el-container style="height: 100%; width: 100%">
    <!-- Header -->
    <el-header style="display: flex; justify-content: space-between; align-items: center;">
      <h3>食品信息录入</h3>
    </el-header>

    <!-- Main Content -->
    <el-main>
      <el-card style="width: 80%; margin-bottom: 20px;">
        <el-form ref="formRef" :model="form" :rules="rules" label-width="120px" style="width: 60%; margin: 0 auto;">
          <el-form-item label="食品名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入食品名称" />
          </el-form-item>
          <el-form-item label="日期" prop="date">
            <el-date-picker v-model="form.date" type="date" placeholder="请选择日期" style="width: 100%;" />
          </el-form-item>
          <el-form-item label="制造商" prop="manufacturer">
            <el-input v-model="form.manufacturer" placeholder="请输入制造商" />
          </el-form-item>
          <el-form-item label="批次号" prop="batch_num">
            <el-input type="number" v-model="form.batch_num" placeholder="请输入批次号" />
          </el-form-item>
          <el-form-item label="原料安全评级">
            <el-select v-model="form.materialSafety" placeholder="请输入原料安全评级" style="width: 100%;">
              <el-option
                v-for="option in statusOptions" :key="option.value" :label="option.label" :value="option.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="生产安全评级">
            <el-select v-model="form.productionSafety" placeholder="请输入生产安全评级" style="width: 100%;">
              <el-option
                v-for="option in statusOptions" :key="option.value" :label="option.label" :value="option.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="加工安全评级">
            <el-select v-model="form.processingSafety" placeholder="请输入加工安全评级" style="width: 100%;">
              <el-option
                v-for="option in statusOptions" :key="option.value" :label="option.label" :value="option.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="运输安全评级">
            <el-select v-model="form.transportationSafety" placeholder="请输入运输安全评级" style="width: 100%;">
              <el-option
                v-for="option in statusOptions" :key="option.value" :label="option.label" :value="option.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="图片上传" prop="image">
            <input type="file" @change="handleFileChange" accept="image/*" class="file-input" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm">提交</el-button>
            <el-button type="warning" @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
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
