package com.aienuo.agile.module.system.bus;

import com.aienuo.agile.constant.base.BaseBus;
import com.aienuo.agile.constant.base.BaseResponse;
import com.aienuo.agile.constant.enums.ArgumentResponseEnum;
import com.aienuo.agile.module.system.model.converter.MenuConverter;
import com.aienuo.agile.module.system.model.dto.MenuAddDTO;
import com.aienuo.agile.module.system.model.dto.MenuUpdateDTO;
import com.aienuo.agile.module.system.model.entity.Menu;
import com.aienuo.agile.module.system.model.entity.RoleMenu;
import com.aienuo.agile.module.system.model.vo.MenuInfoVO;
import com.aienuo.agile.module.system.model.vo.MenuTreeInfoVO;
import com.aienuo.agile.module.system.service.IMenuService;
import com.aienuo.agile.module.system.service.IRoleMenuService;
import com.aienuo.agile.response.CommonResponse;
import com.aienuo.agile.util.AgileUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
        if (AgileUtil.isNotEmpty(add.getParentId())) {
            // 验证父级菜单权限是否存在
            Menu parent = this.menuService.getById(add.getParentId());
            ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertNotNull(parent, "菜单权限", "父级菜单权限信息不存在");
        }
        // 验证 菜单权限名称 是否存在重复
        Menu menu = this.menuService.getOne(Wrappers.<Menu>lambdaQuery()
                .eq(Menu::getName, add.getName()).eq(Menu::getMenuType, add.getMenuType())
                .eq(AgileUtil.isNotEmpty(add.getParentId()), Menu::getParentId, add.getParentId()), Boolean.FALSE
        );
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsNull(menu, "菜单权限", "菜单权限名称存在重复");
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
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(menu, "菜单权限", "菜单权限信息不存在");
        if (AgileUtil.isNotEmpty(update.getParentId()) && !update.getParentId().equals(menu.getParentId())) {
            // 验证父级菜单权限是否存在
            Menu parent = this.menuService.getById(update.getParentId());
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(parent, "菜单权限", "父级菜单权限信息不存在");
        }
        if (AgileUtil.isNotEmpty(update.getName()) && !menu.getName().equals(update.getName())) {
            // 验证 菜单权限名称 是否存在重复
            Menu menuByName = this.menuService.getOne(Wrappers.<Menu>lambdaQuery()
                    .eq(Menu::getName, update.getName()).eq(Menu::getMenuType, update.getMenuType())
                    .eq(AgileUtil.isNotEmpty(update.getParentId()), Menu::getParentId, update.getParentId()), Boolean.FALSE
            );
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsNull(menuByName, "菜单权限", "菜单权限名称存在重复");
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
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsTrue(save, "菜单权限", "请确认信息准确无误后重新添加");
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
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(save, "菜单权限", "请确认信息准确无误后重新更新");
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
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(count == 0, "菜单权限", "部分菜单权限下存在子节点");
        long roleMenuCount = this.roleMenuService.count(Wrappers.<RoleMenu>lambdaQuery().in(RoleMenu::getMenuId, idList));
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(roleMenuCount == 0, "菜单权限", "部分菜单权限已分配给角色使用");
        boolean delete = this.menuService.removeByIds(idList);
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(delete, "菜单权限", "请确认信息准确无误后重新删除");
        return new CommonResponse<>();
    }

}
