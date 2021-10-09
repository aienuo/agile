package com.imis.agile.module.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.module.system.mapper.RoleMapper;
import com.imis.agile.module.system.model.dto.PagingQueryRoleDTO;
import com.imis.agile.module.system.model.entity.Role;
import com.imis.agile.module.system.model.entity.UserRole;
import com.imis.agile.module.system.model.vo.RoleInfoVO;
import com.imis.agile.module.system.model.vo.RolePageVO;
import com.imis.agile.module.system.service.IRoleService;
import com.imis.agile.module.system.service.IUserRoleService;
import com.imis.agile.util.AgileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色管理 服务实现类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-12
 */
@Service
@Slf4j
@DS(DataBaseConstant.DATA_SOURCE_MASTER)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    /**
     * 用户角色关联 服务类
     */
    private IUserRoleService userRoleService;

    @Autowired
    public void setUserRoleService(IUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * 查询系统用户的角色信息
     *
     * @param userId - 系统用户编号
     * @return List<RoleVO> - 角色列表
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/6 14:26
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public List<com.imis.agile.module.api.model.vo.RoleVO> queryRoleListByUserId(final Long userId) {
        List<com.imis.agile.module.api.model.vo.RoleVO> roleList = new ArrayList<>();
        // 用户角色关联
        List<UserRole> userRoleList = this.userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userId));
        if (AgileUtil.isNotEmpty(userRoleList)){
            // 角色编号
            List<Long> roleIdList = userRoleList.stream().map(UserRole::getRoleId).filter(AgileUtil::isNotEmpty).distinct().collect(Collectors.toList());
            roleList = this.baseMapper.queryRoleListByIdList(roleIdList);
        }
        return roleList;
    }

    /**
     * 分页查询
     *
     * @param pagingQuery - 分页查询对象
     * @return Page<RolePageVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/12 15:47
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public Page<RolePageVO> pagingQueryListByParameter(final PagingQueryRoleDTO pagingQuery) {
        // 1、页码、页长
        Page<RolePageVO> pagingQueryList = new Page<>(pagingQuery.getPageNumber(), pagingQuery.getPageSize());
        // 2、排序字段
        if (AgileUtil.isNotEmpty(pagingQuery.getSortFieldList())) {
            pagingQueryList.addOrder(pagingQuery.getSortFieldList());
        }
        // 3、条件分页查询
        pagingQueryList = this.baseMapper.pagingQueryListByParameter(pagingQueryList, pagingQuery);
        return pagingQueryList;
    }

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return RoleInfoVO - 角色信息
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public RoleInfoVO queryById(final Long id) {
        return this.baseMapper.queryById(id);
    }

    /**
     * 列表查看
     *
     * @return List<RoleVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public List<com.imis.agile.module.system.model.vo.RoleVO> queryList() {
        return this.baseMapper.queryList();
    }

}