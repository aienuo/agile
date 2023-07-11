package com.imis.agile.module.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.constant.base.BaseController;
import com.imis.agile.constant.base.BaseResponse;
import com.imis.agile.module.system.bus.UserBus;
import com.imis.agile.module.system.model.dto.PagingQueryUserDTO;
import com.imis.agile.module.system.model.dto.ResetPasswordDTO;
import com.imis.agile.module.system.model.dto.UserAddDTO;
import com.imis.agile.module.system.model.dto.UserUpdateDTO;
import com.imis.agile.module.system.model.vo.UserInfoVO;
import com.imis.agile.module.system.model.vo.UserPageVO;
import com.imis.agile.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * UserController<br>
 * 用户信息模块
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月10日 09:14
 */
@Slf4j
@RestController
@RequestMapping(path = "/sys/user/")
@Tag(name = "用户管理")
@ApiSort(2)
public class UserController extends BaseController<UserBus> {


    @GetMapping(path = "page")
    @Operation(summary = "分页查询", description = "分页查询")
    @ApiOperationSupport(order = 1, author = "XinLau")
    public CommonResponse<Page<UserPageVO>> pagingQueryListByParameter(PagingQueryUserDTO pagingQuery) {
        return service.pagingQueryListByParameter(pagingQuery);
    }

    @PostMapping(path = "add")
    @Operation(summary = "添加接口", description = "单条添加")
    @ApiOperationSupport(order = 2, author = "XinLau")
    public BaseResponse add(@RequestBody @Valid UserAddDTO add) {
        return service.add(add);
    }

    @PutMapping(path = "freeze")
    @Operation(summary = "冻结接口", description = "多条冻结")
    @Parameter(name = "idList", description = "用户标识", required = true)
    @ApiOperationSupport(order = 3, author = "XinLau")
    public BaseResponse freezeUserByIdList(@RequestBody List<Long> idList) {
        return service.freezeUserByIdList(idList);
    }

    @PutMapping(value = "unFreeze")
    @Operation(summary = "解冻接口", description = "多条解冻")
    @Parameter(name = "idList", description = "用户标识", required = true)
    @ApiOperationSupport(order = 4, author = "XinLau")
    public BaseResponse unFreezeUserByIdList(@RequestBody List<Long> idList) {
        return service.unFreezeUserByIdList(idList);
    }

    @GetMapping(path = "query/{id}")
    @Operation(summary = "查看接口", description = "单条查看")
    @Parameter(name = DataBaseConstant.P_KEY, description = "用户标识", required = true)
    @ApiOperationSupport(order = 5, author = "XinLau")
    public CommonResponse<UserInfoVO> queryById(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id) {
        return service.queryById(id);
    }

    @PutMapping(path = "update")
    @Operation(summary = "更新接口", description = "单条更新")
    @ApiOperationSupport(order = 6, author = "XinLau")
    public BaseResponse updateById(@RequestBody @Valid UserUpdateDTO update) {
        return service.updateById(update);
    }

    @PutMapping(path = "reset")
    @Operation(summary = "重置密码", description = "单条重置")
    @ApiOperationSupport(order = 7, author = "XinLau")
    public BaseResponse resetPassword(@RequestBody @Valid ResetPasswordDTO resetPassword) {
        return service.resetPassword(resetPassword);
    }

    @DeleteMapping(value = "remove")
    @Operation(summary = "删除接口", description = "多条逻辑删除")
    @Parameter(name = "idList", description = "用户标识", required = true)
    @ApiOperationSupport(order = 8, author = "XinLau")
    public BaseResponse removeByIdList(@RequestParam(name = "idList", required = true) List<Long> idList) {
        return service.removeByIdList(idList);
    }

    @GetMapping(path = "export/template")
    @Operation(summary = "导出模版", description = "导出导入Excel模版")
    @ApiOperationSupport(order = 9, author = "XinLau")
    public void getImportTemplate() {
        service.getImportTemplate();
    }

    @PostMapping(path = "import")
    @Operation(summary = "导入信息", description = "导入信息")
    @ApiOperationSupport(order = 10, author = "XinLau")
    public void export(MultipartFile file, Boolean update) {
        service.export(file, update);
    }

}
