package com.johnny.concurrent.concurrent05;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch
 *
 * @author johnnyhao
 */
public class Concurrent05 {

    public static void main(String[] args) throws InterruptedException {

        // 创建计数器，总数5。
        CountDownLatch countDownLatch = new CountDownLatch(5);

        // 执行一次线程计数器减一。
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            }, "Thread" + i).start();
        }

        // 阻塞，等待计数器归零。
        countDownLatch.await();

        System.out.println("End");
    }
}
