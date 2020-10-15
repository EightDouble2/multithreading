package com.johnny.concurrent.concurrent10;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 同步队列
 *
 * @author johnnyhao
 */
public class Concurrent10 {

    public static void main(String[] args) {
        BlockingQueue<String> synchronousQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println("put 1");
                synchronousQueue.put("1");
                System.out.println("put 2");
                synchronousQueue.put("2");
                System.out.println("put 3");
                synchronousQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("take " + synchronousQueue.take());
                TimeUnit.SECONDS.sleep(2);
                System.out.println("take " + synchronousQueue.take());
                TimeUnit.SECONDS.sleep(2);
                System.out.println("take " + synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
