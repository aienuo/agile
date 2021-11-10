package com.imis.agile.util;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

/**
 * 流水号生成规则(按默认规则递增，数字从1-999开始递增，数字到999，递增字母;位数不够增加位数)
 * A001
 * A001A002
 *
 * @Author XinLau
 */
public class SerialCodeUtil {
    /**
     * 数字位数
     */
    private static final int NUM_LENGTH = 3;
    /**
     * 大写字母A
     */
    private static final char LETTER_A = 'A';
    /**
     * 大写字母Z
     */
    private static final char LETTER_Z = 'Z';

    /**
     * 将数字前面位数补零
     *
     * @param num - 数字
     * @return String - 补零数字
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/6/6 14:28
     */
    private static String getNextStrNum(final Integer num) {
        return getStrNum(getNextNum(num));
    }

    /**
     * 将数字前面位数补零
     *
     * @param num - 数字
     * @return String - 补零数字
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/6/6 14:29
     */
    private static String getStrNum(final Integer num) {
        return String.format("%0" + NUM_LENGTH + "d", num);
    }

    /**
     * 递增获取下个数字
     *
     * @param num - 数字
     * @return int - 下个数字
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/6/6 14:30
     */
    private static int getNextNum(Integer num) {
        num++;
        return num;
    }

    /**
     * 递增获取下个字母
     *
     * @param letter - 字母
     * @return char - 下个字母
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/6/6 14:31
     */
    private static char getNextLetter(char letter) {
        if (letter == LETTER_Z) {
            return LETTER_A;
        }
        letter++;
        return letter;
    }

    /**
     * 根据数字位数获取最大值
     *
     * @param length - 长度
     * @return Integer - 取最大值
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/6/6 14:32
     */
    private static Integer getMaxNumByLength(final Integer length) {
        if (length == 0) {
            return 0;
        }
        StringBuilder maxNum = new StringBuilder(StringPool.EMPTY);
        for (int i = 0; i < length; i++) {
            maxNum.append("9");
        }
        return Integer.parseInt(maxNum.toString());
    }

    /**
     * 根据前一个code，获取同级下一个code（当前code为A001A001，下一个code为：A001A002）
     *
     * @param code -
     * @return code - code
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/6/6 14:23
     */
    public static synchronized String getNextSerialCode(final String code) {
        if (code == null || StringPool.EMPTY.equals(code.trim()) || StringPool.NULL.equals(code.trim())) {
            return LETTER_A + getStrNum(1);
        } else {
            String beforeCode = code.substring(0, code.length() - 1 - NUM_LENGTH);
            String afterCode = code.substring(code.length() - 1 - NUM_LENGTH, code.length());
            char afterCodeLetter = afterCode.substring(0, 1).charAt(0);
            int afterCodeNum = Integer.parseInt(afterCode.substring(1));
            String nextNum;
            char nextLetter;
            // 先判断数字等于999*，则计数从1重新开始，递增
            if (afterCodeNum == getMaxNumByLength(NUM_LENGTH)) {
                nextNum = getNextStrNum(0);
            } else {
                nextNum = getNextStrNum(afterCodeNum);
            }
            // 先判断数字等于999*，则字母从A重新开始,递增
            if (afterCodeNum == getMaxNumByLength(NUM_LENGTH)) {
                nextLetter = getNextLetter(afterCodeLetter);
            } else {
                nextLetter = afterCodeLetter;
            }
            // 例如Z999，下一个code就是Z999A001
            if (LETTER_Z == afterCodeLetter && getMaxNumByLength(NUM_LENGTH) == afterCodeNum) {
                return code + (nextLetter + nextNum);
            } else {
                return beforeCode + (nextLetter + nextNum);
            }
        }
    }

    /**
     * 根据父亲code,获取下级的下一个code（父code:A001，当前code:A001B003；获取的code:A001B004）
     *
     * @param parentCode 上级code
     * @param localCode  同级code
     * @return String  下一个code
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/6/6 14:56
     */
    public static synchronized String getNextSerialCode(String parentCode, final String localCode) {
        if (localCode == null || StringPool.EMPTY.equals(localCode.trim()) || StringPool.NULL.equals(localCode.trim())) {
            parentCode = parentCode + LETTER_A + getNextStrNum(0);
        } else {
            return getNextSerialCode(localCode);
        }
        return parentCode;
    }

    /**
     * 将流水号切割
     *
     * @param code - 流水号
     * @return String[] - 第一个元素是父级流水号，第二个元素为流水号
     * @author XinLau
     * @creed The only constant is change ! ! !
     * @since 2020/6/6 15:17
     */
    public static String[] cuttingSerialCode(final String code) {
        if (code == null || StringPool.EMPTY.equals(code.trim()) || StringPool.NULL.equals(code.trim())) {
            return null;
        } else {
            // 获取标准长度为numLength+1,截取的数量为code.length/numLength+1
            int c = code.length() / (NUM_LENGTH + 1);
            String[] cutCode = new String[c];
            for (int i = 0; i < c; i++) {
                cutCode[i] = code.substring(0, (i + 1) * (NUM_LENGTH + 1));
            }
            return cutCode;
        }
    }

}
