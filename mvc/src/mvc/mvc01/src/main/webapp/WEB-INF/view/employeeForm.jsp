<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <form action="employee" method="POST">
        <c:if test="${ empty requestScope.employee.id }" var="isAdd"></c:if>
        <c:if test="${ !isAdd }">
            <input type="hidden" name="_method" value="PUT">
            <input type="hidden" name="id" value="${ requestScope.employee.id }">
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
                    <td><input type="text" name="name" id="name" value="${ requestScope.employee.name }"></td>
                </tr>
                <tr>
                    <td><label for="email">Email </label></td>
                    <td><input type="text" name="email" id="email" value="${ requestScope.employee.email }"></td>
                </tr>
                <tr>
                    <td><label for="age">Age </label></td>
                    <td><input type="text" name="age" id="age" value="${ requestScope.employee.age }"></td>
                </tr>
                <tr>
                    <td><label for="department">Department </label></td>
                    <td><select name="departmentId" id="department">
                            <option value=""></option>
                            <c:forEach items="${ requestScope.departments }" var="department">
                                <option value="${ department.id }" ${ requestScope.employee.departmentId == department.id ? "selected" : "" } >${ department.name }</option>
                            </c:forEach>
                        </select>
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
    </form>
</body>

</html>