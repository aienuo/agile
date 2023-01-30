package com.imis.agile.util;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * AgileUtil<br>
 *
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年07月19日 16:47
 */
@Slf4j
public class AgileUtil {

    /**
     * 判断指定对象是否为空，支持：
     *
     * <pre>
     * 1. CharSequence
     * 2. Map
     * 3. Iterable
     * 4. Iterator
     * 5. Array
     * </pre>
     *
     * @param object 被判断的对象
     * @return 是否为空，如果类型不支持，返回false
     */
    public static Boolean isEmpty(final Object object) {
        if (null == object) {
            return Boolean.TRUE;
        }
        if (object instanceof CharSequence) {
            return 0 == object.toString().length();
        } else if (object instanceof Map) {
            return ((Map<?, ?>) object).isEmpty();
        } else if (object instanceof Iterable) {
            return !((Iterable<?>) object).iterator().hasNext();
        } else if (object instanceof Iterator) {
            return !((Iterator<?>) object).hasNext();
        } else if (object.getClass().isArray()) {
            return 0 == Array.getLength(object);
        }
        return Boolean.FALSE;
    }

    /**
     * 一次性判断多个或单个对象为空。
     *
     * @param objects - 最少一个对象
     * @return Boolean - 只要有一个元素为Empty，则返回true
     */
    public static Boolean isEmpty(Object... objects) {
        boolean result = false;
        for (Object object : objects) {
            if (isEmpty(object)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 判断指定对象是否为非空，支持：
     *
     * <pre>
     * 1. CharSequence
     * 2. Map
     * 3. Iterable
     * 4. Iterator
     * 5. Array
     * </pre>
     *
     * @param object 被判断的对象
     * @return 是否为空，如果类型不支持，返回true
     */
    public static Boolean isNotEmpty(final Object object) {
        return !isEmpty(object);
    }

    /**
     * 一次性判断多个或单个对象非空判断
     *
     * @param objects - 最少一个对象
     * @return Boolean
     */
    public static Boolean isNotEmpty(Object... objects) {
        return !isEmpty(objects);
    }

    /**
     * 判断对象是否为纯数字
     *
     * @param object - 最少一个对象
     * @return Boolean - true - 纯数字
     */
    public static Boolean isNumeric(final Object object) {
        return isNotEmpty(object) && object.toString().chars().allMatch(Character::isDigit);
    }

    /**
     * 从字符串的末尾删除任何一组字符。<br>
     * <li>null输入字符串返回null 。 空字符串 ("") 输入返回空字符串。</li>
     * <li>如果 stripChars 为null ，则按照Character.isWhitespace(char)的定义去除空格</li>
     *
     * @param string     - 将要被截取的字符串
     * @param stripChars - 需要删除的一组字符
     * @return String - 删除字符后的字符串
     */
    public static String stripEnd(final String string, final String stripChars) {
        int end = string == null ? 0 : string.length();
        if (end == 0) {
            return string;
        }
        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(string.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.isEmpty()) {
            return string;
        } else {
            while (end != 0 && stripChars.indexOf(string.charAt(end - 1)) != -1) {
                end--;
            }
        }
        return string.substring(0, end);
    }

    /**
     * 删除 HTML 标签
     *
     * @param htmlStr - HTML
     * @return String - 字符串
     */
    public static String delHtmlTag(String htmlStr) {
        if (isEmpty(htmlStr)){
            return htmlStr;
        }
        // 定义script的正则表达式
        final String regExScript = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        // 定义style的正则表达式
        final String regExStyle = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        // 定义HTML标签的正则表达式
        final String regExHtml = "<[^>]+>";
        // 过滤script标签
        Pattern scriptPattern = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
        Matcher scriptMatcher = scriptPattern.matcher(htmlStr);
        htmlStr = scriptMatcher.replaceAll(StringPool.EMPTY);
        // 过滤style标签
        Pattern stylePattern = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
        Matcher styleMatcher = stylePattern.matcher(htmlStr);
        htmlStr = styleMatcher.replaceAll(StringPool.EMPTY);
        // 过滤html标签
        Pattern htmlPattern = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
        Matcher htmlMatcher = htmlPattern.matcher(htmlStr);
        htmlStr = htmlMatcher.replaceAll(StringPool.EMPTY);
        // 返回文本字符串
        return htmlStr.trim();
    }

     /**
     * 十进制转化为二进制
     *
     * @param decimal - 十进制
     * @return binaryString - 二进制
     */
    public static String decimalToBinary(Integer decimal){
        String binaryString = "";
        while (decimal > 0){
            // 除2取余数作为二进制数
            int remainder = decimal % 2;
            binaryString = remainder + binaryString;
            decimal = decimal / 2;
        }
        return binaryString;
    }

    /**
     * 二进制转换为十进制
     *
     * @param binaryString - 二进制
     * @return decimal - 十进制
     */
    public static Integer biannaryToDecimal(String binaryString){
        Integer decimal = 0;
        int len = binaryString.length();
        for (int i = 1; i <= len; i++){
            // 第i位的数字
            int dt = Integer.parseInt(binaryString.substring(i-1,i));
            decimal += (int)Math.pow(2, len - i) * dt;
        }
        return decimal;
    }

}
