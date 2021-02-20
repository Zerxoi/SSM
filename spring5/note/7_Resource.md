# 7 Resource


## 7.1 Resource

参考：

[Resources](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#resources)

[spring classpath:和classpath*:区别和实际应用](https://blog.csdn.net/qq_30038111/article/details/82116559)

[Spring加载resource时classpath*:与classpath:的区别](https://blog.csdn.net/kkdelta/article/details/5507799)

Spring的 `Resource` 接口旨在成为一种功能更强大的接口，用于抽象化对低级资源的访问。

```java
public interface Resource extends InputStreamSource {

    boolean exists();

    boolean isOpen();

    URL getURL() throws IOException;

    File getFile() throws IOException;

    Resource createRelative(String relativePath) throws IOException;

    String getFilename();

    String getDescription();
}
```

Resource 接口继承了 InputStreamSource 接口

```java
public interface InputStreamSource {
    InputStream getInputStream() throws IOException;
}
```

当需要资源时，Spring 本身广泛使用 `Resource` 实例作为许多方法签名中的参数类型。在一些 Spring API 中的其他方法（如各种 `ApplicationContext` 实现的构造函数）取一个 String，以不加修饰或简单的形式用于创建适合该上下文实现的 `Resource`，或者通过 String 路径上的**特殊前缀**，让调用者指定必须创建和使用特定的 `Resource` 实现。

Resource 加载前缀

|Prefix|Example|Explanation|
|------|-------|-----------|
|`classpath:`|`classpath:com/myapp/config.xml`|获取与给定名称匹配的第一个类路径资源|
|`file:`|`file:///data/config.xml`|从文件系统中加载资源|
|`ftp:`|`ftp://speedtest.tele2.net`|通过 FTP 中加载资源|
|`http:`|`http://myserver/logo.png`|通过 HTTP 协议加载资源|
|`https:`|`https://myserver/logo.png`|通过 HTTPS 协议加载资源|
|`(none)`|`/data/config.xml`|由 `ApplicationContext` 的实现类决定，如果实现类是 `ClassPathXmlApplicationContext` 返回 `ClassPathResource`；如果实现类是 `FileSystemXmlApplicationContext` 返回 `FileSystemResource`等|
|`classpath*:`|`classpath*:conf/appContext.xml`|获取与给定名称匹配的所有类路径资源（本质上是通过调用 `ClassLoader.getResources`），然后合并形成最终的应用程序上下文定义|


注：[`classpath:` 使用 Ant-style 模式可能找不到资源](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#resources-wildcards-in-path-other-stuff)

`classpath:` 和 `classpath*:` 的区别

- `classpath:`：获取类路径中**第一个**满足根目录路径的目录，并在该目录中找到满足的资源名条件的资源
- `classpath*:` ：获取类路径中**所有**满足根目录路径的目录，并在所有根目录中找到满足的资源名条件的资源

可以通过 debug 源码查看详情

例如： `xyz/zerxoi/dao/**/*.class`

根目录路径为 `xyz/zerxoi/dao/`，资源匹配为 `**/*.class`。在测试类中运行如下代码

```java
@Test
public void testClasspathResources() throws IOException {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
    Resource[] resources = context.getResources("classpath:xyz/zerxoi/**/*.class");
    for (Resource resource : resources) {
        System.out.println(resource.getURL());
    }
    context.close();
}
```

结果如下

```
file:/C:/Users/Zerxoi/LearnNote/SSM/spring5/src/spring/xml-bean/target/test-classes/xyz/zerxoi/IocInterfaceTest.class
file:/C:/Users/Zerxoi/LearnNote/SSM/spring5/src/spring/xml-bean/target/test-classes/xyz/zerxoi/ResourceTest.class
file:/C:/Users/Zerxoi/LearnNote/SSM/spring5/src/spring/xml-bean/target/test-classes/xyz/zerxoi/SpringTest.class
```

`classpath:`  只能找到测试类路径中的资源

如果改成 `classpath*:xyz/zerxoi/**/*.class`

```
file:/C:/Users/Zerxoi/LearnNote/SSM/spring5/src/spring/xml-bean/target/test-classes/xyz/zerxoi/IocInterfaceTest.class
file:/C:/Users/Zerxoi/LearnNote/SSM/spring5/src/spring/xml-bean/target/test-classes/xyz/zerxoi/ResourceTest.class
file:/C:/Users/Zerxoi/LearnNote/SSM/spring5/src/spring/xml-bean/target/test-classes/xyz/zerxoi/SpringTest.class
file:/C:/Users/Zerxoi/LearnNote/SSM/spring5/src/spring/xml-bean/target/classes/xyz/zerxoi/dao/UserDao.class
file:/C:/Users/Zerxoi/LearnNote/SSM/spring5/src/spring/xml-bean/target/classes/xyz/zerxoi/dao/impl/UserDaoImpl.class
file:/C:/Users/Zerxoi/LearnNote/SSM/spring5/src/spring/xml-bean/target/classes/xyz/zerxoi/pojo/Book.class
file:/C:/Users/Zerxoi/LearnNote/SSM/spring5/src/spring/xml-bean/target/classes/xyz/zerxoi/pojo/Dept.class
file:/C:/Users/Zerxoi/LearnNote/SSM/spring5/src/spring/xml-bean/target/classes/xyz/zerxoi/pojo/Emp.class
file:/C:/Users/Zerxoi/LearnNote/SSM/spring5/src/spring/xml-bean/target/classes/xyz/zerxoi/pojo/LifeCycle.class
file:/C:/Users/Zerxoi/LearnNote/SSM/spring5/src/spring/xml-bean/target/classes/xyz/zerxoi/pojo/MyBean.class
```

`classpath*:` 则是获取所有类路径中的资源

## 7.2 ResourceLoader

`ResourceLoader` 接口旨在由可以返回（即加载）`Resource` 实例的对象实现。以下清单显示了 `ResourceLoader` 接口定义：

```java
public interface ResourceLoader {

    Resource getResource(String location);
}
```

**所有应用程序上下文均实现 `ResourceLoader` 接口**。因此，所有应用程序上下文都可用于获取 `Resource` 实例。

当您在特定的应用程序上下文中调用 `getResource()`，并且指定的位置路径没有特定的前缀时，您将获得适合该特定应用程序上下文的 `Resource` 类型。

## 7.2 Ant-pattern

参考：

[Ant path style patterns](https://stackoverflow.com/questions/2952196/ant-path-style-patterns)

