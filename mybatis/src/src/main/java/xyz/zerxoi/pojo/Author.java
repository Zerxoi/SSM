package xyz.zerxoi.pojo;

import java.io.Serializable;
import java.util.List;

public class Author implements Serializable {

    private static final long serialVersionUID = -6249882553456503233L;
    private Integer id;
    private String username;
    private String password;
    private String email;
    private List<String> interests;

    public Author() {
    }

    public Author(Integer id, String username, String password, String email, List<String> interests) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.interests = interests;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    @Override
    public String toString() {
        return "Author [email=" + email + ", id=" + id + ", interests=" + interests + ", password=" + password
                + ", username=" + username + "]";
    }
}
