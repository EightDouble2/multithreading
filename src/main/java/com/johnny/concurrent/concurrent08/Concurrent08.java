package com.johnny.concurrent.concurrent08;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock
 *
 * @author johnnyhao
 */
public class Concurrent08 {

    public static void main(String[] args) throws InterruptedException {

        // 普通方法同时写入引发并发问题
        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.write(temp, temp);
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.read(temp);
            }, String.valueOf(i)).start();
        }

        TimeUnit.SECONDS.sleep(2);
        System.out.println();

        // 读写锁方法同时写入时独占资源，读取是共享资源。
        MyCacheLock myCacheLock = new MyCacheLock();

        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCacheLock.write(temp, temp);
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCacheLock.read(temp);
            }, String.valueOf(i)).start();
        }
    }
}

/**
 * 普通读写缓存
 */
class MyCache {

    private volatile Map<Integer, Object> map = new HashMap<>();

    /**
     * 写
     */
    public void write(Integer key, Object value) {
        System.out.println(Thread.currentThread().getName() + " Write Start");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.put(key, value);

        System.out.println(Thread.currentThread().getName() + " Write End");
    }

    /**
     * 读
     */
    public void read(Integer key) {
        System.out.println(Thread.currentThread().getName() + " Read Start");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.get(key);

        System.out.println(Thread.currentThread().getName() + " Read End");
    }
}


/**
 * 加读写锁的读写缓存
 */
class MyCacheLock {

    private volatile Map<Integer, Object> map = new HashMap<>();

    /**
     * 创建读写锁，更加细腻度的控制。
     */
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    /**
     * 写
     */
    public void write(Integer key, Object value) {
        reentrantReadWriteLock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + " Write Start");

            TimeUnit.SECONDS.sleep(1);
            map.put(key, value);

            System.out.println(Thread.currentThread().getName() + " Write End");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    /**
     * 读
     */
    public void read(Integer key) {
        reentrantReadWriteLock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + " Read Start");

            TimeUnit.SECONDS.sleep(1);
            map.get(key);

            System.out.println(Thread.currentThread().getName() + " Read End");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }
}