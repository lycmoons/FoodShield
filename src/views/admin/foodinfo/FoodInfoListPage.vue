<script setup>
import {onMounted, ref} from "vue";
// import { UserFilled, Document, Message, PieChart, SwitchButton } from '@element-plus/icons-vue';
import router from "@/router/index.js";
import {post} from "@/net/index.js"
import {ElMessageBox, ElMessage} from "element-plus";
import axios from "axios";
    
const foodInfos = ref([]);

const selectedFoodInfos = ref([]);
const pageSize = ref(20); // 每页显示条数
const currentPage = ref(1); // 当前页码
const totalLogs = ref(0); // 数据总条数
const loading = ref(false); // 加载状态

// 模拟的总数据，写连接的时候删掉
const allData = ref([]);
const generateData = () => {
  const data = [];
  for (let i = 1; i <= 50; i++) {
    data.push({
      id: `F${i}`,
      name: `水果${(i % 5) + 1}`,
      date: `2024-12-${(i % 30) + 1} 12:00`,
    });
  }
  return data;
};

// 初始化数据
onMounted(() => {
  allData.value = generateData(); // 生成 50 条数据
  totalLogs.value = allData.value.length; // 设置总条数
  updatePageData(); // 更新当前页数据
});

// 更新当前页数据
const updatePageData = () => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  foodInfos.value = allData.value.slice(start, end);
};

// 跳转到食品信息详细页面并传递参数
const goToFoodInfoDetail = (foodInfo) => {
  router.push({
    path: "foodinfodetail",
    query: {
      id: foodInfo.id,
    },
  });
};

// 监听分页变化
const handleSizeChange = (size) => {
  pageSize.value = size; // 更新每页条数
  currentPage.value = 1; // 重置到第1页
  updatePageData();
};

const handleCurrentChange = (page) => {
  currentPage.value = page; // 更新当前页码
  updatePageData();
};

// 删除选中食品信息 
const deleteSelectedFoodInfos = () => { 
  ElMessageBox.confirm("确认删除选中的食品信息吗？", "提示", { 
    confirmButtonText: "确定", 
    cancelButtonText: "取消", 
    type: "danger", 
  }).then(() => { 
    foodInfos.value = foodInfos.value.filter(foodInfo => !selectedFoodInfos.value.includes(foodInfo.id)); 
    selectedFoodInfos.value = []; 
    totalLogs.value = foodInfos.value.length; 
    ElMessage.success("选中的食品信息已删除！"); 
  }).catch(() => { 
    ElMessage.info("删除已取消"); 
  }); 
};
</script>
    
<template>
  <el-container style="height: 100%; width: 100%">
    <!-- Header -->
      <el-header style="display: flex; justify-content: space-between; align-items: center;">
        <h3>食品信息列表</h3>
      </el-header>
  
    <el-main>
      <el-container class="deleteButton-container">
        <el-button type="danger" :disabled="!selectedFoodInfos.length" @click="deleteSelectedFoodInfos">删除选中食品信息</el-button>
      </el-container>
        <el-table v-loading="loading" :data="foodInfos" border style="width: 100%; height: 680px;" @selection-change="selectedFoodInfos = $event.map(item => item.id)">
          <el-table-column type="selection" width="55" sortable show-overflow-tooltip />
          <el-table-column prop="id" label="食品ID" width="100" sortable show-overflow-tooltip />
          <el-table-column prop="name" label="食品名称" sortable show-overflow-tooltip />
          <el-table-column prop="date" label="食品进货时间" sortable width="180" show-overflow-tooltip />
          <el-table-column label="操作" width="150">
            <template #default="scope">
              <el-button type="primary" size="small" @click="goToFoodInfoDetail(scope.row)">查看详情</el-button>
            </template>
          </el-table-column>
        </el-table>

      <div class="pagination-container">
        <el-pagination background layout="sizes, prev, pager, next, total" 
                      :total="totalLogs" :page-sizes="[20, 30, 50]" 
                      :page-size="pageSize" :current-page="currentPage"
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"
                      style="margin-top: 15px; text-align: right;" />
      </div>
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

.deleteButton-container{
  display: flex;
  justify-content: flex-end;
  margin-bottom: 5px;
}

.pagination-container {
  display: flex;
  justify-content:flex-end;
  padding-top: 10px;
}
</style>