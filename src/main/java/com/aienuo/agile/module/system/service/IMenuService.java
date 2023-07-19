package com.aienuo.agile.module.system.service;

import com.aienuo.agile.module.api.model.vo.ButtonVO;
import com.aienuo.agile.module.api.model.vo.MenuTreeVO;
import com.aienuo.agile.module.system.model.entity.Menu;
import com.aienuo.agile.module.system.model.vo.MenuInfoVO;
import com.aienuo.agile.module.system.model.vo.MenuTreeInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单权限 服务类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-17
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 查询系统用户的菜单权限信息
     *
     * @param userId - 系统用户编号
     * @return List<MenuTreeVO> - 菜单权限
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    List<MenuTreeVO> queryMenuTreeListByUserId(final Long userId);

    /**
     * 查询系统用户的按钮权限信息
     *
     * @param userId - 系统用户编号
     * @return List<MenuTreeVO> - 按钮权限
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    List<ButtonVO> queryButtonListByUserId(final Long userId);

    /**
     * 树查询
     *
     * @return List<MenuTreeInfoVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    List<MenuTreeInfoVO> queryMenuTreeList();

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return MenuInfoVO - 菜单权限
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    MenuInfoVO queryById(final Long id);

}
