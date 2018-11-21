package com.qs.game.model.even;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.websocket.PongMessage;
import javax.websocket.Session;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by zun.wei on 2018/11/21 13:47.
 * Description:
 */
@Data
@Accessors(chain = true)
public class Even implements Serializable {

    private Session session;

    private String sid;

}
