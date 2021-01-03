package xyz.zerxoi.pojo;

import java.util.Date;
import java.util.List;

public class DetailedPost {
    private Integer id;
    private Date created_on;
    private String body;
    private Author author;
    private List<Comment> comments;

    public DetailedPost(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DetailedPost [author=" + author + ", body=" + body + ", comments=" + comments + ", create_on="
                + created_on + ", id=" + id + "]";
    }
}
