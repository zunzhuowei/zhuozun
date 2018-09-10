package com.qs.game.config.netty;

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
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by zun.wei on 2018/8/29 11:34.
 * Description: ServerHandlerInitializer
 */
@Profile({Env.LOCAL, Env.DEV})
@Component
@Qualifier("serverHandlerInitializer")
public class ServerHandlerInitializer extends ChannelInitializer<SocketChannel> {

//    private final Global global;
//
//    public ServerHandlerInitializer(Global global) {
//        this.global = global;
//    }

    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(16);
    //p.addLast(group, "handler", new HttpHelloWorldServerHandler2());//业务线程独立的线程池

    @Autowired
    private AccessHandler accessHandler;

    @Autowired
    private BusinessHandler businessHandler;

    @Autowired
    private HeartbeatHandler heartbeatHandler;


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //处理日志
        pipeline.addLast(new LoggingHandler(LogLevel.TRACE));
        /*
    保持客户端与服务器端连接的方案常用的有3种
        1.长连接，也就是客户端与服务器端一直保持连接，适用于客户端比较少的情况。
        2.定时段连接，比如在某一天的凌晨建立连接，适用于对实时性要求不高的情况。
        3.设置连接超时，比如超过1分钟没有传输数据就断开连接，等下次需要的时候再建立连接，这种方案比较常用。
         */
        //pipeline.addLast(new ReadTimeoutHandler(120));//服务器端设置超时时间,单位：秒
        //pipeline.addLast(new WriteTimeoutHandler(10));//服务器端设置超时时间,单位：秒
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        //pipeline.addLast(new IdleStateHandler(5, 0, 0));
        pipeline.addLast(group, heartbeatHandler); //心跳
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpRequestHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(StrConst.SLASH));
        pipeline.addLast(group, accessHandler); //访问权限认证
        pipeline.addLast(group, businessHandler);
    }

}
