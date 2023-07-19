package com.aienuo.agile.module.system.service.impl;

import com.aienuo.agile.constant.DataBaseConstant;
import com.aienuo.agile.module.system.mapper.OrganizationMapper;
import com.aienuo.agile.module.system.model.entity.Organization;
import com.aienuo.agile.module.system.model.vo.OrganizationInfoVO;
import com.aienuo.agile.module.system.model.vo.OrganizationTreeInfoVO;
import com.aienuo.agile.module.system.service.IOrganizationService;
import com.aienuo.agile.util.AgileUtil;
import com.aienuo.agile.util.BuildingTreeData;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
