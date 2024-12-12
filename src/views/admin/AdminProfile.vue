<script setup>
import {reactive, ref} from "vue";
import { UserFilled, SwitchButton } from '@element-plus/icons-vue';
import router from "@/router/index.js";
import {post} from "@/net/index.js"
import {ElMessage} from "element-plus";
import axios from "axios";

    
</script>

<template>
    <el-container style="height: 100vb; border: 1px solid #dcdfe6; width: 100%">
        <!-- Header -->
        <el-header style="display: flex; justify-content: space-between; align-items: center;">
          <h3>管理员个人中心</h3>
          
        </el-header>
  
        
    </el-container>
</template>

<style scoped>

</style>