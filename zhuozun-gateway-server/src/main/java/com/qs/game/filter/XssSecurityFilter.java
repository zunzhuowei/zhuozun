package com.qs.game.filter;

import com.qs.game.bean.XssHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zun.wei on 2018/8/18.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *  XSS 过滤
 */
@Slf4j
@Component
public class XssSecurityFilter extends OncePerRequestFilter {


    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(request);
        filterChain.doFilter(xssRequest, response);
    }

}
