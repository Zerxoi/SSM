package xyz.zerxoi.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import xyz.zerxoi.bean.Employee;

@Repository
public class EmployeeDao {
    private static Map<Integer, Employee> map = new HashMap<>();
    private static Integer initId = 10005;

    static {
        map.put(10001, new Employee(10001, "张三", "zs@gmail.com", 33, 1));
        map.put(10002, new Employee(10002, "李四", "ls@163.com", 24, 2));
        map.put(10003, new Employee(10003, "王五", "wu@qq.com", 15, 3));
        map.put(10004, new Employee(10004, "赵六", "zl@126.com", 36, 4));
    }

    public Collection<Employee> getEmployees() {
        return map.values();
    }

    public Employee getEmployee(Integer id) {
        return map.get(id);
    }

    public void save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(initId++);
        }
        map.put(employee.getId(), employee);
    }

    public Employee delete(Integer id) {
        return map.remove(id);
    }
}
