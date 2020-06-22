package com.atguigu.test;

import com.atguigu.aop.MathCalculator;
import com.atguigu.config.MainConfigOfAOP;
import com.atguigu.config.MainConfigOfAutowired;
import com.atguigu.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhanghao
 * @date 2020/6/11 - 22:01
 */
public class IOCTest_AOP {
    @Test
    public void test1(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);

        MathCalculator bean = applicationContext.getBean(MathCalculator.class);
        bean.div(1,1);
    }
}
