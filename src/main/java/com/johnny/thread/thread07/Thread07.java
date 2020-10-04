package com.johnny.thread.thread07;

/**
 * Lambda表达式推导
 *
 * @author johnnyhao
 */
public class Thread07 {

    /**
     * 2.静态内部类
     */
    static class Person02 implements IPerson {

        @Override
        public void todo(int i) {
            System.out.println(i + ".静态内部类实现方法");
        }
    }

    public static void main(String[] args) {
        IPerson person = new Person01();
        person.todo(1);

        person = new Person02();
        person.todo(2);

        /**
         * 3.局部内部类
         */
        class Person03 implements IPerson {

            @Override
            public void todo(int i) {
                System.out.println(i + ".局部内部类实现方法");
            }
        }

        person = new Person03();
        person.todo(3);

        // 4.匿名内部类
        person = new IPerson() {

            @Override
            public void todo(int i) {
                System.out.println(i + ".匿名内部类实现方法");
            }
        };
        person.todo(4);

        // 5.使用Lambda表达式简化
        person = (i) -> {
            System.out.println(i + ".使用Lambda表达式实现方法");
        };
        person.todo(5);
    }
}

/**
 * 定义函数式接口
 */
interface IPerson {

    /**
     * 接口方法
     */
    void todo(int i);
}

/**
 * 1.实现类
 */
class Person01 implements IPerson {

    @Override
    public void todo(int i) {
        System.out.println(i + ".实现方法");
    }
}
