package xyz.zerxoi.uitls;

import java.io.IOException;
import java.io.InputStream;

// import javax.sql.DataSource;

// import com.alibaba.druid.pool.DruidDataSourceFactory;

import org.apache.ibatis.io.Resources;
// import org.apache.ibatis.mapping.Environment;
// import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
// import org.apache.ibatis.transaction.TransactionFactory;
// import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

// import xyz.zerxoi.dao.PostMapper;

public class MyBatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSession getSqlSession() {
        if (sqlSessionFactory != null) {
            return sqlSessionFactory.openSession();
        }
        // Properties properties = new Properties();
        // DataSource dataSource = null;
        // try {
        // properties.load(Resources.getResourceAsReader("druid.properties"));
        // dataSource = DruidDataSourceFactory.createDataSource(properties);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // TransactionFactory transactionFactory = new JdbcTransactionFactory();
        // Environment environment = new Environment("development", transactionFactory,
        // dataSource);
        // Configuration configuration = new Configuration(environment);
        // configuration.addMapper(PostMapper.class);
        // sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // Properties properties = new Properties();
        // properties.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        // properties.setProperty("url", "jdbc:mysql://localhost:3306/mybatis");
        // properties.setProperty("username", "root");
        // properties.setProperty("password", "6019");
        // sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
        return sqlSessionFactory.openSession();
    }
}
