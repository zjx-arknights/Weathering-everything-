<template>
  <div class="container">
    <div class="language-selector">
      <select v-model="sourceLanguage" class="language-select">
        <option value="zh">中文</option>
        <option value="en">英文</option>
        <option value="jp">日语</option>
      </select>
      <span class="language-arrow">→</span>
      <select v-model="targetLanguage" class="language-select">
        <option value="en">英文</option>
        <option value="zh">中文</option>
        <option value="jp">日语</option>
      </select>
    </div>

    <div class="input-area">
      <textarea 
        v-model="inputText" 
        :placeholder="getPlaceholder()" 
        class="textarea"
      ></textarea>
    </div>

    <button @click="translateText" :disabled="isTranslating" class="translate-btn">
      {{ isTranslating ? '翻译中...' : '翻译' }}
    </button>

    <div v-if="isTranslating" class="loading">正在翻译，请稍候...</div>

    <div v-if="error" class="error">{{ error }}</div>

    <div v-if="translatedText" class="result-container">
      <h2 class="result-title">翻译结果：</h2>
      <p class="result-text">{{ translatedText }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      inputText: '',  // 用户输入的文本
      translatedText: '',  // 翻译结果
      isTranslating: false,  // 是否正在翻译
      error: '',  // 错误信息
      sourceLanguage: 'zh',  // 源语言
      targetLanguage: 'en',  // 目标语言
    };
  },
  methods: {
    getPlaceholder() {
      const languageMap = {
        zh: '请输入要翻译的中文内容...',
        en: 'Please enter the English text to translate...',
        ja: '翻訳したい日本語の内容を入力してください...'
      };
      return languageMap[this.sourceLanguage] || '请输入要翻译的内容...';
    },
    async translateText() {
      if (!this.inputText.trim()) {
        this.showError(this.sourceLanguage === 'zh' ? '请输入要翻译的内容' : 
                       this.sourceLanguage === 'en' ? 'Please enter text to translate' :
                       '翻訳するテキストを入力してください');
        return;
      }

      this.clearError();
      this.clearResult();
      this.isTranslating = true;

      try {
        const response = await axios.post('http://zhoujx.com:11454/api/translate', {
          text: this.inputText,
          from: this.sourceLanguage,
          to: this.targetLanguage,
        }, {
          headers: {
            'Content-Type': 'application/json',
          }
        });

        if (response.status === 200 && response.data.result) {
          this.translatedText = response.data.result;
        } else {
          this.showError(this.sourceLanguage === 'zh' ? '未获取到翻译结果' : 
                         this.sourceLanguage === 'en' ? 'Failed to get translation' :
                         '翻訳結果を取得できませんでした');
        }
      } catch (err) {
        this.showError(this.sourceLanguage === 'zh' ? '翻译失败，请稍后再试' : 
                       this.sourceLanguage === 'en' ? 'Translation failed, please try again later' :
                       '翻訳に失敗しました。後でもう一度お試しください');
        console.error('翻译错误:', err);
      } finally {
        this.isTranslating = false;
      }
    },
    showError(message) {
      this.error = message;
    },
    clearError() {
      this.error = '';
    },
    clearResult() {
      this.translatedText = '';
    },
  },
};
</script>

<style scoped>
.container {
  font-family: Arial, sans-serif;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f5f7fa;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.language-selector {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  gap: 10px;
}

.language-select {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
  background-color: white;
}

.language-arrow {
  font-size: 18px;
  color: #666;
}

.input-area {
  margin-bottom: 20px;
}

.textarea {
  width: 100%;
  height: 120px;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 16px;
  box-sizing: border-box;
  resize: vertical;
}

.translate-btn {
  background-color: #1890ff;
  color: white;
  border: none;
  padding: 12px 24px;
  font-size: 16px;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s;
  display: block;
  margin: 0 auto;
}

.translate-btn:hover {
  background-color: #40a9ff;
}

.translate-btn:disabled {
  background-color: #d9d9d9;
  cursor: not-allowed;
}

.loading {
  color: #1890ff;
  text-align: center;
  margin: 20px 0;
}

.error {
  color: #f5222d;
  margin: 20px 0;
  padding: 10px;
  background-color: #fff1f0;
  border-radius: 4px;
  border-left: 4px solid #f5222d;
}

.result-container {
  margin-top: 30px;
  padding: 20px;
  background-color: #fafafa;
  border-radius: 6px;
  border-left: 4px solid #1890ff;
}

.result-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}

.result-text {
  font-size: 16px;
  line-height: 1.6;
}
</style>