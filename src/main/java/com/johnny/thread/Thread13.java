package com.johnny.thread;

/**
 * 线程优先级
 *
 * Java提供一个线程调度器来监控程序中启动后进入就绪状态的所有线程，线程调度器按照优先级决定应该调度哪个线程来执行。
 * 注意：优先级高的不一定先执行，只是被调度的概率高。
 *
 * @author johnnyhao
 */
public class Thread13 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "优先级" + Thread.currentThread().getPriority());
    }

    public static void main(String[] args) {
        // 主线程默认优先级
        System.out.println(Thread.currentThread().getName() + "优先级" + Thread.currentThread().getPriority());

        Thread13 thread13 = new Thread13();

        // 默认优先级
        Thread thread01 = new Thread(thread13, "线程一");
        thread01.setPriority(Thread.NORM_PRIORITY);
        thread01.start();

        // 最小优先级
        Thread thread02 = new Thread(thread13, "线程二");
        thread02.setPriority(Thread.MIN_PRIORITY);
        thread02.start();

        // 最大优先级
        Thread thread03 = new Thread(thread13, "线程三");
        thread03.setPriority(Thread.MAX_PRIORITY);
        thread03.start();
    }
}
