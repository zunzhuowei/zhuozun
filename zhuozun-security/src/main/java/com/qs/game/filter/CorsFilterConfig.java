package com.qs.game.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zun.wei on 2018/8/14 12:22.
 * Description:  跨域访问处理(跨域资源共享)  解决前后端分离架构中的跨域问题
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsFilterConfig {

    /*
    application.yml配置
    # 跨域请求设置
    cors:
      allowOrigin: "*"
      allowMethods: GET,POST,PUT,DELETE,OPTIONS
      allowCredentials: false # 如果要把Cookie发到服务器，需要指定Access-Control-Allow-Credentials字段为true;
      allowHeaders: Content-Type,X-Token
      exposeHeaders:
    */
    /**
     * 如果yml没有配置使用默认值
     */
    private String allowOrigin = "*";

    private String allowMethods = "GET,POST,PUT,DELETE,OPTIONS";

    private String allowCredentials = "false";

    private String allowHeaders = "Origin,No-Cache,X-Requested-With,If-Modified-Since,Pragma,Last-Modified," +
            "Cache-Control,Expires,X-E4M-With,userId,token,Content-Type,X-Token";

    private String exposeHeaders = "";

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CorsFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("allowOrigin", allowOrigin);
        registration.addInitParameter("allowMethods", allowMethods);
        registration.addInitParameter("allowCredentials", allowCredentials);
        registration.addInitParameter("allowHeaders", allowHeaders);
        registration.addInitParameter("exposeHeaders", exposeHeaders);
        registration.setName("corsFilter");
        registration.setOrder(0);
        return registration;
    }

}
