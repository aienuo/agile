package com.imis.agile.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.imis.agile.constant.CommonConstant;
import com.imis.agile.interceptor.AuthenticationInterceptor;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.*;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

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
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration implements WebMvcConfigurer {

    /**
     * 文件本地存储路径（跟jar包同级目录，自动拼接 “./”）
     */
    @Value(value = "${imis-boot.path.upload}")
    private String uploadPath;

    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Autowired(required = false)
    public SwaggerConfiguration(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    /**
     * api文档的详细信息函数,注意这里的注解引用的是哪个
     *
     * @param description - 描述
     * @return ApiInfo
     */
    private ApiInfo apiInfo(final String description) {
        return new ApiInfoBuilder()
                // 大标题
                .title("后台服务API接口文档")
                // 版本号
                .version("1.0")
                // 条款服务URL
                // .termsOfServiceUrl("NO terms of service")
                // 描述
                .description(description)
                // 联系人信息
                .contact(new Contact("XinLau", "https://github.com/XinLau1996", "xiaotaiye1996@gmail.com"))
                // 许可证
                .license("The Apache License, Version 2.0")
                // 许可证URL
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

    /**
     * 全局参数
     *
     * @return List<Parameter>
     */
    private List<Parameter> getGlobalOperationParameters() {
        // 参数数组
        List<Parameter> parameterList = new ArrayList<>();
        parameterList.add(new ParameterBuilder()
                // 更新参数名称
                .name(CommonConstant.X_ACCESS_TOKEN)
                // 更新参数的默认值
                .defaultValue(StringPool.EMPTY)
                // 更新参数说明
                .description("全局请求头参数：X-Access-Token")
                // 表示推断模型参考的便捷方法合并或弄清楚哪些内容可以汇总
                .modelRef(new ModelRef("string"))
                // 更新参数类型
                .parameterType("header")
                // TODO：更新参数是否为必需或可选
                .required(Boolean.FALSE)
                // 构建
                .build());
        return parameterList;
    }

    /**
     * swagger的配置文件，这里可以配置swagger的一些基本的内容，比如扫描的包等等
     *
     * @param groupName       - 模块名称
     * @param basePackageName - 扫描包
     * @param description     - 模块
     * @return Docket
     */
    private Docket createRestApi(final String groupName, final String basePackageName, final String description) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .apiInfo(apiInfo(description))
                .select()
                // 此包路径下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage(basePackageName))
                // 加了ApiOperation注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                // .securitySchemes()
                // 添加将应用于所有操作的默认参数
                .globalOperationParameters(getGlobalOperationParameters())
                // 添加忽略类（不再生成 swaggerModel）
                // .ignoredParameterTypes(BaseResponse.class, CommonResponse.class, ErrorResponse.class)
                // 进行插件赋值
                .extensions(openApiExtensionResolver.buildExtensions(groupName))
                ;
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

    /**
     * 创建 Swagger 接口的 分类
     *
     * @return Docket 分类
     */
    @Bean
    public Docket createLoginRelatedApi() {
        return createRestApi("1、登陆相关模块", "com.imis.agile.module.api.controller", "注册、登录、注销、改密、登陆后获取功能菜单（带Token）、常用接口、下拉接口");
    }

    /**
     * 创建 Swagger 接口的 分类
     *
     * @return Docket 分类
     */
    @Bean
    public Docket createSystemManagementApi() {
        return createRestApi("2、系统管理模块", "com.imis.agile.module.system.controller", "用户管理、角色管理、功能菜单、组织机构、字典管理、系统日志");
    }

}
