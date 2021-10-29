package com.imis.agile.module.system.bus;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.imis.agile.constant.base.BaseBus;
import com.imis.agile.constant.enums.ArgumentResponseEnum;
import com.imis.agile.module.system.model.converter.OrganizationConverter;
import com.imis.agile.module.system.model.dto.OrganizationAddDTO;
import com.imis.agile.module.system.model.dto.OrganizationEditDTO;
import com.imis.agile.module.system.model.dto.OrganizationUpdateDTO;
import com.imis.agile.module.system.model.entity.Organization;
import com.imis.agile.module.system.model.entity.UserOrganization;
import com.imis.agile.module.system.model.vo.OrganizationInfoVO;
import com.imis.agile.module.system.model.vo.OrganizationTreeInfoVO;
import com.imis.agile.module.system.service.IOrganizationService;
import com.imis.agile.module.system.service.IUserOrganizationService;
import com.imis.agile.response.BaseResponse;
import com.imis.agile.response.CommonResponse;
import com.imis.agile.util.AgileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * OrganizationBus<br>
 * 组织机构 业务处理类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年08月24日 14:54
 */
@Service
public class OrganizationBus extends BaseBus {

    /**
     * 组织机构 服务类
     */
    private IOrganizationService organizationService;

    @Autowired
    public void setOrganizationService(IOrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * 用户组织机构关联 服务类
     */
    private IUserOrganizationService userOrganizationService;

    @Autowired
    public void setUserOrganizationService(IUserOrganizationService userOrganizationService) {
        this.userOrganizationService = userOrganizationService;
    }

    /**
     * 添加校验
     *
     * @param add - 添加参数
     * @return Organization - 组织机构
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private Organization organizationAddVerification(final OrganizationAddDTO add) {
        if (AgileUtil.isNotEmpty(add.getParentId())) {
            // 验证父级组织机构是否存在
            Organization parent = this.organizationService.getById(add.getParentId());
            ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_ADD_03.assertNotNull(parent);
        }
        // 验证 组织机构名称 是否存在重复
        Organization organization = this.organizationService.getOne(Wrappers.<Organization>lambdaQuery()
                .eq(Organization::getOrganizationName, add.getOrganizationName())
                .eq(AgileUtil.isNotEmpty(add.getParentId()), Organization::getParentId, add.getParentId()), Boolean.FALSE
        );
        ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_ADD_02.assertIsNull(organization);
        return OrganizationConverter.INSTANCE.getAddEntity(add);
    }

    /**
     * 更新校验
     *
     * @param update - 更新参数
     * @return Organization - 组织机构
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private Organization organizationUpdateVerification(final OrganizationUpdateDTO update) {
        Organization organization = this.organizationService.getById(update.getId());
        ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_UPDATE_02.assertNotNull(organization);
        if (AgileUtil.isNotEmpty(update.getParentId())) {
            // 验证父级组织机构是否存在
            Organization parent = this.organizationService.getById(update.getParentId());
            ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_UPDATE_03.assertNotNull(parent);
        }
        if (AgileUtil.isNotEmpty(update.getOrganizationName()) && !organization.getOrganizationName().equals(update.getOrganizationName())) {
            // 验证 组织机构名称 是否存在重复
            Organization organizationByName = this.organizationService.getOne(Wrappers.<Organization>lambdaQuery()
                    .eq(Organization::getOrganizationName, update.getOrganizationName())
                    .eq(AgileUtil.isNotEmpty(update.getParentId()), Organization::getParentId, update.getParentId()), Boolean.FALSE
            );
            ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_UPDATE_04.assertIsNull(organizationByName);
        }
        OrganizationConverter.INSTANCE.getUpdateEntity(organization, update);
        return organization;
    }

    /**
     * 编辑树节点校验
     *
     * @param editList - 编辑参数
     * @return Organization - 组织机构
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    private List<Organization> organizationEditTreeNodeVerification(final List<OrganizationEditDTO> editList) {
        List<Organization> organizationList = new ArrayList<>();
        // 父级节点 编号
        List<Long> parentIdList = editList.stream().map(OrganizationEditDTO::getParentId).filter(AgileUtil::isNotEmpty).distinct().collect(Collectors.toList());
        if (AgileUtil.isNotEmpty(parentIdList)) {
            // 判断 父级 组织机构 是否存在
            List<Organization> parentOrganizationList = this.organizationService.listByIds(parentIdList);
            ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_UPDATE_03.assertNotEmpty(parentOrganizationList);
            ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_UPDATE_03.assertIsTrue(parentIdList.size() == parentOrganizationList.size());
        }
        // 将要变更节点 编号
        List<Long> idList = editList.stream().map(OrganizationEditDTO::getId).filter(AgileUtil::isNotEmpty).distinct().collect(Collectors.toList());
        if (AgileUtil.isNotEmpty(idList)) {
            // 判断 组织机构 是否存在
            organizationList = this.organizationService.listByIds(idList);
            ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_UPDATE_02.assertNotEmpty(organizationList);
            ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_UPDATE_02.assertIsTrue(idList.size() == organizationList.size());
        }
        ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_UPDATE_01.assertNotEmpty(organizationList);
        // 构建数据
        Map<Long, OrganizationEditDTO> organizationEditMap = new HashMap<>(editList.size());
        editList.forEach(organizationEdit -> organizationEditMap.put(organizationEdit.getId(), organizationEdit));
        organizationList.forEach(
                organization -> {
                    if (organizationEditMap.containsKey(organization.getId())) {
                        OrganizationEditDTO organizationEdit = organizationEditMap.get(organization.getId());
                        // 新 排序号
                        organization.setSortNo(organizationEdit.getSortNo());
                        // 新 父级组织机构
                        organization.setParentId(organizationEdit.getParentId());
                    }
                }
        );
        return organizationList;
    }

    /**
     * 树查询
     *
     * @return CommonResponse<List < OrganizationTreeInfoVO>> - 组织机构
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public CommonResponse<List<OrganizationTreeInfoVO>> queryOrganizationTreeList() {
        return new CommonResponse<>(this.organizationService.queryOrganizationTreeList());
    }

    /**
     * 添加
     *
     * @param add - 添加对象
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse add(final OrganizationAddDTO add) {
        // 1、添加校验
        Organization organization = this.organizationAddVerification(add);
        // 2、创建新组织机构
        boolean save = this.organizationService.save(organization);
        ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_ADD_01.assertIsTrue(save);
        return new CommonResponse<>();
    }

    /**
     * 查看
     *
     * @param id - 查看参数
     * @return CommonResponse<OrganizationInfoVO> - 组织机构
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public CommonResponse<OrganizationInfoVO> queryById(final Long id) {
        // 查询信息
        OrganizationInfoVO organizationInfo = this.organizationService.queryById(id);
        return new CommonResponse<>(organizationInfo);
    }

    /**
     * 更新
     *
     * @param update - 更新参数
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse updateById(final OrganizationUpdateDTO update) {
        // 1、更新校验
        Organization organization = this.organizationUpdateVerification(update);
        // 2、更新组织机构
        boolean save = this.organizationService.updateById(organization);
        ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_UPDATE_01.assertIsTrue(save);
        return new CommonResponse<>();
    }

    /**
     * 移除
     *
     * @param idList - 组织机构标识
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse deleteByIdList(final List<Long> idList) {
        long count = this.organizationService.count(Wrappers.<Organization>lambdaQuery().in(Organization::getParentId, idList));
        ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_DELETE_02.assertIsTrue(count == 0);
        long userOrganizationCount = this.userOrganizationService.count(Wrappers.<UserOrganization>lambdaQuery().in(UserOrganization::getOrganizationId, idList));
        ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_DELETE_03.assertIsTrue(userOrganizationCount == 0);
        boolean delete = this.organizationService.removeByIds(idList);
        ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_DELETE_01.assertIsTrue(delete);
        return new CommonResponse<>();
    }

    /**
     * 编辑树节点
     *
     * @param editList - 编辑参数
     * @return BaseResponse
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public BaseResponse editTreeNode(final List<OrganizationEditDTO> editList) {
        if (AgileUtil.isNotEmpty(editList)) {
            // 1、编辑校验 构建更信息数据
            List<Organization> organizationList = this.organizationEditTreeNodeVerification(editList);
            // 2、更新组织机构
            boolean save = this.organizationService.updateBatchById(organizationList);
            ArgumentResponseEnum.ORGANIZATION_VALID_ERROR_UPDATE_01.assertIsTrue(save);
            return new CommonResponse<>();
        }
        return new CommonResponse<>();
    }

}
