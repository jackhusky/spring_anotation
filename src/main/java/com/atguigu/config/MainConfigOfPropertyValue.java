package com.atguigu.config;

import com.atguigu.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zhanghao
 * @date 2020/6/12 - 13:41
 */
@Configuration
@PropertySource(value = {"person.properties"})
public class MainConfigOfPropertyValue {

    @Bean
    public Person person(){
        return new Person();
    }
}
