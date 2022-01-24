// 自定义无限极菜单组件
Vue.component('agile-menu-tree', {
    template: '<div class="menuTreeList"><template v-for="(item, index) in menuTreeList"><!-- 有子类型 --><el-submenu v-if="item.children" :key="index" :data="item" :index="item.name + \'_\' + index"><template slot="title"><i :class="item.icon"></i><span slot="title">{{item.name}}</span></template><agile-menu-tree :menuTreeList="item.children"></agile-menu-tree></el-submenu><!-- 无子类型 --><el-menu-item v-else :key="index" :data="item" :index="item.name + \'_\' + index" :route="item.url" @click="openTable(item)"><template slot="title"><i :class="item.icon"></i><span slot="title">{{item.name}}</span></template></el-menu-item></template></div>',
    props: ['menuTreeList'],
    data() {return {}},
    mounted() {},
    methods: {
        // 子组件调父组件
        openTable(item) {
            this.$emit('openTableParent', item);
            // 上面不行下面行
            home.addTab(item);
        }
    },
    created() {}
});

const home = new Vue({
    el: '#home',
    components: {},
    data() {
        // 此处即表单发送之前验证
        let validateNewPassword = (rule, value, callback) => {
            if (value === this.passwordForm.oldPassword) {
                callback(new Error('新密码不能与原密码相同！'));
            } else {
                callback();
            }
        };
        let validateConfirmPassword = (rule, value, callback) => {
            if (value !== this.passwordForm.newPassword) {
                callback(new Error('两次输入的密码不一致！'));
            } else {
                callback();
            }
        };
        return {
            homeName: "主页哟",
            isCollapse: false,
            activePath: '',
            userCenterDialog: false,
            editableTabsValue: '',
            editableTabs: [],
            user: {},
            roleList: [],
            menuTreeList: [],
            buttonList: [],
            sexItem: [],
            avatar: '',
            actionUrl: '/sys/common/upload',
            userCenterModel: 'first',
            updateForm: {
                username: '',
                realname: '',
                identityNumber: '',
                email: '',
                phone: '',
                avatar: '',
                birthday: '',
                sex: '',
                age: ''
            },
            passwordForm: {
                username: '',
                oldPassword: '',
                newPassword: '',
                confirmPassword: ''
            },
            // 校验规则
            rules: {
                realname: [
                    {required: true, message: '请输入真实姓名', trigger: 'blur'},
                    {min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur'},
                ],
                identityNumber: [
                    {required: true, message: '请输入身份证号', trigger: 'blur'},
                    {type: 'string', message: '身份证号格式不正确', pattern: /(^\d{8}(0\d|10|11|12)([0-2]\d|30|31)\d{3}$)|(^\d{6}(18|19|20)\d{2}(0\d|10|11|12)([0-2]\d|30|31)\d{3}(\d|X|x)$)/, trigger: ['blur', 'change']}
                ],
                email: [
                    {required: true, message: '请输入电子邮箱', trigger: 'blur'},
                    {type: 'email', message: '电子邮箱格式不正确', trigger: ['blur', 'change']}
                ],
                phone: [
                    {required: true, message: '请输入手机号码', trigger: 'blur'},
                    {type: 'string', message: '手机号码格式不正确', pattern: /^(?:(?:\+|00)86)?1(?:(?:3[\d])|(?:4[5-7|9])|(?:5[0-3|5-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\d])|(?:9[1|8|9]))\d{8}$/, trigger: ['blur', 'change']}
                ],
                oldPassword: [
                    {required: true, message: '请输入原登录密码', trigger: 'blur'}
                ],
                newPassword: [
                    {required: true, message: '请输入登录密码', trigger: 'blur'},
                    {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'},
                    {validator: validateNewPassword, trigger: ['blur', 'change']}
                ],
                confirmPassword: [
                    {required: true, message: '请输入登录密码', trigger: 'blur'},
                    {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'},
                    {validator: validateConfirmPassword, trigger: ['blur', 'change']}
                ]
            },
        }
    },
    mounted() {
        // 初始化
        this.initWebSocket();
    },
    methods: {
        // 点击按钮，切换菜单的折叠与展开
        toggleCollapse() {
            this.isCollapse = !this.isCollapse
        },
        // 处理命令
        handleCommand(cmd) {
            if (cmd === 'logout') {
                this.$confirm('确定退出系统？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    modal: 'false',
                    type: 'warning'
                }).then(() => {
                    localStorage.clear();
                    parent.location.href = '/';
                    this.$message({
                        type: 'success',
                        message: '退出成功!'
                    })
                    setTimeout(() => {
                        // 强制刷新
                        location.reload()
                    }, 1000)
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消退出'
                    })
                });
            } else if (cmd === 'userCenter') {
                this.userCenterDialog = true;
                const user = JSON.parse(localStorage.getItem("X-Data-User"));
                this.passwordForm.username = user.username;
                this.updateForm = user;
            }
        },
        // 开启 Table
        addTab(table) {
            let isExist = false;
            let tabs = this.editableTabs;
            tabs.forEach((tab, index) => {
                if (tab.title === table.name) {
                    isExist = true;
                    return;
                }
            });
            if (isExist) {
                this.editableTabsValue = table.url;
                return;
            }
            tabs.push({
                title: table.name,
                name: table.url,
                content: table.url
            });
            this.editableTabsValue = table.url;
        },
        // 关闭Table
        removeTab(tabName) {
            let tabs = this.editableTabs;
            let activeName = this.editableTabsValue;
            if (activeName === tabName) {
                tabs.forEach((tab, index) => {
                    if (tab.name === tabName) {
                        let nextTab = tabs[index + 1] || tabs[index - 1];
                        if (nextTab) {
                            activeName = nextTab.name;
                        }
                    }
                });
            }
            this.editableTabsValue = activeName;
            this.editableTabs = tabs.filter(tab => tab.name !== tabName);
        },
        handleClick(tab, event) {
        },
        // 处理头像成功
        handleAvatarSuccess(idx, res, file, name) {
            if (res.code === 6666) {
                let data = res.data;
                this.updateForm.avatar = data[data.length - 1].url;
            }
        },
        // 上传头像前
        beforeAvatarUpload(file) {
            const isLt2M = file.size / 1024 / 1024 < 2;
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过2MB!');
            }
            return isLt2M;
        },
        // 更新用户信息
        updateUser() {
            this.$refs.updateForm.validate((valid) => {
                if (valid) {
                    let updateParam = this.updateForm;
                    axios.put('/update', JSON.parse(JSON.stringify(updateParam)))
                        .then((res) => {
                            if (res.code === 6666) {
                                const user = JSON.parse(localStorage.getItem("X-Data-User"));
                                user.realname = updateParam.realname;
                                user.identityNumber = updateParam.identityNumber;
                                user.email = updateParam.email;
                                user.phone = updateParam.phone;
                                user.avatar = updateParam.avatar;
                                user.birthday = updateParam.birthday;
                                user.sex = updateParam.sex;
                                user.age = updateParam.age;
                                localStorage.setItem('X-Data-User', JSON.stringify(user));
                                this.$message({
                                    type: 'success',
                                    message: '修改成功'
                                });
                                this.userCenterDialog = false;
                            } else {
                                this.$message.error(res.message);
                            }
                        });
                } else {
                    return false;
                }
            });
        },
        /// 更新密码
        updatePassword() {
            this.$refs.passwordForm.validate((valid) => {
                if (valid) {
                    let passwordForm = this.passwordForm;
                    axios.put('/password', JSON.parse(JSON.stringify(passwordForm)))
                        .then((res) => {
                            if (res.code === 6666) {
                                this.$message({
                                    type: 'success',
                                    message: '修改成功'
                                });
                                this.userCenterDialog = false;
                            } else {
                                this.$message.error(res.message);
                            }
                        });
                } else {
                    return false;
                }
            });
        },
        // 初始化 WebSocket
        initWebSocket: function () {
            if (typeof (WebSocket) === "undefined") {
                this.$message.error("浏览器不支持 WebSocket");
            } else {
                // 实例化socket
                const ipPort = document.location ? document.location.host : "localhost:80";
                const url = "ws://" + ipPort + "/websocket/" + this.user.username;
                console.log(url);
                this.socket = new WebSocket(url)
                // 监听socket连接
                this.socket.onopen = this.open
                // 监听socket错误信息
                this.socket.onerror = this.error
                // 监听socket消息
                this.socket.onmessage = this.getMessage
            }
        },
        open: function () {
            this.$message("WebSocket 连接成功");
        },
        error: function () {
            this.$message.error("WebSocket 发生了错误事件");
        },
        getMessage: function (messageEvent) {
            console.log(messageEvent.data);
            const data = JSON.parse(messageEvent.data);
            console.log(data);
            this.$notify({
                title: data.title,
                message: data.message,
                dangerouslyUseHTMLString: data.html,
                type: data.type,
                duration: data.duration,
                position: data.position,
                offset: 55,
            });
        },
        send: function () {
            const messageData = {
                sender: this.user.username,
                receiver: this.user.username,
                title: '给你一个惊喜',
                message: '<strong>这是 <a href="http://www.baidu.com">一个惊喜</a></strong>',
                html: true,
                type: 'warning',
                duration: 9999,
                position: 'top-right',
            };
            this.socket.send(JSON.stringify(messageData))
        },
        close: function () {
            this.socket.close();
        }
    },
    destroyed() {
        // 销毁监听
        this.socket.onclose = this.close
    },
    created() {
        if (localStorage.getItem("X-Access-Token")) {
            this.user = JSON.parse(localStorage.getItem("X-Data-User"));
            this.avatar = this.user.avatar;
            this.activePath = window.sessionStorage.getItem('activePath');
            axios.get('/info/' + this.user.username)
                .then((res) => {
                    if (res.code === 6666) {
                        this.roleList = res.data.roleList;
                        localStorage.setItem('X-Data-Role-List', JSON.stringify(this.roleList));
                        res.data.buttonList.forEach(button => {
                            this.buttonList.push(button.url)
                        });
                        localStorage.setItem('X-Data-Buttons-List', JSON.stringify(this.buttonList));
                        this.menuTreeList = res.data.menuTreeList;
                        localStorage.setItem('X-Data-Menu-Tree', JSON.stringify(this.menuTreeList));
                        localStorage.setItem('X-Data-Dict-List', JSON.stringify(res.data.dictList));
                        this.sexItem = res.data.dictList.find(dict => dict.dictCode === "sex").itemList;
                    } else {
                        this.$message.error(res.message);
                    }
                });
        } else {
            localStorage.clear();
            parent.location.href = '/';
        }
    }
});