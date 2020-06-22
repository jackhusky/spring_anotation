package com.atguigu.config;

import com.atguigu.dao.BookDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * @Autowired：自动注入
 * 1、默认优先按照类型去容器中找对应的组件：applicationContext.getBean(BookDao.class);
 * 2、如果找到多个相同类型的组件：再将属性的名称作为组件的id去容器中查找applicationContext.getBean("bookDao");
 * 3、@Qualifier("bookDao2") 用来指定需要装配的组件的id，而不是使用属性名
 * 4、自动装配默认一定要将属性赋值好，没有就会报错。
 *      可以使用@Autowired(required = false)
 * 5、@Primary 让Spring进行自动装配的时候，默认使用首选的bean，也可以继续使用@Qualifier
 */
@Configuration
@ComponentScan(value = {"com.atguigu.dao","com.atguigu.service","com.atguigu.controller","com.atguigu.bean"})
public class MainConfigOfAutowired {
//    @Bean("bookDao2")
//    @Primary
//    public BookDao bookDao(){
//        BookDao bookDao = new BookDao();
//        bookDao.setLable("2");
//        return bookDao;
//    }
}
