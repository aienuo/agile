package com.imis.agile.module.online.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imis.agile.module.online.model.entity.TableFields;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 * 在线开发 - 数据库表字段信息 Mapper 接口
 * </p>
 *
 * @author XinLau
 * @since 2020-10-03
 */
public interface TableFieldsMapper extends BaseMapper<TableFields> {

    /**
     * 判断外键主表名、外键主键字段 是否存在
     *
     * @param tableName  - 外键主表名
     * @param tableField - 外键主键字段
     * @param number     - 外键主键字段数量
     * @return Integer - 0-FALSE，1-TURE
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/3/9 9:47
     */
    Integer countMainFieldByParameter(@Param("tableName") final String tableName, @Param("tableField") final Set<String> tableField, @Param("number") final Integer number);

}