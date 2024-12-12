<script setup>
import {reactive, ref} from "vue";
import { UserFilled, List, Message, PieChart, SwitchButton, Notebook, Document } from '@element-plus/icons-vue';
import router from "@/router/index.js";
import {post} from "@/net/index.js"
import {ElMessage} from "element-plus";
import axios from "axios";

const faviconSrc = "./src/assets/images/foodshield.png";
    
</script>

<template>
      <el-container style="height: 100vb; border: 1px solid #dcdfe6; width: 100%">
        <!-- Header -->
        <el-header style="display: flex; justify-content: space-between; align-items: center; height: 8vb;">
          <el-container style="align-items: center;">
            <el-image style="width: 70px; height: 70px;" :src="faviconSrc" fit="cover" alt="图片加载失败" @load="handleImageLoad" @error="handleImageError">
              <template #placeholder>
                <el-spin />
              </template>
            </el-image>
            <h1 style="font-size: xx-large; font-family: KaiTi;">同济食品安全管理平台</h1>
          </el-container>
          
          <div>
            <el-button @click="router.push('/profile')" type="info" plain><el-icon :size="17"><UserFilled /></el-icon>管理员111</el-button> 
            <el-button type="warning" plain><el-icon :size="17"><SwitchButton /></el-icon>退出登录</el-button>
          </div>
        </el-header>
  
        <el-container>
          <!-- Sidebar -->
          <el-aside width="200px" style="background-color: cadetblue;">
            <el-menu default-active="1" class="el-menu-vertical" style="background-color: bisque">
              <el-sub-menu index="1">
                <template #title>
                  <el-icon><List /></el-icon>
                  <span>食品信息管理</span>
                </template>
                <el-menu-item index="1-1" @click="router.push('/foodinfomanage')">食品信息列表</el-menu-item>
                <el-menu-item index="1-2" @click="router.push('/foodinfomanage/inputfoodinfo')">食品信息录入</el-menu-item>
              </el-sub-menu>
  
              <!-- Complaint Handling -->
              <el-sub-menu index="2">
                <template #title>
                  <el-icon><Message /></el-icon>
                  <span>投诉处理</span>
                </template>
                <el-menu-item index="2-1" @click="router.push('/complaintinfo')">投诉列表</el-menu-item>
                <el-menu-item index="2-2" @click="router.push('/complaintinfo/complaintacceptance')">处理投诉</el-menu-item>
              </el-sub-menu>
  
              <!-- Food Safety Reports -->
              <el-sub-menu index="3">
                <template #title>
                  <el-icon><PieChart /></el-icon>
                  <span>食品安全数据报告</span>
                </template>
                <el-menu-item index="3-1" @click="router.push('/foodsafetyreport')">报告列表</el-menu-item>
                <el-menu-item index="3-2" @click="router.push('/foodsafetyreport/createfoodsafetyreport')">生成报告</el-menu-item>
                <el-menu-item index="3-3" @click="router.push('/foodsafetyreport/launchfoodsafetyreport')">发布报告</el-menu-item>
              </el-sub-menu>

              <!-- Food Safety Article -->
              <el-sub-menu index="4">
                <template #title>
                  <el-icon><Notebook /></el-icon>
                  <span>食品安全推文</span>
                </template>
                <el-menu-item index="4-1" @click="router.push('/foodsafetyarticle')">推文列表</el-menu-item>
                <el-menu-item index="4-2" @click="router.push('/foodsafetyarticle/createfoodsafetyarticle')">推文撰写</el-menu-item>
              </el-sub-menu>

              <!-- log -->
              <el-sub-menu index="5">
                <template #title>
                  <el-icon><Document /></el-icon>
                  <span>修改日志</span>
                </template>
                <el-menu-item index="5-1" @click="router.push('/adminlog')">查看修改日志</el-menu-item>
              </el-sub-menu>
            </el-menu>
          </el-aside>
  
          <!-- Main Content -->
          <el-main>
            <router-view></router-view>
          </el-main>
        </el-container>
      </el-container>
</template>

<style scoped>
.el-header {
  background-color:aqua;
  width: 160vb;
}

.el-menu-item{
  background-color: beige;
}

h3 {
    margin: 0;
}
</style>