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
     * 200 01 01 00 - 字典 - 项 - 添加
     */
    DICT_VALID_ERROR_ADD_01(200010101, "字典 - 项添加失败，请确认信息后重新添加"),
    DICT_VALID_ERROR_ADD_02(200010102, "字典 - 项添加失败，项名称存在重复"),
    DICT_VALID_ERROR_ADD_03(200010103, "字典 - 项添加失败，项编码存在重复"),
    /**
     * 200 01 02 00 - 字典 - 项 - 更新
     */
    DICT_VALID_ERROR_UPDATE_01(200010201, "字典 - 项更新失败，请确认信息后重新更新"),
    DICT_VALID_ERROR_UPDATE_02(200010202, "字典 - 项更新失败，字典 - 项信息不存在"),
    DICT_VALID_ERROR_UPDATE_03(200010203, "字典 - 项更新失败，项名称存在重复"),
    DICT_VALID_ERROR_UPDATE_04(200010204, "字典 - 项更新失败，项编码存在重复"),
    /**
     * 200 02 03 00 - 字典 - 项 - 删除
     */
    DICT_VALID_ERROR_DELETE_01(200020301, "字典 - 项删除失败，请确认信息后重新删除"),
    DICT_VALID_ERROR_DELETE_02(200020302, "字典 - 项下的字典 - 值删除失败，请确认信息后重新删除"),

    /**
     * 200 02 01 00 - 字典 - 值 - 添加
     */
    DICT_ITEM_VALID_ERROR_ADD_01(200020101, "字典 - 值添加失败，请确认信息后重新添加"),
    DICT_ITEM_VALID_ERROR_ADD_02(200020102, "字典 - 值添加失败，字典 - 项信息不存在"),
    DICT_ITEM_VALID_ERROR_ADD_03(200020103, "字典 - 值添加失败，值类型不正确"),
    DICT_ITEM_VALID_ERROR_ADD_04(200020104, "字典 - 值添加失败，值名称存在重复"),
    /**
     * 200 02 02 00 - 字典 - 值 - 更新
     */
    DICT_ITEM_VALID_ERROR_UPDATE_01(200020201, "字典 - 值更新失败，请确认信息后重新更新"),
    DICT_ITEM_VALID_ERROR_UPDATE_02(200020202, "字典 - 值更新失败，字典 - 项信息不存在"),
    DICT_ITEM_VALID_ERROR_UPDATE_03(200020203, "字典 - 值更新失败，值类型不正确"),
    DICT_ITEM_VALID_ERROR_UPDATE_04(200020204, "字典 - 值更新失败，字典 - 值信息不存在"),
    DICT_ITEM_VALID_ERROR_UPDATE_05(200020205, "字典 - 值更新失败，值名称存在重复"),
    /**
     * 200 02 03 00 - 字典 - 值 - 删除
     */
    DICT_ITEM_VALID_ERROR_DELETE_01(200020301, "字典 - 值删除失败，请确认信息后重新删除"),
    DICT_ITEM_VALID_ERROR_DELETE_02(200020302, "字典 - 值删除失败，部分字典 - 值下存在子节点"),

    /**
     * 200 03 00 00 - 用户管理 - 登录
     */
    USER_VALID_ERROR_LOGIN_01(200030001, "登录失败，用户不存在"),
    USER_VALID_ERROR_LOGIN_02(200030002, "登录失败，账号或密码不正确"),
    USER_VALID_ERROR_LOGIN_03(200030003, "登录失败，账号注销，请联系管理员"),
    USER_VALID_ERROR_LOGIN_04(200030004, "登录失败，账号冻结，请联系管理员"),

    /**
     * 200 03 01 00 - 用户管理 - 添加
     */
    USER_VALID_ERROR_ADD_01(200030101, "用户添加失败，请确认信息后重新添加"),
    USER_VALID_ERROR_ADD_02(200030102, "用户添加失败，帐号&身份证号码存在重复"),
    USER_VALID_ERROR_ADD_03(200030103, "用户添加失败，手机号码存在重复"),
    USER_VALID_ERROR_ADD_04(200030104, "用户添加失败，电子邮箱存在重复"),
    USER_VALID_ERROR_ADD_05(200030105, "用户添加失败，用户与角色关联失败"),
    USER_VALID_ERROR_ADD_06(200030106, "用户添加失败，用户与组织机构关联失败"),
    /**
     * 200 03 02 00 - 用户管理 - 更新
     */
    USER_VALID_ERROR_UPDATE_01(200030201, "用户更新失败，请确认信息后重新更新"),
    USER_VALID_ERROR_UPDATE_02(200030202, "用户更新失败，用户信息不存在"),
    USER_VALID_ERROR_UPDATE_03(200030203, "用户更新失败，身份证号码存在重复"),
    USER_VALID_ERROR_UPDATE_04(200030204, "用户更新失败，手机号码存在重复"),
    USER_VALID_ERROR_UPDATE_05(200030205, "用户更新失败，电子邮箱存在重复"),
    USER_VALID_ERROR_UPDATE_06(200030206, "用户冻结失败，用户信息不存在"),
    USER_VALID_ERROR_UPDATE_07(200030207, "用户冻结失败，部分用户不存在"),
    USER_VALID_ERROR_UPDATE_08(200030208, "用户冻结失败，请确认信息后重新冻结"),
    USER_VALID_ERROR_UPDATE_09(200030209, "用户解冻失败，用户信息不存在"),
    USER_VALID_ERROR_UPDATE_10(200030210, "用户解冻失败，部分用户不存在"),
    USER_VALID_ERROR_UPDATE_11(200030211, "用户解冻失败，请确认信息后重新解冻"),
    USER_VALID_ERROR_UPDATE_12(200030212, "用户更新失败，用户与角色关联失败"),
    USER_VALID_ERROR_UPDATE_13(200030213, "用户更新失败，用户与组织机构关联失败"),
    /**
     * 200 03 03 00 - 用户管理 - 删除
     */
    USER_VALID_ERROR_DELETE_01(200030301, "用户删除失败，请确认信息后重新删除"),
    USER_VALID_ERROR_DELETE_02(200030302, "用户删除失败，用户信息不存在"),
    USER_VALID_ERROR_DELETE_03(200030303, "用户删除失败，部分用户不存在"),

    /**
     * 200 04 01 00 - 角色管理 - 添加
     */
    ROLE_VALID_ERROR_ADD_01(200040101, "角色添加失败，请确认信息后重新添加"),
    ROLE_VALID_ERROR_ADD_02(200040102, "角色添加失败，角色名称存在重复"),
    /**
     * 200 04 02 00 - 角色管理 - 更新
     */
    ROLE_VALID_ERROR_UPDATE_01(200040201, "角色更新失败，请确认信息后重新更新"),
    ROLE_VALID_ERROR_UPDATE_02(200040202, "角色更新失败，角色信息不存在"),
    ROLE_VALID_ERROR_UPDATE_03(200040203, "角色更新失败，角色名称存在重复"),
    ROLE_VALID_ERROR_UPDATE_04(200040204, "角色更新失败，角色与菜单权限关联失败"),
    /**
     * 200 04 03 00 - 角色管理 - 删除
     */
    ROLE_VALID_ERROR_DELETE_01(200040301, "角色删除失败，请确认信息后重新删除"),
    ROLE_VALID_ERROR_DELETE_02(200040302, "角色删除失败，角色已被分配给用户"),
    ROLE_VALID_ERROR_DELETE_03(200040303, "角色删除失败，角色与菜单权限关联关系解除失败"),

    /**
     * 200 05 01 00 - 菜单权限 - 添加
     */
    MENU_VALID_ERROR_ADD_01(200050101, "菜单权限添加失败，请确认信息后重新添加"),
    MENU_VALID_ERROR_ADD_02(200050102, "菜单权限添加失败，菜单权限名称存在重复"),
    MENU_VALID_ERROR_ADD_03(200050103, "菜单权限添加失败，父级菜单权限信息不存在"),
    /**
     * 200 05 02 00 - 菜单权限 - 更新
     */
    MENU_VALID_ERROR_UPDATE_01(200050201, "菜单权限更新失败，请确认信息后重新更新"),
    MENU_VALID_ERROR_UPDATE_02(200050202, "菜单权限更新失败，菜单权限信息不存在"),
    MENU_VALID_ERROR_UPDATE_03(200050203, "菜单权限更新失败，父级菜单权限信息不存在"),
    MENU_VALID_ERROR_UPDATE_04(200050204, "菜单权限更新失败，菜单权限名称存在重复"),
    /**
     * 200 05 03 00 - 菜单权限 - 删除
     */
    MENU_VALID_ERROR_DELETE_01(200050301, "菜单权限删除失败，请确认信息后重新删除"),
    MENU_VALID_ERROR_DELETE_02(200050302, "菜单权限删除失败，部分菜单权限下存在子节点"),
    MENU_VALID_ERROR_DELETE_03(200050303, "菜单权限删除失败，部分菜单权限已分配给角色使用"),

    /**
     * 200 06 01 00 - 组织机构 - 添加
     */
    ORGANIZATION_VALID_ERROR_ADD_01(200060101, "组织机构添加失败，请确认信息后重新添加"),
    ORGANIZATION_VALID_ERROR_ADD_02(200060102, "组织机构添加失败，组织机构名称存在重复"),
    ORGANIZATION_VALID_ERROR_ADD_03(200060103, "组织机构添加失败，父级组织机构信息不存在"),
    /**
     * 200 06 02 00 - 组织机构 - 更新
     */
    ORGANIZATION_VALID_ERROR_UPDATE_01(200060201, "组织机构更新失败，请确认信息后重新更新"),
    ORGANIZATION_VALID_ERROR_UPDATE_02(200060202, "组织机构更新失败，组织机构信息不存在"),
    ORGANIZATION_VALID_ERROR_UPDATE_03(200060203, "组织机构更新失败，父级组织机构信息不存在"),
    ORGANIZATION_VALID_ERROR_UPDATE_04(200060204, "组织机构更新失败，组织机构名称存在重复"),
    ORGANIZATION_VALID_ERROR_UPDATE_05(200060205, "组织机构更新失败，更新部门负责人失败"),
    /**
     * 200 06 03 00 - 组织机构 - 删除
     */
    ORGANIZATION_VALID_ERROR_DELETE_01(200060301, "组织机构删除失败，请确认信息后重新删除"),
    ORGANIZATION_VALID_ERROR_DELETE_02(200060302, "组织机构删除失败，部分组织机构下存在子节点"),
    ORGANIZATION_VALID_ERROR_DELETE_03(200060303, "组织机构删除失败，部分组织机构已分配给用户使用"),


    FILE_ADD_ERR(200990101, "%s 上传失败"),
    FILE_ADD_ERR_PATH_NO_EXIST(200990102, "上传失败，路径不存在，路径创建时失败"),
    FILE_ADD_ERR_FILE_SAVE_FAILED(200990103, "上传失败，文件保存失败"),
    FILE_DOWNLOAD_ERR_1(200990201, "文件下载失败，文件不存在"),
    FILE_DOWNLOAD_ERR_2(200990202, "文件下载失败 %s"),

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
