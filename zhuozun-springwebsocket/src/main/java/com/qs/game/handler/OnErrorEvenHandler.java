package com.qs.game.handler;

import com.qs.game.config.SysConfig;
import com.qs.game.model.even.Even;
import com.qs.game.model.even.OnErrorEven;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by zun.wei on 2018/11/21 14:07.
 * Description: 异常报错处理类
 */
@Slf4j
@Component
public class OnErrorEvenHandler implements EvenHandler {


    @Override
    public void handler(Even even) throws Exception {
        OnErrorEven onErrorEven = (OnErrorEven) even;
        Throwable error = onErrorEven.getError();
        String sid = onErrorEven.getSid();
        log.error("OnErrorEvenHandler handler ---- 发生错误 sid:{}, message:{}", sid, error.getMessage());
        //error.printStackTrace();
        SysConfig.WEB_SOCKET_MAP.remove(sid); //从set中删除
    }

}
