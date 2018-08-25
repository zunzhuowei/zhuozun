<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
    <meta charset="UTF-8">
    <title>WebSocket Chat</title>
</head>

<body>

<form method="post" action="mail.html">
    <h3>WebSocket 发送邮件到 聊天室：</h3>
    <textarea id="responseText" style="width: 500px; height: 300px;"></textarea>
    <br>
    <input type="text" name="message" style="width: 300px" value="Welcome to www.waylau.com">
    <input type="submit" value="发送消息">
    <input type="button" onclick="javascript:document.getElementById('responseText').value=''" value="清空聊天记录">
</form>
<br>
<br>

</body>
</html>
