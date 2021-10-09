new Vue({
    el: '#role',
    data() {
        return {
            currentPage: 1,
            pageSize: 10,
            total: 0,
            sortFieldList: [],
            multipleSelection: [],
            tableData: [],
            insertDialog: false,
            updateDialog: false,
            menuData: [],
            defaultProps: {
                children: 'children',
                label: 'name'
            },
            button: {
                insert: false,
                update: false,
                delete: false
            },
            insertForm: {
                roleName: '',
                description: '',
                menuList: []
            },
            updateForm: {
                id: '',
                roleCode: '',
                roleName: '',
                description: '',
                menuList: []
            },
            queryForm: {
                roleName: ''
            },
            rules: {
                roleName: [
                    {required: true, message: '请输入角色名称', trigger: 'blur'},
                    {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
                ]
            }
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
        // 判断按钮权限
        buttonPermissions() {
            let buttons = JSON.parse(localStorage.getItem("X-Data-Buttons-List"));
            this.button.insert = buttons.indexOf("/sys/role/add") > -1;
            this.button.update = buttons.indexOf("/sys/role/update") > -1;
            this.button.delete = buttons.indexOf("/sys/role/delete") > -1;
        },
        // 查询表单提交
        submitQueryForm() {
            let params = {
                pageNumber: this.currentPage,
                pageSize: this.pageSize,
                // sortFieldList: this.sortFieldList,
                roleName: this.queryForm.roleName
            };
            axios.get('/sys/role/page', {params})
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
        // 开启新增表单
        openInsertDialog() {
            this.insertDialog = true;
            if (this.$refs.insertForm !== undefined) {
                this.$refs.insertForm.resetFields();
            }
            axios.get('/sys/menu/tree', {})
                .then((res) => {
                    if (res.code === 6666) {
                        this.menuData = res.data;
                    }
                });
        },
        // 新增表单提交
        insert() {
            this.$refs.insertForm.validate((valid) => {
                if (valid) {
                    let insertParam = this.insertForm;
                    insertParam.menuList = this.$refs.insertTree.getCheckedKeys().concat(this.$refs.insertTree.getHalfCheckedKeys());
                    axios.post('/sys/role/add', JSON.parse(JSON.stringify(insertParam)))
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
        // 开启删除表单
        openDeleteDialog(row) {
            this.$confirm('此操作将删除所选角色，您确定要删除吗', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                modal: 'false',
                type: 'warning'
            }).then(() => {
                let params = {idList: this.multipleSelection.toString()};
                axios.delete('/sys/role/delete', {params})
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
        // 开启更新表单
        openUpdateDialog(row) {
            this.updateDialog = true;
            axios.get('/sys/menu/tree', {})
                .then((res) => {
                    if (res.code === 6666) {
                        this.menuData = res.data;
                    }
                });
            axios.get('/sys/role/query/' + row.id)
                .then((res) => {
                    if (res.code === 6666) {
                        this.updateForm = res.data;
                        if (res.data.menuList != null) {
                            this.$refs.updateTree.setCheckedKeys(res.data.menuList);
                        }
                    }
                });
        },
        // 更新表单提交
        update() {
            this.$refs.updateForm.validate((valid) => {
                if (valid) {
                    let updateParam = this.updateForm;
                    updateParam.menuList = this.$refs.updateTree.getCheckedKeys().concat(this.$refs.updateTree.getHalfCheckedKeys());
                    axios.put('/sys/role/update', JSON.parse(JSON.stringify(updateParam)))
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
        }
    },
    created() {
        this.submitQueryForm();
    }
});