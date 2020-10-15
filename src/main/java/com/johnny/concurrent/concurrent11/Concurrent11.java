package com.johnny.concurrent.concurrent11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池方法
 *
 * @author johnnyhao
 */
public class Concurrent11 {

    public static void main(String[] args) {
        // 单个线程的线程池
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        // 固定线程数的线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        // 可伸缩大小的线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        try {
            for (int i = 0; i < 5; i++) {
                singleThreadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName());
                });
            }

            for (int i = 0; i < 5; i++) {
                fixedThreadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName());
                });
            }

            for (int i = 0; i < 5; i++) {
                cachedThreadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // 线程池用完手动关闭线程池
            singleThreadPool.shutdown();
            fixedThreadPool.shutdown();
            cachedThreadPool.shutdown();
        }
    }
}
