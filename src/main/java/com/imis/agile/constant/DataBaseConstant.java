package com.imis.agile.constant;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

/**
 * <p>
 * DataBaseConstant<br>
 * 数据库上下文常量
 * </p>
 *
 * @author XinLau
 * @since 2020-03-12
 */
public interface DataBaseConstant extends StringPool {
    /**
     * 数据源名称 - master
     */
    String DATA_SOURCE_MASTER = "master";
    /**
     * 数据源名称 - slave
     */
    String DATA_SOURCE_RECORD = "slave";

    //********* 系统建表标准字段 ****************************************

    /**
     * 主键字段
     */
    String P_KEY_FIELD = "ID";
    String P_KEY = "id";
    /**
     * 创建人字段
     */
    String CREATE_BY_FIELD = "CREATE_BY";
    String CREATE_BY = "createBy";
    /**
     * 创建时间字段
     */
    String CREATE_TIME_FIELD = "CREATE_TIME";
    String CREATE_TIME = "createTime";
    /**
     * 修改人字段
     */
    String UPDATE_BY_FIELD = "UPDATE_BY";
    String UPDATE_BY = "updateBy";
    /**
     * 修改时间字段
     */
    String UPDATE_TIME_FIELD = "UPDATE_TIME";
    String UPDATE_TIME = "updateTime";

    //********* 数据库SQL标准字段 ****************************************

    String SQL_CREATE = "CREATE" + SPACE;

    String SQL_VALUES = "VALUES" + SPACE;
    String SQL_SET = "SET" + SPACE;
    String SQL_ADD = "ADD" + SPACE;
    String SQL_RENAME = "RENAME" + SPACE;
    String SQL_CHANGE = "CHANGE" + SPACE;
    String SQL_MODIFY = "MODIFY" + SPACE;
    String SQL_ALTER = "ALTER" + SPACE;
    String SQL_DROP = "DROP" + SPACE;
    String SQL_WHERE = "WHERE" + SPACE;
    String SQL_AND = "AND" + SPACE;
    String SQL_IN = "IN" + SPACE;
    String SQL_NULL = "NULL" + SPACE;
    String SQL_NOT_NULL = "NOT NULL" + SPACE;
    String SQL_LIKE = "LIKE" + SPACE;
    String SQL_ON = "ON" + SPACE;
    String SQL_ORDER = "ORDER BY" + SPACE;
    String SQL_ASC = "ASC" + SPACE;
    String SQL_DESC = "DESC" + SPACE;
    String SQL_INDEX = "INDEX" + SPACE;
    String SQL_COLUMN = "COLUMN" + SPACE;
    String SQL_TABLE = "TABLE" + SPACE;
    String SQL_IF = "IF" + SPACE;
    String SQL_EXISTS = "EXISTS" + SPACE;
    String SQL_COMMENT = "COMMENT" + SPACE;
    String SQL_DEFAULT = "DEFAULT" + SPACE;
    String SQL_IS = "IS" + SPACE;
    String SQL_TO = "TO" + SPACE;
    String SQL_TYPE = "TYPE" + SPACE;

}
