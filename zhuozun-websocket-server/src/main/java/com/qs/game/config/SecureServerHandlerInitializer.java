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
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLEngine;

/**
 * Created by zun.wei on 2018/8/29 11:38.
 * Description: ssl handler
 */
@Profile({Env.TEST, Env.PROD})
@Component
@Qualifier("secureServerHandlerInitializer")
public class SecureServerHandlerInitializer extends ChannelInitializer<SocketChannel> {


    @Autowired
    private SslContext context;

    @Autowired
    private Global global;


//    public SecureServerHandlerInitializer(Global global, SslContext context) {
//        this.global = global;
//        this.context = context;
//    }

    @Autowired
    private AccessHandler accessHandler;

    @Autowired
    private BusinessHandler businessHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //处理日志
        pipeline.addLast(new LoggingHandler(LogLevel.TRACE));
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new HeartbeatHandler()); //心跳
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpRequestHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(StrConst.SLASH));
        pipeline.addLast(accessHandler); //访问权限认证
        pipeline.addLast(businessHandler);

        SSLEngine engine = context.newEngine(ch.alloc());
        engine.setUseClientMode(false);
        pipeline.addFirst(new SslHandler(engine));
    }

}
