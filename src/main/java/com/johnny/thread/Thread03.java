package com.johnny.thread;

/**
 * 模拟抢票
 * 多线程同时操作同一个对象
 *
 * 发现问题：
 * 多个线程操作同一个资源的情况下，线程不安全，数据紊乱。
 *
 * @author johnnyhao
 */
public class Thread03 implements Runnable {
    /**
     * 票
     */
    private int ticket = 100;

    /**
     * 实现run方法
     */
    public void run() {
        // run方法线程体
        while (ticket > 0) {
            System.out.println(Thread.currentThread().getName() + "拿到票：" + ticket--);

            // 休眠
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // 开启线程
        Thread03 thread03 = new Thread03();

        new Thread(thread03, "线程一").start();
        new Thread(thread03, "线程二").start();
        new Thread(thread03, "线程三").start();
    }
}
