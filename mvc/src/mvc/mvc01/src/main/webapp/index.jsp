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
    <a href="hello">
        <button>Hello</button>
    </a><br>
    <hr>
    <form action="login" method="post">
        <label for="username">Username </label><input type="text" name="username" id="username"><br>
        <label for="password">Password </label><input type="text" name="password" id="passowrd"><br>
        <input type="submit" value="Login">
    </form>
    <hr>
    <input id="btn" type="button" value="JSON">
    <hr>
    <form action="upload" method="post" enctype="multipart/form-data">
        <label for="file">File</label> <input type="file" name="file" id="file"><br>
        <label for="desc">Description</label> <input type="text" name="desc" id="desc"><br>
        <input type="submit" value="Upload">
    </form>
    <hr>
    <form action="multiUpload" method="post" enctype="multipart/form-data">
        <label for="file1">File1</label> <input type="file" name="file" id="file1"><br>
        <label for="file1">File2</label> <input type="file" name="file" id="file1"><br>
        <label for="desc">Description</label> <input type="text" name="desc" id="desc"><br>
        <input type="submit" value="MultiUpload">
    </form>
    <hr>
    <a href="nullPointer">NullPointer</a>
</body>

</html>