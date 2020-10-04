package com.johnny.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 *
 * @author johnnyhao
 */
public class Thread20 implements Runnable {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(new Thread20());
        executorService.execute(new Thread20());
        executorService.execute(new Thread20());
        executorService.execute(new Thread20());

        executorService.shutdown();
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
