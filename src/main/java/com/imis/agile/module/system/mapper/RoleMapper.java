package com.imis.agile.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.agile.module.system.model.dto.PagingQueryRoleDTO;
import com.imis.agile.module.system.model.entity.Role;
import com.imis.agile.module.system.model.vo.RoleInfoVO;
import com.imis.agile.module.system.model.vo.RolePageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色管理 Mapper 接口
 * </p>
 *
 * @author XinLau
 * @since 2020-03-12
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询角色信息
     *
     * @param roleIdList - 角色编号
     * @return List<RoleVO> - 角色列表
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    List<com.imis.agile.module.api.model.vo.RoleVO> queryRoleListByIdList(@Param("roleIdList") final List<Long> roleIdList);

    /**
     * 分页查询
     *
     * @param pagingQueryList - 分页查询返回值
     * @param pagingQuery     - 分页查询对象
     * @return Page<RolePageVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/12 15:47
     */
    Page<RolePageVO> pagingQueryListByParameter(@Param("pg") final Page<RolePageVO> pagingQueryList, @Param("param") final PagingQueryRoleDTO pagingQuery);

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return RoleInfoVO - 角色信息
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    RoleInfoVO queryById(@Param("id") final Long id);

    /**
     * 列表查看
     *
     * @return List<RoleVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    List<com.imis.agile.module.system.model.vo.RoleVO> queryList();

}