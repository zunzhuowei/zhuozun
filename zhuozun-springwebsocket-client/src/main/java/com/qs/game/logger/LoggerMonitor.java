package com.qs.game.logger;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;

import java.io.File;
import java.io.IOException;

/**
 * Created by zun.wei on 2018/12/20 18:04.
 * Description:
 */
public class LoggerMonitor implements TailerListener {

    private static Collector collector;

    private static String websocketUrl;

    private static String deviceId = "10";

    private static int sleepInterval = 1000;

    private static int bufSize = 4096;

    public static void main(String[] args) {
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

        collector = Collector.getCollector()
                .buildWebSocket(websocketUrl, deviceId)
                .connectServer(1);

        TailerListener tailerListener = new LoggerMonitor();
        Tailer tailer = new Tailer(new File(inputFile), tailerListener, sleepInterval, true, bufSize);
        tailer.run();
    }


    @Override
    public void init(Tailer tailer) {

    }

    @Override
    public void fileNotFound() {

    }

    @Override
    public void fileRotated() {

    }

    @Override
    public void handle(String line) {
        try {
            collector.getSession().getBasicRemote().sendText(line);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            try {
                collector.getSession().close();
            } catch (IOException | InterruptedException e1) {
                e1.printStackTrace();
            } finally {
                collector = Collector.getCollector()
                        .buildWebSocket(websocketUrl, deviceId)
                        .connectServer(1);
            }
        }

    }

    @Override
    public void handle(Exception ex) {

    }


}
