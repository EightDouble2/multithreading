package com.johnny.concurrent.concurrent17;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Volatile
 * 保证可见性
 * 不保证原则性
 * 禁止指令重排
 *
 * @author johnnyhao
 */
public class Concurrent17 {

    public static void main(String[] args) {
        // 保证可见性
        new Test01().test();
        // 不保证原则性
        new Test02().test();
    }
}

/**
 * 保证可见性
 * 不加volatile关键字程序会死循环。
 * 加volatile关键字可以保证可见性。
 */
class Test01 {

    private volatile int num = 0;

    public void test() {
        new Thread(() -> {
            while (num == 0) {

            }

            System.out.println(Thread.currentThread().getName() + " end");
        }).start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        num = 1;

        System.out.println(num);
    }
}

/**
 * 不保证原则性
 * 结果应为200000。
 */
class Test02 {

    private volatile int num = 0;
    private volatile AtomicInteger atomicNum = new AtomicInteger(0);

    public void test() {

        // num++不是原子性操作。
        for (int i = 0; i < 200; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    num++;
                }
            }).start();
        }

        // 判断所有线程结束，仅剩main线程和gc线程。
        while (Thread.activeCount() > 2){
            Thread.yield();
        }

        System.out.println(num);

        // 要保证该程序的原子性，可以用并发原子类操作。
        // 原子类直接对操作系统进行操作，在内存中修改值。
        for (int i = 0; i < 200; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicNum.getAndIncrement();
                }
            }).start();
        }

        // 判断所有线程结束，仅剩main线程和gc线程。
        while (Thread.activeCount() > 2){
            Thread.yield();
        }

        System.out.println(atomicNum);
    }
}

