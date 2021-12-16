/*
 * 爱组搭 http://aizuda.com 低代码组件化开发平台
 * ------------------------------------------
 * 受知识产权保护，请勿删除版权申明
 */
package com.imis.agile.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

/**
 * JSON 字符与对像转换
 * <p>
 * 尊重知识产权，CV 请保留版权，爱组搭 http://aizuda.com 出品
 *
 * @author hubin
 * @since 2021-11-09
 */
@Slf4j
public class JacksonUtils {

    private static class JacksonHolder {
        private static final ObjectMapper INSTANCE;

        static {
            JsonFactory jsonFactory = JsonFactory.builder()
                    // 可解析反斜杠引用的所有字符
                    .configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true)
                    // 允许JSON字符串包含非引号控制字符（值小于32的ASCII字符，包含制表符和换行符）
                    .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS, true).build();
            // Long 转为 String 防止 js 丢失精度
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
            // 日期格式
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            // 构建
            INSTANCE = JsonMapper.builder(jsonFactory)
                    .configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true)
                    .defaultLocale(Locale.CHINA)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .serializationInclusion(JsonInclude.Include.NON_NULL)
                    .addModules(simpleModule, javaTimeModule)
                    .build();

            INSTANCE.findAndRegisterModules();

        }
    }

    /**
     * 获取 ObjectMapper 实例
     *
     * @return ObjectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return JacksonHolder.INSTANCE;
    }

    /**
     * 把JavaBean转换为json字符串
     */
    public static String toJsonString(Object object) {
        try {
            return toJsonThrows(object);
        } catch (Exception e) {
            log.error("toJSONString error.", e);
        }
        return null;
    }

    /**
     * 把json字符串转换为JavaBean
     */
    public static <T> T parse(String json, Class<T> tClass) {
        try {
            return parseThrows(json, tClass);
        } catch (Exception e) {
            log.error("JSONString parse error.", e);
        }
        return null;
    }

    /**
     * 把json字符串转换为JavaBean,支持泛型
     */
    public static <T> T parse(String json, TypeReference<T> valueTypeRef) {
        try {
            return parseThrows(json, valueTypeRef);
        } catch (Exception e) {
            log.error("JSONString parse error.", e);
        }
        return null;
    }

    /**
     * 把JavaBean转换为json字符串，抛出异常
     */
    public static String toJsonThrows(Object object) throws Exception {
        return getObjectMapper().writeValueAsString(object);
    }

    /**
     * 把json字符串转换为JavaBean，抛出异常
     */
    public static <T> T parseThrows(String json, Class<T> tClass) throws Exception {
        return getObjectMapper().readValue(json, tClass);
    }

    /**
     * 把json字符串转换为JavaBean，支持泛型，抛出异常
     */
    public static <T> T parseThrows(String json, TypeReference<T> valueTypeRef) throws JsonProcessingException {
        return getObjectMapper().readValue(json, valueTypeRef);
    }

}
