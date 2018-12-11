package com.qs.game.config;

import com.qs.game.socket.SysWebSocket;
import com.qs.game.socket.server.WebSocketServer;

import java.io.Serializable;
import java.util.concurrent.*;

/**
 * Created by zun.wei on 2018/11/21 10:55.
 * Description: 系统配置
 */
public class SysConfig implements Serializable {

//    public static final ExecutorService THREAD_POOL_EXECUTOR =
//            //(1)核心线程数最大值;(2)线程总数最大值;(3)非核心线程闲置超时时长;(4)keepAliveTime的单位;(5)
//            new ThreadPoolExecutor(10, 18, 0L,
//                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());//CPU核数4-10倍

    // 消息路由线程池
    public static final ExecutorService ROUTER_POOL_EXECUTOR =
            //(1)核心线程数最大值;(2)线程总数最大值;(3)非核心线程闲置超时时长;(4)keepAliveTime的单位;(5)
            new ThreadPoolExecutor(16, 24, 0L,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());//CPU核数4-10倍

    //存储在线玩家session
    public static final ConcurrentHashMap<String, SysWebSocket> WEB_SOCKET_MAP = new ConcurrentHashMap<>(40960);

}
