package com.qs.game.config;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimiter;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.RateLimiterErrorHandler;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.RedisRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * Created by zun.wei on 2018/8/18.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Slf4j
@Configuration
public class RateLimiterConfig {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Bean
    public RateLimiter rateLimiter(RateLimiterErrorHandler rateLimiterErrorHandler) {
        return new RedisRateLimiter(rateLimiterErrorHandler, stringRedisTemplate);
    }


    @Bean
    public RateLimiterErrorHandler rateLimiterErrorHandler() {
        return new RateLimiterErrorHandler() {
            @Override
            public void handleSaveError(String key, Exception e) {
                log.error("保存key:[{}]异常", key, e);
            }

            @Override
            public void handleFetchError(String key, Exception e) {
                log.error("路由失败:[{}]异常", key);
            }

            @Override
            public void handleError(String msg, Exception e) {
                log.error("限流异常:[{}]", msg, e);
            }
        };
    }

}
