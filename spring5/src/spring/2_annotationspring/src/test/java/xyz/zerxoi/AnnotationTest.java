package xyz.zerxoi;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import xyz.zerxoi.config.SpringConfiguration;
import xyz.zerxoi.dao.UserDao;
import xyz.zerxoi.pojo.Book;
import xyz.zerxoi.service.UserService;

public class AnnotationTest {
    @Test
    public void beanCreateTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        UserDao bean = context.getBean("userDaoImpl", UserDao.class);
        System.out.println(bean.selectUser());
        context.close();
    }

    @Test
    public void attributeInjectionTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        UserService bean = context.getBean("userServiceImpl", UserService.class);
        System.out.println(bean);
        bean.showUser();
        context.close();
    }

    @Test
    public void valueTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        Book bean = context.getBean("book", Book.class);
        System.out.println(bean);
        context.close();
    }

    @Test
    public void withoutXMLTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        UserService bean = context.getBean("userServiceImpl", UserService.class);
        System.out.println(bean);
        bean.showUser();
        context.close();
    }

}
