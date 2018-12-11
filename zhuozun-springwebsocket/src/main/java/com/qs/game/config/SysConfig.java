package com.qs.game.config;

import com.qs.game.socket.SysWebSocket;

import java.io.Serializable;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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


    //参数初始化
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //核心线程数量大小
    private static final int corePoolSize = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    //线程池最大容纳线程数
    private static final int maximumPoolSize = CPU_COUNT * 2 + 1;
    //线程空闲后的存活时长
    private static final int keepAliveTime = 30;

    //任务过多后，存储任务的一个阻塞队列
    private static final BlockingQueue<Runnable> workQueue = new SynchronousQueue<>();

    //线程的创建工厂
    private static final ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AdvacnedAsyncTask #" + mCount.getAndIncrement());
        }
    };

    //线程池任务满载后采取的任务拒绝策略
    //RejectedExecutionHandler rejectHandler = new ThreadPoolExecutor.DiscardOldestPolicy();

    private static final RejectedExecutionHandler rejectHandler = new ThreadPoolExecutor.DiscardOldestPolicy();

    //线程池对象，创建线程
    public static final ThreadPoolExecutor ROUTER_POOL_EXECUTOR2 = new ThreadPoolExecutor(
            corePoolSize,
            maximumPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            workQueue,
            threadFactory,
            rejectHandler
    );


}
