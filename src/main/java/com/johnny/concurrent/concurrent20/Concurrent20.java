package com.johnny.concurrent.concurrent20;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁
 *
 * @author johnnyhao
 */
public class Concurrent20 {

    public static void main(String[] args) throws InterruptedException {
//        new Test01().test();
//        new Test02().test();
//        new Test03().test();
        new Test04().test();
    }
}

/**
 * 公平锁、非公平锁
 */
class Test01 {

    public void test() {
        // 默认都是非公平锁
        ReentrantLock reentrantLock = new ReentrantLock();
        // 设置为公平锁
        ReentrantLock fairReentrantLock = new ReentrantLock(true);
    }
}

/**
 * 可重入锁
 */
class Test02 {

    public void test() {
        // synchronized实现
        new Thread(() -> {
            test01();
        }, "A").start();

        new Thread(() -> {
            test01();
        }, "B").start();

        // look实现
        new Thread(() -> {
            test03();
        }, "C").start();

        new Thread(() -> {
            test03();
        }, "D").start();
    }

    /**
     * synchronized实现
     */
    public synchronized void test01() {
        System.out.println(Thread.currentThread().getName() + " test01");
        test02();
    }

    public synchronized void test02() {
        System.out.println(Thread.currentThread().getName() + " test02");
    }

    /**
     * lock实现
     */
    Lock lock = new ReentrantLock();

    public synchronized void test03() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " test03");
            test04();
        } finally {
            lock.unlock();
        }
    }

    public synchronized void test04() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " test04");
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 自旋锁
 */
class Test03 {

    public void test() throws InterruptedException {
        MyLock myLock = new MyLock();

        new Thread(() -> {
            myLock.lock();

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                myLock.unlock();
            }
        }, "A").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            myLock.lock();

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                myLock.unlock();
            }
        }, "B").start();
    }
}

/**
 * CAS实现自定义自旋锁
 */
class MyLock {
    private AtomicReference<Integer> atomicReference = new AtomicReference<>(0);

    /**
     * 加锁
     */
    public void lock() {
        System.out.println(Thread.currentThread().getName() + " lock");

        // 循环直到解锁
        while (atomicReference.compareAndSet(0, 1)) {

        }
    }

    /**
     * 解锁
     */
    public void unlock() {
        System.out.println(Thread.currentThread().getName() + " unlock");

        atomicReference.compareAndSet(1, 0);
    }
}

/**
 * 死锁
 */
class Test04 {

    public void test() {
        String strA = "strA";
        String strB = "strB";

        new Thread(new MyThread(strA, strB), "A").start();
        new Thread(new MyThread(strB, strA), "B").start();
    }
}

/**
 * 死锁线程
 */
class MyThread implements Runnable {

    private final String strA;
    private final String strB;

    public MyThread(String strA, String strB) {
        this.strA = strA;
        this.strB = strB;
    }

    @Override
    public void run() {
        synchronized (strA) {
            System.out.println(Thread.currentThread().getName() + " lock:" + strA + " get:" + strB);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (strB) {
                System.out.println(Thread.currentThread().getName() + " lock:" + strB);
            }
        }
    }
}