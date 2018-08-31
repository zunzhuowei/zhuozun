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
<script>

    var dic = {"sign":"aaaa","cmd":1000,"token":"abcdefg","stamp":1535562802,"params":{"passwore": "dasfa","sex":"1","user":"zhansgan"}};//输出  {x:2，y:3，z:1}
    //参数字典排序
    var sdic = Object.keys(dic).sort();
    for (ki in sdic) {
        document.writeln(sdic[ki] + ":" + dic[sdic[ki]] + ",");
    }

</script>
</body>
</html>
