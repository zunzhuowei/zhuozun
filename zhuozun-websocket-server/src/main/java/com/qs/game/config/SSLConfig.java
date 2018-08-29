package com.qs.game.config;

import com.qs.game.common.Global;
import com.qs.game.constant.Env;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.net.ssl.KeyManagerFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * Created by zun.wei on 2018/8/29 17:50.
 * Description:
 */
@Profile({Env.TEST, Env.PROD})
@Configuration
public class SSLConfig {

    @Bean
    public SslContext initSSLContextTextProd() throws Exception {
        String type = "JKS";
        String path = "C:\\Users\\xin.tu\\wss.jks";
        String password = "netty123";
        KeyStore ks = KeyStore.getInstance(type); /// "JKS"
        InputStream ksInputStream = new FileInputStream(path); /// 证书存放地址
        ks.load(ksInputStream, password.toCharArray());
        //KeyManagerFactory充当基于密钥内容源的密钥管理器的工厂。
        //getDefaultAlgorithm:获取默认的 KeyManagerFactory 算法名称。
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());
        //SSLContext的实例表示安全套接字协议的实现，它充当用于安全套接字工厂或 SSLEngine 的工厂。
        return SslContextBuilder.forServer(kmf).build();
    }

    @Bean
    public ChannelInitializer<SocketChannel> createInitializer(Global global, SslContext context) {
        return new SecureServerHandlerInitializer(global, context); //ssl
    }

}
