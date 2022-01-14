new Vue({
    el: '#user',
    data() {
        // 此处即表单发送之前验证
        let validateConfirmPassword = (rule, value, callback) => {
            if (value !== this.passwordResetForm.newPassword) {
                callback(new Error('两次输入的密码不一致！'));
            } else {
                callback();
            }
        };
        return {
            currentPage: 1,
            pageSize: 10,
            total: 0,
            sortFieldList: [],
            multipleSelection: [],
            tableData: [],
            insertDialog: false,
            updateDialog: false,
            detailDialog: false,
            resetDialog: false,
            button: {
                insert: false,
                freeze: false,
                unFreeze: false,
                reset: false,
                update: false,
                remove: false,
            },
            queryForm: {
                realname: '',
                username: '',
                identityNumber: '',
                email: '',
                phone: '',
            },
            sexItem: [],
            roleList: [],
            organizationTreeList: [],
            avatar: '',
            actionUrl: '/sys/common/upload',
            insertForm: {
                realname: '',
                username: '',
                password: '',
                identityNumber: '',
                email: '',
                phone: '',
                avatar: '',
                roleList: [],
                organizationList: [],
            },
            detailForm: {},
            updateForm: {
                id: '',
                username: '',
                realname: '',
                identityNumber: '',
                email: '',
                phone: '',
                avatar: '',
                roleList: [],
                organizationList: [],
            },
            passwordResetForm: {
                userId: '',
                newPassword: '',
                confirmPassword: '',
            },
            // 校验规则
            rules: {
                realname: [
                    {required: true, message: '请输入真实姓名', trigger: 'blur'},
                    {min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur'},
                ],
                username: [
                    {required: true, message: '请输入登录账号', trigger: 'blur'},
                    {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'},
                ],
                password: [
                    {required: true, message: '请输入登录密码', trigger: 'blur'},
                    {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
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
                ]
            },
            // 密码校验规则
            passwordFormRules: {
                newPassword: [
                    {required: true, message: '请输入登录密码', trigger: 'blur'},
                    {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
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
    },
    methods: {
        // 当前页码发生变更
        handleCurrentChange(val) {
            this.currentPage = val;
            this.submitQueryForm();
        },
        // 当前页长发生变化
        handleSizeChange(val) {
            this.pageSize = val;
            this.submitQueryForm();
        },
        // 性别格式化展示
        formatSex: function (row, column, cellValue, index) {
            return this.sexItem.find(item => item.value == row.sex).name
        },
        // 判断按钮权限
        buttonPermissions() {
            let buttons = JSON.parse(localStorage.getItem("X-Data-Buttons-List"));
            this.button.insert = buttons.indexOf("/sys/user/add") > -1;
            this.button.freeze = buttons.indexOf("/sys/user/freeze") > -1;
            this.button.unFreeze = buttons.indexOf("/sys/user/unFreeze") > -1;
            this.button.reset = buttons.indexOf("/sys/user/reset") > -1;
            this.button.update = buttons.indexOf("/sys/user/update") > -1;
            this.button.remove = buttons.indexOf("/sys/user/remove") > -1;
        },
        // 查询表单提交
        submitQueryForm() {
            let params = {
                pageNumber: this.currentPage,
                pageSize: this.pageSize,
                // sortFieldList: this.sortFieldList,
                realname: this.queryForm.realname,
                username: this.queryForm.username,
                identityNumber: this.queryForm.identityNumber,
                email: this.queryForm.email,
                phone: this.queryForm.phone
            };
            axios.get('/sys/user/page', {params})
                .then((res) => {
                    if (res.code === 6666) {
                        this.tableData = res.data.records;
                        this.total = res.data.total;
                    }
                });
            this.buttonPermissions();
        },
        // 查询表单清空
        clearQueryForm() {
            this.queryForm = {};
        },
        // 文件上传成功
        handleAvatarSuccess(idx, res, file, name) {
            if (res.code === 6666) {
                let data = res.data;
                const url = data[data.length - 1].fileUrl;
                if ("updateForm" === idx){
                    this.updateForm.avatar = url;
                }else {
                    this.insertForm.avatar = url;
                }
            }
        },
        // 文件上传之前
        beforeAvatarUpload(file) {
            const isLt2M = file.size / 1024 / 1024 < 2;
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过2MB!');
            }
            return isLt2M;
        },
        // 查询角色列表
        queryRole() {
            axios.get('/sys/role/list')
                .then((res) => {
                    if (res.code === 6666) {
                        this.roleList = res.data;
                    }
                });
        },
        // 查询树机构
        queryOrganizationTree() {
            axios.get('/sys/organization/tree')
                .then((res) => {
                    if (res.code === 6666) {
                        this.organizationTreeList = res.data;
                    }
                });
        },
        // 开启新增表单
        openInsertDialog() {
            this.insertDialog = true;
            if (this.$refs.insertForm !== undefined) {
                this.$refs.insertForm.resetFields();
            }
            this.queryRole();
            this.queryOrganizationTree();
        },
        // 新增表单提交
        insert() {
            this.$refs.insertForm.validate((valid) => {
                if (valid) {
                    let insertParam = this.insertForm;
                    axios.post('/sys/user/add', JSON.parse(JSON.stringify(insertParam)))
                        .then((res) => {
                            if (res.code === 6666) {
                                this.submitQueryForm();
                                this.$message({
                                    type: 'success',
                                    message: '新增成功'
                                });
                                this.insertDialog = false;
                            } else {
                                this.$message.error(res.message);
                            }
                        });
                } else {
                    return false;
                }
            });
        },
        // 获取多选的数据
        handleSelectionChange(val) {
            this.multipleSelection = val.map(row => row.id);
        },
        // 开启冻结表单
        openFreezeDialog() {
            this.$confirm('此操作将冻结所选用户，您确定要冻结吗', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                modal: 'false',
                type: 'warning'
            }).then(() => {
                let params = this.multipleSelection;
                axios.put('/sys/user/freeze', params)
                    .then((res) => {
                        if (res.code === 6666) {
                            this.submitQueryForm();
                            this.$message({
                                type: 'success',
                                message: '冻结成功'
                            });
                        } else {
                            this.$message.error(res.message);
                        }
                    });
            }).catch(() => {
            });
        },
        // 开启解冻表单
        openUnFreezeDialog() {
            this.$confirm('此操作将解冻所选用户，您确定要解冻吗', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                modal: 'false',
                type: 'warning'
            }).then(() => {
                let params = this.multipleSelection;
                axios.put('/sys/user/unFreeze', params)
                    .then((res) => {
                        if (res.code === 6666) {
                            this.submitQueryForm();
                            this.$message({
                                type: 'success',
                                message: '解冻成功'
                            });
                        } else {
                            this.$message.error(res.message);
                        }
                    });
            }).catch(() => {
            });
        },
        // 开启删除弹框
        openRemoveMessageBox() {
            this.$confirm('此操作将移除所选用户，您确定要移除吗', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                modal: 'false',
                type: 'warning'
            }).then(() => {
                let params = {idList: this.multipleSelection.toString()};
                axios.delete('/sys/user/remove', {params})
                    .then((res) => {
                        if (res.code === 6666) {
                            this.submitQueryForm();
                            this.$message({
                                type: 'success',
                                message: '删除成功'
                            });
                        } else {
                            this.$message.error(res.message);
                        }
                    });
            }).catch(() => {
            });
        },
        // 开启详情表单
        openDetailDialog(row) {
            this.detailDialog = true;
            axios.get('/sys/user/query/' + row.id)
                .then((res) => {
                    if (res.code === 6666) {
                        this.detailForm = res.data;
                    } else {
                        this.$message.error(res.message);
                    }
                });
        },
        // 打印
        print(){
            this.$message.error("未开发的功能");
        },
        // 开启更新表单
        openUpdateDialog(row) {
            this.updateDialog = true;
            this.queryRole();
            this.queryOrganizationTree();
            axios.get('/sys/user/query/' + row.id)
                .then((res) => {
                    if (res.code === 6666) {
                        this.updateForm = res.data;
                    } else {
                        this.$message.error(res.message);
                    }
                });
        },
        // 更新表单提交
        update() {
            this.$refs.updateForm.validate((valid) => {
                if (valid) {
                    let updateParam = this.updateForm;
                    axios.put('/sys/user/update', JSON.parse(JSON.stringify(updateParam)))
                        .then((res) => {
                            if (res.code === 6666) {
                                this.submitQueryForm();
                                this.$message({
                                    type: 'success',
                                    message: '修改成功'
                                });
                                this.updateDialog = false;
                            } else {
                                this.$message.error(res.message);
                            }
                        });
                } else {
                    return false;
                }
            });
        },
        // 开启密码重置表单
        openResetDialog(row) {
            // 标识当前行的唯一键的名称
            this.resetDialog = true;
            this.passwordResetForm.userId = row.id;
        },
        // 密码重置
        reset() {
            this.$refs.passwordResetForm.validate((valid) => {
                if (valid) {
                    let updateParam = this.passwordResetForm;
                    axios.put('/sys/user/reset', JSON.parse(JSON.stringify(updateParam)))
                        .then((res) => {
                            if (res.code === 6666) {
                                this.submitQueryForm();
                                this.$message({
                                    type: 'success',
                                    message: '密码重置成功'
                                });
                                this.resetDialog = false;
                            } else {
                                this.$message.error(res.message);
                            }
                        });
                } else {
                    return false;
                }
            });
        }
    },
    created() {
        this.submitQueryForm();
        let dictList = JSON.parse(localStorage.getItem("X-Data-Dict-List"));
        this.sexItem = dictList.find(dict => dict.dictCode === "sex").itemList;
    }
});