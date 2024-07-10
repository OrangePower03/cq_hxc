<template>
  <div>
    <el-form :model="user" label-width="80px">
      <el-form-item label="Username">
        <el-input v-model="user.username"></el-input>
      </el-form-item>
      <el-form-item label="Email">
        <el-input v-model="user.email"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive } from 'vue';

export default defineComponent({
  name: 'UserProfile',
  setup() {
    const user = reactive({
      username: 'John Doe',
      email: 'john@example.com',
    });

    return {
      user,
    };
  },
});
</script>
