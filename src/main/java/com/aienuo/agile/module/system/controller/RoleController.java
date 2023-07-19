package com.aienuo.agile.module.system.controller;

import com.aienuo.agile.constant.DataBaseConstant;
import com.aienuo.agile.constant.base.BaseController;
import com.aienuo.agile.constant.base.BaseResponse;
import com.aienuo.agile.module.system.bus.RoleBus;
import com.aienuo.agile.module.system.model.dto.PagingQueryRoleDTO;
import com.aienuo.agile.module.system.model.dto.RoleAddDTO;
import com.aienuo.agile.module.system.model.dto.RoleUpdateDTO;
import com.aienuo.agile.module.system.model.vo.RoleInfoVO;
import com.aienuo.agile.module.system.model.vo.RolePageVO;
import com.aienuo.agile.module.system.model.vo.RoleVO;
import com.aienuo.agile.response.CommonResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * RoleController<br>
 * 角色管理模块
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月10日 09:14
 */
@Slf4j
@RestController
@RequestMapping(path = "/sys/role/")
@Tag(name = "角色管理")
@ApiSort(3)
public class RoleController extends BaseController<RoleBus> {

    @GetMapping(path = "page")
    @Operation(summary = "分页查询", description = "分页查询")
    @ApiOperationSupport(order = 1, author = "XinLau")
    public CommonResponse<Page<RolePageVO>> pagingQueryListByParameter(PagingQueryRoleDTO pagingQuery) {
        return service.pagingQueryListByParameter(pagingQuery);
    }

    @PostMapping(path = "add")
    @Operation(summary = "添加接口", description = "单条添加")
    @ApiOperationSupport(order = 2, author = "XinLau")
    public BaseResponse add(@RequestBody @Valid RoleAddDTO add) {
        return service.add(add);
    }

    @GetMapping(path = "query/{id}")
    @Operation(summary = "查看接口", description = "单条查看")
    @Parameter(name = DataBaseConstant.P_KEY, description = "角色标识", required = true)
    @ApiOperationSupport(order = 3, author = "XinLau")
    public CommonResponse<RoleInfoVO> queryById(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id) {
        return service.queryById(id);
    }

    @PutMapping(path = "update")
    @Operation(summary = "更新接口", description = "单条更新")
    @ApiOperationSupport(order = 4, author = "XinLau")
    public BaseResponse updateById(@RequestBody @Valid RoleUpdateDTO update) {
        return service.updateById(update);
    }

    @DeleteMapping(value = "delete")
    @Operation(summary = "删除接口", description = "多条删除")
    @Parameter(name = "idList", description = "角色标识", required = true)
    @ApiOperationSupport(order = 5, author = "XinLau")
    public BaseResponse deleteByIdList(@RequestParam(name = "idList", required = true) List<Long> idList) {
        return service.deleteByIdList(idList);
    }

    @GetMapping(path = "list")
    @Operation(summary = "列表查看", description = "列表查看")
    @ApiOperationSupport(order = 6, author = "XinLau")
    public CommonResponse<List<RoleVO>> queryList() {
        return service.queryList();
    }

}
