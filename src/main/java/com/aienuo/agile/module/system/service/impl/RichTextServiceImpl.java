package com.aienuo.agile.module.system.service.impl;

import com.aienuo.agile.constant.DataBaseConstant;
import com.aienuo.agile.module.system.mapper.RichTextMapper;
import com.aienuo.agile.module.system.model.entity.RichText;
import com.aienuo.agile.module.system.service.IRichTextService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 富文本 服务实现类
 * </p>
 *
 * @author XinLau
 * @since 2020-07-24
 */
@Slf4j
@Service
@DS(DataBaseConstant.DATA_SOURCE_MASTER)
public class RichTextServiceImpl extends ServiceImpl<RichTextMapper, RichText> implements IRichTextService {

}
