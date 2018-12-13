package com.qs.game.model.even;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.websocket.CloseReason;

/**
 * Created by zun.wei on 2018/11/21 14:09.
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OnCloseEven extends Even {

    //private CloseReason closeReason;

    private String reason;

}
