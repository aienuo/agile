package com.imis.agile.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.*;

/**
 * <p>
 * 全局JSON序列化配置
 * </p>
 *
 * @author XinLau
 * @since 2020-03-27
 */
@Slf4j
@Configuration
public class JacksonConfiguration {

    /**
     * 处理序列化后的1.8的日期时间格式
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // 添加序列化程序
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer());
        // Long 转为 String 防止 js 丢失精度
        javaTimeModule.addSerializer(Long.class, ToStringSerializer.instance);
        // 添加反序列化程序
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
        return objectMapper;
    }

    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
            if (value == null) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeNumber(value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
        }
    }

    public static class LocalDateSerializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
            if (value == null) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeNumber(LocalDateTime.of(value, LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
        }
    }

    public static class LocalTimeSerializer extends JsonSerializer<LocalTime> {
        @Override
        public void serialize(LocalTime value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
            if (value == null) {
                jsonGenerator.writeNull();
            } else {
                jsonGenerator.writeNumber(LocalDateTime.of(LocalDate.now(), value).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
        }
    }

    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
            if (parser.hasTokenId(JsonTokenId.ID_NUMBER_INT)) {
                String string = parser.getText().trim();
                if (string.length() == 0) {
                    return null;
                }
                long timestamp = Long.parseLong(string);
                return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.ofHours(8));
            }
            return null;
        }
    }

    public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
            if (parser.hasTokenId(JsonTokenId.ID_NUMBER_INT)) {
                String string = parser.getText().trim();
                if (string.length() == 0) {
                    return null;
                }
                long timestamp = Long.parseLong(string);
                return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.ofHours(8)).toLocalDate();
            }
            return null;
        }
    }

    public static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
        @Override
        public LocalTime deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
            if (parser.hasTokenId(JsonTokenId.ID_NUMBER_INT)) {
                String string = parser.getText().trim();
                if (string.length() == 0) {
                    return null;
                }
                long timestamp = Long.parseLong(string);
                return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.ofHours(8)).toLocalTime();
            }
            return null;
        }
    }

}
