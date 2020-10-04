package com.johnny.thread.thread16;

/**
 * 死锁
 *
 * @author johnnyhao
 */
public class Thread16 implements Runnable {
    private final Class01 class01 = new Class01();
    private final Class02 class02 = new Class02();

    public static void main(String[] args) {
        Thread16 thread16 = new Thread16();
        new Thread(thread16, "线程一").start();
        new Thread(thread16, "线程二").start();
    }

    @Override
    public void run() {
        if ("线程一".equals(Thread.currentThread().getName())) {
            synchronized (class01) {
                System.out.println("线程一获得class01的锁");
                synchronized (class02) {
                    System.out.println("线程一获得class02的锁");
                }
            }
        }
        else if ("线程二".equals(Thread.currentThread().getName())) {
            synchronized (class02) {
                System.out.println("线程二获得class02的锁");
                synchronized (class01) {
                    System.out.println("线程二获得class01的锁");
                }
            }
        }
    }
}

class Class01 {

}

class Class02 {

}