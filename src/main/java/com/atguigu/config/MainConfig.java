package com.atguigu.config;

import com.atguigu.bean.Person;
import com.atguigu.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @author zhanghao
 * @date 2020/6/11 - 14:32
 */
@Configuration //告诉spring这是一个配置类
@ComponentScan(value = "com.atguigu",includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class),
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = BookService.class),
        @ComponentScan.Filter(type = FilterType.CUSTOM,value = MyTypeFilter.class)
},useDefaultFilters = false)
public class MainConfig {

    @Bean(value = "person") //注入bean,id默认是方法名字,也可以通过@Bean(value = "person")指定
    public Person person01(){
        return new Person("jack",2);
    }
}
