package com.atguigu.test;

import com.atguigu.config.MainConfiOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhanghao
 * @date 2020/6/11 - 22:01
 */
public class IOCTest_LifeCycle {
    @Test
    public void test1(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfiOfLifeCycle.class);
        System.out.println("创建容器完成...");

        applicationContext.close();
    }
}
