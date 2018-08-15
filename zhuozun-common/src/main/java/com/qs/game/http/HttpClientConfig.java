package com.qs.game.http;

import lombok.Data;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zun.wei on 2018/8/15 13:37.
 * Description: 注入连接池等，用于在service层获取httpclient
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "http")
public class HttpClientConfig {
//    #最大连接数
//    http.maxTotal = 100
//            #并发数
//    http.defaultMaxPerRoute = 20
//            #创建连接的最长时间
//    http.connectTimeout=1000
//            #从连接池中获取到连接的最长时间
//    http.connectionRequestTimeout=500
//            #数据传输的最长时间
//    http.socketTimeout=10000
//            #提交请求前测试连接是否可用
//    http.staleConnectionCheckEnabled=true

    //#最大连接数
    private Integer maxTotal = 100;

    //#并发数
    private Integer defaultMaxPerRoute = 20;

    //#创建连接的最长时间
    private Integer connectTimeout = 1000;

    //#从连接池中获取到连接的最长时间
    private Integer connectionRequestTimeout = 500;

    //#数据传输的最长时间
    private Integer socketTimeout = 10000;

    //#提交请求前测试连接是否可用
    private boolean staleConnectionCheckEnabled = true;


    /**
     * 首先实例化一个连接池管理器，设置最大连接数、并发连接数
     *
     * @return
     */
    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        //最大连接数
        httpClientConnectionManager.setMaxTotal(maxTotal);
        //并发数
        httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return httpClientConnectionManager;
    }

    /**
     * 实例化连接池，设置连接池管理器。
     * 这里需要以参数形式注入上面实例化的连接池管理器
     *
     * @param httpClientConnectionManager
     * @return
     */
    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder
    (@Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager) {
        //HttpClientBuilder中的构造方法被protected修饰，所以这里不能直接使用new来实例化一个HttpClientBuilder，
        // 可以使用HttpClientBuilder提供的静态方法create()来获取HttpClientBuilder对象
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        //开启失败重试三次
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true));
        return httpClientBuilder;
    }

    /**
     * 注入连接池，用于获取httpClient
     *
     * @param httpClientBuilder
     * @return
     */
    @Bean
    public CloseableHttpClient getCloseableHttpClient(@Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder) {
        return httpClientBuilder.build();
    }

    /**
     * Builder是RequestConfig的一个内部类
     * 通过RequestConfig的custom方法来获取到一个Builder对象
     * 设置builder的连接信息
     * 这里还可以设置proxy，cookieSpec等属性。有需要的话可以在此设置
     *
     * @return
     */
    @Bean(name = "builder")
    public RequestConfig.Builder getBuilder() {
        RequestConfig.Builder builder = RequestConfig.custom();
        return builder
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout)
                .setStaleConnectionCheckEnabled(staleConnectionCheckEnabled);
    }

    /**
     * 使用builder构建一个RequestConfig对象
     *
     * @param builder
     * @return
     */
    @Bean
    public RequestConfig getRequestConfig(@Qualifier("builder") RequestConfig.Builder builder) {
        return builder.build();
    }

}
