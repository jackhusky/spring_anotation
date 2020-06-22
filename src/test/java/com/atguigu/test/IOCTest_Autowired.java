package com.atguigu.test;

import com.atguigu.config.MainConfiOfLifeCycle;
import com.atguigu.config.MainConfigOfAutowired;
import com.atguigu.dao.BookDao;
import com.atguigu.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhanghao
 * @date 2020/6/11 - 22:01
 */
public class IOCTest_Autowired {
    @Test
    public void test1(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);
        System.out.println("创建容器完成...");

        BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookService);
    }
}
