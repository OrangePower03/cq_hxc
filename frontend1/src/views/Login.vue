<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2>Login</h2>
      <el-form :model="loginForm" ref="loginFormRef" :rules="rules">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" placeholder="Username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="loginForm.password" placeholder="Password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin">Login</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: 'Login',
  setup() {
    const router = useRouter();
    const loginFormRef = ref(null);
    const loginForm = reactive({
      username: '',
      password: ''
    });

    const rules = reactive({
      username: [{ required: true, message: 'Please input username', trigger: 'blur' }],
      password: [{ required: true, message: 'Please input password', trigger: 'blur' }]
    });

    const handleLogin = () => {
      (loginFormRef.value as any).validate((valid: boolean) => {
        if (valid) {
          // 模拟登录成功
          localStorage.setItem('token', 'dummy-token'); // 这里假设用localStorage存储token
          router.push('/products');
        } else {
          console.log('error submit!');
          return false;
        }
      });
    };

    return {
      loginForm,
      loginFormRef,
      rules,
      handleLogin
    };
  }
});
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.login-card {
  width: 400px;
  padding: 20px;
}
</style>
