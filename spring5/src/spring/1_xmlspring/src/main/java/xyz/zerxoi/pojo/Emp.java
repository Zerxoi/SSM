package xyz.zerxoi.pojo;

import java.math.BigDecimal;

public class Emp {
    private String name;
    private BigDecimal salary;
    // 员工属于某一个部门，适用对象形式表示
    private Dept dept;

    public Dept getDept() {
        return dept;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    
    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp [dept=" + dept + ", name=" + name + ", salary=" + salary + "]";
    }
}
