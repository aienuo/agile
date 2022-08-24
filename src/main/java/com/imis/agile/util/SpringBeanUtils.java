package com.imis.agile.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Spring 工具类 方便在非 Spring 管理环境中获取 Bean
 *
 * @author XinLau
 * @version 1.0
 * @since 2021年07月20日 10:51
 */
@Slf4j
@Component
public final class SpringBeanUtils implements BeanFactoryPostProcessor {

    /**
     * Spring 应用上下文环境
     */
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringBeanUtils.beanFactory = beanFactory;
    }

    /**
     * 获取对象
     *
     * @param beanName - Bean 的名字
     * @return Object - 一个以所给名字注册的 Bean 的实例
     * @throws BeansException - Bean 异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(final String beanName) throws BeansException {
        return (T) beanFactory.getBean(beanName);
    }

    /**
     * 获取对象
     *
     * @param beanClass - Bean 的 Class
     * @return Object - 一个 Bean 的实例
     * @throws BeansException - Bean 异常
     */
    public static <T> T getBean(final Class<T> beanClass) throws BeansException {
        return (T) beanFactory.getBean(beanClass);
    }

    /**
     * 判断 BeanFactory 是否包含一个与所给名称匹配的 Bean 定义
     *
     * @param beanName - Bean 的名字
     * @return boolean - true表示包含
     */
    public static boolean containsBean(final String beanName) {
        return beanFactory.containsBean(beanName);
    }

    /**
     * 判断以给定名字注册的 Bean 定义是一个 singleton 还是一个 prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param beanName - Bean 的名字
     * @return boolean
     * @throws NoSuchBeanDefinitionException - 没有这样 Bean 的异常
     */
    public static boolean isSingleton(String beanName) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(beanName);
    }

    /**
     * 获取一个以所给名字注册的 Bean 的类型
     *
     * @param beanName - Bean 的名字
     * @return Class - 注册对象的类型
     * @throws NoSuchBeanDefinitionException - 没有这样 Bean 的异常
     */
    public static Class<?> getType(String beanName) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(beanName);
    }

    /**
     * 如果给定的 Bean 名字在 Bean 定义中有别名，则返回这些别名
     *
     * @param beanName - Bean 的名字
     * @return String[] - Bean 的别字
     * @throws NoSuchBeanDefinitionException -  - 没有这样 Bean 的异常
     */
    public static String[] getAliases(String beanName) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(beanName);
    }

}
