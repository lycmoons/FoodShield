<script setup>
import { onMounted, reactive, ref } from "vue";
import { useRoute } from "vue-router";
import { ElMessage,ElMessageBox} from "element-plus";
import axios from "axios";
import router from "@/router/index.js";

// 路由参数
const route = useRoute();

// 表单数据
const form = reactive({
  id: null,
  name: '',
  date: '',
  manufacturer: '',
  batch_num: '',
  image: null,
  materialSafety: '',
  productionSafety: '',
  processingSafety: '',
  transportationSafety: '',
});


const foodInfoDetail = reactive({
  foodInfoId: route.query.id,
  name: route.query.name,
  date: route.query.date,
  manufacturer: route.query.manufacturer,
  batch_num: route.query.batch_num,
  photo_url: route.query.photo_url,

  // 安全评级
  materialSafety: route.query.materialSafety,
  productionSafety: route.query.productionSafety,
  processingSafety: route.query.processingSafety,
  transportationSafety: route.query.transportationSafety,
})

// 表单校验规则
const rules = {
  name: [{ required: true, message: '请输入食品名称', trigger: 'blur' }],
  date: [{ required: true, message: '请输入日期', trigger: 'blur' }],
  manufacturer: [{ required: true, message: '请输入制造商', trigger: 'blur' }],
  batch_num: [{ required: true, message: '请输入批次号', trigger: 'blur' }],
  image: [{ required: true, message: '请上传图片', trigger: 'change' }],
};

const formRef = ref(null);
const isEditing = ref(false);


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
      formData.append("image", form.image);
      isEditing.value = false;
      axios.post('/api/food', formData)
        .then(response => {
          
          ElMessage.success('食品信息已提交！');
          onMounted() // 不退出的话就更新页面
          router.go(-1);
        })
        .catch(error => {
          ElMessage.error('提交失败，请重试！');
        });
    } else {
      ElMessage.error('请填写完整的表单信息');
    }
  });
};

// 编辑内容数据
const editDetail = ref({
  name: "",
  date: "",
  manufacturer: "",
  batch_num: "",
  photo_url: ""
});

// 处理图片选择
const handleFileChange = (event) => {
  form.image = event.target.files;
}

// 启动编辑模式 
const startEditing = () => { 
  isEditing.value = true; 
  editDetail.value = { 
    foodInfoId: foodInfoId,
    name: foodInfoDetail.value.name,
    date: foodInfoDetail.value.date,
    manufacturer: foodInfoDetail.value.manufacturer,
    batch_num: foodInfoDetail.value.batch_num,
    photo_url: foodInfoDetail.value.photo_url,
  }; 
  form.id = foodInfoId;
  form.name = editDetail.value.name;
  form.date = editDetail.value.date; 
  form.manufacturer = editDetail.value.manufacturer;
  form.batch_num = editDetail.value.batch_num;
}; 

// 取消编辑
const cancelEditing = () => {
  isEditing.value = false;
  editDetail.value = {
    name: "",
    date: "",
    manufacturer: "",
    batch_num: "",
  };
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
        <div v-if="isEditing">
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
            <el-form-item label="图片" prop="image">
              <input type="file" @change="handleFileChange" accept="image/*" class="file-input" />
            </el-form-item>
          </el-form>
        </div>
        <div v-else>
          <el-form label-width="120px" style="width: 60%; margin: 0 auto;">
          <el-form-item label="ID">
              <el-input v-model="foodInfoDetail.foodInfoId" placeholder="请输入ID" disabled />
            </el-form-item>
            <el-form-item label="食品名称">
              <el-input v-model="foodInfoDetail.name" placeholder="请输入食品名称" :disabled="!isEditing" />
            </el-form-item>
            <el-form-item label="日期">
              <el-date-picker v-model="foodInfoDetail.date" type="date" placeholder="请选择日期" style="width: 100%;" :disabled="!isEditing" />
            </el-form-item>
            <el-form-item label="制造商">
              <el-input v-model="foodInfoDetail.manufacturer" placeholder="请输入制造商" :disabled="!isEditing" />
            </el-form-item>
            <el-form-item label="批次号">
              <el-input v-model="foodInfoDetail.batch_num" placeholder="请输入批次号" :disabled="!isEditing" />
            </el-form-item>
            <el-form-item label="图片">
                <div v-if="foodInfoDetail.photo_url" style="margin-top: 10px;">
                  <img :src="foodInfoDetail.photo_url" alt="图片" style="width: 200px; height: 200px; margin-right: 10px;" />
                </div>
            </el-form-item>
          </el-form>
        </div>
        <el-container class="button">
          <el-button type="primary" @click="submitForm" v-show="!isEditing">提交</el-button>
          <el-button type="info" @click="startEditing" v-show="!isEditing">编辑</el-button>
          <el-button type="success" @click="submitForm" v-show="isEditing">保存</el-button>
          <el-button type="info" @click="cancelEditing" v-show="isEditing">取消编辑</el-button>
          <el-button type="danger" @click="deleteInfo">删除</el-button>
        </el-container>
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

.button{
  display: flex;
  justify-content: center;
  margin-top: 40px;
  gap: 10px;
}
</style>
