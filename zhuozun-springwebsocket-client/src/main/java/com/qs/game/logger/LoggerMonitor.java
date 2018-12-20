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

    public static void main(String[] args) {
        String inputFile = "E:/data/file/mock.log";
        int sleepInterval = 1000;
        int bufSize = 4096;

        collector = Collector.getCollector()
                .buildWebSocket("ws://localhost:8600/websocket/", "10")
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
        }

    }

    @Override
    public void handle(Exception ex) {

    }


}
