<script setup>

import {computed, onMounted, reactive} from "vue";
import {getWithToken, postWithToken} from "@/net/index.js";
import {ElMessage} from "element-plus";

function getAllFood(){
  getWithToken('/api/food/all-food',(data) => {
    for (const item of data){
      form.food.push({
        id: item.id,
        name: item.name,
        date: (new Date(item.date)).toLocaleString(),
        manufacturer: item.manufacturer,
        batch_num: item.batch_num,
        photo_url: item.photo_url,
        materialSafety: item.materialSafety,
        productionSafety: item.productionSafety,
        processingSafety: item.processingSafety,
        transportationSafety: item.transportationSafety,
        subscribed: item.subscribed
      })
    }
  })
}

// 订阅
function subScript(id) {
  postWithToken('/api/subscription/subscript', {
    food_id: id
  }, () => {
    form.food = []
    getAllFood()
    ElMessage.success('订阅成功')
  })
}

// 取消订阅
function undoSubscript(id) {
  postWithToken('/api/subscription/undo-subscript', {
    food_id: id
  }, () => {
    form.food = []
    getAllFood()
    ElMessage.success('取消订阅成功')
  })
}

onMounted(() => {
  getAllFood()
})

let form = reactive({
  food: [],
  searchQuery: "", // 搜索框的输入内容
  expandedId: null, // 当前展开的食物项ID
})


// 计算属性：根据搜索框内容过滤数据
const filteredFood = computed(() => {
  if (!form.searchQuery) {
    return form.food;
  }
  return form.food.filter((item) =>
      item.name.includes(form.searchQuery)
  );
})

// 切换展开项的方法
const toggleExpand = (id) => {
  form.expandedId = form.expandedId === id ? null : id;
}
</script>

<template>
  <div class="container">
    <!-- 搜索框 -->
    <div class="search-box">
      <el-input
          type="text"
          v-model="form.searchQuery"
          placeholder="搜索食物名称..."
      />
    </div>

    <!-- 食物列表 -->
    <div class="food-list">
      <div
          class="food-item"
          v-for="item in filteredFood"
          :key="item.id"
      >
        <!-- 食物名称，点击展开/折叠 -->
        <div class="food-name" @click="toggleExpand(item.id)">
          {{ item.name }}
        </div>

        <!-- 详细信息（如果展开） -->
        <div
            v-if="form.expandedId === item.id"
            class="food-details"
        >
          <p><strong>生产日期：</strong>{{ item.date || "暂无信息" }}</p>
          <p><strong>生产商：</strong>{{ item.manufacturer }}</p>
          <p><strong>批次号：</strong>{{ item.batch_num }}</p>
          <p><strong>原料安全：</strong>{{ item.materialSafety }}</p>
          <p><strong>生产安全：</strong>{{ item.productionSafety }}</p>
          <p><strong>加工安全：</strong>{{ item.processingSafety }}</p>
          <p><strong>运输安全：</strong>{{ item.transportationSafety }}</p>
          <img
              :src="item.photo_url"
              alt="食品图片"
              class="food-photo"
          />
          <el-button type="success"
                     plain
                     v-if="item.subscribed === 0"
                     @click="subScript(item.id)">
            订阅食品
          </el-button>
          <el-button type="danger"
                     plain
                     v-if="item.subscribed === 1"
                     @click="undoSubscript(item.id)">
            取消订阅
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  gap: 10px;
  padding: 20px;
  max-width: 600px;
  margin: auto;
}

.search-box input {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.food-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.food-item {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.food-name {
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
}

.food-name:hover {
  text-decoration: underline;
}

.food-details {
  margin-top: 10px;
  font-size: 14px;
  color: #666;
}

.food-photo {
  margin-top: 10px;
  width: 100%;
  height: auto;
  border-radius: 5px;
  border: 1px solid #ccc;
}
</style>