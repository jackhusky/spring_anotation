package com.atguigu.test;

import com.atguigu.config.MainConfigOfTX;
import com.atguigu.tx.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhanghao
 * @date 2021/3/19 16:22
 */
public class IOCTest_TX {

    @Test
    public void test1(){
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(MainConfigOfTX.class);
        UserService bean = applicationContext.getBean(UserService.class);
        bean.insertUser();
    }
}
