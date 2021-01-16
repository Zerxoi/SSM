package xyz.zerxoi;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import xyz.zerxoi.annotation.User;
import xyz.zerxoi.config.AopConfiguration;
import xyz.zerxoi.xml.Book;

public class AopTest {
    @Test
    public void annotationTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("annotation.xml");
        // User bean = context.getBean("user", User.class);
        // bean.hello("F*ck you", "dfas");
        Book book = context.getBean("book", Book.class);
        book.buy("鲁迅", "狂人日记", new BigDecimal("29.99"));
        context.close();
    }

    @Test
    public void xmlTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aopxml.xml");
        Book book = context.getBean("book", Book.class);
        book.buy("鲁迅", "狂人日记", new BigDecimal("29.99"));
        context.close();
    }

    @Test
    public void pureAnnotationTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfiguration.class);
        Book book = context.getBean("book", Book.class);
        book.buy("鲁迅", "狂人日记", new BigDecimal("29.99"));
        context.close();
    }
}
