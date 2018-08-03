package com.qs.game.config;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "memcache")
public class MemcacheConfiguration {

    //@Value("${memcache.servers}")
    private String[] servers;

    //@Value("${memcache.weights}")
    private Integer[] weights;

    //@Value("${memcache.failover}")
    private boolean failover;

    //@Value("${memcache.initConn}")
    private int initConn;

    //@Value("${memcache.minConn}")
    private int minConn;

    //@Value("${memcache.maxConn}")
    private int maxConn;

    //@Value("${memcache.maintSleep}")
    private int maintSleep;

    //@Value("${memcache.nagel}")
    private boolean nagel;

    //@Value("${memcache.socketTO}")
    private int socketTO;

    //@Value("${memcache.aliveCheck}")
    private boolean aliveCheck;

    @Bean
    public SockIOPool sockIOPool () {
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(servers);
        pool.setFailover(failover);
        pool.setInitConn(initConn);
        pool.setMinConn(minConn);
        pool.setMaxConn(maxConn);
        pool.setMaintSleep(maintSleep);
        pool.setNagle(nagel);
        pool.setSocketTO(socketTO);
        pool.setAliveCheck(aliveCheck);
        pool.setWeights(weights);
        pool.initialize();
        return pool;
    }

    @Bean
    public MemCachedClient memCachedClient(){
        return new MemCachedClient();
    }

}
