package xyz.zerxoi;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import xyz.zerxoi.config.SpringConfiguration;
import xyz.zerxoi.service.AccountService;

public class TxTest {
    @Test
    public void transferTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate.xml");
        AccountService bean = context.getBean("accountServiceImpl", AccountService.class);
        bean.transfer("alice", "bob", 200);
        context.close();
    }

    @Test
    public void transactionManagerTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate.xml");
        AccountService bean = context.getBean("accountServiceImpl", AccountService.class);
        bean.transfer("alice", "bob", 200);
        context.close();
    }

    @Test
    public void xmlTransactionManagerTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("tx-xml.xml");
        AccountService bean = context.getBean("accountServiceImpl", AccountService.class);
        bean.transfer("alice", "bob", 200);
        context.close();
    }

    @Test
    public void pureAnnotationTransactionManagerTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        AccountService bean = context.getBean("accountServiceImpl", AccountService.class);
        bean.transfer("alice", "bob", 200);
        context.close();
    }
}
