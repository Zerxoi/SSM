# 处理 JSON 和 文件上传下载

JSON 内容参考：[JSON](https://github.com/Zerxoi/JavaWeb/blob/master/note/21_JSON_AJAX_i18n/json.md)

JSON 处理的目的主要是对 Java 中的 Bean 数据序列化，作为响应数据发送到客户端；客户端对响应数据进行反序列化处理数据。

Spring 提供了对 Jackson JSON 库的支持。

## Spring MVC 处理 JSON

步骤：

1. 配置 Spring MVC DispatcherServlet 注解驱动 `<mvc:annotation-driven />`
2. 导入 [jackson](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind) 依赖
3. 使用 `@ResponseBody` 注解，将处理器方法的返回值作为响应体

### 底层原理

参考：[mvc:annotation-driven到底帮我们做了什么](http://flycloud.me/2018/09/06/spring&springMvc/springMVC/2014-08-28-what-does-mvc-annotation-driven-do/)

`HttpMessageConverter` 可用来将特定的对象转换成字符串并最终作为 HTTP Response 返回的工具。实际上 Spring MVC 中面向开发人员的业务逻辑处理主要集中在各种 `Controller` 的方法中，基本模式是接受代表着 `HttpRequest` 的各种输入参数，在方法体中进行业务逻辑处理，最后得到输出结果，并以返回值的形式交给 Spring MVC，Spring MVC 根据返回值的不同调用不同的处理逻辑并最终以 HTTP Response 的形式返回给客户端。`Controller` 中的返回值可以有很多种，比如**字符串**，**`ModelAndView`**，**普通对象**等等，甚至 `void` 类型都是可以的。那么很容易想到 Spring MVC 会根据返回值的类型做很多的 `if else`，不同的类型调用不同的处理逻辑。那么当函数受 `@ResponseBody` 声明时，Spring 就会尝试用配置好的各种 `HttpMessageConverter` 来将返回值进行序列化。不同 `HttpMessageConverter` 能够处理的对象以及处理方式都是不一样的，Spring 会遍历各 `HttpMessageConverter`，如果该 `HttpMessageConverter` 能够处理该对象则交由其处理。因此，很多基于 Spring 的 REST 风格的应用常常会返回一个 `Model` 对象，那么你就应该配置好正确的 `HttpMessageConverter`，以便 Spring 能够正确的将这些对象序列化回客户端。

`<mvc:annotation-driven/>` 会创建（发现）一组 `HttpMessageConverter`，并把他们配置到 `RequestMappingHandlerAdapter` 中，供 Spring MVC 使用。

1. 首先它会看 `<mvc:annotation-driven>` 中有没有显示指定 `<message-converters>`，如果指定了那么就用指定的配置。
2. 如果没有显示指定，或者虽然显示指定了但是还是指定了 `register-defaults` 属性的话就会默认添加一些常用的 `HttpMessageConverter`，比如 `ByteArrayHttpMessageConverter`，`StringHttpMessageConverter`，`ResourceHttpMessageConverter`，`SourceHttpMessageConverter`，`AllEncompassingFormHttpMessageConverter`。除此之外还会有一些自动发现的逻辑，比如自动发现 jackson 和 jaxb 的相关组件是否在 Classpath 中，如果存在就会加入对应的 `HttpMessageConverter`。因此如果你想用 jackson 来序列化 JSON 或者使用 jaxb 序列化 XML，你只需要将其实现类放入到 Classpath 中，并且再声明 `<mvc:annotation-driven/>` 就自动配置好了。

### 代码示例

```java
@Controller
public class JsonController {
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/json/employee")
    @ResponseBody
    public Collection<DetailedEmployee> jsonEmployee() {
        return employeeService.getEmployees();
    }
}
```

jQuery AJAX 的 `dataType` 属性表示期望从服务器返回的数据类型。如果未指定任何内容，则 jQuery 将尝试根据响应的 MIME 类型进行推断。`dataType` 会对请求的响应将由 jQuery 预处理，处理后的结果会传递到 `success` 回调函数。可处理的数据类型有 `xml`、 `json`、`script`、`html` 等。本例中将 Java Bean 转换成 JSON 字符串之后，通过 `dataType: "json"` 实现对该字符串的预处理，将其转化为 JSON 对象。

```jsp
<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<html>

<head>
    <title>Index</title>
    <script src="js/jquery-3.5.1.min.js"></script>
    <script>
        $(function () {
            $("#btn").click(function () {
                $.ajax({
                    url: "json/employee",
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
                        console.log(data)
                    }
                })
            })
        })
    </script>
</head>

<body>
    <input id="btn" type="button" value="JSON">
</body>

</html>
```

## `HttpMessageConverter`

`HttpMessageConverter` 是用于转换HTTP请求和响应的策略接口。

```java
public interface HttpMessageConverter<T> {

	/**
	 * 指示此转换器是否可以读取给定的类。
	 * @param clazz 测试可读性的类
	 * @param mediaType 要读取的媒体类型（如果未指定，则可以为 null）；通常是 Content-Type 标头的值。
	 * @return 如果可读，则为true；否则为假
	 */
	boolean canRead(Class<?> clazz, @Nullable MediaType mediaType);

	/**
	 * 指示给定的类是否可以由此转换器写入。
	 * @param clazz 测试类的可写性
	 * @param mediaType 要写入的媒体类型（如果未指定，则可以为 null）；通常是 Accept 标头的值。
	 * @return 如果可写，则为true；否则为假
	 */
	boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);

	/**
	 * 返回此转换器支持的 MediaType 对象的列表。
	 * @return 支持的媒体类型列表，可能是一个不可更改的副本。
	 */
	List<MediaType> getSupportedMediaTypes();

	/**
	 * 从给定的输入消息中读取给定类型的对象，并将其返回。
	 * @param clazz 要返回的对象的类型。此类型必须事先已传递给此接口的 canRead 方法，该方法必须返回 true。
	 * @param inputMessage 要读取的HTTP输入消息
	 * @return 转换后的对象
	 * @throws IOException 如果发生 I/O 错误
	 * @throws HttpMessageNotReadableException 如果发生转换错误
	 */
	T read(Class<? extends T> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException;

	/**
	 * 将给定对象写入给定输出消息。
	 * @param t 写入输出消息的对象。该对象的类型必须事先已传递给此接口的 canWrite 方法，该方法必须返回 true。
	 * @param contentType 写入时使用的内容类型。可以为 null，指示必须使用转换器的默认内容类型。如果不为 null，则必须事先将此媒体类型传递给此接口的 canWrite 方法，该方法必须返回true。
	 * @param outputMessage 要写入的HTTP输出消息
	 * @throws IOException 如果发生 I/O 错误
	 * @throws HttpMessageNotWritableException 如果发生转换错误
	 */
	void write(T t, @Nullable MediaType contentType, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException;

}
```

`<mvc:annotation-driven/>` 为 `DispatcherServlet` 装配的 `HandlerAdapter` 有 `HttpRequestHandlerAdapter`、`SimpleControllerHandlerAdapter` 还有 `RequestMappingHandlerAdapter`。

`<mvc:annotation-driven/>` 会创建（发现）一组 `HttpMessageConverter`，并把他们配置到 `RequestMappingHandlerAdapter` 中，供 Spring MVC 使用。

![HttpMessageConverter 作用](HttpMessageConverter%20作用.png)

- [`@RequestBody`](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-requestbody) 将使用合适的 `HttpMessageConverter` 实现类的 `read` 方法从 HTTP 消息输入中读取**请求体**数据。
- [`HttpEntity`](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-httpentity) 和 `@RequestBody` 功能类似，但是读取的是**请求体**和**标头**。
- [`@ResponseBody`](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-httpentity) 将使用合适的 `HttpMessageConverter` 实现类的 `write` 方法将方法返回值写入至 HTTP 消息输出作为**响应体**。
- [`ResponseEntity`](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-responsebody) 类似于 `@ResponseBody`，但 `ResponseEntity` 除了**响应体**还具有**状态**和**标头**。适合用于文件下载等场景下。

### 使用 ResponseEntity 实现文件下载

文件下载要需要设置响应的 `Content-Disposition` 标头，所以需要使用 `ResponseEntity`。

```java
@RequestMapping("/download")
public ResponseEntity<byte[]> download() throws IOException {
    Resource resource = new ClassPathResource("conf/mvc-servlet.xml");
    InputStream is = resource.getInputStream();
    byte[] b = is.readAllBytes();
    is.close();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment;filename=mvc-servlet.xml");
    return new ResponseEntity<>(b, headers, HttpStatus.OK);
}
```

##  文件上传

参考:[Multipart Resolver](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-multipart)

### 单文件上传

`org.springframework.web.multipart` 包中的 `MultipartResolver` 是一种用于解析包括文件上传在内的 Multipart 请求的策略。

要启用 Multipart 处理，您需要在 `DispatcherServlet` Spring 配置中声明一个名为 `multipartResolver` 的 `MultipartResolver` Bean 对象。`DispatcherServlet` 检测到它并将其应用于传入的请求。当收到内容类型为 `multipart/form-data` 的 POST 请求时，解析程序将解析内容并将当前的 `HttpServletRequest` 包装为 `MultipartHttpServletRequest`，以提供对已解析部分的访问权，此外还可以将其公开为请求参数。

---

要使用 Apache Commons `FileUpload`，可以**配置 `id`为 `multipartResolver`**的 `CommonsMultipartResolver` 类型的 Bean。在 `DispatcherServlet` 的 `WebApplicationContext` 中创建 `id` 为 `multipartResolver` 的 `MultipartResolver` Bean 对象。

```xml
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"></bean>
```

还需需要在类路径中**添加 `commons-fileupload` 依赖**

```xml
<dependency>
  <groupId>commons-fileupload</groupId>
  <artifactId>commons-fileupload</artifactId>
  <version>${commons.version}</version>
</dependency>
```


---

页面表单的发送内容类型为 `multipart/form-data` 的 POST 请求

```jsp
<form action="upload" method="post" enctype="multipart/form-data">
    <label for="file">File</label> <input type="file" name="file" id="file"><br>
    <label for="desc">Description</label> <input type="text" name="desc" id="desc"><br>
    <input type="submit" value="Upload">
</form>
```

---

处理器方法通过方法参数获取请求文件 `file` 和请求数据 `desc`，并把上传的数据传输到服务器中。

```java
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("desc") String desc,
            HttpServletRequest request) throws Exception {
        System.out.println(desc);
        ServletContext sc = request.getServletContext();
        // 获取给定路径在文件系统中的真实路径
        String originalFilename = file.getOriginalFilename();
        String path = sc.getRealPath("/upload");
        String ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String name = UUID.randomUUID() + ext;
        File uploadFile = new File(path, name);
        // 如果不存在父目录创建父目录
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }
        file.transferTo(uploadFile);
        return "SUCCESS";
    }
```

### 多文件上传

```jsp
<form action="multiUpload" method="post" enctype="multipart/form-data">
    <label for="file1">File1</label> <input type="file" name="file" id="file1"><br>
    <label for="file1">File2</label> <input type="file" name="file" id="file1"><br>
    <label for="desc">Description</label> <input type="text" name="desc" id="desc"><br>
    <input type="submit" value="MultiUpload">
</form>
```

```java
@RequestMapping(value = "/multiUpload", method = RequestMethod.POST)
public String multiUpload(@RequestParam("file") MultipartFile[] files, @RequestParam("desc") String desc,
        HttpServletRequest request) throws Exception {
    System.out.println(desc);
    ServletContext sc = request.getServletContext();
    for (MultipartFile file : files) {
        String originalFilename = file.getOriginalFilename();
        String path = sc.getRealPath("/upload");
        String name = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf('.'));
        File upload = new File(path, name);
        // 如果不存在父目录创建父目录
        if (!upload.getParentFile().exists()) {
            upload.getParentFile().mkdirs();
        }
        file.transferTo(upload);
    }
    return "SUCCESS";
}
```
