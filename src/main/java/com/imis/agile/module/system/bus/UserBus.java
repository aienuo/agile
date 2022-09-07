package com.imis.agile.module.system.bus;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.agile.constant.CommonConstant;
import com.imis.agile.constant.base.BaseBus;
import com.imis.agile.constant.base.BaseResponse;
import com.imis.agile.constant.enums.ArgumentResponseEnum;
import com.imis.agile.module.system.model.converter.UserConverter;
import com.imis.agile.module.system.model.dto.*;
import com.imis.agile.module.system.model.entity.User;
import com.imis.agile.module.system.model.entity.UserOrganization;
import com.imis.agile.module.system.model.entity.UserRole;
import com.imis.agile.module.system.model.vo.UserInfoVO;
import com.imis.agile.module.system.model.vo.UserPageVO;
import com.imis.agile.module.system.service.IUserOrganizationService;
import com.imis.agile.module.system.service.IUserRoleService;
import com.imis.agile.module.system.service.IUserService;
import com.imis.agile.response.CommonResponse;
import com.imis.agile.util.AgileUtil;
import com.imis.agile.util.IdCardUtil;
import com.imis.agile.util.PasswordUtil;
import com.imis.agile.util.excel.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * UserBus<br>
 * 用户相关功能业务处理类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年04月15日 11:54
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserBus extends BaseBus {

    /**
     * 系统用户 服务类
     */
    private final IUserService userService;

    /**
     * 用户角色关联 服务类
     */
    private final IUserRoleService userRoleService;

    /**
     * 用户组织机构关联 服务类
     */
    private final IUserOrganizationService userOrganizationService;

    /**
     * 添加校验
     *
     * @param add - 添加参数
     * @return User - 系统用户
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private User userAddVerification(final UserAddDTO add) {
        // 身份证件号码
        String identityNumber = add.getIdentityNumber();
        // 验证身份证件号码格式是否正确
        ArgumentResponseEnum.USER_VALID_ERROR_ADD_02.assertIsTrue(IdCardUtil.isIdCard(identityNumber));
        // 验证 用户帐号\身份证号码 是否存在重复
        User user = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, add.getUsername()).or().eq(User::getIdentityNumber, identityNumber), Boolean.FALSE);
        ArgumentResponseEnum.USER_VALID_ERROR_ADD_02.assertIsNull(user);
        if (AgileUtil.isNotEmpty(add.getPhone())) {
            // 验证 手机号码 是否存在重复
            user = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, add.getPhone()), Boolean.FALSE);
            ArgumentResponseEnum.USER_VALID_ERROR_ADD_03.assertIsNull(user);
        }
        if (AgileUtil.isNotEmpty(add.getEmail())) {
            // 验证 电子邮箱 是否存在重复
            user = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, add.getEmail()), Boolean.FALSE);
            ArgumentResponseEnum.USER_VALID_ERROR_ADD_04.assertIsNull(user);
        }
        return UserConverter.INSTANCE.getAddEntity(add);
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
        User user = this.userService.getById(update.getId());
        ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_02.assertNotNull(user);
        if (AgileUtil.isNotEmpty(update.getIdentityNumber()) && !user.getIdentityNumber().equals(update.getIdentityNumber())) {
            // 身份证件号码
            String identityNumber = update.getIdentityNumber();
            // 验证身份证件号码格式是否正确
            ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_03.assertIsTrue(IdCardUtil.isIdCard(identityNumber));
            // 验证 身份证号码 是否存在重复
            User userByIdentityNumber = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getIdentityNumber, identityNumber), Boolean.FALSE);
            ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_03.assertIsNull(userByIdentityNumber);
        }
        if (AgileUtil.isNotEmpty(update.getPhone()) && !user.getPhone().equals(update.getPhone())) {
            // 验证 手机号码 是否存在重复
            User userByPhone = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, update.getPhone()), Boolean.FALSE);
            ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_04.assertIsNull(userByPhone);
        }
        if (AgileUtil.isNotEmpty(update.getEmail()) && !user.getEmail().equals(update.getEmail())) {
            // 验证 电子邮箱 是否存在重复
            User userByEmail = this.userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, update.getEmail()), Boolean.FALSE);
            ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_05.assertIsNull(userByEmail);
        }
        UserConverter.INSTANCE.getUpdateEntity(user, update);
        // 清除原来的 用户角色关联
        this.userRoleService.remove(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, user.getId()));
        // 清除原来的 用户组织机构关联
        this.userOrganizationService.remove(Wrappers.<UserOrganization>lambdaQuery().eq(UserOrganization::getUserId, user.getId()));
        return user;
    }

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return CommonResponse<Page < UserPageVO>>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/12 15:47
     */
    public CommonResponse<Page<UserPageVO>> pagingQueryListByParameter(final PagingQueryUserDTO pagingQuery) {
        Page<UserPageVO> pagingQueryList = this.userService.pagingQueryListByParameter(pagingQuery);
        return new CommonResponse<>(pagingQueryList);
    }

    /**
     * 添加
     *
     * @param add - 添加对象
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse add(final UserAddDTO add) {
        // 1、添加校验
        User user = this.userAddVerification(add);
        // 2、创建新用户
        boolean save = this.userService.save(user);
        ArgumentResponseEnum.USER_VALID_ERROR_ADD_01.assertIsTrue(save);
        // 3、创建用户角色关联
        List<Long> roleList = add.getRoleList();
        if (AgileUtil.isNotEmpty(roleList)) {
            List<UserRole> userRoleList = UserConverter.INSTANCE.getUserRoleEntity(user.getId(), roleList);
            boolean saveUserRole = this.userRoleService.saveBatch(userRoleList);
            ArgumentResponseEnum.USER_VALID_ERROR_ADD_05.assertIsTrue(saveUserRole);
        }
        // 4、创建用户组织机构关联
        List<Long> organizationList = add.getOrganizationList();
        if (AgileUtil.isNotEmpty(organizationList)) {
            List<UserOrganization> userOrganizationList = UserConverter.INSTANCE.getUserOrganizationEntity(user.getId(), organizationList);
            boolean saveUserOrganization = this.userOrganizationService.saveBatch(userOrganizationList);
            ArgumentResponseEnum.USER_VALID_ERROR_ADD_06.assertIsTrue(saveUserOrganization);
        }
        return new CommonResponse<>();
    }

    /**
     * 用户冻结
     *
     * @param idList - 用户标识
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse freezeUserByIdList(final List<Long> idList) {
        List<User> userList = this.userService.listByIds(idList);
        ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_06.assertNotEmpty(userList);
        ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_07.assertIsTrue(idList.size() == userList.size());
        userList.forEach(
                // 冻结状态(0-正常，1-冻结）
                user -> user.setStatus(CommonConstant.USER_FREEZE_1)
        );
        boolean save = this.userService.updateBatchById(userList);
        ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_08.assertIsTrue(save);
        return new CommonResponse<>();
    }

    /**
     * 用户解冻
     *
     * @param idList - 用户标识
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse unFreezeUserByIdList(final List<Long> idList) {
        List<User> userList = this.userService.listByIds(idList);
        ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_09.assertNotEmpty(userList);
        ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_10.assertIsTrue(idList.size() == userList.size());
        userList.forEach(
                // 冻结状态(0-正常，1-冻结）
                user -> user.setStatus(CommonConstant.USER_FREEZE_0)
        );
        boolean save = this.userService.updateBatchById(userList);
        ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_11.assertIsTrue(save);
        return new CommonResponse<>();
    }

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return CommonResponse<?>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public CommonResponse<UserInfoVO> queryById(final Long id) {
        // 查询信息
        UserInfoVO userInfo = this.userService.queryById(id);
        return new CommonResponse<>(userInfo);
    }

    /**
     * 更新
     *
     * @param update - 更新参数
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateById(final UserUpdateDTO update) {
        // 1、更新校验
        User user = this.userUpdateVerification(update);
        // 2、更新用户
        boolean save = this.userService.updateById(user);
        ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_01.assertIsTrue(save);
        // 3、更新用户角色关联
        List<Long> roleList = update.getRoleList();
        if (AgileUtil.isNotEmpty(roleList)) {
            List<UserRole> userRoleList = UserConverter.INSTANCE.getUserRoleEntity(user.getId(), roleList);
            boolean saveUserRole = this.userRoleService.saveBatch(userRoleList);
            ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_12.assertIsTrue(saveUserRole);
        }
        // 4、创建用户组织机构关联
        List<Long> organizationList = update.getOrganizationList();
        if (AgileUtil.isNotEmpty(organizationList)) {
            List<UserOrganization> userOrganizationList = UserConverter.INSTANCE.getUserOrganizationEntity(user.getId(), organizationList);
            boolean saveUserOrganization = this.userOrganizationService.saveBatch(userOrganizationList);
            ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_13.assertIsTrue(saveUserOrganization);
        }
        return new CommonResponse<>();
    }

    /**
     * 重置密码
     *
     * @param resetPassword - 重置密码参数
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse resetPassword(final ResetPasswordDTO resetPassword) {
        // 1、验证用户存在
        User user = this.userService.getById(resetPassword.getUserId());
        ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_02.assertNotNull(user);
        // 2、取盐
        user.setSalt(PasswordUtil.getStringSalt());
        // 3、构建密码
        user.setPassword(PasswordUtil.encrypt(user.getUsername(), resetPassword.getNewPassword(), user.getSalt()));
        // 4、更新用户
        boolean save = this.userService.updateById(user);
        ArgumentResponseEnum.USER_VALID_ERROR_UPDATE_01.assertIsTrue(save);
        return new CommonResponse<>();
    }

    /**
     * 移除
     *
     * @param idList - 用户标识
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse removeByIdList(final List<Long> idList) {
        List<User> userList = this.userService.listByIds(idList);
        ArgumentResponseEnum.USER_VALID_ERROR_DELETE_02.assertNotEmpty(userList);
        ArgumentResponseEnum.USER_VALID_ERROR_DELETE_03.assertIsTrue(idList.size() == userList.size());
        userList.forEach(
                // 删除状态（0-正常，1-已删除）
                user -> user.setDelFlag(CommonConstant.DEL_FLAG_1)
        );
        boolean save = this.userService.updateBatchById(userList);
        ArgumentResponseEnum.USER_VALID_ERROR_DELETE_01.assertIsTrue(save);
        return new CommonResponse<>();
    }

    /**
     * 导出导入Excel模版
     *
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public void getImportTemplate() {
        ExcelUtil<UserExcelDTO> util = new ExcelUtil<>(UserExcelDTO.class);
        util.exportExcelTemplate(getHttpServletResponse(), "用户数据");
    }

    /**
     * 导入信息
     *
     * @param multipartFile - 文件
     * @param update        - 是否更新
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public void export(final MultipartFile multipartFile, final Boolean update) {
        ExcelUtil<UserExcelDTO> util = new ExcelUtil<>(UserExcelDTO.class);
        if (multipartFile == null || multipartFile.isEmpty()) {
            ArgumentResponseEnum.EXCEL_IMPORT_ERR_0.assertFail("文件不存在");
        }
        try {
            List<UserExcelDTO> userImportList = util.importExcel(multipartFile.getInputStream());
            if (AgileUtil.isNotEmpty(userImportList)) {
                List<User> userAddList = new ArrayList<>();
                List<User> userUpdateList = new ArrayList<>();
                userImportList.forEach(
                        userImport -> {
                            // 登录账号
                            String userName = userImport.getUsername();
                            ArgumentResponseEnum.USER_VALID_ERROR_IMPORT_02.assertIsTrueWithMessage(AgileUtil.isNotEmpty(userName), userImport.getRealname());
                            // 身份证件号码
                            String identityNumber = userImport.getIdentityNumber();
                            // 验证身份证件号码格式是否正确
                            ArgumentResponseEnum.USER_VALID_ERROR_IMPORT_03.assertIsTrueWithMessage(IdCardUtil.isIdCard(identityNumber), userImport.getRealname());
                            // 验证 用户帐号\身份证号码 是否存在重复
                            User user = this.userService.getOne(Wrappers.<User>lambdaQuery()
                                            .eq(User::getUsername, userName)
                                            .or().eq(User::getIdentityNumber, identityNumber)
                                            .or(AgileUtil.isNotEmpty(userImport.getPhone())).eq(User::getPhone, userImport.getPhone())
                                            .or(AgileUtil.isNotEmpty(userImport.getEmail())).eq(User::getEmail, userImport.getEmail())
                                    , Boolean.FALSE);
                            if (update) {
                                if (AgileUtil.isEmpty(user)) {
                                    ArgumentResponseEnum.USER_VALID_ERROR_IMPORT_04.assertIsTrue(AgileUtil.isEmpty(user), userImport.getRealname());
                                }
                                UserConverter.INSTANCE.getImportEntity(user, userImport);
                                userUpdateList.add(user);
                            } else {
                                if (AgileUtil.isNotEmpty(user)) {
                                    ArgumentResponseEnum.USER_VALID_ERROR_IMPORT_05.assertIsTrue(AgileUtil.isEmpty(user), userImport.getRealname());
                                }
                                userAddList.add(UserConverter.INSTANCE.getImportEntity(userImport));
                            }
                        }
                );
                if (AgileUtil.isNotEmpty(userAddList)) {
                    // 添加
                    boolean save = this.userService.saveBatch(userAddList);
                    ArgumentResponseEnum.USER_VALID_ERROR_IMPORT_01.assertIsTrue(save);
                }
                if (AgileUtil.isNotEmpty(userUpdateList)) {
                    // 更新
                    boolean updateUser = this.userService.updateBatchById(userUpdateList);
                    ArgumentResponseEnum.USER_VALID_ERROR_IMPORT_01.assertIsTrue(updateUser);
                }
            }
        } catch (IOException e) {
            log.error("从给定的 MultipartFile 中获取 InputStream 失败：{}", e.getMessage());
            ArgumentResponseEnum.EXCEL_IMPORT_ERR_0.assertFail(e.getMessage());
        }
    }

}
