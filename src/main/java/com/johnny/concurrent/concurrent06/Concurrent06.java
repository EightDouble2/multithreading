package com.johnny.concurrent.concurrent06;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier
 *
 * @author johnnyhao
 */
public class Concurrent06 {

    public static void main(String[] args) {

        // 创建计数器，总数5。并配置最后执行的线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            System.out.println("end");
        });

        // 每一次执行线程都阻塞
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "Thread" + i).start();
        }
    }
}
