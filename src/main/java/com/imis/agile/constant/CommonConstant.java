package com.imis.agile.constant;

/**
 * <p>
 * CommonConstant<br>
 *
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年07月20日 10:51
 */
public interface CommonConstant {

    /**
     * 自定义Token<br>
     * eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1OTYxNjUyNzIsInVzZXJuYW1lIjoiYWRtaW5fcmljaGFuZyJ9.R8ZaEbHFP95Arjo9WZZGAtFZngfAWlIQ_URJjq-24Vg
     */
    String X_ACCESS_TOKEN = "X-Access-Token";

    /**
     * 自定义Cookie
     */
    String X_COOKIE_NAME = "X.SESSION.ID";

    /**
     * Token缓存时间：3600000毫秒即一小时
     */
    Long EXPIRE_TIME = 60 * 60 * 1000L;

    /**
     * Max-Age属性指定从现在开始 Cookie 存在的秒数 - 一个小时
     */
    Integer MAX_AGE = 60 * 60;

    /**
     * 文件上传类型
     */
    String UPLOAD_TYPE_LOCAL = "local";

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    Integer USER_FREEZE_0 = 0;
    Integer USER_FREEZE_1 = 1;

    /**
     * 删除状态（0-正常，1-已删除）
     */
    Integer DEL_FLAG_0 = 0;
    Integer DEL_FLAG_1 = 1;

    /**
     * 是否负责部门（0-不负责，1-负责）
     */
    Integer RESPONSIBLE_0 = 0;
    Integer RESPONSIBLE_1 = 1;

    /**
     * 是否叶子节点（0-不是，1-是）
     */
    Integer LEAF_TYPE_0 = 0;
    Integer LEAF_TYPE_1 = 1;

    /**
     * 菜单类型（0-一级菜单，1-子菜单，2-按钮权限）
     */
    Integer MENU_TYPE_0 = 2;
    Integer MENU_TYPE_1 = 1;
    Integer MENU_TYPE_2 = 2;

    /**
     * 权限策略（0-显示，1-禁用）
     */
    Integer PERMS_TYPE_0 = 0;
    Integer PERMS_TYPE_1 = 1;

    /**
     * 字典类型（0-String，1-Number）
     */
    Integer DICT_TYPE_0 = 0;
    Integer DICT_TYPE_1 = 1;

}
