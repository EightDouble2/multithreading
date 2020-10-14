package com.johnny.concurrent.concurrent04;

import java.sql.SQLOutput;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Callable
 *
 * 相较于不同方法，Callable可以有返回值，可以抛出异常，调用方法也不同，而且具有缓存功能。
 *
 * FutureTask实现了RunnableFuture接口，RunnableFuture继承了Runnable接口。
 * 而FutureTask有Runnable和Callable两个构造器。
 * 所以Callable可以通过FutureTask适配器被Thread调用。
 *
 * @author johnnyhao
 */
public class Concurrent04 {

    public static void main(String[] args) {

        MyCallable myCallable = new MyCallable();

        FutureTask<Integer> futureTask = new FutureTask<>(myCallable);

        new Thread(futureTask, "A").start();
        new Thread(futureTask, "B").start();
    }
}

class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {

        System.out.println("call");

        return 1024;
    }
}
