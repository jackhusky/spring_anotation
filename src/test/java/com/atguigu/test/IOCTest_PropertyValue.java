package com.atguigu.test;

import com.atguigu.config.MainConfigOfPropertyValue;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhanghao
 * @date 2020/6/12 - 13:46
 */
public class IOCTest_PropertyValue {

    @Test
    public void test1(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfPropertyValue.class);
        printBeans(applicationContext);

        Object person = applicationContext.getBean("person");
        System.out.println(person);
    }

    private void printBeans(AnnotationConfigApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
