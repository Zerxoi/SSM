package xyz.zerxoi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import xyz.zerxoi.dao.AuthorDao;
import xyz.zerxoi.pojo.Author;
import xyz.zerxoi.service.AuthorService;

public class JdbcTemplateTest {
    @Test
    public void insertAuthorTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate.xml");
        AuthorService bean = context.getBean("authorServiceImpl", AuthorService.class);
        Author author = new Author();
        author.setUsername("动漫高手");
        author.setPassword("yyds");
        author.setEmail("acgking@163.com");
        author.setInterests("cpp,golang");
        System.out.println(bean.insertAuthor(author));
        context.close();
    }



    @Test
    public void queryAuthorTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate.xml");
        AuthorDao bean = context.getBean("authorDaoImpl", AuthorDao.class);
        System.out.println(bean.selectAuthor(1));
        context.close();
    }

    @Test
    public void queryAuthorUsernamesTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate.xml");
        AuthorDao bean = context.getBean("authorDaoImpl", AuthorDao.class);
        System.out.println(bean.selectAuthorUsernames());
        context.close();
    }

    @Test
    public void queryCountTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate.xml");
        AuthorDao bean = context.getBean("authorDaoImpl", AuthorDao.class);
        System.out.println(bean.selectCount());
        context.close();
    }

    @Test
    public void selectAuthorsTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate.xml");
        AuthorDao bean = context.getBean("authorDaoImpl", AuthorDao.class);
        System.out.println(bean.selecAuthors());
        context.close();
    }

    @Test
    public void insertBatchAuthorsTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("jdbctemplate.xml");
        AuthorDao bean = context.getBean("authorDaoImpl", AuthorDao.class);
        List<Object[]> authors = new ArrayList<Object[]>();
        Object[] author1 = new Object[]{"动漫高手","yyds","acgking@163.com","cpp,golang"};
        Object[] author2 = new Object[]{"茄子","wdnmd","wdnmd@163.com","java,c"};
        authors.add(author1);
        authors.add(author2);
        System.out.println(Arrays.toString(bean.insertBatchAuthors(authors)));
        context.close();
    }
}
