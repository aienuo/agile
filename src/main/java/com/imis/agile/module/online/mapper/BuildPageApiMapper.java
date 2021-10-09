package com.imis.agile.module.online.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * BuildPageApiMapper<br>
 * 构建页面相关 Mapper 接口
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月07日 15:00
 */
public interface BuildPageApiMapper {

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

    /**
     * 执行数据库模式定义语言（DDL）<br>
     * 关键字：CREATE, ALTER, DROP, RENAME, TRUNCATE
     *
     * @param structureQueryLanguage - DataDefinitionLanguage 数据库模式定义语言（DDL）
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 10:57
     */
    void executeDataDefinitionLanguage(@Param("ddl") final String structureQueryLanguage);

    /**
     * 统计索引数量
     *
     * @param structureQueryLanguage - 统计索引数量SQL
     * @return Integer - 索引数量
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:18
     */
    Long countTheNumberOfIndexes(@Param("sql") final String structureQueryLanguage);

    /**
     * 执行 INSERT SQL语句
     *
     * @param structureQueryLanguage - SQL语句
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    void executeInsertStructuredQueryLanguage(@Param("sql") final Map<String, Object> structureQueryLanguage);

    /**
     * 执行 DELETE SQL语句
     *
     * @param structureQueryLanguage - SQL语句
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    void executeDeleteStructuredQueryLanguage(@Param("sql") final String structureQueryLanguage);

    /**
     * 查询指定数据库表的指定数据所有字段内容
     *
     * @param tableName - 数据库表表名
     * @param dataId    - 数据库表数据编号
     * @return Map<String, Object> - 指定数据库表的指定数据所有字段内容
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    Map<String, Object> queryTableDataByTableNameAndDataId(@Param("tableName") final String tableName, @Param("dataId") final Long dataId);

    /**
     * 查询子节点数量
     *
     * @param tableName             - 数据库表表名
     * @param treeParentIdFieldName - 树形表单父 ID 字段
     * @param parentId              - 数据 父 ID
     * @return Long - 子节点数量
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    Long queryNumberOfChildNodes(@Param("tableName") final String tableName, @Param("treeParentIdFieldName") final String treeParentIdFieldName, @Param("parentId") final String parentId);

    /**
     * 执行 UPDATE SQL语句
     *
     * @param structureQueryLanguage - SQL语句
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    void executeUpdateStructuredQueryLanguage(@Param("sql") final Map<String, Object> structureQueryLanguage);

    /**
     * 执行 SELECT SQL语句
     *
     * @param structureQueryLanguage - SQL语句
     * @return Map<String, Object> - 数据库表数据
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    Map<String, Object> executeQueryStructuredQueryLanguage(@Param("sql") final String structureQueryLanguage);

    /**
     * 执行 SELECT SQL语句
     *
     * @param structureQueryLanguage - SQL语句
     * @return List<Map < String, Object>> - 数据库表数据
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    List<Map<String, Object>> executeQueryTableDataListByStructuredQueryLanguage(@Param("sql") final String structureQueryLanguage);

    /**
     * 执行分页 SELECT SQL语句
     *
     * @param pageQuery              - 分页对象
     * @param structureQueryLanguage - SQL语句
     * @return List<Map < String, Object>> - 数据库表数据
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    Page<Map<String, Object>> executePagingQueryTableDataListByStructuredQueryLanguage(@Param("pg") Page<Map<String, Object>> pageQuery, @Param("sql") final String structureQueryLanguage);

}
