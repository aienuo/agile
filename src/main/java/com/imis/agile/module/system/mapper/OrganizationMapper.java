package com.imis.agile.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imis.agile.module.system.model.entity.Organization;
import com.imis.agile.module.system.model.vo.OrganizationInfoVO;
import com.imis.agile.module.system.model.vo.OrganizationTreeInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组织机构 Mapper 接口
 * </p>
 *
 * @author XinLau
 * @since 2020-04-22
 */
public interface OrganizationMapper extends BaseMapper<Organization> {

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
    OrganizationInfoVO queryById(@Param("id") final Long id);

    /**
     * 查询出最大的(靠后) 组织机构  最顶层的 \ 同一层的
     *
     * @param parentId - 父级标识
     * @return OrganizationInfoVO - 最大的(靠后) 组织机构
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    OrganizationInfoVO queryTopOrganization(@Param("parentId") final Long parentId);

}