package com.qs.game.socket;

import com.qs.game.constant.EvenType;
import com.qs.game.handler.Handler;
import com.qs.game.model.even.Even;

import java.io.Serializable;

/**
 * Created by zun.wei on 2018/11/21 11:38.
 * Description: message route
 */
public class MessageRouter implements Serializable {

    public static void route(Even even, EvenType evenType) {
        switch (evenType) {
            case ON_OPEN:
                Handler.getInstance().getOpenEvenHandler().handler(even);
                break;
            case ON_CLOSE:
                Handler.getInstance().getCloseEvenHandler().handler(even);
                break;
            case ON_STR_MESSAGE:
                Handler.getInstance().getStrEvenHandler().handler(even);
                break;
            case ON_BYTE_MESSAGE:
                Handler.getInstance().getBinaryEvenHandler().handler(even);
                break;
            case ON_PONE_MESSAGE:
                Handler.getInstance().getPongEvenHandler().handler(even);
                break;
            case ON_ERROR_MESSAGE:
                Handler.getInstance().getErrorEvenHandler().handler(even);
                break;
            default:
                throw new RuntimeException("no even error");
        }
    }

}
