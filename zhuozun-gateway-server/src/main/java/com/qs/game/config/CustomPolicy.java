package com.qs.game.config;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.DefaultRateLimitKeyGenerator;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.RateLimitUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by zun.wei on 2018/8/18.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 * 扩展 策略生成的key,因为默认的策略key 在循环policy-list 的配置时会将当前的url对应的策略 policy 覆盖掉。
 */
@Slf4j
@Configuration
public class CustomPolicy extends DefaultRateLimitKeyGenerator {

    private final RateLimitProperties properties;

    private final RateLimitUtils rateLimitUtils;

    public CustomPolicy(RateLimitProperties properties, RateLimitUtils rateLimitUtils) {
        super(properties, rateLimitUtils);
        log.info("CustomPolicy RateLimitProperties -----:: {}", properties);
        log.info("CustomPolicy RateLimitUtils -----:: {}", rateLimitUtils);
        this.properties = properties;
        this.rateLimitUtils = rateLimitUtils;
    }

    @Override
    public String key(HttpServletRequest request, Route route, RateLimitProperties.Policy policy) {
        // 获取当前 policy 的索引
        int indexOf = properties.getPolicies(route.getId()).indexOf(policy);

        final List<RateLimitProperties.Policy.Type> types = policy.getType().stream().map(RateLimitProperties.Policy.MatchType::getType).collect(Collectors.toList());
        final StringJoiner joiner = new StringJoiner(":");
        joiner.add(properties.getKeyPrefix());
        joiner.add(route.getId());
        if (!types.isEmpty()) {
            if (types.contains(RateLimitProperties.Policy.Type.URL)) {
                joiner.add(route.getPath());
            }
            if (types.contains(RateLimitProperties.Policy.Type.ORIGIN)) {
                joiner.add(rateLimitUtils.getRemoteAddress(request));
            }
            if (types.contains(RateLimitProperties.Policy.Type.USER)) {
                joiner.add(rateLimitUtils.getUser(request));
            }
        }

        // 添加索引
        joiner.add(String.valueOf(indexOf));

        return joiner.toString();

    }

}
