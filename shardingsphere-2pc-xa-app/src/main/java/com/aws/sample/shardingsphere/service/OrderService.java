package com.aws.sample.shardingsphere.service;

import com.aws.sample.shardingsphere.dao.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    public void createTable() {
        orderDAO.createTable();
    }

    public void dropTable() {
        orderDAO.dropTable();
    }

    public void insert() {
        orderDAO.insert();
    }

    public int selectAll() {
        return orderDAO.selectAll();
    }

    public void insertFailed() {
        orderDAO.insertFailed();
    }

}
