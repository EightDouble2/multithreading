package com.johnny.thread;

/**
 * 守护线程
 *
 * 虚拟机必须确保用户线程执行完毕
 * 虚拟机不用等待守护线程执行完毕
 *
 * @author johnnyhao
 */
public class Thread14 {

    public static void main(String[] args) {
        Thread thread01 = new Thread(() -> {
            System.out.println("守护线程 Run...");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("守护线程 Stop...");
        });

        // 设置守护线程
        thread01.setDaemon(true);
        thread01.start();

        Thread thread02 = new Thread(() -> {
            System.out.println("用户线程 Run...");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("用户线程 Stop...");
        });

        thread02.start();
    }
}
