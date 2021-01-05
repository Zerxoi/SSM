package xyz.zerxoi.dao.impl;

import org.springframework.stereotype.Repository;

import xyz.zerxoi.dao.UserDao;
import xyz.zerxoi.pojo.User;

@Repository("MyUserDao")
public class UserDaoImpl implements UserDao {
    @Override
    public User selectUser() {
        User user = new User();
        user.setAddress("China");
        user.setAge(18);
        user.setName("Zerxoi");
        return user;
    }
}