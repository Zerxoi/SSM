package xyz.zerxoi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// 创建控制器对象
@Controller
public class MvcController {
    // 配置请求映射,将 hello 路径与该方法进行映射,映射与方法名无关
    @RequestMapping(value = "/hello/{name:\\w+}", method = RequestMethod.GET)
    public String hello(@PathVariable("name") String name) {
        System.out.println("Hello, " + name);
        // 视图名称
        return "SUCCESS";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password, HttpServletRequest req, HttpServletResponse resp) {
        System.out.println(req.getCharacterEncoding());
        System.out.println(resp.getCharacterEncoding());
        System.out.println("Username ================ " + username);
        System.out.println("Password ================ " + password);
        return "SUCCESS";
    }
}