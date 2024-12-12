<script setup>

</script>

<template>
<div class="container">
    <div class="box-container">
      <router-view v-slot="{ Component }">
        <transition name="el-fade-in-linear" mode="out-in">
          <component :is="Component"/>
        </transition>
      </router-view>
    </div>
  </div>
</template>

<style scoped>
.container {
    display: flex;
    height: 100vh;
}
</style>