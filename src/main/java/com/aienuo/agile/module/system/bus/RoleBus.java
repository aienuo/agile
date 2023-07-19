package com.aienuo.agile.module.system.bus;

import com.aienuo.agile.constant.base.BaseBus;
import com.aienuo.agile.constant.base.BaseResponse;
import com.aienuo.agile.constant.enums.ArgumentResponseEnum;
import com.aienuo.agile.module.system.model.converter.RoleConverter;
import com.aienuo.agile.module.system.model.dto.PagingQueryRoleDTO;
import com.aienuo.agile.module.system.model.dto.RoleAddDTO;
import com.aienuo.agile.module.system.model.dto.RoleUpdateDTO;
import com.aienuo.agile.module.system.model.entity.Role;
import com.aienuo.agile.module.system.model.entity.RoleMenu;
import com.aienuo.agile.module.system.model.entity.UserRole;
import com.aienuo.agile.module.system.model.vo.RoleInfoVO;
import com.aienuo.agile.module.system.model.vo.RolePageVO;
import com.aienuo.agile.module.system.model.vo.RoleVO;
import com.aienuo.agile.module.system.service.IRoleMenuService;
import com.aienuo.agile.module.system.service.IRoleService;
import com.aienuo.agile.module.system.service.IUserRoleService;
import com.aienuo.agile.response.CommonResponse;
import com.aienuo.agile.util.AgileUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsNull(role, "角色", "角色名称存在重复");
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
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(role, "角色", "角色信息不存在");
        if (AgileUtil.isNotEmpty(update.getRoleName()) && !role.getRoleName().equals(update.getRoleName())) {
            // 验证 角色名称 是否存在重复
            Role roleByRoleName = this.roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getRoleName, update.getRoleName()), Boolean.FALSE);
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsNull(roleByRoleName, "角色", "角色名称存在重复");
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
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse add(final RoleAddDTO add) {
        // 1、添加校验
        Role role = this.roleAddVerification(add);
        // 2、创建新角色
        boolean save = this.roleService.save(role);
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsTrue(save, "角色", "请确认信息准确无误后重新添加");
        // 3、创建角色菜单权限关联
        List<Long> menuList = add.getMenuList();
        if (AgileUtil.isNotEmpty(menuList)) {
            List<RoleMenu> roleMenuList = RoleConverter.INSTANCE.getRoleMenuEntity(role.getId(), menuList);
            boolean saveRoleMenu = this.roleMenuService.saveBatch(roleMenuList);
            ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsTrue(saveRoleMenu, "角色", "角色与菜单权限关联失败");
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
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateById(final RoleUpdateDTO update) {
        // 1、更新校验
        Role role = this.roleUpdateVerification(update);
        // 2、更新角色
        boolean save = this.roleService.updateById(role);
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(save, "角色", "请确认信息准确无误后重新更新");
        // 3、更新角色菜单权限关联
        List<Long> menuList = update.getMenuList();
        if (AgileUtil.isNotEmpty(menuList)) {
            List<RoleMenu> roleMenuList = RoleConverter.INSTANCE.getRoleMenuEntity(role.getId(), menuList);
            boolean saveRoleMenu = this.roleMenuService.saveBatch(roleMenuList);
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(saveRoleMenu, "角色", "角色与菜单权限关联失败");
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
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(number == 0, "角色", "角色已被分配给用户");
        boolean delete = this.roleMenuService.remove(Wrappers.<RoleMenu>lambdaQuery().in(RoleMenu::getRoleId, idList));
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(delete, "角色", "角色与菜单权限关联关系解除失败");
        delete = this.roleService.removeByIds(idList);
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(delete, "角色", "请确认信息准确无误后重新删除");
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
