package com.aienuo.agile.module.system.service.impl;

import com.aienuo.agile.constant.DataBaseConstant;
import com.aienuo.agile.module.system.mapper.FileMapper;
import com.aienuo.agile.module.system.model.entity.File;
import com.aienuo.agile.module.system.service.IFileService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件存放 服务实现类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-27
 */
@Service
@Slf4j
@DS(DataBaseConstant.DATA_SOURCE_MASTER)
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

}
