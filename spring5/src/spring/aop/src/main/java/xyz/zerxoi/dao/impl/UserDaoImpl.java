package xyz.zerxoi.dao.impl;

import xyz.zerxoi.dao.UserDao;

public class UserDaoImpl implements UserDao {
    @Override
    public void insert(String username) {
        System.out.println("添加用户" + username);
    }
}
