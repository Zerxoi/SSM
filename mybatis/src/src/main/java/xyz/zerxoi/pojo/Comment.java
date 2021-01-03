package xyz.zerxoi.pojo;

public class Comment {
    private Integer id;
    private Integer author_id;
    private Integer post_id;
    private String text;

    @Override
    public String toString() {
        return "Comment [author_id=" + author_id + ", id=" + id + ", post_id=" + post_id + ", text=" + text + "]";
    }
}
