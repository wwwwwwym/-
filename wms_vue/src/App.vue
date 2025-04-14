<template>
  <div id="app">
<!-- <my_main></my_main> -->
<router-view></router-view> 
  </div>
</template>

<script>
// import my_main from './components/main.vue'


export default {
  name: 'App',
  components: {
    // my_main
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  position:absolute;
  top:0;
  left:0;
  width: 100%;
  height: 100%;
}
</style>
