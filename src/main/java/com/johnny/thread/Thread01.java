package com.johnny.thread;

/**
 * 创建线程方式一：继承Thread类
 *
 * @author johnnyhao
 */
public class Thread01 extends Thread {
    /**
     * 重写run方法
     */
    @Override
    public void run() {
        // run方法线程体
        for (int i = 0; i <= 100; i++) {
            System.out.println("多线程：" + i);

            // 休眠
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // 多线程
        Thread01 thread01 = new Thread01();
        thread01.start();

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
