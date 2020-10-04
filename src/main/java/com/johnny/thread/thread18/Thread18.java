package com.johnny.thread.thread18;

import java.util.ArrayList;
import java.util.List;

/**
 * (生产者/消费者模型)管程法
 *
 * @author johnnyhao
 */
public class Thread18 {

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
     * 容器
     */
    private final List<Product> products = new ArrayList<>();

    /**
     * 容器最大容量
     */
    private final int maxStze = 10;

    /**
     * 生产
     */
    public synchronized void produce(Product product) {
        // 如果仓库满了，需要等待消费者消费
        if (products.size() == maxStze) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 如果仓库没有满，生产产品
        products.add(product);
        System.out.println("生产：" + product);

        // 通知消费者消费
        this.notifyAll();
    }

    /**
     * 消费
     */
    public synchronized void consume() {
        // 如果仓库空了，需要等待生产者生产
        if (products.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 如果仓库没空，消费产品
        Product product = products.remove(0);
        System.out.println("消费：" + product);

        // 通知生产者生产
        this.notifyAll();
    }
}