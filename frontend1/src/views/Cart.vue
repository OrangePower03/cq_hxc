<template>
  <div>
    <el-table :data="cartItems">
      <el-table-column prop="name" label="Product Name"></el-table-column>
      <el-table-column prop="price" label="Price"></el-table-column>
      <el-table-column label="Actions">
        <template #default="scope">
          <el-button @click="removeFromCart(scope.row)">Remove</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'Cart',
  setup() {
    const cartItems = ref([
      { name: 'Product 1', price: '$10' },
      { name: 'Product 2', price: '$20' },
      // more cart items
    ]);

    const removeFromCart = (item: any) => {
      // Remove from cart logic here
    };

    return {
      cartItems,
      removeFromCart,
    };
  },
});
</script>
