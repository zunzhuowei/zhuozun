package com.qs.game.socket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * Created by zun.wei on 2018/8/24 18:16.
 * Description: Server
 */
@Data
@Component
@Slf4j
public class Server {

    private final ServerBootstrap serverBootstrap;

    private final InetSocketAddress inetSocketAddress;

    private ChannelFuture channelFuture;

    @Autowired
    public Server(@Qualifier("serverBootstrap") ServerBootstrap serverBootstrap,
                  @Qualifier("tcpSocketAddress") InetSocketAddress inetSocketAddress) {
        this.serverBootstrap = serverBootstrap;
        this.inetSocketAddress = inetSocketAddress;
    }

    @PostConstruct
    public void start() throws Exception {
        try {
            channelFuture = serverBootstrap.bind(inetSocketAddress).syncUninterruptibly();
            log.info("Starting server at {} , local address is {}", inetSocketAddress,
                    channelFuture.channel().localAddress());
        } catch (Exception e) {
            log.warn(e.toString());
            //e.printStackTrace();
        }
    }

    @PreDestroy
    public void stop() throws Exception {
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }

}
