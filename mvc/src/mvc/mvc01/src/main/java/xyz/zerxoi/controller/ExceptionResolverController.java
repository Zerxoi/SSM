package xyz.zerxoi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionResolverController {
    @RequestMapping("nullPointer")
    public String nullPointer() {
        String s = null;
        System.out.println(s.length());
        return "SUCCESS";
    }
}
