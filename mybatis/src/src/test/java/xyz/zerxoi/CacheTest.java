package xyz.zerxoi;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import xyz.zerxoi.dao.AuthorMapper;
import xyz.zerxoi.pojo.Author;
import xyz.zerxoi.uitls.MyBatisUtils;

public class CacheTest {
    @Test
    public void firstLevelCacheTest() {
        SqlSession session = MyBatisUtils.getSqlSession();
        AuthorMapper mapper = session.getMapper(AuthorMapper.class);
        mapper.selectAuthorById(1);
        System.out.println("==========================");
        mapper.selectAuthorById(1);
        System.out.println("==========================");
        mapper.insertAuthor(new Author(3, "kobayashi", "tohru", "kobayashi@gmail.com", null));
        // session.clearCache();
        System.out.println("==========================");
        mapper.selectAuthorById(1);
        session.close();
    }

    @Test
    public void secondLevelCacheTest() {
        SqlSession session1 = MyBatisUtils.getSqlSession();
        AuthorMapper mapper1 = session1.getMapper(AuthorMapper.class);
        Author author1 = mapper1.selectAuthorById(1);
        System.out.println("author1: " + author1);
        session1.commit();
        author1.setUsername("cache");
        System.out.println("修改author1的 username 属性为 cache");
        System.out.println("==========================");
        SqlSession session2 = MyBatisUtils.getSqlSession();
        AuthorMapper mapper2 = session2.getMapper(AuthorMapper.class);
        Author author2 = mapper2.selectAuthorById(1);
        System.out.println(author1 == author2);
        System.out.println("author2: " + author2);
        session1.close();
        session2.close();

    }
}
