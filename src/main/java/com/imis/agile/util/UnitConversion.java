package com.imis.agile.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计量单位换算
 *
 * @author XinLau
 */
public class UnitConversion {

    /**
     * 默认保留两位小数,四舍五入
     *
     * @param value    - 原始数值
     * @param original - 原始单位
     * @param need     - 转换的单位
     * @return BigDecimal - 转换后的值
     */
    public static BigDecimal conversion(BigDecimal value, String original, String need) {
        return conversion(value, original, need, 2);
    }

    /**
     * 默认保留两位小数,四舍五入
     *
     * @param value    - 原始数值
     * @param original - 原始单位
     * @param need     - 转换的单位
     * @return BigDecimal - 转换后的值
     */
    public static BigDecimal conversion(final BigDecimal value, final UnitsEnum original, final UnitsEnum need) {
        return conversion(value, original, need, 2);
    }

    /**
     * 转换主方法，指定小数点位数
     *
     * @param value    - 原始数值
     * @param original - 原始单位
     * @param need     - 转换的单位
     * @param scale    - 小数点位数
     * @return BigDecimal - 转换后的值
     */
    public static BigDecimal conversion(BigDecimal value, String original, String need, final Integer scale) {
        return conversion(value, getUnitEnum(original), getUnitEnum(need), scale);
    }

    /**
     * 转换主方法
     *
     * @param value    - 原始数值
     * @param original - 原始单位
     * @param need     - 转换的单位
     * @param scale    - 小数点位数
     * @return BigDecimal - 数值
     */
    public static BigDecimal conversion(final BigDecimal value, final UnitsEnum original, final UnitsEnum need, final Integer scale) {
        if (original == UnitsEnum.UN_KNOWN || need == UnitsEnum.UN_KNOWN) {
            throw new IllegalArgumentException("存在不支持的计量单位参数");
        }
        if (original.category != need.category) {
            throw new IllegalArgumentException(String.format("转换计量单位不统一 %s 不能转换为 %s ", original.category.name, need.category.name));
        }
        return value == null ? new BigDecimal(0) : value.multiply(need.rate).divide(original.rate, scale, RoundingMode.HALF_UP);
    }

    /**
     * 获取计量单位枚举
     *
     * @param unit - 计量单位名称
     * @return UnitsEnum - 计量单位
     */
    public static UnitsEnum getUnitEnum(String unit) {
        if (AgileUtil.isNotEmpty(unit)) {
            for (UnitsEnum unitEnum : UnitsEnum.values()) {
                for (String possibleName : unitEnum.possibleNames) {
                    if (possibleName.equals(unit.toLowerCase())) {
                        return unitEnum;
                    }
                }
            }
        }
        return UnitsEnum.UN_KNOWN;
    }

    /**
     * 获取计量单位集合
     *
     * @return Map<String, List < Map < String, String>>> - 计量单位集合
     */
    public static Map<String, List<Map<String, String>>> getUnitListMap() {
        Map<String, List<Map<String, String>>> listMap = new HashMap<>(UnitsEnum.CategoryEnum.values().length);
        List<Map<String, String>> lengthList = new ArrayList<>();
        List<Map<String, String>> weightList = new ArrayList<>();
        for (UnitsEnum unit : UnitsEnum.values()) {
            if (UnitsEnum.CategoryEnum.LENGTH.equals(unit.category)) {
                Map<String, String> lengthMap = new HashMap<>(2);
                lengthMap.put("code", unit.getUnits());
                lengthMap.put("name", unit.getDescription());
                lengthList.add(lengthMap);
            }
            if (UnitsEnum.CategoryEnum.WEIGHT.equals(unit.category)) {
                Map<String, String> weightMap = new HashMap<>(2);
                weightMap.put("code", unit.getUnits());
                weightMap.put("name", unit.getDescription());
                weightList.add(weightMap);
            }
        }
        listMap.put(UnitsEnum.CategoryEnum.LENGTH.name, lengthList);
        listMap.put(UnitsEnum.CategoryEnum.WEIGHT.name, weightList);
        return listMap;
    }

    /**
     * 获取计量单位类型
     *
     * @return List<Map < String, String>> - 计量单位类型
     */
    public static List<Map<String, String>> getCategoryList() {
        List<Map<String, String>> categoryList = new ArrayList<>();
        for (UnitsEnum.CategoryEnum category : UnitsEnum.CategoryEnum.values()) {
            Map<String, String> lengthMap = new HashMap<>(2);
            lengthMap.put("code", category.getName());
            lengthMap.put("name", category.getDescription());
            categoryList.add(lengthMap);
        }
        return categoryList;
    }

    /**
     * 计量单位枚举
     */
    @Getter
    @AllArgsConstructor
    public enum UnitsEnum {
        /**
         * 长度计量单位
         */
        LG_KM(CategoryEnum.LENGTH, "km", new String[]{"km", "千米"}, new BigDecimal("0.001"), "千米"),
        LG_M(CategoryEnum.LENGTH, "m", new String[]{"m", "米"}, new BigDecimal("1"), "米"),
        LG_DM(CategoryEnum.LENGTH, "dm", new String[]{"dm", "分米"}, new BigDecimal("10"), "分米"),
        LG_CM(CategoryEnum.LENGTH, "cm", new String[]{"cm", "厘米"}, new BigDecimal("100"), "厘米"),
        LG_MM(CategoryEnum.LENGTH, "mm", new String[]{"mm", "毫米"}, new BigDecimal("1000"), "毫米"),
        LG_UM(CategoryEnum.LENGTH, "um", new String[]{"um", "微米"}, new BigDecimal("1000000"), "微米"),
        LG_NM(CategoryEnum.LENGTH, "nm", new String[]{"nm", "纳米"}, new BigDecimal("1000000000"), "纳米"),
        LG_INCH(CategoryEnum.LENGTH, "inch", new String[]{"in", "inch", "英寸"}, new BigDecimal("39.3700787"), "英寸"),
        LG_FOOT(CategoryEnum.LENGTH, "foot", new String[]{"ft", "foot", "英尺"}, new BigDecimal("3.2808399"), "英尺"),

        /**
         * 重量计量单位
         */
        EG_T(CategoryEnum.WEIGHT, "t", new String[]{"t", "吨"}, new BigDecimal("0.001"), "吨"),
        EG_KG(CategoryEnum.WEIGHT, "kg", new String[]{"kg", "千克"}, new BigDecimal("1"), "千克"),
        EG_G(CategoryEnum.WEIGHT, "g", new String[]{"g", "克"}, new BigDecimal("1000"), "克"),
        EG_MG(CategoryEnum.WEIGHT, "mg", new String[]{"mg", "毫克"}, new BigDecimal("1000000"), "毫克"),
        EG_UG(CategoryEnum.WEIGHT, "μg", new String[]{"μg", "ug", "微克"}, new BigDecimal("1000000000"), "微克"),
        EG_LB(CategoryEnum.WEIGHT, "lb", new String[]{"lb", "lbs", "磅"}, new BigDecimal("2.2046226"), "磅"),
        EG_OZ(CategoryEnum.WEIGHT, "oz", new String[]{"oz", "盎司"}, new BigDecimal("35.2739619"), "盎司"),
        EG_CT(CategoryEnum.WEIGHT, "ct", new String[]{"ct", "克拉"}, new BigDecimal("5000"), "克拉"),

        /**
         * 未知
         */
        UN_KNOWN(null, "未知", null, new BigDecimal("0"), "未知");

        /**
         * 计量类别
         */
        private final CategoryEnum category;
        /**
         * 计量单位
         */
        private final String units;
        /**
         * 可能的名称
         */
        private final String[] possibleNames;
        /**
         * 换算率
         */
        private final BigDecimal rate;
        /**
         * 描述
         */
        private final String description;

        /**
         * 计量类别
         */
        @Getter
        @AllArgsConstructor
        private enum CategoryEnum {
            /**
             * 长度
             */
            LENGTH("Length", UnitsEnum.LG_M, "长度"),
            /**
             * 重量
             */
            WEIGHT("Weight", UnitsEnum.EG_KG, "重量");

            /**
             * 类别名称
             */
            private final String name;
            /**
             * 类别基准
             */
            private final UnitsEnum base;
            /**
             * 描述
             */
            private final String description;
        }

    }

}


