package com.qs.game.socket.client;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qs.game.socket.TextEncoder;
import com.qs.game.socket.client.msg.HelloProtoBuf;
import com.qs.game.socket.client.msg.LoginProto;
import com.qs.game.socket.client.msg.LoginRespBuf;
import com.qs.game.socket.client.msg.PowerReqProto;
import com.qs.game.utils.ByteUtils;
import com.qs.game.utils.DataUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by zun.wei on 2018/11/19 16:26.
 * Description:
 */
@ClientEndpoint(encoders = {TextEncoder.class})
public class WebSocketClientProtoBuf {

    private static Log log = LogFactory.getLog(WebSocketClientProtoBuf.class);

    private static final ScheduledExecutorService exec2 = Executors.newScheduledThreadPool(20);

    private String deviceId;

    private Session session;

    public WebSocketClientProtoBuf() {
    }

    private static final WebSocketClientProtoBuf[] webSocketClients = new WebSocketClientProtoBuf[20000];

    public WebSocketClientProtoBuf(String deviceId) {
        this.deviceId = deviceId;
    }

    protected boolean start(int c) {//tomcat 1145;nginx
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        //String uri = "ws://127.0.0.1:3654";
        String uri = "ws://127.0.0.1:8600";
        System.out.println("Connecting to " + uri);
        try {
            session = container.connectToServer(WebSocketClientProtoBuf.class, URI.create(uri));
            webSocketClients[c] = this;
            //list.add(session);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // protoc  --java_out=. Hello.proto
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        WebSocketClientProtoBuf wSocketTest = new WebSocketClientProtoBuf(String.valueOf(0));
        wSocketTest.start(0);

        // test protoBuf
        //webSocketClients[0].session.getBasicRemote().sendBinary(ByteBuffer.wrap(getProtoBuf()), true);
        //webSocketClients[0].session.getBasicRemote().sendBinary(ByteBuffer.wrap(getProtoBuf2()), true);
        webSocketClients[0].session.getBasicRemote().sendBinary(ByteBuffer.wrap(getProtoBuf3()), true);
        Thread.sleep(5000);
    }

    private static byte[] getProtoBuf() throws InvalidProtocolBufferException {
        HelloProtoBuf.Hello.Builder builder = HelloProtoBuf.Hello.newBuilder();
        builder.setName("java websocket");
        HelloProtoBuf.Hello hello = builder.build();
        byte[] buf = hello.toByteArray();

        // test parse buf
        HelloProtoBuf.Hello parseHello = HelloProtoBuf.Hello.parseFrom(buf);
        System.out.println("parseHello getName = " + parseHello.getName());

        //-------------------------
        //| id | protobuf message |
        //-------------------------
        return ByteUtils.beginBuild().append((short)0).append(buf).buildByteArr();
    }

    private static byte[] getProtoBuf2() throws InvalidProtocolBufferException {
        LoginProto.Login.Builder builder = LoginProto.Login.newBuilder();
        builder.setMid(111);
        builder.setSCode((int) new Date().getTime());
        builder.setSKey("0111-aa-xxx-ddd");
        LoginProto.Login login = builder.build();
        byte[] buf = login.toByteArray();

        // test parse buf
        LoginProto.Login parseLogin = LoginProto.Login.parseFrom(buf);
        System.out.println("parseLogin = " + parseLogin);

        //-------------------------
        //| id | protobuf message |
        //-------------------------
        return ByteUtils.beginBuild().append((short)1).append(buf).buildByteArr();
    }

    private static byte[] getProtoBuf3() throws InvalidProtocolBufferException {
        PowerReqProto.PowerReqProtocol.Builder builder = PowerReqProto.PowerReqProtocol.newBuilder();
        builder.setRequestId(111);
        builder.setType(1);
        builder.setReqMsg("websocket client");

        PowerReqProto.PowerReqProtocol reqProtocol = builder.build();
        byte[] buf = reqProtocol.toByteArray();

        // test parse buf
        PowerReqProto.PowerReqProtocol powerReqProtocol =  PowerReqProto.PowerReqProtocol.parseFrom(buf);
        System.out.println("powerReqProtocol = " + powerReqProtocol);

        //-------------------------
        //| id | protobuf message |
        //-------------------------
        return ByteUtils.beginBuild().append(buf).buildByteArr();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口服务端" + "的信息:" + message);
    }


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        log.debug("连接建立成功调用的方法 ---::");
        session.getBasicRemote().sendPong(ByteBuffer.allocate(0));
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
    public void onMessage(ByteBuffer message, Session session) throws InvalidProtocolBufferException {
        short q0 = DataUtils.getShortByBuffer(message);
        System.out.println("q0 = " + q0);
        LoginRespBuf.LoginResp loginResp = LoginRespBuf.LoginResp.parseFrom(message);
        System.out.println("loginResp = " + loginResp);

        message.clear();
    }

    /**
     *  服务器心跳
     * @param pongMessage
     * @param session
     */
    @OnMessage
    public void onMessage(PongMessage pongMessage, Session session) throws IOException, InterruptedException {
        Thread.sleep(3000);
        session.getBasicRemote().sendPong(ByteBuffer.allocate(0));
    }

    /**
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

}
