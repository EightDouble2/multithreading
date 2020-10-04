package com.johnny.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 线程同步
 *
 * @author johnnyhao
 */
public class Thread15 implements Runnable {

    /**
     * 票
     */
    private Integer ticket = 10;

    /**
     * 停止标志位
     */
    private boolean flag = true;

    public static void main(String[] args) {
        // 开启线程
        Thread15 thread15 = new Thread15();

        new Thread(thread15, "线程一").start();
        new Thread(thread15, "线程二").start();
        new Thread(thread15, "线程三").start();

        List<Object> list1 = new ArrayList<>();
        List<Object> list2 = new ArrayList<>();
        List<Object> list3 = new CopyOnWriteArrayList<>();

        // 线程不安全集合
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                list1.add(new Object());
            }).start();
        }

        // 使用synchronized 同步块锁定List对象
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (list2) {
                    list2.add(new Object());
                }
            }).start();
        }

        // 使用线程安全的CopyOnWriteArrayList
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                list3.add(new Object());
            }).start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(list1.size());
        System.out.println(list2.size());
        System.out.println(list3.size());
    }

    /**
     * 实现run方法
     */
    @Override
    public void run() {
        // run方法线程体
        while (flag) {
//            buy01();
            buy02();
        }
    }

    /**
     * synchronized 同步方法锁的是this
     */
    private synchronized void buy01() {
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

    /**
     * synchronized 同步块锁的是ticket对象
     */
    private void buy02() {
        synchronized (ticket) {
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
    }
}
