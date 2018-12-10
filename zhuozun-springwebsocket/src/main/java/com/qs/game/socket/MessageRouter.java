package com.qs.game.socket;

import com.qs.game.constant.EvenType;
import com.qs.game.handler.Handler;
import com.qs.game.model.even.Even;

import java.io.Serializable;
import java.util.Objects;

import static com.qs.game.config.SysConfig.ROUTER_POOL_EXECUTOR;

/**
 * Created by zun.wei on 2018/11/21 11:38.
 * Description: message route
 */
public class MessageRouter implements Serializable {

    public static void route(Even even, EvenType evenType) {
        ROUTER_POOL_EXECUTOR.execute(() -> {
            Handler handler = Handler.getInstance(even, false);
            if (Objects.isNull(handler)) return;
            switch (evenType) {
                case ON_OPEN:
                    handler.getOpenEvenHandler().handler(even);
                    break;
                case ON_CLOSE:
                    handler.getCloseEvenHandler().handler(even);
                    break;
                case ON_STR_MESSAGE:
                    handler.getStrEvenHandler().handler(even);
                    break;
                case ON_BYTE_MESSAGE:
                    handler.getBinaryEvenHandler().handler(even);
                    break;
                case ON_PONE_MESSAGE:
                    handler.getPongEvenHandler().handler(even);
                    break;
                case ON_ERROR_MESSAGE:
                    handler.getErrorEvenHandler().handler(even);
                    break;
                default:
                    throw new RuntimeException("no even error");
            }
        });
    }

}
