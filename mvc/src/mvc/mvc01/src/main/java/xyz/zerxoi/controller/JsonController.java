package xyz.zerxoi.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.zerxoi.bean.DetailedEmployee;
import xyz.zerxoi.service.EmployeeService;

@Controller
public class JsonController {
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/json/employee")
    @ResponseBody
    public Collection<DetailedEmployee> jsonEmployee() {
        return employeeService.getEmployees();
    }
}
