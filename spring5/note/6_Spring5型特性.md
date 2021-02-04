# Spring5 框架新特性

Spring 5 框架为了充分利用 Java 8 特性，它的代码库已进行了改进，而且该框架要求将 Java 8 作为最低的 JDK 版本。

---

## 日志

参考：

[Logging](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#spring-jcl)

从Spring Framework 5.0开始，Spring自带了自己的[Commons Logging](https://cloud.tencent.com/developer/article/1334226)，在 `spring-jcl` 模块中实现。该实现会检查 `classpath` 中是否存在 `Log4j 2.x API` 和 `SLF4J 1.7 API`，并使用其中第一个发现的 API 作为日志记录的实现，如果 `Log4j 2.x` 和 `SLF4J` 都不可用，则回落到Java平台的核心日志记录设施（也称为 `JUL` 或 `java.util.logging`）。

**使用 log4j2**

[log4j2配置文件log4j2.xml配置详解](https://blog.csdn.net/thekenofDIS/article/details/80439776)

1. 引入 `log4j2` 依赖

```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>${log4j.version}</version>
</dependency>
```

2. 在 `classpath` 中创建一个 `log4j2.xml` 文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
  <Appenders>
    <Console name="Console" target="SYSTEM_OUT">
      <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
    </Console>
  </Appenders>
  <Loggers>
    <Root level="info">
      <AppenderRef ref="Console"/>
    </Root>
  </Loggers>
</Configuration>
```

3. 在 Spring5 启动时 `spring-jcl` 实现会检查 `classpath` 中是否存在 `Log4j 2.x API`，并使用其作为日志输出实现。

---

## `@Nullable` 注解

`@Nullable` 注解可以使用在方法，属性，参数上面，表示方法返回值，属性值，方法参数可以为空。

---

## 函数式风格 GenericApplicationContext

函数式风格创建对象，交给 Spring 进行管理。

```java
// 创建 GenericApplicationContext 上下文
GenericApplicationContext context = new GenericApplicationContext();
// 注册 Bean 对象
context.refresh();
context.registerBean("account", Account.class);
// 获取 Spring 注册的对象
Account account = (Account) context.getBean("account");
System.out.println(account);
context.close();
```

---

## 测试

**Spring5 支持整合 JUnit5**

整合 JUnit4

参考：[Spring JUnit 4 Runner](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#testcontext-junit4-runner)

导入 `spring-test` 依赖

```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-test</artifactId>
  <version>${spring.version}</version>
  <scope>test</scope>
</dependency>
```

创建测试类，使用注解方式完成

```java
@RunWith(SpringJUnit4ClassRunner.class) // 指定单元测试框架测试版本
@ContextConfiguration("classpath:bean.xml") // 加载配置文件
public class JUnit4Test {
    // 将 Spring 创建的 AccountService 对象注入到属性中
    @Autowired
    AccountService accountService;

    @Test
    public void accountServiceTest() {
        System.out.println(accountService);
        accountService.transfer("bob", "alice", 1000);
    }
}
```

整合 JUnit5

参考：[Spring JUnit Jupiter Testing Annotations](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#integration-testing-annotations-junit-jupiter)

引入 JUnit5 依赖和 `spring-test` 依赖

```xml
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter-api</artifactId>
  <version>5.7.0</version>
  <scope>test</scope>
</dependency>
```

创建测试类，使用注解方式完成

```java
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:bean.xml")
public class JUnit5Test {
    // 将 Spring 创建的 AccountService 对象注入到属性中
    @Autowired
    AccountService accountService;

    @Test
    public void accountServiceTest() {
        System.out.println(accountService);
        accountService.transfer("alice", "bob", 1000);
    }
}
```

`@SpringJUnitConfig` 一个合并 `@ExtendWith` 和 `@ContextConfiguration` 的符合注解，可以用 `@SpringJUnitConfig` 提上上述注解。

```java
@SpringJUnitConfig(locations = "classpath:bean.xml")
public class JUnit5Test {
    // 将 Spring 创建的 AccountService 对象注入到属性中
    @Autowired
    AccountService accountService;

    @Test
    public void accountServiceTest() {
        System.out.println(accountService);
        accountService.transfer("alice", "bob", 1000);
    }
}
```

---

## WebFlux

<!-- TODO: 学习完 Spring MVC 后补充 -->
### 介绍

Spring WebFlux 是 Spring5 添加的用于 Web 开发的新的模块，功能和 Spring MVC 类似，WebFlux 是使用响应式编程的框架。

传统的 Web 框架，比如 Spring MVC基于 Servlet 容器；WebFlux 是一种一部非阻塞的框架，异步非阻塞的框架，异步非阻塞框架在 Servlet3.1 以后才支持。WebFlux 的核心是基于 Reactor 的相关 API 实现的。

**异步非阻塞**

## 特点

1. 非阻塞：在有限的资源下，提高系统吞吐量和伸缩性，以 Reactor 为基础实现响应式编程
2. 函数式编程，Spring5 框架基于 Java8，WebFlux 使用 Java8 函数式编程方式实现路由请求

## 比较 SpringMVC