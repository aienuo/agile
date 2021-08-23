package com.imis.agile.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * MyBatisPlusConfig<br>
 *
 * </p>
 *
 * @author XinLau
 * @version 1.0
 * @since 2020年03月31日 16:50
 */
@Configuration
@MapperScan(value = {"com.imis.agile.**.mapper"})
public class MyBatisPlusConfig {

    /**
     * 分页插件，自动识别数据库类型
     * @return PaginationInterceptor - 分页拦截器
     * @author XinLau
     * @since 2020/7/31 10:08
     * @creed The only constant is change ! ! !
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * mybatis-plus SQL执行效率插件
     * @return PerformanceInterceptor - 性能拦截器
     * @author XinLau
     * @since 2020/7/31 10:06
     * @creed The only constant is change ! ! !
     */
    /*@Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }*/

}
