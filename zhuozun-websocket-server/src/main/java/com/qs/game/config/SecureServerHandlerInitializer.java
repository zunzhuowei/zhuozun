package com.qs.game.config;

import com.qs.game.common.Global;
import com.qs.game.constant.Env;
import com.qs.game.constant.StrConst;
import com.qs.game.handler.AccessHandler;
import com.qs.game.handler.BusinessHandler;
import com.qs.game.handler.HeartbeatHandler;
import com.qs.game.handler.HttpRequestHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * Created by zun.wei on 2018/8/29 11:38.
 * Description: ssl handler
 */
@Profile({Env.TEST, Env.PROD})
@Component
@Qualifier("secureServerHandlerInitializer")
public class SecureServerHandlerInitializer extends ChannelInitializer<SocketChannel> {


    //@Autowired
    //private SslContext context;

    @Autowired
    private Global global;

    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(16);


//    public SecureServerHandlerInitializer(Global global, SslContext context) {
//        this.global = global;
//        this.context = context;
//    }

    @Autowired
    private AccessHandler accessHandler;

    @Autowired
    private BusinessHandler businessHandler;

    @Autowired
    private HeartbeatHandler heartbeatHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
      /*
        ChannelPipeline pipeline = ch.pipeline();
        //处理日志
        pipeline.addLast(new LoggingHandler(LogLevel.TRACE));
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new IdleStateHandler(10, 0, 0));
        pipeline.addLast(group, heartbeatHandler); //心跳
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpRequestHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(StrConst.SLASH));
        pipeline.addLast(group, accessHandler); //访问权限认证
        pipeline.addLast(group, businessHandler);


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
        SslContext context = SslContextBuilder.forServer(kmf).build();

        SSLEngine engine = context.newEngine(ch.alloc());
        engine.setUseClientMode(false);
        pipeline.addFirst(new SslHandler(engine));
        */
    }

   /* @Bean
    @Profile({Env.TEST, Env.PROD})
    public SslContext createSSLContext() throws Exception {
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
    }*/

}
