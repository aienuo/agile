package com.imis.agile.module.online.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * BuildPageApiService<br>
 * 构建页面相关 服务类（第二数据源）
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月07日 14:54
 */
public interface BuildPageApiService {

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

    /**
     * 执行数据库模式定义语言（DDL）<br>
     * 关键字：CREATE, ALTER, DROP, RENAME, TRUNCATE
     *
     * @param structureQueryLanguage - DataDefinitionLanguage 数据库模式定义语言（DDL） SQL
     * @return Boolean -
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 10:57
     */
    Boolean executeDataDefinitionLanguage(final String structureQueryLanguage);

    /**
     * 执行 COUNT SQL语句
     *
     * @param structureQueryLanguage - 统计索引数量SQL
     * @return Long -
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    Long executeCountStructuredQueryLanguage(final String structureQueryLanguage);

    /**
     * 判断索引是否存在
     *
     * @param structureQueryLanguage - 统计索引数量SQL
     * @return Boolean -
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    Boolean isExistIndex(final String structureQueryLanguage);

    /**
     * 执行 INSERT SQL语句
     *
     * @param params - 统计索引数量SQL
     * @return Boolean -
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    Boolean executeInsertStructuredQueryLanguage(final Map<String, Object> params);

    /**
     * 执行 DELETE SQL语句
     *
     * @param structureQueryLanguage - SQL语句
     * @return Boolean -
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    Boolean executeDeleteStructuredQueryLanguage(final String structureQueryLanguage);

    /**
     * 执行 SELECT SQL语句
     *
     * @param structureQueryLanguage - SQL语句
     * @return Map<String, Object> - 数据库表数据
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    Map<String, Object> executeQueryStructuredQueryLanguage(final String structureQueryLanguage);

    /**
     * 执行 Update SQL语句
     *
     * @param structureQueryLanguage - SQL语句
     * @return Boolean -
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    Boolean executeUpdateStructuredQueryLanguage(final String structureQueryLanguage);

    /**
     * 执行 Update SQL语句
     *
     * @param params - SQL语句
     * @return Boolean -
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    Boolean executeUpdateStructuredQueryLanguage(final Map<String, Object> params);

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
    Map<String, Object> queryTableDataByTableNameAndDataId(final String tableName, final Long dataId);

    /**
     * 查询子节点数量
     *
     * @param tableName             - 数据库表表名
     * @param treeParentIdFieldName - 树形表单父 ID 字段
     * @param parentId              - 数据 父 ID
     * @return Integer - 子节点数量
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    Long queryNumberOfChildNodes(final String tableName, final String treeParentIdFieldName, final String parentId);

    /**
     * 执行 SELECT SQL语句
     *
     * @param structureQueryLanguage - SQL语句
     * @return List<Map < String, Object>> - 数据库表数据
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    List<Map<String, Object>> executeQueryTableDataListByStructuredQueryLanguage(final String structureQueryLanguage);

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
    Page<Map<String, Object>> executePagingQueryTableDataListByStructuredQueryLanguage(final Page<Map<String, Object>> pageQuery, final String structureQueryLanguage);

}
