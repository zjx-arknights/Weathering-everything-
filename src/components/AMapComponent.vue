<template>
  <div class="map-container">
    <div class="search-container">
      <form @submit.prevent="searchLocation">
        <input 
          type="text" 
          v-model="searchQuery" 
          placeholder="输入地址名称，如：北京"
          class="search-input"
        >
        <button type="submit" class="search-button">搜索</button>
      </form>
      
      <div v-if="locations.length > 0" class="location-results">
        <select v-model="selectedLocation" @change="onLocationSelect" class="location-select">
          <option value="">请选择地址</option>
          <option 
            v-for="location in locations" 
            :key="location.id" 
            :value="location"
          >
            {{ location.name }} ({{ location.adm1 }})
          </option>
        </select>
      </div>
    </div>

    <div id="container" ref="mapContainer"></div>
    <div v-if="clickedPosition" class="clicked-position">
      点击位置: 经度 {{ clickedPosition.lng.toFixed(6) }}, 纬度 {{ clickedPosition.lat.toFixed(6) }}
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'AMapComponent',
  props: {
    centerCoords: {
      type: Object,
      required: true,
      validator: value => {
        return value.longitude && value.latitude
      }
    },
      locationId: {
        type: String,
        required: true
      }
    
  },
  data() {
    return {
      searchQuery: '',
      locations: [],
      selectedLocation: null,
      map: null,
      marker: null,
      infoWindow: null,
      isLoading: false,
      clickedPosition: null
    };
  },
  mounted() {
    this.loadAMapScript().then(() => {
      this.initMap();
    });
  },
  methods: {
    loadAMapScript() {
      return new Promise((resolve, reject) => {
        if (window.AMap) {
          resolve();
          return;
        }
        
        const script = document.createElement('script');
        script.src = `https://webapi.amap.com/maps?v=2.0&key=1a74d881336deafe6c20e911798f825c`;
        script.onload = resolve;
        script.onerror = reject;
        document.head.appendChild(script);
      });
    },
    
    initMap() {
      const initialCenter = this.centerCoords && this.centerCoords.longitude && this.centerCoords.latitude 
        ? [this.centerCoords.longitude, this.centerCoords.latitude] 
        : [104.06, 35.86];
      
      this.map = new AMap.Map(this.$refs.mapContainer, {
        viewMode: '2D',
        zoom: 13,
        center: initialCenter
      });

      // 添加地图点击事件监听
      this.map.on('click', (e) => {
        this.handleMapClick(e);
      });

      AMap.plugin('AMap.DistrictSearch', () => {
        const districtSearch = new AMap.DistrictSearch({
          extensions: 'all',
          level: 'country'
        });

        districtSearch.search('中国', (status, result) => {
          if (status === 'complete') {
            const bounds = result.districtList[0].boundaries;
            bounds.forEach(boundary => {
              new AMap.Polygon({
                map: this.map,
                strokeWeight: 1,
                path: boundary,
                fillOpacity: 0.2,
                fillColor: '#CCF3FF',
                strokeColor: '#CC66CC'
              });
            });
          }
        });
      });

      if (this.centerCoords && this.centerCoords.longitude && this.centerCoords.latitude) {
        this.markPosition(this.centerCoords.longitude, this.centerCoords.latitude);
      }
    },

    // 处理地图点击事件
    async handleMapClick(e) {
      
      this.clickedPosition = {
        lng: e.lnglat.getLng(),
        lat: e.lnglat.getLat()
      };
      const position = this.clickedPosition
      // console.log(position)
      // 标记点击位置
      this.markPosition(this.clickedPosition.lng, this.clickedPosition.lat);
    
    try {
      // 尝试调用真实API
      const response = await axios.post(`http://${this.$ipLocation}:8080/api/position`, {
        position: position
      });
      if (response.data.data && response.data.data.code === "200") {
        // console.log(response.data.data)
        this.locations = response.data.data.location;
        try {
          // console.log(this.locations)
         const locationId = this.locations[0].id
        //  console.log(locationId)
          // 发送地址ID到后端获取天气数据
          const response = await axios.post(`http://${this.$ipLocation}:8080/api/dailyweather`, {
            locationId: locationId
          });
          if(response.data.data)
          console.log(response.data.data)
          //请求成功才会执行
          this.$emit('location-selected', {
            longitude: this.clickedPosition.lng,
            latitude: this.clickedPosition.lat,
            weatherData: response.data.data,
            locationName: this.locations[0].name,
            locationId:locationId
          });
          
        } catch (error) {
          console.error('获取天气数据失败:', error);
          // alert('获取天气数据失败，请重试');
        }

      }
    } catch (apiError) {
      console.warn('失败:', apiError);
      // API调用失败时，继续使用默认的mockResponse
    }
      //通知父组件坐标变化
      // this.$emit('location-selected', {
      //   longitude: this.clickedPosition.lng,
      //   latitude: this.clickedPosition.lat,
      //   weatherData: null,
      //   locationName: '点击位置',
      //   locationId: null
      // });
    },
    

    async searchLocation() {
     if (!this.searchQuery.trim()) {
      alert('请输入搜索地址');
      return;
     }
  
  this.isLoading = true;
  try {
    // 默认的模拟数据
    const defaultMockResponse = {
     "code":"200",
      "location":[
    {
      "name":"北京",
      "id":"101010100",
      "lat":"39.90499",
      "lon":"116.40529",
      "adm2":"北京",
      "adm1":"北京市",
      "country":"中国",
      "tz":"Asia/Shanghai",
      "utcOffset":"+08:00",
      "isDst":"0",
      "type":"city",
      "rank":"10",
      "fxLink":"https://www.qweather.com/weather/beijing-101010100.html"
    },
    {
      "name":"海淀",
      "id":"101010200",
      "lat":"39.95607",
      "lon":"116.31032",
      "adm2":"北京",
      "adm1":"北京市",
      "country":"中国",
      "tz":"Asia/Shanghai",
      "utcOffset":"+08:00",
      "isDst":"0",
      "type":"city",
      "rank":"15",
      "fxLink":"https://www.qweather.com/weather/haidian-101010200.html"
    },
    {
      "name":"朝阳",
      "id":"101010300",
      "lat":"39.92149",
      "lon":"116.48641",
      "adm2":"北京",
      "adm1":"北京市",
      "country":"中国",
      "tz":"Asia/Shanghai",
      "utcOffset":"+08:00",
      "isDst":"0",
      "type":"city",
      "rank":"15",
      "fxLink":"https://www.qweather.com/weather/chaoyang-101010300.html"
    },
    {
      "name":"昌平",
      "id":"101010700",
      "lat":"40.21809",
      "lon":"116.23591",
      "adm2":"北京",
      "adm1":"北京市",
      "country":"中国",
      "tz":"Asia/Shanghai",
      "utcOffset":"+08:00",
      "isDst":"0",
      "type":"city",
      "rank":"23",
      "fxLink":"https://www.qweather.com/weather/changping-101010700.html"
    },
    {
      "name":"房山",
      "id":"101011200",
      "lat":"39.73554",
      "lon":"116.13916",
      "adm2":"北京",
      "adm1":"北京市",
      "country":"中国",
      "tz":"Asia/Shanghai",
      "utcOffset":"+08:00",
      "isDst":"0",
      "type":"city",
      "rank":"23",
      "fxLink":"https://www.qweather.com/weather/fangshan-101011200.html"
    },
    {
      "name":"通州",
      "id":"101010600",
      "lat":"39.90249",
      "lon":"116.65860",
      "adm2":"北京",
      "adm1":"北京市",
      "country":"中国",
      "tz":"Asia/Shanghai",
      "utcOffset":"+08:00",
      "isDst":"0",
      "type":"city",
      "rank":"23",
      "fxLink":"https://www.qweather.com/weather/tongzhou-101010600.html"
    },
    {
      "name":"丰台",
      "id":"101010900",
      "lat":"39.86364",
      "lon":"116.28696",
      "adm2":"北京",
      "adm1":"北京市",
      "country":"中国",
      "tz":"Asia/Shanghai",
      "utcOffset":"+08:00",
      "isDst":"0",
      "type":"city",
      "rank":"25",
      "fxLink":"https://www.qweather.com/weather/fengtai-101010900.html"
    },
    {
      "name":"大兴",
      "id":"101011100",
      "lat":"39.72891",
      "lon":"116.33804",
      "adm2":"北京",
      "adm1":"北京市",
      "country":"中国",
      "tz":"Asia/Shanghai",
      "utcOffset":"+08:00",
      "isDst":"0",
      "type":"city",
      "rank":"25",
      "fxLink":"https://www.qweather.com/weather/daxing-101011100.html"
    },
    {
      "name":"延庆",
      "id":"101010800",
      "lat":"40.46532",
      "lon":"115.98501",
      "adm2":"北京",
      "adm1":"北京市",
      "country":"中国",
      "tz":"Asia/Shanghai",
      "utcOffset":"+08:00",
      "isDst":"0",
      "type":"city",
      "rank":"33",
      "fxLink":"https://www.qweather.com/weather/yanqing-101010800.html"
    },
    {
      "name":"平谷",
      "id":"101011500",
      "lat":"40.14478",
      "lon":"117.11234",
      "adm2":"北京",
      "adm1":"北京市",
      "country":"中国",
      "tz":"Asia/Shanghai",
      "utcOffset":"+08:00",
      "isDst":"0",
      "type":"city",
      "rank":"33",
      "fxLink":"https://www.qweather.com/weather/pinggu-101011500.html"
    }
  ],
  "refer":{
    "sources":[
      "QWeather"
    ],
    "license":[
      "QWeather Developers License"
    ]
  }
    };
    console.log(this.$ipLocation)
    let mockResponse = defaultMockResponse; // 默认使用模拟数据
    
    try {
      // 尝试调用真实API
      const response = await axios.post(`http://${this.$ipLocation}:8080/api/locations`, {
        query: this.searchQuery
      });
      // if(response.data.data){
      //   console.log(response.data.data)
      // }
      // 如果API返回有效数据，则使用API数据
      if (response.data.data && response.data.data.code === "200") {
        mockResponse = response.data.data;
      }
    } catch (apiError) {
      console.warn('API调用失败，使用默认数据:', apiError);
      // API调用失败时，继续使用默认的mockResponse
    }

    if (mockResponse.code === "200") {
      this.locations = mockResponse.location;
    } else {
      throw new Error('API返回错误');
    }
    
  } catch (error) {
    console.error('搜索地址失败:', error);
    alert('搜索地址失败，请重试');
    this.locations = [];
  } finally {
    this.isLoading = false;
  }
    },

    async searchLocationfirst(locationId) {

  this.isLoading = true;
  try {    
    try {
      // 尝试调用真实API
      const response = await axios.post(`http://${this.$ipLocation}:8080/api/locations`, {
        query: locationId
      });
      // if(response.data.data){
      //   console.log(response.data.data)
      // }
      // 如果API返回有效数据，则使用API数据
      if (response.data.data && response.data.data.code === "200") {
        // mockResponse = response.data.data;
        const lat = response.data.data.location[0].lat
        const lon =response.data.data.location[0].lon
        const locationfirst =response.data.data.location[0].name

        console.log('11'+lat)
        console.log('22'+lon)
      

        this.$emit('location-selected', {
            longitude: lon,
            latitude: lat,
            locationName: locationfirst,
          });
          
          
      }
    } catch (apiError) {
      console.warn('API调用失败，使用默认数据:', apiError);
      // API调用失败时，继续使用默认的mockResponse
    }

    // if (mockResponse.code === "200") {
    //   this.locations = mockResponse.location;
    // } else {
    //   throw new Error('API返回错误');
    // }
    
  } catch (error) {
    console.error('搜索地址失败:', error);
    alert('搜索地址失败，请重试');
    this.locations = [];
  } finally {
    this.isLoading = false;
  }
    },
    
    async onLocationSelect() {
      if (!this.selectedLocation) return;
      
      const lon = parseFloat(this.selectedLocation.lon);
      const lat = parseFloat(this.selectedLocation.lat);
      const locationId = this.selectedLocation.id;
      
      if (!isNaN(lon) && !isNaN(lat)) {
        this.markPosition(lon, lat);
        
        try {
          console.log('11122')
           // 通知父组件坐标和天气数据变化

          // 发送地址ID到后端获取天气数据
          const response = await axios.post(`http://${this.$ipLocation}:8080/api/dailyweather`, {
            locationId: locationId
          });
          //请求成功才会执行
          this.$emit('location-selected', {
            longitude: lon,
            latitude: lat,
            weatherData: response.data.data,
            locationName: this.selectedLocation.name,
            locationId:locationId
          });
         
          
        } catch (error) {
          console.error('获取天气数据失败:', error);
          // alert('获取天气数据失败，请重试');
        }
      }
    },
    
    async onLocationSelectfirst(locationId) {
      // if (!this.selectedLocation) return;
      
      // const lon = parseFloat(this.selectedLocation.lon);
      // const lat = parseFloat(this.selectedLocation.lat);
      // const locationId = this.selectedLocation.id;
      
      // if (!isNaN(lon) && !isNaN(lat)) {
      //   this.markPosition(lon, lat);
        
        try {
          console.log('11122')
           // 通知父组件坐标和天气数据变化

          // 发送地址ID到后端获取天气数据
          const response = await axios.post(`http://${this.$ipLocation}:8080/api/dailyweather`, {
            locationId: locationId
          });
          //请求成功才会执行
          this.$emit('location-selected', {
            // longitude: lon,
            // latitude: lat,
            weatherData: response.data.data,
            // locationName: this.selectedLocation.name,
            locationId:locationId
          });
          
          
        } catch (error) {
          console.error('获取天气数据失败:', error);
          // alert('获取天气数据失败，请重试');
        }
      
    },

    markPosition(lng, lat) {
      // 清除之前的标记
      if (this.marker) {
        this.map.remove(this.marker);
      }
      if (this.infoWindow) {
        this.infoWindow.close();
      }
      
      // 添加新标记
      this.marker = new AMap.Marker({
        position: [lng, lat],
        map: this.map
      });
      
      // 定位到标记位置
      this.map.setCenter([lng, lat]);
      this.map.setZoom(13);
      
      // 添加信息窗口
      this.infoWindow = new AMap.InfoWindow({
        content: `<div>标记位置<br>经度: ${lng.toFixed(6)}<br>纬度: ${lat.toFixed(6)}</div>`,
        offset: new AMap.Pixel(0, -30)
      });
      this.infoWindow.open(this.map, [lng, lat]);
    }
  },
  watch: {
    centerCoords: {
      immediate: true,
      deep: true,
      handler(newVal) {
        if (this.map && newVal && newVal.longitude && newVal.latitude) {
          this.map.setCenter([newVal.longitude, newVal.latitude]);
          this.map.setZoom(13);
          
          if (this.marker) {
            this.marker.setPosition([newVal.longitude, newVal.latitude]);
          }
          
          if (this.infoWindow) {
            this.infoWindow.setPosition([newVal.longitude, newVal.latitude]);
          }
        }
      }
    },
    locationId: {
      // immediate: false,  // 初次加载不立即执行
      handler(newVal) {
        if (newVal) {
          console.log('123456'+newVal)
          this.onLocationSelectfirst(newVal)
          this.searchLocationfirst(newVal)
        }
      }
    }
  }
};
</script>

<style scoped>
.map-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
 
}

#container {
  width: 100%;
  height: 600px;
  margin-top: 10px;
}

.search-container {
  padding: 15px;
  background: #f5f5f5;
  border-radius: 5px;
  margin-bottom: 10px;
}

.search-input {
  padding: 8px 12px;
  width: 300px;
  border: 1px solid #ddd;
  border-radius: 12px;
  margin-right: 10px;
}

.search-button {
  padding: 8px 16px;
  background-color: rgba(0,0,0,0.6);
  color: white;
  border: none;
  border-radius: 12px;
  cursor: pointer;
}

.search-button:hover {
  background-color: #45a049;
}

.location-results {
  margin-top: 10px;
}

.location-select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: white;
}

.location-select:focus {
  outline: none;
  border-color: #4CAF50;
}

.clicked-position {
  margin-top: 10px;
  padding: 8px;
  background: #f5f5f5;
  border-radius: 4px;
  font-size: 14px;
}
</style>
