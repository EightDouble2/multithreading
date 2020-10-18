package com.johnny.concurrent.concurrent13;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 函数式接口
 *
 * @author johnnyhao
 */
public class Concurrent13 {

    public static void main(String[] args) {
        // Function(函数型接口)：有参数，有返回值。
        Function<Object, String> function = new Function<Object, String>() {
            @Override
            public String apply(Object o) {
                return o.toString();
            }
        };

        System.out.println(function.apply("obj"));

        // lambda表达式简化。
        Function<Object, String> functionLambda = Object::toString;

        System.out.println(function.apply("obj"));

        // Predicate(断定型接口)：有参数，返回布尔值。
        Predicate<Object> predicate = new Predicate<Object>() {
            @Override
            public boolean test(Object o) {
                return "obj".equals(o);
            }
        };

        System.out.println(predicate.test("obj"));

        // lambda表达式简化。
        Predicate<Object> predicateLambda = "obj"::equals;

        System.out.println(predicateLambda.test("obj"));

        // Consumer(消费型接口)：只有参数，没有返回值。
        Consumer<Object> consumer = new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                System.out.println(o.toString());
            }
        };

        consumer.accept("obj");

        // lambda表达式简化。
        Consumer<Object> consumerLambda = System.out::println;

        consumerLambda.accept("obj");

        // Supplier(供给型接口)：没有参数，只有返回值。
        Supplier<Object> supplier = new Supplier<Object>() {
            @Override
            public Object get() {
                return "obj";
            }
        };

        System.out.println(supplier.get());

        // lambda表达式简化。
        Supplier<Object> supplierLambda = () -> "obj";

        System.out.println(supplierLambda.get());
    }
}
