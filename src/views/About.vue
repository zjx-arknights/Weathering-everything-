<template>
  <div class="mostbigbox">
    <NavBar />
    <div class="weather-container">
      <!-- 左侧天气信息区域 -->
      <div class="weather-info">
        <div class="weather-header">
          <h2>{{ location }}</h2>
          <div class="update-time">更新时间: {{ formattedUpdateTime }}</div>
        </div>
  
        <div class="weather-main">
          <div class="current-weather">
            <div class="temperature">
              <span class="temp-value">{{ weatherData.now.temp }}</span>
              <span class="temp-unit">°C</span>
            </div>
            <div class="weather-condition">
              <div class="weather-icon">
                <img :src="getWeatherIcon(weatherData.now.icon)" :alt="weatherData.now.text">
              </div>
              <div class="weather-text">{{ weatherData.now.text }}</div>
            </div>
          </div>
  
          <div class="weather-details">
            <div class="detail-item">
              <span class="detail-label">体感温度</span>
              <span class="detail-value">{{ weatherData.now.feelsLike }}°C</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">风力风向</span>
              <span class="detail-value">{{ weatherData.now.windDir }} {{ weatherData.now.windScale }}级 ({{ weatherData.now.windSpeed }}km/h)</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">相对湿度</span>
              <span class="detail-value">{{ weatherData.now.humidity }}%</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">大气压强</span>
              <span class="detail-value">{{ weatherData.now.pressure }}hPa</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">降水量</span>
              <span class="detail-value">{{ weatherData.now.precip }}mm</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">能见度</span>
              <span class="detail-value">{{ weatherData.now.vis }}km</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">露点温度</span>
              <span class="detail-value">{{ weatherData.now.dew }}°C</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">云量</span>
              <span class="detail-value">{{ weatherData.now.cloud }}%</span>
            </div>
          </div>
  
          <div class="weather-footer">
            <a :href="weatherData.fxLink" target="_blank" class="more-info">查看更多天气信息</a>
            <div class="data-source">数据来源: {{ weatherData.refer.sources.join(', ') }}</div>
          </div>
        </div>
      </div>
  
      <!-- 右侧地图区域 -->
      <div class="map-container">
        <slot name="map">
            <map_
            :centerCoords="centerCoords"
            :location-id="locationId"
            @location-selected="handleLocationSelected" ></map_>
        </slot>
      </div>
    </div>
    <div class="daily_weather">
    <WeatherForecast 
    :location-id="locationId" 
    />
  </div>
  <div class="hour_weather">
    <HourlyForecast :location-id="locationId" />
  </div>
  <div class="life_way">
    <LifeWay :location-id="locationId" />
  </div>
  <!-- <i class="qi-805"></i> -->
  <Warning :location-id="locationId" />
  </div>
  </template>
  
  <script>
import NavBar from '../components/NavBar.vue'
import map_ from "../components/AMapComponent.vue"
import WeatherForecast from "../components/dailyweather.vue";
import HourlyForecast from '../components/hourweather.vue';
import LifeWay from '../components/lifeweather.vue';
import Warning from '../components/warning.vue';

export default {
  components: {
    NavBar,
    map_,
    WeatherForecast,
    HourlyForecast,
    LifeWay,
    Warning,
  },
  data() {
    return {
      centerCoords: {
        longitude: 114.41784,
        latitude: 30.51439
      },
      weatherData: {
  "code": "200",
  "updateTime": "2020-06-30T22:00+08:00",
  "fxLink": "http://hfx.link/2ax1",
  "now": {
    "obsTime": "2020-06-30T21:40+08:00",
    "temp": "24",
    "feelsLike": "26",
    "icon": "101",
    "text": "多云",
    "wind360": "123",
    "windDir": "东南风",
    "windScale": "1",
    "windSpeed": "3",
    "humidity": "72",
    "precip": "0.0",
    "pressure": "1003",
    "vis": "16",
    "cloud": "10",
    "dew": "21"
  },
  "refer": {
    "sources": [
      "QWeather",
      "NMC",
      "ECMWF"
    ],
    "license": [
      "QWeather Developers License"
    ]
  }
},
      location: '当前位置',
      locationId:''
    }
  },
  mounted() {
       // 从路由参数中获取 locationId
       if (this.$route.params.locationId) {
      this.locationId = this.$route.params.locationId;
      // 如果需要，可以立即使用这个 locationId 获取天气数据
      // this.fetchWeatherData(this.locationId);
    }
  },
  methods: {
    async fetchWeatherData(locationId) {
      try {
        const response = await axios.post('/api/weather', {
          locationId: locationId
        });
        
        if (response.data.code === "200") {
          this.weatherData = response.data;
        }
      } catch (error) {
        // console.error('获取天气数据失败:', error);
      }
    },
    
    handleLocationSelected(payload) {
      this.centerCoords = {
        longitude: payload.longitude,
        latitude: payload.latitude
      };
      
      if (payload.weatherData) {
        this.weatherData = payload.weatherData;
      }
      
      if (payload.locationName) {
        this.location = payload.locationName;
      }
      if (payload.locationId) {
        this.locationId = payload.locationId;
      }
    },
    getWeatherIcon(iconCode) {
        // 使用和风天气的图标
        // return `https://a.hecdn.net/img/plugin/190516/icon/c/${iconCode}.png`;
        return `https://dev.qweather.com/assets/images/logo-s-dark.png`;
      }
  
  },
    computed: {
      formattedUpdateTime() {
        if (!this.weatherData.updateTime) return '--';
        try {
          const date = new Date(this.weatherData.updateTime);
          return date.toLocaleString('zh-CN', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
          });
        } catch (e) {
          return this.weatherData.updateTime;
        }
      }
    },
    watch:{
    //   locationId(newVal, oldVal) {
    //   console.log('count 变化:', newVal, oldVal);
    // },
// 监听路由变化，当 locationId 变化时更新
    '$route.params.locationId'(newVal) {
      if (newVal) {
        this.locationId = newVal;
        console.log('count 变化:', newVal);
        // this.fetchWeatherData(this.locationId);
      }
    }
    }
  }
  </script>
  
  <style scoped>
 .mostbigbox {
  background-color: rgba(220, 234, 255);
}

.weather-container {
  display: flex;
  flex-direction: row;
  height: 100%;
  font-family: 'Arial', sans-serif;
}

.weather-info {
  flex: 0 0 350px;
  padding: 20px;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-right: 20px;
  overflow-y: auto;
  order: 1; /* 默认顺序 */
}

.map-container {
  flex: 1;
  background-color: #f0f0f0;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  min-height: 500px;
  order: 2; /* 默认顺序 */
}

/* 响应式布局 - 当屏幕宽度小于 768px 时 */
@media (max-width: 768px) {
  .weather-container {
    flex-direction: column;
  }
  
  .weather-info {
    flex: 1 1 auto;
    margin-right: 0;
    margin-bottom: 20px;
    order: 1;
  }
  
  .map-container {
    flex: 1 1 auto;
    min-height: 400px;
    order: 2;
  }
}
  
  .weather-header {
    margin-bottom: 20px;
    border-bottom: 1px solid #e0e0e0;
    padding-bottom: 10px;
  }
  
  .weather-header h2 {
    margin: 0;
    color: #333;
    font-size: 24px;
  }
  
  .update-time {
    font-size: 12px;
    color: #666;
    margin-top: 5px;
  }
  
  .current-weather {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 20px;
  }
  
  .temperature {
    display: flex;
    align-items: flex-start;
  }
  
  .temp-value {
    font-size: 48px;
    font-weight: bold;
    color: #333;
  }
  
  .temp-unit {
    font-size: 24px;
    margin-top: 5px;
    color: #666;
  }
  
  .weather-condition {
    text-align: right;
  }
  
  .weather-icon img {
    width: 50px;
    height: 50px;
  }
  
  .weather-text {
    font-size: 16px;
    color: #333;
    margin-top: 5px;
  }
  
  .weather-details {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 15px;
  }
  
  .detail-item {
    display: flex;
    justify-content: space-between;
    padding: 8px 0;
    border-bottom: 1px dashed #e0e0e0;
  }
  
  .detail-label {
    color: #666;
    font-size: 14px;
  }
  
  .detail-value {
    color: #333;
    font-weight: 500;
  }
  
  .weather-footer {
    margin-top: 20px;
    padding-top: 10px;
    border-top: 1px solid #e0e0e0;
    font-size: 12px;
  }
  
  .more-info {
    color: #1e88e5;
    text-decoration: none;
    display: inline-block;
    margin-bottom: 5px;
  }
  
  .more-info:hover {
    text-decoration: underline;
  }
  
  .data-source {
    color: #999;
  }

 .mostbigbox{
  background-color: rgba(220, 234, 255);
 }
  </style>
  