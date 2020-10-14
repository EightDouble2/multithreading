package com.johnny.concurrent.concurrent07;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore
 *
 * @author johnnyhao
 */
public class Concurrent07 {

    public static void main(String[] args) {

        // 创建一组许可证，总数为3
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    //获取并阻塞
                    semaphore.acquire();

                    System.out.println(Thread.currentThread().getName() + "acquire");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName() + "release");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放
                    semaphore.release();
                }
            }).start();
        }
    }
}
