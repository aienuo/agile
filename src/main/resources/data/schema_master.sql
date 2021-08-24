/*
 Navicat Premium Data Transfer

 Source Server         : ContOS_7_64_3306
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 192.168.1.102:3306
 Source Schema         : agile

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 28/12/2020 11:17:42
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
    `update_time` datetime                                               NULL     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `index_table_dict_code` (`dict_code`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '字典-项'
  ROW_FORMAT = DYNAMIC;

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
    `value`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '字典项值',
    `sort_no`     double(3, 2)                                           NULL     DEFAULT NULL COMMENT '排序',
    `disabled`    tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '禁用状态(true1-禁用，false0-不禁用）',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '描述',
    `create_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '创建人',
    `create_time` datetime                                               NOT NULL COMMENT '创建时间',
    `update_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL     DEFAULT NULL COMMENT '更新人',
    `update_time` datetime                                               NULL     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_table_dict_id` (`dict_id`) USING BTREE,
    INDEX `index_table_sort_no` (`sort_no`) USING BTREE,
    INDEX `index_table_dict_disabled` (`disabled`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '字典-值'
  ROW_FORMAT = DYNAMIC;

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
    `create_time` datetime(0)                                            NOT NULL COMMENT '创建时间',
    `update_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL     DEFAULT NULL COMMENT '更新人',
    `update_time` datetime(0)                                            NULL     DEFAULT NULL COMMENT '更新时间',
    `del_flag`    tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '删除状态（0-正常，1-已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '文件存放'
  ROW_FORMAT = DYNAMIC;

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
    `update_time` datetime                                               NULL     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '菜单权限'
  ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization`
(
    `id`                bigint(20)                                             NOT NULL COMMENT '主键',
    `parent_id`         bigint(20)                                             NULL     DEFAULT NULL COMMENT '父级编号',
    `organization_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '组织机构名称',
    `organization_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '组织机构编号',
    `description`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '描述',
    `sort_order`        double(3, 2)                                           NULL     DEFAULT NULL COMMENT '排序号',
    `create_by`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NOT NULL COMMENT '创建人',
    `create_time`       datetime(0)                                            NOT NULL COMMENT '创建时间',
    `update_by`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL     DEFAULT NULL COMMENT '更新人',
    `update_time`       datetime(0)                                            NULL     DEFAULT NULL COMMENT '更新时间',
    `del_flag`          tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '删除状态（0-正常，1-已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '组织机构'
  ROW_FORMAT = DYNAMIC;

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
    `create_time`      datetime(0)                                           NOT NULL COMMENT '创建时间',
    `update_by`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '更新人',
    `update_time`      datetime(0)                                           NULL     DEFAULT NULL COMMENT '更新时间',
    `del_flag`         tinyint(1)                                            NOT NULL DEFAULT 0 COMMENT '删除状态（0-正常，1-已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '富文本'
  ROW_FORMAT = COMPACT;

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
    `create_time` datetime(0)                                            NOT NULL COMMENT '创建时间',
    `update_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime(0)                                            NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `unique_rolename` (`role_name`) USING BTREE COMMENT '角色名唯一约束'
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '角色管理'
  ROW_FORMAT = COMPACT;

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
    `create_time` datetime(0)                                            NOT NULL COMMENT '创建时间',
    `update_by`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime(0)                                            NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '角色菜单权限关联'
  ROW_FORMAT = COMPACT;

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
    `create_time`     datetime(0)                                            NOT NULL COMMENT '创建时间',
    `update_by`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin  NULL     DEFAULT NULL COMMENT '更新人',
    `update_time`     datetime(0)                                            NULL     DEFAULT NULL COMMENT '更新时间',
    `del_flag`        tinyint(1)                                             NOT NULL DEFAULT 0 COMMENT '删除状态（0-正常，1-已删除）',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `unique_username` (`username`) USING BTREE COMMENT '用户名唯一约束'
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '系统用户'
  ROW_FORMAT = COMPACT;

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
    `create_time`     datetime(0)                                           NOT NULL COMMENT '创建时间',
    `update_by`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL     DEFAULT NULL COMMENT '更新人',
    `update_time`     datetime(0)                                           NULL     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '用户组织机构关联'
  ROW_FORMAT = DYNAMIC;

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
    `create_time` datetime(0)                                           NOT NULL COMMENT '创建时间',
    `update_by`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime(0)                                           NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT = '用户角色关联'
  ROW_FORMAT = COMPACT;


INSERT INTO `sys_user` (`id`, `username`, `password`, `salt`, `avatar`, `realname`, `identity_number`, `email`, `phone`, `birthday`, `sex`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`)
VALUES (1400748678460739585, 'admin', 'aa42648a3fc79393', '87de877e', 'admin', '超级管理员', '110101201801017389', 'admin@qq.com', '12345678910', '2018-01-01', 2, 0, 'admin', '2021-07-22 14:40:16', NULL, NULL, 0);

INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960194, '超级管理员1', 'A001', '超管', 'admin', '2021-07-23 09:47:36', NULL, NULL);
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960195, '超级管理员2', 'A002', '超管', 'admin', '2021-07-23 09:47:36', NULL, NULL);
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960196, '超级管理员3', 'A003', '超管', 'admin', '2021-07-23 09:47:36', NULL, NULL);

INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960194, 1400748678460739585, 1418031238717960194, 'admin', '2021-07-23 09:49:37', NULL, NULL);
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960195, 1400748678460739585, 1418031238717960195, 'admin', '2021-07-23 09:49:37', NULL, NULL);
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960196, 1400748678460739585, 1418031238717960196, 'admin', '2021-07-23 09:49:37', NULL, NULL);

INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960190, NULL, 1, 0, '系统管理', 'el-icon-setting', NULL, '测试', 1.00, 0, '系统管理', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960191, 1418031238717960190, 0, 1, '用户管理', 'el-icon-user', '/view/sys/user.html', '测试', 1.00, 0, '用户管理', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960192, 1418031238717960191, 0, 2, '添加', NULL, '/sys/user/add', '测试', 1.00, 0, '用户添加', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960193, 1418031238717960191, 0, 2, '冻结', NULL, '/sys/user/freeze', '测试', 2.00, 0, '用户冻结', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960194, 1418031238717960191, 0, 2, '解冻', NULL, '/sys/user/unFreeze', '测试', 3.00, 0, '用户解冻', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960195, 1418031238717960191, 0, 2, '密码重置', NULL, '/sys/user/reset', '测试', 4.00, 0, '密码重置', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960196, 1418031238717960191, 0, 2, '删除', NULL, '/sys/user/remove', '测试', 5.00, 0, '用户删除', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960197, 1418031238717960191, 0, 2, '修改', NULL, '/sys/user/update', '测试', 6.00, 0, '用户修改', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960198, 1418031238717960190, 0, 1, '角色管理', 'el-icon-key', '/view/sys/role.html', '测试', 2.00, 0, '角色管理', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960199, 1418031238717960198, 0, 2, '添加', NULL, '/sys/role/add', '测试', 1.00, 0, '角色添加', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960200, 1418031238717960198, 0, 2, '修改', NULL, '/sys/role/update', '测试', 2.00, 0, '角色修改', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960201, 1418031238717960198, 0, 2, '删除', NULL, '/sys/role/delete', '测试', 3.00, 0, '角色删除', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960202, 1418031238717960190, 0, 1, '菜单管理', 'el-icon-menu', '/view/sys/menu.html', '测试', 3.00, 0, '菜单管理', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960203, 1418031238717960202, 0, 2, '添加', NULL, '/sys/menu/add', '测试', 1.00, 0, '菜单添加', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960204, 1418031238717960202, 0, 2, '修改', NULL, '/sys/menu/update', '测试', 2.00, 0, '菜单修改', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960205, 1418031238717960202, 0, 2, '删除', NULL, '/sys/menu/delete', '测试', 3.00, 0, '菜单删除', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960206, 1418031238717960190, 0, 1, '字典管理', 'el-icon-notebook-2', '/view/sys/dict.html', '测试', 1.00, 0, '用户管理', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960207, 1418031238717960206, 0, 2, '添加', NULL, '/sys/dict/add', '测试', 1.00, 0, '项添加', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960208, 1418031238717960206, 0, 2, '修改', NULL, '/sys/dict/update', '测试', 2.00, 0, '项修改', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960209, 1418031238717960206, 0, 2, '删除', NULL, '/sys/dict/delete', '测试', 3.00, 0, '项删除', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960210, 1418031238717960206, 0, 2, '添加', NULL, '/sys/dict/item/add', '测试', 4.00, 0, '值添加', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960211, 1418031238717960206, 0, 2, '修改', NULL, '/sys/dict/item/update', '测试', 5.00, 0, '值修改', 'admin', '2021-07-23 09:57:25', '', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `leaf_type`, `menu_type`, `name`, `icon`, `url`, `component`, `sort_no`, `status`, `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960212, 1418031238717960206, 0, 2, '删除', NULL, '/sys/dict/item/delete', '测试', 6.00, 0, '值删除', 'admin', '2021-07-23 09:57:25', '', NULL);


INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960190, 1418031238717960194, 1418031238717960190, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960191, 1418031238717960194, 1418031238717960191, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960192, 1418031238717960194, 1418031238717960192, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960193, 1418031238717960194, 1418031238717960193, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960194, 1418031238717960194, 1418031238717960194, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960195, 1418031238717960194, 1418031238717960195, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960196, 1418031238717960194, 1418031238717960196, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960197, 1418031238717960194, 1418031238717960197, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960198, 1418031238717960194, 1418031238717960198, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960199, 1418031238717960194, 1418031238717960199, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960200, 1418031238717960194, 1418031238717960200, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960201, 1418031238717960194, 1418031238717960201, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960202, 1418031238717960194, 1418031238717960202, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960203, 1418031238717960194, 1418031238717960203, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960204, 1418031238717960194, 1418031238717960204, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960205, 1418031238717960194, 1418031238717960205, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960206, 1418031238717960194, 1418031238717960206, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960207, 1418031238717960194, 1418031238717960207, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960208, 1418031238717960194, 1418031238717960208, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960209, 1418031238717960194, 1418031238717960209, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960210, 1418031238717960194, 1418031238717960210, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960211, 1418031238717960194, 1418031238717960211, 'admin', '2021-07-23 10:13:48', NULL, NULL);
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (1418031238717960212, 1418031238717960194, 1418031238717960212, 'admin', '2021-07-23 10:13:48', NULL, NULL);


SET FOREIGN_KEY_CHECKS = 1;
