package xyz.zerxoi.service.impl;

import xyz.zerxoi.dao.UserDao;
import xyz.zerxoi.pojo.User;
import xyz.zerxoi.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void showUser() {
        User user =  userDao.selectUser();
        System.out.println(user);
    }
}
