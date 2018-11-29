package com.qs.game.config;

import com.qs.game.socket.server.WebSocketServer;

import java.io.Serializable;
import java.util.concurrent.*;

/**
 * Created by zun.wei on 2018/11/21 10:55.
 * Description: 系统配置
 */
public class SysConfig implements Serializable {

    public static final ExecutorService THREAD_POOL_EXECUTOR =
            new ThreadPoolExecutor(12, 16, 0L,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100000));//CPU核数4-10倍

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //public static final  CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>(new ArrayList<>(102400));

    public static final ConcurrentHashMap<String, WebSocketServer> WEB_SOCKET_MAP = new ConcurrentHashMap<>(10240);

}
