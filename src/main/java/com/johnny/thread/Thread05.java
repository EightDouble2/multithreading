package com.johnny.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 创建线程方式三：实现Callable接口
 *
 * @author johnnyhao
 */
public class Thread05 implements Callable<Boolean> {
    /**
     * 实现call方法
     */
    public Boolean call() {
        // call方法线程体
        for (int i = 0; i <= 100; i++) {
            System.out.println("多线程" + Thread.currentThread().getName() + "：" + i);

            // 休眠
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 多线程
        Thread05 thread05 = new Thread05();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Boolean> result1 = executorService.submit(thread05);
        Future<Boolean> result2 = executorService.submit(thread05);
        boolean r1 = result1.get();
        boolean r2 = result2.get();
        executorService.shutdownNow();

        // 主线程
        for (int i = 0; i <= 100; i++) {
            System.out.println("主线程：" + i);

            // 休眠
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
