<script setup>
import { onMounted, reactive, ref } from "vue";
import { useRoute } from "vue-router";
import { ElMessage,ElMessageBox} from "element-plus";
import axios from "axios";
import router from "@/router/index.js";
import {postWithToken} from "@/net/index.js";

// 路由参数
const route = useRoute();

const foodInfoDetail = reactive({
  id: route.query.id,
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

// 删除信息 
const deleteInfo = () => { 
  ElMessageBox.confirm('确认删除这条信息吗？', '提示', { 
    confirmButtonText: '确定', 
    cancelButtonText: '取消', 
    type: 'warning', 
  }).then(() => {
    postWithToken('/api/food/delete-food', {
      foodIds: [ foodInfoDetail.id ]
    }, ()=>{
      ElMessage.success('食品信息删除成功')
      router.push('/admin/foodinfoManage/foodinfoList')
    })
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
        <div>
          <el-form label-width="120px" style="width: 60%; margin: 0 auto;">
            <el-form-item label="ID">
              <el-input v-model="foodInfoDetail.id" placeholder="请输入ID" disabled />
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
            <el-form-item label="原料安全评级">
              <el-input v-model="foodInfoDetail.materialSafety" placeholder="请输入原料安全评级" :disabled="!isEditing" />
            </el-form-item>
            <el-form-item label="生产安全评级">
              <el-input v-model="foodInfoDetail.productionSafety" placeholder="请输入生产安全评级" :disabled="!isEditing" />
            </el-form-item>
            <el-form-item label="加工安全评级">
              <el-input v-model="foodInfoDetail.processingSafety" placeholder="请输入加工安全评级" :disabled="!isEditing" />
            </el-form-item>
            <el-form-item label="运输安全评级">
              <el-input v-model="foodInfoDetail.transportationSafety" placeholder="请输入运输安全评级" :disabled="!isEditing" />
            </el-form-item>
          </el-form>
        </div>
        <el-container class="button">
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
