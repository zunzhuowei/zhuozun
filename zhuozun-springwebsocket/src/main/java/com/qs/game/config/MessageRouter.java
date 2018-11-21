package com.qs.game.config;

import com.qs.game.constant.EvenType;
import com.qs.game.handler.*;
import com.qs.game.model.even.Even;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by zun.wei on 2018/11/21 11:38.
 * Description: message router
 */
@Component
public class MessageRouter implements Serializable {

    @Autowired
    private OnBinaryEvenHandler onBinaryEvenHandler;

    @Autowired
    private OnCloseEvenHandler onCloseEvenHandler;

    @Autowired
    private OnErrorEvenHandler onErrorEvenHandler;

    @Autowired
    private OnOpenEvenHandler onOpenEvenHandler;

    @Autowired
    private OnPongEvenHandler onPongEvenHandler;

    @Autowired
    private OnStrEvenHandler onStrEvenHandler;


    public void router(Even even, EvenType evenType) {
        switch (evenType) {
            case ON_OPEN:
                onOpenEvenHandler.handler(even);
                break;
            case ON_CLOSE:
                onCloseEvenHandler.handler(even);
                break;
            case ON_STR_MESSAGE:
                onStrEvenHandler.handler(even);
                break;
            case ON_BYTE_MESSAGE:
                onBinaryEvenHandler.handler(even);
                break;
            case ON_PONE_MESSAGE:
                onPongEvenHandler.handler(even);
                break;
            case ON_ERROR_MESSAGE:
                onErrorEvenHandler.handler(even);
                break;
            default:
                throw new RuntimeException("no even error");
        }
    }

}
