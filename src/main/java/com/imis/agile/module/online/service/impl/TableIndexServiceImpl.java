package com.imis.agile.module.online.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.module.online.mapper.TableIndexMapper;
import com.imis.agile.module.online.model.entity.TableIndex;
import com.imis.agile.module.online.service.ITableIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 在线开发 - 数据库表表索引 服务实现类
 * </p>
 *
 * @author XinLau
 * @since 2020-10-03
 */
@Slf4j
@Service
@DS(DataBaseConstant.DATA_SOURCE_MASTER)
public class TableIndexServiceImpl extends ServiceImpl<TableIndexMapper, TableIndex> implements ITableIndexService {

}