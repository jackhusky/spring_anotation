package com.atguigu.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author zhanghao
 * @date 2021/3/19 16:02
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertUser(){
        String sql = "INSERT INTO `user`(user_name,age) VALUES(?,?);";
        String userName = UUID.randomUUID().toString().substring(0, 4);

        jdbcTemplate.update(sql,userName,99);
    }
}
