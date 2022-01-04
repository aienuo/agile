// 默认请求域名配置
axios.defaults.baseURl = "/"
// 跨域传递 Cookie 设置
axios.defaults.withCredentials = true
// 默认请求域名配置 5秒：
axios.defaults.timeout = 1000 * 5
// 请求拦截器（在请求之前进行一些配置，设置全局参数）
axios.interceptors.request.use(config => {
    // 导出时 超时时间设为 5分钟
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
    const responseCode = response.data.code;
    const responseType = response.request.responseType;
    let token = response.headers['x-access-token'] ? response.headers['x-access-token'] : response.headers['X-Access-Token'];
    if (responseCode === 9999) {
        //  服务器遇到错误，无法完成请求。服务器异常，无法识别的异常
        parent.location.href = '/';
    }
    if (responseType === 'arraybuffer') {
        // 文件
        return response;
    }
    if (token) {
        localStorage.setItem('X-Access-Token', JSON.stringify(token));
    }
    return response.data;
});
// JS反调试 & 无限DEBUGGER
// setInterval(() => {
//     (function (a) {return (function (a) {return (Function('Function(arguments[0]+"' + a + '")()'))})(a)})('bugger')('de', 0, 0, (0, 0));
// }, 1000);