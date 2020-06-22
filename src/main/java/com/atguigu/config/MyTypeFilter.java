package com.atguigu.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @author zhanghao
 * @date 2020/6/11 - 15:09
 */
public class MyTypeFilter implements TypeFilter {
    // metadataReader:目标类的元数据读取器
    // metadataReaderFactory:用于获得元数据的读者其他类的工厂（如超类和接口）
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 获取当前类注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        // 获取当前类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取类资源(路径)
        Resource resource = metadataReader.getResource();
        // 正在扫描类的名字
        String className = classMetadata.getClassName();
        System.out.println("类的全路径名字---->" + className);
        if (className.contains("er")){
            return true;
        }
        return false;
    }
}
