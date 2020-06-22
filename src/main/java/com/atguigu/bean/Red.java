package com.atguigu.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @author zhanghao
 * @date 2020/6/11 - 16:18
 */
@Component
public class Red implements ApplicationContextAware, EmbeddedValueResolverAware, BeanNameAware {

    @Override
    public void setBeanName(String name) {
        System.out.println("bean的名字：" + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("IOC容器：" + applicationContext);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        System.out.println(resolver.resolveStringValue("你好#{2022-2},${os.name}"));
    }
}
