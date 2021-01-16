package xyz.zerxoi.xml;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class Book {
    public void buy(String author, String name, BigDecimal price) {
        System.out.println("买了一本" + author + "的" + "《" + name + "》 价值" + price + "元");
    }
}
