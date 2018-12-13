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


    // websocket url path
    public static final String WEBSOCKET_URI_PATH = "/websocket/{sid}";

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

    /**
     * 任务过多后，存储任务的一个阻塞队列
     *
     * ArrayBlockingQueue：是一个用数组实现的有界阻塞队列，此队列按照先进先出（FIFO）的原则对元素进行排序。支持公平锁和非公平锁。
     * 【注：每一个线程在获取锁的时候可能都会排队等待，如果在等待时间上，先获取锁的线程的请求一定先被满足，那么这个锁就是公平的。
     * 反之，这个锁就是不公平的。公平的获取锁，也就是当前等待时间最长的线程先获取锁】
     *
     * LinkedBlockingQueue：一个由链表结构组成的有界队列，此队列的长度为Integer.MAX_VALUE。此队列按照先进先出的顺序进行排序。
     *
     * PriorityBlockingQueue： 一个支持线程优先级排序的无界队列，默认自然序进行排序，也可以自定义实现compareTo()方法来指定元素排序规则，
     * 不能保证同优先级元素的顺序。
     *
     * DelayQueue： 一个实现PriorityBlockingQueue实现延迟获取的无界队列，在创建元素时，可以指定多久才能从队列中获取当前元素。
     * 只有延时期满后才能从队列中获取元素。（DelayQueue可以运用在以下应用场景：1.缓存系统的设计：可以用DelayQueue保存缓存元素的有效期，
     * 使用一个线程循环查询DelayQueue，一旦能从DelayQueue中获取元素时，表示缓存有效期到了。2.定时任务调度。
     * 使用DelayQueue保存当天将会执行的任务和执行时间，一旦从DelayQueue中获取到任务就开始执行，从比如TimerQueue就是使用DelayQueue实现的。）
     *
     * SynchronousQueue： 一个不存储元素的阻塞队列，每一个put操作必须等待take操作，否则不能添加元素。支持公平锁和非公平锁。
     * SynchronousQueue的一个使用场景是在线程池里。Executors.newCachedThreadPool()就使用了SynchronousQueue，
     * 这个线程池根据需要（新任务到来时）创建新的线程，如果有空闲线程则会重复使用，线程空闲了60秒后会被回收。
     *
     * LinkedTransferQueue： 一个由链表结构组成的无界阻塞队列，相当于其它队列，LinkedTransferQueue队列多了transfer和tryTransfer方法。
     *
     * LinkedBlockingDeque： 一个由链表结构组成的双向阻塞队列。队列头部和尾部都可以添加和移除元素，多线程并发时，可以将锁的竞争最多降到一半。
     */
    private static final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

    //线程的创建工厂
    private static final ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AdvacnedAsyncTask #" + mCount.getAndIncrement());
        }
    };

    /**
     * 线程池任务满载后采取的任务拒绝策略
     * 1. CallerRunsPolicy ：这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
     * 2. AbortPolicy ：对拒绝任务抛弃处理，并且抛出异常。
     * 3. DiscardPolicy ：对拒绝任务直接无声抛弃，没有异常信息。
     * 4. DiscardOldestPolicy ：对拒绝任务不抛弃，而是抛弃队列里面等待最久的一个线程，然后把拒绝任务加到队列。
     */
    private static final RejectedExecutionHandler rejectHandler = new ThreadPoolExecutor.CallerRunsPolicy();

    //线程池对象，创建线程
    public static final ThreadPoolExecutor ROUTER_POOL_EXECUTOR = new ThreadPoolExecutor(
            corePoolSize, //核心线程数最大值
            maximumPoolSize,//线程总数最大值
            keepAliveTime,//非核心线程闲置超时时长
            TimeUnit.SECONDS,//非核心线程闲置超时时长 单位
            workQueue, //阻塞任务队列
            threadFactory, //新建线程工厂
            rejectHandler //当提交任务数超过maxmumPoolSize+workQueue之和时，任务会交给RejectedExecutionHandler来处理
    );


}
