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

    let user= JSON.parse(localStorage.getItem("user") ||'{}');//从当前缓存获取token
    config.headers['token'] = user.token;//如果有token就把token设置为请求头
    return config
},error => {
    return Promise.reject(error)
});

//request 拦截器可以在响应后统一处理结果
request.interceptors.response.use(response => {
    console.log(response);
    let res=response.data;
    if(typeof res === 'string')
    {
        res=res ? JSON.parse(res) : res
    }
    if(res.code === 1 && (res.msg === 'token无效,请重新登录' || res.msg === 'token验证失败,请重新登录'))
    // if(res.code === 401)
    {
        // 401 跳转到登录页面
        localStorage.removeItem('user');
        router.push('/login')
    }
    return res;
},error => {
    console.log('err'+ error)
    return Promise.reject(error)
})
// 向外暴露 request
export default request;