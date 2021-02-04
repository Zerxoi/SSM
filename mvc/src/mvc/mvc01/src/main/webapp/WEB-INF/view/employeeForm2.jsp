<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<html>

<head>
    <title>Employee</title>
    <base href="${ pageScope.basePath }">
</head>

<body>
    <form:form action="employee" method="POST">
        <c:if test="${ empty requestScope.command.id }" var="isAdd"></c:if>
        <c:if test="${ !isAdd }">
                <input type="hidden" name="_method" value="PUT" />
                <form:hidden path="id" />
        </c:if>
        <table>
            <thead>
                <tr>
                    <th colspan="2">${ isAdd ? "ADD" : "UPDATE" } EMPLOYEE</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><label for="name">Name </label></td>
                    <td><form:input path="name" id="name" /></td>
                </tr>
                <tr>
                    <td><label for="email">Email </label></td>
                    <td><form:input type="email" path="email" id="email" /></td>
                </tr>
                <tr>
                    <td><label for="age">Age </label></td>
                    <td><form:input path="age" id="age" /></td>
                </tr>
                <tr>
                    <td><label for="department">Department </label></td>
                    <td><form:select path="departmentId" id="department">
                            <form:option value="" label=""/>
                            <form:options items="${ requestScope.departments }" itemValue="id" itemLabel="name"/>
                        </form:select>
                    </td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="2">
                        <input type="submit" value='${ isAdd ? "ADD" : "UPDATE" }'>
                    </td>
                </tr>
            </tfoot>
        </table>
    </form:form>
</body>

</html>