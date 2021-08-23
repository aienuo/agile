package com.imis.agile.module.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.constant.base.BaseController;
import com.imis.agile.module.system.bus.RoleBus;
import com.imis.agile.module.system.model.dto.PagingQueryRoleDTO;
import com.imis.agile.module.system.model.dto.RoleAddDTO;
import com.imis.agile.module.system.model.dto.RoleUpdateDTO;
import com.imis.agile.module.system.model.vo.RoleInfoVO;
import com.imis.agile.module.system.model.vo.RolePageVO;
import com.imis.agile.module.system.model.vo.RoleVO;
import com.imis.agile.response.BaseResponse;
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
@Api(tags = {"角色管理"})
@ApiSort(3)
public class RoleController extends BaseController<RoleBus> {

    @GetMapping(path = "page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiOperationSupport(order = 1, author = "XinLau")
    public CommonResponse<Page<RolePageVO>> pagingQueryListByParameter(PagingQueryRoleDTO pagingQuery) {
        return service.pagingQueryListByParameter(pagingQuery);
    }

    @PostMapping(path = "add")
    @ApiOperation(value = "添加接口", notes = "单条添加")
    @ApiOperationSupport(order = 2, author = "XinLau")
    public BaseResponse add(@RequestBody @Valid RoleAddDTO add) {
        return service.add(add);
    }

    @GetMapping(path = "query/{id}")
    @ApiOperation(value = "查看接口", notes = "单条查看")
    @ApiImplicitParam(name = DataBaseConstant.P_KEY, value = "角色标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 3, author = "XinLau")
    public CommonResponse<RoleInfoVO> queryById(@PathVariable(name = "id", required = true) Long id) {
        return service.queryById(id);
    }

    @PutMapping(path = "update")
    @ApiOperation(value = "更新接口", notes = "单条更新")
    @ApiOperationSupport(order = 4, author = "XinLau")
    public BaseResponse updateById(@RequestBody @Valid RoleUpdateDTO update) {
        return service.updateById(update);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除接口", notes = "多条删除")
    @ApiImplicitParam(name = "idList", value = "用户标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 5, author = "XinLau")
    public BaseResponse deleteByIdList(@RequestParam(name = "idList", required = true) List<Long> idList) {
        return service.deleteByIdList(idList);
    }

    @GetMapping(path = "list")
    @ApiOperation(value = "列表查看", notes = "列表查看")
    @ApiOperationSupport(order = 6, author = "XinLau")
    public CommonResponse<List<RoleVO>> queryList() {
        return service.queryList();
    }

}
