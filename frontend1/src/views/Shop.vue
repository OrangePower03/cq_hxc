<template>
  <div>
    <el-button type="primary" @click="openShop">免费开店</el-button>
    <el-button type="success" @click="viewShop">查看店铺</el-button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'Shop',
  setup() {
    const openShop = () => {
      // Logic to open a shop
    };

    const viewShop = () => {
      // Logic to view shop
    };

    return {
      openShop,
      viewShop,
    };
  },
});
</script>
