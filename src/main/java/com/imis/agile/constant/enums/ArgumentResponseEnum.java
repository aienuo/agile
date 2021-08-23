package com.imis.agile.constant.enums;

import com.imis.agile.constant.CommonExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * ArgumentResponseEnum<br>
 * 参数校验异常返回结果<br>
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
@Getter
@AllArgsConstructor
public enum ArgumentResponseEnum implements CommonExceptionAssert {
    /**
     * 200 - 逻辑错误类型   00 - 模块   01 - 功能   00 - 错误码
     */
    COMMON_EXCEPTION(200000000, "参数校验异常"),
    NULL_POINTER_EXCEPTION(200000001, "空指针异常"),

    /**
     * 200 01 00 00 - 用户管理 - 登录
     */
    USER_VALID_ERROR_LOGIN_01(200010001, "登录失败，用户不存在"),
    USER_VALID_ERROR_LOGIN_02(200010002, "登录失败，账号或密码不正确"),
    USER_VALID_ERROR_LOGIN_03(200010003, "登录失败，账号注销，请联系管理员"),
    USER_VALID_ERROR_LOGIN_04(200010004, "登录失败，账号冻结，请联系管理员"),

    /**
     * 200 01 01 00 - 用户管理 - 添加
     */
    USER_VALID_ERROR_ADD_01(200010101, "用户添加失败，请确认信息后重新添加"),
    USER_VALID_ERROR_ADD_02(200010102, "帐号&身份证号码存在重复"),
    USER_VALID_ERROR_ADD_03(200010103, "手机号码存在重复"),
    USER_VALID_ERROR_ADD_04(200010104, "电子邮箱存在重复"),
    /**
     * 200 01 02 00 - 用户管理 - 更新
     */
    USER_VALID_ERROR_UPDATE_01(200010201, "用户更新失败，请确认信息后重新更新"),
    USER_VALID_ERROR_UPDATE_02(200010202, "用户更新失败，用户信息不存在"),
    USER_VALID_ERROR_UPDATE_03(200010203, "身份证号码存在重复"),
    USER_VALID_ERROR_UPDATE_04(200010204, "手机号码存在重复"),
    USER_VALID_ERROR_UPDATE_05(200010205, "电子邮箱存在重复"),
    USER_VALID_ERROR_UPDATE_06(200010206, "用户冻结失败，用户信息不存在"),
    USER_VALID_ERROR_UPDATE_07(200010207, "用户冻结失败，部分用户不存在"),
    USER_VALID_ERROR_UPDATE_08(200010208, "用户冻结失败，请确认信息后重新冻结"),
    USER_VALID_ERROR_UPDATE_09(200010209, "用户解冻失败，用户信息不存在"),
    USER_VALID_ERROR_UPDATE_10(200010210, "用户解冻失败，部分用户不存在"),
    USER_VALID_ERROR_UPDATE_11(200010211, "用户解冻失败，请确认信息后重新解冻"),
    /**
     * 200 01 03 00 - 用户管理 - 删除
     */
    USER_VALID_ERROR_DELETE_01(200010301, "用户删除失败，请确认信息后重新删除"),
    USER_VALID_ERROR_DELETE_02(200010302, "用户删除失败，用户信息不存在"),
    USER_VALID_ERROR_DELETE_03(200010303, "用户删除失败，部分用户不存在"),

    /**
     * 200 02 01 00 - 角色管理 - 添加
     */
    ROLE_VALID_ERROR_ADD_01(200020101, "角色添加失败，请确认信息后重新添加"),
    ROLE_VALID_ERROR_ADD_02(200020102, "角色名称存在重复"),
    /**
     * 200 02 02 00 - 角色管理 - 更新
     */
    ROLE_VALID_ERROR_UPDATE_01(200020201, "角色更新失败，请确认信息后重新更新"),
    ROLE_VALID_ERROR_UPDATE_02(200020202, "角色更新失败，角色信息不存在"),
    ROLE_VALID_ERROR_UPDATE_03(200020203, "角色名称存在重复"),
    /**
     * 200 02 03 00 - 角色管理 - 删除
     */
    ROLE_VALID_ERROR_DELETE_01(200020301, "角色删除失败，请确认信息后重新删除"),
    ROLE_VALID_ERROR_DELETE_02(200020302, "角色删除失败，角色已被分配给用户"),
    ROLE_VALID_ERROR_DELETE_03(200020303, "角色删除失败，角色与菜单权限关联关系解除失败"),

    /**
     * 200 03 01 00 - 菜单权限 - 添加
     */
    MENU_VALID_ERROR_ADD_01(200030101, "菜单权限添加失败，请确认信息后重新添加"),
    MENU_VALID_ERROR_ADD_02(200030102, "菜单权限名称存在重复"),
    /**
     * 200 03 02 00 - 菜单权限 - 更新
     */
    MENU_VALID_ERROR_UPDATE_01(200030201, "菜单权限更新失败，请确认信息后重新更新"),
    MENU_VALID_ERROR_UPDATE_02(200030202, "菜单权限更新失败，菜单权限信息不存在"),
    MENU_VALID_ERROR_UPDATE_03(200030203, "菜单权限名称存在重复"),
    /**
     * 200 03 03 00 - 菜单权限 - 删除
     */
    MENU_VALID_ERROR_DELETE_01(200030301, "菜单权限删除失败，请确认信息后重新删除"),
    MENU_VALID_ERROR_DELETE_02(200030302, "菜单权限删除失败，部分菜单权限下存在子节点"),
    MENU_VALID_ERROR_DELETE_03(200030303, "菜单权限删除失败，部分菜单权限已分配给角色使用"),

    /**
     * 200 04 01 00 - 字典 - 项 - 添加
     */
    DICT_VALID_ERROR_ADD_01(200040101, "字典 - 项添加失败，请确认信息后重新添加"),
    DICT_VALID_ERROR_ADD_02(200040102, "字典 - 项名称存在重复"),
    DICT_VALID_ERROR_ADD_03(200040103, "字典 - 项编码存在重复"),
    /**
     * 200 04 02 00 - 字典 - 项 - 更新
     */
    DICT_VALID_ERROR_UPDATE_01(200040201, "字典 - 项更新失败，请确认信息后重新更新"),
    DICT_VALID_ERROR_UPDATE_02(200040202, "字典 - 项更新失败，字典 - 项信息不存在"),
    DICT_VALID_ERROR_UPDATE_03(200040203, "字典 - 项名称存在重复"),
    DICT_VALID_ERROR_UPDATE_04(200040204, "字典 - 项编码存在重复"),
    /**
     * 200 04 03 00 - 字典 - 项 - 删除
     */
    DICT_VALID_ERROR_DELETE_01(200050301, "字典 - 项删除失败，请确认信息后重新删除"),
    DICT_VALID_ERROR_DELETE_02(200050302, "字典 - 项下的字典 - 值删除失败，请确认信息后重新删除"),

    /**
     * 200 05 01 00 - 字典 - 值 - 添加
     */
    DICT_ITEM_VALID_ERROR_ADD_01(200050101, "字典 - 值添加失败，请确认信息后重新添加"),
    DICT_ITEM_VALID_ERROR_ADD_02(200050102, "字典 - 值名称存在重复"),
    /**
     * 200 05 02 00 - 字典 - 值 - 更新
     */
    DICT_ITEM_VALID_ERROR_UPDATE_01(200050201, "字典 - 值更新失败，请确认信息后重新更新"),
    DICT_ITEM_VALID_ERROR_UPDATE_02(200050202, "字典 - 值更新失败，字典 - 值信息不存在"),
    DICT_ITEM_VALID_ERROR_UPDATE_03(200050203, "字典 - 值名称存在重复"),
    /**
     * 200 05 03 00 - 字典 - 值 - 删除
     */
    DICT_ITEM_VALID_ERROR_DELETE_01(200050301, "字典 - 值删除失败，请确认信息后重新删除"),
    DICT_ITEM_VALID_ERROR_DELETE_02(200050302, "字典 - 值删除失败，部分字典 - 值下存在子节点"),





    FILE_ADD_ERR(200050101, "%s 上传失败"),
    FILE_DOWNLOAD_ERR_1(200050201, "文件下载失败，文件不存在"),
    FILE_DOWNLOAD_ERR_2(200050202, "文件下载失败 %s"),

    ;

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;

}
