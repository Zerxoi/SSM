# Spring MVC 运行流程

Servlet 容器（以 Tomcat 服务器为例）默认有两个 Servlet 程序，分别为 `default` 和 `jsp`。其中 `default` Servlet 适用于处理一些例如 `.html`，`.jpg` 等的静态资源； `jsp` Servlet 用于处理 `.jsp` 和 `.jspx` 的静态资源。

配置详情可以在 Tomcat 的 `conf/web.xml` 文件中看到：

```xml
<servlet>
    <servlet-name>default</servlet-name>
    <servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
    <init-param>
        <param-name>debug</param-name>
        <param-value>0</param-value>
    </init-param>
    <init-param>
        <param-name>listings</param-name>
        <param-value>false</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet>
    <servlet-name>jsp</servlet-name>
    <servlet-class>org.apache.jasper.servlet.JspServlet</servlet-class>
    <init-param>
        <param-name>fork</param-name>
        <param-value>false</param-value>
    </init-param>
    <init-param>
        <param-name>xpoweredBy</param-name>
        <param-value>false</param-value>
    </init-param>
    <load-on-startup>3</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>default</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
<servlet-mapping>
    <servlet-name>jsp</servlet-name>
    <url-pattern>*.jsp</url-pattern>
    <url-pattern>*.jspx</url-pattern>
</servlet-mapping>
```

我们通常会将 `DispatcherServlet` 的映射路径配置为默认路径 `/` 从而覆盖 Servlet 容器中的默认 Servlet 对某些静态资源的处理。

如果在 Spring MVC 配置中添加了 `<mvc:default-servlet-handler />` 元素就会创建一个**最低优先级**，**使用 `/**` 路径映射到默认 Servlet** 的 `SimpleUrlHandlerMapping` 对象，该对象会被添加到 `DispatcherServlet` 的 `handlerMappings` 属性中。当请求发送到 `DispatcherServlet` 时会按优先级遍历 `handlerMappings`，获取能够处理请求的处理器。

需要注意的是如果添加了 `<mvc:default-servlet-handler />` 最好也要添加 `<mvc:annotation-driven></mvc:annotation-driven>` 配置。按照默认的初始化策略，只有在容器中没有其他 `HandlerMapping` 时才会从 springmvc-webmvc JAR 包中的 `DispatcherServlet.properties` 中创建默认的 `HandlerMapping` Beand对象；但是 `<mvc:default-servlet-handler />` 会创建 `SimpleUrlHandlerMapping` 对象，这就导致默认策略中的 `RequestMappingHandlerMapping` 无法创建，从而使得请求无法映射至处理器方法。

`HandlerMapping` 获取的处理器对象的类型是 `HandlerExecutionChain`，该对象由**处理器对象**和**多个拦截器**组成。

再根据处理器对象获取调用处理器程序的处理器是配置器 `HandlerAdapter` 对象。

**在 `HandlerAdapter` 调用处理程序之前**会先调用拦截器的 `preHandle` 方法用于拦截处理程序的执行。如果执行链 `HandlerExecutionChain` 应继续下一个拦截器或处理程序本身，则为 `true`；否则，`DispatcherServlet` 假定此拦截器已经处理了响应本身。`preHandle` 会按照处理程序链中的拦截器的**正向**顺序执行。

`HandlerAdapter` 调用处理器程序本事获取 `ModelAndView` 对象。

**在 `HandlerAdapter` 实际调用处理程序之后**但**在 `DispatcherServlet` 呈现视图之前**调用 `postHandle`；如果处理程序出现异常 ———— `HandlerAdapter` 没有正常运行也没有向 `DispatcherServlet` 呈现视图，因此不会调用 `postHandle` 方法。此外该方法还可以通过给定的 `ModelAndView` 向视图公开额外的模型对象。`postHandle` 会按照处理程序链中的拦截器的**反向**顺序执行。

上述过程中如果出现异常，就会将异常交给异常解析器来处理，获取一个 `ModelAndView` 对象；如果没有异常则使用调用处理器程序返回的 `ModelAndView` 对象。

将 `ModelAndView` 交给视图解析器  `ViewResolver` 来渲染视图。

在完成请求处理后(视图渲染完之后)会通过 `afterCompletion` 回调来进行一些适当的资源清理。**注意：仅当此拦截器的 `preHandle` 方法成功完成并返回 `true` 时，才会调用它**！与 `postHandle` 方法一样，该方法会按照处理程序链中的拦截器的**反向**顺序执行。