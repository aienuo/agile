package com.imis.agile.module.api.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.imis.agile.constant.base.BaseController;
import com.imis.agile.constant.base.BaseResponse;
import com.imis.agile.module.api.bus.CommonBus;
import com.imis.agile.module.api.model.vo.FileVO;
import com.imis.agile.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * <p>
 * CommonController<br>
 * 常用接口相关 前端控制器
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月26日 11:11
 */
@Slf4j
@Controller
@RequestMapping(path = "/sys/common")
@Tag(name = "常用接口")
@ApiSort(2)
public class CommonController extends BaseController<CommonBus> {

    @ResponseBody
    @PostMapping(path = "/upload")
    @Operation(summary = "文件上传接口", description = "使用Spring文件上传")
    @ApiOperationSupport(order = 1, author = "XinLau")
    public CommonResponse<List<FileVO>> doFileUpload(MultipartHttpServletRequest multipartHttpServletRequest) {
        return service.doFileUpload(multipartHttpServletRequest);
    }

    @GetMapping(path = "/download")
    @Operation(summary = "文件下载接口", description = "跨服务器文件下载")
    @Parameter(name = "fileId", description = "文件编号", required = true, in = ParameterIn.PATH)
    @ApiOperationSupport(order = 2, author = "XinLau")
    public BaseResponse doFileDownload(@RequestParam(name = "fileId", required = true) Long fileId) {
        return service.doFileDownload(fileId);
    }

}
