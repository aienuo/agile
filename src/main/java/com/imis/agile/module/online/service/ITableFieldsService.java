package com.imis.agile.module.online.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imis.agile.module.online.model.entity.TableFields;

import java.util.Set;

/**
 * <p>
 * 在线开发 - 数据库表字段信息 服务类
 * </p>
 *
 * @author XinLau
 * @since 2020-10-03
 */
public interface ITableFieldsService extends IService<TableFields> {

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
    Integer countMainFieldByParameter(final String tableName, final Set<String> tableField);

}