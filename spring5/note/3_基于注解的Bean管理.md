# 基于注解的Bean管理

## 1 什么是注解？

注解是代码的特殊标记

**格式**

```
@注解名称(属性名称 = 属性值[, 属性名称 =  属性值])
```

注解**作用目标**可以是属性，方法，类，包等多种对象

注解的**目的**是简化 XML 配置

## 2 Spring 针对 Bean 管理中对象创建的注解

1. `@Component`（普通：Bean）
2. `@Service`（逻辑层：Service层）
3. `@Controller`（Web层）
4. `@Repository`（Dao层）

上面四个注解的功能都是一样的，都可以用来创建 Bean 实例。

## 3 基于注解方式实现对象创建

1. 引入 `spring-aop` 依赖（注：`spring-context` 依赖于，所以不用额外导入该依赖）

2. 开启组件扫描

引入 context 命名空间，并设定扫名的包。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context" xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <!-- 开启组件扫描 -->
    <!-- 多个包使用逗号 (,) 隔开 -->
    <context:component-scan base-package="xyz.zerxoi.dao,xyz.zerxoi.service"></context:component-scan>
</beans>
```

如果要扫描多个包同样可以使用多个包的公共上层目录。

```xml
<context:component-scan base-package="xyz.zerxoi"></context:component-scan>
```

4. 创建类，在类上面添加创建对象注释

```java
// 等价于  <bean id="userDao" class="xyz.zerxoi.service.impl.UserDaoImpl" />
// value 值可以省略，如果省略 id 的默认值就是首字母小写的类名（userDaoImpl）
@Repository("userDao")
public class UserDaoImpl implements UserDao {
    @Override
    public User selectUser() {
        User user = new User();
        user.setAddress("China");
        user.setAge(18);
        user.setName("高中生");
        return user;
    }
}
```

5. 测试

```java
public class AnnotationTest {
    @Test
    public void annotationTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        UserDao bean = context.getBean("userDao", UserDao.class);
        System.out.println(bean.selectUser());
        context.close();
    }
}
```

## 4 开启组件扫描细节配置

示例一

`use-default-filters="false"` 不适用默认的 filter，而是用自己配置的 filter，该 filter 只会扫描 `xyz.zerxoi` 包中带有 `org.springframework.stereotype.Controller` 注解的类。

```xml
<context:component-scan base-package="xyz.zerxoi" use-default-filters="false">
    <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller" />
</context:component-scan>
```

示例二

使用默认的过滤器，同时会设定过滤器不扫描带有 `@Service` 注释的类。

```xml
<context:component-scan base-package="xyz.zerxoi">
    <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Service" />
</context:component-scan>
```

## 5 基于注解方式实现属性注入

属性注入的注解

- `@AutoWired` 根据属性类型进行自动注入
- `@Qualifier` 根据属性名称进行自动注入
- `@Resource` 可以根据类型注入，可以根据名称注入
- `@Value` 注入普通类型属性

注：注解不需要为该属性添加 set 方法

使用 **`@AutoWired`** 注解将 `UserDao` 对象注入到 `UserService` 对象中

1. 在`UserDaoImpl` 对象和 `UserServiceImpl` 对象中添加对象注释，以让 Spring 容器自动创建对象

```java
@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public User selectUser() {
        User user = new User();
        user.setAddress("China");
        user.setAge(18);
        user.setName("高中生");
        return user;
    }
}
```

```java
@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Override
    public void showUser() {
        User user =  userDao.selectUser();
        System.out.println(user);
    }
}
```

2. 将 `UserDao` 注入到 `UserService`

为 `UserDao` 类型属性添加注解

```java
@Service
public class UserServiceImpl implements UserService {
    // @AutoWired 根据属性类型进行自动注入
    @AutoWired
    private UserDao userDao;

    @Override
    public void showUser() {
        User user =  userDao.selectUser();
        System.out.println(user);
    }
}
```

3. 测试用例

```java
ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
UserService bean = context.getBean("userServiceImpl", UserService.class);
System.out.println(bean);
bean.showUser();
context.close();
```

**`@Qualifier`** 注解的使用

`@Qualifier` 注解要和 `@AutoWired` 一起使用

和属性的自动装配一样，如果使用按照类型装配的（`@AutoWired`），`UserDao` 类型属性会有多个实现类，Spring 不知道要使用哪个实现类，但是根据名称可以确定要注入的特定的实现类对象。

```java
@Repository("MyUserDao")
public class UserDaoImpl implements UserDao {
    @Override
    public User selectUser() {
        User user = new User();
        user.setAddress("China");
        user.setAge(18);
        user.setName("高中生");
        return user;
    }
}
```

使用 `@Qualifier("MyUserDao")` 来将上述 `UserDao` 对象注入到属性中。

```java
@Service
public class UserServiceImpl implements UserService {
    // 将 id 为 MyUserDao 的 Bean 对象注入到属性中
    @Autowired
    @Qualifier("MyUserDao")
    private UserDao userDao;

    @Override
    public void showUser() {
        User user =  userDao.selectUser();
        System.out.println(user);
    }
}
```

**`@Resource`** 注解的使用

`@Resource` 注解没有任何参数就是根据类型注入

```java
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public void showUser() {
        User user =  userDao.selectUser();
        System.out.println(user);
    }
}
```

`@Resource` 注解是 `javax.annotation.Resource` 类，是 Java原生的注解。Spring 更推荐使用 `@AutoWired` 和 `@Qualifier`。

`@Resource` 注解指定 `name` 参数可以指定注入的 Bean 对象的 id。

```java
@Service
public class UserServiceImpl implements UserService {
    @Resource(name = "MyUserDao")
    private UserDao userDao;

    @Override
    public void showUser() {
        User user =  userDao.selectUser();
        System.out.println(user);
    }
}
```

**`@Value`** 注解注入普通类型

```java
@Component
public class Book {
    @Value("《狂人日记》")
    private String name;
    @Value("鲁迅")
    private String author;
    @Value("19.9")
    private BigDecimal price;

    @Override
    public String toString() {
        return "Book [author=" + author + ", name=" + name + ", price=" + price + "]";
    }
}
```

测试用例

```java
ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
Book bean = context.getBean("book", Book.class);
System.out.println(bean);
context.close();
```

**纯注解开发**

1. 创建配置类，替代 XML 配置文件

```java
@Configuration // 作为配置类，替代 XML 配置文件
@ComponentScan(basePackages = {"xyz.zerxoi"})
// 等价于 <context:component-scan base-package="xyz.zerxoi"></context:component-scan>
public class SpringConfiguration {
    
}
```

2. 测试用例

```java
AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
UserService bean = context.getBean("userServiceImpl", UserService.class);
System.out.println(bean);
bean.showUser();
context.close();
```

## 6 基于注解注入数组

https://my.oschina.net/u/1000241/blog/3029017

https://www.cnblogs.com/duanxz/p/4516716.html

对于@Autowired声明的数组、集合类型，spring并不是根据beanName去找容器中对应的bean，而是把容器中所有类型与集合（数组）中元素类型相同的bean构造出一个对应集合，注入到目标bean中。

Spring 把 bean 放入了List中的顺序怎么控制呢？

在实现类中加入@Order(value) 注解即可 ，值越小越先被初始化越先被放入List