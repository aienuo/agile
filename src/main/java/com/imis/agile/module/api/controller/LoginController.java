package com.imis.agile.module.api.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.imis.agile.constant.base.BaseController;
import com.imis.agile.constant.base.BaseResponse;
import com.imis.agile.module.api.bus.LoginBus;
import com.imis.agile.module.api.model.dto.LoginDTO;
import com.imis.agile.module.api.model.dto.PasswordUpdateDTO;
import com.imis.agile.module.api.model.dto.UserUpdateDTO;
import com.imis.agile.module.api.model.vo.LoginVO;
import com.imis.agile.module.api.model.vo.QueryVO;
import com.imis.agile.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * LoginController<br>
 * 用户登录模块
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年04月15日 11:54
 */
@RestController
@Api(tags = {"登录模块"})
@ApiSort(1)
public class LoginController extends BaseController<LoginBus> {

    @PostMapping(path = "/login")
    @ApiOperation(value = "用户登录接口", notes = "登录")
    @ApiOperationSupport(order = 1, author = "XinLau")
    public CommonResponse<LoginVO> login(@RequestBody @Valid LoginDTO login) {
        return service.login(login);
    }


    @GetMapping(path = "/info/{username}")
    @ApiOperation(value = "基础信息", notes = "基础信息")
    @ApiImplicitParam(name = "username", value = "用户标识", dataType = "String", dataTypeClass = String.class, required = true)
    @ApiOperationSupport(order = 2, author = "XinLau")
    public CommonResponse<QueryVO> queryByUserName(@PathVariable(name = "username", required = true) String username) {
        return service.queryByUserName(username);
    }

    @PutMapping(path = "/update")
    @ApiOperation(value = "更新基本信息", notes = "更新基本信息")
    @ApiOperationSupport(order = 3, author = "XinLau")
    public BaseResponse update(@RequestBody @Valid UserUpdateDTO update) {
        return service.update(update);
    }

    @PutMapping(path = "/password")
    @ApiOperation(value = "修改密码接口", notes = "用户自助修改密码")
    @ApiOperationSupport(order = 4, author = "XinLau")
    public BaseResponse password(@RequestBody @Valid PasswordUpdateDTO update) {
        return service.password(update);
    }

}
