package xyz.zerxoi.pojo;

import java.math.BigDecimal;

public class Book {
    private String name;
    private String author;
    private BigDecimal price;

    @Deprecated
    public Book(String name, String author, BigDecimal price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book [author=" + author + ", name=" + name + ", price=" + price + "]";
    }

}
