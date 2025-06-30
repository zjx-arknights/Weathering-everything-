<template>
    <div class="container">
      <div class="left">
        <h1>login</h1>
        <p>欢迎,这是一款基于和风天气，高德地图及deepseek的智能天气预报系统, 请输入账号和密码进行登录!</p>
      </div>
      <div class="right">
        <svg class="svg1" viewBox="0 0 320 300">
          <defs>
            <linearGradient inkscape:collect="always" id="linearGradient1" x1="13" y1="193.49992" x2="307"
              y2="193.49992" gradientUnits="userSpaceOnUse">
              <stop style="stop-color:#0ff;" offset="0" />
              <stop style="stop-color:#f0f;" offset="1" />
            </linearGradient>
          </defs>
          <path
            d="m 40,120.00016 239.99984,-3.2e-4 c 0,0 24.99263,0.79932 25.00016,35.00016 0.008,34.20084 -25.00016,35 -25.00016,35 h -239.99984 c 0,-0.0205 -25,4.01348 -25,38.5 0,34.48652 25,38.5 25,38.5 h 215 c 0,0 20,-0.99604 20,-25 0,-24.00396 -20,-25 -20,-25 h -190 c 0,0 -20,1.71033 -20,25 0,24.00396 20,25 20,25 h 168.57143"
            ref="svgPath" />
        </svg>
        <svg class="svg2" viewBox="0 0 320 300">
          <defs>
            <linearGradient inkscape:collect="always" id="linearGradient1" x1="13" y1="193.49992" x2="307"
              y2="193.49992" gradientUnits="userSpaceOnUse">
              <stop style="stop-color:#0ff;" offset="0" />
              <stop style="stop-color:#f0f;" offset="1" />
            </linearGradient>
          </defs>
          <path
            d="m 40,120.00016 239.99984,-3.2e-4 c 0,0 24.99263,0.79932 25.00016,35.00016 0.008,34.20084 -25.00016,35 -25.00016,35 h -239.99984 c 0,-0.0205 -25,4.01348 -25,38.5 0,34.48652 25,38.5 25,38.5 h 215 c 0,0 20,-0.99604 20,-25 0,-24.00396 -20,-25 -20,-25 h -190 c 0,0 -20,1.71033 -20,25 0,24.00396 20,25 20,25 h 168.57143"
            ref="svgPath" />
        </svg>
        <div class="form">
          <label for="account">账号</label>
          <input 
            type="text" 
            id="account" 
            autocomplete="off"
            v-model="account">
          <label for="password">密码</label>
          <input 
            type="password" 
            id="password"
            v-model="password">
          <button 
            id="submit"
            @click="login()"
            >登录</button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';

  export default {
    name: 'LoginComponent',
    data() {
      return {
        account:'',
        password:'',
        loading: false,
        error: null,
      }
    },
    methods: {
      async login(){
        this.loading = true;
        this.error = null;
        const account = this.account;
        const password = this.password;
        // this.$router.push('/about')
        try {
          // 调用后端API
          // this.$router.push({ name: 'about', params: { locationId: '111111' } })
          const response = await axios.post(`http://${this.$ipLocation}:8080/login`, {
            userName: account,
            password: password
          });
        if(response.data.data.isLogin == 1)
        this.$router.push({ name: 'about', params: { locationId: response.data.data.token } })
        // this.$router.push('/about')
        else if(response.data.data.isLogin == 0){
        alert("注册成功，请重新登录")
        window.location.reload()
        }else if(response.data.data.isLogin == -1){
          alert("密码错误")
          window.location.reload()
        }
        } catch (err) {
          console.error('失败:', err);
          this.error = err;
        } finally {
          // window.location.reload()
          // this.$router.push({ name: 'user', params: { id: this.account } })
          // this.$router.push('/about')
        }
      }
      
     }
    }
  
  </script>
  
  <style scoped>
* {
  /* 初始化 */
  margin: 0;
  padding: 0;
}

body {
  height: 100vh;
  /* 弹性布局 居中显示 */
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #e2e2e5;
}

.container {
  width: 640px;
  height: 320px;
  display: flex;
  /* 移除 position: fixed 和 left: 20% */
  /* 添加 margin: auto 可以确保在 flex 布局中居中 */
  margin: auto;
}

.left {
  width: 50%;
  height: calc(100% - 40px);
  background-color: #fff;
  position: relative;
  top: 20px;
}

.left h1 {
  color: #222;
  font-size: 50px;
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 4px;
  margin: 55px 40px 40px 40px;
}

.left p {
  color: #999;
  font-size: 14px;
  margin: 40px;
  line-height: 22px;
}

.right {
  width: 50%;
  height: 100%;
  background-color: #474a59;
  color: #f1f1f1;
  position: relative;
  box-shadow: 0 0 40px 16px rgba(0, 0, 0, 0.2);
  /* 添加相对定位 */
  overflow: hidden;
}

.right .svg1 {
  position: absolute;
  width: 320px;
  /* 居中定位 */
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  /* 确保SVG在内容上方但不干扰交互 */
  pointer-events: none;
  z-index: 1;
}

.right .svg2 {
  position: absolute;
  width: 320px;
  /* 居中定位 */
  top: 70%;
  left: 50%;
  transform: translate(-50%, -50%);
  /* 确保SVG在内容上方但不干扰交互 */
  pointer-events: none;
  z-index: 1;
}

.form {
  margin: 40px;
  position: absolute;
  /* 确保表单在SVG上方 */
  z-index: 2;
  width: calc(100% - 80px);
}


.right path {
  /* 取消填充 */
  fill: none;
  /* 定义一条线,引用定义好的线性渐变 */
  stroke: url(#linearGradient1);
  /* 线的厚度 */
  stroke-width: 4;
  /* 设置虚线:虚线长度 间距 */
  stroke-dasharray: 240 1386;
}


.form label {
  color: #c2c2c2;
  display: block;
  font-size: 14px;
  margin-top: 20px;
  margin-bottom: 5px;
}

.form input {
  width: 100%;
  height: 30px;
  line-height: 30px;
  font-size: 20px;
  color: #f2f2f2;
  background-color: transparent;
  border: none;
  outline: none;
  text-indent: 2px;
}

.form button {
  width: 100%;
  height: 30px;
  color: #d0d0d0;
  font-size: 18px;
  background-color: transparent;
  border: none;
  margin-top: 40px;
  cursor: pointer;
  outline: none;
}
</style>

  