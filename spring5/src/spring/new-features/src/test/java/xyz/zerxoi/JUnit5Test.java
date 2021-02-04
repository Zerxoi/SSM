package xyz.zerxoi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import xyz.zerxoi.service.AccountService;

// @ExtendWith(SpringExtension.class)
// @ContextConfiguration("classpath:bean.xml")
@SpringJUnitConfig(locations = "classpath:bean.xml")
public class JUnit5Test {
    // 将 Spring 创建的 AccountService 对象注入到属性中
    @Autowired
    AccountService accountService;

    @Test
    public void accountServiceTest() {
        System.out.println(accountService);
        accountService.transfer("alice", "bob", 1000);
    }
}
