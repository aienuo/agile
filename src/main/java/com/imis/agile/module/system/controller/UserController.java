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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@Api(tags = {"用户管理"})
@ApiSort(2)
public class UserController extends BaseController<UserBus> {


    @GetMapping(path = "page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiOperationSupport(order = 1, author = "XinLau")
    public CommonResponse<Page<UserPageVO>> pagingQueryListByParameter(PagingQueryUserDTO pagingQuery) {
        return service.pagingQueryListByParameter(pagingQuery);
    }

    @PostMapping(path = "add")
    @ApiOperation(value = "添加接口", notes = "单条添加")
    @ApiOperationSupport(order = 2, author = "XinLau")
    public BaseResponse add(@RequestBody @Valid UserAddDTO add) {
        return service.add(add);
    }

    @PutMapping(path = "freeze")
    @ApiOperation(value = "冻结接口", notes = "多条冻结")
    @ApiImplicitParam(name = "idList", value = "用户标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 3, author = "XinLau")
    public BaseResponse freezeUserByIdList(@RequestBody List<Long> idList) {
        return service.freezeUserByIdList(idList);
    }

    @PutMapping(value = "unFreeze")
    @ApiOperation(value = "解冻接口", notes = "多条解冻")
    @ApiImplicitParam(name = "idList", value = "用户标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 4, author = "XinLau")
    public BaseResponse unFreezeUserByIdList(@RequestBody List<Long> idList) {
        return service.unFreezeUserByIdList(idList);
    }

    @GetMapping(path = "query/{id}")
    @ApiOperation(value = "查看接口", notes = "单条查看")
    @ApiImplicitParam(name = DataBaseConstant.P_KEY, value = "用户标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 5, author = "XinLau")
    public CommonResponse<UserInfoVO> queryById(@PathVariable(name = DataBaseConstant.P_KEY, required = true) Long id) {
        return service.queryById(id);
    }

    @PutMapping(path = "update")
    @ApiOperation(value = "更新接口", notes = "单条更新")
    @ApiOperationSupport(order = 6, author = "XinLau")
    public BaseResponse updateById(@RequestBody @Valid UserUpdateDTO update) {
        return service.updateById(update);
    }

    @PutMapping(path = "reset")
    @ApiOperation(value = "重置密码", notes = "单条重置")
    @ApiOperationSupport(order = 7, author = "XinLau")
    public BaseResponse resetPassword(@RequestBody @Valid ResetPasswordDTO resetPassword) {
        return service.resetPassword(resetPassword);
    }

    @DeleteMapping(value = "remove")
    @ApiOperation(value = "删除接口", notes = "多条逻辑删除")
    @ApiImplicitParam(name = "idList", value = "用户标识", dataType = "Long", dataTypeClass = Long.class, required = true)
    @ApiOperationSupport(order = 8, author = "XinLau")
    public BaseResponse removeByIdList(@RequestParam(name = "idList", required = true) List<Long> idList) {
        return service.removeByIdList(idList);
    }

}
