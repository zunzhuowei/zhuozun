package com.qs.game.filter;

import com.auth0.jwt.interfaces.Claim;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.qs.game.auth.JWTUtils;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.constant.SecurityConstants;
import com.qs.game.constant.SystemConst;
import com.qs.game.enum0.Code;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * Zuul大部分功能都是通过过滤器来实现的。Zuul中定义了四种标准过滤器类型，这些过滤器类型对应于请求的典型生命周期。
 * <p>
 * PRE：这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
 * ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
 * POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
 * ERROR：在其他阶段发生错误时执行该过滤器。
 * 除了默认的过滤器类型，Zuul还允许我们创建自定义的过滤器类型。例如，我们可以定制一种STATIC类型的过滤器，直接在Zuul中生成响应，而不将请求转发到后端的微服务。
 */
@Slf4j
@Component
public class AccessFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //优先级，数字越大，优先级越低
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        //是否执行该过滤器，true代表需要过滤
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest servletRequest = ctx.getRequest();
        String requestURI = servletRequest.getRequestURI();
        String lastPath = StringUtils.substringAfterLast(requestURI, "/");
        boolean igonre = StringUtils.equalsIgnoreCase(SystemConst.register, lastPath)
                || StringUtils.equalsIgnoreCase(SystemConst.login, lastPath);
        return !igonre;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest servletRequest = ctx.getRequest();
        String userToken = servletRequest.getParameter(SecurityConstants.DEFAULT_TOKEN_NAME);
        if (Objects.isNull(userToken)) {
            log.warn("access token is empty");
            //过滤该请求，不往下级服务去转发请求，到此结束
            //ctx.setSendZuulResponse(false);
            //ctx.setResponseStatusCode(401);
            ctx.setResponseStatusCode(HttpStatus.OK.value());
            ctx.setResponseBody(BaseResult.getBuilder().setCode(Code.ERROR_4)
                    .setSuccess(false).buildJsonStr());
            return null;
        } else {
            Map<String, Claim> claimMap = JWTUtils.verifyToken(userToken);
            if (Objects.isNull(claimMap)) { //token非法
                ctx.setResponseBody(BaseResult.getBuilder().setCode(Code.ERROR_5)
                        .setSuccess(false).buildJsonStr());
                return null;
            }
            Long uid = JWTUtils.getAppUID(userToken);
            if (Objects.isNull(uid)) {
                ctx.setResponseBody(BaseResult.getBuilder().setCode(Code.ERROR_5)
                        .setSuccess(false).buildJsonStr());
                return null;
            }
        }
        return null;
    }

}
