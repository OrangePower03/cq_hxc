<template>
  <el-container>
    <el-header>
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
        <el-menu-item index="2" @click="goTo('/products')">Product List</el-menu-item>
        <el-menu-item index="3" @click="goTo('/shop')">Shop</el-menu-item>
        <el-menu-item index="4" @click="goTo('/profile')">Profile</el-menu-item>
        <el-menu-item index="5" @click="goTo('/cart')">Cart</el-menu-item>
      </el-menu>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'MainLayout',
  setup() {
    const activeIndex = ref('1');
    const router = useRouter();

    const handleSelect = (key: string, keyPath: string[]) => {
      console.log(key, keyPath);
    };

    const goTo = (path: string) => {
      router.push(path);
    };

    return {
      activeIndex,
      handleSelect,
      goTo,
    };
  },
});
</script>

<style>
.el-menu-demo {
  background-color: #545c64;
  color: #fff;
}

.el-menu-demo .el-menu-item {
  color: #fff;
}
</style>
