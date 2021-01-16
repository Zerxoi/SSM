package xyz.zerxoi.xml;

import java.math.BigDecimal;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BookProxy {
    @Before(value = "execution(* xyz.zerxoi.xml.Book.buy(..)) && args(arg1, arg2, arg3)", argNames = "arg2, arg1, arg3")
    public void before(String name, String author, BigDecimal price) {
        System.out.println("============Before Advice============");
        System.out.println("name: " + name);
        System.out.println("author: " + author);
        System.out.println("price: " + price);
        System.out.println("============Before Advice============");
    }
}