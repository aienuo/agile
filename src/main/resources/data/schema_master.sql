/*
 Navicat Premium Data Transfer

 Source Server         : 虚拟机_ContOS_7_64_3306
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 192.168.1.102:3306
 Source Schema         : agile

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 09/09/2021 10:40:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`          bigint(20)                                             NOT NULL,
    `dict_name`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '字典名称',
    `dict_code`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '字典编码',
    `dict_type`   tinyint(1) UNSIGNED ZEROFILL                           NOT NULL DEFAULT 0 COMMENT '字典类型（0-String，1-Number）',
    `status`      tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '冻结状态(0-正常，1-冻结）',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '描述',
    `create_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '创建人',
    `create_time` datetime                                               NOT NULL COMMENT '创建时间',
    `update_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL     DEFAULT NULL COMMENT '更新人',
    `update_time` datetime                                               NULL     DEFAULT NULL COMMENT '更新时间（乐观锁）',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `index_table_dict_code` (`dict_code`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '字典-项'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict`
VALUES (1427200729583525890, '菜单类型', 'menuType', 1, 0, '菜单类型（0-一级菜单，1-子菜单，2-按钮权限）', 'admin', '2021-08-16 17:29:05', NULL, NULL);
INSERT INTO `sys_dict`
VALUES (1427202386623012865, '冻结状态', 'status', 1, 0, '冻结状态（0-正常，1-冻结）', 'admin', '2021-08-16 17:35:40', NULL, NULL);
INSERT INTO `sys_dict`
VALUES (1427205456849346561, '字典类型', 'dictType', 1, 0, '字典类型（0-String，1-Number）', 'admin', '2021-08-16 17:47:52', NULL, NULL);
INSERT INTO `sys_dict`
VALUES (1427892414210744321, '性别', 'sex', 1, 0, '性别(2-默认未知，1-男，0-女)', 'admin', '2021-08-18 15:17:35', NULL, NULL);
INSERT INTO `sys_dict`
VALUES (1434715993741058050, '按钮类型', 'buttonType', 0, 0, '按钮类型（primary / success / warning / danger / info / text）', 'admin', '2021-09-06 11:12:03', NULL, NULL);
INSERT INTO `sys_dict`
VALUES (1434716063622356994, '按钮尺寸', 'buttonSize', 0, 0, '按钮尺寸（medium / small / mini）', 'admin', '2021-09-06 11:12:20', NULL, NULL);
INSERT INTO `sys_dict`
VALUES (1435494139671130113, '表类型', 'tableType', 1, 0, '表类型（0-单表，1-主表，2-附表）', 'admin', '2021-09-08 14:44:08', NULL, NULL);
INSERT INTO `sys_dict`
VALUES (1435494311331409922, '表单风格', 'formStyle', 0, 0, '表单风格（dialog、drawer）', 'admin', '2021-09-08 14:44:49', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`
(
    `id`          bigint(20)                                             NOT NULL,
    `dict_id`     bigint(20)                                             NOT NULL COMMENT '字典id',
    `parent_id`   bigint(20)                                             NULL     DEFAULT NULL COMMENT '父记录id',
    `name`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '字典项文本',
    `value`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '字典项值',
    `disabled`    tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '禁用状态(true1-禁用，false0-不禁用）',
    `sort_no`     double(3, 2)                                           NULL     DEFAULT NULL COMMENT '排序',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '描述',
    `create_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '创建人',
    `create_time` datetime                                               NOT NULL COMMENT '创建时间',
    `update_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL     DEFAULT NULL COMMENT '更新人',
    `update_time` datetime                                               NULL     DEFAULT NULL COMMENT '更新时间（乐观锁）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '字典-值'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item`
VALUES (1427558447548817410, 1427200729583525890, NULL, '一级菜单', '0', 0, 1.00, '一级菜单', 'admin', '2021-08-17 17:10:31', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1427560069083521026, 1427200729583525890, NULL, '子菜单', '1', 0, 2.00, '子菜单', 'admin', '2021-08-17 17:16:58', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1427560184221360130, 1427200729583525890, NULL, '按钮权限', '2', 0, 3.00, '按钮权限', 'admin', '2021-08-17 17:17:25', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1427560374269468674, 1427202386623012865, NULL, '正常', '0', 0, 1.00, '冻结状态', 'admin', '2021-08-17 17:18:11', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1427560444905742338, 1427202386623012865, NULL, '冻结', '1', 0, 2.00, '冻结状态', 'admin', '2021-08-17 17:18:28', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1427560686434738178, 1427205456849346561, NULL, 'String', '0', 0, 1.00, '字典类型', 'admin', '2021-08-17 17:19:25', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1427560767057649665, 1427205456849346561, NULL, 'Number', '1', 0, 2.00, '字典类型', 'admin', '2021-08-17 17:19:44', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1427892578333859842, 1427892414210744321, NULL, '未知', '2', 0, 1.00, '性别-未知', 'admin', '2021-08-18 15:18:14', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1427892699603771394, 1427892414210744321, NULL, '男 ', '1', 0, 2.00, '性别-男', 'admin', '2021-08-18 15:18:43', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1427892784668450817, 1427892414210744321, NULL, '女 ', '0', 0, 3.10, '性别-女', 'admin', '2021-08-18 15:19:03', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1434716410940088321, 1434715993741058050, NULL, '主要按钮', 'primary', 0, 1.00, '', 'admin', '2021-09-06 11:13:43', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1434716534906937346, 1434715993741058050, NULL, '成功按钮', 'success', 0, 2.00, '', 'admin', '2021-09-06 11:14:12', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1434716600992391170, 1434715993741058050, NULL, '信息按钮', 'info', 0, 3.00, '', 'admin', '2021-09-06 11:14:28', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1434716658315943938, 1434715993741058050, NULL, '警告按钮', 'warning', 0, 4.00, '', 'admin', '2021-09-06 11:14:42', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1434716864386293761, 1434715993741058050, NULL, '危险按钮', 'danger', 0, 5.00, '', 'admin', '2021-09-06 11:15:31', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1434716946993111042, 1434715993741058050, NULL, '文字按钮', 'text', 0, 6.00, '', 'admin', '2021-09-06 11:15:51', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1434717111862812674, 1434716063622356994, NULL, '中等', 'medium', 0, 1.00, '', 'admin', '2021-09-06 11:16:30', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1434717171216408578, 1434716063622356994, NULL, '小型', 'small', 0, 2.00, '', 'admin', '2021-09-06 11:16:44', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1434717226447003649, 1434716063622356994, NULL, '迷你', 'mini', 0, 3.00, '', 'admin', '2021-09-06 11:16:57', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1435494997720870914, 1435494139671130113, NULL, '单表', '0', 0, 1.00, '', 'admin', '2021-09-08 14:47:32', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1435495044965511169, 1435494139671130113, NULL, '主表', '1', 0, 2.00, '', 'admin', '2021-09-08 14:47:44', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1435495089135726593, 1435494139671130113, NULL, '附表', '2', 0, 3.00, '', 'admin', '2021-09-08 14:47:54', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1435497854545211393, 1435494311331409922, NULL, '弹窗', 'dialog', 0, 1.00, '', 'admin', '2021-09-08 14:58:53', NULL, NULL);
INSERT INTO `sys_dict_item`
VALUES (1435497963500646401, 1435494311331409922, NULL, '抽屉', 'drawer', 0, 1.00, '', 'admin', '2021-09-08 14:59:19', NULL, NULL);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`
(
    `id`          bigint(20)                                             NOT NULL COMMENT 'ID',
    `file_name`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文件名称',
    `real_name`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '文件上传名',
    `file_type`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '文件类型',
    `file_url`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文件地址',
    `file_size`   bigint(20)                                             NULL     DEFAULT NULL COMMENT '文件大小',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '文件描述',
    `create_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '创建人',
    `create_time` datetime                                               NOT NULL COMMENT '创建时间',
    `update_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL     DEFAULT NULL COMMENT '更新人',
    `update_time` datetime                                               NULL     DEFAULT NULL COMMENT '更新时间（乐观锁）',
    `del_flag`    tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '删除状态（0-正常，1-已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '文件存放'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`          bigint(20)                                             NOT NULL COMMENT '主键id',
    `parent_id`   bigint(20)                                             NULL     DEFAULT NULL COMMENT '父记录id',
    `leaf_type`   tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '是否叶子节点（0-不是，1-是）',
    `menu_type`   tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '菜单类型（0-一级菜单，1-子菜单，2-按钮权限）',
    `button_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL     DEFAULT NULL COMMENT '按钮类型（primary / success / warning / danger / info / text）',
    `button_size` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL     DEFAULT NULL COMMENT '按钮尺寸（medium / small / mini）',
    `name`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单名称',
    `icon`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '菜单图标',
    `url`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '路径',
    `component`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '组件',
    `sort_no`     double(3, 2) UNSIGNED                                  NOT NULL COMMENT '菜单排序',
    `status`      tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '按钮权限冻结状态(0-正常，1-冻结）',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '菜单描述',
    `create_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '创建人',
    `create_time` datetime                                               NOT NULL COMMENT '创建时间',
    `update_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL     DEFAULT NULL COMMENT '更新人',
    `update_time` datetime                                               NULL     DEFAULT NULL COMMENT '更新时间（乐观锁）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '菜单权限'
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu`
VALUES (1418031238717960190, NULL, 1, 0, NULL, NULL, '系统管理', 'el-icon-setting', NULL, '测试', 1.00, 0, '系统管理', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960191, 1418031238717960190, 0, 1, NULL, NULL, '用户管理', 'el-icon-user', '/view/sys/user.html', '测试', 2.00, 0, '用户管理', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960192, 1418031238717960191, 0, 2, NULL, NULL, '添加', NULL, '/sys/user/add', '测试', 1.00, 0, '用户添加', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960193, 1418031238717960191, 0, 2, NULL, NULL, '冻结', NULL, '/sys/user/freeze', '测试', 2.00, 0, '用户冻结', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960194, 1418031238717960191, 0, 2, NULL, NULL, '解冻', NULL, '/sys/user/unFreeze', '测试', 3.00, 0, '用户解冻', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960195, 1418031238717960191, 0, 2, NULL, NULL, '密码重置', NULL, '/sys/user/reset', '测试', 4.00, 0, '密码重置', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960196, 1418031238717960191, 0, 2, NULL, NULL, '删除', NULL, '/sys/user/remove', '测试', 5.00, 0, '用户删除', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960197, 1418031238717960191, 0, 2, NULL, NULL, '修改', NULL, '/sys/user/update', '测试', 6.00, 0, '用户修改', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960198, 1418031238717960190, 0, 1, NULL, NULL, '角色管理', 'el-icon-key', '/view/sys/role.html', '测试', 3.00, 0, '角色管理', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960199, 1418031238717960198, 0, 2, NULL, NULL, '添加', NULL, '/sys/role/add', '测试', 1.00, 0, '角色添加', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960200, 1418031238717960198, 0, 2, NULL, NULL, '修改', NULL, '/sys/role/update', '测试', 2.00, 0, '角色修改', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960201, 1418031238717960198, 0, 2, NULL, NULL, '删除', NULL, '/sys/role/delete', '测试', 3.00, 0, '角色删除', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960202, 1418031238717960190, 0, 1, NULL, NULL, '菜单管理', 'el-icon-menu', '/view/sys/menu.html', '测试', 4.00, 0, '菜单管理', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960203, 1418031238717960202, 0, 2, NULL, NULL, '添加', NULL, '/sys/menu/add', '测试', 1.00, 0, '菜单添加', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960204, 1418031238717960202, 0, 2, NULL, NULL, '修改', NULL, '/sys/menu/update', '测试', 2.00, 0, '菜单修改', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960205, 1418031238717960202, 0, 2, NULL, NULL, '删除', NULL, '/sys/menu/delete', '测试', 3.00, 0, '菜单删除', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960206, 1418031238717960190, 0, 1, NULL, NULL, '字典管理', 'el-icon-notebook-2', '/view/sys/dict.html', '测试', 1.00, 0, '字典管理', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960207, 1418031238717960206, 0, 2, NULL, NULL, '添加', NULL, '/sys/dict/add', '测试', 1.00, 0, '项添加', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960208, 1418031238717960206, 0, 2, NULL, NULL, '修改', NULL, '/sys/dict/update', '测试', 2.00, 0, '项修改', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960209, 1418031238717960206, 0, 2, NULL, NULL, '删除', NULL, '/sys/dict/delete', '测试', 3.00, 0, '项删除', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960210, 1418031238717960206, 0, 2, NULL, NULL, '添加', NULL, '/sys/dict/item/add', '测试', 4.00, 0, '值添加', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960211, 1418031238717960206, 0, 2, NULL, NULL, '修改', NULL, '/sys/dict/item/update', '测试', 5.00, 0, '值修改', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960212, 1418031238717960206, 0, 2, NULL, NULL, '删除', NULL, '/sys/dict/item/delete', '测试', 6.00, 0, '值删除', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960213, 1418031238717960190, 0, 1, NULL, NULL, '组织机构', 'el-icon-office-building', '/view/sys/organization.html', '测试', 5.00, 0, '组织机构', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960214, 1418031238717960213, 0, 2, NULL, NULL, '编辑', NULL, '/sys/organization/edit', '测试', 1.00, 0, '机构编辑', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960215, 1418031238717960213, 0, 2, NULL, NULL, '新增', NULL, '/sys/organization/add', '测试', 3.00, 0, '机构新增', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960216, 1418031238717960213, 0, 2, NULL, NULL, '删除', NULL, '/sys/organization/delete', '测试', 5.00, 0, '机构删除', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960217, 1418031238717960213, 0, 2, NULL, NULL, '保存', NULL, '/sys/organization/updateBach', '测试', 2.00, 0, '机构保存', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960218, 1418031238717960213, 0, 2, NULL, NULL, '修改', NULL, '/sys/organization/update', '测试', 4.00, 0, '机构修改', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960219, NULL, 1, 0, NULL, NULL, '在线开发', 'el-icon-loading', '', '测试', 2.00, 0, '在线开发', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960220, 1418031238717960219, 0, 1, NULL, NULL, '表单开发', 'el-icon-loading', '/view/online/develop.html', '测试', 1.00, 0, '表单开发', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960221, 1418031238717960220, 0, 2, NULL, NULL, '添加', NULL, '/online/develop/add', '测试', 1.00, 0, '表单添加', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960222, 1418031238717960220, 0, 2, NULL, NULL, '修改', NULL, '/online/develop/update', '测试', 2.00, 0, '表单修改', 'admin', '2021-07-23 09:57:25', NULL, NULL);
INSERT INTO `sys_menu`
VALUES (1418031238717960223, 1418031238717960220, 0, 2, NULL, NULL, '删除', NULL, '/online/develop/delete', '测试', 3.00, 0, '表单删除', 'admin', '2021-07-23 09:57:25', NULL, NULL);

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization`
(
    `id`                bigint(20) UNSIGNED ZEROFILL                           NOT NULL COMMENT '主键',
    `parent_id`         bigint(20)                                             NULL     DEFAULT NULL COMMENT '父级编号',
    `organization_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '组织机构名称',
    `organization_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '组织机构编号',
    `sort_no`           double(3, 2)                                           NULL     DEFAULT NULL COMMENT '排序号',
    `status`            tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '冻结状态(0-正常，1-冻结）',
    `description`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '描述',
    `create_by`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '创建人',
    `create_time`       datetime                                               NOT NULL COMMENT '创建时间',
    `update_by`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL     DEFAULT NULL COMMENT '更新人',
    `update_time`       datetime                                               NULL     DEFAULT NULL COMMENT '更新时间（乐观锁）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '组织机构'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO `sys_organization`
VALUES (01430335266362490881, NULL, '第一级1', 'A001', 0.90, 0, '第一级', 'admin', '2021-08-25 09:04:37', NULL, NULL);
INSERT INTO `sys_organization`
VALUES (01431153856208719873, NULL, '第一级2', 'A002', 1.00, 0, '', 'admin', '2021-08-27 15:17:24', NULL, NULL);
INSERT INTO `sys_organization`
VALUES (01431430761046618113, 1431153856208719873, '第二级001', 'A002A001', 1.00, 0, '商标注册急速申报', 'admin', '2021-08-28 09:37:43', NULL, NULL);
INSERT INTO `sys_organization`
VALUES (01431430936276250626, 1431430761046618113, '第三级别001', 'A002A001A001', 1.00, 0, '', 'admin', '2021-08-28 09:38:25', NULL, NULL);

-- ----------------------------
-- Table structure for sys_rich_text
-- ----------------------------
DROP TABLE IF EXISTS `sys_rich_text`;
CREATE TABLE `sys_rich_text`
(
    `id`               bigint(20)                                            NOT NULL COMMENT 'ID',
    `text_type`        tinyint(2)                                            NULL     DEFAULT NULL COMMENT '文本类型',
    `primary_table_id` bigint(20)                                            NULL     DEFAULT NULL COMMENT '主表id',
    `content`          longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin    NOT NULL COMMENT '文本内容',
    `create_by`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
    `create_time`      datetime                                              NOT NULL COMMENT '创建时间',
    `update_by`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '更新人',
    `update_time`      datetime                                              NULL     DEFAULT NULL COMMENT '更新时间（乐观锁）',
    `del_flag`         tinyint(1)                                            NOT NULL DEFAULT 0 COMMENT '删除状态（0-正常，1-已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '富文本'
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_rich_text
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint(20)                                             NOT NULL COMMENT '主键id',
    `role_name`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
    `role_code`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色编码',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
    `create_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '创建人',
    `create_time` datetime                                               NOT NULL COMMENT '创建时间',
    `update_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime                                               NULL DEFAULT NULL COMMENT '更新时间（乐观锁）',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `unique_rolename` (`role_name`) USING BTREE COMMENT '角色名唯一约束'
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '角色管理'
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1418031238717960194, '超级管理员', 'A001', '全系统高权限，分配注意', 'admin', '2021-07-23 09:47:36', NULL, NULL);
INSERT INTO `sys_role`
VALUES (1418031238717960195, '超级管理员2', 'A002', '超管', 'admin', '2021-07-23 09:47:36', NULL, NULL);
INSERT INTO `sys_role`
VALUES (1418031238717960196, '超级管理员3', 'A003', '超管', 'admin', '2021-07-23 09:47:36', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`          bigint(20)                                             NOT NULL COMMENT 'ID',
    `role_id`     bigint(20)                                             NOT NULL COMMENT '角色id',
    `menu_id`     bigint(20)                                             NOT NULL COMMENT '权限id',
    `create_by`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
    `create_time` datetime                                               NOT NULL COMMENT '创建时间',
    `update_by`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime                                               NULL DEFAULT NULL COMMENT '更新时间（乐观锁）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '角色菜单权限关联'
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu`
VALUES (1418031238717960190, 1418031238717960194, 1418031238717960190, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960191, 1418031238717960194, 1418031238717960191, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960192, 1418031238717960194, 1418031238717960192, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960193, 1418031238717960194, 1418031238717960193, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960194, 1418031238717960194, 1418031238717960194, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960195, 1418031238717960194, 1418031238717960195, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960196, 1418031238717960194, 1418031238717960196, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960197, 1418031238717960194, 1418031238717960197, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960198, 1418031238717960194, 1418031238717960198, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960199, 1418031238717960194, 1418031238717960199, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960200, 1418031238717960194, 1418031238717960200, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960201, 1418031238717960194, 1418031238717960201, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960202, 1418031238717960194, 1418031238717960202, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960203, 1418031238717960194, 1418031238717960203, 'admin', '2021-08-07 17:58:30', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960204, 1418031238717960194, 1418031238717960204, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960205, 1418031238717960194, 1418031238717960205, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960206, 1418031238717960194, 1418031238717960206, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960207, 1418031238717960194, 1418031238717960207, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960208, 1418031238717960194, 1418031238717960208, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960209, 1418031238717960194, 1418031238717960209, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960210, 1418031238717960194, 1418031238717960210, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960211, 1418031238717960194, 1418031238717960211, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960212, 1418031238717960194, 1418031238717960212, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960213, 1418031238717960194, 1418031238717960213, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960214, 1418031238717960194, 1418031238717960214, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960215, 1418031238717960194, 1418031238717960215, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960216, 1418031238717960194, 1418031238717960216, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960217, 1418031238717960194, 1418031238717960217, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960218, 1418031238717960194, 1418031238717960218, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960219, 1418031238717960194, 1418031238717960219, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960220, 1418031238717960194, 1418031238717960220, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960221, 1418031238717960194, 1418031238717960221, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960222, 1418031238717960194, 1418031238717960222, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu`
VALUES (1418031238717960223, 1418031238717960194, 1418031238717960223, 'admin', '2021-07-23 10:13:48', NULL, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`              bigint(20)                                             NOT NULL COMMENT '主键',
    `username`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '登录账号',
    `password`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录密码',
    `salt`            varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT 'md5密码盐',
    `avatar`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '头像',
    `realname`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '真实姓名',
    `identity_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '身份证号码',
    `email`           varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL     DEFAULT NULL COMMENT '电子邮箱',
    `phone`           varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL     DEFAULT NULL COMMENT '手机号码',
    `birthday`        date                                                   NULL     DEFAULT NULL COMMENT '生日',
    `sex`             tinyint(1)                                             NOT NULL DEFAULT 2 COMMENT '性别(2-默认未知，1-男，0-女)',
    `status`          tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '冻结状态(0-正常，1-冻结）',
    `create_by`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '创建人',
    `create_time`     datetime                                               NOT NULL COMMENT '创建时间',
    `update_by`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL     DEFAULT NULL COMMENT '更新人',
    `update_time`     datetime                                               NULL     DEFAULT NULL COMMENT '更新时间（乐观锁）',
    `del_flag`        tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '删除状态（0-正常，1-已删除）',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `unique_username` (`username`) USING BTREE COMMENT '用户名唯一约束'
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '系统用户'
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1400748678460739585, 'admin', 'aa42648a3fc79393', '87de877e', 'https://avatars.githubusercontent.com/u/47814002?s=60&v=4', '超级管理员', '110101201801017389', 'admin@qq.com', '12345678910', '2018-01-01', 2, 0, 'admin',
        '2021-07-22 14:40:16', NULL, NULL, 0);
INSERT INTO `sys_user`
VALUES (1423838618178289665, 'liuxin', '11c36cd122c3c71b', '33206d4e', '', '刘鑫', '110101199603238973', '1234@qq.com', '17605481537', '1996-03-23', 1, 0, 'admin', '2021-08-07 10:49:15', NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_user_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_organization`;
CREATE TABLE `sys_user_organization`
(
    `id`              bigint(20)                                            NOT NULL COMMENT '主键',
    `user_id`         bigint(20)                                            NOT NULL COMMENT '用户编号',
    `organization_id` bigint(20)                                            NOT NULL COMMENT '组织机构编号',
    `is_responsible`  tinyint(1)                                            NOT NULL DEFAULT 0 COMMENT '是否负责部门（0-不负责，1-负责）',
    `create_by`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
    `create_time`     datetime                                              NOT NULL COMMENT '创建时间',
    `update_by`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '更新人',
    `update_time`     datetime                                              NULL     DEFAULT NULL COMMENT '更新时间（乐观锁）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '用户组织机构关联'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_organization
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`          bigint(20)                                            NOT NULL COMMENT 'ID',
    `user_id`     bigint(20)                                            NOT NULL COMMENT '用户id',
    `role_id`     bigint(20)                                            NOT NULL COMMENT '角色id',
    `create_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
    `create_time` datetime                                              NOT NULL COMMENT '创建时间',
    `update_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime                                              NULL DEFAULT NULL COMMENT '更新时间（乐观锁）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '用户角色关联'
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role`
VALUES (1418031238717960194, 1400748678460739585, 1418031238717960194, 'admin', '2021-07-23 09:49:37', NULL, NULL);
INSERT INTO `sys_user_role`
VALUES (1418031238717960195, 1400748678460739585, 1418031238717960195, 'admin', '2021-07-23 09:49:37', NULL, NULL);
INSERT INTO `sys_user_role`
VALUES (1418031238717960196, 1400748678460739585, 1418031238717960196, 'admin', '2021-07-23 09:49:37', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
