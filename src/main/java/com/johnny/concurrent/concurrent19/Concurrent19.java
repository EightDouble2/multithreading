package com.johnny.concurrent.concurrent19;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS
 * compareAndSet调用Unsafe类直接对内存进行操作，保证原子性。
 *
 *
 * @author johnnyhao
 */
public class Concurrent19 {

    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(0, 0);

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        // 值与期望相等则更新
        System.out.println(atomicInteger.compareAndSet(0, 1));
        System.out.println(atomicInteger.get());

        // 值与期望不相等则更新失败
        System.out.println(atomicInteger.compareAndSet(0, 1));
        System.out.println(atomicInteger.get());

        // AtomicStampedReference解决ABA问题
        new Thread(() -> {
            // 获得版本号
            System.out.println("A1=>"+atomicStampedReference.getStamp());

            System.out.println(atomicStampedReference.compareAndSet(0, 1, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));

            System.out.println("A2=>"+atomicStampedReference.getStamp());

            System.out.println(atomicStampedReference.compareAndSet(1, 0, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));

            System.out.println("A3=>"+atomicStampedReference.getStamp());

        }, "A").start();

        new Thread(() -> {
            // 获得版本号
            System.out.println("B1=>"+atomicStampedReference.getStamp());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 版本好闻已被修改，更新失败
            System.out.println(atomicStampedReference.compareAndSet(0, 1, 0, atomicStampedReference.getStamp() + 1));

            System.out.println("B2=>"+atomicStampedReference.getStamp());

        }, "B").start();
    }
}
