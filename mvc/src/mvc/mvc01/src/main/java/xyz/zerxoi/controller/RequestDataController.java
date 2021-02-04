package xyz.zerxoi.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import xyz.zerxoi.bean.User;

@Controller
public class RequestDataController {
    @RequestMapping(value = "/param", method = RequestMethod.POST)
    public String param(@RequestParam(value = "username", required = false, defaultValue = "admin") String name,
            String password, Integer age) {
        System.out.println("username = " + name + ", password = " + password + ", age = " + age);
        return "SUCCESS";
    }

    @RequestMapping(value = "/header", method = RequestMethod.GET)
    public String header(
            @RequestHeader(value = "Accept-Language", required = false, defaultValue = "zh-CN") String lang) {
        System.out.println("Accept-Language = " + lang);
        return "SUCCESS";
    }

    @RequestMapping(value = "/cookie", method = RequestMethod.GET)
    public String cookie(@CookieValue(value = "JSESSIONID") String jSessionId) {
        System.out.println("JSESSIONID = " + jSessionId);
        return "SUCCESS";
    }

    @RequestMapping(value = "/paramPojo", method = RequestMethod.POST)
    public String paramPojo(User user) {
        System.out.println(user);
        return "SUCCESS";
    }

    @RequestMapping(value = "/paramMap", method = RequestMethod.POST)
    public String paramMap(@RequestParam Map<String, String> map) {
        System.out.println(map);
        return "SUCCESS";
    }

    @RequestMapping(value = "/servletReq", method = RequestMethod.POST)
    public String servletReq(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("username");
        req.setAttribute("key", "req");
        System.out.println(username);
        return "SUCCESS";
    }

    @RequestMapping(value = "/mav", method = RequestMethod.GET)
    public ModelAndView mav() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("key", "mav"); // 设置**请求**作用域属性
        mav.setViewName("SUCCESS"); // 设置视图名称，实现页面跳转
        return mav; // 与外部 Servlet 交互
    }

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public String map(Map<String, Object> map) {
        map.put("key", "map");
        return "SUCCESS";
    }

    @RequestMapping(value = "/model", method = RequestMethod.GET)
    public String model(Model model) {
        model.addAttribute("key", "model");
        return "SUCCESS";
    }

    @RequestMapping(value = "/redirect", method = RequestMethod.POST)
    public String redirect(Model model) {
        model.addAttribute("key", "redirect");
        return "redirect:index.jsp";
    }
}
