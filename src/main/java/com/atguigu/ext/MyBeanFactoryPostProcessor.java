package com.atguigu.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author zhanghao
 * @date 2020/6/22 - 16:31
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor...postProcessBeanFactory()..");
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        System.out.println(Arrays.asList(beanDefinitionNames));
    }
}
