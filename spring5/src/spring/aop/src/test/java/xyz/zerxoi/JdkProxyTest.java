package xyz.zerxoi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

import xyz.zerxoi.dao.UserDao;
import xyz.zerxoi.dao.impl.UserDaoImpl;

public class JdkProxyTest {
    @Test
    public void jdkProxyTest() {
        UserDao userDao = new UserDaoImpl();
        InvocationHandler handler = new MyInvocationHandler(userDao);
        UserDao proxy = (UserDao)Proxy.newProxyInstance(userDao.getClass().getClassLoader(), userDao.getClass().getInterfaces(), handler);
        proxy.insert("zerxoi");
    }
}

class MyInvocationHandler implements InvocationHandler {
    private Object obj;

    public MyInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理类对象的方法分配给被代理类对象之前");
        // 将代理类对象的方法分配给被代理类对象
        Object result = method.invoke(obj, args);
        System.out.println("代理类对象的方法分配给被代理类对象之后");
        return result;
    }
}