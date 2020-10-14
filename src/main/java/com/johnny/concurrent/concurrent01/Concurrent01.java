package com.johnny.concurrent.concurrent01;

/**
 * 生产者和消费者问题Synchronized实现
 *
 * 使用while避免虚假唤醒，等待应该总是出现在循环中
 *
 * @author johnnyhao
 */
public class Concurrent01 {

    public static void main(String[] args) {
        Data data = new Data();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data.increment();
            }
        },"A").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data.decrement();
            }
        },"B").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data.increment();
            }
        },"C").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data.decrement();
            }
        },"D").start();
    }
}

class Data {

    private int number = 0;

    //+1
    public synchronized void increment() {
        while (number != 0) {
            // 等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number++;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        // 通知其他线程，+1完毕了
        this.notifyAll();
    }

    //-1
    public synchronized void decrement() {
        while (number == 0) {
            // 等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        // 通知其他线程，-1完毕了
        this.notifyAll();
    }
}