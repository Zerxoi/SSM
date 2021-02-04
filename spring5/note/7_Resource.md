# 7 Resource


## 7.1 Resource

参考：

[Resources](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#resources)

[spring classpath:和classpath*:区别和实际应用](https://blog.csdn.net/qq_30038111/article/details/82116559)

[Spring加载resource时classpath*:与classpath:的区别](https://blog.csdn.net/kkdelta/article/details/5507799)

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

我的直观理解是 `classpath:` 会找到第一个满足与给定名称匹配的类资源的加载器，通过该加载器获取匹配的资源；`classpath*:` 会找到所有满足与给定名称匹配的类资源的加载器，通过该加载器获取匹配的资源。


## 7.2 Ant-pattern

参考：

[Ant path style patterns](https://stackoverflow.com/questions/2952196/ant-path-style-patterns)

