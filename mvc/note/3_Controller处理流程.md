`Controller` 接口代表一个组件，该组件像 `HttpServlet` 一样接收 `HttpServletRequest` 和 `HttpServletResponse` 实例，但能够参与MVC工作流程。`DispatcherServlet` 收到请求并完成解析语言环境，主题等的工作后，便尝试使用 `HandlerMapping` 解析控制器。当找到一个 `Controller` 处理请求时，将调用所定位 `Controller` 的 `handleRequest` 方法；然后，所定位的 `Controller` 负责处理实际请求，并在适用时返回适当的 `ModelAndView`。因此，实际上，此方法是 `DispatcherServlet` 的主要入口点，它将请求委托给控制器。

---

`RequestMappingHandlerMapping` 会在上下文中查找所有 Bean 对象

```java
/**
    * Scan beans in the ApplicationContext, detect and register handler methods.
    * @see #getCandidateBeanNames()
    * @see #processCandidateBean
    * @see #handlerMethodsInitialized
    */
protected void initHandlerMethods() {
    for (String beanName : getCandidateBeanNames()) {
        if (!beanName.startsWith(SCOPED_TARGET_NAME_PREFIX)) {
            processCandidateBean(beanName);
        }
    }
    handlerMethodsInitialized(getHandlerMethods());
}
```

在 `processCandidateBean` 方法会先判断该 Bean 对象是否是处理器对象，如果是的话，则使用 `detectHandlerMethods(beanName)` 对 Bean 对象进行进一步处理。

```java
/**
    * Determine the type of the specified candidate bean and call
    * {@link #detectHandlerMethods} if identified as a handler type.
    * <p>This implementation avoids bean creation through checking
    * {@link org.springframework.beans.factory.BeanFactory#getType}
    * and calling {@link #detectHandlerMethods} with the bean name.
    * @param beanName the name of the candidate bean
    * @since 5.1
    * @see #isHandler
    * @see #detectHandlerMethods
    */
protected void processCandidateBean(String beanName) {
    Class<?> beanType = null;
    try {
        beanType = obtainApplicationContext().getType(beanName);
    }
    catch (Throwable ex) {
        // An unresolvable bean type, probably from a lazy bean - let's ignore it.
        if (logger.isTraceEnabled()) {
            logger.trace("Could not resolve type for bean '" + beanName + "'", ex);
        }
    }
    if (beanType != null && isHandler(beanType)) {
        detectHandlerMethods(beanName);
    }
}
```

如果对象有@Controller和@RequestMapping注解，则该对象包含映射处理器所以该对象是映射处理器。

```java
/**
    * {@inheritDoc}
    * <p>Expects a handler to have either a type-level @{@link Controller}
    * annotation or a type-level @{@link RequestMapping} annotation.
    */
@Override
protected boolean isHandler(Class<?> beanType) {
    return (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class) ||
            AnnotatedElementUtils.hasAnnotation(beanType, RequestMapping.class));
}
```

`detectHandlerMethods` 首先获取 Bean 对象的类型，

```java
/**
    * Look for handler methods in the specified handler bean.
    * @param handler either a bean name or an actual handler instance
    * @see #getMappingForMethod
    */
protected void detectHandlerMethods(Object handler) {
    Class<?> handlerType = (handler instanceof String ?
            obtainApplicationContext().getType((String) handler) : handler.getClass());

    if (handlerType != null) {
        Class<?> userType = ClassUtils.getUserClass(handlerType);
        // 将类中带有 RequestMapping 的方法（处理器方法）都加入的该 Map 中
        // Map 的键为方法，值为请求映射信息（RequestMappingInfo）
        Map<Method, T> methods = MethodIntrospector.selectMethods(userType,
                (MethodIntrospector.MetadataLookup<T>) method -> {
                    try {
                        return getMappingForMethod(method, userType);
                    }
                    catch (Throwable ex) {
                        throw new IllegalStateException("Invalid mapping on handler class [" +
                                userType.getName() + "]: " + method, ex);
                    }
                });
        if (logger.isTraceEnabled()) {
            logger.trace(formatMappings(userType, methods));
        }
        // 为每个处理器方法进行注册到一个类型为Map的注册器中
        // 注册器的键是请求路径，值是处理器方法
        methods.forEach((method, mapping) -> {
            Method invocableMethod = AopUtils.selectInvocableMethod(method, userType);
            registerHandlerMethod(handler, invocableMethod, mapping);
        });
    }
}
```

---

在 `DispatcherServlet` 收到请求时会 `DispatcherServlet.doDispatch` 方法来对请求进行分派：通过按顺序应用 Servlet 的 `HandlerMappings` 可以获得处理程序。通过查询 Servlet 的已安装 `HandlerAdapter` 来查找支持该处理程序类的第一个 `HandlerAdapter`，从而获得 `HandlerAdapter`。

```java
/**
 * Process the actual dispatching to the handler.
 * <p>The handler will be obtained by applying the servlet's HandlerMappings in order.
 * The HandlerAdapter will be obtained by querying the servlet's installed HandlerAdapters
 * to find the first that supports the handler class.
 * <p>All HTTP methods are handled by this method. It's up to HandlerAdapters or handlers
 * themselves to decide which methods are acceptable.
 * @param request current HTTP request
 * @param response current HTTP response
 * @throws Exception in case of any kind of processing failure
 */
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpServletRequest processedRequest = request;
    HandlerExecutionChain mappedHandler = null;
    boolean multipartRequestParsed = false;

    WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);

    try {
        ModelAndView mv = null;
        Exception dispatchException = null;

        try {
            processedRequest = checkMultipart(request);
            multipartRequestParsed = (processedRequest != request);

            // Determine handler for the current request.
            mappedHandler = getHandler(processedRequest);
            if (mappedHandler == null) {
                noHandlerFound(processedRequest, response);
                return;
            }

            // Determine handler adapter for the current request.
            HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());

            // Process last-modified header, if supported by the handler.
            String method = request.getMethod();
            boolean isGet = "GET".equals(method);
            if (isGet || "HEAD".equals(method)) {
                long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
                if (new ServletWebRequest(request, response).checkNotModified(lastModified) && isGet) {
                    return;
                }
            }

            if (!mappedHandler.applyPreHandle(processedRequest, response)) {
                return;
            }

            // Actually invoke the handler.
            mv = ha.handle(processedRequest, response, mappedHandler.getHandler());

            if (asyncManager.isConcurrentHandlingStarted()) {
                return;
            }

            applyDefaultViewName(processedRequest, mv);
            mappedHandler.applyPostHandle(processedRequest, response, mv);
        }
        catch (Exception ex) {
            dispatchException = ex;
        }
        catch (Throwable err) {
            // As of 4.3, we're processing Errors thrown from handler methods as well,
            // making them available for @ExceptionHandler methods and other scenarios.
            dispatchException = new NestedServletException("Handler dispatch failed", err);
        }
        processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
    }
    catch (Exception ex) {
        triggerAfterCompletion(processedRequest, response, mappedHandler, ex);
    }
    catch (Throwable err) {
        triggerAfterCompletion(processedRequest, response, mappedHandler,
                new NestedServletException("Handler processing failed", err));
    }
    finally {
        if (asyncManager.isConcurrentHandlingStarted()) {
            // Instead of postHandle and afterCompletion
            if (mappedHandler != null) {
                mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
            }
        }
        else {
            // Clean up any resources used by a multipart request.
            if (multipartRequestParsed) {
                cleanupMultipart(processedRequest);
            }
        }
    }
}
```

通过 `mappedHandler = getHandler(processedRequest);` 语句获取请求的映射处理器。该方法桉顺序尝试每一个请求映射查找到第一个能够处理给定请求的处理程序。在 `mapping.getHandler(request)` 中请求映射会调用各自的 `getHandlerInternal` 方法来寻找能够处理请求的处理器，如果未找到指定处理器，则返回 `null`；如果 `getHandlerInternal` 返回空值，`mapping.getHandler(request)` 将返回处理器映射的默认处理器。

```java
/**
 * Return the HandlerExecutionChain for this request.
 * <p>Tries all handler mappings in order.
 * @param request current HTTP request
 * @return the HandlerExecutionChain, or {@code null} if no handler could be found
 */
@Nullable
protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
    if (this.handlerMappings != null) {
        for (HandlerMapping mapping : this.handlerMappings) {
            HandlerExecutionChain handler = mapping.getHandler(request);
            if (handler != null) {
                return handler;
            }
        }
    }
    return null;
}
```

获取到处理器 `mappedHandler` 之后，在通过 `HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());` 来获取支持该处理器调用的处理器适配器对象。

```java
/**
 * Return the HandlerAdapter for this handler object.
 * @param handler the handler object to find an adapter for
 * @throws ServletException if no HandlerAdapter can be found for the handler. This is a fatal error.
 */
protected HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {
    if (this.handlerAdapters != null) {
        for (HandlerAdapter adapter : this.handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
    }
    throw new ServletException("No adapter for handler [" + handler +
            "]: The DispatcherServlet configuration needs to include a HandlerAdapter that supports this handler");
}
```

`DispatcherServlet` 会遍历多个的处理器适配器并找到第一个能够支持该处理器调用的处理器适配器并返回。

在获取到请求适配器后，通过 `mv = ha.handle(processedRequest, response, mappedHandler.getHandler());` 语句来调用处理器。