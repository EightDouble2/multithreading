package com.johnny.concurrent.concurrent03;

import java.util.concurrent.TimeUnit;

/**
 * 8锁问题，判断锁的到底是谁
 *
 * new、this加锁，锁的是对象实例。
 * static、class加锁，锁的是整个类的Class。
 *
 * @author johnnyhao
 */
public class Concurrent03 {

    public static void main(String[] args) {
        Test test1 = new Test();
        Test test2 = new Test();

        /*
        1.两个同步方法：test1
          两个线程谁先拿到锁，就先执行。
          synchronized锁的对象是方法的调用者。
        2.test1休眠4秒：test1
          两个线程谁先拿到锁，就先执行。
          synchronized锁的对象是方法的调用者。
         */
        new Thread(() -> test1.test1()).start();
        new Thread(() -> test1.test2()).start();

        /*
        3.增加一个普通方法：test3
          普通方法不需要判断锁，线程触发了就执行。
         */
        new Thread(() -> test1.test1()).start();
        new Thread(() -> test1.test3()).start();

        /*
        4.两个对象两个同步方法：test2
          两个线程谁先触发就先执行。
          synchronized锁的对象是方法的调用者，所以锁对两个对象互不影响。
         */
        new Thread(() -> test1.test1()).start();
        new Thread(() -> test2.test2()).start();

        /*
        5.两个静态同步方法：test4
          两个线程谁先拿到锁，就先执行。
          synchronized锁的对象是整个Class。
        6.两个对象两个静态同步方法：test4
          两个线程谁先拿到锁，就先执行。
          synchronized锁的对象是整个Class。
         */
        new Thread(() -> test1.test4()).start();
        new Thread(() -> test1.test5()).start();
        new Thread(() -> test2.test5()).start();

        /*
        7.一个普通同步方法一个静态同步方法：test5
          两个线程谁先触发就先执行。
          synchronized锁的对象不同，所以互不影响。
        8.两个对象两个静态同步方法：test5
          两个线程谁先触发就先执行。
          synchronized锁的对象不同，所以互不影响。
         */
        new Thread(() -> test1.test1()).start();
        new Thread(() -> test1.test5()).start();
        new Thread(() -> test2.test5()).start();
    }
}

class Test {

    public synchronized void test1() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("test1");
    }

    public synchronized void test2() {
        System.out.println("test2");
    }

    public void test3() {
        System.out.println("test3");
    }

    public static synchronized void test4() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("test4");
    }

    public static synchronized void test5() {
        System.out.println("test5");
    }
}