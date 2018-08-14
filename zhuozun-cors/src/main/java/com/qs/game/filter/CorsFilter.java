package com.qs.game.filter;

import com.qs.game.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by zun.wei on 2018/8/14 12:22.
 * Description:  跨域访问处理(跨域资源共享)  解决前后端分离架构中的跨域问题
 */
@Slf4j
public class CorsFilter implements Filter {


    private String allowOrigin;
    private String allowMethods;
    private String allowCredentials;
    private String allowHeaders;
    private String exposeHeaders;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowOrigin = filterConfig.getInitParameter("allowOrigin");
        allowMethods = filterConfig.getInitParameter("allowMethods");
        allowCredentials = filterConfig.getInitParameter("allowCredentials");
        allowHeaders = filterConfig.getInitParameter("allowHeaders");
        exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
    }


    /**
     * @description 通过CORS技术实现AJAX跨域访问,只要将CORS响应头写入response对象中即可
     * @author rico
     * @created 2017年7月4日 下午5:02:38
     * @param req
     * @param res
     * @param chain
     * @throws IOException
     * @throws ServletException
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("9999 = " + 9999);
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String currentOrigin = request.getHeader(HttpHeaders.ORIGIN);
        System.out.println("\"null\".equals(currentOrigin) = " + "null".equals(currentOrigin));
        log.info("currentOrigin : " + currentOrigin);
        if (StringUtils.isNotEmpty(allowOrigin)) {
            if (StringUtils.equals("*", allowOrigin)) {
                response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, currentOrigin);
            } else {
                List<String> allowOriginList = Arrays.asList(allowOrigin.split(","));
                log.info("allowOriginList : " + allowOrigin);

                if (CollectionUtil.isNotEmpty(allowOriginList)) {
                    System.out.println("allowOriginList = " + allowOriginList);
                    if (allowOriginList.contains(currentOrigin)) {
                        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, currentOrigin);
                    }
                }
            }
        }
        if (StringUtils.isNotEmpty(allowMethods)) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, allowMethods);
        }
        if (StringUtils.isNotEmpty(allowCredentials)) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, allowCredentials);
        }
        if (StringUtils.isNotEmpty(allowHeaders)) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, allowHeaders);
        }
        if (StringUtils.isNotEmpty(exposeHeaders)) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, exposeHeaders);
        }
        chain.doFilter(req, res);
        return;
    }

    @Override
    public void destroy() {}

    public static boolean isCorsRequest(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader(HttpHeaders.ORIGIN));
    }

    /*
    Access-Control-Allow-Origin：允许访问的客户端域名，例如：http://web.xxx.com，若为*，则表示从任意域都能访问，即不做任何限制；

    Access-Control-Allow-Methods：允许访问的方法名，多个方法名用逗号分割，例如：GET,POST,PUT,DELETE,OPTIONS；

    Access-Control-Allow-Credentials：是否允许请求带有验证信息，若要获取客户端域下的cookie时，需要将其设置为true；

    Access-Control-Allow-Headers：允许服务端访问的客户端请求头，多个请求头用逗号分割，例如：Content-Type；

    Access-Control-Expose-Headers：允许客户端访问的服务端响应头，多个响应头用逗号分割。
     */

}
