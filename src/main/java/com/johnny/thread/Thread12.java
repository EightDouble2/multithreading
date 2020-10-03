package com.johnny.thread;

/**
 * 线程状态
 *
 * NEW 尚未启动的线程处于此状态
 * RUNNABLE 在Java虚拟机中的线程处于此状态
 * BLOCKED 被阻塞等待监视器锁定的线程处于此状态
 * WAITING 正在等待另一个线程执行特定动作的线程处于此状态
 * TIME WAITING 正在等待另一个线程执行动作达到指定等待时间的线程处于此状态
 * TERMINATED 已退出的线程处于此状态
 *
 * @author johnnyhao
 */
public class Thread12 {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // NEW
        System.out.println(thread.getState());

        thread.start();

        // RUNNABLE
        // TIMED_WAITING
        System.out.println(thread.getState());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // TERMINATED
        System.out.println(thread.getState());
    }
}
