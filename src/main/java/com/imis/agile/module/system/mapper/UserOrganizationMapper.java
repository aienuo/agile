package com.imis.agile.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imis.agile.module.system.model.entity.UserOrganization;
import com.imis.agile.module.system.model.vo.OrganizationUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户组织机构关联 Mapper 接口
 * </p>
 *
 * @author XinLau
 * @since 2020-06-18
 */
public interface UserOrganizationMapper extends BaseMapper<UserOrganization> {

    /**
     * 根据组织机构编号查询组织机构下的用户
     *
     * @param organizationId - 组织机构编号
     * @return List<OrganizationUserVO> - 组织机构下的用户
     */
    List<OrganizationUserVO> queryOrganizationUserByOrganizationId(@Param("organizationId") final Long organizationId);

}