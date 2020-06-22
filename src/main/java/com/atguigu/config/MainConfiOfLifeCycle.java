package com.atguigu.config;

import com.atguigu.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author zhanghao
 * @date 2020/6/11 - 21:58
 */
@Configuration
@ComponentScan("com.atguigu.bean")
public class MainConfiOfLifeCycle {

    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car(){
        return new Car();
    }
}
