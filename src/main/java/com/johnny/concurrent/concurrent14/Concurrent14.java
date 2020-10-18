package com.johnny.concurrent.concurrent14;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Stream流式编程
 *
 * 要求：
 * ID为偶数。
 * 年龄大于4。
 * 用户名转小写。
 * ID倒序排序。
 * 只输出两个用户。
 *
 * @author johnnyhao
 */
public class Concurrent14 {

    public static void main(String[] args) {
        Person person1 = new Person(1L, "A", 1);
        Person person2 = new Person(2L, "B", 2);
        Person person3 = new Person(3L, "C", 3);
        Person person4 = new Person(4L, "D", 4);
        Person person5 = new Person(5L, "E", 5);
        Person person6 = new Person(6L, "F", 6);
        Person person7 = new Person(7L, "G", 7);
        Person person8 = new Person(8L, "H", 8);
        Person person9 = new Person(9L, "I", 9);
        Person person10 = new Person(10L, "J", 10);

        // 用集合做存储
        List<Person> list = Arrays.asList(person1, person2, person3, person4, person5, person6, person7, person8, person9, person10);

        // 用Stream流做计算
        list.stream()
                .filter(person -> {
                    return person.getId() % 2 == 0;
                })
                .filter(person -> {
                    return person.getAge() > 4;
                })
                .map(person -> {
                    return person.getName().toLowerCase();
                })
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .forEach(System.out::println);
    }
}

class Person {

    private Long id;
    private String name;
    private int age;

    public Person(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}