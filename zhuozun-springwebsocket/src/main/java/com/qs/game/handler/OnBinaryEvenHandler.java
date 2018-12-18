package com.qs.game.handler;

import com.qs.game.handler.spring.SpringWebSocketSession;
import com.qs.game.handler.spring.WebSocketSender;
import com.qs.game.model.even.Event;
import com.qs.game.model.even.OnBinaryEvent;
import com.qs.game.utils.ByteUtils;
import com.qs.game.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

import static com.qs.game.config.SysConfig.WEB_SOCKET_MAP;

/**
 * Created by zun.wei on 2018/11/21 14:07.
 * Description: 二进制消息事件处理类
 */
@Slf4j
@Component
public class OnBinaryEvenHandler implements EvenHandler {


    @Override
    public void handler(Event event) throws Exception{
        OnBinaryEvent onBinaryEven = (OnBinaryEvent) event;
        ByteBuffer message = onBinaryEven.getByteBuffer();
        ByteBuffer dup = message.duplicate();

        String sid = onBinaryEven.getSid();
        SpringWebSocketSession springWebSocketSession = onBinaryEven.getSpringWebSocketSession();

        char q = DataUtils.getCharByBuffer(message);
        char s = DataUtils.getCharByBuffer(message);

//        System.out.println("OnBinaryEvenHandler handler sid =========================== " + sid);
//
//        System.out.println("q = " + q);
//        System.out.println("s = " + s);

        int cmd = DataUtils.getIntByBuffer(message);
        int strLen = DataUtils.getIntByBuffer(message);
        String str = DataUtils.getStrByBuffer(message, strLen);
        int mid = DataUtils.getIntByBuffer(message);
        char p1 = DataUtils.getCharByBuffer(message);
        int telLen = DataUtils.getIntByBuffer(message);
        String tel = DataUtils.getStrByBuffer(message, telLen);

//        System.out.println("mid = " + mid);
//        System.out.println("p1 = " + p1);
//        System.out.println("cmd = " + cmd);
//        System.out.println("str = " + str);
//        System.out.println("tel = " + tel);

        //WebSocketSender.sendMessage(springWebSocketSession, ByteUtils.beginBuild().append(q).append(s)
         //       .append(Integer.parseInt(sid)).append(WEB_SOCKET_MAP.size()).buildByteArr());

        message.clear();
    }

    // web request

    /*@Override
    public void handler(Event even) throws Exception{
        OnBinaryEvent onBinaryEven = (OnBinaryEvent) even;
        ByteBuffer message = onBinaryEven.getByteBuffer();
        ByteBuffer dup = message.duplicate();

        String sid = onBinaryEven.getSid();
        SysWebSocket sysWebSocket = onBinaryEven.getSysWebSocket();

        char q = DataUtils.getCharByBuffer(message,true);
        char s = DataUtils.getCharByBuffer(message,true);

        System.out.println("q = " + q);
        System.out.println("s = " + s);

        int cmd = DataUtils.getIntByBuffer(message,true);
        int strLen = DataUtils.getIntByBuffer(message,true);
        String str = DataUtils.getStrByBuffer(message, strLen);
        int mid = DataUtils.getIntByBuffer(message,true);
        char p1 = DataUtils.getCharByBuffer(message,true);
        int telLen = DataUtils.getIntByBuffer(message,true);
        String tel = DataUtils.getStrByBuffer(message, telLen);

        System.out.println("mid = " + mid);
        System.out.println("p1 = " + p1);
        System.out.println("cmd = " + cmd);
        System.out.println("str = " + str);
        System.out.println("tel = " + tel);

        sysWebSocket.sendMessage(dup);

        message.clear();
    }*/

}
