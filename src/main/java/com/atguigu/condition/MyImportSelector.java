package com.atguigu.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/**
 * @author zhanghao
 * @date 2020/6/11 - 16:30
 */
public class MyImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        String className = importingClassMetadata.getClassName();
        System.out.println("标注@Import注解的类的名字是: " + className);
        Set<String> annotationTypes = importingClassMetadata.getAnnotationTypes();
        System.out.println("标注@Import注解的类的其他注解有: " + annotationTypes);
        return new String[]{"com.atguigu.bean.Blue","com.atguigu.bean.Yellow"};
    }
}
