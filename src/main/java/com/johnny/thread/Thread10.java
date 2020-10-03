package com.johnny.thread;

/**
 * 线程礼让
 *
 * 让当前正在执行的线程暂停，但不阻塞。
 * 将线程从运行状态转为就绪状态。
 * 让CPU重新调度，礼让不一定成功。
 *
 * @author johnnyhao
 */
public class Thread10 implements Runnable {

    public static void main(String[] args) {
        new Thread(new Thread10(), "线程一").start();
        new Thread(new Thread10(), "线程二").start();
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始");
        Thread.yield();
        System.out.println(Thread.currentThread().getName() + "结束");
    }
}