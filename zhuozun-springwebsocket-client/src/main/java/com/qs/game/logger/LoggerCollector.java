package com.qs.game.logger;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by zun.wei on 2018/12/14.
 */
public class LoggerCollector implements Serializable {

    private static String websocketUrl;

    private static String deviceId = "10";

    private static int sleepInterval = 1000;

    private static int bufSize = 4096;

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("need args ! " +
                    "arg0:filePath; " +
                    "arg1:websocket Url; " +
                    "arg2:deviceId; " +
                    "arg3:sleepInterval; " +
                    "arg4:bufSize;");
            System.exit(-1);
            return;
        }

        String inputFile = args[0];
        websocketUrl = args[1];

        if (args.length >= 3) {
            deviceId = args[2];
        }
        if (args.length >= 4) {
            sleepInterval = Integer.parseInt(args[3]);
        }
        if (args.length >= 5) {
            bufSize = Integer.parseInt(args[4]);
        }

        System.out.println("inputFile = " + inputFile);
        System.out.println("websocketUrl = " + websocketUrl);
        System.out.println("deviceId = " + deviceId);
        System.out.println("sleepInterval = " + sleepInterval);
        System.out.println("bufSize = " + bufSize);

        Collector.getCollector()
                .buildWebSocket(websocketUrl, deviceId)
                .initLogFile(inputFile)
                .setLogBatchSize(bufSize)
                .setExecDelay(sleepInterval)
                .readLogFile()
                .connectServer(1)
                .sendLog2Server();

    }


}
