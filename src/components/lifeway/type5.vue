<template>
    <div class="child-item">
      <div class="head">空气污染指数:<div 
          v-for="n in parseInt(level)" 
          :key="n"
          class="level-box"
          :style="{ backgroundColor: getLevelColor(n) }"
        ></div>{{ level }}
      </div>
      <div class="svg"><svg fill="#000000" width="50px" height="50px" viewBox="0 0 512 512" id="Layer_1" version="1.1" xml:space="preserve" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">

<g>

<path d="M419.1,187.3h-18.6c-0.1,0-0.2,0-0.3,0c-9.2-11.8-23-19.9-39-21.5l-0.8-0.1c-3.9-0.4-7.7-0.8-11.4-1.4l-72.1-12.2   c-13.9-2.3-28-2.3-41.9,0l-72.1,12.2c-3.7,0.6-7.4,1-11.4,1.4l-0.8,0.1c-16,1.7-29.8,9.7-38.9,21.5c-0.1,0-0.2,0-0.3,0H92.9   c-27.7,0-50.2,22.5-50.2,50.2v41.7c0,27.7,22.5,50.2,50.2,50.2h12.4c9.1,19.1,28.6,32.3,51.2,32.3h199c22.5,0,42-13.2,51.1-32.3   h12.5c27.7,0,50.2-22.5,50.2-50.2v-41.7C469.3,209.8,446.8,187.3,419.1,187.3z M92.9,314.4c-19.4,0-35.2-15.8-35.2-35.2v-41.7   c0-19.4,15.8-35.2,35.2-35.2h10.5c-2.3,6.2-3.6,12.9-3.6,19.9V305c0,3.2,0.3,6.3,0.8,9.4H92.9z M397.2,305.1   c0,23-18.7,41.7-41.7,41.7h-199c-23,0-41.7-18.7-41.7-41.7v-82.7c0-21.5,16.1-39.3,37.4-41.5l0.8-0.1c4-0.4,8.2-0.8,12.4-1.5   l72.1-12.2c6.2-1.1,12.3-1.6,18.5-1.6s12.3,0.5,18.5,1.5l72.1,12.2c4.2,0.7,8.4,1.1,12.4,1.5l0.8,0.1c21.3,2.2,37.4,20,37.4,41.5   V305.1z M454.3,279.2c0,19.4-15.8,35.2-35.2,35.2h-7.8c0.5-3,0.8-6.1,0.8-9.3v-82.9c0-7-1.3-13.7-3.6-19.9h10.6   c19.4,0,35.2,15.8,35.2,35.2V279.2z"/>

<g>

<g>

<path d="M355,271.2H157c-4.1,0-7.5-3.4-7.5-7.5s3.4-7.5,7.5-7.5h198c4.1,0,7.5,3.4,7.5,7.5S359.1,271.2,355,271.2z"/>

</g>

<g>

<g>

<path d="M355,308.2H206.5c-4.1,0-7.5-3.4-7.5-7.5s3.4-7.5,7.5-7.5H355c4.1,0,7.5,3.4,7.5,7.5S359.1,308.2,355,308.2z"/>

</g>

<g>

<path d="M183,308.2h-26c-4.1,0-7.5-3.4-7.5-7.5s3.4-7.5,7.5-7.5h26c4.1,0,7.5,3.4,7.5,7.5S187.1,308.2,183,308.2z"/>

</g>

</g>

<g>

<g>

<path d="M305.5,234.1H157c-4.1,0-7.5-3.4-7.5-7.5s3.4-7.5,7.5-7.5h148.5c4.1,0,7.5,3.4,7.5,7.5S309.6,234.1,305.5,234.1z"/>

</g>

<g>

<path d="M355,234.1h-26c-4.1,0-7.5-3.4-7.5-7.5s3.4-7.5,7.5-7.5h26c4.1,0,7.5,3.4,7.5,7.5S359.1,234.1,355,234.1z"/>

</g>

</g>

</g>

</g>

</svg></div>
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
          default: return '';
        }
      }
    },
    methods: {
      getLevelColor(level) {
        const colorMap = {
          '1': 'rgb(0, 228, 0)',
          '2': 'rgb(146, 208, 80)',
          '3': 'rgb(255, 215, 0)',
          '4': 'rgb(255, 126, 0)',
          '5': 'rgb(255, 0, 0)',
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
    color: rgb(0, 228, 0);
  }
  .level-2 {
    color: rgb(146, 208, 80);
  }
  .level-3 {
    color: rgb(255, 215, 0);
  }
  .level-4 {
    color: rgb(255, 126, 0);
  }
  .level-5 {
    color: rgb(255, 0, 0);
  }
  p{
    font-size: 14px;
    }
  .svg{
    margin-top: 30px;
    margin-bottom: 5px;
  }
  </style>
  