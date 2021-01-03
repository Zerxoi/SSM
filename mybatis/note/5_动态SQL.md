# 5 动态 SQL

## 5.1  OGNL

[Language Guide](https://commons.apache.org/proper/commons-ognl/language-guide.html)


## if

多条件查询，若页面中没有设置此条件，SQL语句中一定不能有该条件。这个时候就需要使用 if 元素

```xml
<select id="selectAuthorsBy" resultType="xyz.zerxoi.pojo.Author">
    select * from t_author where 1 = 1
    <if test="id != null">
        and id = #{id}
    </if>
    <if test="username != null and username.trim() != ''">
        and username = #{username}
    </if>
    <if test="password != null and password.trim() != ''">
        and password = #{password}
    </if>
    <if test="email != null and email.trim() != ''">
        and email = #{email}
    </if>
    <if test="interests != null">
        and interests = #{interests,javaType=List}
    </if>
</select>
```

## where

where 元素只会在子元素返回任何内容的情况下才插入 “WHERE” 子句。而且，若子句的开头为 “AND” 或 “OR”，where 元素也会将它们去除。

```xml
<select id="selectAuthorsBy" resultType="xyz.zerxoi.pojo.Author">
    select * from t_author
    <where>
        <if test="id != null">
            id = #{id}
        </if>
        <if test="username != null and username.trim() != ''">
            and username = #{username}
        </if>
        <if test="password != null and password.trim() != ''">
            and password = #{password}
        </if>
        <if test="email != null and email.trim() != ''">
            and email = #{email}
        </if>
        <if test="interests != null">
            and interests = #{interests,javaType=List}
        </if>
    </where>
</select>
```

## trim

如果 where 元素与你期望的不太一样，你也可以通过自定义 trim 元素来定制 where 元素的功能。比如，和 where 元素等价的自定义 trim 元素为：

trim 元素有4个属性，分别是：

- prefixOverrides：移除操作的 SQL 前 `prefixOverrides` 属性指定的内容
- prefixOverrides：移除操作的 SQL 后 `suffixOverrides` 属性指定的内容
- suffix：在操作的 SQL 语句前插入的内容
- prefix：在操作的 SQL 语句后插入的内容

```xml
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  <!-- ... -->
</trim>
```

注： `"AND |OR "` 中的空格是必要的，如果列名以`AND` 或者 `OR` 开头则会出现错误。同理 `suffixOverrides` 属性应该是 `" AND| OR"`。

```xml
    <select id="selectAuthorsBy" resultType="xyz.zerxoi.pojo.Author">
        select * from t_author
        <trim prefix="where" prefixOverrides="and |or " suffixOverrides=" and| or">
            <if test="id != null">
                id = #{id}
            </if>
            <if test="username != null and username.trim() != ''">
                and username = #{username}
            </if>
            <if test="password != null and password.trim() != ''">
                and password = #{password}
            </if>
            <if test="email != null and email.trim() != ''">
                and email = #{email}
            </if>
            <if test="interests != null">
                and interests = #{interests,javaType=List}
            </if>
        </trim>
    </select>
```

## set

set 元素会动态地在行首插入 SET 关键字，并会删掉额外的逗号（这些逗号是在使用条件语句给列赋值时引入的）。

与 set 元素等价的自定义 trim 元素：

```xml
<trim prefix="SET" suffixOverrides=",">
  <!-- ... -->
</trim>
```

## choose（when，otherwise）

choose 元素类似于 Java 中的 switch 语句，是从多个条件中选择一个使用。

## foreach

foreach 元素允许你指定一个可迭代对象，声明可以在元素体内使用的集合项（item）和索引（index）变量。它也允许你指定开头与结尾的字符串以及集合项迭代之间的分隔符。

- collection：指定遍历的可迭代对象（集合，数组或Map对象）

当使用可迭代对象或者数组时，index 是当前迭代的序号，item 的值是本次迭代获取到的元素。当使用 Map 对象（或者 Map.Entry 对象的集合）时，index 是键，item 是值。

- open：循环体的开始内容
- close：循环体的结束内容
- separatpr：循环体的分隔符

你可以将任何可迭代对象（如 List、Set 等）、Map 对象或者数组对象作为集合参数传递给 foreach。当使用可迭代对象或者数组时，index 是当前迭代的序号，item 的值是本次迭代获取到的元素。当使用 Map 对象（或者 Map.Entry 对象的集合）时，index 是键，item 是值。

### 批量删除

```xml
<delete id="deleteAuthors">
    delete from t_author where id in
    <foreach collection="ids" item="id" open="(" separator="," close=")">
        #{id}
    </foreach>
</delete>
```

```java
public int deleteAuthors(@Param("ids") List<Integer> ids);
public int deleteAuthors(@Param("ids") Integer[] ids);
```

### 批量查找

```xml
<select id="selectAuthorsByIds" resultMap="author">
    select * from t_author where id in
    <foreach collection="ids" item="id" open="(" close=")" separator=",">
        #{id}
    </foreach>
</select>
```
```java
public List<Author> selectAuthorsByIds(@Param("ids") List<Integer> ids);
public List<Author> selectAuthorsByIds(@Param("ids") Integer[] ids);
```
### 批量插入

```xml
<insert id="insertAuthors">
    insert
        into t_author (`id`, `username`, `password`, `email`, `interests`)
    values
    <foreach collection="authors" item="author" separator=",">
        (#{author.id}, #{author.username}, #{author.password}, #{author.email}, #{author.interests,javaType=List})
    </foreach>
</insert>
```

```java
public int insertAuthors(@Param("authors") Author[] authors);
public int insertAuthors(@Param("authors") List<Author> authors);
```

### 批量更新

```xml
<update id="updateAuthors">
    update t_author
    <trim prefix="set" suffixOverrides=",">
        <trim prefix="username = case" suffix="end,">
            <foreach collection="authors" item="author">
                when id = #{author.id} then #{author.username}
            </foreach>
        </trim>
        <trim prefix="password = case" suffix="end,">
            <foreach collection="authors" item="author">
                when id = #{author.id} then #{author.password}
            </foreach>
        </trim>
        <trim prefix="email = case" suffix="end,">
            <foreach collection="authors" item="author">
                when id = #{author.id} then #{author.email}
            </foreach>
        </trim>
        <trim prefix="interests = case" suffix="end,">
            <foreach collection="authors" item="author">
                when id = #{author.id} then #{author.interests,javaType=List}
            </foreach>
        </trim>
    </trim>
    where id in
    <foreach collection="authors" item="author" open="(" close=")" separator=",">
        #{author.id}
    </foreach>
</update>
```

```java
public int updateAuthors(@Param("authors") List<Author> authors);
public int updateAuthors(@Param("authors") Author[] authors);
```

## SQL 

这个元素可以用来定义可重用的 SQL 代码片段，以便在其它语句中使用。 参数可以静态地（在加载的时候）确定下来，并且可以在不同的 include 元素中定义不同的参数值。比如：

```xml
<sql id="sometable">
  ${prefix}Table
</sql>

<sql id="someinclude">
  from
    <include refid="${include_target}"/>
</sql>

<select id="select" resultType="map">
  select
    field1, field2, field3
  <include refid="someinclude">
    <property name="prefix" value="Some"/>
    <property name="include_target" value="sometable"/>
  </include>
</select>
```
