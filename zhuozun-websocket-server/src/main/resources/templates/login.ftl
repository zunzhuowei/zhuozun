<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
    <meta charset="UTF-8">
    <title>login Chat Room </title>
</head>

<body>

<script type="text/javascript">

</script>

<form action="login.html" method="post">
    <h3>登录 WebSocket 聊天室：</h3>
    <br>
    <label for="uid">用户ID:</label>
    <input type="number" id="uid" name="uid" style="width: 300px" /><br/>
    <label for="password">密码:</label>
    <input type="password" id="password" name="password" style="width: 300px" /><br/><br/>
    <input type="submit" value="登录"/>
</form>
<br>
<br>

</body>
</html>
