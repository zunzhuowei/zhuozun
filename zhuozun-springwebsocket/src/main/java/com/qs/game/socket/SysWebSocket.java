package com.qs.game.socket;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.io.Serializable;

/**
 * Created by zun.wei on 2018/12/9.
 */
@Data
@Accessors(chain = true)
@Slf4j
public class SysWebSocket implements Serializable {

    private Session session;

    //接收sid
    private String sid = "";

    private WebSocketSession webSocketSession;

}
