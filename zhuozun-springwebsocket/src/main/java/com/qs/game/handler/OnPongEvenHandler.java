package com.qs.game.handler;

import com.qs.game.model.even.Even;
import com.qs.game.model.even.OnPongEven;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by zun.wei on 2018/11/21 14:07.
 * Description: 心跳包处理类
 */
@Slf4j
@Component
public class OnPongEvenHandler implements EvenHandler {


    @Override
    public void handler(Even even) throws Exception {
        OnPongEven onPongEven = (OnPongEven) even;
        String sid = onPongEven.getSid();
        log.info("pong from sid -------::" + sid);
    }

}
