package com.atguigu.test;

import com.atguigu.bean.Person;
import com.atguigu.config.MainConfig;
import com.atguigu.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import sun.applet.Main;

import java.util.Map;

/**
 * @author zhanghao
 * @date 2020/6/11 - 14:58
 */
public class IOCTest {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

    @Test
    public void testImport(){
        printBeans(applicationContext);
        Object colorFactoryBean = applicationContext.getBean("&colorFactoryBean");
        System.out.println(colorFactoryBean.getClass());
    }

    private void printBeans(AnnotationConfigApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

    @Test
    public void test03(){
        Map<String, Person> persons = applicationContext.getBeansOfType(Person.class);
        System.out.println(persons);

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("os.name");
        System.out.println(property);
    }

    @Test
    public void test02(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

        Object person = applicationContext.getBean("person");
        Object person2 = applicationContext.getBean("person");
        System.out.println(person == person2);
    }

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

}
