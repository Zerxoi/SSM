package xyz.zerxoi.dao;

public interface AccountDao {
    public int increaseBalance(String name, Integer amount);
    public int decreaseBalance(String name, Integer amount);
}
