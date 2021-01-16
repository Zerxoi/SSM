package xyz.zerxoi.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import xyz.zerxoi.dao.AuthorDao;
import xyz.zerxoi.pojo.Author;

@Repository
public class AuthorDaoImpl implements AuthorDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insertAuthor(Author author) {
        String sql = "insert into t_author(username, password, email, interests) values(?, ?, ?, ?)";
        Object[] args = { author.getUsername(), author.getPassword(), author.getEmail(), author.getInterests() };
        return jdbcTemplate.update(sql, args);
    }

    @Override
    public Author selectAuthor(Integer id) {
        String sql = "select * from t_author where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Author.class), id);
        // return jdbcTemplate.queryForObject(sql, new RowMapper<Author>() {
        // @Override
        // public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Author author = new Author();
        // author.setId(rs.getInt("id"));
        // author.setUsername(rs.getString("username"));
        // author.setPassword(rs.getString("password"));
        // author.setEmail(rs.getString("email"));
        // author.setInterests(rs.getString("interests"));
        // return author;
        // }
        // }, id);
    }

    @Override
    public int selectCount() {
        String sql = "select count(*) from t_author";
        return jdbcTemplate.queryForObject(sql, Integer.class);
        // return jdbcTemplate.queryForObject(sql, new RowMapper<Integer>() {
        // @Override
        // public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        // return rs.getInt(1);
        // }
        // });
    }

    @Override
    public List<String> selectAuthorUsernames() {
        String sql = "select username from t_author";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public List<Author> selecAuthors() {
        String sql = "select * from t_author";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Author.class));
        // return jdbcTemplate.query(sql, new RowMapper<Author>(){
        // @Override
        // public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Author author = new Author();
        // author.setId(rs.getInt("id"));
        // author.setUsername(rs.getString("username"));
        // author.setPassword(rs.getString("password"));
        // author.setEmail(rs.getString("email"));
        // author.setInterests(rs.getString("interests"));
        // return author;
        // }
        // });
    }

    @Override
    public int[] insertBatchAuthors(List<Object[]> authors) {
        String sql = "insert into t_author(username, password, email, interests) values(?, ?, ?, ?)";
        return jdbcTemplate.batchUpdate(sql, authors);
    }

}
