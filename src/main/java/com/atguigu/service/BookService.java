package com.atguigu.service;

import com.atguigu.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * @author zhanghao
 * @date 2020/6/11 - 14:57
 */
@Service
public class BookService {

//    @Autowired(required = false)
//    @Qualifier("bookDao2")
//    @Autowired
//    @Resource
    @Inject
    private BookDao bookDao;

    public BookDao getBookDao() {
        return bookDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public String toString() {
        return "BookService{" +
                "bookDao=" + bookDao +
                '}';
    }
}
