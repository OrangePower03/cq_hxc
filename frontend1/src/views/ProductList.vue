<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6" v-for="item in products" :key="item.id">
        <el-card :body-style="{ padding: '0px' }">
          <img :src="item.image" class="image">
          <div style="padding: 14px;">
            <span>{{ item.name }}</span>
            <div class="bottom clearfix">
              <span class="price">{{ item.price }}</span>
              <el-button type="primary" @click="addToCart(item)">加入购物车</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'ProductList',
  setup() {
    const products = ref([
      { id: 1, name: '商品 1', price: '$10', image: 'path/to/image1.jpg' },
      { id: 2, name: '商品 2', price: '$20', image: 'path/to/image2.jpg' },
      // more products
    ]);

    const addToCart = (item: any) => {
      // Add to cart logic here
    };

    return {
      products,
      addToCart,
    };
  },
});
</script>

<style scoped>
.image {
  width: 100%;
}
.price {
  color: #f56c6c;
}
</style>
