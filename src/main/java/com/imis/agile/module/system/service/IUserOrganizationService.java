package com.imis.agile.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imis.agile.module.system.model.entity.UserOrganization;
import com.imis.agile.module.system.model.vo.OrganizationUserVO;

import java.util.List;

/**
 * <p>
 * 用户组织机构关联 服务类
 * </p>
 *
 * @author XinLau
 * @since 2020-06-18
 */
public interface IUserOrganizationService extends IService<UserOrganization> {

    /**
     * 根据组织机构编号查询组织机构下的用户
     *
     * @param organizationId - 组织机构编号
     * @return List<OrganizationUserVO> - 组织机构下的用户
     */
    List<OrganizationUserVO> queryOrganizationUserByOrganizationId(final Long organizationId);

}