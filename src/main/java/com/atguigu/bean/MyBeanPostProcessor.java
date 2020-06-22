package com.atguigu.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author zhanghao
 * @date 2020/6/12 - 10:41
 */
@Component // 添加到容器中使其工作
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor postProcessBeforeInitialization..." + "beanName = " + beanName + " ==> " + bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor postProcessAfterInitialization..." + "beanName = " + beanName + " ==> " + bean);
        return bean;
    }
}
