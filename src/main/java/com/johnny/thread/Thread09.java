package com.johnny.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 线程休眠
 *
 * 模拟网络延时，倒计时，打印当前系统时间等。
 *
 * 每一个对象都有一个锁，sleep不会释放锁。
 *
 * @author johnnyhao
 */
public class Thread09 implements Runnable {
    /**
     * 票
     */
    private int ticket = 10;

    /**
     * 实现run方法
     */
    @Override
    public void run() {
        // run方法线程体
        while (ticket > 0) {
            // 模拟网络延时
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "拿到票：" + ticket--);
        }
    }

    public static void main(String[] args) {
        // 模拟抢票网络延时
        Thread09 thread09 = new Thread09();

        new Thread(thread09, "线程一").start();
        new Thread(thread09, "线程二").start();
        new Thread(thread09, "线程三").start();

        // 倒计时
        for (int i = 10; i >= 0; i--) {
            System.out.println(i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 打印当前系统时间
        for (int i = 0; i < 10; i++) {
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
