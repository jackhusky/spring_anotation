package com.atguigu;

import com.atguigu.bean.Person;
import com.atguigu.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sun.applet.Main;

/**
 * @author zhanghao
 * @date 2020/6/11 - 14:33
 */
public class MainTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Person person = applicationContext.getBean(Person.class);
        System.out.println(person);
        System.out.println("****************************");
        String[] beanNamesForType = applicationContext.getBeanNamesForType(Person.class);
        for (String name : beanNamesForType) {
            System.out.println("在IOC容器中Person的名字是: " + name);
        }
    }
}
