package xyz.zerxoi;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import xyz.zerxoi.pojo.User;

public class IocInterfaceTest {
    @Test
    public void beanFactoryTest() {
        // XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("bean.xml"));
        // User user = beanFactory.getBean("user", User.class);
        // System.out.println(user);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        new XmlBeanDefinitionReader(beanFactory).loadBeanDefinitions("classpath:bean.xml");
        System.out.println("=========================");
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);
    }

    @Test
    public void applicationContextTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        System.out.println("=========================");
        User user1 = context.getBean("user", User.class);
        System.out.println(user1);
        User user2 = context.getBean("user", User.class);
        System.out.println(user2);
        System.out.println(user1 == user2);
        context.close();
    }

}
