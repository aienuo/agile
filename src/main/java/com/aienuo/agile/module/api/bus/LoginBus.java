package com.aienuo.agile.module.api.bus;

import com.aienuo.agile.constant.CommonConstant;
import com.aienuo.agile.constant.base.BaseBus;
import com.aienuo.agile.constant.base.BaseResponse;
import com.aienuo.agile.constant.enums.ArgumentResponseEnum;
import com.aienuo.agile.module.api.model.dto.LoginDTO;
import com.aienuo.agile.module.api.model.dto.PasswordUpdateDTO;
import com.aienuo.agile.module.api.model.dto.UserUpdateDTO;
import com.aienuo.agile.module.api.model.vo.*;
import com.aienuo.agile.module.system.model.converter.UserConverter;
import com.aienuo.agile.module.system.model.entity.User;
import com.aienuo.agile.module.system.service.*;
import com.aienuo.agile.response.CommonResponse;
import com.aienuo.agile.util.*;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * SysLoginBus<br>
 * 登录相关功能 业务处理类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月10日 09:18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginBus extends BaseBus {

    /**
     * 系统用户 服务类
     */
    private final IUserService userService;

    /**
     * 角色管理 服务类
     */
    private final IRoleService roleService;

    /**
     * 菜单权限 服务类
     */
    private final IMenuService menuService;

    /**
     * 字典 - 项 服务类
     */
    private final IDictService dictService;

    /**
     * 字典 - 值 服务类
     */
    private final IDictItemService dictItemService;

    /**
     * 构建登录返回值
     *
     * @param user     - 系统用户
     * @param password - 登录密码
     * @return CommonResponse<LoginVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    private CommonResponse<LoginVO> buildingData(final User user, final String password) {
        HttpServletResponse httpServletResponse = getHttpServletResponse();
        // 验证密码
        ArgumentResponseEnum.SELECT_PARAMETERS_VALID_ERROR.assertIsTrue(user.getPassword().equals(PasswordUtil.encrypt(user.getUsername(), password, user.getSalt())), "登录", "密码或账号不正确");
        // 删除状态（0-正常，1-已删除）
        ArgumentResponseEnum.SELECT_PARAMETERS_VALID_ERROR.assertIsTrue(CommonConstant.DEL_FLAG_0.equals(user.getDelFlag()), "登录", "账号注销，请联系管理员");
        // 冻结状态(0-正常，1-冻结）
        ArgumentResponseEnum.SELECT_PARAMETERS_VALID_ERROR.assertIsTrue(CommonConstant.USER_FREEZE_0.equals(user.getStatus()), "登录", "账号冻结，请联系管理员");
        LoginVO userLogin = new LoginVO();
        // Token
        userLogin.setToken(JwtUtil.sign(user.getUsername(), user.getPassword()));
        // 系统用户
        userLogin.setUser(UserConverter.INSTANCE.getReturnValue(user));
        // 往 Header 中 设置 Token
        httpServletResponse.setHeader(CommonConstant.X_ACCESS_TOKEN, userLogin.getToken());
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, CommonConstant.X_ACCESS_TOKEN);
        // 登录用户返回值
        return new CommonResponse<>(userLogin);
    }

    /**
     * 更新校验
     *
     * @param update - 更新参数
     * @return User - 系统用户
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private User userUpdateVerification(final UserUpdateDTO update) {
        User user = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, update.getUsername()), Boolean.FALSE);
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(user, "用户", "请确认信息准确无误后重新更新");
        if (AgileUtil.isNotEmpty(update.getIdentityNumber()) && !user.getIdentityNumber().equals(update.getIdentityNumber())) {
            // 身份证件号码
            String identityNumber = update.getIdentityNumber();
            // 验证身份证件号码格式是否正确
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(IdCardUtil.isIdCard(identityNumber), "用户", "请确认信息准确无误后重新更新");
            // 验证 身份证号码 是否存在重复
            User userByIdentityNumber = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getIdentityNumber, identityNumber), Boolean.FALSE);
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsNull(userByIdentityNumber, "用户", "请确认信息准确无误后重新更新");
        }
        if (AgileUtil.isNotEmpty(update.getPhone()) && !user.getPhone().equals(update.getPhone())) {
            // 验证 手机号码 是否存在重复
            User userByPhone = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, update.getPhone()), Boolean.FALSE);
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsNull(userByPhone, "用户", "请确认信息准确无误后重新更新");
        }
        if (AgileUtil.isNotEmpty(update.getEmail()) && !user.getEmail().equals(update.getEmail())) {
            // 验证 电子邮箱 是否存在重复
            User userByEmail = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, update.getEmail()), Boolean.FALSE);
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsNull(userByEmail, "用户", "请确认信息准确无误后重新更新");
        }
        UserConverter.INSTANCE.getUserUpdateEntity(user, update);
        return user;
    }

    /**
     * 构建基础数据返回值
     *
     * @return List<DictVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    private List<DictVO> buildingData() {
        List<DictVO> dictList = this.dictService.queryList();
        if (AgileUtil.isNotEmpty(dictList)) {
            // 字典项 id
            List<String> dictIdList = dictList.stream().map(DictVO::getId).filter(AgileUtil::isNotEmpty).distinct().collect(Collectors.toList());
            // 字典值
            List<ItemVO> itemList = this.dictItemService.queryDictItemListByDictIdList(dictIdList);
            Map<String, List<ItemVO>> itemListMap = itemList.stream().collect(Collectors.groupingBy(ItemVO::getDictId));
            dictList.forEach(
                    dict -> {
                        if (itemListMap.containsKey(dict.getId())) {
                            // 构建数据
                            dict.setItemList(new BuildingTreeData<ItemVO>().buildingTreeData(itemListMap.get(dict.getId())));
                        }
                    }
            );
            // 字典 - 数据库表名称
            dictList.add(new DictVO().setDictCode("tableName").setItemList(this.dictItemService.queryTableItemList()));
        }
        return dictList;
    }

    /**
     * 用户登录
     *
     * @param login - 用户登录表单对象
     * @return CommonResponse<LoginVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    public CommonResponse<LoginVO> login(final LoginDTO login) {
        // 用户名
        String username = login.getUsername();
        User user = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username), Boolean.FALSE);
        ArgumentResponseEnum.SELECT_PARAMETERS_VALID_ERROR.assertNotNull(user, "登录", "用户不存在");
        // 构建登录返回值
        return buildingData(user, login.getPassword());
    }

    /**
     * 基础信息
     *
     * @param username - 用户标识
     * @return CommonResponse<QueryVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    public CommonResponse<QueryVO> queryByUserName(final String username) {
        QueryVO query = new QueryVO();
        // 判断角色是否存在
        User user = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username), Boolean.FALSE);
        if (AgileUtil.isNotEmpty(user)) {
            Long id = user.getId();
            // 角色管理
            List<RoleVO> roleList = this.roleService.queryRoleListByUserId(id);
            query.setRoleList(roleList);
            // 菜单权限
            List<MenuTreeVO> menuTreeList = this.menuService.queryMenuTreeListByUserId(id);
            query.setMenuTreeList(menuTreeList);
            // 按钮权限
            List<ButtonVO> buttonList = this.menuService.queryButtonListByUserId(id);
            query.setButtonList(buttonList);
            // 字典
            List<DictVO> dictList = this.buildingData();
            query.setDictList(dictList);
        }
        return new CommonResponse<>(query);
    }

    /**
     * 更新基本信息
     *
     * @param update - 更新参数
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse update(final UserUpdateDTO update) {
        // 1、更新校验
        User user = this.userUpdateVerification(update);
        // 2、更新用户
        boolean save = this.userService.updateById(user);
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(save, "用户", "请确认信息准确无误后重新更新");
        return new CommonResponse<>();
    }

    /**
     * 更新登录密码
     *
     * @param update - 更新参数
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse password(final PasswordUpdateDTO update) {
        // 1、验证用户存在
        User user = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, update.getUsername()), Boolean.FALSE);
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(user, "用户", "请确认信息准确无误后重新修改密码");
        // 2、判断密码是否正确
        String encrypt = PasswordUtil.encrypt(update.getUsername(), update.getOldPassword(), user.getSalt());
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(user.getPassword().equals(encrypt), "用户", "请确认信息准确无误后重新修改密码");
        // 3、取盐
        user.setSalt(PasswordUtil.getStringSalt());
        // 4、构建密码
        user.setPassword(PasswordUtil.encrypt(user.getUsername(), update.getNewPassword(), user.getSalt()));
        // 5、更新用户
        boolean save = this.userService.updateById(user);
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(save, "用户", "请确认信息准确无误后重新修改密码");
        return new CommonResponse<>();
    }

}
