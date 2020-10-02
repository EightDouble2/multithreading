package com.johnny.thread;

/**
 * 模拟龟兔赛跑
 *
 * @author johnnyhao
 */
public class Thread04 implements Runnable {
    /**
     * 赛道
     */
    private int track = 100;

    /**
     * 胜利者
     */
    private String winner;

    /**
     * 实现run方法
     */
    public void run() {
        // run方法线程体
        int i = 0;

        // 判断比赛是否结束
        while (!gameover(i)) {
            System.out.println(Thread.currentThread().getName() + "跑了：" + i++);

            // 休眠
            try {
                // 模拟兔子速度
                if ("兔子".equals(Thread.currentThread().getName())) {
                    Thread.sleep(100);
                }
                // 模拟乌龟速度
                else if ("乌龟".equals(Thread.currentThread().getName())) {
                    Thread.sleep(200);
                }
                // 模拟兔子睡觉
                if ("兔子".equals(Thread.currentThread().getName()) && i == 50) {
                    Thread.sleep(12500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean gameover(int steps) {
        // 已经分出胜负
        if (winner != null) {
            return true;
        }
        // 已到达终点
        else if (steps >= track) {
            winner = Thread.currentThread().getName();

            System.out.println("winner is " + winner);

            return true;
        }
        else {
            return false;
        }
    }

    public static void main(String[] args) {
        // 开启线程
        Thread04 thread04 = new Thread04();

        new Thread(thread04, "乌龟").start();
        new Thread(thread04, "兔子").start();
    }
}
