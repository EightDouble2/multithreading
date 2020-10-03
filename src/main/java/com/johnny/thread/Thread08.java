package com.johnny.thread;

/**
 * 线程停止
 *
 * 建议线程正常停止，利用计数器，不建议死循环。
 * 建议使用标志位，设置标志位停止线程。
 * 不要使用stop或destory等过时或者JDK不建议使用的方法。
 *
 * @author johnnyhao
 */
public class Thread08 implements Runnable {

    /**
     * 标志位
     */
    private boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            System.out.println("Thread Run ...");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止线程方法
     */
    public void stop() {
        this.flag = false;
    }

    public static void main(String[] args) {
        Thread08 thread08 = new Thread08();
        new Thread(thread08).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread08.stop();
        System.out.println("Thread Stop !");
    }
}
