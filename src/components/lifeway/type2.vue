<template>
  <div class="child-item">
    <div class="head">洗车指数: <div 
          v-for="n in parseInt(level)" 
          :key="n"
          class="level-box"
          :style="{ backgroundColor: getLevelColor(n) }"
        ></div>{{ level }}
      </div>
    <div class="svg"><svg width="50px" height="50px" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g>
        <path fill="none" d="M0 0h24v24H0z"/>
        <path d="M19 21H5v1a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1v-9l2.417-4.029A2 2 0 0 1 6.132 8h11.736a2 2 0 0 1 1.715.971L22 13v9a1 1 0 0 1-1 1h-1a1 1 0 0 1-1-1v-1zM4.332 13h15.336l-1.8-3H6.132l-1.8 3zM6.5 18a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3zm11 0a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3zM5.44 3.44L6.5 2.378l1.06 1.06a1.5 1.5 0 1 1-2.121 0zm5.5 0L12 2.378l1.06 1.06a1.5 1.5 0 1 1-2.121 0zm5.5 0l1.06-1.061 1.06 1.06a1.5 1.5 0 1 1-2.121 0z"/>
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
        default: return '';
      }
    }
  },
  methods: {
      getLevelColor(level) {
        const colorMap = {
          '1': 'green',
          '2': 'rgba(255, 208, 44)',
          '3': 'red',
          '4': 'black'
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
  color: green;
}
.level-2 {
  color: rgba(255, 208, 44);
}
.level-3 {
  color: red;
}
.level-4 {
  color: black;
}
p{
  font-size: 14px;
  }
.svg{
  margin-top: 30px;
  margin-bottom: 5px;
}
</style>
