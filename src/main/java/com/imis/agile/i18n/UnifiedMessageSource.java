package com.imis.agile.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * UnifiedMessageSource<br>
 * 全局I18N消息服务<br>
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年01月20日 13:24
 */
@Service
public class UnifiedMessageSource {

    /**
     * 用于解析消息的策略接口，支持此类消息的参数化和国际化
     */
    @Resource
    private MessageSource messageSource;

    /**
     * 获取国际化消息
     *
     * @param code - 消息code
     * @return String - 消息
     */
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * 获取国际化消息
     *
     * @param code - 消息code
     * @param args - 参数
     * @return String - 消息
     */
    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }

    /**
     * 获取国际化消息
     *
     * @param code           - 消息code
     * @param args           - 参数
     * @param defaultMessage - 默认消息
     * @return String - 消息
     */
    public String getMessage(String code, Object[] args, String defaultMessage) {
        return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

}
