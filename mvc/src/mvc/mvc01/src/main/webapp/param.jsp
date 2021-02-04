<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <title>Param</title>
</head>

<body>
    <form action="paramMap" method="POST">
        <label for="username">Username</label> <input type="text" name="username" id="username"> <br>
        <label for="password">Password</label> <input type="text" name="password" id="password"> <br>
        <label for="age">Age</label> <input type="text" name="age" id="age"> <br>
        <label for="province">Province</label> <input type="text" name="address.province" id="province"> <br>
        <label for="city">City</label> <input type="text" name="address.city" id="city"> <br>
        <label for="country">Country</label> <input type="text" name="address.country" id="country"> <br>
        <input type="submit" value="Submit">
    </form>
</body>

</html>