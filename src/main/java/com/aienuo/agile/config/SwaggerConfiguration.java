package com.aienuo.agile.config;

import com.aienuo.agile.interceptor.AuthenticationInterceptor;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * <p>
 * SwaggerConfiguration<br>
 * knife4j 配置类
 * </p>
 *
 * @author XinLau
 * @since 2020-03-12
 */
@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {

    /**
     * 文件本地存储路径（跟jar包同级目录，自动拼接 “./”）
     */
    @Value(value = "${aienuo-boot.path.upload}")
    private String uploadPath;

    /**
     * 根据@Tag 上的排序，写入x-order TODO:This
     *
     * @return the global open api customizer
     */
    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getTags() != null) {
                openApi.getTags().forEach(tag -> {
                    tag.addExtension("x-order", 995);
                });
            }
            if (openApi.getPaths() != null) {
                openApi.addExtension("x-test123", "333");
                openApi.getPaths().addExtension("x-abb", 996);
            }
        };
    }

    /**
     * 创建 OpenAPI 简介信息
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI introductionInformation() {
        return new OpenAPI().info(
                new Info().title("后台服务API接口文档")
                        .version("1.0")
                        .description("基于SpringBoot3 + Vue2 前后端不分离面向企业级Java Web应用的框架")
                        .termsOfService("https://doc.aienuo.com")
                        .license(new License().name("Apache 2.0")
                                .url("https://aienuo.com")
                        )
        );
    }

    /**
     * 添加资源处理程序
     *
     * @param resourceHandlerRegistry - 资源处理程序注册表
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        // 本地文件上传-路径模式
        String pathPatterns = StringPool.SLASH + uploadPath + StringPool.SLASH + StringPool.ASTERISK + StringPool.ASTERISK;
        // 本地文件上传-文件资源位置（跟jar包同级目录，自动拼接 “./”）
        String resourceLocations = "file" + StringPool.COLON + StringPool.DOT + StringPool.SLASH + uploadPath + StringPool.SLASH;
        resourceHandlerRegistry.addResourceHandler(pathPatterns).addResourceLocations(resourceLocations);
        resourceHandlerRegistry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        resourceHandlerRegistry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        resourceHandlerRegistry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        resourceHandlerRegistry.addResourceHandler("/static/**").addResourceLocations("classpath:/templates/static/");
        resourceHandlerRegistry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
    }

    /**
     * 跨域访问
     *
     * @param registry - Cors 注册表
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许跨域访问的路径
        registry.addMapping("/**")
                // 允许跨域访问的源
                .allowedOriginPatterns("*")
                // 允许请求方法
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                // 预检间隔时间
                .maxAge(168000)
                // 允许头部设置
                .allowedHeaders("*")
                // 是否允许证书（Cookies）
                .allowCredentials(true);
    }

    /**
     * 添加视图控制器
     *
     * @param registry - 视图控制器注册表
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    /**
     * 创建 身份验证拦截器
     *
     * @return AuthenticationInterceptor
     */
    @Bean
    public AuthenticationInterceptor setAuthenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    /**
     * 添加拦截器
     *
     * @param registry - InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链 addPathPatterns 用于添加拦截规则
        String[] pathArray = new String[]{
                "/info/**", "/sys/**", "/websocket/**", "/update", "/password"
        };
        registry.addInterceptor(setAuthenticationInterceptor()).addPathPatterns(pathArray);
    }

}
