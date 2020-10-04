package com.johnny.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock(锁)
 *
 * @author johnnyhao
 */
public class Thread17 implements Runnable {

    /**
     * 票
     */
    private int ticket = 10;

    /**
     * 停止标志位
     */
    private boolean flag = true;

    /**
     * 锁
     */
    private final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread17 thread17 = new Thread17();
        new Thread(thread17, "线程一").start();
        new Thread(thread17, "线程二").start();
        new Thread(thread17, "线程三").start();
    }

    @Override
    public void run() {
        while (flag) {
            lock.lock();
            try {
                if (ticket <= 0) {
                    flag = false;
                    return;
                }

                // 休眠
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "拿到票：" + ticket--);
            }
            finally {
                lock.unlock();
            }
        }
    }
}
