package com.imis.agile.module.api.bus;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.imis.agile.constant.CommonConstant;
import com.imis.agile.constant.base.BaseBus;
import com.imis.agile.constant.base.BaseResponse;
import com.imis.agile.constant.enums.ArgumentResponseEnum;
import com.imis.agile.module.api.model.vo.FileVO;
import com.imis.agile.module.system.model.converter.FileConverter;
import com.imis.agile.module.system.service.IFileService;
import com.imis.agile.response.CommonResponse;
import com.imis.agile.util.AgileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * CommonBus<br>
 * 公共 业务处理类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月26日 11:18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonBus extends BaseBus {

    /**
     * 文件存放 服务类
     */
    private final IFileService fileService;

    /**
     * 文件本地存储路径（跟jar包同级目录，自动拼接 “./”）
     */
    @Value(value = "${imis-boot.path.upload}")
    private String uploadPath;

    /**
     * 文件存储类型（本地：local）
     */
    @Value(value = "${imis-boot.uploadType}")
    private String uploadType;

    /**
     * 文件字节本地保存
     *
     * @param filePath      - 文件路径
     * @param fileName      - 文件名称
     * @param multipartFile - 文件
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/7/27 13:54
     */
    private void doFileUploadForLocal(final String filePath, final String fileName, final MultipartFile multipartFile) {
        try {
            byte[] multipartFileBytes = multipartFile.getBytes();
            File file = new File(filePath);
            if (!file.exists()) {
                // 创建文件根目录
                boolean mkdirs = file.mkdirs();
                ArgumentResponseEnum.FILE_ADD_ERR_PATH_NO_EXIST.assertIsTrue(mkdirs);
            }
            String savePath = file.getPath() + File.separator + fileName;
            File saveFile = new File(savePath);
            FileCopyUtils.copy(multipartFileBytes, saveFile);
        } catch (IOException e) {
            ArgumentResponseEnum.FILE_ADD_ERR.assertFail(e);
        }
    }

    /**
     * 跨服务器单文件下载
     *
     * @param filePath - 文件路径
     * @param fileName - 文件名称
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/7/27 13:54
     */
    private void doFileDownloadByFileUrl(final String filePath, final String fileName) {
        // 1.获取并设置 HttpServletResponse 强制下载不打开
        HttpServletResponse httpServletResponse = getHttpServletResponse();
        httpServletResponse.setContentType("application/force-download");
        httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
        try {
            // 2.根据文件地址创建URL
            URL url = new URL(filePath);
            // 3.获取此路径的连接
            URLConnection urlConnection = url.openConnection();
            // 4.获取文件大小，并设置给Response
            Long fileLength = urlConnection.getContentLengthLong();
            httpServletResponse.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileLength));
            // 5.设置缓冲区大小
            byte[] buffer = new byte[1024];
            // 6.初始化缓冲输入流；  获取Response输出流；   初始化缓冲输出流
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
                 OutputStream httpServletResponseOutputStream = httpServletResponse.getOutputStream();
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpServletResponseOutputStream)
            ) {
                int i = bufferedInputStream.read(buffer);
                //  7.每次读取缓存大小的流，写到输出流
                while (i != -1) {
                    bufferedOutputStream.write(buffer, 0, i);
                    i = bufferedInputStream.read(buffer);
                }
                //  8.将所有的读取的流返回给客户端
                httpServletResponse.flushBuffer();
            }
        } catch (IOException e) {
            ArgumentResponseEnum.FILE_DOWNLOAD_ERR_2.assertFail(e);
        }
    }

    /**
     * 多文件上传
     *
     * @param multipartHttpServletRequest - MultipartHttpServletRequest
     * @return CommonResponse<List < FileVO>>
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/10 15:32
     */
    public CommonResponse<List<FileVO>> doFileUpload(final MultipartHttpServletRequest multipartHttpServletRequest) {
        // 1.编码格式
        String characterEncoding = multipartHttpServletRequest.getCharacterEncoding();
        log.debug("characterEncoding_" + characterEncoding);
        // 2.requestParameterMap 请求参数 - 文字
        Map<String, String[]> requestParameterMap = multipartHttpServletRequest.getParameterMap();
        for (Map.Entry<String, String[]> parameterMap : requestParameterMap.entrySet()) {
            // 参数值Key
            String parameterKey = parameterMap.getKey();
            // 参数值数组
            String[] valueArray = parameterMap.getValue();
            log.debug("parameterKey_ {} : {}", parameterKey, valueArray);
        }
        // 文件保存对象
        List<com.imis.agile.module.system.model.entity.File> fileList = new ArrayList<>();
        // 3.requestMultiFileMap 请求参数 - 文件
        MultiValueMap<String, MultipartFile> requestMultiFileMap = multipartHttpServletRequest.getMultiFileMap();
        // IP + Port
        String localUrl = getHttpServletRequest().getRequestURL().toString().replaceAll(getHttpServletRequest().getRequestURI(), StringPool.SLASH);
        for (Map.Entry<String, List<MultipartFile>> multiFileMap : requestMultiFileMap.entrySet()) {
            // 参数Key
            String multiFileKey = multiFileMap.getKey();
            log.debug("multiFileKey_ {}", multiFileKey);
            // 参数Key对应的文件集合
            List<MultipartFile> multipartFileList = multiFileMap.getValue();
            for (MultipartFile multipartFile : multipartFileList) {
                //  文件非空
                if (!multipartFile.isEmpty()) {
                    //  参数Key
                    String name = multipartFile.getName();
                    log.debug(name);
                    //  文件全名称
                    String originalFilename = multipartFile.getOriginalFilename();
                    //  4.文件对象
                    com.imis.agile.module.system.model.entity.File file = new com.imis.agile.module.system.model.entity.File();
                    file.setFileType(multipartFile.getContentType());
                    file.setFileSize(multipartFile.getSize());
                    file.setDelFlag(CommonConstant.DEL_FLAG_0);
                    //  文件存放名 UUID 防止重复文件名
                    String newName = IdWorker.get32UUID();
                    if (AgileUtil.isNotEmpty(originalFilename)) {
                        //  文件后缀名（带 “.”）
                        String ext = originalFilename.substring(originalFilename.lastIndexOf(StringPool.DOT));
                        newName = newName + ext;
                    }
                    file.setFileName(newName);
                    file.setRealName(originalFilename);
                    file.setDescription(StringPool.EMPTY);
                    if (CommonConstant.UPLOAD_TYPE_LOCAL.equals(uploadType)) {
                        // 文件地址
                        file.setFileUrl(localUrl + uploadPath + StringPool.SLASH + newName);
                        // 文件本地保存（uploadPath跟jar包同级目录，自动拼接 “./”）
                        String filePath = StringPool.DOT + StringPool.SLASH + uploadPath;
                        this.doFileUploadForLocal(filePath, newName, multipartFile);
                    } else {
                        // TODO：自己整合其他
                        ArgumentResponseEnum.FILE_ADD_ERR.assertFailWithMessage("自己整合其他非 本地保存文件 的方式");
                    }
                    fileList.add(file);
                }
            }
        }
        // 6.将文件记录保存到数据库
        boolean saveBatch = this.fileService.saveBatch(fileList);
        ArgumentResponseEnum.FILE_ADD_ERR.assertIsTrue(saveBatch);
        // 7.保存成功，回显给前端
        return new CommonResponse<>(FileConverter.INSTANCE.getReturnValue(fileList));
    }

    /**
     * 单文件下载
     *
     * @param fileId - 文件编号
     * @return Result
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/10 15:32
     */
    public BaseResponse doFileDownload(final Long fileId) {
        // 1.fileId 合法性判断
        ArgumentResponseEnum.FILE_DOWNLOAD_ERR_1.assertNotNull(fileId);
        // 2.获取文件信息
        com.imis.agile.module.system.model.entity.File sysFile = this.fileService.getById(fileId);
        ArgumentResponseEnum.FILE_DOWNLOAD_ERR_1.assertNotNull(sysFile);
        // 3.文件下载
        this.doFileDownloadByFileUrl(sysFile.getFileUrl(), sysFile.getFileName());
        return new BaseResponse();
    }

}
