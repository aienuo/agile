package com.imis.agile.module.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.module.system.mapper.OrganizationMapper;
import com.imis.agile.module.system.model.entity.Organization;
import com.imis.agile.module.system.model.vo.OrganizationInfoVO;
import com.imis.agile.module.system.model.vo.OrganizationTreeInfoVO;
import com.imis.agile.module.system.service.IOrganizationService;
import com.imis.agile.util.AgileUtil;
import com.imis.agile.util.BuildingTreeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 组织机构 服务实现类
 * </p>
 *
 * @author XinLau
 * @since 2020-04-22
 */
@Slf4j
@Service
@DS(DataBaseConstant.DATA_SOURCE_MASTER)
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements IOrganizationService {

    /**
     * 树查询
     *
     * @return List<OrganizationTreeInfoVO>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public List<OrganizationTreeInfoVO> queryOrganizationTreeList() {
        // 查询数据
        List<OrganizationTreeInfoVO> organizationTreeList = this.baseMapper.queryOrganizationTreeList();
        if (AgileUtil.isNotEmpty(organizationTreeList)) {
            // 构建数据
            BuildingTreeData<OrganizationTreeInfoVO> buildingTreeData = new BuildingTreeData<>();
            return buildingTreeData.buildingTreeData(organizationTreeList);
        }
        return organizationTreeList;
    }

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return OrganizationInfoVO - 组织机构
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public OrganizationInfoVO queryById(final Long id) {
        return this.baseMapper.queryById(id);
    }

    /**
     * 查询出最大的(靠后) 组织机构  最顶层的 \ 同一层的
     *
     * @param parentId - 父级标识
     * @return OrganizationInfoVO - 最大的(靠后) 组织机构
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_SLAVE)
    public OrganizationInfoVO queryTopOrganization(final Long parentId) {
        return this.baseMapper.queryTopOrganization(parentId);
    }

}