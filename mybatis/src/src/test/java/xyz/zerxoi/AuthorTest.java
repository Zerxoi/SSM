package xyz.zerxoi;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Collection;
// import java.util.HashMap;
// import java.util.Iterator;
import java.util.List;
// import java.util.Map;

// import org.apache.ibatis.annotations.Mapper;
// import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
// import org.apache.ibatis.type.TypeHandlerRegistry;
// import org.apache.ibatis.type.TypeReference;
import org.junit.Test;

import xyz.zerxoi.dao.AuthorMapper;
import xyz.zerxoi.pojo.Author;
import xyz.zerxoi.uitls.MyBatisUtils;

public class AuthorTest {
    @Test
    public void selectAuthorsTest() {
        try (SqlSession session = MyBatisUtils.getSqlSession()) {
            AuthorMapper mapper = session.getMapper(AuthorMapper.class);
            List<Author> authors = new ArrayList<>();
            authors.add(new Author(4, "dio", "konodioda", "dio@jojo.com", Arrays.asList("wryyyyyyy", "赛高尼嗨铁鸭子哒")));
            authors.add(new Author(5, "jotaro", "starplatinum", "jotaro@jojo.com", Arrays.asList("欧拉", "呀嘞呀嘞daze")));
            mapper.insertAuthors(authors);
            System.out.println(authors);
            // session.commit();
        }
    }

    @Test
    public void aTest() {
        try (SqlSession session = MyBatisUtils.getSqlSession()) {
            AuthorMapper mapper = session.getMapper(AuthorMapper.class);
            List<Author> authors = new ArrayList<>();
            authors.add(new Author(4, "xxxxxx", "konodioda", "dio@jojo.com", Arrays.asList("wryyyyyyy", "赛高尼嗨铁鸭子哒")));
            // authors.add(new Author(5, "------", "starplatinum", "jotaro@jojo.com",
            // Arrays.asList("欧拉", "呀嘞呀嘞daze")));
            authors.add(new Author(5, "------", "123456", "jotaro@jojo.com", Arrays.asList("欧拉", "呀嘞呀嘞daze")));
            mapper.insertAuthors(authors);
            System.out.println(mapper.selectAuthorsBy(new Author(1, null, "", "", null)));
            // Map<String, Object> map = new HashMap<>();
            // map.put("id", 6);
            // map.put("username","xxxxx");
            // map.put("password","xxxxx");
            // map.put("email","xxxxx");
            // map.put("interests",Arrays.asList("欧拉", "呀嘞呀嘞daze"));
            // mapper.insertAuthor(map);
            mapper.deleteAuthors(new Integer[] { 4, 5 });
            // session.commit();
        }

    }
}

class TypeToken<T> {
    Class<?> clz;

    protected TypeToken() {
        ParameterizedType parameterized = (ParameterizedType) getClass().getGenericSuperclass();
        Type type = parameterized.getActualTypeArguments()[0];
        clz = (Class<?>) ((ParameterizedType) type).getRawType();

    }

    public Class<?> getClz() {
        return clz;
    }
}