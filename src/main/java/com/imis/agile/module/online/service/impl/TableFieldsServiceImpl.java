package com.imis.agile.module.online.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.module.online.mapper.TableFieldsMapper;
import com.imis.agile.module.online.model.entity.TableFields;
import com.imis.agile.module.online.service.ITableFieldsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 在线开发 - 数据库表字段信息 服务实现类
 * </p>
 *
 * @author XinLau
 * @since 2020-10-03
 */
@Slf4j
@Service
@DS(DataBaseConstant.DATA_SOURCE_MASTER)
public class TableFieldsServiceImpl extends ServiceImpl<TableFieldsMapper, TableFields> implements ITableFieldsService {

    /**
     * 判断外键主表名、外键主键字段 是否存在
     *
     * @param tableName  - 外键主表名
     * @param tableField - 外键主键字段
     * @return Integer - 0-FALSE，1-TURE
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    @Override
    public Integer countMainFieldByParameter(final String tableName, final Set<String> tableField) {
        return this.baseMapper.countMainFieldByParameter(tableName, tableField, tableField.size());
    }

}