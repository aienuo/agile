package com.imis.agile.module.online.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.agile.constant.DataBaseConstant;
import com.imis.agile.module.online.mapper.BuildPageApiMapper;
import com.imis.agile.module.online.service.BuildPageApiService;
import com.imis.agile.util.AgileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * BuildPageApiServiceImpl<br>
 * 构建页面相关 服务实现类
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年09月07日 14:55
 */
@Slf4j
@Service
@DS(DataBaseConstant.DATA_SOURCE_RECORD)
public class BuildPageApiServiceImpl implements BuildPageApiService {

    /**
     * 构建页面相关 Mapper 接口
     */
    private BuildPageApiMapper mapper;

    @Autowired
    public void setMapper(BuildPageApiMapper mapper) {
        this.mapper = mapper;
    }

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
        return this.mapper.countMainFieldByParameter(tableName, tableField, tableField.size());
    }

    /**
     * 执行数据库模式定义语言（DDL）<br>
     * 关键字：CREATE, ALTER, DROP, RENAME, TRUNCATE
     *
     * @param structureQueryLanguage - DataDefinitionLanguage 数据库模式定义语言（DDL）
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 10:57
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_RECORD)
    public Boolean executeDataDefinitionLanguage(final String structureQueryLanguage) {
        try {
            this.mapper.executeDataDefinitionLanguage(structureQueryLanguage);
            return Boolean.TRUE;
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return Boolean.FALSE;
        }
    }

    /**
     * 执行 COUNT SQL语句
     *
     * @param structureQueryLanguage - 统计索引数量SQL
     * @return Long -
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_RECORD)
    public Long executeCountStructuredQueryLanguage(final String structureQueryLanguage) {
        return this.mapper.countTheNumberOfIndexes(structureQueryLanguage);
    }

    /**
     * 判断索引是否存在
     *
     * @param structureQueryLanguage - 统计索引数量SQL
     * @return Boolean -
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_RECORD)
    public Boolean isExistIndex(final String structureQueryLanguage) {
        if (AgileUtil.isEmpty(structureQueryLanguage)) {
            return Boolean.TRUE;
        }
        Long count = this.executeCountStructuredQueryLanguage(structureQueryLanguage);
        if (count != null && count > 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 执行 INSERT SQL语句
     *
     * @param params - 统计索引数量SQL
     * @return Boolean -
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_RECORD)
    public Boolean executeInsertStructuredQueryLanguage(final Map<String, Object> params) {
        try {
            this.mapper.executeInsertStructuredQueryLanguage(params);
            return Boolean.TRUE;
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return Boolean.FALSE;
        }
    }

    /**
     * 执行 DELETE SQL语句
     *
     * @param structureQueryLanguage - SQL语句
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_RECORD)
    public Boolean executeDeleteStructuredQueryLanguage(final String structureQueryLanguage) {
        try {
            this.mapper.executeDeleteStructuredQueryLanguage(structureQueryLanguage);
            return Boolean.TRUE;
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return Boolean.FALSE;
        }
    }

    /**
     * 执行 SELECT SQL语句
     *
     * @param structureQueryLanguage - SQL语句
     * @return Map<String, Object> - 数据库表数据
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_RECORD)
    public Map<String, Object> executeQueryStructuredQueryLanguage(final String structureQueryLanguage) {
        return this.mapper.executeQueryStructuredQueryLanguage(structureQueryLanguage);
    }

    /**
     * 执行 UPDATE SQL语句
     *
     * @param structureQueryLanguage - SQL语句
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    @Override
    public Boolean executeUpdateStructuredQueryLanguage(final String structureQueryLanguage) {
        try {
            Map<String, Object> params = new HashMap<>(1);
            params.put("execute_structured_query_language_string" , structureQueryLanguage);
            this.executeUpdateStructuredQueryLanguage(params);
            return Boolean.TRUE;
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return Boolean.FALSE;
        }
    }

    /**
     * 执行 UPDATE SQL语句
     *
     * @param params - SQL语句
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_RECORD)
    public Boolean executeUpdateStructuredQueryLanguage(final Map<String, Object> params) {
        try {
            this.mapper.executeUpdateStructuredQueryLanguage(params);
            return Boolean.TRUE;
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return Boolean.FALSE;
        }
    }

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
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_RECORD)
    public Map<String, Object> queryTableDataByTableNameAndDataId(final String tableName, final Long dataId) {
        return this.mapper.queryTableDataByTableNameAndDataId(tableName, dataId);
    }

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
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_RECORD)
    public Long queryNumberOfChildNodes(final String tableName, final String treeParentIdFieldName, final String parentId) {
        return this.mapper.queryNumberOfChildNodes(tableName, treeParentIdFieldName, parentId);
    }

    /**
     * 执行 SELECT SQL语句
     *
     * @param structureQueryLanguage - SQL语句
     * @return List<Map < String, Object>> - 数据库表数据
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/10/5 11:11
     */
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_RECORD)
    public List<Map<String, Object>> executeQueryTableDataListByStructuredQueryLanguage(final String structureQueryLanguage) {
        return this.mapper.executeQueryTableDataListByStructuredQueryLanguage(structureQueryLanguage);
    }

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
    @Override
    @DS(DataBaseConstant.DATA_SOURCE_RECORD)
    public Page<Map<String, Object>> executePagingQueryTableDataListByStructuredQueryLanguage(final Page<Map<String, Object>> pageQuery, final String structureQueryLanguage) {
        return this.mapper.executePagingQueryTableDataListByStructuredQueryLanguage(pageQuery, structureQueryLanguage);
    }

}
