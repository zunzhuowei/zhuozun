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
    <span style="display: none;" id="uid" >${uid ?c }</span> <!-- 如何去掉","逗号呢.,加上"?c"就可以了。 -->
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
<script src="md5.js" type="text/javascript"></script>

<script type="text/javascript">
    var req = {
        makeReq: function (base, params) {
            var obj = Object.assign({}, base, params);

            var sdic = Object.keys(obj).sort();
            var sortStr = "";
            for (ki in sdic) {
                sortStr += sdic[ki] + "=" + obj[sdic[ki]] + "&";
            }
            return sortStr;
        }
    };

    var socket;
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        window.onbeforeunload = function(event) {
            console.log("关闭WebSocket连接！");
            socket.close();
        };

        var uid = document.getElementById('uid');
        var token = document.getElementById('token');

        socket = new WebSocket("ws://${websocketHost}:${websocketPort}"
                + "?uid=" + uid.innerText + "&token=" + token.value);
        //ssl需要域名，否则握手失败        //socket = new WebSocket("wss://localhost:${websocketPort}"
        //        + "?uid=" + uid.value + "&token=" + token.value);

        socket.onmessage = function (event) {
            debugger;
            var resp = JSON.parse(event.data);
            console.log(resp);
            var cmd = resp.cmd;
            if (cmd === 999) {
                window.localStorage.setItem("sKey", resp.content);
    debugger;
                var dic = {
                    cmd: 1000,
                    token: '${token}',
                    stamp: Date.parse(new Date()) / 1000,
                };

                var parmas = {
                    mid: 111,
                    username: "zhangsan"
                };

                var sortStr = req.makeReq(dic, parmas) + "key=" + window.localStorage.getItem("sKey");
                var md5Str = hex_md5(sortStr);
                dic.sign = md5Str;
                dic.params = parmas;

                socket.send(JSON.stringify(dic));
            }
            if (cmd === 1000) {
                var sKey = window.localStorage.getItem("sKey");
                if (resp.err < 0) {
                    alert(resp.comment + ":" + resp.err);
                } else {
                    alert("login success");
                }
            }
            var ta = document.getElementById('responseText');
            ta.value = ta.value + '\n' + event.data
        };
        socket.onopen = function (event) {
            debugger;
            var ta = document.getElementById('responseText');
            ta.value = "连接开启!";
        };
        socket.onclose = function (event) {
            var ta = document.getElementById('responseText');
            ta.value = ta.value + "连接被关闭";
            window.location.href = "login.html";
        };
        socket.addEventListener('open', function (event) {
            //socket.send('{"cmd":1000,"params":{"passwore":"dasfa","sex":"1","user":"zhansgan"},"sign":"aaaa","stamp":1535562802,"token":"abcdefg"}');
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
