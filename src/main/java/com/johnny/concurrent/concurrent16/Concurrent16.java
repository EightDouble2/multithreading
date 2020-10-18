package com.johnny.concurrent.concurrent16;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 异步回调
 *
 * @author johnnyhao
 */
public class Concurrent16 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 没有返回值的runAsync异步回调方法
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName());
        });

        System.out.println(Thread.currentThread().getName());

        // 阻塞获取执行结果。
        voidCompletableFuture.get();

        // 有返回值的supplyAsync异步回调方法
        CompletableFuture<Boolean> booleanCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName());

            int i = 1/0;

            // 成功返回值
            return true;
        }).exceptionally((e) -> {
            e.printStackTrace();

            // 异常返回值
            return false;
        });

        System.out.println(Thread.currentThread().getName());

        // 阻塞获取执行结果。
        System.out.println(booleanCompletableFuture.get());
    }
}
