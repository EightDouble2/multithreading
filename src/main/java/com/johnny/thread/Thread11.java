package com.johnny.thread;

/**
 * 线程合并
 *
 * 待此线程执行完成后，再执行其他线程，其他线程阻塞。
 *
 * @author johnnyhao
 */
public class Thread11 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("多线程：" + i);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread11 thread11 = new Thread11();
        Thread thread = new Thread(thread11);
        thread.start();

        for (int i = 0; i < 100; i++) {
            System.out.println("主线程：" + i);

            if (i == 50) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
