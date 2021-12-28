package com.imis.agile.module.system.bus;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.imis.agile.constant.base.BaseBus;
import com.imis.agile.constant.base.BaseResponse;
import com.imis.agile.constant.enums.ArgumentResponseEnum;
import com.imis.agile.module.system.model.converter.MenuConverter;
import com.imis.agile.module.system.model.dto.MenuAddDTO;
import com.imis.agile.module.system.model.dto.MenuUpdateDTO;
import com.imis.agile.module.system.model.entity.Menu;
import com.imis.agile.module.system.model.entity.RoleMenu;
import com.imis.agile.module.system.model.vo.MenuInfoVO;
import com.imis.agile.module.system.model.vo.MenuTreeInfoVO;
import com.imis.agile.module.system.service.IMenuService;
import com.imis.agile.module.system.service.IRoleMenuService;
import com.imis.agile.response.CommonResponse;
import com.imis.agile.util.AgileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * MenuBus<br>
 * 菜单权限相关功能业务处理类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年08月09日 12:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MenuBus extends BaseBus {

    /**
     * 菜单权限 服务类
     */
    private final IMenuService menuService;

    /**
     * 角色菜单权限关联 服务类
     */
    private final IRoleMenuService roleMenuService;

    /**
     * 添加校验
     *
     * @param add - 添加参数
     * @return Menu - 菜单权限
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private Menu menuAddVerification(final MenuAddDTO add) {
        if (AgileUtil.isNotEmpty(add.getParentId())){
            // 验证父级菜单权限是否存在
            Menu parent = this.menuService.getById(add.getParentId());
            ArgumentResponseEnum.MENU_VALID_ERROR_ADD_03.assertNotNull(parent);
        }
        // 验证 菜单权限名称 是否存在重复
        Menu menu = this.menuService.getOne(Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getName, add.getName()).eq(Menu::getMenuType, add.getMenuType())
                .eq(AgileUtil.isNotEmpty(add.getParentId()), Menu::getParentId, add.getParentId()), Boolean.FALSE
        );
        ArgumentResponseEnum.MENU_VALID_ERROR_ADD_02.assertIsNull(menu);
        return MenuConverter.INSTANCE.getAddEntity(add);
    }

    /**
     * 更新校验
     *
     * @param update - 更新参数
     * @return Menu - 菜单权限
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private Menu menuUpdateVerification(final MenuUpdateDTO update) {
        Menu menu = this.menuService.getById(update.getId());
        ArgumentResponseEnum.MENU_VALID_ERROR_UPDATE_02.assertNotNull(menu);
        if (AgileUtil.isNotEmpty(update.getParentId()) && !update.getParentId().equals(menu.getParentId())){
            // 验证父级菜单权限是否存在
            Menu parent = this.menuService.getById(update.getParentId());
            ArgumentResponseEnum.MENU_VALID_ERROR_UPDATE_03.assertNotNull(parent);
        }
        if (AgileUtil.isNotEmpty(update.getName()) && !menu.getName().equals(update.getName())) {
            // 验证 菜单权限名称 是否存在重复
            Menu menuByName = this.menuService.getOne(Wrappers.<Menu>lambdaQuery()
                    .eq(Menu::getName, update.getName()).eq(Menu::getMenuType, update.getMenuType())
                    .eq(AgileUtil.isNotEmpty(update.getParentId()), Menu::getParentId, update.getParentId()), Boolean.FALSE
            );
            ArgumentResponseEnum.MENU_VALID_ERROR_UPDATE_04.assertIsNull(menuByName);
        }
        MenuConverter.INSTANCE.getUpdateEntity(menu, update);
        return menu;
    }

    /**
     * 树查询
     *
     * @return CommonResponse<List < MenuTreeInfoVO>> - 菜单权限
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public CommonResponse<List<MenuTreeInfoVO>> queryMenuTreeList() {
        return new CommonResponse<>(this.menuService.queryMenuTreeList());
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
    public BaseResponse add(final MenuAddDTO add) {
        // 1、添加校验
        Menu menu = this.menuAddVerification(add);
        // 2、创建新菜单权限
        boolean save = this.menuService.save(menu);
        ArgumentResponseEnum.MENU_VALID_ERROR_ADD_01.assertIsTrue(save);
        return new CommonResponse<>();
    }

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return CommonResponse<MenuInfoVO> - 菜单权限
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public CommonResponse<MenuInfoVO> queryById(final Long id) {
        // 查询信息
        MenuInfoVO menuInfo = this.menuService.queryById(id);
        return new CommonResponse<>(menuInfo);
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
    public BaseResponse updateById(final MenuUpdateDTO update) {
        // 1、更新校验
        Menu menu = this.menuUpdateVerification(update);
        // 2、更新菜单权限
        boolean save = this.menuService.updateById(menu);
        ArgumentResponseEnum.MENU_VALID_ERROR_UPDATE_01.assertIsTrue(save);
        return new CommonResponse<>();
    }

    /**
     * 移除
     *
     * @param idList - 菜单权限标识
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse deleteByIdList(final List<Long> idList) {
        long count = this.menuService.count(Wrappers.<Menu>lambdaQuery().in(Menu::getParentId, idList));
        ArgumentResponseEnum.MENU_VALID_ERROR_DELETE_02.assertIsTrue(count == 0);
        long roleMenuCount = this.roleMenuService.count(Wrappers.<RoleMenu>lambdaQuery().in(RoleMenu::getMenuId, idList));
        ArgumentResponseEnum.MENU_VALID_ERROR_DELETE_03.assertIsTrue(roleMenuCount == 0);
        boolean delete = this.menuService.removeByIds(idList);
        ArgumentResponseEnum.MENU_VALID_ERROR_DELETE_01.assertIsTrue(delete);
        return new CommonResponse<>();
    }

}
