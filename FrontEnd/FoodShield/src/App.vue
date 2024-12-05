<template>
    <router-link to="/login"></router-link>
    <router-view />
</template>

<script>
import { computed } from 'vue';
import { useRoute } from 'vue-router';

import Login from './components/Login/Login.vue';

export default{
  components: {
    Login,
  },

  setup() {
    const route = useRoute();
    
    const isAdminRoute = computed(() => route.path.startsWith('/admin'));

    return {
      isAdminRoute
    };
  }
}
</script>

<style>

</style>

