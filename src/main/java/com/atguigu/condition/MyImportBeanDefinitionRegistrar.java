package com.atguigu.condition;

import com.atguigu.bean.Rainbow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zhanghao
 * @date 2020/6/11 - 16:51
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String className = importingClassMetadata.getClassName();
        System.out.println("导入类的名字: " + className);
        registry.registerBeanDefinition("rainbow",new RootBeanDefinition(Rainbow.class));
    }
}
