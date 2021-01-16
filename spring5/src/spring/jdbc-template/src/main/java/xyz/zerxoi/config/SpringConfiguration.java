package xyz.zerxoi.config;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration // 作为配置类，替代 XML 配置文件
@ComponentScan(basePackages = {"xyz.zerxoi"}) // 开启组件扫描
// 等价于 <context:component-scan base-package="xyz.zerxoi"></context:component-scan>
@EnableTransactionManagement // 开启注解驱动的事务管理
public class SpringConfiguration {
    
    @Bean
    // 创建数据库连接池
    public DruidDataSource getDruidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis");
        dataSource.setUsername("root");
        dataSource.setPassword("6019");
        return dataSource;
    }

    @Bean
    // 创建 JdbcTemplate 对象
    // IoC 容器将类型为 DataSource 的 Bean 对象注入到参数中
    public  JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    // 创建事务管理器
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager tx = new DataSourceTransactionManager();
        tx.setDataSource(dataSource);
        return tx;
    }
}