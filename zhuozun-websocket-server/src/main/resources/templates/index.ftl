<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
    <meta charset="UTF-8">
    <title>WebSocket Chat</title>
</head>

<body>
<form onsubmit="return false;">
    <h3>WebSocket 聊天室 </h3>
    <input type="hidden" id="username" value="${username}">
    <input type="hidden" id="token" value="${token}">
    <textarea id="responseText" style="width: 500px; height: 300px;"></textarea>
    <br>
    <input type="text" name="message" style="width: 300px" value="Welcome to www.waylau.com">
    <input type="button" value="发送消息" onclick="send(this.form.message.value)">
    <input type="button" onclick="javascript:document.getElementById('responseText').value=''" value="清空聊天记录">
    <input type="button" onclick="close()" value="关闭连接">
</form>
<br>
<br>

<#--<script src="https://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>-->

<script type="text/javascript">
    var socket;
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        window.onbeforeunload = function(event) {
            console.log("关闭WebSocket连接！");
            socket.close();
        };

        var username = document.getElementById('username');
        var token = document.getElementById('token');

        //socket = new WebSocket("ws://${websocketHost}:${websocketPort}");
        socket = new WebSocket("ws://${websocketHost}:${websocketPort}"
                + "?username=" + encodeURIComponent(username.value) + "&token=" + token.value);
        socket.onmessage = function (event) {
            var ta = document.getElementById('responseText');
            ta.value = ta.value + '\n' + event.data
        };
        socket.onopen = function (event) {
            var ta = document.getElementById('responseText');
            ta.value = "连接开启!";
        };
        socket.onclose = function (event) {
            var ta = document.getElementById('responseText');
            ta.value = ta.value + "连接被关闭";
            window.location.href = "login.html";
        };
        socket.addEventListener('open', function (event) {
            socket.send('{"cmd":11,"params":{"passwore":"dasfa","sex":"1","user":"zhansgan"},"sign":"aaaa","token":"abcdefg"}');
        });
    } else {
        alert("你的浏览器不支持 WebSocket！");
    }

    function send(message) {
        if (!window.WebSocket) {
            return;
        }
        if (socket.readyState === WebSocket.OPEN) {
            socket.send(message);
        } else {
            alert("连接没有开启.");
            window.location.href = "login.html";
        }
    }


</script>
</body>
</html>
