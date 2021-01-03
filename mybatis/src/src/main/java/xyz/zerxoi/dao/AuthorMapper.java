package xyz.zerxoi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cursor.Cursor;

import xyz.zerxoi.pojo.Author;

public interface AuthorMapper {
    public List<Author> selectAuthors();

    @MapKey("id")
    public Map<String, Object> selectAuthorMaps();

    public List<Author> selectAuthorsByIds(@Param("ids") List<Integer> ids);

    public List<Author> selectAuthorsByIds(@Param("ids") Integer[] ids);

    public List<Author> selectAuthorsBy(Author author);

    public Author selectAuthorById(Integer id);

    public Map<String, Object> selectAuthorMapById(Integer id);

    public Cursor<Author> selectAuthorCursor();

    public Cursor<Author> selectAuthorCursorById(Integer id);

    public int insertAuthor(Author author);

    public int insertAuthor(Map<String, Object> author);

    public int insertAuthors(@Param("authors") Author[] authors);

    public int insertAuthors(@Param("authors") List<Author> authors);

    public int updateAuthor(Author author);

    public int deleteAuthor(Author author);

    public int deleteAuthor(Integer id);

    public int updateAuthors(@Param("authors") List<Author> authors);

    public int updateAuthors(@Param("authors") Author[] authors);

    public int deleteAuthors(@Param("ids") List<Integer> ids);

    public int deleteAuthors(@Param("ids") Integer[] ids);
}
