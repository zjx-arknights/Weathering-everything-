<template>
    <div class="hourly-forecast">
      <div class="header">
        <h3>逐小时天气预报（24h）</h3>
        <div class="update-time">更新时间: {{ formatUpdateTime }}</div>
      </div>
      
      <!-- 温度折线图 -->
      <div class="chart-container" @mousemove="handleChartHover" @mouseleave="hideTooltip">
        <canvas ref="temperatureChart"></canvas>

<!-- 天气图标容器（固定在图表顶部） -->
<div class="weather-icons-container">
        <div 
          v-for="(hour, index) in hourlyData" 
          :key="index" 
          class="weather-icon-wrapper"
          :style="getIconPosition(index)"
        >
          <i :class="'qi-' + hour.icon" class="weather-icon"></i>
          <div class="hour-label">{{ formatHourLabel(hour.fxTime) }}</div>
        </div>
      </div>

        <div v-if="showTooltip" class="custom-tooltip" :style="tooltipStyle">
          <div class="tooltip-time">{{ hoverData.time }}</div>
          <div class="tooltip-weather">
            <i :class="'qi-' + hoverData.icon"></i> {{ hoverData.text }}
          </div>
          <div class="tooltip-temp">温度: {{ hoverData.temp }}°C</div>
          <div class="tooltip-pop" v-if="hoverData.pop > 0">
            降水概率: {{ hoverData.pop }}%
          </div>
        </div>
        <div 
          v-if="showVerticalLine" 
          class="vertical-line" 
          :style="{ left: verticalLinePosition + 'px' }"
        ></div>
      </div>
      
      <!-- 降水概率图 -->
      <div class="chart-container" @mousemove="handlePrecipHover" @mouseleave="hidePrecipTooltip">
        <canvas ref="precipitationChart"></canvas>
        <div 
          v-if="showPrecipTooltip" 
          class="custom-tooltip" 
          :style="precipTooltipStyle"
        >
          <div class="tooltip-time">{{ hoverPrecipData.time }}</div>
          <div class="tooltip-pop-value">{{ hoverPrecipData.pop }}%</div>
          <div class="tooltip-weather">
            <i :class="'qi-' + hoverPrecipData.icon"></i> {{ hoverPrecipData.text }}
          </div>
        </div>
        <div 
          v-if="showPrecipVerticalLine" 
          class="vertical-line" 
          :style="{ left: precipVerticalLinePosition + 'px' }"
        ></div>
      </div>
    </div>
  </template>
  
  <script>
  import { Chart, registerables } from 'chart.js';
  import axios from 'axios';

  Chart.register(...registerables);
  
  export default {
    name: 'HourlyForecast',
    props: {
      locationId: {
        type: String,
        required: true
      }
    },
    data() {
      return {
        forecastData:{
  "code": "200",
  "updateTime": "2021-02-16T13:35+08:00",
  "fxLink": "http://hfx.link/2ax1",
  "hourly": [
    {
      "fxTime": "2021-02-16T15:00+08:00",
      "temp": "2",
      "icon": "100",
      "text": "晴",
      "wind360": "335",
      "windDir": "西北风",
      "windScale": "3-4",
      "windSpeed": "20",
      "humidity": "11",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1025",
      "cloud": "0",
      "dew": "-25"
    },
    {
      "fxTime": "2021-02-16T16:00+08:00",
      "temp": "1",
      "icon": "100",
      "text": "晴",
      "wind360": "339",
      "windDir": "西北风",
      "windScale": "3-4",
      "windSpeed": "24",
      "humidity": "11",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1025",
      "cloud": "0",
      "dew": "-26"
    },
    {
      "fxTime": "2021-02-16T17:00+08:00",
      "temp": "0",
      "icon": "100",
      "text": "晴",
      "wind360": "341",
      "windDir": "西北风",
      "windScale": "4-5",
      "windSpeed": "25",
      "humidity": "11",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1026",
      "cloud": "0",
      "dew": "-26"
    },
    {
      "fxTime": "2021-02-16T18:00+08:00",
      "temp": "0",
      "icon": "150",
      "text": "晴",
      "wind360": "344",
      "windDir": "西北风",
      "windScale": "4-5",
      "windSpeed": "25",
      "humidity": "12",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1025",
      "cloud": "0",
      "dew": "-27"
    },
    {
      "fxTime": "2021-02-16T19:00+08:00",
      "temp": "-2",
      "icon": "150",
      "text": "晴",
      "wind360": "349",
      "windDir": "西北风",
      "windScale": "3-4",
      "windSpeed": "24",
      "humidity": "13",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1025",
      "cloud": "0",
      "dew": "-27"
    },
    {
      "fxTime": "2021-02-16T20:00+08:00",
      "temp": "-3",
      "icon": "150",
      "text": "晴",
      "wind360": "353",
      "windDir": "北风",
      "windScale": "3-4",
      "windSpeed": "22",
      "humidity": "14",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1025",
      "cloud": "0",
      "dew": "-27"
    },
    {
      "fxTime": "2021-02-16T21:00+08:00",
      "temp": "-3",
      "icon": "150",
      "text": "晴",
      "wind360": "355",
      "windDir": "北风",
      "windScale": "3-4",
      "windSpeed": "20",
      "humidity": "14",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1026",
      "cloud": "0",
      "dew": "-27"
    },
    {
      "fxTime": "2021-02-16T22:00+08:00",
      "temp": "-4",
      "icon": "150",
      "text": "晴",
      "wind360": "356",
      "windDir": "北风",
      "windScale": "3-4",
      "windSpeed": "18",
      "humidity": "16",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1026",
      "cloud": "0",
      "dew": "-27"
    },
    {
      "fxTime": "2021-02-16T23:00+08:00",
      "temp": "-4",
      "icon": "150",
      "text": "晴",
      "wind360": "356",
      "windDir": "北风",
      "windScale": "3-4",
      "windSpeed": "18",
      "humidity": "16",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1026",
      "cloud": "0",
      "dew": "-27"
    },
    {
      "fxTime": "2021-02-17T00:00+08:00",
      "temp": "-4",
      "icon": "150",
      "text": "晴",
      "wind360": "354",
      "windDir": "北风",
      "windScale": "3-4",
      "windSpeed": "16",
      "humidity": "16",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1027",
      "cloud": "0",
      "dew": "-27"
    },
    {
      "fxTime": "2021-02-17T01:00+08:00",
      "temp": "-4",
      "icon": "150",
      "text": "晴",
      "wind360": "351",
      "windDir": "北风",
      "windScale": "3-4",
      "windSpeed": "16",
      "humidity": "16",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1028",
      "cloud": "0",
      "dew": "-27"
    },
    {
      "fxTime": "2021-02-17T02:00+08:00",
      "temp": "-4",
      "icon": "150",
      "text": "晴",
      "wind360": "350",
      "windDir": "北风",
      "windScale": "3-4",
      "windSpeed": "16",
      "humidity": "16",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1028",
      "cloud": "0",
      "dew": "-27"
    },
    {
      "fxTime": "2021-02-17T03:00+08:00",
      "temp": "-5",
      "icon": "150",
      "text": "晴",
      "wind360": "350",
      "windDir": "北风",
      "windScale": "3-4",
      "windSpeed": "16",
      "humidity": "16",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1028",
      "cloud": "0",
      "dew": "-27"
    },
    {
      "fxTime": "2021-02-17T04:00+08:00",
      "temp": "-5",
      "icon": "150",
      "text": "晴",
      "wind360": "351",
      "windDir": "北风",
      "windScale": "3-4",
      "windSpeed": "16",
      "humidity": "15",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1027",
      "cloud": "0",
      "dew": "-28"
    },
    {
      "fxTime": "2021-02-17T05:00+08:00",
      "temp": "-5",
      "icon": "150",
      "text": "晴",
      "wind360": "352",
      "windDir": "北风",
      "windScale": "3-4",
      "windSpeed": "16",
      "humidity": "14",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1026",
      "cloud": "0",
      "dew": "-29"
    },
    {
      "fxTime": "2021-02-17T06:00+08:00",
      "temp": "-5",
      "icon": "150",
      "text": "晴",
      "wind360": "355",
      "windDir": "北风",
      "windScale": "3-4",
      "windSpeed": "14",
      "humidity": "16",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1025",
      "cloud": "0",
      "dew": "-27"
    },
    {
      "fxTime": "2021-02-17T07:00+08:00",
      "temp": "-7",
      "icon": "150",
      "text": "晴",
      "wind360": "359",
      "windDir": "北风",
      "windScale": "3-4",
      "windSpeed": "16",
      "humidity": "20",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1024",
      "cloud": "0",
      "dew": "-26"
    },
    {
      "fxTime": "2021-02-17T08:00+08:00",
      "temp": "-5",
      "icon": "100",
      "text": "晴",
      "wind360": "1",
      "windDir": "北风",
      "windScale": "3-4",
      "windSpeed": "14",
      "humidity": "19",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1023",
      "cloud": "0",
      "dew": "-26"
    },
    {
      "fxTime": "2021-02-17T09:00+08:00",
      "temp": "-4",
      "icon": "100",
      "text": "晴",
      "wind360": "356",
      "windDir": "北风",
      "windScale": "3-4",
      "windSpeed": "14",
      "humidity": "17",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1023",
      "cloud": "0",
      "dew": "-25"
    },
    {
      "fxTime": "2021-02-17T10:00+08:00",
      "temp": "-1",
      "icon": "100",
      "text": "晴",
      "wind360": "344",
      "windDir": "西北风",
      "windScale": "3-4",
      "windSpeed": "14",
      "humidity": "14",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1024",
      "cloud": "0",
      "dew": "-26"
    },
    {
      "fxTime": "2021-02-17T11:00+08:00",
      "temp": "0",
      "icon": "100",
      "text": "晴",
      "wind360": "333",
      "windDir": "西北风",
      "windScale": "3-4",
      "windSpeed": "14",
      "humidity": "12",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1024",
      "cloud": "0",
      "dew": "-26"
    },
    {
      "fxTime": "2021-02-17T12:00+08:00",
      "temp": "1",
      "icon": "100",
      "text": "晴",
      "wind360": "325",
      "windDir": "西北风",
      "windScale": "3-4",
      "windSpeed": "14",
      "humidity": "10",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1025",
      "cloud": "16",
      "dew": "-28"
    },
    {
      "fxTime": "2021-02-17T13:00+08:00",
      "temp": "2",
      "icon": "100",
      "text": "晴",
      "wind360": "319",
      "windDir": "西北风",
      "windScale": "3-4",
      "windSpeed": "16",
      "humidity": "8",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1025",
      "cloud": "32",
      "dew": "-29"
    },
    {
      "fxTime": "2021-02-17T14:00+08:00",
      "temp": "2",
      "icon": "100",
      "text": "晴",
      "wind360": "313",
      "windDir": "西北风",
      "windScale": "3-4",
      "windSpeed": "16",
      "humidity": "9",
      "pop": "0",
      "precip": "0.0",
      "pressure": "1025",
      "cloud": "48",
      "dew": "-27"
    }
  ],
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
}
,
        temperatureChart: null,
        precipitationChart: null,
        showTooltip: false,
        showVerticalLine: false,
        verticalLinePosition: 0,
        hoverData: {
          time: '',
          temp: 0,
          pop: 0,
          icon: '',
          text: ''
        },
        tooltipStyle: {
          left: '0px',
          top: '0px'
        },
        showPrecipTooltip: false,
        showPrecipVerticalLine: false,
        precipVerticalLinePosition: 0,
        hoverPrecipData: {
          time: '',
          pop: 0,
          icon: '',
          text: ''
        },
        precipTooltipStyle: {
          left: '0px',
          top: '0px'
        },
        minTemp: null,
        maxTemp: null,
        iconPositions: [],
        chart: null,
      isLoading: false,
      error: null
      };
    },
    computed: {
      hourlyData() {
        return this.forecastData.hourly.map(hour => ({
          ...hour,
          temp: parseInt(hour.temp),
          pop: parseInt(hour.pop)
        }));
      },
      formatUpdateTime() {
        const date = new Date(this.forecastData.updateTime);
        return date.toLocaleString();
      },
      hourLabels() {
        return this.hourlyData.map(hour => {
          const date = new Date(hour.fxTime);
          return date.getHours() + '时';
        });
      }
    },
    mounted() {
      this.calculateTempRange();
      this.renderCharts();
      window.addEventListener('resize', this.handleResize);
    },
    beforeDestroy() {
      if (this.temperatureChart) {
        this.temperatureChart.destroy();
      }
      if (this.precipitationChart) {
        this.precipitationChart.destroy();
      }
      window.removeEventListener('resize', this.handleResize);
    },

    watch: {
    locationId: {
      immediate: false,  // 初次加载不立即执行
      handler(newVal) {
        if (newVal) {
          this.fetchhourForecast(newVal);
        }
      }
    }
  },
    methods: {

        async fetchhourForecast(locationId) {
      this.isLoading = true;
      this.error = null;
      
      try {
        // console.log('send')
        const response = await axios.post(
          `http://${this.$ipLocation}:8080/api/hourweather`,
          { locationId: locationId }
        );
        
        if (response.data.data.code === "200") {
          // console.log(response.data.data)
          this.forecastData = response.data.data;
          this.renderCharts();
        } else {
          throw new Error(response.data.data.message || 'Invalid data format');
        }
      } catch (err) {
        console.error('获取天气预报失败:', err);
        this.error = err.message;
        // 可以在这里显示错误提示
      } finally {
        this.isLoading = false;
      }
    },

      calculateTempRange() {
        const temps = this.hourlyData.map(h => h.temp);
        const min = Math.min(...temps);
        const max = Math.max(...temps);
        console.log(min,max)
        // 设置合理的上下限，使曲线更平滑
        this.minTemp = min - 2;
        this.maxTemp = max + 2;
      },
      
      renderCharts() {
        this.calculateTempRange();
        this.renderTemperatureChart();
        this.renderPrecipitationChart();
      },
      
      renderTemperatureChart() {
        const ctx = this.$refs.temperatureChart.getContext('2d');
        
        if (this.temperatureChart) {
          this.temperatureChart.destroy();
        }
        
         // 必须在图表渲染完成后获取canvas高度
  this.$nextTick(() => {
    // 创建垂直渐变（从顶部到底部）
    const gradient = ctx.createLinearGradient(
      0, 0,                     // 起点坐标（左上角）
      0, ctx.canvas.height*1.5    // 终点坐标（左下角）
    );
        
      gradient.addColorStop(0, 'rgba(255, 0, 0, 0.2)');    // 红色 - 高温
      gradient.addColorStop(0.5, 'rgba(255, 255, 0, 0.2)'); // 黄色 - 中温
      gradient.addColorStop(1, 'rgba(0, 0, 255, 0.2)');    // 蓝色 - 低温
      

      this.temperatureChart = new Chart(ctx, {
        type: 'line',
        data: {
          labels: this.hourLabels,
          datasets: [{
            label: '温度 (°C)',
            data: this.hourlyData.map(h => h.temp),
            borderColor: '#ff6b6b',
            backgroundColor: gradient,
            borderWidth: 3,
            pointRadius: 0,
            pointHoverRadius: 0,
            fill: {
            target: 'origin',  // 从曲线到坐标原点（底部）
            above: gradient,    // 曲线以上区域
            below: gradient     // 曲线以下区域
            },
            tension: 0.4
          }]
        },
          options: {
            responsive: true,
            maintainAspectRatio: false,
            layout: {
              padding: {
                top: 50 // 为顶部图标留出空间
              }
            },
            onHover: (e) => {
              // 禁用默认的tooltip
            },
            plugins: {
              tooltip: {
                enabled: false
              },
              legend: {
                display: false
              }
            },
            scales: {
              y: {
                min: this.minTemp,
                max: this.maxTemp,
                title: {
                  display: true,
                  text: '温度 (°C)'
                },
                grid: {
                  color: 'rgba(0, 0, 0, 0.1)'
                }
              },
              x: {
                grid: {
                  display: false
                }
              }
            },
            elements: {
              line: {
                cubicInterpolationMode: 'monotone'
              },
              point: {
                radius: 0, // 确保没有点显示
                hitRadius: 10 // 修复hitRadius错误的关键
              }
            }
          }
        });
        this.updateIconPositions();
    });
      },
      
// 修改后的图标定位方法 - 固定在顶部
getIconPosition(index) {
      if (!this.temperatureChart) return {};
      
      const meta = this.temperatureChart.getDatasetMeta(0);
      if (!meta.data[index]) return {};
      
      const point = meta.data[index];
      return {
        left: `${point.x}px`,
        top: '10px' // 固定在顶部
      };
    },
    
    // 新增方法：格式化小时标签
    formatHourLabel(timeString) {
      const date = new Date(timeString);
      return date.getHours() + '时';
    },
    
    updateIconPositions() {
      this.$nextTick(() => {
        this.iconPositions = this.hourlyData.map((_, index) => 
          this.getIconPosition(index)
        );
      });
    },
    
    handleResize() {
      if (this.temperatureChart) {
        this.temperatureChart.resize();
        this.updateIconPositions();
      }
    },

      renderPrecipitationChart() {
        const ctx = this.$refs.precipitationChart.getContext('2d');
        
        if (this.precipitationChart) {
          this.precipitationChart.destroy();
        }
        
        this.precipitationChart = new Chart(ctx, {
          type: 'bar',
          data: {
            labels: this.hourLabels,
            datasets: [{
              label: '降水概率 (%)',
              data: this.hourlyData.map(h => h.pop),
              backgroundColor: this.hourlyData.map(h => 
                h.pop > 0 ? 'rgba(77, 171, 247, 0.7)' : 'rgba(233, 236, 239, 0.5)'
              ),
              borderColor: this.hourlyData.map(h => 
                h.pop > 0 ? 'rgba(77, 171, 247, 1)' : 'rgba(233, 236, 239, 1)'
              ),
              borderWidth: 1
            }]
          },
          options: {
            responsive: true,
            onHover: (e) => {
              // 禁用默认的tooltip
            },
            plugins: {
              tooltip: {
                enabled: false
              },
              legend: {
                display: false
              }
            },
            scales: {
              y: {
                beginAtZero: true,
                max: 100,
                title: {
                  display: true,
                  text: '降水概率 (%)'
                },
                grid: {
                  color: 'rgba(0, 0, 0, 0.1)'
                }
              },
              x: {
                grid: {
                  display: false
                }
              }
            }
          }
        });
      },
      
      handleChartHover(event) {
        if (!this.temperatureChart) return;
        
        const canvasPosition = this.$refs.temperatureChart.getBoundingClientRect();
        
        // 获取最近的点的索引
        const points = this.temperatureChart.getElementsAtEventForMode(
          event, 'index', { intersect: false }, false
        );
        
        if (points.length) {
          const index = points[0].index;
          const hour = this.hourlyData[index];
          
          this.hoverData = {
            time: this.formatHour(hour.fxTime),
            temp: hour.temp,
            pop: hour.pop,
            icon: hour.icon,
            text: hour.text
          };
          
          // 更新垂直线的位置
          const point = this.temperatureChart.getDatasetMeta(0).data[index];
          this.verticalLinePosition = point.x;
          this.showVerticalLine = true;
          
          // 更新tooltip位置
          this.tooltipStyle = {
            left: `${point.x + 10}px`,
            top: `${Math.min(point.y - 40, canvasPosition.height - 120)}px`
          };
          
          this.showTooltip = true;
        }
      },
      
      handlePrecipHover(event) {
        if (!this.precipitationChart) return;
        
        const canvasPosition = this.$refs.precipitationChart.getBoundingClientRect();
        
        // 获取最近的点的索引
        const points = this.precipitationChart.getElementsAtEventForMode(
          event, 'index', { intersect: false }, false
        );
        
        if (points.length) {
          const index = points[0].index;
          const hour = this.hourlyData[index];
          
          this.hoverPrecipData = {
            time: this.formatHour(hour.fxTime),
            pop: hour.pop,
            icon: hour.icon,
            text: hour.text
          };
          
          // 更新垂直线的位置
          const point = this.precipitationChart.getDatasetMeta(0).data[index];
          this.precipVerticalLinePosition = point.x;
          this.showPrecipVerticalLine = true;
          
          // 更新tooltip位置
          this.precipTooltipStyle = {
            left: `${point.x + 10}px`,
            top: `${Math.min(point.y - 40, canvasPosition.height - 100)}px`
          };
          
          this.showPrecipTooltip = true;
        }
      },
      
      hideTooltip() {
        this.showTooltip = false;
        this.showVerticalLine = false;
      },
      
      hidePrecipTooltip() {
        this.showPrecipTooltip = false;
        this.showPrecipVerticalLine = false;
      },
      
      formatHour(timeString) {
        const date = new Date(timeString);
        return date.getHours() + '时';
      }
    }
  };
  </script>
  
  <style scoped>
  .hourly-forecast {
    font-family: 'Arial', sans-serif;
    max-width: 800px;
    margin: 50px auto;
    padding: 20px;
    background: #f8f9fa;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  }
  
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }
  
  .header h3 {
    margin: 0;
    color: #333;
    font-size: 1.4em;
  }
  
  .update-time {
    color: #666;
    font-size: 0.9em;
  }
  
  .chart-container {
    position: relative;
    height: 350px;
    margin-bottom: 30px;
    background: white;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  }
  
  .custom-tooltip {
    position: absolute;
    background: rgba(255, 255, 255, 0.95);
    padding: 12px 16px;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    font-size: 0.95em;
    z-index: 10;
    pointer-events: none;
    min-width: 140px;
    backdrop-filter: blur(2px);
    border: 1px solid rgba(0, 0, 0, 0.1);
  }
  
  .tooltip-time {
    font-weight: bold;
    margin-bottom: 6px;
    color: #333;
    font-size: 1.1em;
  }
  
  .tooltip-weather {
    display: flex;
    align-items: center;
    margin: 8px 0;
    font-size: 0.95em;
  }
  
  .tooltip-temp {
    color: #ff6b6b;
    font-weight: bold;
    font-size: 1.2em;
    margin: 5px 0;
  }
  
  .tooltip-pop {
    color: #4dabf7;
    font-size: 0.95em;
  }
  
  .tooltip-pop-value {
    color: #4dabf7;
    font-size: 1.3em;
    font-weight: bold;
    margin: 8px 0;
  }
  
  .vertical-line {
    position: absolute;
    top: 0;
    width: 1px;
    height: 100%;
    background-color: transparent;
    pointer-events: none;
    z-index: 5;
    border-left: 1px dashed rgba(0, 0, 0, 0.4);
  }
  
  /* 天气图标样式 */
  .qi {
    font-size: 1.2em;
    margin-right: 8px;
  }

  /* 修改天气图标样式 */
.weather-icons-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 50px; /* 固定高度 */
  pointer-events: none;
}

.weather-icon-wrapper {
  position: absolute;
  transform: translateX(-50%);
  display: flex;
  flex-direction: column;
  align-items: center;
}

.weather-icon {
  font-size: 20px;
  color: #333;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 50%;
  padding: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 4px;
}

.hour-label {
  font-size: 10px;
  color: #666;
  white-space: nowrap;
}

/* 调整图表容器内边距 */
.chart-container {
  padding-top: 50px; /* 为顶部图标留出空间 */
  height: 400px; /* 增加总高度 */
}

  </style>
  