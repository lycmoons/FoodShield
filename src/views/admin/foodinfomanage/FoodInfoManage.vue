<script setup>
import {reactive, ref} from "vue";
// import { UserFilled, Document, Message, PieChart, SwitchButton } from '@element-plus/icons-vue';
import router from "@/router/index.js";
import {post} from "@/net/index.js"
import {ElMessage} from "element-plus";
import axios from "axios";
    
</script>
    
<template>
    <el-container style="height: 100%; width: 100%">
        <!-- Header -->
        <el-header style="display: flex; justify-content: space-between; align-items: center;">
          <h3>食品信息管理</h3>
          
        </el-header>

    </el-container>
</template>

<style scoped>

</style>