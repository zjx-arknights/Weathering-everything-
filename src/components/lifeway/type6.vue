<template>
    <div class="child-item">
      <div class="head">紫外线指数:<div 
          v-for="n in parseInt(level)" 
          :key="n"
          class="level-box"
          :style="{ backgroundColor: getLevelColor(n) }"
        ></div>{{ level }}
      </div>
      <div class="svg"><svg width="50px" height="50px" viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg">

<g id="a"/>

<g id="b">

<path d="M29,8v5c0,1.1-.9,2-2,2h-1.21l-.1-1H6.31l-.1,1h-1.21c-1.1,0-2-.9-2-2v-5c0-1.1,.9-2,2-2H27c1.1,0,2,.9,2,2Z" style="fill:#cfb1fc;"/>

<path d="M27,5H5c-1.6543,0-3,1.3457-3,3v5c0,1.6543,1.3457,3,3,3h.098l-.0926,.8975c-.0293,.2812,.0625,.562,.252,.7725,.1899,.21,.4595,.3301,.7427,.3301H26c.2832,0,.5527-.1201,.7427-.3301,.1895-.2104,.2812-.4912,.252-.7725l-.0926-.8975h.098c1.6543,0,3-1.3457,3-3v-5c0-1.6543-1.3457-3-3-3ZM7.1084,16l.1035-1H24.7881l.1035,1H7.1084Zm20.8916-3c0,.5513-.4487,1-1,1h-.3044l-.0106-.1025c-.0527-.5098-.4819-.8975-.9946-.8975H6.3096c-.5127,0-.9419,.3877-.9946,.8975l-.0106,.1025h-.3044c-.5513,0-1-.4487-1-1v-5c0-.5513,.4487-1,1-1H27c.5513,0,1,.4487,1,1v5Zm-1-3c0,.5522-.4478,1-1,1h-1c-.5522,0-1-.4478-1-1s.4478-1,1-1h1c.5522,0,1,.4478,1,1Zm-4,0c0,.5522-.4478,1-1,1h-1c-.5522,0-1-.4478-1-1s.4478-1,1-1h1c.5522,0,1,.4478,1,1Zm-1.3291,15.2422c.4097,.3706,.4414,1.0029,.0708,1.4126-.1973,.2183-.4692,.3291-.7422,.3291-.2393,0-.479-.0854-.6704-.2583-2.5371-2.2954-2.3774-6.5806-2.3696-6.7622,.0244-.5513,.4976-.9932,1.0415-.9536,.5508,.0234,.979,.4893,.9565,1.0405-.0015,.0352-.1216,3.5322,1.7134,5.1919Zm6.2363-.8213c-.1694,.3647-.5308,.5791-.9077,.5791-.1411,0-.2842-.0298-.4204-.0928-.1099-.0513-2.6978-1.2852-3.667-4.624-.1538-.5303,.1514-1.0854,.6816-1.2393,.5322-.1538,1.0854,.1514,1.2393,.6816,.707,2.4355,2.5693,3.3589,2.5879,3.3672,.501,.2324,.7188,.8271,.4863,1.3281Zm-13.8667-4.4575c.0078,.1816,.1675,4.4668-2.3696,6.7622-.1914,.1729-.4312,.2583-.6704,.2583-.2729,0-.5444-.1108-.7422-.3291-.3706-.4097-.3389-1.042,.0708-1.4126,1.5327-1.3862,1.7563-4.2168,1.7134-5.1899-.0244-.5518,.4028-1.019,.9546-1.0435,.5381-.0249,1.0186,.4023,1.0435,.9546Zm-4.9526,.3198c-.9692,3.3389-3.5571,4.5728-3.667,4.624-.1362,.063-.2793,.0928-.4204,.0928-.377,0-.7383-.2144-.9077-.5791-.2314-.499-.0161-1.0913,.481-1.3257,.0938-.0454,1.8901-.9468,2.5933-3.3696,.1538-.5303,.7065-.8359,1.2393-.6816,.5303,.1538,.8354,.709,.6816,1.2393Z" style="fill:#96c;"/>

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
          '1': 'rgb(230, 204, 255)',
          '2': 'rgb(204, 153, 255)',
          '3': 'rgb(178, 102, 255)',
          '4': 'rgb(153, 51, 255)',
          '5': 'rgb(102, 0, 204)',
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
    color: rgb(230, 204, 255);
  }
  .level-2 {
    color: rgb(204, 153, 255);
  }
  .level-3 {
    color: rgb(178, 102, 255);
  }
  .level-4 {
    color: rgb(153, 51, 255);
  }
  .level-5 {
    color: rgb(102, 0, 204);
  }
  p{
    font-size: 14px;
    }
  .svg{
    margin-top: 30px;
    margin-bottom: 5px;
  }
  </style>
  