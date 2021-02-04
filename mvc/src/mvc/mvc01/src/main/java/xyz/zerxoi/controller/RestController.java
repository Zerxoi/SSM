package xyz.zerxoi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RestController {
    @RequestMapping(value = "/testREST/{id}", method = RequestMethod.GET)
    public String getUserById(@PathVariable("id") Integer id) {
        System.out.println("GET ID:" + id);
        return "SUCCESS";
    }

    @RequestMapping(value = "/testREST", method = RequestMethod.POST)
    public String insertUser(Integer id) {
        System.out.println("POST ID:" + id);
        // DefaultServletHandlerConfigurer
        return "SUCCESS";
    }

    @RequestMapping(value = "/testREST", method = RequestMethod.PUT)
    public String updateUser(Integer id) {
        System.out.println("UPDATE ID:" + id);
        return "SUCCESS";
    }

    @RequestMapping(value = "/testREST/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("id") Integer id) {
        System.out.println("DELETE ID:" + id);
        return "SUCCESS";
    }

    @RequestMapping(value = "/testAJAX/{id}", method = RequestMethod.DELETE)
    public void ajaxDelete(@PathVariable("id") Integer id) {
        System.out.println("testAJAX id:" + id);
    }
}
