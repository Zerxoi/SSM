package xyz.zerxoi.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import xyz.zerxoi.bean.Department;

@Repository
public class DepartmentDao {
    private static Map<Integer, Department> map = new HashMap<>();
    static {
        map.put(1, new Department(1, "会计"));
        map.put(2, new Department(2, "销售"));
        map.put(3, new Department(3, "研发"));
        map.put(4, new Department(4, "产品"));
    }

    public Collection<Department> getDepartments() {
        return map.values();
    }

    public Department getDepartment(Integer id) {
        return map.get(id);
    }
}
