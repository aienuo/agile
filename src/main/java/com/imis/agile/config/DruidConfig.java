package com.imis.agile.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * DruidConfig<br>
 * 关系型数据库连接池
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月31日 16:50
 */
@Configuration
public class DruidConfig {

    @Value("${spring.datasource.druid.stat-view-servlet.loginUsername}")
    private String loginUserName;

    @Value("${spring.datasource.druid.stat-view-servlet.loginPassword}")
    private String loginPassword;

    @Bean
    public ServletRegistrationBean<StatViewServlet> startViewServlet() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // IP白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // IP黑名单(共同存在时，deny优先于allow)
        // servletRegistrationBean.addInitParameter("deny","127.0.0.1");
        // 控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername" , loginUserName);
        servletRegistrationBean.addInitParameter("loginPassword" , loginPassword);
        // 是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable" , "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> statFilter() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean;
        filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        // 添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 忽略过滤的格式
        filterRegistrationBean.addInitParameter("exclusions" , "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
