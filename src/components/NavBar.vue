<template>
  <nav class="navbar">
    <router-link to="/" class="nav-link" exact>首页</router-link>
    <router-link to="/about" class="nav-link">天气查询</router-link>
    <!-- <router-link 
      :to="{ name: 'user', params: { id: 123 } }" 
      class="nav-link"
    >
      用户中心
    </router-link> -->
    <router-link :to="{ name: 'user', params: { id: 123 } }" class="nav-link">
      AI助手
    </router-link>
    <!-- 程序式导航示例 -->
    <!-- <button @click="goToAbout" class="nav-button">关于页面</button> -->
    <span @click="goBack" class="nav-button">返回</span>
  </nav>
</template>

<script>
export default {
  methods: {
    goToAbout() {
      this.$router.push('/about')
      // 或者使用命名路由
      // this.$router.push({ name: 'about' })
    },
    goBack() {
      this.$router.go(-1)
    }
  }
}
</script>

<style scoped>
.navbar {
  padding: 1rem;
  background: #f5f5f5;
  margin-bottom: 2rem;
}

.nav-link {
  margin-right: 1rem;
  text-decoration: none;
  color: #2c3e50;
}

.nav-link.router-link-exact-active {
  color: #42b983;
  font-weight: bold;
}

.nav-button {
  margin-left: 1rem;
  padding: 0.25rem 0.5rem;
  cursor: pointer;
}
</style>
