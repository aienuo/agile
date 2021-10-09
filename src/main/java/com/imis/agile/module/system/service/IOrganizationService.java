package com.imis.agile.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imis.agile.module.system.model.entity.Organization;
import com.imis.agile.module.system.model.vo.OrganizationInfoVO;
import com.imis.agile.module.system.model.vo.OrganizationTreeInfoVO;

import java.util.List;

/**
 * <p>
 * 组织机构 服务类
 * </p>
 *
 * @author XinLau
 * @since 2020-04-22
 */
public interface IOrganizationService extends IService<Organization> {

    /**
     * 树查询
     *
     * @return List<OrganizationTreeInfoVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    List<OrganizationTreeInfoVO> queryOrganizationTreeList();

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return OrganizationInfoVO - 组织机构
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    OrganizationInfoVO queryById(final Long id);

}