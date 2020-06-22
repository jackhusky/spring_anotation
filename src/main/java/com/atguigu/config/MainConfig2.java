package com.atguigu.config;

import com.atguigu.bean.Color;
import com.atguigu.bean.ColorFactoryBean;
import com.atguigu.bean.Person;
import com.atguigu.bean.Red;
import com.atguigu.condition.LinuxCondition;
import com.atguigu.condition.MyImportBeanDefinitionRegistrar;
import com.atguigu.condition.MyImportSelector;
import com.atguigu.condition.WindowsCondition;
import org.springframework.context.annotation.*;

/**
 * @author zhanghao
 * @date 2020/6/11 - 15:33
 */
@Configuration
@Import(value = {Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {

    @Bean
    @Lazy
//    @Scope("prototype")
    public Person person(){
        System.out.println("给IOC容器中注册...");
        return new Person("lucy",3);
    }

    @Bean("bill")
    @Conditional(WindowsCondition.class)
    public Person person01(){
        return new Person("bill gates",60);
    }

    @Bean("linus")
    @Conditional(LinuxCondition.class)
    Person person02(){
        return new Person("linus",50);
    }

    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }
}
