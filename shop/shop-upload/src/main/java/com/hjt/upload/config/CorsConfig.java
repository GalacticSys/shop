package com.hjt.upload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2020/12/3 6:23
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){

        //初始化cors配置对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许跨域的域名，若要携带cookie,则不能配置为“*”，“*”代表所有域名都可以跨域访问
        corsConfiguration.addAllowedOrigin("http://manage.shop.com");
        corsConfiguration.addAllowedOrigin("http://127.0.0.1:9001");
        corsConfiguration.addAllowedOrigin("http://localhost:9001");
        corsConfiguration.setAllowCredentials(true);//允许携带cookie
        corsConfiguration.addAllowedMethod("*");//代表所有请求方法
        corsConfiguration.addAllowedHeader("*");//允许携带任何头信息
        //初始化cors配置源对象
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
