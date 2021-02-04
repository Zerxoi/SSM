package xyz.zerxoi.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import xyz.zerxoi.dao.AccountDao;

@Repository
public class AccountDaoImpl implements AccountDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int increaseBalance(String name, Integer amount) {
        String sql = "update t_account set balance = balance + ? where name = ?";
        return jdbcTemplate.update(sql, amount, name);
    }

    @Override
    public int decreaseBalance(String name, Integer amount) {
        String sql = "update t_account set balance = balance - ? where name = ?";
        return jdbcTemplate.update(sql, amount, name);
    }
}
