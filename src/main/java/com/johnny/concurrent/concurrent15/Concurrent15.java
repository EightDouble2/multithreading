package com.johnny.concurrent.concurrent15;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * ForkJoin
 *
 * 求和计算的三种方式：
 * 普通计算。
 * ForkJoin计算。
 * Stream并行流计算。
 *
 * @author johnnyhao
 */
public class Concurrent15 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // time=9048
        test01();
        // time=5322
        test02();
        // time=489
        test03();
    }

    /**
     * 普通计算
     */
    public static void test01() {
        Long sum = 0L;

        long start = System.currentTimeMillis();
        for (Long i = 1L; i <= 10_0000_0000; i++) {
            sum += i;
        }

        long end = System.currentTimeMillis();

        System.out.println("sum=" + sum + " time=" + (end - start));
    }

    /**
     * ForkJoin计算
     */
    public static void test02() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        // 创建ForkJoin线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 创建任务
        ForkJoinTask<Long> task = new ForkJoin(0L, 10_0000_0000L);
        // 提交任务
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);
        // 获取结果
        long sum = submit.get();

        long end = System.currentTimeMillis();

        System.out.println("sum=" + sum + " time=" + (end - start));
    }

    /**
     * Stream并行流
     */
    public static void test03() {
        long start = System.currentTimeMillis();

        long sum = LongStream.rangeClosed(0L, 10_0000_0000L).parallel().reduce(0, Long::sum);

        long end = System.currentTimeMillis();

        System.out.println("sum=" + sum + " time=" + (end - start));
    }
}

class ForkJoin extends RecursiveTask<Long> {

    /**
     * 起止值
     */
    private long startNum;
    private long endNum;

    /**
     * 临界值
     */
    private long criticalNum = 10000L;

    public ForkJoin(long startNum, long endNum) {
        this.startNum = startNum;
        this.endNum = endNum;
    }

    /**
     * 计算方法
     */
    @Override
    protected Long compute() {
        if ((endNum - startNum) < criticalNum){
            Long sum = 0L;

            for (Long i = startNum; i <= endNum; i++) {
                sum += i;
            }

            return sum;
        }
        else {
            long middleNum = (startNum + endNum) / 2;
            // 拆分任务，把任务压入线程队列
            ForkJoin forkJoin1 = new ForkJoin(startNum, middleNum);
            forkJoin1.fork();
            ForkJoin forkJoin2 = new ForkJoin(middleNum + 1, endNum);
            forkJoin2.fork();

            // 合并结果
            return forkJoin1.join() + forkJoin2.join();
        }
    }
}