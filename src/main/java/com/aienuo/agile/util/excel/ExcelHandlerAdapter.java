package com.aienuo.agile.util.excel;

/**
 * Excel数据格式处理适配器
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月26日 11:11
 */
public interface ExcelHandlerAdapter {

    /**
     * 格式化
     *
     * @param value - 单元格数据值
     * @param args  - excel注解args参数组
     * @return Object - 处理后的值
     */
    Object format(Object value, String[] args);

}
