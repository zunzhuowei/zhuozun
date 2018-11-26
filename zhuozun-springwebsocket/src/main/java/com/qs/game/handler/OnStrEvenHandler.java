package com.qs.game.handler;

import com.qs.game.model.even.Even;
import com.qs.game.model.even.OnStrEven;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by zun.wei on 2018/11/21 14:07.
 * Description: 字符串消息处理类
 */
@Slf4j
@Component
public class OnStrEvenHandler implements EvenHandler {


    @Override
    public void handler(Even even) {
        OnStrEven onStrEven = (OnStrEven) even;
        String sid = onStrEven.getSid();
        String message = onStrEven.getMessage();
        log.info("OnStrEvenHandler handler 收到来自窗口{}的信息:{}", sid, message);
    }

}
