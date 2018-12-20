package com.qs.game.logger;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by zun.wei on 2018/12/14.
 */
public class LoggerCollector implements Serializable {


    public static void main(String[] args) throws IOException {
        Collector.getCollector()
                .buildWebSocket("ws://localhost:8600/websocket/", "10")
                .initLogFile("E:/data/file/mock.log")
                .setLogBatchSize(4096)
                .setExecDelay(50)
                .readLogFile()
                .connectServer(1)
                .sendLog2Server();

    }


}
