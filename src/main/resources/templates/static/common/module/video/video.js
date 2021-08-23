new Vue({
    el: '#video',
    // 数据对象
    data() {
        return {
            video:{
                fileName: '',
            },
            // 播放器选项
            playerOptions: {
                // 高度
                height: '360',
                // 可选的播放速度
                playbackRates: [0.5, 1.0, 1.5, 2.0, 3.0],
                // 如果为true,浏览器准备好时开始回放。
                autoplay: false,
                // 默认情况下将会消除任何音频。
                muted: false,
                // 是否视频一结束就重新开始。
                loop: false,
                // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
                preload: 'auto',
                // 语言
                language: 'zh-CN',
                // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
                aspectRatio: '16:9',
                // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
                fluid: true,
                // 播放来源
                sources: [{
                    // 视频格式类型
                    type: "video/mp4",
                    // url地址
                    src: ''
                }],
                // 封面地址
                poster: '',
                // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
                notSupportedMessage: '此视频暂时无法播放，请稍后再试',
                // 控制栏
                controlBar: {
                    // 当前时间和持续时间的分隔符
                    timeDivider: true,
                    // 显示持续时间
                    durationDisplay: true,
                    // 是否显示剩余时间功能
                    remainingTimeDisplay: true,
                    // 是否显示全屏按钮
                    fullscreenToggle: true
                }
            },
        }
    },
    mounted() {
        console.log('this is current player instance object', this.player)
        setTimeout(() => {
            console.log('动态更改选项', this.player)
            // 更改 src
            this.playerOptions.sources[0].src = 'https://cdn.theguardian.tv/webM/2015/07/20/150716YesMen_synd_768k_vp8.webm';
            // 更改 播放来源
            // this.$set(this.playerOptions.sources, 0, {
            //   type: "video/mp4",
            //   src: 'https://cdn.theguardian.tv/webM/2015/07/20/150716YesMen_synd_768k_vp8.webm',
            // });
            // 替换 播放来源数组
            // this.playerOptions.sources = [{
            //   type: "video/mp4",
            //   src: 'https://cdn.theguardian.tv/webM/2015/07/20/150716YesMen_synd_768k_vp8.webm',
            // }]
            this.player.muted(false)
        }, 5000)
    },
    // 计算属性和侦听器
    computed: {
        player() {
            return this.$refs.videoPlayer.player
        }
    },
    // 监听事件
    methods: {
        // 播放回调
        onPlayerPlay(player) {
            console.log('player play!', player);
        },
        // 暂停回调
        onPlayerPause(player) {
            console.log('player pause!', player);
        },
        // 视频播完回调
        onPlayerEnded(player) {
            console.log('player ended!', player)
        },
        // 当播放器在当前播放位置下载数据时触发
        onPlayerLoadeddata(player) {
            console.log('player Loadeddata!', player)
        },
        // DOM元素上的readyState更改导致播放停止
        onPlayerWaiting(player) {
            console.log('player Waiting!', player)
        },
        // 已开始播放回调
        onPlayerPlaying(player) {
            console.log('player Playing!', player)
        },
        // 当前播放位置发生变化时触发
        onPlayerTimeupdate(player) {
            console.log('player Timeupdate!', player.currentTime())
        },
        // 媒体的 readyState 为 HAVE_FUTURE_DATA 或更高
        onPlayerCanplay(player) {
            console.log('player Canplay!', player)
        },
        // 媒体的 readyState 为 HAVE_ENOUGH_DATA 或更高。这意味着可以在不缓冲的情况下播放整个媒体文件。
        onPlayerCanplaythrough(player) {
            console.log('player Canplaythrough!', player)
        },
        // 播放状态改变回调
        playerStateChanged(playerCurrentState) {
            console.log('player current update state', playerCurrentState)
        },
        // 将侦听器绑定到组件的就绪状态。与事件监听器的不同之处在于，如果 ready 事件已经发生，它将立即触发该函数。。
        playerReadied(player) {
            // seek to 10s
            console.log('example player 1 readied', player)
            player.currentTime(10)
            console.log('example 01: the player is readied', player)
        }
    }
});