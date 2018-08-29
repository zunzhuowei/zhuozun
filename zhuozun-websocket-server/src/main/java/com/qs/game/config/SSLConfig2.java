package com.qs.game.config;

import com.qs.game.common.Global;
import com.qs.game.constant.Env;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.ApplicationProtocolNegotiator;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSessionContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.List;

/**
 * Created by zun.wei on 2018/8/29 17:50.
 * Description:
 */
@Profile({Env.LOCAL, Env.DEV})
@Configuration
public class SSLConfig2 {

    @Bean
    public SslContext initSSLContextDevLocal() throws Exception {
        return new SslContext() {
            @Override
            public boolean isClient() {
                return false;
            }

            @Override
            public List<String> cipherSuites() {
                return null;
            }

            @Override
            public long sessionCacheSize() {
                return 0;
            }

            @Override
            public long sessionTimeout() {
                return 0;
            }

            @Override
            public ApplicationProtocolNegotiator applicationProtocolNegotiator() {
                return null;
            }

            @Override
            public SSLEngine newEngine(ByteBufAllocator alloc) {
                return null;
            }

            @Override
            public SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
                return null;
            }

            @Override
            public SSLSessionContext sessionContext() {
                return null;
            }
        };
    }

    @Bean
    public ChannelInitializer<SocketChannel> createInitializer(Global global, SslContext context) {
        return new ServerHandlerInitializer(global);
    }

}
