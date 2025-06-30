<template>
    <div class="not-found">
      <h1>404 - 页面不存在</h1>
      <p>您访问的 {{ $route.path }} 不存在</p>
      <router-link to="/">返回首页</router-link>
    </div>
  </template>
  
  <script>
  export default {
    name: 'NotFound'
  }
  </script>
  
  <style scoped>
  .not-found {
    text-align: center;
    padding: 2rem;
  }
  </style>
  