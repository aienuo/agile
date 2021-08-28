new Vue({
    el: '#organization',
    data() {
        return {
            sortFieldList: [],
            multipleSelection: [],
            tableData: [],
            insertDialog: false,
            updateDialog: false,
            draggable: false,
            updateBachButton: false,
            // 编辑节点
            editNodeForm: [],
            // 冻结状态(0-正常，1-冻结）
            statusItem: [],
            // 父级机构
            parentOrganization: [],
            filterText: '',
            button: {
                edit: false,
                updateBach: false,
                insert: false,
                update: false,
                delete: false
            },
            insertForm: {
                parentId: '',
                organizationName: '',
                sortNo: 1.00,
                status: '',
                description: ''
            },
            updateForm: {
                id: '',
                parentId: '',
                organizationName: '',
                organizationCode: '',
                sortNo: '',
                status: '',
                description: ''
            },
            // 校验规则
            rules: {
                organizationName: [
                    {required: true, message: '请输入组织机构名称', trigger: 'blur'},
                    {min: 1, max: 10, message: '长度在 1 到 50 个字符', trigger: 'blur'}
                ],
                status: [
                    {required: true, message: '请选择组织机构状态', trigger: 'blur'}
                ],
            },
        }
    },
    mounted() {
    },
    watch: {
        filterText(val) {
            this.$refs.tree.filter(val);
        }
    },
    methods: {
        // 判断按钮
        buttonPermissions() {
            let buttons = JSON.parse(localStorage.getItem("X-Data-Buttons-List"));
            this.button.edit = buttons.indexOf("/sys/organization/edit") > -1;
            this.button.updateBach = buttons.indexOf("/sys/organization/updateBach") > -1;
            this.button.insert = buttons.indexOf("/sys/organization/add") > -1;
            this.button.update = buttons.indexOf("/sys/organization/update") > -1;
            this.button.delete = buttons.indexOf("/sys/organization/delete") > -1;
        },
        // 查询表单提交
        submitQueryForm() {
            axios.get('/sys/organization/tree')
                .then((res) => {
                    if (res.code === 6666) {
                        this.tableData = res.data;
                        this.draggable = false;
                        this.updateBachButton = false;
                    }
                });
            this.buttonPermissions();
            this.editNodeForm = [];
        },
        // 过滤
        filterNode(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        },
        // 查看详情
        queryDetailsById(node) {
            axios.get('/sys/organization/query/' + node.id)
                .then((res) => {
                    if (res.code === 6666) {
                        this.updateForm = res.data;
                    } else {
                        this.$message.error(res.message);
                    }
                });
            this.queryOrganizationTree();
        },
        // 编辑树
        editNode() {
            this.button.insert = false;
            this.button.delete = false;
            this.draggable = true;
            this.updateBachButton = true;
        },
        // 判断节点能否被拖拽
        allowDrag(draggingNode) {
            return !draggingNode.disabled;
        },
        // 拖拽时判定目标节点能否被放置
        allowDrop(draggingNode, dropNode, type) {
            if (dropNode.disabled) {
                // 禁用的节点 不允许添加子节点
                return type !== 'inner';
            } else {
                return true;
            }
        },
        // 构建保存数据
        buildEditNodeForm(parameter) {
            if (this.editNodeForm.length > 0) {
                for (let i = 0; i < this.editNodeForm.length; i++) {
                    if (this.editNodeForm[i].id === parameter.id) {
                        this.editNodeForm[i] = parameter;
                        break;
                    } else if (i === this.editNodeForm.length - 1) {
                        this.editNodeForm.push(parameter);
                        break;
                    }
                }
            } else {
                this.editNodeForm.push(parameter);
            }
        },
        // 构建子级数据
        buildChildren(nodeData) {
            // 同时变更旗下子级节点
            if (nodeData.children) {
                for (let i = 0; i < nodeData.children.length; i++) {
                    const childrenData = nodeData.children[i];
                    let parameterChildren = {
                        id: childrenData.id,
                        name: childrenData.label,
                        parentId: nodeData.id,
                        parentName: nodeData.label,
                        sortNo: childrenData.sortNo
                    }
                    this.buildEditNodeForm(parameterChildren);
                    // 判断子级数否拥有数据
                    if (childrenData.children) {
                        this.buildChildren(childrenData);
                    }
                }
            }
        },
        // 拖拽成功完成时触发的事件
        handleDrop(draggingNode, dropNode, type, event) {
            // 被拖拽的节点数据
            const draggingNodeData = draggingNode.data;
            // console.log('被拖拽的节点数据: ', draggingNodeData);
            // 结束拖拽时最后进入的节点数据
            const dropNodeData = dropNode.data;
            // console.log('结束拖拽时最后产生Type关系的节点数据: ', dropNodeData);
            // 放置在目标节点的 类型
            // console.log('放置在目标节点的 类型: ', type);
            // 'inner' 插入至目标节点
            if (type === 'inner') {
                let parameter = {
                    id: draggingNodeData.id,
                    name: draggingNodeData.label,
                    parentId: dropNodeData.id,
                    parentName: dropNodeData.label,
                    sortNo: 1.00
                }
                this.buildEditNodeForm(parameter);
                // 同时变更旗下子级节点
                if (draggingNodeData.children) {
                    this.buildChildren(draggingNodeData);
                }
            }
            // 'prev' 放置在目标节点前
            if (type === 'before') {
                let parameter = {
                    id: draggingNodeData.id,
                    name: draggingNodeData.label,
                    parentId: dropNodeData.parentId ? dropNodeData.parentId : null,
                    sortNo: dropNodeData.sortNo - 0.1
                }
                this.buildEditNodeForm(parameter);
                // 同时变更旗下子级节点
                if (draggingNodeData.children) {
                    this.buildChildren(draggingNodeData);
                }
            }
            // 'next' 放置在目标节点后
            if (type === 'after') {
                let parameter = {
                    id: draggingNodeData.id,
                    name: draggingNodeData.label,
                    parentId: dropNodeData.parentId ? dropNodeData.parentId : null,
                    sortNo: dropNodeData.sortNo + 0.1
                }
                this.buildEditNodeForm(parameter);
                // 同时变更旗下子级节点
                if (draggingNodeData.children) {
                    this.buildChildren(draggingNodeData);
                }
            }
        },
        // 保存
        updateBach() {
            let editNodeParam = this.editNodeForm;
            axios.put('/sys/organization/edit', JSON.parse(JSON.stringify(editNodeParam)))
                .then((res) => {
                    if (res.code === 6666) {
                        this.submitQueryForm();
                        this.$message({
                            type: 'success',
                            message: '保存成功'
                        });
                    } else {
                        this.$message.error(res.message);
                    }
                });
        },
        // 查询树机构
        queryOrganizationTree() {
            axios.get('/sys/organization/tree')
                .then((res) => {
                    if (res.code === 6666) {
                        this.parentOrganization = res.data;
                    }
                });
        },
        // 开启新增表单
        openInsertDialog(node, data) {
            this.insertDialog = true;
            if (this.$refs.insertForm !== undefined) {
                this.$refs.insertForm.resetFields();
            }
            if (data) {
                // 添加下级
                this.insertForm.status = 0;
                this.insertForm.parentId = data.id;
                this.insertForm.parentName = data.label;
            } else {
                // 添加
                this.insertForm.status = 0;
                this.insertForm.parentId = '';
                this.insertForm.parentName = '';
                this.queryOrganizationTree();
            }
        },
        // 新增
        insert() {
            this.$refs.insertForm.validate((valid) => {
                if (valid) {
                    this.insertDialog = false;
                    let insertParam = this.insertForm;
                    axios.post('/sys/organization/add', JSON.parse(JSON.stringify(insertParam)))
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
        openRemoveMessageBox(node, data) {
            this.$confirm('您确定删除吗', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                if (data.children) {
                    this.$message({
                        type: 'warning',
                        message: '父机构，禁止删除'
                    });
                } else {
                    let params = {idList: data.id};
                    axios.delete('/sys/organization/delete/', {params})
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
        // 更新
        update() {
            this.$refs.updateForm.validate((valid) => {
                if (valid) {
                    this.updateDialog = false;
                    let updateParam = this.updateForm;
                    axios.put('/sys/organization/update', JSON.parse(JSON.stringify(updateParam)))
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
        this.statusItem = dictList.find(dict => dict.dictCode === "status").itemList;
    }
});