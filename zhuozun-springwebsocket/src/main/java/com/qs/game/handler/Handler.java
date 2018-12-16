package com.qs.game.handler;

import com.qs.game.constant.CMD;
import com.qs.game.model.even.Even;
import com.qs.game.model.even.OnBinaryEven;
import com.qs.game.model.even.OnStrEven;
import com.qs.game.socket.SysWebSocket;
import com.qs.game.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Optional;

import static com.qs.game.config.SysConfig.WEB_SOCKET_MAP;

/**
 * Created by zun.wei on 2018/11/22 11:35.
 * Description: 事件处理实现类
 */
@Slf4j
public class Handler implements EvenHandler {

    private static OnBinaryEvenHandler binaryEvenHandler;
    private static OnCloseEvenHandler closeEvenHandler;
    private static OnErrorEvenHandler errorEvenHandler;
    private static OnOpenEvenHandler openEvenHandler;
    private static OnPongEvenHandler pongEvenHandler;
    private static OnStrEvenHandler strEvenHandler;

    private static Handler instantce = new Handler();

    private Handler(){}

    /**
     * 获取消息操作类
     * @param even 消息时间
     * @param withCustomProtocol 是否校验协议头
     * @return 消息操作类
     */
    public static Handler getInstance(Even even, boolean withCustomProtocol) {
        if (withCustomProtocol) {
            OnBinaryEven onBinaryEven = even instanceof OnBinaryEven ? ((OnBinaryEven) even) : null;
            if (Objects.nonNull(onBinaryEven)) {
                ByteBuffer byteBuffer = onBinaryEven.getByteBuffer();
                if (Objects.isNull(byteBuffer)) return null;
                ByteBuffer message = byteBuffer.duplicate();

                //TODO check connect sid
                String sid = onBinaryEven.getSid();
                if (!StringUtils.isNumeric(sid)) {
                    log.warn("Handler withCustomProtocol sid :{} is not numeric", sid);
                    return null;
                }


                // check socket
                SysWebSocket sysWebSocket = WEB_SOCKET_MAP.get(sid);
                if (Objects.isNull(sysWebSocket)) {
                    log.warn("Handler withCustomProtocol sysWebSocket is null", sid);
                    return null;
                }

                // check protocol length
                int packHeadLen = message.array().length;
                if (packHeadLen < 4) {
                    log.warn("Handler withCustomProtocol msgLen less than {}", 4);
                    return null;
                }

                // check protocol package head
                char q = DataUtils.getCharByBuffer(message);
                char s = DataUtils.getCharByBuffer(message);
                if (q != 'Q' || s != 'S') {
                    log.warn("Handler withCustomProtocol protocol not equals {},{}", q, s);
                    return null;
                }

                // check protocol package command
                int cmd = DataUtils.getIntByBuffer(message);
                boolean b = CMD.existCmd(cmd);
                if (!b) {
                    log.warn("Handler withCustomProtocol cmd :{} not exist", cmd);
                    return null;
                }


                message.clear();
            }
        }

        //OnStrEven onStrEven = even instanceof OnStrEven ? ((OnStrEven) even) : null;


        //OnCloseEven onCloseEven = even instanceof OnCloseEven ? ((OnCloseEven) even) : null;
        //OnErrorEven onErrorEven = even instanceof OnErrorEven ? ((OnErrorEven) even) : null;
        //OnOpenEven onOpenEven = even instanceof OnOpenEven ? ((OnOpenEven) even) : null;
        //OnPongEven onPongEven = even instanceof OnPongEven ? ((OnPongEven) even) : null;

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
    public void handler(Even even) throws Exception {

    }

}
