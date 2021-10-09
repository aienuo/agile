package com.imis.agile.module.online.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.constant.base.BaseController;
import com.imis.agile.module.online.bus.BuildPageApiBus;
import com.imis.agile.module.online.model.dto.PagingQueryDataDTO;
import com.imis.agile.module.online.model.vo.PageElementsVO;
import com.imis.agile.response.BaseResponse;
import com.imis.agile.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * BuildPageApiController<br>
 * 构建页面相关 前端控制器
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月04日 14:42
 */
@Slf4j
@RestController
@RequestMapping(path = "/online/{id}")
@Api(tags = {"构建页面"})
@ApiSort(998)
public class BuildPageApiController extends BaseController<BuildPageApiBus> {

    @GetMapping()
    @ApiOperation(value = "页面元素查看", notes = "页面元素查看")
    @ApiImplicitParam(name = DataBaseConstant.P_KEY, value = "页面标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 1, author = "XinLau")
    public CommonResponse<PageElementsVO> queryElementsById(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id) {
        return service.queryElementsById(id);
    }

    @GetMapping(path = "/page")
    @ApiOperation(value = "查询页面列表数据", notes = "查询页面列表数据")
    @ApiImplicitParam(name = DataBaseConstant.P_KEY, value = "页面标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 2, author = "XinLau")
    public CommonResponse<?> pagingQueryListByParameter(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id, PagingQueryDataDTO pagingQuery) {
        return service.pagingQueryListByParameter(id, pagingQuery);
    }

    @PostMapping(path = "/add")
    @ApiOperation(value = "添加数据接口", notes = "单条添加")
    @ApiImplicitParam(name = DataBaseConstant.P_KEY, value = "页面标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 3, author = "XinLau")
    public BaseResponse add(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id, @RequestBody Map<String, Object> add) {
        return service.add(id, add);
    }

    @GetMapping(path = "/query/{dataId}")
    @ApiOperation(value = "查看接口", notes = "单条查看")
    @ApiImplicitParams({
            @ApiImplicitParam(name = DataBaseConstant.P_KEY, value = "页面标识", dataType = "Long", dataTypeClass = Long.class, required = true),
            @ApiImplicitParam(name = "dataId", value = "数据库表数据编号", dataType = "Long", dataTypeClass = Long.class, required = true)
    })
    @ApiOperationSupport(order = 4, author = "XinLau")
    public CommonResponse<Map<String, Object>> queryById(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id, @PathVariable(name = "dataId", required = true) Long dataId) {
        return service.queryById(id, dataId);
    }

    @PutMapping(path = "/update")
    @ApiOperation(value = "更新接口", notes = "单条更新")
    @ApiImplicitParam(name = DataBaseConstant.P_KEY, value = "页面标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 5, author = "XinLau")
    public BaseResponse updateById(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id, @RequestBody Map<String, Object> update) {
        return service.updateById(id, update);
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除接口", notes = "多条删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = DataBaseConstant.P_KEY, value = "页面标识", dataType = "Long", dataTypeClass = Long.class, required = true),
            @ApiImplicitParam(name = "idList", value = "数据库表数据编号", dataType = "Long", dataTypeClass = Long.class, required = true)
    })
    @ApiOperationSupport(order = 6, author = "XinLau")
    public BaseResponse deleteByIdList(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id, @RequestParam(name = "idList", required = true) List<Long> idList) {
        return service.deleteByIdList(id, idList);
    }

}
