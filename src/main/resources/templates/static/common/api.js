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
    let token = sessionStorage.getItem("X-Access-Token");
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
    if (responseCode === 9998) {
        //  Token 过期，清除所有缓存
        sessionStorage.clear();
        parent.location.href = '/';
    }
    // 文件
    if (responseType === 'arraybuffer' || responseType === 'blob') {
        // 内容类型
        const contentType = response.headers['content-type'] ? response.headers['content-type'] : response.headers['Content-Type'];
        // 内容处置
        const contentDisposition = response.headers['content-disposition'] ? response.headers['content-disposition'] : response.headers['Content-Disposition'];
        // 文件名
        let filename = new Date().getTime();
        if (contentDisposition){
            const name = contentDisposition.split(";")[1];
            if (name){
                filename = filename + name.replace("filename=", "");
            }
        }
        let downloadElement = document.createElement('a');
        // 将二进制的数据转为 Blob 对象 并 创建下载的链接
        let href = window.URL.createObjectURL(new Blob([response.data], {type: contentType}));
        downloadElement.href = href;
        // 下载后文件名
        downloadElement.download = decodeURI(filename);
        document.body.appendChild(downloadElement);
        // 点击下载
        downloadElement.click();
        // 下载完成移除元素
        document.body.removeChild(downloadElement);
        // 释放掉blob对象
        window.URL.revokeObjectURL(href)
    }
    // 设置 Token
    if (token) {
        sessionStorage.setItem('X-Access-Token', JSON.stringify(token));
    }
    return response.data;
});
// JS反调试 & 无限DEBUGGER
// setInterval(() => {
//     (function (a) {return (function (a) {return (Function('Function(arguments[0]+"' + a + '")()'))})(a)})('bugger')('de', 0, 0, (0, 0));
// }, 1000);