package com.qs.game.model.even;

import com.qs.game.socket.SysWebSocket;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.websocket.Session;
import java.io.Serializable;

/**
 * Created by zun.wei on 2018/11/21 13:47.
 * Description:
 */
@Data
@Accessors(chain = true)
public class Event implements Serializable {

    @Deprecated
    private Session session;

    private String sid;

    private SysWebSocket sysWebSocket;

}
