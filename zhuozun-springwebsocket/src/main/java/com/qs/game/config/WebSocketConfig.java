package com.qs.game.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.MultipartConfigElement;

/**
 * Created by zun.wei on 2018/11/21.
 */
@Configuration
public class WebSocketConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

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

}
