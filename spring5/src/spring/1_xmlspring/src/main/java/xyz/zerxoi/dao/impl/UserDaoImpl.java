package xyz.zerxoi.dao.impl;

import xyz.zerxoi.dao.UserDao;
import xyz.zerxoi.pojo.User;

public class UserDaoImpl implements UserDao {
    @Override
    public User selectUser() {
        User user = new User();
        user.setAddress("China");
        user.setAge(18);
        user.setName("高中生");
        return user;
    }
}
