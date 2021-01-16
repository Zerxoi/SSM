# JdbcTemplate

参考：[Data Access with JDBC](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#jdbc)

## 概念

Spring 框架对之前的 JDBC 进行封装，使用 JdbcTemplate 方便实现对数据库操作。

## 1 准备部分

### 1.1 依赖准备

引入依赖 `com.alibaba.druid`，`mysql.mysql-connector-java`，`org.springframework.spring-jdbc`（除了依赖于 `spring-core` 和 `spring-beans`, 还依赖于 `spring-tx` 用于事务控制） 和 `org.springframework.spring-orm`(可选用于整合数据库框架)

依赖关系图

```
xyz.zerxoi:jdbc-template:jar:1.0-SNAPSHOT
+- org.springframework:spring-context:jar:5.3.2:compile
|  +- org.springframework:spring-aop:jar:5.3.2:compile
|  +- org.springframework:spring-beans:jar:5.3.2:compile
|  +- org.springframework:spring-core:jar:5.3.2:compile
|  |  \- org.springframework:spring-jcl:jar:5.3.2:compile
|  \- org.springframework:spring-expression:jar:5.3.2:compile
+- org.springframework:spring-aspects:jar:5.3.2:compile
|  \- org.aspectj:aspectjweaver:jar:1.9.6:compile
+- org.springframework:spring-jdbc:jar:5.3.2:compile
|  \- org.springframework:spring-tx:jar:5.3.2:compile
+- org.springframework:spring-orm:jar:5.3.2:compile
+- com.alibaba:druid:jar:1.2.4:compile
|  \- javax.annotation:javax.annotation-api:jar:1.3.2:compile
+- mysql:mysql-connector-java:jar:8.0.22:compile
|  \- com.google.protobuf:protobuf-java:jar:3.11.4:compile
\- junit:junit:jar:4.12:test
   \- org.hamcrest:hamcrest-core:jar:1.3:test
```

### 1.2 配置文件配置数据库配置连接池

使用基于 setter 的依赖注入来注入 `driverClassName`，`url` 等属性。

`com.alibaba.druid.pool.DruidDataSource` 的父类中有对应的 `setDriverClassName`，`setUrl` 等 setter 方法用于设置 Bean 的属性。

```xml
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close">
    <property name="driverClassName" value="com.mysql.cj.jdbc.Driver" />
    <property name="url" value="jdbc:mysql://localhost:3306/mybatis" />
    <property name="username" value="root" />
    <property name="password" value="6019" />
</bean>
```

### 1.3 配置 JdbcTemplate 对象，注入 DataSource

```xml
<!-- JdbcTemplate 对象 -->
<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
    <!-- 注入 dataSource 对象 -->
    <property name="dataSource" ref="dataSource" />
</bean>
```

### 1.4 创建 service 类，创建 dao 类，在 dao 注入 jdbcTemplate 对象

在配置文件中开启组件扫描

```xml
<context:component-scan base-package="xyz.zerxoi"></context:component-scan>
```

Dao 对象的创建及 JdbcTemplate 对象的注入

```java
public interface AuthorDao {
    
}

@Repository
public class AuthorDaoImpl implements AuthorDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
}
```

Service 对象创建及 Dao 对象的注入

```java
public interface AuthorService {
    
}

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorDao authorDao;
}
```


### 1.5 JdbcTemplate 增删改

使用 `JdbcTemplate` 对象的 `update` 方法实现添加、删除、修改操作，使用 `query` 方法实现查询操作。

```java
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
}

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorDao authorDao;

    @Override
    public int insertAuthor(Author author) {
        return authorDao.insertAuthor(author);
    }
}
```

测试代码

```java
ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
AuthorService bean = context.getBean("authorServiceImpl", AuthorService.class);
Author author = new Author();
author.setUsername("动漫高手");
author.setPassword("yyds");
author.setEmail("acgking@163.com");
author.setInterests("cpp,golang");
System.out.println(bean.insertAuthor(author));
context.close();
```

### 1.6 JdbcTemplate 查询一个对象

`JdbcTemplate.selectForObject` 会返回一个行结果，如果**返回多行结果则出现异常**；通过使用 `ResultMapper` 参数来进行结果映射。

#### 1.6.1 单个值（一行一列）

如果结果行只有一列，可以通过 `RowMapper` 来进行结果映射。

```java
@Override
public int selectCount() {
    String sql = "select count(*) from t_author";
    return jdbcTemplate.queryForObject(sql, new RowMapper<Integer>() {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getInt(1);
        }
    });
}
```

也可以使用使用 requiredType 参数来指定需求类型来进行将结果进行类型转换。

```java
@Override
public int selectCount() {
    String sql = "select count(*) from t_author";
    return jdbcTemplate.queryForObject(sql, Integer.class);
}
```

#### 1.6.2 一个对象（一行多列）

使用 `RowMapper` 来将多个列转换成相应的对象。

```java
@Override
public Author selectAuthor(Integer id) {
    String sql = "select * from t_author where id = ?";
    return jdbcTemplate.queryForObject(sql, new RowMapper<Author>() {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setUsername(rs.getString("username"));
            author.setPassword(rs.getString("password"));
            author.setEmail(rs.getString("email"));
            author.setInterests(rs.getString("interests"));
            return author;
        }
    }, id);
}
```

也可以使用 `BeanPropertyRowMapper` 来指定返回的对象类型，它通过对象类型的 setter 方法来设置对象属性。

```java
@Override
public Author selectAuthor(Integer id) {
    String sql = "select * from t_author where id = ?";
    return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Author.class), id);
}
```

### 1.7 JdbcTemplate 查询集合

#### 1.7.1 单列结果集

`jdbcTemplate.queryForList` 智能处理单列的结果映射，不能处理 Bean 对象的结果映射。

```java
@Override
public List<String> selectAuthorUsernames() {
    String sql = "select username from t_author";
    return jdbcTemplate.queryForList(sql, String.class);
}
```

#### 1.7.2 多列结果集（Bean对象结果映射）

使用 `jdbcTemplate.queryForList` 返回 Bean 对象集合，给定 `RowMapper` 参数来自定义实现结果映射

```java
@Override
public List<Author> selecAuthors() {
    String sql = "select * from t_author";
    return jdbcTemplate.query(sql, new RowMapper<Author>(){
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setUsername(rs.getString("username"));
            author.setPassword(rs.getString("password"));
            author.setEmail(rs.getString("email"));
            author.setInterests(rs.getString("interests"));
            return author;
        }
    });
}
```

也可以是同 `BeanPropertyRowMapper` 实现相同的效果。

```java
@Override
public List<Author> selecAuthors() {
    String sql = "select * from t_author";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Author.class));
}
```

### 1.8 批量操作

通过 `JdbcTemplate.batchUpdate` 实现批量增删改，其本质上是对 `List` 集合进行遍历多次调用 SQL 语句，将每个占位符 `?` 替换为 `Object[]` 中的元素。

```java
@Override
public int[] insertBatchAuthors(List<Object[]> authors) {
    String sql = "insert into t_author(username, password, email, interests) values(?, ?, ?, ?)";
    return jdbcTemplate.batchUpdate(sql, authors);
}
```
测试结果

```java
ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
AuthorDao bean = context.getBean("authorDaoImpl", AuthorDao.class);
List<Object[]> authors = new ArrayList<Object[]>();
Object[] author1 = new Object[]{"动漫高手","yyds","acgking@163.com","cpp,golang"};
Object[] author2 = new Object[]{"茄子","wdnmd","wdnmd@163.com","java,c"};
authors.add(author1);
authors.add(author2);
System.out.println(Arrays.toString(bean.insertBatchAuthors(authors)));
context.close();
```