package com.qs.game.config;

import com.qs.game.common.Global;
import com.qs.game.constant.StrConst;
import com.qs.game.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.ImmediateEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

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
    private Global global;


    //bootstrap配置
    @SuppressWarnings("unchecked")
    @Bean(name = "serverBootstrap")
    public ServerBootstrap bootstrap() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024) //消息队列容量
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new WebSocketServerHandlerInitializer());
        return bootstrap;
    }

    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(bossCount);
    }

    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(workerCount);
    }

    @Bean(name = "businessGroup", destroyMethod = "close")
    public ChannelGroup businessGroup() {
        return new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    }

    @Bean(name = "tcpSocketAddress")
    public InetSocketAddress tcpPort() {
        return new InetSocketAddress(tcpPort);
    }



    protected class WebSocketServerHandlerInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            //处理日志
            pipeline.addLast(new LoggingHandler(LogLevel.TRACE));
            pipeline.addLast(new HttpServerCodec());
            pipeline.addLast(new HttpObjectAggregator(64 * 1024));
            pipeline.addLast(new HeartbeatHandler()); //心跳
            pipeline.addLast(new ChunkedWriteHandler());
            pipeline.addLast(new HttpRequestHandler(StrConst.SLASH));
            pipeline.addLast(new WebSocketServerProtocolHandler(StrConst.SLASH));
            pipeline.addLast(new AuthHandler(global)); //访问权限认证
            pipeline.addLast(new TextWebSocketFrameHandler2());
        }

    }

}
