package xyz.zerxoi;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigBeanDefinitionParser;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import xyz.zerxoi.config.SpringConfiguration;
import xyz.zerxoi.dao.UserDao;
import xyz.zerxoi.pojo.Book;
import xyz.zerxoi.pojo.ConstructorAutowiredBean;
import xyz.zerxoi.pojo.IterableBean;
import xyz.zerxoi.service.UserService;

public class AnnotationTest {
    @Test
    public void beanCreateTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        UserDao bean = context.getBean("MyUserDao", UserDao.class);
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

    @Test
    public void iterableTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        IterableBean bean = context.getBean("iterableBean", IterableBean.class);
        System.out.println(bean);
        System.out.println(bean.getList());
        System.out.println(Arrays.toString(bean.getArray()));
        System.out.println(bean.getMap());
        System.out.println(bean.getSet());
        context.close();
    }

    @Test
    public void constructorTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        ConstructorAutowiredBean bean = context.getBean("constructorAutowiredBean", ConstructorAutowiredBean.class);
        System.out.println(bean);
        context.close();
    }
}
