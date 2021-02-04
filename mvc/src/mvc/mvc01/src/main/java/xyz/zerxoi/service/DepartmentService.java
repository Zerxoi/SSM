package xyz.zerxoi.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.zerxoi.bean.Department;
import xyz.zerxoi.dao.DepartmentDao;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    public Collection<Department> getDepartments() {
        return departmentDao.getDepartments();
    }
}
