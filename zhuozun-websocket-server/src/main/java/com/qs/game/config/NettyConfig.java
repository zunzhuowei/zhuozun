package com.qs.game.config;

import com.qs.game.common.Global;
import com.qs.game.constant.Env;
import com.qs.game.service.IRedisService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.net.ssl.KeyManagerFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;

/**
 * Created by zun.wei on 2018/8/24 18:04.
 * Description: netty配置
 */
@Configuration
public class NettyConfig {

    //读取yml中配置
    @Value("${netty.boss.thread.count}")
    private int bossCount;

    @Value("${netty.worker.thread.count}")
    private int workerCount;

    @Value("${netty.websocket.port}")
    private int tcpPort;

    @Autowired
    private ChannelInitializer channelInitializer;

    @Autowired
    private IRedisService redisService;

    //bootstrap配置
    @SuppressWarnings("unchecked")
    @Bean(name = "serverBootstrap")
    public ServerBootstrap bootstrap() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                //标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(channelInitializer);
        return bootstrap;
    }

    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(bossCount);
    }

    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        //源码内部默认取的是 2*n,  N=CPU数量 , 此处注意生产应该以压测结果为准。
        return new NioEventLoopGroup(workerCount);
    }

    /*@Bean(name = "businessGroup", destroyMethod = "close")
    public ChannelGroup businessGroup() {
        return new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    }*/

    @Bean(name = "tcpSocketAddress")
    public InetSocketAddress tcpPort() {
        return new InetSocketAddress(tcpPort);
    }

//    @Bean
//    @Profile({Env.TEST, Env.PROD})
//    public ChannelInitializer<SocketChannel> createSSLChannelInitializer(SecureServerHandlerInitializer secureServerHandlerInitializer) throws Exception {
//        return secureServerHandlerInitializer; //ssl
//    }




//    @Bean
//    @Profile({Env.LOCAL, Env.DEV})
//    public ChannelInitializer<SocketChannel> createChannelInitializer(ServerHandlerInitializer serverHandlerInitializer) {
//        return serverHandlerInitializer;
//    }

}
