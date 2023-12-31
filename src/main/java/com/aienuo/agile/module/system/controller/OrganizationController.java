package com.aienuo.agile.module.system.controller;

import com.aienuo.agile.constant.DataBaseConstant;
import com.aienuo.agile.constant.base.BaseController;
import com.aienuo.agile.constant.base.BaseResponse;
import com.aienuo.agile.module.system.bus.OrganizationBus;
import com.aienuo.agile.module.system.model.dto.OrganizationAddDTO;
import com.aienuo.agile.module.system.model.dto.OrganizationEditDTO;
import com.aienuo.agile.module.system.model.dto.OrganizationUpdateDTO;
import com.aienuo.agile.module.system.model.vo.OrganizationInfoVO;
import com.aienuo.agile.module.system.model.vo.OrganizationTreeInfoVO;
import com.aienuo.agile.module.system.model.vo.OrganizationUserVO;
import com.aienuo.agile.response.CommonResponse;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "组织机构")
@ApiSort(5)
public class OrganizationController extends BaseController<OrganizationBus> {

    @GetMapping(path = "tree")
    @Operation(summary = "树查询", description = "树查询")
    @ApiOperationSupport(order = 1, author = "XinLau")
    public CommonResponse<List<OrganizationTreeInfoVO>> queryOrganizationTreeList() {
        return service.queryOrganizationTreeList();
    }

    @PostMapping(path = "add")
    @Operation(summary = "添加接口", description = "单条添加")
    @ApiOperationSupport(order = 2, author = "XinLau")
    public BaseResponse add(@RequestBody @Valid OrganizationAddDTO add) {
        return service.add(add);
    }

    @GetMapping(path = "query/{id}")
    @Operation(summary = "查看接口", description = "单条查看")
    @Parameter(name = DataBaseConstant.P_KEY, description = "组织机构标识", required = true)
    @ApiOperationSupport(order = 3, author = "XinLau")
    public CommonResponse<OrganizationInfoVO> queryById(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id) {
        return service.queryById(id);
    }

    @PutMapping(path = "update")
    @Operation(summary = "更新接口", description = "单条更新")
    @ApiOperationSupport(order = 4, author = "XinLau")
    public BaseResponse updateById(@RequestBody @Valid OrganizationUpdateDTO update) {
        return service.updateById(update);
    }

    @DeleteMapping(value = "delete")
    @Operation(summary = "删除接口", description = "多条删除")
    @Parameter(name = "idList", description = "组织机构标识", required = true)
    @ApiOperationSupport(order = 5, author = "XinLau")
    public BaseResponse deleteByIdList(@RequestParam(name = "idList", required = true) List<Long> idList) {
        return service.deleteByIdList(idList);
    }

    @PutMapping(path = "edit")
    @Operation(summary = "编辑接口", description = "编辑树节点")
    @ApiOperationSupport(order = 6, author = "XinLau")
    public BaseResponse editTreeNode(@RequestBody @Valid List<OrganizationEditDTO> editList) {
        return service.editTreeNode(editList);
    }

    @GetMapping(path = "user/{id}")
    @Operation(summary = "组织机构下的用户", description = "列表查看")
    @Parameter(name = DataBaseConstant.P_KEY, description = "组织机构标识", required = true)
    @ApiOperationSupport(order = 7, author = "XinLau")
    public CommonResponse<List<OrganizationUserVO>> queryOrganizationUserByOrganizationId(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id) {
        return service.queryOrganizationUserByOrganizationId(id);
    }

}
