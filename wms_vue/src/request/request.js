import axios from 'axios'
import router from '@/router/router';
// 封装 baseURL
const request = axios.create({
baseURL:"http://localhost:9000",
timeout:5000
})

//reuqest 拦截器
//可以自请求发送前对请求数据做处理，例如统一加token，统一设置请求头等
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=UTF-8';

    const token = localStorage.getItem('token');//从当前缓存获取token
    if(token){
        config.headers['token'] = token;//如果有token就把token设置为请求头
    }
    return config
},error => {
    return Promise.reject(error)
});

//request 拦截器可以在响应后统一处理结果
request.interceptors.response.use(response => {
    let res=response.data;
    if(typeof res === 'string')
    {
        res=res ? JSON.parse(res) : res
    }
    if(res.code === 401)
    {
        // 401 清除token信息并跳转到登录页面
        localStorage.removeItem('token');
        router.push('/login').catch(() => {})
    }
    return res;
},error => {
    console.log('err'+ error)
    return Promise.reject(error)
})
// 向外暴露 request
export default request;