<script setup>
import { onMounted, reactive, ref } from "vue";
import { useRoute } from "vue-router";
import { ElMessage,ElMessageBox} from "element-plus";
import axios from "axios";

// 路由参数
const route = useRoute();
const foodInfoId = ref(route.query.id || "未知");

// 表单数据
const form = reactive({
  id: "",
  name: "",
  date: "",
  manufacturer: "",
  batch_num: "",
  photo_url: "",
});

// 表单校验规则
const rules = {
  name: [{ required: true, message: '请输入食品名称', trigger: 'blur' }],
  date: [{ required: true, message: '请输入日期', trigger: 'blur' }],
  manufacturer: [{ required: true, message: '请输入制造商', trigger: 'blur' }],
  batch_num: [{ required: true, message: '请输入批次号', trigger: 'blur' }],
  photo_url: [{ required: true, message: '请上传图片', trigger: 'change' }],
};

const formRef = ref(null);
const isEditing = ref(false);

// 模拟后端获取的原有数据
onMounted(() => {
  form.id = foodInfoId;
  form.name = "苹果";
  form.date = "2023-12-01";
  form.manufacturer = "苹果公司";
  form.batch_num = "001";
  form.photo_url = "https://example.com/image1.jpg";
});

// 提交表单
const submitForm = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      const formData = new FormData();
      formData.append("id", foodInfoId);
      formData.append("name", form.name);
      formData.append("date", form.date);
      formData.append("manufacturer", form.manufacturer);
      formData.append("batch_num", form.batch_num);
      formData.append("photo_url", form.photo_url);
      
      axios.post('/api/food', formData)
        .then(response => {
          ElMessage.success('食品信息已提交！');
          router.push('/'); 
        })
        .catch(error => {
          ElMessage.error('提交失败，请重试！');
        });
    } else {
      ElMessage.error('请填写完整的表单信息');
    }
  });
};

// 处理图片上传
const handleUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      form.photo_url = e.target.result;
    };
    reader.readAsDataURL(file);
  }
};

// 保存编辑
const saveEdit = () => {
  isEditing.value = false;
  ElMessage.success("信息已保存！");
};

// 取消编辑
const cancelEdit = () => {
  isEditing.value = false;
  onMounted(); // 重置表单为原有数据
};

// 发布信息
const publishInfo = () => {
  ElMessage.success("信息已发布！");
};

// 删除信息 
const deleteInfo = () => { 
  ElMessageBox.confirm('确认删除这条信息吗？', '提示', { 
    confirmButtonText: '确定', 
    cancelButtonText: '取消', 
    type: 'warning', 
  }).then(() => { 
    // 模拟删除 
    console.log("信息已删除:", form.id); 
    ElMessage.success("信息已删除！"); 
    router.push('/'); 
    // 删除后返回上一页 
  }).catch(() => { 
    ElMessage.info("删除已取消");
  }); 
};
</script>

<template>
  <el-container style="height: 100%; width: 100%">
    <el-header style="display: flex; justify-content: space-between; align-items: center;">
      <h3>食品信息详情</h3>
      <el-button type="primary" @click="$router.go(-1)">返回</el-button>
    </el-header>

    <el-main>
      <el-card style="width: 80%; margin-bottom: 20px;">
        <el-form ref="formRef" :model="form" :rules="rules" label-width="120px" style="width: 60%; margin: 0 auto;">
          <el-form-item label="ID" prop="id">
            <el-input v-model="form.id" placeholder="请输入ID" disabled />
          </el-form-item>
          <el-form-item label="食品名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入食品名称" :disabled="!isEditing" />
          </el-form-item>
          <el-form-item label="日期" prop="date">
            <el-date-picker v-model="form.date" type="date" placeholder="请选择日期" style="width: 100%;" :disabled="!isEditing" />
          </el-form-item>
          <el-form-item label="制造商" prop="manufacturer">
            <el-input v-model="form.manufacturer" placeholder="请输入制造商" :disabled="!isEditing" />
          </el-form-item>
          <el-form-item label="批次号" prop="batch_num">
            <el-input v-model="form.batch_num" placeholder="请输入批次号" :disabled="!isEditing" />
          </el-form-item>
          <el-form-item label="图片上传" prop="photo_url">
            <input type="file" @change="handleUpload" v-show="isEditing"/>
            <div v-if="form.photo_url" style="margin-top: 10px;">
              <img :src="form.photo_url" alt="图片" style="width: 200px; height: 200px; margin-right: 10px;" />
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm" v-show="!isEditing">提交</el-button>
            <el-button type="info" @click="isEditing = true" v-show="!isEditing">编辑</el-button>
            <el-button type="success" @click="saveEdit" v-show="isEditing">保存</el-button>
            <el-button type="info" @click="cancelEdit" v-show="isEditing">取消编辑</el-button>
            <el-button type="danger" @click="deleteInfo">删除</el-button>
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
</style>
