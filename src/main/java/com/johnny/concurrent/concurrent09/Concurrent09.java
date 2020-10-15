package com.johnny.concurrent.concurrent09;

import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 *
 * @author johnnyhao
 */
public class Concurrent09 {

    public static void main(String[] args) throws InterruptedException {
        test01();
        test02();
        test03();
        test04();
    }

    /**
     * 抛出异常
     */
    public static void test01() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        // 添加
        System.out.println(blockingQueue.add("A"));
        System.out.println(blockingQueue.add("B"));
        System.out.println(blockingQueue.add("C"));

        // 队列满抛出异常
        try {
            System.out.println(blockingQueue.add("D"));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        // 移除
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

        // 队列空抛出异常
        try {
            System.out.println(blockingQueue.remove());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    /**
     * 有返回值，不抛出异常
     */
    public static void test02() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        // 添加
        System.out.println(blockingQueue.offer("A"));
        System.out.println(blockingQueue.offer("B"));
        System.out.println(blockingQueue.offer("C"));

        // 队列满返回false
        System.out.println(blockingQueue.offer("D"));

        // 移除
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

        // 队列空返回null
        System.out.println(blockingQueue.poll());
    }

    /**
     * 阻塞，一直等待
     */
    public static void test03() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        // 添加
        blockingQueue.put("A");
        blockingQueue.put("B");
        blockingQueue.put("C");

        // 队列满一直阻塞
        blockingQueue.put("D");

        // 移除
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());

        // 队列空一直阻塞
        System.out.println(blockingQueue.take());
    }

    /**
     * 阻塞，等待超时
     */
    public static void test04() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        // 添加
        System.out.println(blockingQueue.offer("A"));
        System.out.println(blockingQueue.offer("B"));
        System.out.println(blockingQueue.offer("C"));

        // 队列满等待超时
        System.out.println(blockingQueue.offer("D", 2, TimeUnit.SECONDS));

        // 移除
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

        // 队列空等待超时
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
    }
}

