<script setup>

import {onMounted, reactive} from "vue";
import {get} from "@/net/index.js";

function getReportPhoto(){
  get('/api/report/get-report', (data) => {
    form.photo_url = data.photo_url
  })
}

onMounted(() => {
  getReportPhoto()
})

let form = reactive({
  photo_url: ''
})

</script>

<template>
  <el-image class="image" :src="form.photo_url"/>
</template>

<style scoped>
.image{
  width: 100%;
  height: 100%;
  object-fit: fill;
}
</style>