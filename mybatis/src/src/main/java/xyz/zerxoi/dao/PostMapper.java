package xyz.zerxoi.dao;

import java.util.List;


import xyz.zerxoi.pojo.DetailedPost;
import xyz.zerxoi.pojo.Post;

public interface PostMapper {
    public List<Post> selectPosts();

    public Post selectPostById(Integer id);

    public DetailedPost selectDetailedPost(Integer id);

    public DetailedPost selectDetailedPostBySP(Integer id);
}
