package com.imis.agile.util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;

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
    public static Boolean isEmpty(Object object) {
        if (null == object) {
            return Boolean.TRUE;
        }
        if (object instanceof CharSequence) {
            return object.toString().length() == 0;
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
    public static Boolean isNotEmpty(Object object) {
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

}
