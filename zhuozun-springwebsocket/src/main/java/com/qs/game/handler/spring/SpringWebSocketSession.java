package com.qs.game.handler.spring;

import com.qs.game.socket.SysWebSocket;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by zun.wei on 2018/12/9.
 *
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@Accessors(chain = true)
public class SpringWebSocketSession extends SysWebSocket {

    private WebSocketSession webSocketSession;

    //接收sid
    private String sid;



}
