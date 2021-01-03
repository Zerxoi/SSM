package xyz.zerxoi.pojo;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Book {
    @Value("《狂人日记》")
    private String name;
    @Value("鲁迅")
    private String author;
    @Value("19.9")
    private BigDecimal price;

    // @Deprecated
    // public Book(String name, String author, BigDecimal price) {
    //     this.name = name;
    //     this.author = author;
    //     this.price = price;
    // }

    @Override
    public String toString() {
        return "Book [author=" + author + ", name=" + name + ", price=" + price + "]";
    }

}
