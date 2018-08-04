package com.qs.game.config;

import com.qs.game.utils.IntUtils;
import lombok.Data;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * memcached 配置
 *
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "memcache")
public class MemcacheConfigurationTcp {

    //@Value("${memcache.servers}")
    private String[] servers;

    //@Value("${memcached.ports}")
    //private int[] ports;

    //@Value("${memcache.connectionPoolSize}")
    private int connectionPoolSize;

    //@Value("${memcache.connectTimeout}")
    private int connectTimeout;

    @Bean("memcachedClient")
    public MemcachedClient getMemcachedClient() throws IOException {
        List<InetSocketAddress> inetSocketAddresses = new ArrayList<>();
        for (int i = 0; i < servers.length; i++) {
            String[] hostPort = servers[i].split(":");
            String host = hostPort[0].trim();
            int port = IntUtils.str2Int(hostPort[1].trim());
            InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
            inetSocketAddresses.add(inetSocketAddress);
        }
        XMemcachedClientBuilder memcachedClientBuilder = new XMemcachedClientBuilder(inetSocketAddresses);
        memcachedClientBuilder.setConnectionPoolSize(connectionPoolSize);
        memcachedClientBuilder.setConnectTimeout(connectTimeout);
        memcachedClientBuilder.setCommandFactory(new BinaryCommandFactory());
        memcachedClientBuilder.setTranscoder(new SerializingTranscoder());
        return memcachedClientBuilder.build();
    }


}
