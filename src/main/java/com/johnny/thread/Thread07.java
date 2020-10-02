package com.johnny.thread;

/**
 * Lambda表达式推导
 *
 * @author johnnyhao
 */
public class Thread07 {

    /**
     * 2.静态内部类
     */
    static class Person0702 implements IPerson07 {

        @Override
        public void todo(int i) {
            System.out.println(i + ".静态内部类实现方法");
        }
    }

    public static void main(String[] args) {
        IPerson07 person = new Person0701();
        person.todo(1);

        person = new Person0702();
        person.todo(2);

        /**
         * 3.局部内部类
         */
        class Person0703 implements IPerson07 {

            @Override
            public void todo(int i) {
                System.out.println(i + ".局部内部类实现方法");
            }
        }

        person = new Person0703();
        person.todo(3);

        // 4.匿名内部类
        person = new IPerson07() {

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
interface IPerson07 {

    /**
     * 接口方法
     */
    void todo(int i);
}

/**
 * 1.实现类
 */
class Person0701 implements IPerson07 {

    @Override
    public void todo(int i) {
        System.out.println(i + ".实现方法");
    }
}
