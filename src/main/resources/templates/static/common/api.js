// 默认请求域名配置
axios.defaults.baseURl = "/"
// 跨域传递 Cookie 设置
axios.defaults.withCredentials = true
// 默认请求域名配置：
axios.defaults.timeout = 3000
// 请求拦截器（在请求之前进行一些配置，设置全局参数）
axios.interceptors.request.use(config => {
    // 导出时 超时时间设为5min
    if (config.responseType === 'blob') {
        config.timeout = 1000 * 60 * 5
    }
    let token = localStorage.getItem("X-Access-Token");
    if (token) {
        const headerToken = JSON.parse(token);
        config.headers.token = headerToken;
        if (!config.headers['X-Access-Token']) {
            config.headers['X-Access-Token'] = headerToken;
        }
    }
    return config;
}, function (error) {
    return Promise.reject(error);
});
// 响应了拦截器（在响应之后对数据进行一些处理）
axios.interceptors.response.use(response => {
    if (response.data.code === 9999) {
        parent.location.href = '/';
    }
    if (response.request.responseType === 'arraybuffer') {
        return response;
    }
    let token = response.headers['x-access-token'] ? response.headers['x-access-token'] : response.headers['X-Access-Token']
    console.log(token);
    if (token) {
        localStorage.setItem('X-Access-Token', JSON.stringify(token));
    }
    return response.data;
});
