package xyz.zerxoi.bean;

public class Employee {
    private Integer id;
    private String name;
    private String email;
    private Integer age;
    private Integer departmentId;

    public Employee() {
    }

    public Employee(Integer id, String name, String email, Integer age, Integer departmentId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.departmentId = departmentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Employee [age=" + age + ", departmentId=" + departmentId + ", email=" + email + ", id=" + id + ", name="
                + name + "]";
    }
    
}
