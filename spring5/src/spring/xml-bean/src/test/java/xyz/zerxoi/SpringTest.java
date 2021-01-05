package xyz.zerxoi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import xyz.zerxoi.pojo.Book;
import xyz.zerxoi.pojo.Dept;
import xyz.zerxoi.pojo.Emp;
import xyz.zerxoi.pojo.LifeCycle;
import xyz.zerxoi.pojo.MyIterable;
import xyz.zerxoi.pojo.User;
import xyz.zerxoi.service.UserService;

public class SpringTest {
    @Test
    public void userTest() {
        // 1. 加载 Spring 配置文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        // 2. 获取配置创建的对象
        User user = context.getBean("user", User.class);
        System.out.println(user);
        context.close();
    }

    @Test
    public void bookTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        Book book = context.getBean("book", Book.class);
        System.out.println(book);
        context.close();
    }

    @Test
    public void userServiceTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.showUser();
        context.close();
    }

    @Test
    public void EmpDeptTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean3.xml");
        Emp emp = context.getBean("emp", Emp.class);
        System.out.println(emp);
        context.close();
    }

    @Test
    public void iterableTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean4.xml");
        MyIterable iterable = context.getBean("iterable", MyIterable.class);
        System.out.println(iterable);
        context.close();
    }

    @Test
    public void utilListTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean5.xml");
        MyIterable iterable = context.getBean("iterable", MyIterable.class);
        System.out.println(iterable);
        context.close();
    }

    @Test
    public void factoryBeanTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("factorybean.xml");
        Dept bean = context.getBean("facotrybean", Dept.class);
        System.out.println(bean);
        context.close();
    }

    @Test
    public void prototypeTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("prototypescope.xml");
        Dept bean1 = context.getBean("bean1", Dept.class);
        Dept bean2 = context.getBean("bean1", Dept.class);
        System.out.println(bean1 == bean2);
        bean1 = context.getBean("bean2", Dept.class);
        bean2 = context.getBean("bean2", Dept.class);
        System.out.println(bean1 == bean2);
        context.close();
    }

    @Test
    public void lifeCycleTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("lifecycle.xml");
        LifeCycle bean = context.getBean("lifecycle", LifeCycle.class);
        System.out.println("4. 获取 Bean 对象 " + bean);
        context.close();
    }

    @Test
    public void autoWireTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("autoWire.xml");
        Emp bean = context.getBean("emp", Emp.class);
        System.out.println(bean);
        context.close();
    }

    @Test
    public void externalPropertiesTest() throws SQLException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("externalProperties.xml");
        DataSource bean = context.getBean("dataSource", DataSource.class);
        Connection conn = bean.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from t_author");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("username"));
        }
        conn.close();
        context.close();
    }
}
