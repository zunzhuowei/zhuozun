package com.qs.game.handler;

import com.qs.game.model.even.Even;

import java.util.Optional;

/**
 * Created by zun.wei on 2018/11/22 11:35.
 * Description: 事件处理实现类
 */
public class Handler implements EvenHandler {

    private static OnBinaryEvenHandler binaryEvenHandler;
    private static OnCloseEvenHandler closeEvenHandler;
    private static OnErrorEvenHandler errorEvenHandler;
    private static OnOpenEvenHandler openEvenHandler;
    private static OnPongEvenHandler pongEvenHandler;
    private static OnStrEvenHandler strEvenHandler;

    private static Handler instantce = new Handler();

    private Handler(){}

    public static Handler getInstance() {
        return instantce;
    }

    public OnBinaryEvenHandler getBinaryEvenHandler() {
        return Optional.ofNullable(binaryEvenHandler).orElseGet(() -> {
                    binaryEvenHandler = this.getOnBinaryEvenHandler();
                    return binaryEvenHandler;
                });
    }

    public OnCloseEvenHandler getCloseEvenHandler() {
        return Optional.ofNullable(closeEvenHandler).orElseGet(() -> {
                    closeEvenHandler = this.getOnCloseEvenHandler();
                    return closeEvenHandler;
                });
    }

    public OnErrorEvenHandler getErrorEvenHandler() {
        return Optional.ofNullable(errorEvenHandler).orElseGet(() -> {
            errorEvenHandler = this.getOnErrorEvenHandler();
            return errorEvenHandler;
        });
    }

    public OnOpenEvenHandler getOpenEvenHandler() {
        return Optional.ofNullable(openEvenHandler).orElseGet(() -> {
            openEvenHandler = this.getOnOpenEvenHandler();
            return openEvenHandler;
        });
    }

    public OnPongEvenHandler getPongEvenHandler() {
        return Optional.ofNullable(pongEvenHandler).orElseGet(() -> {
            pongEvenHandler = this.getOnPongEvenHandler();
            return pongEvenHandler;
        });
    }

    public OnStrEvenHandler getStrEvenHandler() {
        return Optional.ofNullable(strEvenHandler).orElseGet(() -> {
            strEvenHandler = this.getOnStrEvenHandler();
            return strEvenHandler;
        });
    }

    @Override
    public void handler(Even even) {

    }

}
