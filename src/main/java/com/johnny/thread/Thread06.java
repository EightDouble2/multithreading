package com.johnny.thread;

/**
 * 静态代理
 *
 * 真实对象和代理对象都要实现同一个接口，代理对象要代理真实角色。
 *
 * 优点：
 * 代理对象可以做很多真实对象做不了的事情。
 * 真实对象专注做自己的事情
 *
 * @author johnnyhao
 */
public class Thread06 {

    public static void main(String[] args) {
        // 通过静态代理类调用真实方法
        PersonProxy personProxy = new PersonProxy(new Person06());
        personProxy.todo();

        new PersonProxy(() -> {
            System.out.println("使用Lambda表达式真实方法");
        });
    }
}

/**
 * 接口
 */
interface IPerson06 {

    /**
     * 方法
     */
    void todo();
}

/**
 * 静态代理类
 */
class PersonProxy implements IPerson06 {

    /**
     * 真实对象
     */
    private final IPerson06 target;

    public PersonProxy(IPerson06 target) {
        this.target = target;
    }

    /**
     * 代理方法
     */
    @Override
    public void todo() {
        System.out.println("操作前");
        target.todo();
        System.out.println("操作后");
    }
}

/**
 * 真实类
 */
class Person06 implements IPerson06 {

    /**
     * 真实方法
     */
    @Override
    public void todo() {
        System.out.println("真实方法");
    }
}