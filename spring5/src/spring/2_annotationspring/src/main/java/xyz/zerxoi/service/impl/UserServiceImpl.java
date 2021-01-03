package xyz.zerxoi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import xyz.zerxoi.dao.UserDao;
import xyz.zerxoi.pojo.User;
import xyz.zerxoi.service.UserService;

// value 值可以省略，如果省略 id 的默认值就是首字母小写的类名（userService）
// 等价于  <bean id="userServiceImpl" class="xyz.zerxoi.service.impl.UserServiceImpl" />
@Service
public class UserServiceImpl implements UserService {
    // @AutoWired 根据属性类型进行自动注入
    @Autowired
    @Qualifier("MyUserDao")
    private UserDao userDao;

    @Override
    public void showUser() {
        User user =  userDao.selectUser();
        System.out.println(user);
    }
}