package xyz.zerxoi;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import xyz.zerxoi.pojo.Account;
import xyz.zerxoi.service.AccountService;

public class LogTest {
    @Test
    public void logTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        AccountService bean = context.getBean("accountServiceImpl", AccountService.class);
        bean.transfer("alice", "bob", 200);
        context.close();
    }

    @Test
    public void GenericApplicationContextTest() {
        GenericApplicationContext context = new GenericApplicationContext();
        context.refresh();
        context.registerBean("account", Account.class);
        Account account = (Account) context.getBean("account");
        System.out.println(account);
        context.close();
    }
}
