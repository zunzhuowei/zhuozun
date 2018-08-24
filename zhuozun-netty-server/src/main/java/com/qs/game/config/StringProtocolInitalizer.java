package com.qs.game.config;

import com.qs.game.handler.ServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * Created by zun.wei on 2018/8/24 18:11.
 * Description: 字符串协议标识符
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
@Qualifier("springProtocolInitializer")
public class StringProtocolInitalizer extends ChannelInitializer<SocketChannel> {

    @Autowired
    StringDecoder stringDecoder;

    @Autowired
    StringEncoder stringEncoder;

    @Autowired
    ServerHandler serverHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("decoder", stringDecoder); //解码器
        pipeline.addLast("handler", serverHandler); //配置通道来业务处理
        pipeline.addLast("encoder", stringEncoder); //编码器
        // 进行设置心跳检测
        pipeline.addLast(new IdleStateHandler
                (60, 30, 60 * 30, TimeUnit.SECONDS));

    }



}
