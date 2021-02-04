<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Employees</title>
    <script src="js/jquery-3.5.1.min.js"></script>
    <script>
        $(function () {
            $(".delete").click(function () {
                if (confirm("Are you sure you want to delete?")) {
                    $("form").attr("action", $(this).attr("href")).submit();
                }
                return false; // 返回 false 会组织事件冒泡和默认行为，<a> 的默认行为是发出 get 请求
            })
        })
    </script>
</head>

<body>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>NAME</th>
                <th>EMAIL</th>
                <th>AGE</th>
                <th>DEPARTMENT</th>
                <th>OPERATION(<a href="employee">ADD</a>)</th>
            </tr>
        </thead>
        <tbody>
        	<c:forEach items="${ requestScope.employees }" var="employee">
				<tr>
					<td>${ employee.id }</td>
					<td>${ employee.name }</td>
					<td>${ employee.email }</td>
					<td>${ employee.age }</td>
					<td>${ employee.department.name }</td>
					<td> <a href="employee/${ employee.id }">UPDATE</a> <a class="delete" href="employee/${ employee.id }">DELETE</a></td>
                </tr>
			</c:forEach>
        </tbody>
    </table>
    <form method="POST">
        <input type="hidden" name="_method" value="DELETE">
    </form>
</body>

</html>