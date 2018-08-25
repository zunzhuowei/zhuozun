package com.qs.game.initializer;

import com.qs.game.handler.HeartbeatHandler;
import com.qs.game.handler.HttpRequestHandler;
import com.qs.game.handler.TextWebSocketFrameHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.qs.game.common.Constants.URI;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
@Qualifier("businessServerInitializer")
public class BusinessServerInitializer extends ChannelInitializer<Channel> {

    @Autowired
    private TextWebSocketFrameHandler textWebSocketFrameHandler;

    @Autowired
    private HeartbeatHandler heartbeatHandler;

    @Autowired
    private HttpRequestHandler httpRequestHandler;

    @Autowired
    private ChannelGroup channelGroup;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //处理日志
        pipeline.addLast(new LoggingHandler(LogLevel.TRACE));

        //处理心跳
        pipeline.addLast(new IdleStateHandler(0, 0, 1800, TimeUnit.SECONDS));
        pipeline.addLast(heartbeatHandler);

        pipeline.addLast(new HttpServerCodec()); //http编码解码器
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new ChunkedWriteHandler()); //处理大数据传输，支持异步写大数据流，不引起高内存消耗。
//        pipeline.addLast(httpRequestHandler);
        pipeline.addLast(new HttpRequestHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(URI));
//        pipeline.addLast(textWebSocketFrameHandler);
        pipeline.addLast(new TextWebSocketFrameHandler(channelGroup));
    }


    /*
    BinaryWebSocketFrame	二进制数据。
    TextWebSocketFrame	文本数据。
    ContinuationWebSocketFrame	超大文本或者二进制数据。
     */
}
