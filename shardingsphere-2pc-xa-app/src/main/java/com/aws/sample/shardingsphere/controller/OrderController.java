package com.aws.sample.shardingsphere.controller;

import com.aws.sample.shardingsphere.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 创建表
     */
    @GetMapping("/create-table")
    public String createTable() {

        orderService.createTable();
        return "create table success";
    }

    /**
     * 删除表
     */
    @GetMapping("/drop-table")
    public String dropTable() {

        orderService.dropTable();
        return "drop table success";
    }

    /**
     * 批量插入数据
     */
    @GetMapping("/insert")
    public String insert() {

        orderService.insert();
        return "insert success";
    }

    /**
     * 批量插入数据
     */
    @GetMapping("/count")
    public Integer count() {

        Integer count = orderService.selectAll();
        return count;
    }

    /**
     * 批量插入数据
     */
    @GetMapping("/insert-failed")
    public String insertFailed() {

        orderService.insertFailed();
        return "insert fail";
    }

}
