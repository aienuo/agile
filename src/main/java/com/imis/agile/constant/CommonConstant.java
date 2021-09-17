package com.imis.agile.constant;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

/**
 * <p>
 * CommonConstant<br>
 *
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年07月20日 10:51
 */
public interface CommonConstant {

    /**
     * 自定义Token<br>
     * eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1OTYxNjUyNzIsInVzZXJuYW1lIjoiYWRtaW5fcmljaGFuZyJ9.R8ZaEbHFP95Arjo9WZZGAtFZngfAWlIQ_URJjq-24Vg
     */
    String X_ACCESS_TOKEN = "X-Access-Token";

    /**
     * 自定义Cookie
     */
    String X_COOKIE_NAME = "X.SESSION.ID";

    /**
     * Token缓存时间：3600000毫秒即一小时
     */
    Long EXPIRE_TIME = 60 * 60 * 1000L;

    /**
     * Max-Age属性指定从现在开始 Cookie 存在的秒数 - 一个小时
     */
    Integer MAX_AGE = 60 * 60;

    /**
     * 文件上传类型
     */
    String UPLOAD_TYPE_LOCAL = "local";

    /**
     * 标准日期格式：yyyy-MM-dd
     */
    String NORM_DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 标准日期格式：yyyy年MM月dd日
     */
    String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";
    /**
     * 标准时间格式：HH:mm:ss
     */
    String NORM_TIME_PATTERN = "HH:mm:ss";
    /**
     * 标准时间格式：HH时mm分ss秒
     */
    String CHINESE_TIME_PATTERN = "HH时mm分ss秒";
    /**
     * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
     */
    String NORM_DATETIME_PATTERN = NORM_DATE_PATTERN + StringPool.SPACE + NORM_TIME_PATTERN;
    /**
     * 标准日期格式：yyyy年MM月dd日 HH时mm分ss秒
     */
    String CHINESE_DATE_TIME_PATTERN = CHINESE_DATE_PATTERN + StringPool.SPACE + CHINESE_TIME_PATTERN;

    /**
     * 字典类型（0-String，1-Number）
     */
    Integer DICT_TYPE_0 = 0;
    Integer DICT_TYPE_1 = 1;

    /**
     * 冻结状态(0-正常，1-冻结）
     */
    Integer USER_FREEZE_0 = 0;
    Integer USER_FREEZE_1 = 1;

    /**
     * 删除状态（0-正常，1-已删除）
     */
    Integer DEL_FLAG_0 = 0;
    Integer DEL_FLAG_1 = 1;

}
