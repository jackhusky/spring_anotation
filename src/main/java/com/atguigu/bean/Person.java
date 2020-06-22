package com.atguigu.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author zhanghao
 * @date 2020/6/11 - 14:29
 */
public class Person {

    @Value("jack")
    private String name;

    @Value("#{26-24}")
    private Integer age;

    @Value("${person.nickName}")
    private String nickName;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

}
