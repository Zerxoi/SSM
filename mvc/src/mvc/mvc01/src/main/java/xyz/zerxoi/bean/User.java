package xyz.zerxoi.bean;

public class User {
    private Integer id;
    private String username;
    private String email;
    private Integer age;
    private Integer addressId;

    public User() {
    }

    public User(Integer id, String username, String email, Integer age, Integer addressId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;
        this.addressId = addressId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "User [addressId=" + addressId + ", age=" + age + ", id=" + id + ", email=" + email + ", username="
                + username + "]";
    }

}
