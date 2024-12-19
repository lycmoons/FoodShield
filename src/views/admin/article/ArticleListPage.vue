<script setup>
import {onMounted, ref} from "vue";
import router from "@/router/index.js";
import {post} from "@/net/index.js"
import {ElMessageBox, ElMessage} from "element-plus";
import axios from "axios";
    
const articles = ref([]);
const selectedArticles = ref([]);
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
      id: `A${i}`,
      title: `食品安全推文${i + 1}`,
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
  articles.value = allData.value.slice(start, end);
};

// 跳转到食品安全推文详细页面并传递参数
const goToArticleDetail = (articles) => {
  router.push({
    path: "articledetail",
    query: {
      id: articles.id,
    },
  });
};

// 监听分页变化
const handleSizeChange = (size) => {
  pageSize.value = size;
  currentPage.value = 1;
  updatePageData();
};

const handleCurrentChange = (page) => {
  currentPage.value = page;
  updatePageData();
};

// 删除选中推文 
const deleteSelectedArticles = () => { 
  ElMessageBox.confirm("确认删除选中的推文吗？", "提示", { 
    confirmButtonText: "确定", 
    cancelButtonText: "取消", 
    type: "danger", 
  }).then(() => { 
    articles.value = articles.value.filter(article => !selectedArticles.value.includes(article.id)); 
    selectedArticles.value = []; 
    totalLogs.value = articles.value.length; 
    ElMessage.success("选中的推文已删除！"); 
  }).catch(() => { 
    ElMessage.info("删除已取消"); 
  }); 
};
</script>
    
<template>
  <el-container style="height: 100%; width: 100%">
    <!-- Header -->
    <el-header style="display: flex; justify-content: space-between; align-items: center;">
      <h3>食品安全推文列表</h3>   
    </el-header>

    <!-- Main Content -->
    <el-main>
      <el-container class="deleteButton-container">
        <el-button type="danger" :disabled="!selectedArticles.length" @click="deleteSelectedArticles">删除选中推文</el-button>
      </el-container>
      <el-table v-loading="loading" :data="articles" border style="width: 100%; height: 700px;" @selection-change="selectedArticles = $event.map(item => item.id)">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="推文ID" width="100" sortable show-overflow-tooltip />
        <el-table-column prop="title" label="推文标题" sortable show-overflow-tooltip />
        <el-table-column prop="date" label="推文发表时间" sortable width="180" show-overflow-tooltip />
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button type="primary" size="small" @click="goToArticleDetail(scope.row)">查看详情</el-button>
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