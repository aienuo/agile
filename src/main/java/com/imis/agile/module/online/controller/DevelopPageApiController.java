package com.imis.agile.module.online.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.constant.base.BaseController;
import com.imis.agile.module.api.model.vo.ItemVO;
import com.imis.agile.module.online.bus.DevelopPageApiBus;
import com.imis.agile.module.online.model.dto.PagingTableBasicDTO;
import com.imis.agile.module.online.model.dto.TableAddDTO;
import com.imis.agile.module.online.model.vo.TableBasicPageVO;
import com.imis.agile.module.system.model.dto.RoleUpdateDTO;
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
 * BuildPageApiController<br>
 * 表单开发相关 前端控制器
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月04日 14:42
 */
@Slf4j
@RestController
@RequestMapping(path = "/online/develop/")
@Api(tags = {"表单开发"})
@ApiSort(988)
public class DevelopPageApiController extends BaseController<DevelopPageApiBus> {

    @GetMapping(path = "page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiOperationSupport(order = 1, author = "XinLau")
    public CommonResponse<Page<TableBasicPageVO>> pagingQueryListByParameter(PagingTableBasicDTO pagingQuery) {
        return service.pagingQueryListByParameter(pagingQuery);
    }

    @GetMapping(path = "query/field/{tableName}")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParam(name = "tableName", value = "数据库表名称", dataType = "String", dataTypeClass = String.class, required = true)
    @ApiOperationSupport(order = 2, author = "XinLau")
    public CommonResponse<List<ItemVO>> queryMainTableFieldListByTableName(@PathVariable(name = "tableName", required = true) String tableName) {
        return service.queryMainTableFieldListByTableName(tableName);
    }

    @PostMapping(path = "add")
    @ApiOperation(value = "添加接口", notes = "单条添加")
    @ApiOperationSupport(order = 3, author = "XinLau")
    public BaseResponse add(@RequestBody @Valid TableAddDTO add) {
        return service.add(add);
    }

    @GetMapping(path = "query/{id}")
    @ApiOperation(value = "查看接口", notes = "单条查看")
    @ApiImplicitParam(name = DataBaseConstant.P_KEY, value = "表单标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 4, author = "XinLau")
    public CommonResponse<?> queryById(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id) {
        // return service.queryById(id);
        return new CommonResponse<>();
    }

    @PutMapping(path = "update")
    @ApiOperation(value = "更新接口", notes = "单条更新")
    @ApiOperationSupport(order = 5, author = "XinLau")
    public BaseResponse updateById(@RequestBody @Valid RoleUpdateDTO update) {
        // return service.updateById(update);
        return new CommonResponse<>();
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除接口", notes = "多条删除")
    @ApiImplicitParam(name = "idList", value = "用户标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 6, author = "XinLau")
    public BaseResponse deleteByIdList(@RequestParam(name = "idList", required = true) List<Long> idList) {
        // return service.deleteByIdList(idList);
        return new CommonResponse<>();
    }

    @PutMapping(path = "database/{id}")
    @ApiOperation(value = "生成表到数据库接口", notes = "单条生成表到数据库")
    @ApiImplicitParam(name = DataBaseConstant.P_KEY, value = "表单标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 7, author = "XinLau")
    public CommonResponse<?> generateDatabase(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id) {
        // return service.generateDatabaseById(id);
        return new CommonResponse<>();
    }

}
