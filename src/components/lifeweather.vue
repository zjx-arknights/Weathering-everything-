<template>
    <div class="lifeway">
      <!-- 直接引入不同的子组件 -->
      <Type1 v-if="level1" 
      :level="level1"
      :message="message1"
      :text="text1" />

      <Type2 v-if="level2" 
      :level="level2"
      :message="message2"
      :text="text2" />

      <Type3 v-if="level3" 
      :level="level3"
      :message="message3"
      :text="text3" />

      <Type4 v-if="level4" 
      :level="level4"
      :message="message4"
      :text="text4" />

      <Type5 v-if="level5" 
      :level="level5"
      :message="message5"
      :text="text5" />

      <Type6 v-if="level6" 
      :level="level6"
      :message="message6"
      :text="text6" />

    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import Type1 from './lifeway/type1.vue';
  import Type2 from './lifeway/type2.vue';
  import Type3 from './lifeway/type3.vue';
  import Type4 from './lifeway/type4.vue';
  import Type5 from './lifeway/type5.vue';
  import Type6 from './lifeway/type6.vue';

  
  export default {
    name: 'ParentContainer',
    props: {
      locationId: {
        type: String,
        required: true
      }
    },
    watch: {
    locationId: {
      immediate: false,  // 初次加载不立即执行
      handler(newVal) {
        if (newVal) {
          this.fetchlifeweather(newVal);
        }
      }
    }
  },
    components: {
      Type1,
      Type2,
      Type3,
      Type4,
      Type5,
      Type6,

    },
    data() {
    return {
      lifeweatherdata:[],
      isLoading: false,
      error: null,

      level1: "3",
      message1:"较不宜",
      text1:"天气较好，但考虑天气寒冷，风力较强，推荐您进行室内运动，若户外运动请注意保暖并做好准备活动。",

      level2: "3",
      message2:"较不宜",
      text2:"较不宜洗车，未来一天无雨，风力较大，如果执意擦洗汽车，要做好蒙上污垢的心理准备。",

      level3: "6",
      message3:"热",
      text3:"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。",
      
      level4: "3",
      message4:"去油防晒",
      text4:"建议用蜜质SPF15以上面霜打底，水质无油粉底霜。",

      level5: "5",
      message5:"良",
      text5:"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。",
      
      level6: "3",
      message6:"中等",
      text6:"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。",
    };
    
  },
  methods:{
    async fetchlifeweather(locationId) {
      this.isLoading = true;
      this.error = null;
      
      try {
        const response = await axios.post(
          `http://${this.$ipLocation}:8080/api/lifeweather`,
          { locationId: locationId }
        );
        
        if (response.data.data.code === "200") {
          this.lifeweatherdata = response.data.data.daily;
          // console.log(this.lifeweatherdata)
          this.getallmessage()
        } else {
          throw new Error(response.data.data.message || 'Invalid data format');
        }
      } catch (err) {
        console.error('获取天气指数失败:', err);
        this.error = err.message;
        // 可以在这里显示错误提示
      } finally {
        this.isLoading = false;
      }
    },
    getallmessage(){
      this.getmessage(1,1);
      this.getmessage(2,2);
      this.getmessage(3,3);
      this.getmessage(4,13);
      this.getmessage(5,10);
      this.getmessage(6,5);
    },
    getmessage(i,j){
      const foundItem = this.lifeweatherdata.find(item => item.type == j);
      // console.log(foundItem)
      this[`level${i}`] = foundItem?.level || null;
      this[`message${i}`] = foundItem?.category || null;
      this[`text${i}`] = foundItem?.text || null;
    }

  }
  };
  </script>
  
  <style scoped>
  .lifeway {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-content: center; /* 可选：内容居中 */
    width: 80%;
    margin: 0 auto; /* 替代 left: 10% */
  }
  
  /* 统一子组件宽度（防止样式冲突） */
  .parent-container > * {
    flex: 0 0 300px; /* 不伸缩，固定宽度 */
  }
  </style>
  