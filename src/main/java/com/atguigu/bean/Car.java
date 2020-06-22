package com.atguigu.bean;

/**
 * @author zhanghao
 * @date 2020/6/11 - 21:58
 */
public class Car {
    public Car() {
        System.out.println("car constructor...");
    }

    public void init(){
        System.out.println("car init...");
    }

    public void destroy(){
        System.out.println("car destroy...");
    }
}
