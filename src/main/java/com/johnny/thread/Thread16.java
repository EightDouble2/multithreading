package com.johnny.thread;

/**
 * 死锁
 *
 * @author johnnyhao
 */
public class Thread16 implements Runnable {
    private final Class1601 class1601 = new Class1601();
    private final Class1602 class1602 = new Class1602();

    public static void main(String[] args) {
        Thread16 thread16 = new Thread16();
        new Thread(thread16, "线程一").start();
        new Thread(thread16, "线程二").start();
    }

    @Override
    public void run() {
        if ("线程一".equals(Thread.currentThread().getName())) {
            synchronized (class1601) {
                System.out.println("线程一获得class1601的锁");
                synchronized (class1602) {
                    System.out.println("线程一获得class1602的锁");
                }
            }
        }
        else if ("线程二".equals(Thread.currentThread().getName())) {
            synchronized (class1602) {
                System.out.println("线程二获得class1602的锁");
                synchronized (class1601) {
                    System.out.println("线程二获得class1601的锁");
                }
            }
        }
    }
}

class Class1601 {

}

class Class1602 {

}