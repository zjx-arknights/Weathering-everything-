<template>
    <div class="weather-warning-container">
      <!-- 标题和更新时间 -->
      <div class="header">
        <h2>天气预警信息</h2>
        <div class="update-time">
          更新时间: {{ formatDateTime(data.updateTime) }}
        </div>
      </div>
  
      <!-- 预警信息列表 -->
      <div class="warning-list">
        <div 
          v-for="(item, index) in data.warning" 
          :key="item.id"
          class="warning-item"
          :class="getSeverityClass(item.severityColor)"
          @click="showDetails(index)"
        >
          <div class="warning-header">
            <div class="warning-level">{{ item.typeName }} {{ item.title }}</div>
            <div class="warning-time">
              {{ formatDateTime(item.pubTime) }} 发布
            </div>
          </div>
          
          <div class="warning-body">
            <div class="warning-sender">发布单位: {{ item.sender }}</div>
            <div class="warning-period">
              生效时间: {{ formatDateTime(item.startTime) }} 至 {{ formatDateTime(item.endTime) }}
            </div>
          </div>
          
          <!-- 详细信息 -->
          <div class="warning-details" v-if="expandedIndex === index">
            <div class="warning-text">{{ item.text }}</div>
            <a 
              v-if="data.fxLink" 
              :href="data.fxLink" 
              target="_blank" 
              class="more-link"
            >
              查看更多详情 &raquo;
            </a>
          </div>
        </div>
      </div>
  
      <!-- 模拟数据/真实数据切换 -->
      <div class="data-source-toggle">
        <button @click="toggleDataSource">
          {{ useMockData ? '切换到真实数据' : '使用模拟数据' }}
        </button>
      </div>
  
      <!-- 加载状态 -->
      <div v-if="loading" class="loading">
        <div class="spinner"></div>
        正在获取预警信息...
      </div>
  
      <!-- 错误信息 -->
      <div v-if="error" class="error-message">
        {{ error }}
        <button @click="fetchData">重试</button>
      </div>
  
      <!-- 无数据提示 -->
      <div v-if="!loading && !error && (!data.warning || data.warning.length === 0)" class="no-data">
        当前没有天气预警信息
      </div>
    </div>
  </template>
  
  <script>
import axios from 'axios';

  // 模拟数据
  const mockData = {
    "code": "200",
    "updateTime": "2023-04-03T14:20+08:00",
    "fxLink": "https://www.qweather.com/severe-weather/shanghai-101020100.html",
    "warning": [
      {
        "id": "10102010020230403103000500681616",
        "sender": "上海中心气象台",
        "pubTime": "2023-04-03T10:30+08:00",
        "title": "上海中心气象台发布大风蓝色预警[Ⅳ级/一般]",
        "startTime": "2023-04-03T10:30+08:00",
        "endTime": "2023-04-04T10:30+08:00",
        "status": "active",
        "level": "",
        "severity": "Minor",
        "severityColor": "Blue",
        "type": "1006",
        "typeName": "大风",
        "urgency": "",
        "certainty": "",
        "text": "上海中心气象台2023年04月03日10时30分发布大风蓝色预警[Ⅳ级/一般]：受江淮气旋影响，预计明天傍晚以前本市大部地区将出现6级阵风7-8级的东南大风，沿江沿海地区7级阵风8-9级，请注意防范大风对高空作业、交通出行、设施农业等的不利影响。",
        "related": ""
      },
      {
        "id": "10102010020230403153000500681617",
        "sender": "上海中心气象台",
        "pubTime": "2023-04-03T15:30+08:00",
        "title": "上海中心气象台发布雷电黄色预警[Ⅲ级/较重]",
        "startTime": "2023-04-03T16:00+08:00",
        "endTime": "2023-04-03T20:00+08:00",
        "status": "active",
        "level": "",
        "severity": "Moderate",
        "severityColor": "Yellow",
        "type": "1003",
        "typeName": "雷电",
        "urgency": "",
        "certainty": "",
        "text": "上海中心气象台2023年04月03日15时30分发布雷电黄色预警[Ⅲ级/较重]：预计未来6小时内本市大部地区将发生雷电活动，可能会造成雷电灾害事故，并伴有1小时20-40毫米的短时强降水和7-9级雷雨大风，请注意防范。",
        "related": ""
      }
    ],
    "refer": {
      "sources": [
        "12379"
      ],
      "license": [
        "QWeather Developers License"
      ]
    }
  };
  
  export default {
    name: 'WeatherWarning',
    props: {
      locationId: {  // 改为只接收locationId
      type: String,
      required: true
    },
      // 是否自动刷新
      autoRefresh: {
        type: Boolean,
        default: true
      },
      // 刷新间隔(分钟)
      refreshInterval: {
        type: Number,
        default: 30
      },
      // 初始是否使用模拟数据
      initialUseMockData: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        data: {
          warning: [],
          updateTime: null,
          fxLink: ''
        },
        loading: false,
        error: null,
        expandedIndex: 0, // 默认展开第一个预警
        refreshTimer: null,
        useMockData: this.initialUseMockData
      }
    },
    created() {
      // 初始化时加载数据
      if (this.useMockData) {
        this.loadMockData();
      } else {
        this.fetchData();
      }
    },
    mounted() {
    //   if (this.autoRefresh && !this.useMockData) {
    //     this.setupRefresh();
    //   }
    },
    beforeDestroy() {
      if (this.refreshTimer) {
        clearTimeout(this.refreshTimer);
      }
    },
    methods: {
        async fetchData(locationId) {
  this.loading = true;
  this.error = null;
  
  try {
    console.log('正在获取预警数据，locationId:', locationId);
    const response = await axios.post(
      `http://${this.$ipLocation}:8080/api/warning`,
      { locationId: locationId }
    );
    
    // axios 不需要检查 response.ok，直接检查返回的数据
    if (response.data.data.code !== '200') {
      throw new Error(`API返回错误: ${response.data.data.code}`);
    }
    
    this.data = response.data.data;
    // 默认展开第一个预警
    if (response.data.data.warning && response.data.data.warning.length > 0) {
      this.expandedIndex = 0;
    }
  } catch (err) {
    console.error('获取天气预警失败:', err);
    // this.error = `获取天气预警信息失败: ${err.message}`;
  } finally {
    this.loading = false;
  }
},

      
      loadMockData() {
        this.loading = true;
        setTimeout(() => {
          this.data = mockData;
          if (mockData.warning && mockData.warning.length > 0) {
            this.expandedIndex = 0;
          }
          this.loading = false;
        }, 500); // 模拟网络延迟
      },
      
      toggleDataSource() {
        this.useMockData = !this.useMockData;
        if (this.useMockData) {
          this.loadMockData();
          if (this.refreshTimer) {
            clearTimeout(this.refreshTimer);
          }
        } else {
          this.fetchData();
          this.setupRefresh();

        }
      },
      
      setupRefresh() {
        if (this.refreshTimer) {
          clearTimeout(this.refreshTimer);
        }
        
        this.refreshTimer = setTimeout(() => {
          this.fetchData();
          this.setupRefresh();
        }, this.refreshInterval * 60 * 1000);
      },
      
      showDetails(index) {
        this.expandedIndex = this.expandedIndex === index ? -1 : index;
      },
      
      formatDateTime(dateTimeStr) {
        if (!dateTimeStr) return '';
        try {
          const date = new Date(dateTimeStr);
          return date.toLocaleString('zh-CN', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
          }).replace(/\//g, '-');
        } catch (e) {
          return dateTimeStr;
        }
      },
      
      getSeverityClass(severityColor) {
        if (!severityColor) return '';
        return `severity-${severityColor.toLowerCase()}`;
      }
    },
  watch: {
    locationId: {
      immediate: false,  // 初次加载不立即执行
      handler(newVal) {
        if (newVal) {
            console.log("aaa"+newVal)
          this.fetchData(newVal);
          this.setupRefresh();
        }
      }
    }
  },

  }
  </script>
  
  <style scoped>
  .weather-warning-container {
    font-family: 'Microsoft YaHei', Arial, sans-serif;
    max-width: 800px;
    margin: 0 auto;
    margin-top: 50px;
    padding: 20px;
    background-color: #f5f7fa;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  }
  
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 1px solid #e1e4e8;
  }
  
  .header h2 {
    margin: 0;
    color: #333;
    font-size: 22px;
  }
  
  .update-time {
    color: #666;
    font-size: 14px;
  }
  
  .warning-list {
    margin-top: 15px;
  }
  
  .warning-item {
    margin-bottom: 15px;
    border-radius: 6px;
    overflow: hidden;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    transition: all 0.3s ease;
  }
  
  .warning-item:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  .warning-header {
    padding: 12px 15px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .warning-level {
    font-weight: bold;
    font-size: 16px;
  }
  
  .warning-time {
    font-size: 13px;
    color: #666;
  }
  
  .warning-body {
    padding: 10px 15px;
    background-color: rgba(255, 255, 255, 0.7);
    font-size: 14px;
    display: flex;
    justify-content: space-between;
  }
  
  .warning-details {
    padding: 15px;
    background-color: white;
    border-top: 1px solid #eee;
    font-size: 14px;
    line-height: 1.6;
  }
  
  .warning-text {
    white-space: pre-line;
    margin-bottom: 10px;
  }
  
  .more-link {
    color: #1890ff;
    text-decoration: none;
    display: inline-block;
    margin-top: 8px;
  }
  
  .more-link:hover {
    text-decoration: underline;
  }
  
  .data-source-toggle {
    text-align: center;
    margin-top: 20px;
  }
  
  .data-source-toggle button {
    padding: 8px 16px;
    background-color: #1890ff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
  }
  
  .data-source-toggle button:hover {
    background-color: #40a9ff;
  }
  
  /* 不同严重级别的样式 */
  .severity-blue {
    background-color: #e6f7ff;
    border-left: 4px solid #1890ff;
  }
  
  .severity-green {
    background-color: #f6ffed;
    border-left: 4px solid #52c41a;
  }
  
  .severity-yellow {
    background-color: #fffbe6;
    border-left: 4px solid #faad14;
  }
  
  .severity-orange {
    background-color: #fff7e6;
    border-left: 4px solid #fa8c16;
  }
  
  .severity-red {
    background-color: #fff1f0;
    border-left: 4px solid #f5222d;
  }
  
  /* 加载状态 */
  .loading {
    text-align: center;
    padding: 20px;
    color: #666;
  }
  
  .spinner {
    display: inline-block;
    width: 20px;
    height: 20px;
    border: 3px solid rgba(0, 0, 0, 0.1);
    border-radius: 50%;
    border-top-color: #1890ff;
    animation: spin 1s ease-in-out infinite;
    margin-right: 10px;
    vertical-align: middle;
  }
  
  @keyframes spin {
    to { transform: rotate(360deg); }
  }
  
  /* 错误信息 */
  .error-message {
    padding: 15px;
    background-color: #fff1f0;
    color: #f5222d;
    border-radius: 4px;
    text-align: center;
  }
  
  .error-message button {
    margin-left: 10px;
    padding: 4px 10px;
    background-color: #f5222d;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .no-data {
    text-align: center;
    padding: 20px;
    color: #666;
    font-style: italic;
  }
  </style>
  