package xyz.zerxoi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xyz.zerxoi.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class) // 指定单元测试框架测试版本
@ContextConfiguration("classpath:bean.xml") // 加载配置文件
public class JUnit4Test {
    // 将 Spring 创建的 AccountService 对象注入到属性中
    @Autowired
    AccountService accountService;

    @Test
    public void accountServiceTest() {
        System.out.println(accountService);
        accountService.transfer("bob", "alice", 1000);
    }
}
