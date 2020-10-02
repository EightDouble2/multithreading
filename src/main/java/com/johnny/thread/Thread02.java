package com.johnny.thread;

/**
 * 创建线程方式二：实现Runnable接口
 *
 * @author johnnyhao
 */
public class Thread02 implements Runnable {
    /**
     * 实现run方法
     */
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
        Thread02 thread02 = new Thread02();
        Thread thread = new Thread(thread02);
        thread.start();

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
