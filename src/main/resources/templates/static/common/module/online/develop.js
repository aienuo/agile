new Vue({
    el: '#develop',
    data() {
        return {
            currentPage: 1,
            pageSize: 10,
            total: 0,
            sortFieldList: [],
            multipleSelection: [],
            tableData: [],
            button: {
                insert: false,
                update: false,
                delete: false,
                database: false,
            },
            // 表类型（0-单表，1-主表，2-附表）
            tableTypeItem: [],
            // 表单风格（dialog-弹框、drawer-抽屉）
            formStyleItem: [],
            // 主题模板
            themeTemplateItem: [],
            // 固定字段
            fixedField: [
                {
                    disabled: true,
                    delete: false,
                    sortNo: 1,
                    fieldName: 'id',
                    fieldTxt: '主键',
                    synchronize: 0,
                    isKey: 1,
                    isNull: 0,
                    fieldType: 'Long',
                    fieldLength: 20,
                    fieldPointLength: 0,
                    fieldDefaultValue: ' ',
                    description: '',
                    queryShow: 0,
                    insertShow: 0,
                    updateShow: 0,
                    tableShow: 0,
                    onlyRead: 1,
                    sortFlag: 0,
                    controlType: 0,
                    controlLength: 0,
                    queryMode: 0,
                    validationRules: '',
                    mainTableName: '',
                    mainTableField: '',
                    dictTableName: '',
                    dictCodeField: '',
                },
                {
                    disabled: true,
                    delete: true,
                    sortNo: 2,
                    fieldName: 'create_by',
                    fieldTxt: '创建人',
                    synchronize: 1,
                    isKey: 0,
                    isNull: 0,
                    fieldType: 'String',
                    fieldLength: 20,
                    fieldPointLength: 0,
                    fieldDefaultValue: ' ',
                    description: '',
                    queryShow: 0,
                    insertShow: 0,
                    updateShow: 0,
                    tableShow: 0,
                    onlyRead: 1,
                    sortFlag: 0,
                    controlType: 0,
                    controlLength: 0,
                    queryMode: 0,
                    validationRules: '',
                    mainTableName: '',
                    mainTableField: '',
                    dictTableName: '',
                    dictCodeField: '',
                },
                {
                    disabled: true,
                    delete: true,
                    sortNo: 3,
                    fieldName: 'create_time',
                    fieldTxt: '创建日期',
                    synchronize: 1,
                    isKey: 0,
                    isNull: 0,
                    fieldType: 'LocalDateTime',
                    fieldLength: 20,
                    fieldPointLength: 0,
                    fieldDefaultValue: '2021-01-01 00:00:00',
                    description: '',
                    queryShow: 0,
                    insertShow: 0,
                    updateShow: 0,
                    tableShow: 0,
                    onlyRead: 1,
                    sortFlag: 0,
                    controlType: 0,
                    controlLength: 0,
                    queryMode: 0,
                    validationRules: '',
                    mainTableName: '',
                    mainTableField: '',
                    dictTableName: '',
                    dictCodeField: '',
                },
                {
                    disabled: true,
                    delete: true,
                    sortNo: 4,
                    fieldName: 'update_by',
                    fieldTxt: '更新人',
                    synchronize: 1,
                    isKey: 0,
                    isNull: 1,
                    fieldType: 'String',
                    fieldLength: 20,
                    fieldPointLength: 0,
                    fieldDefaultValue: ' ',
                    description: '',
                    queryShow: 0,
                    insertShow: 0,
                    updateShow: 0,
                    tableShow: 0,
                    onlyRead: 1,
                    sortFlag: 0,
                    controlType: 0,
                    controlLength: 0,
                    queryMode: 0,
                    validationRules: '',
                    mainTableName: '',
                    mainTableField: '',
                    dictTableName: '',
                    dictCodeField: '',
                },
                {
                    disabled: true,
                    delete: true,
                    sortNo: 5,
                    fieldName: 'update_time',
                    fieldTxt: '更新日期',
                    synchronize: 1,
                    isKey: 0,
                    isNull: 1,
                    fieldType: 'LocalDateTime',
                    fieldLength: 20,
                    fieldPointLength: 0,
                    fieldDefaultValue: '2021-01-01 00:00:00',
                    description: '',
                    queryShow: 0,
                    insertShow: 0,
                    updateShow: 0,
                    tableShow: 0,
                    onlyRead: 1,
                    sortFlag: 0,
                    controlType: 0,
                    controlLength: 0,
                    queryMode: 0,
                    validationRules: '',
                    mainTableName: '',
                    mainTableField: '',
                    dictTableName: '',
                    dictCodeField: '',
                }
            ],
            // 字段类型
            fieldTypeItem: [],
            // 控件类型
            controlTypeItem: [],
            // 验证规则
            validationRuleItem: [],
            // 查询模式（0-普通，1-范围，2-左模糊，3-右模糊，4-全模糊）
            queryModeItem: [],
            // 主表名
            mainTableNameItem: [],
            // 主表字段
            mainTableFieldItem: [],
            // 字典表表名
            dictTableNameItem: [],
            // 字典表字段
            dictTableFieldItem: [],
            // 索引类型
            indexTypeItem: [],
            // 对话框标题
            dialogTitle: '',
            // 表单数据
            formData: {
                id: '',
                textName: '',
                tableName: '',
                tableType: '',
                paging: 1,
                insertStyle: '',
                updateStyle: '',
                themeTemplate: '',
                description: '',
                fieldData: [],
                indexData: [],
            },
            // 表单开启展示
            openDialog: false,
            insertButton: true,
            queryForm: {
                tableType: '',
                textName: '',
                tableName: '',
                synchronize: '',
            },
            rules: {
                textName: [
                    {required: true, message: '请输入字段名字', trigger: 'blur'},
                    {min: 2, max: 100, message: '长度在 2 到 100 个字符', trigger: 'blur'}
                ],
                tableName: [
                    {required: true, message: '请输入字段名字', trigger: 'blur'},
                    {min: 2, max: 100, message: '长度在 2 到 100 个字符', trigger: 'blur'}
                ],
                tableType: [
                    {required: true, message: '请选择数据库表类型', trigger: 'blur'}
                ],
                paging: [
                    {required: true, message: '是否分页', trigger: 'blur'}
                ],
                insertStyle: [
                    {required: true, message: '请选择新增表单风格', trigger: 'blur'}
                ],
                updateStyle: [
                    {required: true, message: '请选择更新表单风格', trigger: 'blur'}
                ],

                fieldTxt: [
                    {required: true, message: '请输入字段备注', trigger: 'blur'},
                    {min: 2, max: 100, message: '长度在 2 到 100 个字符', trigger: 'blur'}
                ],
                fieldType: [
                    {required: true, message: '请选择字段类型', trigger: 'blur'}
                ],
                controlType: [
                    {required: true, message: '请选择控件类型', trigger: 'blur'}
                ],
                queryMode: [
                    {required: true, message: '请选择查询模式', trigger: 'blur'}
                ]
            }
        }
    },
    mounted() {
    },
    methods: {
        // 表类型 格式化展示
        formatTableType: function (row, column, cellValue, index) {
            // 表类型（0-单表，1-主表，2-附表）
            return this.tableTypeItem.find(item => item.value == row.tableType).name
        },
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
            this.button.insert = buttons.indexOf("/online/develop/add") > -1;
            this.button.update = buttons.indexOf("/online/develop/update") > -1;
            this.button.delete = buttons.indexOf("/online/develop/delete") > -1;
            this.button.database = buttons.indexOf("/online/develop/database") > -1;
        },
        // 查询表单提交
        submitQueryForm() {
            let params = {
                pageNumber: this.currentPage,
                pageSize: this.pageSize,
                // sortFieldList: this.sortFieldList,
                tableType: this.queryForm.tableType,
                textName: this.queryForm.textName,
                tableName: this.queryForm.tableName,
                synchronize: this.queryForm.synchronize,
            };
            axios.get('/online/develop/page', {params})
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
        // 删除指定行数据
        deleteRow(row, index) {
            this.formData.fieldData.splice(index, 1);
        },
        // 新增字段
        addField() {
            const newField = {
                disabled: false,
                delete: true,
                sortNo: this.formData.fieldData.length + 1,
                synchronize: 1,
                isKey: 0,
                isNull: 0,
            };
            this.formData.fieldData.push(newField);
        },
        // 删除指定索引
        deleteIndexData(row, index) {
            this.formData.indexData.splice(index, 1);
        },
        // 新增索引
        addIndexData() {
            const newIndex = {
                synchronize: 1,
            };
            this.formData.indexData.push(newIndex);
        },
        // 查询主表字段
        queryMainTableFieldItem(row, index) {
            if (row.mainTableName) {
                const tableName = row.mainTableName;
                axios.get('/online/develop/query/field/' + tableName)
                    .then((res) => {
                        if (res.code === 6666) {
                            this.mainTableFieldItem = res.data;
                        }
                    });
            }
        },
        // 查询字典表字段
        queryDictTableFieldItem(row, index) {
            if (row.dictTableName) {
                const tableName = row.dictTableName;
                axios.get('/online/develop/query/field/' + tableName)
                    .then((res) => {
                        if (res.code === 6666) {
                            this.dictTableFieldItem = res.data;
                        }
                    });
            }
        },
        // 开启新增表单
        openInsertDialog() {
            this.dialogTitle = '新增';
            this.openDialog = true;
            this.insertButton = true;
            if (this.$refs.formData !== undefined) {
                this.$refs.formData.resetFields();
            }
            // 设置常用字段
            this.formData.fieldData = this.fixedField;
        },
        // 新增表单提交
        insert() {
            this.$refs.formData.validate((valid) => {
                if (valid) {
                    let insertParam = this.formData;
                    axios.post('/online/develop/add', JSON.parse(JSON.stringify(insertParam)))
                        .then((res) => {
                            if (res.code === 6666) {
                                this.submitQueryForm();
                                this.$message({
                                    type: 'success',
                                    message: '新增成功'
                                });
                                this.openDialog = false;
                                this.insertButton = false;
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
            this.$confirm('此操作将删除所选表全部内容，您确定要删除吗', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                modal: 'false',
                type: 'warning'
            }).then(() => {
                let params = {idList: this.multipleSelection.toString()};
                axios.delete('/online/develop/delete', {params})
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
        // 生成数据库
        generateDatabase(row) {
            this.$confirm('此操作会将所选数据生成表到数据库，您确定要进行吗', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                modal: 'false',
                type: 'warning'
            }).then(() => {
                if (row.id) {
                    const tableId = row.id;
                    axios.put('/online/develop/database/' + tableId)
                        .then((res) => {
                            if (res.code === 6666) {
                                this.submitQueryForm();
                                this.$message({
                                    type: 'success',
                                    message: '生成表到数据库成功'
                                });
                            } else {
                                this.$message.error(res.message);
                            }
                        });
                }
            }).catch(() => {
            });
        },
        // 开启更新表单
        openUpdateDialog(row) {
            this.dialogTitle = '更新';
            this.openDialog = true;
            this.insertButton = false;
            axios.get('/online/develop/query/' + row.id)
                .then((res) => {
                    if (res.code === 6666) {
                        this.formData = res.data;
                    }
                });
        },
        // 更新表单提交
        update() {
            this.$refs.formData.validate((valid) => {
                if (valid) {
                    let updateParam = this.formData;
                    axios.put('/online/develop/update', JSON.parse(JSON.stringify(updateParam)))
                        .then((res) => {
                            if (res.code === 6666) {
                                this.submitQueryForm();
                                this.$message({
                                    type: 'success',
                                    message: '修改成功'
                                });
                                this.openDialog = false;
                                this.insertButton = true;
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
        this.tableTypeItem = dictList.find(dict => dict.dictCode === "tableType").itemList;
        this.formStyleItem = dictList.find(dict => dict.dictCode === "formStyle").itemList;
        this.themeTemplateItem = dictList.find(dict => dict.dictCode === "themeTemplate").itemList;
        this.fieldTypeItem = dictList.find(dict => dict.dictCode === "fieldType").itemList;
        this.controlTypeItem = dictList.find(dict => dict.dictCode === "controlType").itemList;
        this.queryModeItem = dictList.find(dict => dict.dictCode === "queryMode").itemList;
        this.validationRuleItem = dictList.find(dict => dict.dictCode === "validationRule").itemList;
        this.mainTableNameItem = dictList.find(dict => dict.dictCode === "tableName").itemList;
        this.dictTableNameItem = dictList.find(dict => dict.dictCode === "tableName").itemList;
        this.indexTypeItem = dictList.find(dict => dict.dictCode === "indexType").itemList;
    }
});