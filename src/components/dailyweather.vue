<template>
  <div class="forecast-container" v-if="dailyForecastData.daily">
    <div class="forecast-header">
      <h3>15天天气预报</h3>
      <div class="update-time">更新时间: {{ formatUpdateTime(dailyForecastData.updateTime) }}</div>
    </div>
    
    <div class="chart-container">
      <canvas ref="temperatureChart"></canvas>
    </div>
    
    <div class="forecast-details">
      <div v-for="(day, index) in dailyForecastData.daily" :key="index" class="forecast-day">
        <div class="forecast-date">{{ formatDate(day.fxDate) }}</div>
        <div class="forecast-temp">
          <span class="temp-max">{{ day.tempMax }}°</span> / 
          <span class="temp-min">{{ day.tempMin }}°</span>
        </div>
        <div class="forecast-weather">
          <!-- <img :src="getWeatherIcon(day.iconDay)" :alt="day.textDay" class="weather-icon-small"> -->
          <i :class="'qi-' + day.iconDay"></i>
          <span>{{ day.textDay }}</span>
        </div>
        <div class="forecast-wind">
          {{ day.windDirDay }} {{ day.windScaleDay }}级
        </div>
      </div>
    </div>
    
    <div class="data-source">
      数据来源: {{ dailyForecastData.refer.sources.join('、') }}
      <a :href="dailyForecastData.fxLink" target="_blank" class="more-info">查看更多</a>
    </div>
  </div>
</template>

<script>
import Chart from 'chart.js/auto';
import axios from 'axios';

export default {
  name: 'WeatherForecast',
  props: {
    locationId: {  // 改为只接收locationId
      type: String,
      required: true
    }
  },
  data() {
    return {
      dailyForecastData: {
  "code": "200",
  "updateTime": "2021-11-15T16:35+08:00",
  "fxLink": "http://hfx.link/2ax1",
  "daily": [
    {
      "fxDate": "2021-11-15",
      "sunrise": "06:58",
      "sunset": "16:59",
      "moonrise": "15:16",
      "moonset": "03:40",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "12",
      "tempMin": "-1",
      "iconDay": "101",
      "textDay": "多云",
      "iconNight": "150",
      "textNight": "晴",
      "wind360Day": "45",
      "windDirDay": "东北风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "0",
      "windDirNight": "北风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "65",
      "precip": "0.0",
      "pressure": "1020",
      "vis": "25",
      "cloud": "4",
      "uvIndex": "3"
    },
    {
      "fxDate": "2021-11-16",
      "sunrise": "07:00",
      "sunset": "16:58",
      "moonrise": "15:38",
      "moonset": "04:40",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "13",
      "tempMin": "0",
      "iconDay": "100",
      "textDay": "晴",
      "iconNight": "101",
      "textNight": "多云",
      "wind360Day": "225",
      "windDirDay": "西南风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "225",
      "windDirNight": "西南风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "74",
      "precip": "0.0",
      "pressure": "1016",
      "vis": "25",
      "cloud": "1",
      "uvIndex": "3"
    },
    {
      "fxDate": "2021-11-17",
      "sunrise": "07:01",
      "sunset": "16:57",
      "moonrise": "16:01",
      "moonset": "05:41",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "13",
      "tempMin": "0",
      "iconDay": "100",
      "textDay": "晴",
      "iconNight": "150",
      "textNight": "晴",
      "wind360Day": "225",
      "windDirDay": "西南风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "225",
      "windDirNight": "西南风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "56",
      "precip": "0.0",
      "pressure": "1009",
      "vis": "25",
      "cloud": "0",
      "uvIndex": "3"
    },
    {
      "fxDate": "2021-11-17",
      "sunrise": "07:01",
      "sunset": "16:57",
      "moonrise": "16:01",
      "moonset": "05:41",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "13",
      "tempMin": "0",
      "iconDay": "100",
      "textDay": "晴",
      "iconNight": "150",
      "textNight": "晴",
      "wind360Day": "225",
      "windDirDay": "西南风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "225",
      "windDirNight": "西南风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "56",
      "precip": "0.0",
      "pressure": "1009",
      "vis": "25",
      "cloud": "0",
      "uvIndex": "3"
    },
    {
      "fxDate": "2021-11-17",
      "sunrise": "07:01",
      "sunset": "16:57",
      "moonrise": "16:01",
      "moonset": "05:41",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "13",
      "tempMin": "0",
      "iconDay": "100",
      "textDay": "晴",
      "iconNight": "150",
      "textNight": "晴",
      "wind360Day": "225",
      "windDirDay": "西南风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "225",
      "windDirNight": "西南风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "56",
      "precip": "0.0",
      "pressure": "1009",
      "vis": "25",
      "cloud": "0",
      "uvIndex": "3"
    },
    {
      "fxDate": "2021-11-17",
      "sunrise": "07:01",
      "sunset": "16:57",
      "moonrise": "16:01",
      "moonset": "05:41",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "13",
      "tempMin": "0",
      "iconDay": "100",
      "textDay": "晴",
      "iconNight": "150",
      "textNight": "晴",
      "wind360Day": "225",
      "windDirDay": "西南风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "225",
      "windDirNight": "西南风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "56",
      "precip": "0.0",
      "pressure": "1009",
      "vis": "25",
      "cloud": "0",
      "uvIndex": "3"
    },
    {
      "fxDate": "2021-11-17",
      "sunrise": "07:01",
      "sunset": "16:57",
      "moonrise": "16:01",
      "moonset": "05:41",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "13",
      "tempMin": "0",
      "iconDay": "100",
      "textDay": "晴",
      "iconNight": "150",
      "textNight": "晴",
      "wind360Day": "225",
      "windDirDay": "西南风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "225",
      "windDirNight": "西南风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "56",
      "precip": "0.0",
      "pressure": "1009",
      "vis": "25",
      "cloud": "0",
      "uvIndex": "3"
    },
    {
      "fxDate": "2021-11-17",
      "sunrise": "07:01",
      "sunset": "16:57",
      "moonrise": "16:01",
      "moonset": "05:41",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "13",
      "tempMin": "0",
      "iconDay": "100",
      "textDay": "晴",
      "iconNight": "150",
      "textNight": "晴",
      "wind360Day": "225",
      "windDirDay": "西南风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "225",
      "windDirNight": "西南风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "56",
      "precip": "0.0",
      "pressure": "1009",
      "vis": "25",
      "cloud": "0",
      "uvIndex": "3"
    },
    {
      "fxDate": "2021-11-17",
      "sunrise": "07:01",
      "sunset": "16:57",
      "moonrise": "16:01",
      "moonset": "05:41",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "13",
      "tempMin": "0",
      "iconDay": "100",
      "textDay": "晴",
      "iconNight": "150",
      "textNight": "晴",
      "wind360Day": "225",
      "windDirDay": "西南风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "225",
      "windDirNight": "西南风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "56",
      "precip": "0.0",
      "pressure": "1009",
      "vis": "25",
      "cloud": "0",
      "uvIndex": "3"
    },
    {
      "fxDate": "2021-11-17",
      "sunrise": "07:01",
      "sunset": "16:57",
      "moonrise": "16:01",
      "moonset": "05:41",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "13",
      "tempMin": "0",
      "iconDay": "100",
      "textDay": "晴",
      "iconNight": "150",
      "textNight": "晴",
      "wind360Day": "225",
      "windDirDay": "西南风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "225",
      "windDirNight": "西南风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "56",
      "precip": "0.0",
      "pressure": "1009",
      "vis": "25",
      "cloud": "0",
      "uvIndex": "3"
    },
    {
      "fxDate": "2021-11-17",
      "sunrise": "07:01",
      "sunset": "16:57",
      "moonrise": "16:01",
      "moonset": "05:41",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "13",
      "tempMin": "0",
      "iconDay": "100",
      "textDay": "晴",
      "iconNight": "150",
      "textNight": "晴",
      "wind360Day": "225",
      "windDirDay": "西南风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "225",
      "windDirNight": "西南风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "56",
      "precip": "0.0",
      "pressure": "1009",
      "vis": "25",
      "cloud": "0",
      "uvIndex": "3"
    },
    {
      "fxDate": "2021-11-17",
      "sunrise": "07:01",
      "sunset": "16:57",
      "moonrise": "16:01",
      "moonset": "05:41",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "13",
      "tempMin": "0",
      "iconDay": "100",
      "textDay": "晴",
      "iconNight": "150",
      "textNight": "晴",
      "wind360Day": "225",
      "windDirDay": "西南风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "225",
      "windDirNight": "西南风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "56",
      "precip": "0.0",
      "pressure": "1009",
      "vis": "25",
      "cloud": "0",
      "uvIndex": "3"
    },
    {
      "fxDate": "2021-11-17",
      "sunrise": "07:01",
      "sunset": "16:57",
      "moonrise": "16:01",
      "moonset": "05:41",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "13",
      "tempMin": "0",
      "iconDay": "100",
      "textDay": "晴",
      "iconNight": "150",
      "textNight": "晴",
      "wind360Day": "225",
      "windDirDay": "西南风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "225",
      "windDirNight": "西南风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "56",
      "precip": "0.0",
      "pressure": "1009",
      "vis": "25",
      "cloud": "0",
      "uvIndex": "3"
    },
    {
      "fxDate": "2021-11-17",
      "sunrise": "07:01",
      "sunset": "16:57",
      "moonrise": "16:01",
      "moonset": "05:41",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "13",
      "tempMin": "0",
      "iconDay": "100",
      "textDay": "晴",
      "iconNight": "150",
      "textNight": "晴",
      "wind360Day": "225",
      "windDirDay": "西南风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "225",
      "windDirNight": "西南风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "56",
      "precip": "0.0",
      "pressure": "1009",
      "vis": "25",
      "cloud": "0",
      "uvIndex": "3"
    },
    {
      "fxDate": "2021-11-17",
      "sunrise": "07:01",
      "sunset": "16:57",
      "moonrise": "16:01",
      "moonset": "05:41",
      "moonPhase": "盈凸月",
      "moonPhaseIcon": "803",
      "tempMax": "13",
      "tempMin": "0",
      "iconDay": "100",
      "textDay": "晴",
      "iconNight": "150",
      "textNight": "晴",
      "wind360Day": "225",
      "windDirDay": "西南风",
      "windScaleDay": "1-2",
      "windSpeedDay": "3",
      "wind360Night": "225",
      "windDirNight": "西南风",
      "windScaleNight": "1-2",
      "windSpeedNight": "3",
      "humidity": "56",
      "precip": "0.0",
      "pressure": "1009",
      "vis": "25",
      "cloud": "0",
      "uvIndex": "3"
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
},
      chart: null,
      isLoading: false,
      error: null
    };
  },
  mounted() {
    this.renderChart();
  },
  watch: {
    locationId: {
      immediate: false,  // 初次加载不立即执行
      handler(newVal) {
        if (newVal) {
          this.fetchDailyForecast(newVal);
        }
      }
    }
  },
  methods: {

    async fetchDailyForecast(locationId) {
      this.isLoading = true;
      this.error = null;
      
      try {
        // console.log('send')
        const response = await axios.post(
          `http://${this.$ipLocation}:8080/api/everydayweather`,
          { locationId: locationId }
        );
        
        if (response.data.data.code === "200") {
          // console.log(response.data.data)
          this.dailyForecastData = response.data.data;
          this.renderChart();
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

    formatDate(dateString) {
      const date = new Date(dateString);
      const month = date.getMonth() + 1;
      const day = date.getDate();
      return `${month}月${day}日`;
    },
    formatUpdateTime(timeString) {
      if (!timeString) return '--';
      const date = new Date(timeString);
      return date.toLocaleString('zh-CN', {
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
      }).replace(/\//g, '-');
    },
    getWeatherIcon(iconCode) {
      return `https://a.hecdn.net/img/plugin/190516/icon/c/${iconCode}.png`;
    },
    renderChart() {
  if (!this.dailyForecastData.daily) return;
  
  if (this.chart) {
    this.chart.destroy();
  }

  const ctx = this.$refs.temperatureChart.getContext('2d');
  const forecastDays = this.dailyForecastData.daily.slice(0, 15);
  
  const dates = forecastDays.map(day => this.formatDate(day.fxDate));
  const maxTemps = forecastDays.map(day => parseInt(day.tempMax));
  const minTemps = forecastDays.map(day => parseInt(day.tempMin));

  // 计算温度范围
  const allTemps = [...maxTemps, ...minTemps];
  const minTemp = Math.min(...allTemps);
  const maxTemp = Math.max(...allTemps);
  
  // 设置Y轴缓冲范围（增加20%的上下空间）
  const padding = (maxTemp - minTemp) * 0.2;
  const yMin = Math.floor(minTemp - padding);
  const yMax = Math.ceil(maxTemp + padding);

  // 自定义插件：绘制温度区间虚线
  const verticalLinePlugin = {
    id: 'verticalLine',
    afterDraw(chart) {
      if (chart.tooltip?._active?.length) {
        const ctx = chart.ctx;
        const activePoint = chart.tooltip._active[0];
        const x = activePoint.element.x;
        const yAxis = chart.scales.y;
        
        ctx.save();
        ctx.beginPath();
        ctx.moveTo(x, yAxis.top);
        ctx.lineTo(x, yAxis.bottom);
        ctx.lineWidth = 1;
        ctx.strokeStyle = '#999';
        ctx.setLineDash([5, 3]);
        ctx.stroke();
        ctx.restore();
      }
    }
  };

  this.chart = new Chart(ctx, {
    type: 'line',
    plugins: [verticalLinePlugin],
    data: {
      labels: dates,
      datasets: [
        {
          label: '最高温度 (°C)',
          data: maxTemps,
          borderColor: '#ff6384',
          backgroundColor: 'rgba(255, 99, 132, 0.1)',
          borderWidth: 2,
          tension: 0.3,
          pointBackgroundColor: '#ff6384',
          pointHoverRadius: 6,
          pointHitRadius: 15
        },
        {
          label: '最低温度 (°C)',
          data: minTemps,
          borderColor: '#36a2eb',
          backgroundColor: 'rgba(54, 162, 235, 0.1)',
          borderWidth: 2,
          tension: 0.3,
          pointBackgroundColor: '#36a2eb',
          pointHoverRadius: 6,
          pointHitRadius: 15
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      interaction: {
        mode: 'index',
        intersect: false
      },
      plugins: {
        legend: {
          position: 'top',
          labels: {
            usePointStyle: true,
            padding: 20
          }
        },
        tooltip: {
          enabled: true,
          mode: 'index',
          intersect: false,
          backgroundColor: 'rgba(0,0,0,0.8)',
          titleFont: { size: 14, weight: 'bold' },
          bodyFont: { size: 12 },
          padding: 12,
          callbacks: {
            title: (context) => {
              return forecastDays[context[0].dataIndex].fxDate;
            },
            label: (context) => {
              const dayData = forecastDays[context.dataIndex];
              let label = context.dataset.label || '';
              if (label) {
                label += ': ';
              }
              label += context.raw + '°C';
              
              if (context.datasetIndex === 0) {
                return [
                  label,
                  `最低: ${dayData.tempMin}°C`,
                  `天气: ${dayData.textDay}`,
                  `风力: ${dayData.windDirDay} ${dayData.windScaleDay}级`
                ];
              }
              return label;
            }
          }
        }
      },
      scales: {
        y: {
          min: yMin,  // 设置Y轴最小值
          max: yMax,  // 设置Y轴最大值
          beginAtZero: false,
          title: {
            display: true,
            text: '温度 (°C)'
          },
          grid: {
            color: 'rgba(0, 0, 0, 0.05)'
          },
          ticks: {
            stepSize: 2  // 设置刻度间隔为2度
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
}


  },
  beforeUnmount() {
    if (this.chart) {
      this.chart.destroy();
    }
  }
};
</script>

<style scoped>
.forecast-container {
  background-color: rgba(220, 234, 255);
  margin: auto;
  margin-top: 30px;
  padding: 20px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  max-width: 800px;
}

.forecast-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

h3 {
  margin: 0;
  color: #333;
  font-size: 18px;
  font-weight: 600;
}

.update-time {
  font-size: 12px;
  color: #666;
}

.chart-container {
  height: 300px;
  margin-bottom: 20px;
}

.forecast-details {
  display: flex; /* 改为flex布局 */
  overflow-x: auto; /* 允许横向滚动 */
  gap: 12px;
  margin-top: 20px;
  padding-bottom: 10px; /* 给滚动条留空间 */
  -webkit-overflow-scrolling: touch; /* iOS平滑滚动 */
  scrollbar-width: thin; /* Firefox滚动条样式 */
  white-space: nowrap; /* 防止内容换行 */
}

.forecast-day {
  flex: 0 0 140px; /* 固定宽度，不伸缩 */
  padding: 12px;
  border-radius: 8px;
  background: #f8fafc;
  transition: all 0.3s ease;
  /* 移除原来的grid相关属性 */
}



.forecast-day:hover {
  background: #f1f5f9;
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.forecast-date {
  font-weight: bold;
  margin-bottom: 8px;
  color: #333;
  font-size: 14px;
}

.forecast-temp {
  margin: 8px 0;
  font-size: 15px;
}

.temp-max {
  color: #ff6384;
  font-weight: bold;
}

.temp-min {
  color: #36a2eb;
}

.forecast-weather {
  display: flex;
  align-items: center;
  gap: 6px;
  margin: 8px 0;
  font-size: 13px;
}

.weather-icon-small {
  width: 24px;
  height: 24px;
}

.forecast-wind {
  font-size: 12px;
  color: #666;
}

.data-source {
  margin-top: 20px;
  padding-top: 15px;
  border-top: 1px dashed #eee;
  font-size: 12px;
  color: #888;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.more-info {
  color: #1890ff;
  text-decoration: none;
  font-size: 12px;
}

.more-info:hover {
  text-decoration: underline;
}

@media (max-width: 768px) {
  .forecast-details {
    grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  }
}
</style>
