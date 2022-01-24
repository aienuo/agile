new Vue({
    el: '#dict',
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
            // 字典类型（0-String，1-Number）
            dictTypeItem: [],
            queryForm: {
                dictName: '',
                dictCode: ''
            },
            insertForm: {
                dictName: '',
                dictCode: '',
                dictType: 0,
                status: 0,
                description: '',
            },
            updateForm: {
                id: '',
                dictName: '',
                dictCode: '',
                dictType: 0,
                status: 0,
                description: '',
            },
            configurationDrawer: false,
            dictItemData: [],
            insertItemDialog: false,
            updateItemDialog: false,
            queryItemForm: {
                name: '',
                disabled: ''
            },
            insertItemForm: {
                dictId: '',
                parentId:'',
                name: '',
                value: '',
                disabled: 0,
                sortNo: 1.00,
                description: '',
            },
            updateItemForm: {
                id: '',
                dictId: '',
                parentId:'',
                name: '',
                value: '',
                disabled: 0,
                sortNo: '',
                description: '',
            },
            rules: {
                dictName: [
                    {required: true, message: '请输入字典名称', trigger: 'blur'},
                    {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
                ],
                dictCode: [
                    {required: true, message: '请输入字典编码', trigger: 'blur'},
                    {min: 2, max: 10, message: '长度在 2 到 100 个字符', trigger: 'blur'}
                ],
                name: [
                    {required: true, message: '请输入字典项名', trigger: 'blur'},
                    {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
                ],
            },
            updateFormStatus:0,
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
        // 查询表单提交
        submitQueryForm() {
            let params = {
                pageNumber: this.currentPage,
                pageSize: this.pageSize,
                // sortFieldList: this.sortFieldList,
                dictName: this.queryForm.dictName,
                dictCode: this.queryForm.dictCode,
            };
            axios.get('/sys/dict/page', {params})
                .then((res) => {
                    if (res.code === 6666) {
                        this.tableData = res.data.records;
                        this.total = res.data.total;
                    }
                });
        },
        // 查询表单清空
        clearQueryForm() {
            this.queryForm = {};
        },
        // 字典类型格式化展示
        formatDictType: function (row, column, cellValue, index) {
            return this.dictTypeItem.find(item => item.value == row.dictType).name
        },
        // 开启新增表单
        openInsertDialog() {
            this.insertDialog = true;
            if (this.$refs.insertForm !== undefined) {
                this.$refs.insertForm.resetFields();
            }
        },
        // 新增表单提交
        insert() {
            this.$refs.insertForm.validate((valid) => {
                if (valid) {
                    let insertParam = this.insertForm;
                    axios.post('/sys/dict/add', JSON.parse(JSON.stringify(insertParam)))
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
            this.$confirm('此操作将删除所选字典，您确定要删除吗', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                modal: 'false',
                type: 'warning'
            }).then(() => {
                let params = {idList: this.multipleSelection.toString()};
                axios.delete('/sys/dict/delete', {params})
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
            if (row) {
                this.updateForm = row;
            }
        },
        // 更新表单提交
        update() {
            this.$refs.updateForm.validate((valid) => {
                if (valid) {
                    let updateParam = this.updateForm;
                    axios.put('/sys/dict/update', JSON.parse(JSON.stringify(updateParam)))
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
        // 开启配置表单
        openConfigurationDrawer(row) {
            this.configurationDrawer = true;
            if (row) {
                this.queryItemForm.dictId = row.id;
                this.submitItemQueryForm();
            }
        },
        // 查询表单提交
        submitItemQueryForm() {
            let params = {
                dictId: this.queryItemForm.dictId,
                name: this.queryItemForm.name,
                disabled: this.queryItemForm.disabled,
            };
            axios.get('/sys/dict/item/tree', {params})
                .then((res) => {
                    if (res.code === 6666) {
                        this.dictItemData = res.data;
                    }
                });
        },
        // 查询表单清空
        clearItemQueryForm() {
            this.queryItemForm.name = ''
            this.queryItemForm.disabled = ''
        },
        // 关闭配置表单
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => {
                });
        },
        // 开启新增表单
        openInsertItemDialog(row){
            this.insertItemDialog = true;
            if (this.$refs.insertItemForm !== undefined) {
                this.$refs.insertItemForm.resetFields();
                this.insertItemForm.dictId = this.queryItemForm.dictId;
                if (row){
                    this.insertItemForm.parentId = row.id;
                }
            }
        },
        // 新增表单提交
        insertItem() {
            this.$refs.insertItemForm.validate((valid) => {
                if (valid) {
                    this.insertItemForm.dictId = this.queryItemForm.dictId;
                    let insertParam = this.insertItemForm;
                    axios.post('/sys/dict/item/add', JSON.parse(JSON.stringify(insertParam)))
                        .then((res) => {
                            if (res.code === 6666) {
                                this.submitItemQueryForm();
                                this.$message({
                                    type: 'success',
                                    message: '新增成功'
                                });
                                this.insertItemDialog = false;
                            } else {
                                this.$message.error(res.message);
                            }
                        });
                } else {
                    return false;
                }
            });
        },
        // 开启更新表单
        openUpdateItemDialog(row) {
            this.updateItemDialog = true;
            if (row) {
                this.updateItemForm = row;
                this.updateItemForm.disabled = row.disabled ? 1 : 0;
            }
        },
        // 更新表单提交
        updateItem() {
            this.$refs.updateItemForm.validate((valid) => {
                if (valid) {
                    let updateParam = this.updateItemForm;
                    axios.put('/sys/dict/item/update', JSON.parse(JSON.stringify(updateParam)))
                        .then((res) => {
                            if (res.code === 6666) {
                                this.submitItemQueryForm();
                                this.$message({
                                    type: 'success',
                                    message: '修改成功'
                                });
                                this.updateItemDialog = false;
                            } else {
                                this.$message.error(res.message);
                            }
                        });
                } else {
                    return false;
                }
            });
        },
        // 开启删除表单
        openDeleteItemDialog(row) {
            this.$confirm('此操作将删除所选字典项，您确定要删除吗', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                modal: 'false',
                type: 'warning'
            }).then(() => {
                let params = {idList:row.id};
                axios.delete('/sys/dict/item/delete', {params})
                    .then((res) => {
                        if (res.code === 6666) {
                            this.submitItemQueryForm();
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
    },
    created() {
        this.submitQueryForm();
        let dictList = JSON.parse(localStorage.getItem("X-Data-Dict-List"));
        this.dictTypeItem = dictList.find(dict => dict.dictCode === "dictType").itemList;
    }
});