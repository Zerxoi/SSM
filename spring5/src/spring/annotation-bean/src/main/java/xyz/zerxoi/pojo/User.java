package xyz.zerxoi.pojo;

public class User {
    private String name;
    private Integer age;
    private String address;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void speak() {
        System.out.println("Speaking");
    }

    @Override
    public String toString() {
        return "User [address=" + address + ", age=" + age + ", name=" + name + "]";
    }
}
