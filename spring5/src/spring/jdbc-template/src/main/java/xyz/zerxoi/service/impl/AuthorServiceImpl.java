package xyz.zerxoi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.zerxoi.dao.AuthorDao;
import xyz.zerxoi.pojo.Author;
import xyz.zerxoi.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorDao authorDao;

    @Override
    public int insertAuthor(Author author) {
        return authorDao.insertAuthor(author);
    }
}
