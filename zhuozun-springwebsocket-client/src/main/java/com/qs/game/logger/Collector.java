package com.qs.game.logger;

import org.apache.commons.lang3.StringUtils;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by zun.wei on 2018/12/14.
 */
@ClientEndpoint
public class Collector implements Serializable {

    private long pointer = 0; //上次文件大小

    private Session session;

    private String wsUrl;

    private String deviceId;

    private int batchSize = 4096; //每个批次发送的字节数

    private Optional<File> logFile;

    private String logStr;

    private long execDelay = 100;

    private static final ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

    public static Collector getCollector() {
        return new Collector();
    }

    public Collector buildWebSocket(String wsUrl, String deviceId) {
        this.wsUrl = wsUrl;
        this.deviceId = deviceId;
        return this;
    }

    public Collector initLogFile(String logFilePath) {
        Function<String, Optional<File>> function = path -> Optional.of(new File(path));
        logFile =  function.apply(logFilePath);
        return this;
    }

    public Collector connectServer(long tryConnectTimes) {
        if (Objects.isNull(wsUrl) || Objects.isNull(deviceId)) throw new RuntimeException("wsUrl or deviceId is null");

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = wsUrl.endsWith("/") ? wsUrl + deviceId : wsUrl + "/" + deviceId;

        System.out.println("Connecting to " + uri
                + ",try connect " + tryConnectTimes + " times");
        try {
            this.session = container.connectToServer(Collector.class, URI.create(uri));
            System.out.println("deviceId " + deviceId + " connect successful");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            connectServer(++tryConnectTimes);
            return this;
        }
        return this;
    }

    public Collector setLogBatchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    /**
     * 毫秒单位
     */
    public Collector setExecDelay(long delay) {
        this.execDelay = delay;
        return this;
    }

    public Collector readLogFile() {
        File logFile = this.logFile.get();
        long len = logFile.length();
        if (this.pointer == 0)
            this.pointer = logFile.length();

        if (len < this.pointer) {
            System.out.println("Log file was reset. Restarting logging from start of file.");
            this.pointer = logFile.length();
            return null;
        }
        StringBuilder result = null;

        try {
            //指定文件可读可写
            RandomAccessFile randomFile = new RandomAccessFile(logFile, "rw");

            //获取RandomAccessFile对象文件指针的位置，初始位置是0
            System.out.println("RandomAccessFile文件指针的初始位置:" + this.pointer);

            randomFile.seek(this.pointer);//移动文件指针位置

            String tmp;
            result = new StringBuilder();
            while ((tmp = randomFile.readLine()) != null) {
                String line = new String(tmp.getBytes("utf-8"));
                result.append(line);
                this.pointer = randomFile.getFilePointer();
                // 只读取指定字节长度的，如果单行已经超过批次长度，尚未解决
                if (result.toString().getBytes().length > this.batchSize) {
                    randomFile.close();
                    this.logStr = result.toString();
                    return this;
                }
            }
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.logStr = Objects.isNull(result) ? null : result.toString();
        return this;
    }

    public void sendLog2Server() {
        exec.scheduleWithFixedDelay(() ->
                {
                    if (Objects.nonNull(this.session) && this.session.isOpen()) {
                        String loggerStr = this.readLogFile().logStr;
                        if (StringUtils.isBlank(loggerStr)) return;
                        try {
                            this.session.getBasicRemote().sendText(loggerStr);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        this.logStr = null;
                    } else {
                        this.connectServer(1);
                    }
                }
                ,0, execDelay, TimeUnit.MILLISECONDS);

    }

    public Session getSession() throws InterruptedException {
        if (Objects.nonNull(this.session) && this.session.isOpen()) {
            return this.session;
        } else {
            Thread.sleep(2000);
            this.connectServer(1);
        }
        return this.session;
    }

}
