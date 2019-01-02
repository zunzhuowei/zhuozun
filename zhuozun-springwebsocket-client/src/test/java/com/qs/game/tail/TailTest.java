package com.qs.game.tail;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.commons.io.input.TailerListenerAdapter;

import java.io.File;

/**
 * Created by zun.wei on 2018/12/19 12:44.
 * Description:
 */
public class TailTest {

    public static void main(String[] args) {
        TailTest.monitor("E:/data/file/mock.log",10);
    }

    /**
     * @param inputFile     监控文件
     * @param sleepInterval 当文件没有日志时sleep间隔
     */
    private static void monitor(String inputFile, int sleepInterval) {
        TailerListener tailerListener = new TailerListenerAdapter(){
            @Override
            public void handle(String line) {
                System.out.println("line = " + line);
            }
        };
        Tailer tailer = new Tailer(new File(inputFile), tailerListener, sleepInterval, true, 4096);
        tailer.run();
    }
}
