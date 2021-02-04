package xyz.zerxoi.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xyz.zerxoi.bean.DetailedEmployee;
import xyz.zerxoi.bean.Employee;
import xyz.zerxoi.service.DepartmentService;
import xyz.zerxoi.service.EmployeeService;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public String getEmployees(Model model) {
        Collection<DetailedEmployee> employees = employeeService.getEmployees();
        model.addAttribute("employees", employees);
        return "employees";
    }

    // 进入员工表单
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String getEmployee(Model model) {
        model.addAttribute("command", new Employee());
        model.addAttribute("departments", departmentService.getDepartments());
        return "employeeForm2";
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public String postEmployee(Employee employee) {
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    public String updateEmployee(Employee employee) {
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public String postEmployee(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("command", employeeService.getEmployee(id));
        model.addAttribute("departments", departmentService.getDepartments());
        return "employeeForm2";
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeService.delete(id);
        System.out.println("delete dfasdfasdfasd");
        return "redirect:/employees";
    }
}
