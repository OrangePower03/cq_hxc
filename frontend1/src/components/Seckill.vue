<template>
  <div class="seckill-body">
    <el-header>
      <div class="seckill-top">
        <div class="seckill-welcome">
          <div class="welcome-content">
            <div class="seckill-text">欢迎来到叩丁严选</div>
            <ul class="seckill-ul">
              <li class="seckill-li userInfo">
                <a href="#" class="user-name">
                  <label>用户名: </label>
                  <span>{{ userName }}</span>
                </a>
              </li>
              <li class="seckill-li userInfo">
                <a href="#" target="_blank" class="user-name">
                  <label>我的订单</label>
                </a>
              </li>
              <li class="seckill-li loginBtn">
                <a href="login.html" class="shopping-content user-change-login">登录</a>
              </li>
            </ul>
          </div>
          <div class="seckill-nav">
            <div class="nav-content">
              <div class="nav-logo"> </div>
              <div class="nav-select">
                <ul class="nav-list">
                  <li class="nav-text"><a href="index.html">首页</a></li>
                  <li class="nav-text"><a href="#">全部商品</a></li>
                  <li class="nav-text"><a href="#">个人中心</a></li>
                  <li class="nav-text"><a href="#">我的订单</a></li>
                  <li class="nav-text"><a href="#">专属福利</a></li>
                </ul>
              </div>
              <div class="nav-search">
                <div class="input-content">
                  <el-input placeholder="输入关键字" class="search-input"></el-input>
                </div>
                <div class="search-img"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-header>
    <el-main>
      <div class="seckill-content">
        <div class="seckill-classify">
          <div class="seckill-list">
            <div class="seckill-menu">
              <span class="seckill-menu-text seckill-menu-active">叩丁秒杀</span>
              <span class="seckill-menu-text">每日特价</span>
              <span class="seckill-menu-text">大牌闪购</span>
              <span class="seckill-menu-text">品类秒杀</span>
            </div>
          </div>
        </div>
        <div class="seckill-container">
          <div class="seckill-bg"></div>
          <div class="seckill-tab" id="seckillTab"></div>
          <div class="seckill-commodity">
            <ul class="flow-default current-list" id="currentCommodity">
              <li v-for="product in products" :key="product.id" class="commodity-data">
                <div class="commodity-data-flex">
                  <div class="commodityImg">
                    <img :src="product.productImg" alt="" class="moreGood-img" />
                  </div>
                  <div class="textParent">
                    <p class="commodity-text">{{ product.productName }}</p>
                    <div class="seckill-tag">
                      <p class="seckill-tag-text">{{ product.productDetail }}</p>
                    </div>
                  </div>
                  <div class="seckill-operation">
                    <div class="seckill-good-cur">
                      <div class="seckill-money-num">
                        <div class="seckill-money-status">
                          <h2 class="seckill-number">
                            <span class="seckill-num">￥</span>{{ product.seckillPrice }}
                          </h2>
                          <span class="old-number">￥<del>{{ product.productPrice }}</del></span>
                        </div>
                        <div class="seckill-shop-number">
                          <div class="seckill-start-status">
                            <span class="comeback-num">已售{{ (product.stockCount - product.currentCount) * 100 / product.stockCount }}%</span>
                            <div class="shop-number-status">
                              <i class="current-progress" :style="{ width: (product.stockCount - product.currentCount) * 100 / product.stockCount + '%' }"></i>
                            </div>
                          </div>
                        </div>
                      </div>
                      <a :href="'commodityDetails.html?id=' + product.time + '&seckillId=' + product.id" target="_blank" class="operation-button">立即抢购</a>
                    </div>
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </el-main>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import "../assets/css/index.css";
import "../assets/layui/css/layui.css";
import "../assets/css/header.css";

interface Product {
  id: number;
  productImg: string;
  productName: string;
  productDetail: string;
  seckillPrice: number;
  productPrice: number;
  stockCount: number;
  currentCount: number;
  time: string;
}

export default defineComponent({
  name: 'Seckill',
  setup() {
    const userName = ref<string>(''); // 用户名
    const products = ref<Product[]>([]); // 商品列表

    const fetchProducts = async () => {
      // 模拟 AJAX 请求获取商品数据
      products.value = [
        {
          id: 1,
          productImg: 'image1.jpg',
          productName: '商品1',
          productDetail: '详细信息1',
          seckillPrice: 100,
          productPrice: 200,
          stockCount: 100,
          currentCount: 50,
          time: '10:00'
        },
        // 其他商品
      ];
    };

    onMounted(() => {
      fetchProducts();
    });

    return {
      userName,
      products,
    };
  },
});
</script>

<style scoped>
/* 添加你的样式 */
</style>
