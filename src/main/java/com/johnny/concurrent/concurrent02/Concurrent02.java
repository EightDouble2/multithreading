package com.johnny.concurrent.concurrent02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者和消费者问题Lock实现
 *
 * Condition精准的通知和唤醒线程
 *
 * @author johnnyhao
 */
public class Concurrent02 {

    public static void main(String[] args) {
        Data data = new Data();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data.increment1();
            }
        },"A").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data.decrement1();
            }
        },"B").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data.increment2();
            }
        },"C").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data.decrement2();
            }
        },"D").start();
    }
}

class Data {

    private int number = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private Condition condition4 = lock.newCondition();

    //+1
    public void increment1() {
        lock.lock();
        try {
            while (number != 0) {
                // 等待
                condition1.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            // 通知其他线程，+1完毕了
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void increment2() {
        lock.lock();
        try {
            while (number != 0) {
                // 等待
                condition3.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            // 通知其他线程，+1完毕了
            condition4.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //-1
    public void decrement1() {
        lock.lock();
        try {
            while (number == 0) {
                // 等待
                condition2.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            // 通知其他线程，-1完毕了
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement2() {
        lock.lock();
        try {

            while (number == 0) {
                // 等待
                condition4.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            // 通知其他线程，-1完毕了
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}