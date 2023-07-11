package com.imis.agile.module.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.imis.agile.constant.base.BaseController;
import com.imis.agile.constant.base.BaseResponse;
import com.imis.agile.module.system.bus.DictBus;
import com.imis.agile.module.system.model.dto.*;
import com.imis.agile.module.system.model.vo.DictItemVO;
import com.imis.agile.module.system.model.vo.DictPageVO;
import com.imis.agile.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * DictController<br>
 * 字典管理模块
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月10日 09:14
 */
@Slf4j
@RestController
@RequestMapping(path = "/sys/dict/")
@Tag(name = "字典管理")
@ApiSort(1)
public class DictController extends BaseController<DictBus> {

    @GetMapping(path = "page")
    @Operation(summary = "分页查询", description = "分页查询")
    @ApiOperationSupport(order = 1, author = "XinLau")
    public CommonResponse<Page<DictPageVO>> pagingQueryListByParameter(PagingQueryDictDTO pagingQuery) {
        return service.pagingQueryListByParameter(pagingQuery);
    }

    @PostMapping(path = "add")
    @Operation(summary = "项添加接口", description = "单条添加")
    @ApiOperationSupport(order = 2, author = "XinLau")
    public BaseResponse add(@RequestBody @Valid DictAddDTO add) {
        return service.add(add);
    }

    @PutMapping(path = "update")
    @Operation(summary = "项更新接口", description = "单条更新")
    @ApiOperationSupport(order = 3, author = "XinLau")
    public BaseResponse updateById(@RequestBody @Valid DictUpdateDTO update) {
        return service.updateById(update);
    }

    @DeleteMapping(value = "delete")
    @Operation(summary = "项删除接口", description = "多条删除")
    @Parameter(name = "idList", description = "项标识", required = true)
    @ApiOperationSupport(order = 4, author = "XinLau")
    public BaseResponse deleteByIdList(@RequestParam(name = "idList", required = true) List<Long> idList) {
        return service.deleteByIdList(idList);
    }

    @GetMapping(path = "item/tree")
    @Operation(summary = "值树列表查看", description = "树列表查看")
    @ApiOperationSupport(order = 5, author = "XinLau")
    public CommonResponse<List<DictItemVO>> queryDictItemList(@Valid QueryDictItemDTO query) {
        return service.queryDictItemList(query);
    }

    @PostMapping(path = "item/add")
    @Operation(summary = "值添加接口", description = "单条添加")
    @ApiOperationSupport(order = 6, author = "XinLau")
    public BaseResponse add(@RequestBody @Valid DictItemAddDTO add) {
        return service.add(add);
    }

    @PutMapping(path = "item/update")
    @Operation(summary = "值更新接口", description = "单条更新")
    @ApiOperationSupport(order = 7, author = "XinLau")
    public BaseResponse updateById(@RequestBody @Valid DictItemUpdateDTO update) {
        return service.updateById(update);
    }

    @DeleteMapping(value = "item/delete")
    @Operation(summary = "值删除接口", description = "多条删除")
    @Parameter(name = "idList", description = "值标识", required = true)
    @ApiOperationSupport(order = 8, author = "XinLau")
    public BaseResponse deleteDictItemByIdList(@RequestParam(name = "idList", required = true) List<Long> idList) {
        return service.deleteDictItemByIdList(idList);
    }

}
