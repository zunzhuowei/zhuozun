package com.qs.game.logger;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.URI;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * Created by zun.wei on 2018/12/5 15:03.
 * Description: logger 收集者
 */
@ClientEndpoint
@Slf4j
@Component
public class LoggerCollector {

    @Value("${LoggerCollector.deviceId}")
    private String deviceId;

    @Value("${LoggerCollector.batchSize}")
    private int batchSize;

    @Value("${LoggerCollector.wsUrl}")
    private String wsUrl = "ws://localhost:8600/websocket/";

    @Value("${LoggerCollector.loggerPath}")
    private String loggerPath;

    private static Session session;

    private static long pointer = 0; //上次文件大小

    private static File tmpLogFile;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");


    @Scheduled(fixedDelay = 500)//5000
    public void executeCollectLogger() {
        log.debug("SchedulingJobTest =====>>>>>使用cron  {}", System.currentTimeMillis());
        if (Objects.nonNull(session) && session.isOpen()) {
            if (Objects.isNull(tmpLogFile)) tmpLogFile = new File(loggerPath);
            String loggerStr = this.readLogFile(tmpLogFile);
            if (StringUtils.isBlank(loggerStr)) return;
            sendTextMessageAsync(loggerStr, 1000);
        } else {
            this.connectServer(1);
        }
    }

    private String readLogFile(File logFile) {
        //获得变化部分
        try {
            long len = logFile.length();
            if (len < pointer) {
                log.info("Log file was reset. Restarting logging from start of file.");
                pointer = 0;
                return null;
            } else {
                //指定文件可读可写
                RandomAccessFile randomFile = new RandomAccessFile(logFile, "rw");

                //获取RandomAccessFile对象文件指针的位置，初始位置是0
                log.info("RandomAccessFile文件指针的初始位置:{}", pointer);

                randomFile.seek(pointer);//移动文件指针位置

                String tmp;
                StringBuilder result = new StringBuilder();
                while ((tmp = randomFile.readLine()) != null) {
                    String line = new String(tmp.getBytes("utf-8"));
                    log.info("line ----------::{}", line);
                    result.append(line);
                    pointer = randomFile.getFilePointer();
                    if (result.toString().getBytes().length > batchSize) {
                        randomFile.close();
                        return result.toString();
                    }
                }
                randomFile.close();
                return result.toString();
            }
        } catch (Exception e) {
            //实时读取日志异常，需要记录时间和lastTimeFileSize 以便后期手动补充
            log.error(dateFormat.format(new Date()) + " File read error, pointer: " + pointer);
            return null;
        } finally {
            //将pointer 落地以便下次启动的时候，直接从指定位置获取
        }
    }


    private boolean connectServer(long tryConnectTimes) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        //String uri = "ws://localhost:8600/websocket/" + deviceId;
        String uri = wsUrl + deviceId;
        log.info("Connecting to {},try connect {} times", uri, tryConnectTimes);
        try {
            session = container.connectToServer(LoggerCollector.class, URI.create(uri));
            log.info("deviceId {} connect successful", deviceId);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            connectServer(++tryConnectTimes);
            return false;
        }
        return true;
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口服务端" + "的信息:" + message);
    }


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        log.debug("连接建立成功调用的方法 ---::");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.debug("连接关闭调用的方法v ---::");
    }

    /**
     *  接收服务端 二进制格式请求数据
     */
    @OnMessage
    public void onMessage(ByteBuffer message, Session session) {
        log.debug("接收服务端 二进制格式请求数据 -----------");
    }

    /**
     *  服务器心跳
     * @param pongMessage
     * @param session
     */
    @OnMessage
    public void onMessage(PongMessage pongMessage, Session session) {
        System.out.println("pongMessage = " + pongMessage);
    }

    /**
     */
    @OnError
    public void onError(Session session, Throwable error) throws IOException {
        log.error("发生错误");
        error.printStackTrace();
        session.close(new CloseReason(CloseReason.CloseCodes.NO_STATUS_CODE, error.getMessage()));
    }





    /**
     *  Send the message, blocking until the message is sent.
     */
    public static void sendTextMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    /**
     *  Send the message, blocking until the message is sent.
     */
    public static void sendBinaryMessage(byte[] message) throws IOException {
        session.getBasicRemote().sendBinary(ByteBuffer.wrap(message));
    }

    /**
     *  Send the message, blocking until the message is sent.
     *  Encodes object as a message and sends it to the remote endpoint.
     */
    public static void sendObjectMessage(Serializable object) throws IOException, EncodeException {
        session.getBasicRemote().sendObject(object);
    }






    /**
     * Send the message asynchronously
     */
    public static Future<Void> sendTextMessageAsync(String message) {
        return session.getAsyncRemote().sendText(message);
    }

    /**
     * Send the message asynchronously
     */
    public static Future<Void> sendTextMessageAsync(String message, long timeout) {
        RemoteEndpoint.Async async = session.getAsyncRemote();
        async.setSendTimeout(timeout);
        return async.sendText(message);
    }

    /**
     * @param completion Used to signal to the client when the message has been sent
     */
    public static void sendTextMessageAsync(String message, SendHandler completion) {
        session.getAsyncRemote().sendText(message, completion);
    }

    /**
     *
     * @param completion Used to signal to the client when the message has been sent
     * @param timeout The new timeout for sending messages asynchronously
     *                 in milliseconds. A non-positive value means an
     *                 infinite timeout.
     */
    public static void sendTextMessageAsync(String message, SendHandler completion, long timeout) {
        RemoteEndpoint.Async async = session.getAsyncRemote();
        async.setSendTimeout(timeout);
        async.sendText(message, completion);
    }


    public static Future<Void> sendObjectMessageAsync(Serializable object) {
        return session.getAsyncRemote().sendObject(object);
    }

    public static void sendObjectMessageAsync(Serializable object, SendHandler completion) {
        session.getAsyncRemote().sendObject(object, completion);
    }

    public static Future<Void> sendObjectMessageAsync(Serializable object,long timeout) {
        RemoteEndpoint.Async async = session.getAsyncRemote();
        async.setSendTimeout(timeout);
        return async.sendObject(object);
    }

    public static void sendObjectMessageAsync(Serializable object, SendHandler completion,long timeout) {
        RemoteEndpoint.Async async = session.getAsyncRemote();
        async.setSendTimeout(timeout);
        async.sendObject(object, completion);
    }








    /**
     * Send the message asynchronously
     */
    public static Future<Void> sendBinaryMessageAsync(byte[] message) {
        return session.getAsyncRemote().sendBinary(ByteBuffer.wrap(message));
    }

    /**
     * Send the message asynchronously
     */
    public static Future<Void> sendBinaryMessageAsync(byte[] message, long timeout) {
        RemoteEndpoint.Async async = session.getAsyncRemote();
        async.setSendTimeout(timeout);
        return async.sendBinary(ByteBuffer.wrap(message));
    }

    /**
     * @param completion Used to signal to the client when the message has been sent
     */
    public static void sendBinaryMessageAsync(byte[] message, SendHandler completion) {
        session.getAsyncRemote().sendBinary(ByteBuffer.wrap(message), completion);
    }

    /**
     *
     * @param completion Used to signal to the client when the message has been sent
     * @param timeout The new timeout for sending messages asynchronously
     *                 in milliseconds. A non-positive value means an
     *                 infinite timeout.
     */
    public static void sendBinaryMessageAsync(byte[] message, SendHandler completion, long timeout) {
        RemoteEndpoint.Async async = session.getAsyncRemote();
        async.setSendTimeout(timeout);
        async.sendBinary(ByteBuffer.wrap(message), completion);
    }


}
