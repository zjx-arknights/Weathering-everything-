<template>
  <div id="app">
    
    <keep-alive>
    <router-view class="router-view" />
  </keep-alive>
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

.router-view {
  padding: 1rem;
  max-width: 1200px;
  margin: 0 auto;
}

#app {
  margin: 0;
  padding: 0;
  text-align: center;
}
</style>



