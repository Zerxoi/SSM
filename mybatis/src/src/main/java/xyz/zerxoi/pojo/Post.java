package xyz.zerxoi.pojo;

import java.sql.Date;

public class Post {
    private Integer id;
    private Integer author_id;
    private Date created_on;
    private String body;

    @Override
    public String toString() {
        return "Post [author_id=" + author_id + ", body=" + body + ", created_on=" + created_on + ", id=" + id + "]";
    }

}
