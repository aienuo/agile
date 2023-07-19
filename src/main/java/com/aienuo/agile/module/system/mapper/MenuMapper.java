package com.aienuo.agile.module.system.mapper;

import com.aienuo.agile.module.api.model.vo.ButtonVO;
import com.aienuo.agile.module.api.model.vo.MenuTreeVO;
import com.aienuo.agile.module.system.model.entity.Menu;
import com.aienuo.agile.module.system.model.vo.MenuInfoVO;
import com.aienuo.agile.module.system.model.vo.MenuTreeInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单权限 Mapper 接口
 * </p>
 *
 * @author XinLau
 * @since 2020-03-17
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查询菜单权限信息
     *
     * @param menuIdList - 菜单权限编号
     * @return List<MenuTreeVO> - 菜单权限
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    List<MenuTreeVO> queryMenuTreeListByIdList(@Param("menuIdList") final List<Long> menuIdList);

    /**
     * 查询按钮权限信息
     *
     * @param buttonIdList - 按钮权限编号
     * @return List<ButtonVO> - 按钮权限
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    List<ButtonVO> queryButtonListByIdList(@Param("buttonIdList") final List<Long> buttonIdList);

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
    MenuInfoVO queryById(@Param("id") final Long id);

}
