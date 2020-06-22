package com.atguigu.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author zhanghao
 * @date 2020/6/11 - 21:09
 */
public class ColorFactoryBean implements FactoryBean<Color> {
    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
