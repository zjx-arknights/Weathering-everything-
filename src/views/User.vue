<template>
    <div class="user">
      <!-- <p>用户ID: {{ id }}</p>
      <p>通过路由参数获取: {{ $route.params.id }}</p> -->
      <div class="head">
       <div class="text">智能AI天气服务助手</div>
       <!-- <button class="botton" @click="goToAbout">天气查询</button> -->
      </div>
      <deepseek />
      
    </div>
  </template>
  
  <script>
import deepseek from '../components/deepseek.vue';

  export default {
    name: 'User',
    components:{
      deepseek,
    },
    props: {
      id: {
        type: String,
        required: true
      }
    },
    methods: {
      goToAbout() {
        this.$router.push('/about')
      }
    }
  }
  </script>

  <style scoped>
  .head{
    display: flex;
    justify-content: center;
    text-align: center;
  }
.botton{
width: 100px;
height: 30px;
border-radius: 12px;

}

.text{
  color: rgba(1, 122, 254);
  font-weight: 700;
  font-size: 32px;
}
  </style>
  