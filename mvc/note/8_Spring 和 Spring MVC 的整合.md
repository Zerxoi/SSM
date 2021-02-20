# Spring 和 Spring MVC 的整合

## Servlet 与 ApplicationContext 的区别

参考：[Servlet 与 ApplicationContext 的区别](https://essviv.github.io/2016/07/02/spring/spring-differerce-between-application-context-and-servlet-context/)

### 基本概念

- **`ServletContext`**: 这个是来自于 Servlet 规范里的概念，它是 Servlet 用来与容器间进行交互的接口的组合，也就是说，这个接口定义了一系列的方法，Servlet 通过这些方法可以很方便地与自己所在的容器进行一些交互，比如通过 `getMajorVersion` 与 `getMinorVersion` 来获取容器的版本信息等。从它的定义中也可以看出，在一个应用中(一个JVM)只有一个 `ServletContext`, 换句话说，容器中所有的 Servlet 都共享同一个 `ServletContext`。
- **`ServletConfig`**: 它与 `ServletContext` 的区别在于，`ServletConfig` 是针对 `Servlet` 而言的，每个 `Servlet` 都有它独有的 `ServeltConfig` 信息，相互之间不共享。
- **`ApplicationContext`**: 这个类是 Spring 实现容器功能的核心接口，它也是 Spring 实现 IoC 功能中最重要的接口，从它的名字中可以看出，它维护了整个程序运行期间所需要的上下文信息， 注意这里的应用程序并不一定是 Web 程序，也可能是其它类型的应用。在Spring中允许存在多个`ApplicationContext`，这些 `ApplicationContext` 相互之间还形成了父与子，继承与被继承的关系，这也是通常我们所说的，在spring中存在两个 `ApplicationContext`,一个是`Root ApplicationContext`，一个是 `Servlet ApplicationContext`的意思。
- **`WebApplicationContext`**: 其实这个接口不过是 `WebApplicationContext` 接口的一个子接口罢了，只不过说它的应用形式是 Web 罢了。它在 `ApplicationContext` 的基础上，添加了对 `ServletContext` 的引用，即 `getServletContext` 方法；它还被设置为 `ServletContext` 的属性，通过 `RequestContextUtils` 上的静态方法来查找 `WebApplicationContext`。

### ServletContext

`ServletContext` 是容器中所有 `Servlet` 共享的配置，它在应用中是全局的。

可以通过 `web.xml` 中的 `context-param` 元素指定 ServletContext 中参数的参数名和参数值。比如，`ContextLoaderListener` 就会根据 ServletContext 中的 `contextConfigLocation` 值来确定 Root WebApplicationContext 的上下文配置文件的位置。

```xml
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:conf/applicationContext.xml</param-value>
</context-param>
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```

### ServletConfig

`ServletConfig` 是针对每个 Servlet 进行配置的，因此它的配置是在 Servlet 的配置中。

可以通过 `web.xml` 中的 `servlet` 元素中的 `init-param` 元素为 `ServletConfig` 对象指定参数的参数名和参数值。比如，`DispatcherServlet` 的 Servlet ApplicationContext 就会根据对应 Servlet 的 `ServletConfig` 对象中的 `contextConfigLocation` 参数值确定 Servlet ApplicationContext 配置文件的位置。

```xml
<servlet>
    <servlet-name>mvc-dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/mvc-dispatcher-servlet.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
    <async-supported>true</async-supported>
</servlet>
```

### ContextLoaderListener

ContextLoaderListener 会从 ServletContext 的 `contextConfigLocation` 参数值处获取配置文件用于创建 Root ApplicationContext，并将创建好的上下文放入 ServletContext 的属性中；如果未显示指定 `contextConfigLocation` 参数值则默认从 `/WEB-INF/applicationContext.xml` 位置获取配置文件。

```java
@Override
public void contextInitialized(ServletContextEvent event) {
    initWebApplicationContext(event.getServletContext());
}
```

```java
public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
    // 判断是否存在 Root ApplicationContext，如果存在直接抛出异常结束
    if (servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE) != null) {
        throw new IllegalStateException(
                "Cannot initialize context because there is already a root application context present - " +
                "check whether you have multiple ContextLoader* definitions in your web.xml!");
    }

    // ...

    try {
        // Store context in local instance variable, to guarantee that
        // it is available on ServletContext shutdown.
        if (this.context == null) {
            this.context = createWebApplicationContext(servletContext);
        }
        if (this.context instanceof ConfigurableWebApplicationContext) {
            ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) this.context;
            if (!cwac.isActive()) {
                // The context has not yet been refreshed -> provide services such as
                // setting the parent context, setting the application context id, etc
                if (cwac.getParent() == null) {
                    // The context instance was injected without an explicit parent ->
                    // determine parent for root web application context, if any.
                    ApplicationContext parent = loadParentContext(servletContext);
                    cwac.setParent(parent);
                }
                configureAndRefreshWebApplicationContext(cwac, servletContext);
            }
        }
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);

        ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        if (ccl == ContextLoader.class.getClassLoader()) {
            currentContext = this.context;
        }
        else if (ccl != null) {
            currentContextPerThread.put(ccl, this.context);
        }

        if (logger.isInfoEnabled()) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("Root WebApplicationContext initialized in " + elapsedTime + " ms");
        }

        return this.context;
    }
    catch (RuntimeException | Error ex) {
        logger.error("Context initialization failed", ex);
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, ex);
        throw ex;
    }
}
```

### DispatcherServlet

DispatcherServlet 作为 Servlet，应在 `init` 方法中完成初始化。

首先从 `ServletConfig` 中获取所有的配置参数， `ServletConfigPropertyValues` 的构造函数中会遍历 `ServletConfig` 对象的所有初始化参数，并把它们一一存储在 `pvs` 中。

```java
@Override
public final void init() throws ServletException {

    // Set bean properties from init parameters.
    PropertyValues pvs = new ServletConfigPropertyValues(getServletConfig(), this.requiredProperties);
    if (!pvs.isEmpty()) {
        try {
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this);
            ResourceLoader resourceLoader = new ServletContextResourceLoader(getServletContext());
            bw.registerCustomEditor(Resource.class, new ResourceEditor(resourceLoader, getEnvironment()));
            initBeanWrapper(bw);
            bw.setPropertyValues(pvs, true);
        }
        catch (BeansException ex) {
            if (logger.isErrorEnabled()) {
                logger.error("Failed to set bean properties on servlet '" + getServletName() + "'", ex);
            }
            throw ex;
        }
    }

    // Let subclasses do whatever initialization they like.
    initServletBean();
}
```

`DispatcherServlet` 是继承自 `FrameworkServlet` 并调用 `FrameworkServlet.initServletBean` 初始化上下文。

```java
@Override
protected final void initServletBean() throws ServletException {
    // ...

    try {
        this.webApplicationContext = initWebApplicationContext();
        initFrameworkServlet();
    }
    catch (ServletException | RuntimeException ex) {
        logger.error("Context initialization failed", ex);
        throw ex;
    }

    // ...
}
```

`initServletBean` 中会调用了 `initWebApplicationContext` 方法来初始化 Servlet ApplicationContext`。

通过 `web.xml` 中的 `servlet` 元素中的 `init-param` 元素为 `ServletConfig` 对象指定参数的参数名和参数值。`DispatcherServlet` 的 Servlet ApplicationContext 就会根据对应 Servlet 的 `ServletConfig` 对象中的 `contextConfigLocation` 参数值确定 Servlet ApplicationContext 配置文件的位置；如果没有显式配置 `contextConfigLocation` 默认配置文件位置为 `/WEB-INF/[servlet-name]-servlet.xml`。

```java
protected WebApplicationContext initWebApplicationContext() {
    WebApplicationContext rootContext =
            WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    WebApplicationContext wac = null;

    if (this.webApplicationContext != null) {
        // A context instance was injected at construction time -> use it
        wac = this.webApplicationContext;
        if (wac instanceof ConfigurableWebApplicationContext) {
            ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) wac;
            if (!cwac.isActive()) {
                // The context has not yet been refreshed -> provide services such as
                // setting the parent context, setting the application context id, etc
                if (cwac.getParent() == null) {
                    // The context instance was injected without an explicit parent -> set
                    // the root application context (if any; may be null) as the parent
                    cwac.setParent(rootContext);
                }
                configureAndRefreshWebApplicationContext(cwac);
            }
        }
    }
    if (wac == null) {
        // No context instance was injected at construction time -> see if one
        // has been registered in the servlet context. If one exists, it is assumed
        // that the parent context (if any) has already been set and that the
        // user has performed any initialization such as setting the context id
        wac = findWebApplicationContext();
    }
    if (wac == null) {
        // No context instance is defined for this servlet -> create a local one
        wac = createWebApplicationContext(rootContext);
    }

    if (!this.refreshEventReceived) {
        // Either the context is not a ConfigurableApplicationContext with refresh
        // support or the context injected at construction time had already been
        // refreshed -> trigger initial onRefresh manually here.
        synchronized (this.onRefreshMonitor) {
            onRefresh(wac);
        }
    }

    if (this.publishContext) {
        // Publish the context as a servlet context attribute.
        String attrName = getServletContextAttributeName();
        getServletContext().setAttribute(attrName, wac);
    }

    return wac;
}
```

`initWebApplicationContext` 首先获取到 `rootContext`， 接着就开始初始化 `wac` 这个对象，在创建这个 `wac` 对象的方法中，传入了 `rootContext` 作为它的父上下文，也就是在这里，两者之间的父子关系建立，也就形成了我们平时常说的继承关系。最后在 `ServletContext`  中设置 Servlet ApplicationContext 属性。

## 问题：Spring MVC 是否需要再加入 Spring的 IOC 容器？

- 需要：通常情况下，类似于数据源，事务，整合其他框架都是放在 Spring 的配置文件中（而不是 Spring MVC 的配置文件中）。
    - 实际上放入 Spring 配置文件对应的 IoC 容器中的还有 `Service` 和 `DAO`。
- 不需要：都放在 Spring MVC 的配置文件中。
    - 可以使用多个 Spring 的配置文件，使用 `<import>` 元素导入其他配置元素。
    - 使用通配符配置 `contextConfigLocation` 文件路径以支持文件配置

## 整合 Spring

在 `web.xml` 中加入如下内容

```xml
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/root-context.xml</param-value>
</context-param>

<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```

---

**使用分包来分离 Bean 对象**

在 `/WEB-INF/root-context.xml` 中配置 Root ApplicationContext 只扫描 `xyz.zerxoi.service` 和 `xyz.zerxoi.dao` 包中的容器 Bean 注解类。

```xml
<context:component-scan base-package="xyz.zerxoi.service, xyz.zerxoi.dao"></context:component-scan>
```

为了组件扫描重叠导致的 Bean 对象重复创建，修改 Servlet ApplicationContext 只扫描 `xyz.zerxoi.controller` 包中的容器 Bean 注解类。

```xml
<context:component-scan base-package="xyz.zerxoi.controller"></context:component-scan>
```

---

**使用 <include-filter> 和 <exclude-filter> 元素限制扫描注解**

但是随着项目的不断升级，项目的包有可能是根据功能模块来划分的，比如用户模块，订单模块等，每个模块中都有可能包含 `@Service`，`@Controller` 和 `@Repository` 注解。这样通过分包来实现 Root ApplicationContext 和 Servlet ApplicationContext 中 Bean 对象的分离显然式不可取的。


`use-default-filters="false"` 不使用默认的 filter，禁用默认的 `@Component`，`@Repository`，`@Service`，`@Controller`，`@RestController` 和 `@Configuration` 注解的扫描，而是用自己配置的 filter。

配置 Servlet ApplicationContext 只扫描 `@Controller` 和 `@ControllerAdvice` 的注解类。

```xml
<context:component-scan base-package="xyz.zerxoi" use-default-filters="false">
    <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller" />
    <context:include-filter type="annotation" expression="org.springframework.web.bind.annotation.ControllerAdvice" />
</context:component-scan>
```

配置 Root ApplicationContext 默认过滤器中除了 `@Controller` 和 `@ControllerAdvice` 注解。

```xml
<context:component-scan base-package="xyz.zerxoi">
    <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller" />
    <context:exclude-filter type="annotation" expression="org.springframework.web.bind.annotation.ControllerAdvice" />
</context:component-scan>
```

## Root ApplicationContext 和 Servlet ApplicationContext 的关系

参考：[DispatcherServlet 上下文层级](https://github.com/Zerxoi/SSM/blob/main/mvc/note/0_%E6%96%87%E6%A1%A3.md#%E4%B8%8A%E4%B8%8B%E6%96%87%E5%B1%82%E7%BA%A7)

Root ApplicationContext 和 Servlet ApplicationContext 是父子关系。

Child(Servlet) WebApplicationContext 可以引用 Root WebApplicationContext 中的 Bean 对象，但是反之则不可以。