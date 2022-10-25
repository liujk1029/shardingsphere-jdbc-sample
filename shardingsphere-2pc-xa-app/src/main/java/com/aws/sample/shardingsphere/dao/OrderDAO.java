package com.aws.sample.shardingsphere.dao;

import org.apache.shardingsphere.transaction.annotation.ShardingSphereTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class OrderDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 创建表
     */
    public void createTable() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS t_order");
        jdbcTemplate.execute("CREATE TABLE t_order (order_id BIGINT AUTO_INCREMENT, user_id INT NOT NULL, status VARCHAR(50), PRIMARY KEY (order_id))");
    }

    /**
     * 删除表
     */
    public void dropTable() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS t_order");
    }

    /**
     * 批量插入
     */
    @Transactional
    @ShardingSphereTransactionType(TransactionType.XA)
    public void insert() {

        jdbcTemplate.execute("INSERT INTO t_order (user_id, status) VALUES (?, ?)",
                (PreparedStatementCallback<TransactionType>) preparedStatement -> {
            doInsert(20, preparedStatement);
            return TransactionTypeHolder.get();
        });

    }

    private void doInsert(final int count, final PreparedStatement preparedStatement) throws SQLException {
        for (int i = 0; i < count; i++) {
            preparedStatement.setObject(1, i);
            preparedStatement.setObject(2, "init");
            preparedStatement.executeUpdate();
        }
    }

    /**
     * 查询数量
     *
     * @return
     */
    public int selectAll() {
        return jdbcTemplate.queryForObject("SELECT COUNT(1) AS count FROM t_order", Integer.class);
    }


    /**
     * 批量插入失败
     */
    @Transactional
    @ShardingSphereTransactionType(TransactionType.XA)
    public void insertFailed() {
        jdbcTemplate.execute("INSERT INTO t_order (user_id, status) VALUES (?, ?)", (PreparedStatementCallback<TransactionType>) preparedStatement -> {
            doInsert(20, preparedStatement);
            throw new SQLException("mock transaction failed");
        });
    }


}
