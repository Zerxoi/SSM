package xyz.zerxoi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.zerxoi.dao.AccountDao;
import xyz.zerxoi.service.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public void transfer(String from, String to, Integer amount) {
        accountDao.decreaseBalance(from, amount);
        // 模拟异常
        // int i = 10 / 0;
        accountDao.increaseBalance(to, amount);
    }
}
