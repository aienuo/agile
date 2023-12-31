package com.aienuo.agile.module.system.service.impl;

import com.aienuo.agile.constant.DataBaseConstant;
import com.aienuo.agile.module.api.model.vo.ButtonVO;
import com.aienuo.agile.module.api.model.vo.MenuTreeVO;
import com.aienuo.agile.module.system.mapper.MenuMapper;
import com.aienuo.agile.module.system.model.entity.Menu;
import com.aienuo.agile.module.system.model.entity.RoleMenu;
import com.aienuo.agile.module.system.model.entity.UserRole;
import com.aienuo.agile.module.system.model.vo.MenuInfoVO;
import com.aienuo.agile.module.system.model.vo.MenuTreeInfoVO;
import com.aienuo.agile.module.system.service.IMenuService;
import com.aienuo.agile.module.system.service.IRoleMenuService;
import com.aienuo.agile.module.system.service.IUserRoleService;
import com.aienuo.agile.util.AgileUtil;
import com.aienuo.agile.util.BuildingTreeData;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限 服务实现类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-17
 */
@Service
@Slf4j
@RequiredArgsConstructor
@DS(DataBaseConstant.DATA_SOURCE_MASTER)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    /**
     * 用户角色关联 服务类
     */
    private final IUserRoleService userRoleService;

    /**
     * 角色菜单权限关联 服务类
     */
    private final IRoleMenuService roleMenuService;

    /**
     * 查询系统用户的角色信息
     *
     * @param userId - 系统用户编号
     * @return List<MenuTreeVO> - 菜单权限
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public List<MenuTreeVO> queryMenuTreeListByUserId(final Long userId) {
        // 用户角色关联
        List<UserRole> userRoleList = this.userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userId));
        if (AgileUtil.isNotEmpty(userRoleList)) {
            // 角色编号
            List<Long> roleIdList = userRoleList.stream().map(UserRole::getRoleId).filter(AgileUtil::isNotEmpty).distinct().collect(Collectors.toList());
            // 角色菜单权限关联
            List<RoleMenu> roleMenuList = this.roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().in(RoleMenu::getRoleId, roleIdList));
            if (AgileUtil.isNotEmpty(userRoleList)) {
                List<Long> menuIdList = roleMenuList.stream().map(RoleMenu::getMenuId).filter(AgileUtil::isNotEmpty).distinct().collect(Collectors.toList());
                // 查询数据
                List<MenuTreeVO> menuList = this.baseMapper.queryMenuTreeListByIdList(menuIdList);
                if (AgileUtil.isNotEmpty(menuList)) {
                    // 构建树形结构数据
                    return new BuildingTreeData<MenuTreeVO>().buildingTreeData(menuList);
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * 查询系统用户的按钮权限信息
     *
     * @param userId - 系统用户编号
     * @return List<ButtonVO> - 按钮权限
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public List<ButtonVO> queryButtonListByUserId(final Long userId) {
        List<ButtonVO> buttonList = new ArrayList<>();
        // 用户角色关联
        List<UserRole> userRoleList = this.userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userId));
        if (AgileUtil.isNotEmpty(userRoleList)) {
            // 角色编号
            List<Long> roleIdList = userRoleList.stream().map(UserRole::getRoleId).filter(AgileUtil::isNotEmpty).distinct().collect(Collectors.toList());
            // 角色按钮权限关联
            List<RoleMenu> roleMenuList = this.roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().in(RoleMenu::getRoleId, roleIdList));
            if (AgileUtil.isNotEmpty(userRoleList)) {
                List<Long> buttonIdList = roleMenuList.stream().map(RoleMenu::getMenuId).filter(AgileUtil::isNotEmpty).distinct().collect(Collectors.toList());
                buttonList = this.baseMapper.queryButtonListByIdList(buttonIdList);
            }
        }
        return buttonList;
    }

    /**
     * 树查询
     *
     * @return List<MenuTreeInfoVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public List<MenuTreeInfoVO> queryMenuTreeList() {
        // 查询数据
        List<MenuTreeInfoVO> menuTreeList = this.baseMapper.queryMenuTreeList();
        if (AgileUtil.isNotEmpty(menuTreeList)) {
            // 构建数据
            return new BuildingTreeData<MenuTreeInfoVO>().buildingTreeData(menuTreeList);
        }
        return menuTreeList;
    }

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return MenuInfoVO - 菜单权限
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public MenuInfoVO queryById(final Long id) {
        return this.baseMapper.queryById(id);
    }

}
