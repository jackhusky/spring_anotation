package com.atguigu.controller;

import com.atguigu.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author zhanghao
 * @date 2020/6/11 - 14:57
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;
}
