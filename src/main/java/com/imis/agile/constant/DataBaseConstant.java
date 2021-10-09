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

}
