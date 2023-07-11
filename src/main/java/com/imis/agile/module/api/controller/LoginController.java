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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "登录模块")
@ApiSort(1)
public class LoginController extends BaseController<LoginBus> {

    @PostMapping(path = "/login")
    @Operation(summary = "用户登录接口", description = "登录")
    @ApiOperationSupport(order = 1, author = "XinLau")
    public CommonResponse<LoginVO> login(@RequestBody @Valid LoginDTO login) {
        return service.login(login);
    }


    @GetMapping(path = "/info/{username}")
    @Operation(summary = "基础信息", description = "基础信息")
    @Parameter(name = "username", description = "用户标识", required = true, in = ParameterIn.PATH)
    @ApiOperationSupport(order = 2, author = "XinLau")
    public CommonResponse<QueryVO> queryByUserName(@PathVariable(name = "username", required = true) String username) {
        return service.queryByUserName(username);
    }

    @PutMapping(path = "/update")
    @Operation(summary = "更新基本信息", description = "更新基本信息")
    @ApiOperationSupport(order = 3, author = "XinLau")
    public BaseResponse update(@RequestBody @Valid UserUpdateDTO update) {
        return service.update(update);
    }

    @PutMapping(path = "/password")
    @Operation(summary = "修改密码接口", description = "用户自助修改密码")
    @ApiOperationSupport(order = 4, author = "XinLau")
    public BaseResponse password(@RequestBody @Valid PasswordUpdateDTO update) {
        return service.password(update);
    }

}
