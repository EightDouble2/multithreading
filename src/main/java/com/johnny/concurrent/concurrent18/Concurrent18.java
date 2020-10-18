package com.johnny.concurrent.concurrent18;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 单例模式
 *
 * @author johnnyhao
 */
public class Concurrent18 {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        // 饿汉式
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                Test01.getInstance();
            }).start();
        }

        // 懒汉式
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                Test02.getInstance();
            }).start();
        }

        // 静态内部类
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                Test03.getInstance();
            }).start();
        }

        // 单例不安全，反射破坏单例
        // 正常创建
//        Test04.getInstance();
        // 反射获取构造方法
        Constructor<Test04> test04DeclaredConstructor = Test04.class.getDeclaredConstructor(null);
        // 关闭构造方法安全检测
        test04DeclaredConstructor.setAccessible(true);

        // 创建对象
        Test04 test04 = test04DeclaredConstructor.newInstance();

        // 反射获取标志位成员变量
        Field flag = Test04.class.getDeclaredField("flag");
        // 关闭标志位安全检测
        flag.setAccessible(true);
        // 破坏标志位值
        flag.set(test04, false);

        // 创建对象
        test04DeclaredConstructor.newInstance();

        // 枚举
        System.out.println(Test05.INSTANCE.getInstance());

        // 枚举没有空参数的构造方法 java.lang.NoSuchMethodException
//        Constructor<Test05> test05DeclaredConstructor = Test05.class.getDeclaredConstructor(null);
        Constructor<Test05> test05DeclaredConstructor = Test05.class.getDeclaredConstructor(String.class, int.class);
        test05DeclaredConstructor.setAccessible(true);
        // 枚举不允许使用反射创建 java.lang.IllegalArgumentException: Cannot reflectively create enum objects
        System.out.println(test05DeclaredConstructor.newInstance());
    }
}

/**
 * 饿汉式
 * 类加载就创建好对象，可能会浪费资源。
 */
class Test01 {

    /**
     * 类加载就创建好对象。
     */
    private final static Test01 test01 = new Test01();

    /**
     * 将构造方法私有化，避免外部调用。
     */
    private Test01() {
        System.out.println(Thread.currentThread().getName() + " Test01 ok");
    }

    /**
     * 直接返回对象。
     */
    public static Test01 getInstance() {
        return test01;
    }
}

/**
 * DCL懒汉式(双重锁)
 * 当需要的时候再创建对象。
 * 需要使用双重锁模式和volatile关键字，避免并发和指令重排带来错误。
 */
class Test02 {

    /**
     * 类加载时不创建对象。
     */
    private static volatile Test02 test02;

    /**
     * 将构造方法私有化，避免外部调用。
     */
    private Test02() {
        System.out.println(Thread.currentThread().getName() + " Test02 ok");
    }

    /**
     * 先判断有没有创建过对象，没有创建过对象则创建对象。
     * 在并发时可能会创建多个对象，所以要对类加锁，锁前后进行双重判断，也就是双重锁。
     * 类的创建过程不是原子性操作，分配内存空间、执行构造方法初始化对象、把这个对象指向这个空间。在这个过程中，可能发生指令重排，所以导致返回的对象还没有创建，所以必须加上volatile关键字，禁止指令重排。
     */
    public static Test02 getInstance() {
        if (test02 == null) {
            synchronized (Test02.class) {
                if (test02 == null) {
                    test02 = new Test02();
                }
            }
        }
        return test02;
    }
}

/**
 * 静态内部类
 */
class Test03 {

    private Test03() {
        System.out.println(Thread.currentThread().getName() + " Test03 ok");
    }

    public static Test03 getInstance() {
        return Inner.test03;
    }

    public static class Inner {
        private static final Test03 test03 = new Test03();
    }
}

/**
 * 反射破坏单例
 */
class Test04 {

    private static volatile Test04 test04;

    // 标志位
    private static boolean flag = false;

    private Test04() {
        // 避免通过反射多次创建对象，但是在没有正常创建过对象时依然无法避免。
//        synchronized (Test04.class) {
//            if (test04 != null) {
//                throw new RuntimeException("使用反射破坏单例");
//            }
//        }
        // 避免多次创建对象，但是通过反射还可以修改标志位的值。
        synchronized (Test04.class) {
            if (!flag) {
                flag = true;
            }
            else {
                throw new RuntimeException("使用反射破坏单例");
            }
        }
        System.out.println(Thread.currentThread().getName() + " Test04 ok");
    }

    public static Test04 getInstance() {
        if (test04 == null) {
            synchronized (Test04.class) {
                if (test04 == null) {
                    test04 = new Test04();
                }
            }
        }
        return test04;
    }
}

/**
 * 枚举
 * 枚举已经实现了单例模式
 */
enum Test05 {

    INSTANCE;

    public Test05 getInstance() {
        return INSTANCE;
    }
}