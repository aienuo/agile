package com.imis.agile.module.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.constant.base.BaseController;
import com.imis.agile.constant.base.BaseResponse;
import com.imis.agile.module.system.bus.OrganizationBus;
import com.imis.agile.module.system.model.dto.OrganizationAddDTO;
import com.imis.agile.module.system.model.dto.OrganizationEditDTO;
import com.imis.agile.module.system.model.dto.OrganizationUpdateDTO;
import com.imis.agile.module.system.model.vo.OrganizationInfoVO;
import com.imis.agile.module.system.model.vo.OrganizationTreeInfoVO;
import com.imis.agile.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * OrganizationController<br>
 * 组织机构模块
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年08月24日 15:57
 */
@Slf4j
@RestController
@RequestMapping(path = "/sys/organization/")
@Api(tags = {"组织机构"})
@ApiSort(5)
public class OrganizationController extends BaseController<OrganizationBus> {

    @GetMapping(path = "tree")
    @ApiOperation(value = "树查询", notes = "树查询")
    @ApiOperationSupport(order = 1, author = "XinLau")
    public CommonResponse<List<OrganizationTreeInfoVO>> queryOrganizationTreeList() {
        return service.queryOrganizationTreeList();
    }

    @PostMapping(path = "add")
    @ApiOperation(value = "添加接口", notes = "单条添加")
    @ApiOperationSupport(order = 2, author = "XinLau")
    public BaseResponse add(@RequestBody @Valid OrganizationAddDTO add) {
        return service.add(add);
    }

    @GetMapping(path = "query/{id}")
    @ApiOperation(value = "查看接口", notes = "单条查看")
    @ApiImplicitParam(name = DataBaseConstant.P_KEY, value = "组织机构标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 3, author = "XinLau")
    public CommonResponse<OrganizationInfoVO> queryById(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id) {
        return service.queryById(id);
    }

    @PutMapping(path = "update")
    @ApiOperation(value = "更新接口", notes = "单条更新")
    @ApiOperationSupport(order = 4, author = "XinLau")
    public BaseResponse updateById(@RequestBody @Valid OrganizationUpdateDTO update) {
        return service.updateById(update);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除接口", notes = "多条删除")
    @ApiImplicitParam(name = "idList", value = "组织机构标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 5, author = "XinLau")
    public BaseResponse deleteByIdList(@RequestParam(name = "idList", required = true) List<Long> idList) {
        return service.deleteByIdList(idList);
    }

    @PutMapping(path = "edit")
    @ApiOperation(value = "编辑接口", notes = "编辑树节点")
    @ApiOperationSupport(order = 6, author = "XinLau")
    public BaseResponse editTreeNode(@RequestBody @Valid List<OrganizationEditDTO> editList) {
        return service.editTreeNode(editList);
    }

}
