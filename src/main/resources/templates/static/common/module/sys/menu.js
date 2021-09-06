new Vue({
    el: '#menu',
    data() {
        return {
            sortFieldList: [],
            multipleSelection: [],
            tableData: [],
            insertDialog: false,
            updateDialog: false,
            button: {
                insert: false,
                update: false,
                delete: false
            },
            // 菜单类型（0-一级菜单，1-子菜单，2-按钮权限）
            menuTypeItem: [],
            // 冻结状态(0-正常，1-冻结）
            statusItem: [],
            // 父级菜单
            parentMenu: [],
            // 按钮类型
            buttonTypeItem: [],
            // 按钮尺寸
            buttonSizeItem: [],
            // 图标选择器
            iconPicker: {
                icon: '',
                options: {
                    FontAwesome: false,
                    ElementUI: true,
                    eIcon: false,
                    eIconSymbol: false,
                    addIconList: [],
                    removeIconList: []
                },
                disabled: false,
                readonly: false,
                placement: 'bottom',
                style: {},
                width: 800
            },
            insertForm: {
                parentId: '',
                parentIcon: '',
                parentName: '',
                menuType: '',
                name: '',
                icon: '',
                url: '',
                component: '',
                sortNo: 1.00,
                status: '',
                description: '',
                buttonType: '',
                buttonSize: ''
            },
            updateForm: {
                id: '',
                parentId: '',
                parentIcon: '',
                parentName: '',
                menuType: '',
                name: '',
                icon: '',
                url: '',
                component: '',
                sortNo: '',
                status: '',
                description: '',
                buttonType: '',
                buttonSize: ''
            },
            // 校验规则
            rules: {
                name: [
                    {required: true, message: '请输入菜单名称', trigger: 'blur'},
                    {min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur'}
                ],
                menuType: [
                    {required: true, message: '请选择菜单类型', trigger: 'blur'}
                ],
                status: [
                    {required: true, message: '请选择菜单状态', trigger: 'blur'}
                ],
            },
        }
    },
    mounted() {
    },
    methods: {
        // 菜单类型 格式化展示
        formatMenuType: function (row, column, cellValue, index) {
            return this.menuTypeItem.find(item => item.value == row.menuType).name
        },
        // 判断按钮权限
        buttonPermissions(){
            let buttons = JSON.parse(localStorage.getItem("X-Data-Buttons-List"));
            this.button.insert = buttons.indexOf("/sys/menu/add") > -1;
            this.button.update = buttons.indexOf("/sys/menu/update") > -1;
            this.button.delete = buttons.indexOf("/sys/menu/delete") > -1;
        },
        // 查询表单提交
        submitQueryForm() {
            axios.get('/sys/menu/tree')
                .then((res) => {
                    if (res.code === 6666) {
                        this.tableData = res.data;
                    }
                });
            this.buttonPermissions();
        },
        // 查询树机构
        queryMenuTree() {
            axios.get('/sys/menu/tree')
                .then((res) => {
                    if (res.code === 6666) {
                        this.parentMenu = res.data;
                    }
                });
        },
        // 开启新增表单
        openInsertDialog(row) {
            this.insertDialog = true;
            if (this.$refs.insertForm !== undefined) {
                this.$refs.insertForm.resetFields();
            }
            if (row.id) {
                // 添加下级
                this.insertForm.status = 0;
                this.insertForm.menuType = 1;
                this.insertForm.parentId = row.id;
                this.insertForm.parentName = row.name;
                this.insertForm.parentIcon = row.icon;
                this.menuTypeItem[0].disabled = true;
            } else {
                // 添加
                this.insertForm.status = 0;
                this.insertForm.menuType = 0;
                this.insertForm.parentId = '';
                this.insertForm.parentName = '';
                this.insertForm.parentIcon = '';
                this.menuTypeItem[0].disabled = false;
                this.queryMenuTree();
            }
        },
        // 新增
        insert() {
            this.$refs.insertForm.validate((valid) => {
                if (valid) {
                    this.insertDialog = false;
                    let insertParam = this.insertForm;
                    axios.post('/sys/menu/add', JSON.parse(JSON.stringify(insertParam)))
                        .then((res) => {
                            if (res.code === 6666) {
                                this.submitQueryForm();
                                this.$message({
                                    type: 'success',
                                    message: '新增成功'
                                });
                            } else {
                                this.$message.error(res.message);
                            }
                        });
                } else {
                    return false;
                }
            });
        },
        // 开启删除弹框
        openRemoveMessageBox(row) {
            this.$confirm('您确定删除吗', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                if (row.children) {
                    this.$message({
                        type: 'warning',
                        message: '父菜单，禁止删除'
                    });
                } else {
                    let params = {idList: row.id};
                    axios.delete('/sys/menu/delete/', {params})
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
                }
            }).catch(() => {
            });
        },
        // 开启更新表单
        openUpdateDialog(row) {
            this.updateDialog = true;
            this.menuTypeItem[0].disabled = false;
            axios.get('/sys/menu/query/' + row.id)
                .then((res) => {
                    if (res.code === 6666) {
                        this.updateForm = res.data;
                    } else {
                        this.$message.error(res.message);
                    }
                });
            this.queryMenuTree();
        },
        // 更新
        update() {
            this.$refs.updateForm.validate((valid) => {
                if (valid) {
                    this.updateDialog = false;
                    let updateParam = this.updateForm;
                    axios.put('/sys/menu/update', JSON.parse(JSON.stringify(updateParam)))
                        .then((res) => {
                            if (res.code === 6666) {
                                this.submitQueryForm();
                                this.$message({
                                    type: 'success',
                                    message: '修改成功'
                                });
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
        this.menuTypeItem = dictList.find(dict => dict.dictCode === "menuType").itemList;
        this.statusItem = dictList.find(dict => dict.dictCode === "status").itemList;
        this.buttonTypeItem = dictList.find(dict => dict.dictCode === "buttonType").itemList;
        this.buttonSizeItem = dictList.find(dict => dict.dictCode === "buttonSize").itemList;
    }
});