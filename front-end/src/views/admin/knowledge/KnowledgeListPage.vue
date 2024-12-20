<script setup>
import { onMounted, ref, watch } from "vue";
import router from "@/router/index.js";
import {post} from "@/net/index.js"
import {ElMessageBox, ElMessage} from "element-plus";
import axios from "axios";
    
const knowledges = ref([]);
const filteredKnowledges = ref([]);
const selectedKnowledges = ref([]);
const pageSize = ref(20); // 每页显示条数
const currentPage = ref(1); // 当前页码
const totalLogs = ref(0); // 数据总条数
const loading = ref(false); // 加载状态
const selectedCategory = ref(""); // 选中的类别
const categories = ref(["分类1", "分类2", "分类3"]); // TODO: 看要前端写死还是后端获取

// 模拟的总数据，写连接的时候删掉
const allData = ref([]);
const generateData = () => {
  const data = [];
  for (let i = 1; i <= 50; i++) {
    data.push({
      id: `F${i}`,
      title: `食品安全知识${i + 1}`,
      category: `商超`,
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
  knowledges.value = allData.value.slice(start, end);
};

// 跳转到食品安全知识库文章详细页面并传递参数
const goToKnowledgeDetail = (knowledge) => {
  router.push({
    path: "/admin/knowledgemanage/knowledgedetail",
    query: {
      id: knowledge.id,
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

// 删除选中知识库文章 
const deleteSelectedKnowledges = () => { 
  ElMessageBox.confirm("确认删除选中的知识库文章吗？", "提示", { 
    confirmButtonText: "确定", 
    cancelButtonText: "取消", 
    type: "warning", 
  }).then(() => { 
    knowledges.value = knowledges.value.filter(knowledge => !selectedKnowledges.value.includes(knowledge.id)); 
    selectedKnowledges.value = []; 
    totalLogs.value = knowledges.value.length; 
    ElMessage.success("选中的知识库文章已删除！"); 
  }).catch(() => { 
    ElMessage.info("删除已取消"); 
  }); 
};

// TODO: 类别筛选 
watch(selectedCategory, (newCategory) => { 
  
});

</script>
    
<template>
  <el-container style="height: 100%; width: 100%">
    <!-- Header -->
    <el-header style="display: flex; justify-content: space-between; align-items: center;">
      <h3>食品安全知识库列表</h3>   
    </el-header>

    <!-- Main Content -->
    <el-main>
      <el-container class="operate-container">
        <!-- TODO: 按分类搜索 -->
        <el-select v-model="selectedCategory" placeholder="选择类别" style="width: 200px; margin-right: 30px;"> 
          <el-option v-for="category in categories" :key="category" :label="category" :value="category" />
        </el-select>
        <el-button type="danger" :disabled="!selectedKnowledges.length" @click="deleteSelectedKnowledges">删除选中知识库文章</el-button>
      </el-container>
      <el-table v-loading="loading" :data="knowledges" border style="width: 100%; height: 700px;" @selection-change="selectedKnowledges = $event.map(item => item.id)">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="文章ID" sortable width="100" show-overflow-tooltip />
        <el-table-column prop="title" label="文章标题" sortable show-overflow-tooltip />
        <el-table-column prop="category" label="文章类别" sortable width="180" show-overflow-tooltip />
        <el-table-column prop="date" label="文章发表时间" sortable width="180" show-overflow-tooltip />
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button type="primary" size="small" @click="goToKnowledgeDetail(scope.row)">查看详情</el-button>
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

.operate-container{
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