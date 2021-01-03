package xyz.zerxoi;

// import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
// import xyz.zerxoi.pojo.Post;

import xyz.zerxoi.dao.PostMapper;
import xyz.zerxoi.uitls.MyBatisUtils;

public class PostTest {
    @Test
    public void selectPostDetailsTest() {
        try (SqlSession session = MyBatisUtils.getSqlSession()) {
            PostMapper mapper = session.getMapper(PostMapper.class);

            System.out.println(mapper.selectDetailedPost(1));
            session.commit();
            
        }
    }

    @Test
    public void selectPostAuthorCommentsByIdTest() {
        try (SqlSession session = MyBatisUtils.getSqlSession()) {
            PostMapper mapper = session.getMapper(PostMapper.class);

            System.out.println(mapper.selectDetailedPostBySP(1));
            session.commit();
            
        }
    }

    @Test
    public void myBatisTest() {
        try (SqlSession session = MyBatisUtils.getSqlSession()) {
            // List<Post> posts = session.selectList("xyz.zerxoi.dao.PostMapper.selectPosts");
            // System.out.println(posts);
            // Post post = session.selectOne("xyz.zerxoi.dao.PostMapper.selectPostById", 1);
            // System.out.println(post);
            PostMapper mapper = session.getMapper(PostMapper.class);
            // List<Post> posts = mapper.selectPosts();
            // System.out.println(posts);
            // Post post = mapper.selectPostById(1);
            // System.out.println(post);
            // System.out.println(mapper.selectPostAuthorCommentsById(1));
            System.out.println(mapper.selectDetailedPost(1));
            // session.commit();
            
        }
    }
}
