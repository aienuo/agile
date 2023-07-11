package com.imis.agile.module.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.constant.base.BaseController;
import com.imis.agile.constant.base.BaseResponse;
import com.imis.agile.module.system.bus.MenuBus;
import com.imis.agile.module.system.model.dto.MenuAddDTO;
import com.imis.agile.module.system.model.dto.MenuUpdateDTO;
import com.imis.agile.module.system.model.vo.MenuInfoVO;
import com.imis.agile.module.system.model.vo.MenuTreeInfoVO;
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
 * MenuController<br>
 * 菜单权限模块
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月10日 09:14
 */
@Slf4j
@RestController
@RequestMapping(path = "/sys/menu/")
@Tag(name = "菜单权限")
@ApiSort(4)
public class MenuController extends BaseController<MenuBus> {

    @GetMapping(path = "tree")
    @Operation(summary = "树查询", description = "树查询")
    @ApiOperationSupport(order = 1, author = "XinLau")
    public CommonResponse<List<MenuTreeInfoVO>> queryMenuTreeList() {
        return service.queryMenuTreeList();
    }

    @PostMapping(path = "add")
    @Operation(summary = "添加接口", description = "单条添加")
    @ApiOperationSupport(order = 2, author = "XinLau")
    public BaseResponse add(@RequestBody @Valid MenuAddDTO add) {
        return service.add(add);
    }

    @GetMapping(path = "query/{id}")
    @Operation(summary = "查看接口", description = "单条查看")
    @Parameter(name = DataBaseConstant.P_KEY, description = "权限标识", required = true)
    @ApiOperationSupport(order = 3, author = "XinLau")
    public CommonResponse<MenuInfoVO> queryById(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id) {
        return service.queryById(id);
    }

    @PutMapping(path = "update")
    @Operation(summary = "更新接口", description = "单条更新")
    @ApiOperationSupport(order = 4, author = "XinLau")
    public BaseResponse updateById(@RequestBody @Valid MenuUpdateDTO update) {
        return service.updateById(update);
    }

    @DeleteMapping(value = "delete")
    @Operation(summary = "删除接口", description = "多条删除")
    @Parameter(name = "idList", description = "权限标识", required = true)
    @ApiOperationSupport(order = 5, author = "XinLau")
    public BaseResponse deleteByIdList(@RequestParam(name = "idList", required = true) List<Long> idList) {
        return service.deleteByIdList(idList);
    }

}
