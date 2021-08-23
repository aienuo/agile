const service = axios.create({
    baseUrl: 'http://localhost:9900',
    timeout: 5000,
    withCredentials: true
});

// 设置全局参数
axios.interceptors.request.use(config => {
    // 导出时 超时时间设为5min
    if (config.responseType === 'blob') {
        config.timeout = 1000 * 60 * 5
    }
    let token = JSON.parse(localStorage.getItem("X-Access-Token"));
    if (token) {
        config.headers.token = token;
        if (!config.headers['X-Access-Token']) {
            config.headers['X-Access-Token'] = token;
        }
    }
    return config;
}, function (error) {
    return Promise.reject(error);
});

axios.interceptors.response.use(response => {
    if (response.data.code === 9999) {
        parent.location.href = '/';
    }
    if (response.request.responseType === 'arraybuffer') {
        return response;
    }
    return response.data;
});
