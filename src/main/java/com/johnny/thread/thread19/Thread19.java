package com.johnny.thread.thread19;

import java.util.ArrayList;
import java.util.List;

/**
 * (生产者/消费者模型)信号灯法
 *
 * @author johnnyhao
 */
public class Thread19 {

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();

        new Thread(new Producer(warehouse)).start();
        new Thread(new Consumer(warehouse)).start();
    }
}
/**
 * 生产者
 */
class Producer implements Runnable {

    /**
     * 仓库
     */
    private Warehouse warehouse;

    public Producer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            warehouse.produce(new Product(i));
        }
    }
}

/**
 * 消费者
 */
class Consumer implements Runnable {

    /**
     * 仓库
     */
    private Warehouse warehouse;

    public Consumer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            warehouse.consume();
        }
    }
}

/**
 * 产品
 */
class Product {
    private int id;

    public Product(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                '}';
    }
}

/**
 * 仓库
 */
class Warehouse {
    /**
     * 产品
     */
    private Product product = null;

    /**
     * 状态
     * 生产：produce
     * 消费：consume
     */
    private String flag = "produce";

    private final String PRODUCE = "produce";
    private final String CONSUME = "consume";

    /**
     * 生产
     */
    public synchronized void produce(Product product) {
        // 如果标志位为消费，则等待消费
        if (CONSUME.equals(flag)) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 如果标志位为生产，生产产品
        this.product = product;
        System.out.println("生产：" + product);

        // 将标志位置为消费
        flag = CONSUME;

        // 通知消费者消费
        this.notifyAll();
    }

    /**
     * 消费
     */
    public synchronized void consume() {
        // 如果标志位为生产，则等待生产
        if (PRODUCE.equals(flag)) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 如果标志位为消费，消费产品
        Product product = this.product;
        System.out.println("消费：" + product);

        // 将标志位置为生产
        flag = PRODUCE;

        // 通知生产者生产
        this.notifyAll();
    }
}