package com.imis.agile.util;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.imis.agile.constant.enums.ArgumentResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * 文件处理 工具类
 *
 * @author XinLau
 * @since 2014-5-8
 */
@Slf4j
public class FileUtil {

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
    public static void doFileUploadForLocal(final String filePath, final String fileName, final MultipartFile multipartFile) {
        try {
            byte[] multipartFileBytes = multipartFile.getBytes();
            String savePath = filePath + File.separator + fileName;
            writeBytes(multipartFileBytes, filePath, savePath);
        } catch (IOException e) {
            ArgumentResponseEnum.FILE_ADD_ERR.assertFail(e);
        }
    }

    /**
     * 写数据到文件中
     *
     * @param filedByteData - 文件数据
     * @param savePath      - 保存路径
     */
    public static void writeBytes(final byte[] filedByteData, final String filePath, final String savePath) {
        try {
            // 文件保存文件夹
            File fileSaveFolder = new File(filePath);
            if (!fileSaveFolder.exists()) {
                // 创建文件根目录
                boolean mkdirs = fileSaveFolder.mkdirs();
                ArgumentResponseEnum.FILE_ADD_ERR_PATH_NO_EXIST.assertIsTrue(mkdirs);
            }
            FileCopyUtils.copy(filedByteData, new File(savePath));
        } catch (IOException e) {
            ArgumentResponseEnum.FILE_ADD_ERR_FILE_SAVE_FAILED.assertFail(e);
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
    public static void doFileDownloadByFileUrl(final String filePath, final String fileName, final HttpServletResponse httpServletResponse) {
        // 1.获取并设置 HttpServletResponse 强制下载不打开
        httpServletResponse.setContentType("application/force-download");
        httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
        httpServletResponse.setCharacterEncoding(StringPool.UTF_8);
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
     * 删除文件
     *
     * @param filePath - 文件本地存放地址
     * @return Boolean - 是否删除成功
     */
    public static Boolean deleteFileForLocal(final String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            flag = file.delete();
        }
        return flag;
    }

}
