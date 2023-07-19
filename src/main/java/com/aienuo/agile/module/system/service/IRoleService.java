package com.aienuo.agile.module.system.service;

import com.aienuo.agile.module.system.model.dto.PagingQueryRoleDTO;
import com.aienuo.agile.module.system.model.entity.Role;
import com.aienuo.agile.module.system.model.vo.RoleInfoVO;
import com.aienuo.agile.module.system.model.vo.RolePageVO;
import com.aienuo.agile.module.system.model.vo.RoleVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色管理 服务类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-12
 */
public interface IRoleService extends IService<Role> {

    /**
     * 查询系统用户的角色信息
     *
     * @param userId - 系统用户编号
     * @return List<RoleVO> - 角色列表
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    List<com.aienuo.agile.module.api.model.vo.RoleVO> queryRoleListByUserId(final Long userId);

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return Page<RolePageVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/12 15:47
     */
    Page<RolePageVO> pagingQueryListByParameter(final PagingQueryRoleDTO pagingQuery);

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return RoleInfoVO - 角色信息
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    RoleInfoVO queryById(final Long id);

    /**
     * 列表查看
     *
     * @return List<RoleVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    List<RoleVO> queryList();

}
