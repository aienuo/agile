package com.imis.agile.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份证号码解析工具类
 *
 * @author XinLau
 * @remarks 根据《中华人民共和国国家标准GB 11643-1999》中有关公民身份号码的规定，
 * 公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：
 * 六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
 * 顺序码的奇数分给男性，偶数分给女性。校验码是根据前面十七位数字码，
 * 按照ISO 7064:1983.MOD 11-2校验码计算出来的检验码。
 * 最后一位：18位数据计算规则：
 * 居民身份证是国家法定的证明公民个人身份的有效证件．身份证号码由十七位数字本体码和一位数字校验码组成．
 * 第1-6位是地址码，第7-14位是出生日期码，第15-17位是顺序码，即是县、区级政府所辖派出所的分配码．
 * 第18位也就是最后一位是数字校验码，是根据前面十七位数字码，按一定规则计算出来的校验码．算法如下：
 * 规定第1-17位对应的系数分别为：7，9，10，5，8，4，2，1，6，3，7，9，10，5，8，4，2．
 * 将身份证号码的前17位数字分别乘以对应的系数，再把积相加．相加的结果除以11，求出余数．
 * 余数只可能有0，1，2，3，4，5，6，7，8，9，10这11种情况．其分别对应身份证号码的第18位数字如表所示．
 * 余数	0	1	2	3	4	5	6	7	8	9	10
 * 第18位	1	0	x	9	8	7	6	5	4	3	2
 * 通过上面得知如果余数是3，则身份证的第18位数字就是9．如果余数是2，则身份证的第18位号码就是x．若
 * 某人的身份证号码的前17位依次是11010219600302011，则他身份证号码的第18位数字是3.
 * @since 2020-09-08 16:17
 **/
@Slf4j
public class IdCardUtil {

    final static Map<Integer, String> ZONE_NUM = new HashMap<>();
    final static int[] PARITY_BIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    final static int[] POWER_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
            5, 8, 4, 2};

    static {
        ZONE_NUM.put(11, "北京");
        ZONE_NUM.put(12, "天津");
        ZONE_NUM.put(13, "河北");
        ZONE_NUM.put(14, "山西");
        ZONE_NUM.put(15, "内蒙古");
        ZONE_NUM.put(21, "辽宁");
        ZONE_NUM.put(22, "吉林");
        ZONE_NUM.put(23, "黑龙江");
        ZONE_NUM.put(31, "上海");
        ZONE_NUM.put(32, "江苏");
        ZONE_NUM.put(33, "浙江");
        ZONE_NUM.put(34, "安徽");
        ZONE_NUM.put(35, "福建");
        ZONE_NUM.put(36, "江西");
        ZONE_NUM.put(37, "山东");
        ZONE_NUM.put(41, "河南");
        ZONE_NUM.put(42, "湖北");
        ZONE_NUM.put(43, "湖南");
        ZONE_NUM.put(44, "广东");
        ZONE_NUM.put(45, "广西");
        ZONE_NUM.put(46, "海南");
        ZONE_NUM.put(50, "重庆");
        ZONE_NUM.put(51, "四川");
        ZONE_NUM.put(52, "贵州");
        ZONE_NUM.put(53, "云南");
        ZONE_NUM.put(54, "西藏");
        ZONE_NUM.put(61, "陕西");
        ZONE_NUM.put(62, "甘肃");
        ZONE_NUM.put(63, "青海");
        ZONE_NUM.put(64, "宁夏");
        ZONE_NUM.put(65, "新疆");
        ZONE_NUM.put(71, "台湾");
        ZONE_NUM.put(81, "香港");
        ZONE_NUM.put(82, "澳门");
        ZONE_NUM.put(91, "外国");
    }

    /**
     * 身份证号码长度
     */
    private final static int ID_CARD_15 = 15;
    private final static int ID_CARD_18 = 18;
    /**
     * 魔法值常量数
     */
    private final static int NUMBER_0 = 0;
    private final static int NUMBER_1 = 1;
    private final static int NUMBER_2 = 2;
    private final static int NUMBER_3 = 3;
    private final static int YEAR_START_NUMBER = 1900;
    private final static int MONTH_NUMBER = 12;
    private final static int DAY_OF_MONTH_NUMBER = 31;

    /**
     * 校验身份证信息是否正确
     *
     * @param idCardNumber 身份证号码
     * @return 是否有效 null和 "" 都是false
     */
    public static Boolean isIdCard(final String idCardNumber) {
        if (idCardNumber == null || (idCardNumber.length() != ID_CARD_15 && idCardNumber.length() != ID_CARD_18)) {
            return false;
        }
        final char[] cs = idCardNumber.toUpperCase().toCharArray();
        final int length = cs.length;
        // 校验位数
        int power = NUMBER_0;
        for (int i = NUMBER_0; i < length; i++) {
            if (i == length - 1 && cs[i] == 'X') {
                // 最后一位可以 是X或x
                break;
            }
            if (cs[i] < '0' || cs[i] > '9') {
                return false;
            }
            if (i < length - 1) {
                power += (cs[i] - '0') * POWER_LIST[i];
            }
        }
        // 校验区位码
        if (!ZONE_NUM.containsKey(Integer.valueOf(idCardNumber.substring(NUMBER_0, NUMBER_2)))) {
            return false;
        }
        // 校验年份
        final int intYear = getYearByIdCard(idCardNumber);
        if (intYear < YEAR_START_NUMBER || intYear > Calendar.getInstance().get(Calendar.YEAR)) {
            // 1900年的 PASS，超过今年的 PASS
            return false;
        }
        // 校验月份
        final int intMonth = getMonthByIdCard(idCardNumber);
        if (intMonth < NUMBER_1 || intMonth > MONTH_NUMBER) {
            return false;
        }
        // 校验天数
        final int intDay = getDateByIdCard(idCardNumber);
        if (intDay < NUMBER_1 || intDay > DAY_OF_MONTH_NUMBER) {
            return false;
        }
        // 校验 "校验码"
        if (idCardNumber.length() == ID_CARD_15) {
            return true;
        }
        return cs[cs.length - NUMBER_1] == PARITY_BIT[power % 11];
    }

    /**
     * 解析身份证获取地址区域编码
     *
     * @param idCardNumber 身份证号码
     * @return Integer 区域码
     */
    public static Integer getAreaCode(final String idCardNumber) {
        String areaString = idCardNumber.substring(NUMBER_0, 6);
        return Integer.parseInt(areaString);
    }

    /**
     * 判断来源地址是否属于目标地址
     *
     * @param sourceCode 来源地址
     * @param targetCode 目标地址
     * @param level      级别（1：省 2：市  3：县）
     * @return Boolean
     */
    public static Boolean isBelong(final String sourceCode, final String targetCode, final Integer level) {
        if (level.equals(NUMBER_1)) {
            return sourceCode.substring(NUMBER_0, NUMBER_2).equals(targetCode.substring(NUMBER_0, NUMBER_2));
        }
        if (level.equals(NUMBER_2)) {
            return sourceCode.substring(NUMBER_0, 4).equals(targetCode.substring(NUMBER_0, 4));
        }
        if (level.equals(NUMBER_3)) {
            return sourceCode.substring(NUMBER_0, 6).equals(targetCode.substring(NUMBER_0, 6));
        }
        return false;
    }

    /**
     * 解析身份证号码获取性别
     *
     * @param idCardNumber 身份证号码
     * @return Integer 1 - 男；0 - 女
     */
    public static Integer getSex(final String idCardNumber) {
        String sex = idCardNumber.substring(12, 15);
        if (idCardNumber.length() == ID_CARD_18) {
            sex = idCardNumber.substring(16, 17);
        }
        if (Integer.parseInt(sex) % NUMBER_2 != NUMBER_0) {
            return NUMBER_1;
        } else {
            return NUMBER_0;
        }
    }

    /**
     * 解析身份证号码获取年龄
     *
     * @param idCardNumber 身份证号码
     * @return Integer
     */
    public static Integer getAge(final String idCardNumber) {
        return getBirthByIdCard(idCardNumber).until(LocalDate.now()).getYears();
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCardNumber 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static String getBirthStringByIdCard(final String idCardNumber) {
        String birthday = idCardNumber.substring(6, 14);
        if (idCardNumber.length() == ID_CARD_15) {
            birthday = "19" + idCardNumber.substring(6, 12);
        }
        return birthday;
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCardNumber 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static LocalDate getBirthByIdCard(final String idCardNumber) {
        return LocalDate.parse(getBirthStringByIdCard(idCardNumber), DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 根据身份编号获取生日年
     *
     * @param idCardNumber 身份编号
     * @return 生日(yyyy)
     */
    public static Integer getYearByIdCard(final String idCardNumber) {
        LocalDate localDate = getBirthByIdCard(idCardNumber);
        return localDate.getYear();
    }

    /**
     * 根据身份编号获取生日月
     *
     * @param idCardNumber 身份编号
     * @return 生日(MM)
     */
    public static Integer getMonthByIdCard(final String idCardNumber) {
        LocalDate localDate = getBirthByIdCard(idCardNumber);
        return localDate.getMonthValue();
    }

    /**
     * 根据身份编号获取生日天
     *
     * @param idCardNumber 身份编号
     * @return 生日(dd)
     */
    public static Integer getDateByIdCard(final String idCardNumber) {
        LocalDate localDate = getBirthByIdCard(idCardNumber);
        return localDate.getDayOfMonth();
    }

}
