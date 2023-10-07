package com.aienuo.agile.module.system.bus;

import com.aienuo.agile.constant.base.BaseBus;
import com.aienuo.agile.constant.base.BaseResponse;
import com.aienuo.agile.constant.enums.ArgumentResponseEnum;
import com.aienuo.agile.module.system.model.converter.OrganizationConverter;
import com.aienuo.agile.module.system.model.dto.OrganizationAddDTO;
import com.aienuo.agile.module.system.model.dto.OrganizationEditDTO;
import com.aienuo.agile.module.system.model.dto.OrganizationUpdateDTO;
import com.aienuo.agile.module.system.model.entity.Organization;
import com.aienuo.agile.module.system.model.entity.UserOrganization;
import com.aienuo.agile.module.system.model.vo.OrganizationInfoVO;
import com.aienuo.agile.module.system.model.vo.OrganizationTreeInfoVO;
import com.aienuo.agile.module.system.model.vo.OrganizationUserVO;
import com.aienuo.agile.module.system.service.IOrganizationService;
import com.aienuo.agile.module.system.service.IUserOrganizationService;
import com.aienuo.agile.response.CommonResponse;
import com.aienuo.agile.util.AgileUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationBus extends BaseBus {

    /**
     * 组织机构 服务类
     */
    private final IOrganizationService organizationService;

    /**
     * 用户组织机构关联 服务类
     */
    private final IUserOrganizationService userOrganizationService;

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
            ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertNotNull(parent, "组织机构", "父级组织机构信息不存在");
        }
        // 验证 组织机构名称 是否存在重复
        Organization organization = this.organizationService.getOne(Wrappers.<Organization>lambdaQuery()
                .eq(Organization::getOrganizationName, add.getOrganizationName())
                .eq(AgileUtil.isNotEmpty(add.getParentId()), Organization::getParentId, add.getParentId()), Boolean.FALSE
        );
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsNull(organization, "组织机构", "组织机构名称存在重复");
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
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(organization, "组织机构", "组织机构信息不存在");
        if (AgileUtil.isNotEmpty(update.getParentId())) {
            // 验证父级组织机构是否存在
            Organization parent = this.organizationService.getById(update.getParentId());
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotNull(parent, "组织机构", "父级组织机构信息不存在");
        }
        if (AgileUtil.isNotEmpty(update.getOrganizationName()) && !organization.getOrganizationName().equals(update.getOrganizationName())) {
            // 验证 组织机构名称 是否存在重复
            Organization organizationByName = this.organizationService.getOne(Wrappers.<Organization>lambdaQuery()
                    .eq(Organization::getOrganizationName, update.getOrganizationName())
                    .eq(AgileUtil.isNotEmpty(update.getParentId()), Organization::getParentId, update.getParentId()), Boolean.FALSE
            );
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsNull(organizationByName, "组织机构", "组织机构名称存在重复");
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
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotEmpty(parentOrganizationList, "组织机构", "父级组织机构信息不存在");
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(parentIdList.size() == parentOrganizationList.size(), "组织机构", "父级组织机构信息不存在");
        }
        // 将要变更节点 编号
        List<Long> idList = editList.stream().map(OrganizationEditDTO::getId).filter(AgileUtil::isNotEmpty).distinct().collect(Collectors.toList());
        if (AgileUtil.isNotEmpty(idList)) {
            // 判断 组织机构 是否存在
            organizationList = this.organizationService.listByIds(idList);
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotEmpty(organizationList, "组织机构", "组织机构信息不存在");
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(idList.size() == organizationList.size(), "组织机构", "组织机构信息不存在");
        }
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertNotEmpty(organizationList, "组织机构", "请确认信息准确无误后重新编辑");
        // 构建数据
        Map<Long, OrganizationEditDTO> organizationEditMap = editList.stream().collect(Collectors.toMap(OrganizationEditDTO::getId, Function.identity()));
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
        ArgumentResponseEnum.INSERT_PARAMETERS_VALID_ERROR.assertIsTrue(save, "组织机构", "请确认信息准确无误后重新添加");
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
        if (AgileUtil.isNotEmpty(organizationInfo)) {
            UserOrganization userOrganization = this.userOrganizationService.getOne(Wrappers.<UserOrganization>lambdaQuery().eq(UserOrganization::getOrganizationId, id).eq(UserOrganization::getResponsible, 1), Boolean.FALSE);
            if (AgileUtil.isNotEmpty(userOrganization)) {
                organizationInfo.setOrganizationUserId(String.valueOf(userOrganization.getId()));
            }
        }
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
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateById(final OrganizationUpdateDTO update) {
        // 1、更新校验
        Organization organization = this.organizationUpdateVerification(update);
        // 2、更新组织机构
        boolean save = this.organizationService.updateById(organization);
        ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(save, "组织机构", "请确认信息准确无误后重新更新");
        // 3、更新部门负责人
        if (AgileUtil.isNotEmpty(update.getOrganizationUserId())) {
            List<UserOrganization> userOrganizationList = this.userOrganizationService.list(Wrappers.<UserOrganization>lambdaQuery().eq(UserOrganization::getOrganizationId, update.getId()));
            if (AgileUtil.isNotEmpty(userOrganizationList)) {
                userOrganizationList.forEach(
                        userOrganization -> userOrganization.setResponsible(userOrganization.getId().equals(update.getOrganizationUserId()) ? 1 : 0)
                );
                boolean updateBatchById = this.userOrganizationService.updateBatchById(userOrganizationList);
                ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(updateBatchById, "组织机构", "更新部门负责人失败");
            }
        }
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
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteByIdList(final List<Long> idList) {
        long count = this.organizationService.count(Wrappers.<Organization>lambdaQuery().in(Organization::getParentId, idList));
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(count == 0, "组织机构", "部分组织机构下存在子节点");
        long userOrganizationCount = this.userOrganizationService.count(Wrappers.<UserOrganization>lambdaQuery().in(UserOrganization::getOrganizationId, idList));
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(userOrganizationCount == 0, "组织机构", "请确认信息准确无误后重新更新");
        boolean delete = this.organizationService.removeByIds(idList);
        ArgumentResponseEnum.DELETE_PARAMETERS_VALID_ERROR.assertIsTrue(delete, "组织机构", "部分组织机构下存在用户");
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
            ArgumentResponseEnum.UPDATE_PARAMETERS_VALID_ERROR.assertIsTrue(save, "组织机构", "请确认信息准确无误后重新编辑");
            return new CommonResponse<>();
        }
        return new CommonResponse<>();
    }

    /**
     * 根据组织机构编号查询组织机构下的用户
     *
     * @param id - 组织机构标识
     * @return CommonResponse<List < OrganizationUserVO>>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/5 17:25
     */
    public CommonResponse<List<OrganizationUserVO>> queryOrganizationUserByOrganizationId(final Long id) {
        List<OrganizationUserVO> organizationUserList = this.userOrganizationService.queryOrganizationUserByOrganizationId(id);
        return new CommonResponse<>(organizationUserList);
    }

}
