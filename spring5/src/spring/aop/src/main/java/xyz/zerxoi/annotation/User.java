package xyz.zerxoi.annotation;

import org.springframework.stereotype.Component;

@Component
public class User {
    public String hello(String name, String c) {
        String string = "Hello, " + name + c;
        System.out.println(string);
        return string;
    }
}