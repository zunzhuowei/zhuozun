package com.qs.game.config;

import com.qs.game.handler.spring.SpringBinaryWebSocketHandler;
import com.qs.game.handler.spring.SpringTextWebSocketHandler;
import com.qs.game.interceptor.SpringWebSocketHandlerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import javax.annotation.Resource;
import javax.servlet.MultipartConfigElement;

import static com.qs.game.config.SysConfig.WEBSOCKET_URI_PATH;

/**
 * Created by zun.wei on 2018/11/21.
 */
@Configuration
@EnableWebSocket // (1) spring websocket config
public class WebSocketConfig implements WebSocketConfigurer, WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Value("${tomcat.server.port}")
    private String port;
    @Value("${tomcat.server.acceptorThreadCount}")
    private String acceptorThreadCount;
    @Value("${tomcat.server.minSpareThreads}")
    private String minSpareThreads;
    @Value("${tomcat.server.maxSpareThreads}")
    private String maxSpareThreads;
    @Value("${tomcat.server.maxThreads}")
    private String maxThreads;
    @Value("${tomcat.server.maxConnections}")
    private String maxConnections;
    @Value("${tomcat.server.protocol}")
    private String protocol;
    @Value("${tomcat.server.redirectPort}")
    private String redirectPort;
    @Value("${tomcat.server.compression}")
    private String compression;
    @Value("${tomcat.server.connectionTimeout}")
    private String connectionTimeout;

    @Value("${tomcat.server.MaxFileSize}")
    private String MaxFileSize;
    @Value("${tomcat.server.MaxRequestSize}")
    private String MaxRequestSize;


    // websocket support
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    // upload config
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(MaxFileSize); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize(MaxRequestSize);
        return factory.createMultipartConfig();
    }

    //优化配置 spring boot 2.0 内置tomcat的
    @Override
    public void customize(ConfigurableServletWebServerFactory webServerFactory) {
        TomcatServletWebServerFactory factory = (TomcatServletWebServerFactory) webServerFactory;
        factory.addConnectorCustomizers
                ((TomcatConnectorCustomizer) connector -> {
                    connector.setPort(Integer.valueOf(port));
                    connector.setAttribute("connectionTimeout", connectionTimeout);
                    connector.setAttribute("acceptorThreadCount", acceptorThreadCount);
                    connector.setAttribute("minSpareThreads", minSpareThreads);
                    connector.setAttribute("maxSpareThreads", maxSpareThreads);
                    connector.setAttribute("maxThreads", maxThreads);
                    connector.setAttribute("maxConnections", maxConnections);
                    connector.setAttribute("protocol", protocol);
                    connector.setAttribute("redirectPort", redirectPort);
                    connector.setAttribute("compression", compression);
                });

    }

    // 配置 servlet 接收的包大小
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192); // 接收单个包最大缓存值
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }


    @Resource
    private SpringBinaryWebSocketHandler springBinaryWebSocketHandler;

    @Resource
    private SpringTextWebSocketHandler springTextWebSocketHandler;

    // spring websocket 和tomcat websocket 同时配置相同path，tomcat优先级比较高，
    // 如果不同两者path路径，则两者共存。
    @Override// (2) spring websocket config
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                //.addHandler(springTextWebSocketHandler, WEBSOCKET_URI_PATH)
                .addHandler(springBinaryWebSocketHandler, WEBSOCKET_URI_PATH)
                .addInterceptors(new SpringWebSocketHandlerInterceptor())
                .setAllowedOrigins("*");
    }

}
