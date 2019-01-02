package com.qs.game.model.even;

import com.qs.game.handler.spring.SpringWebSocketSession;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by zun.wei on 2018/11/21 13:47.
 * Description:
 */
@Data
@Accessors(chain = true)
public class Event implements Serializable {

    private String sid;

    private SpringWebSocketSession springWebSocketSession;

    public Event extractSid() {
        if (Objects.nonNull(this.springWebSocketSession)) {
            this.sid = this.springWebSocketSession.getSid();
        }
        return this;
    }

}
