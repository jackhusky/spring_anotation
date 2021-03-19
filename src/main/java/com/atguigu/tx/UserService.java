package com.atguigu.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhanghao
 * @date 2021/3/19 16:19
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void insertUser(){
        userDao.insertUser();
        System.out.println("插入完成");
        int i = 1/0;
    }
}
