<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>

<head>
    <title>REST</title>
    <script src="js/jquery-3.5.1.min.js"></script>
    <script>
        $(function () {
            // delete 请求方法不能携带数据，所以使用路径参数
            $("#testAJAX").on("click", function (event) {
                $.ajax({
                    url: "testAJAX/" + 1001,
                    type: "DELETE",
                    dataType: "json",
                    success: function (data) {
                        alert(data);
                    }
                })
            })
        })
    </script>
</head>

<body>
    <a href="testREST/1001">测试GET</a>
    <form action="testREST" method="POST">
        <input type="hidden" name="id" value="1002">
        <input type="submit" value="测试POST">
    </form>
    <form action="testREST" method="POST">
        <input type="hidden" name="_method" value="PUT">
        <input type="hidden" name="id" value="1003">
        <input type="submit" value="测试PUT">
    </form>
    <form action="testREST/1004" method="POST">
        <input type="hidden" name="_method" value="DELETE">
        <input type="submit" value="测试DELETE">
    </form>
    <input type="button" value="测试AJAX" id="testAJAX">
</body>

</html>