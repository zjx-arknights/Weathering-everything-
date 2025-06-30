<template>
  <div class="deepseek-chat">
    <div class="chat-container">
      <div ref="messagesContainer" class="messages">
        <div v-for="(message, index) in messages" :key="index" :class="['message', message.type]">
          <div v-if="message.type === 'assistant' && message.isStreaming" class="streaming-content" v-html="formatStreamingContent(message)">
          </div>
          <div v-else class="formatted-text" v-html="formatText(message.text)">
          </div>
        </div>
        <div v-if="loading" class="message assistant loading">
          思考中<span class="ellipsis"></span>
        </div>
      </div>
      
      <div class="input-area">
        <input
          v-model="userInput"
          @keyup.enter="sendMessage"
          placeholder="输入你的问题..."
          :disabled="loading"
        />
        <button @click="sendMessage" :disabled="loading || !userInput.trim()">
          {{ loading ? '生成中...' : '发送' }}
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DeepSeekChat',
  data() {
    return {
      userInput: '',
      messages: [],
      loading: false,
      error: null,
      currentStreamingMessage: null,
      abortController: null
    };
  },
  methods: {
    async sendMessage() {
      const question = this.userInput.trim();
      if (!question || this.loading) return;
      
      // 添加用户消息
      this.messages.push({
        type: 'user',
        text: question
      });
      
      this.userInput = '';
      this.loading = true;
      this.error = null;
      
      // 创建新的AbortController用于取消请求
      this.abortController = new AbortController();
      
      try {
        // 添加初始AI消息用于流式更新
        this.currentStreamingMessage = {
          type: 'assistant',
          text: '',
          tokens: [],
          isStreaming: true
        };
        this.messages.push(this.currentStreamingMessage);
        
        // 调用流式API
        const response = await fetch(`http://${this.$ipLocation}:8080/ai/chat`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ content: question }),
          signal: this.abortController.signal
        });
        
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const reader = response.body.getReader();
        const decoder = new TextDecoder();
        let done = false;
        let fullText = '';
        
        while (!done) {
          const { value, done: readerDone } = await reader.read();
          done = readerDone;
          
          if (value) {
            const chunk = decoder.decode(value, { stream: true });
            fullText += chunk;
            this.currentStreamingMessage.tokens = [fullText]; // 保存完整文本
            this.scrollToBottom();
          }
        }
        
        // 流式传输完成
        this.currentStreamingMessage.isStreaming = false;
        this.currentStreamingMessage.text = fullText;
      } catch (err) {
        if (err.name !== 'AbortError') {
          console.error('流式请求失败:', err);
          this.error = '获取回答时出错，请稍后再试';
          this.messages.push({
            type: 'assistant error',
            text: this.error
          });
        }
      } finally {
        this.loading = false;
        this.currentStreamingMessage = null;
        this.abortController = null;
        this.scrollToBottom();
      }
    },
    
    // 格式化文本，保留原始空格和换行
    formatText(text) {
      if (!text) return '';
      return text
        .replace(/ /g, '&nbsp;')
        .replace(/\n/g, '<br>')
        .replace(/\t/g, '&nbsp;&nbsp;&nbsp;&nbsp;');
    },
    
    // 格式化流式内容
    formatStreamingContent(message) {
      if (!message.tokens || message.tokens.length === 0) return '';
      const fullText = message.tokens[0] || '';
      return this.formatText(fullText) + '<span class="cursor">|</span>';
    },
    
    scrollToBottom() {
      this.$nextTick(() => {
        const container = this.$refs.messagesContainer;
        container.scrollTop = container.scrollHeight;
      });
    },
    
    cancelStream() {
      if (this.abortController) {
        this.abortController.abort();
      }
    }
  },
  
  beforeDestroy() {
    this.cancelStream();
  }
};
</script>

<style scoped>
.deepseek-chat {
  max-width: 800px;
  margin: 0 auto;
  font-family: Arial, sans-serif;
}

.chat-container {
  display: flex;
  flex-direction: column;
  height: 500px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
}

.messages {
  flex: 1;
  padding: 16px;
  overflow-y: auto;
  background-color: #f9f9f9;
}

.message {
  margin-bottom: 12px;
  padding: 10px 16px;
  border-radius: 18px;
  max-width: 80%;
  word-wrap: break-word;
  line-height: 1.5;
}

.message.user {
  background-color: #e3f2fd;
  margin-left: auto;
  border-bottom-right-radius: 4px;
}

.message.assistant {
  background-color: #f1f1f1;
  margin-right: auto;
  border-bottom-left-radius: 4px;
}

.message.error {
  background-color: #ffebee;
  color: #d32f2f;
}

.message.loading {
  color: #666;
  font-style: italic;
}

.streaming-content,
.formatted-text {
  white-space: pre-wrap;
  word-break: break-word;
}

.cursor {
  animation: blink 1s infinite;
  margin-left: 2px;
}

@keyframes blink {
  0%, 100% { opacity: 1; }
  50% { opacity: 0; }
}

.ellipsis::after {
  content: '...';
  animation: dots 1.5s steps(5, end) infinite;
}

@keyframes dots {
  0%, 20% { content: '.'; }
  40% { content: '..'; }
  60%, 100% { content: '...'; }
}

.input-area {
  display: flex;
  padding: 12px;
  border-top: 1px solid #e0e0e0;
  background-color: white;
}

.input-area input {
  flex: 1;
  padding: 10px 16px;
  border: 1px solid #e0e0e0;
  border-radius: 20px;
  outline: none;
  font-size: 14px;
}

.input-area input:focus {
  border-color: #1976d2;
}

.input-area button {
  margin-left: 8px;
  padding: 10px 20px;
  background-color: #1976d2;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-size: 14px;
  min-width: 80px;
}

.input-area button:disabled {
  background-color: #b0bec5;
  cursor: not-allowed;
}
</style>
