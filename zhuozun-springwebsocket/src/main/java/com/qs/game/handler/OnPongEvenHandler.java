package com.qs.game.handler;

import com.qs.game.job.SchedulingJob;
import com.qs.game.model.even.Event;
import com.qs.game.model.even.OnPongEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.PongMessage;

import java.nio.ByteBuffer;
import java.util.Date;

/**
 * Created by zun.wei on 2018/11/21 14:07.
 * Description: 心跳包处理类
 */
@Slf4j
@Component
public class OnPongEvenHandler implements EvenHandler {


    @Override
    public void handler(Event event) throws Exception {
        OnPongEvent onPongEven = (OnPongEvent) event;
        String sid = onPongEven.getSid();
        log.info("pong from sid -------::" + sid);
        SchedulingJob.heartBeats.put(sid, new Date().getTime());
        event.getSysWebSocket().sendMessage(new PongMessage(ByteBuffer.allocate(0))); //ping msg
    }

}
