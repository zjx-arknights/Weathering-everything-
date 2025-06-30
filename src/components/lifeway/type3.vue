<template>
    <div class="child-item">
      <div class="head">穿衣指数:<div 
          v-for="n in parseInt(level)" 
          :key="n"
          class="level-box"
          :style="{ backgroundColor: getLevelColor(n) }"
        ></div>{{ level }}
      </div>
      <div class="svg">
        <svg width="50px" height="50px" viewBox="0 0 24 24" id="Layer_1" data-name="Layer 1" xmlns="http://www.w3.org/2000/svg">
      <path class="cls-1" d="M20.76,18.68H3.24A1.74,1.74,0,0,1,1.5,16.94h0a1.76,1.76,0,0,1,1-1.59L12,11.05l9.48,4.3a1.76,1.76,0,0,1,1,1.59h0A1.74,1.74,0,0,1,20.76,18.68Z"/>
      <path class="cls-1" d="M12,11.05v-.26a2.53,2.53,0,0,1,1.13-2,1.91,1.91,0,1,0-3-1.54"/>
    </svg>
      </div>
      <div class="message" :class="messageColorClass">{{ message }}</div>
  
      <p>{{ text }}</p>
    </div>
  </template>
  
  <script>
  export default {
    name: 'ChildComponent',
    props: {
      message: {
        type: String,
        required: true
      },
      level: {
        type: String,
        required: true
      },
      text: {
        type: String,
        required: true
      }
    },
    computed: {
      messageColorClass() {
        switch(this.level) {
          case '1': return 'level-1';
          case '2': return 'level-2';
          case '3': return 'level-3';
          case '4': return 'level-4';
          case '5': return 'level-5';
          case '6': return 'level-7';
          case '7': return 'level-7';
          default: return '';
        }
      }
    },
  methods: {
      getLevelColor(level) {
        const colorMap = {
          '1': 'rgb(0, 112, 255)',
          '2': 'rgb(100, 181, 246)',
          '3': 'rgb(158, 212, 255)',
          '4': 'rgb(178, 235, 242)',
          '5': 'rgb(129, 199, 132)',
          '6': 'rgb(255, 183, 77)',
          '7': 'rgb(255, 61, 0)'
        }
        return colorMap[level] || '#ccc'
      }
    }
  };
  </script>
  
  <style scoped>
  .child-item {
    position: relative;
    width: 250px;
    height: 200px;
    background: white;
    border-radius: 12px;
    padding: 16px;

  }
  
  .head {
    position: absolute;
    font-weight: 700;
    display: flex;
    align-items: center;
    gap: 6px; /* 盒子间距 */
  }
  
  .level-box {
    width: 12px;
    height: 12px;
    border-radius: 2px;
    display: inline-block;
  }
  
  .message {
    font-size: 20px;
    font-weight: 700;
  }
  
  /* 颜色等级 */
  .level-1 {
    color: rgb(0, 112, 255);
  }
  .level-2 {
    color: rgb(100, 181, 246);
  }
  .level-3 {
    color: rgb(158, 212, 255);
  }
  .level-4 {
    color: rgb(178, 235, 242);
  }
  .level-5 {
    color: rgb(129, 199, 132);
  }
  .level-6 {
    color: rgb(255, 183, 77);
  }
  .level-7 {
    color: rgb(255, 61, 0);
  }
  p{
    font-size: 14px;
    }
  .svg{
    margin-top: 30px;
    margin-bottom: 5px;
  }
  .cls-1 {
    fill:none;stroke:#020202;stroke-miterlimit:10;stroke-width:1.91px;
}
  </style>
  