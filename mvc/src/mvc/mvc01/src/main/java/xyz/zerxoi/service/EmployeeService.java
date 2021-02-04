package xyz.zerxoi.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.zerxoi.bean.DetailedEmployee;
import xyz.zerxoi.bean.Employee;
import xyz.zerxoi.dao.DepartmentDao;
import xyz.zerxoi.dao.EmployeeDao;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DepartmentDao departmentDao;

    public Collection<DetailedEmployee> getEmployees() {
        Collection<DetailedEmployee> employees = new ArrayList<>();
        for (Employee employee : employeeDao.getEmployees()) {
            employees.add(new DetailedEmployee(employee.getId(), employee.getName(), employee.getEmail(),
                    employee.getAge(), departmentDao.getDepartment(employee.getDepartmentId())));
        }
        return employees;
    }

    public Employee getEmployee(Integer id) {
        return employeeDao.getEmployee(id);
    }

    public void save(Employee employee) {
        employeeDao.save(employee);
    }

    public void delete(Integer id) {
        employeeDao.delete(id);
    }
}
