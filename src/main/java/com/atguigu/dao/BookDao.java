package com.atguigu.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * @author zhanghao
 * @date 2020/6/11 - 14:57
 */
@Repository
public class BookDao {

    private String lable = "1";

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "lable='" + lable + '\'' +
                '}';
    }
}
