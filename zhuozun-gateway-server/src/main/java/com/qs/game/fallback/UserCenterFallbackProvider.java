package com.qs.game.fallback;

import com.qs.game.constant.ServiceName;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.stereotype.Component;

/**
 * Created by zun.wei on 2018/8/16.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 *  此处用于zuul 负载均衡时熔断
 *
 */
@Component
public class UserCenterFallbackProvider extends AbstractFallbackProvider implements FallbackProvider{

    public UserCenterFallbackProvider() {
        super.serviceName = ServiceName.USER_CENTER_PROVIDER;
    }

}
