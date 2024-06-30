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
                  <span id="nickName"></span>
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
                  <li class="nav-text"><a href="../../../index.html">首页</a></li>
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
            <ul class="flow-default current-list" id="currentCommodity"> </ul>
          </div>
        </div>
      </div>
    </el-main>
  </div>
</template>

<script>
export default {
  name: 'Seckill',
};
</script>

<style scoped>
/* 添加你的样式 */
</style>
