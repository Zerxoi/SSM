package xyz.zerxoi.dao;

import java.util.List;

import xyz.zerxoi.pojo.Author;

public interface AuthorDao {
    public int insertAuthor(Author author);
    public int[] insertBatchAuthors(List<Object[]> authors);
    public int selectCount();
    public Author selectAuthor(Integer id);
    public List<String> selectAuthorUsernames();
    public List<Author> selecAuthors();
}
