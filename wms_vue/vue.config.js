const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})


// module.exports = defineConfig({
//   transpileDependencies: true, //启用依赖转译,以便在不同环境中运行
//   devServer: {
//   //代理可以写多个，代理不同的后端地址
//   proxy: {
//   // "/requirement": { //请求前缀 (可以有多组)
//   // target: "http://localhost:80", // 将请求代理到后端的地址（代理地址）
//   // 是否修改origin字段，控制请求体中的host的值（是否将http://localhost:8080修改为http://localhost:80）
//   // changeOrigin: true, // // pathRewrite: {
//   // // "^/requirement": "", // 重写路径，后端接受到的将不带/requirement
//   // // }, // }, 
//   "/": {
//   ws:false, // 是否支持webSocket代理
//   target: "http://localhost:9000",
//   changeOrigin: true, }, }, }
//   });