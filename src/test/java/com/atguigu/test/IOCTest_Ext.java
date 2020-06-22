package com.atguigu.test;

import com.atguigu.aop.MathCalculator;
import com.atguigu.config.MainConfigOfAOP;
import com.atguigu.ext.ExtConfig;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhanghao
 * @date 2020/6/22 - 16:34
 */
public class IOCTest_Ext {

    @Test
    public void test1(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);

        applicationContext.publishEvent(new ApplicationEvent("我发布的事件") {
        });
        applicationContext.close();
    }
}
