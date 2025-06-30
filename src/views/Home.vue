<template>
    <div class="home">
      <Login />
    </div>
  </template>
  
  <script>
import Login from '@/components/login.vue';

  export default {
    name: 'Home',
    components:{
      Login
    },
    data() {
      return {
        userId: ''
      }
    },
    methods: {
      goToUser() {
        if (this.userId) {
          this.$router.push({ name: 'user', params: { id: this.userId } })
        }
      }
    }
  }
  </script>
  
  <style scoped>
  .home {
    height: 80vh;
  /* 弹性布局 居中显示 */
  display: flex;
  justify-content: center;
  align-items: center;
    padding: 2rem;
  }
  </style>