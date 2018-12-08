package com.qs.game.interceptor;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * Created by zun.wei on 2018/12/9.
 *  spring websocket 握手拦截器
 */
@Slf4j
public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        log.info("Before Handshake");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            URI uri = servletRequest.getURI();
            String path = uri.getPath();
            path = path.substring(path.lastIndexOf("/") + 1, path.length());
            boolean isNum = StringUtils.isNumeric(path);
            log.info("beforeHandshake isNum = {}", isNum);
            if (!isNum) return false;
        } else {
            return false;
        }
        boolean b = super.beforeHandshake(request, response, wsHandler, attributes);
        log.info("SpringWebSocketHandlerInterceptor b:{}", b);
        return b;

    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }

}
