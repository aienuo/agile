package com.imis.agile.module.system.bus;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.agile.constant.base.BaseBus;
import com.imis.agile.constant.enums.ArgumentResponseEnum;
import com.imis.agile.module.system.model.converter.RoleConverter;
import com.imis.agile.module.system.model.dto.PagingQueryRoleDTO;
import com.imis.agile.module.system.model.dto.RoleAddDTO;
import com.imis.agile.module.system.model.dto.RoleUpdateDTO;
import com.imis.agile.module.system.model.entity.Role;
import com.imis.agile.module.system.model.entity.RoleMenu;
import com.imis.agile.module.system.model.entity.UserRole;
import com.imis.agile.module.system.model.vo.RoleInfoVO;
import com.imis.agile.module.system.model.vo.RolePageVO;
import com.imis.agile.module.system.model.vo.RoleVO;
import com.imis.agile.module.system.service.IRoleMenuService;
import com.imis.agile.module.system.service.IRoleService;
import com.imis.agile.module.system.service.IUserRoleService;
import com.imis.agile.response.BaseResponse;
import com.imis.agile.response.CommonResponse;
import com.imis.agile.util.AgileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * RoleBus<br>
 * 角色相关功能业务处理类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年07月28日 11:27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleBus extends BaseBus {

    /**
     * 系统角色 服务类
     */
    private final IRoleService roleService;

    /**
     * 用户角色关联 服务类
     */
    private final IUserRoleService userRoleService;

    /**
     * 角色菜单权限关联 服务类
     */
    private final IRoleMenuService roleMenuService;

    /**
     * 添加校验
     *
     * @param add - 添加参数
     * @return Role - 系统角色
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private Role roleAddVerification(final RoleAddDTO add) {
        // 验证 角色名称 是否存在重复
        Role role = this.roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getRoleName, add.getRoleName()), Boolean.FALSE);
        ArgumentResponseEnum.ROLE_VALID_ERROR_ADD_02.assertIsNull(role);
        add.setRoleCode("A008");
        return RoleConverter.INSTANCE.getAddEntity(add);
    }

    /**
     * 更新校验
     *
     * @param update - 更新参数
     * @return Role - 系统角色
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private Role roleUpdateVerification(final RoleUpdateDTO update) {
        Role role = this.roleService.getById(update.getId());
        ArgumentResponseEnum.ROLE_VALID_ERROR_UPDATE_02.assertNotNull(role);
        if (AgileUtil.isNotEmpty(update.getRoleName()) && !role.getRoleName().equals(update.getRoleName())) {
            // 验证 角色名称 是否存在重复
            Role roleByRoleName = this.roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getRoleName, update.getRoleName()), Boolean.FALSE);
            ArgumentResponseEnum.ROLE_VALID_ERROR_UPDATE_03.assertIsNull(roleByRoleName);
        }
        RoleConverter.INSTANCE.getUpdateEntity(role, update);
        // 清除 角色菜单权限关联
        this.roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, update.getId()));
        return role;
    }

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return CommonResponse<Page < RolePageVO>>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/12 15:47
     */
    public CommonResponse<Page<RolePageVO>> pagingQueryListByParameter(final PagingQueryRoleDTO pagingQuery) {
        Page<RolePageVO> pagingQueryList = this.roleService.pagingQueryListByParameter(pagingQuery);
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
    public BaseResponse add(final RoleAddDTO add) {
        // 1、添加校验
        Role role = this.roleAddVerification(add);
        // 2、创建新角色
        boolean save = this.roleService.save(role);
        ArgumentResponseEnum.ROLE_VALID_ERROR_ADD_01.assertIsTrue(save);
        // 3、创建角色菜单权限关联
        List<Long> menuList = add.getMenuList();
        if (AgileUtil.isNotEmpty(menuList)) {
            List<RoleMenu> roleMenuList = RoleConverter.INSTANCE.getRoleMenuEntity(role.getId(), menuList);
            boolean saveRoleMenu = this.roleMenuService.saveBatch(roleMenuList);
            ArgumentResponseEnum.ROLE_VALID_ERROR_UPDATE_04.assertIsTrue(saveRoleMenu);
        }
        return new CommonResponse<>();
    }

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return CommonResponse<RoleInfoVO> - 角色
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public CommonResponse<RoleInfoVO> queryById(final Long id) {
        // 查询信息
        RoleInfoVO roleInfo = this.roleService.queryById(id);
        return new CommonResponse<>(roleInfo);
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
    public BaseResponse updateById(final RoleUpdateDTO update) {
        // 1、更新校验
        Role role = this.roleUpdateVerification(update);
        // 2、更新角色
        boolean save = this.roleService.updateById(role);
        ArgumentResponseEnum.ROLE_VALID_ERROR_UPDATE_01.assertIsTrue(save);
        // 3、更新角色菜单权限关联
        List<Long> menuList = update.getMenuList();
        if (AgileUtil.isNotEmpty(menuList)) {
            List<RoleMenu> roleMenuList = RoleConverter.INSTANCE.getRoleMenuEntity(role.getId(), menuList);
            boolean saveRoleMenu = this.roleMenuService.saveBatch(roleMenuList);
            ArgumentResponseEnum.ROLE_VALID_ERROR_UPDATE_04.assertIsTrue(saveRoleMenu);
        }
        return new CommonResponse<>();
    }

    /**
     * 删除
     *
     * @param idList - 角色标识
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteByIdList(final List<Long> idList) {
        long number = this.userRoleService.count(Wrappers.<UserRole>lambdaQuery().in(UserRole::getRoleId, idList));
        ArgumentResponseEnum.ROLE_VALID_ERROR_DELETE_02.assertIsTrue(number == 0);
        boolean delete = this.roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().in(RoleMenu::getRoleId, idList));
        ArgumentResponseEnum.ROLE_VALID_ERROR_DELETE_03.assertIsTrue(delete);
        delete = this.roleService.removeByIds(idList);
        ArgumentResponseEnum.ROLE_VALID_ERROR_DELETE_01.assertIsTrue(delete);
        return new CommonResponse<>();
    }

    /**
     * 列表查看
     *
     * @return CommonResponse<List < RoleVO>>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public CommonResponse<List<RoleVO>> queryList() {
        List<RoleVO> queryList = this.roleService.queryList();
        return new CommonResponse<>(queryList);
    }

}
