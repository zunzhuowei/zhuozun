package com.qs.game.websocket2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.InetSocketAddress;

/**
 * Created by zun.wei on 2018/8/25 18:22.
 * Description:
 */
public class WebSocketServer {

    public static void main(String[] args) {
        new WebSocketServer().run("127.0.0.1", 8500);
    }


    public void run(String addr, int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new WebSocketServerHandlerInitializer());

            ChannelFuture f = b.bind(new InetSocketAddress(addr, port)).sync();
            System.out.println("启动服务器:" + f.channel().localAddress());
            //等等服务器端监听端口关闭
            f.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    protected class WebSocketServerHandlerInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            //处理日志
            pipeline.addLast(new LoggingHandler(LogLevel.TRACE));
            pipeline.addLast(new HttpServerCodec());
            pipeline.addLast(new HttpObjectAggregator(64 * 1024));
            pipeline.addLast(new ChunkedWriteHandler());
            pipeline.addLast(new HttpRequestHandler("/ws"));
            pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
            pipeline.addLast(new TextWebSocketFrameHandler());
        }

    }

}
