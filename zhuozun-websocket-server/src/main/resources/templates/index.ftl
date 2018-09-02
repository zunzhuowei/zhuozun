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

/*
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

*/

    var ws;//websocket实例
    var lockReconnect = false;//避免重复连接
    var wsUrl = "ws://${websocketHost}:${websocketPort}"
            + "?uid=" + uid.innerText + "&token=" + token.value;

    function createWebSocket(url) {
        try {
            ws = new WebSocket(url);
            initEventHandle();
        } catch (e) {
            reconnect(url);
        }
    }

    function initEventHandle() {
        ws.onclose = function () {
            var ta = document.getElementById('responseText');
            ta.value = ta.value + "连接被关闭";
            reconnect(wsUrl);
        };
        ws.onerror = function () {
            reconnect(wsUrl);
        };
        ws.onopen = function () {
            var ta = document.getElementById('responseText');
            ta.value = "连接开启!";

            //心跳检测重置
            heartCheck.reset().start();
        };
        ws.onmessage = function (event) {
            if (event.data === "HB") {
                ws.send("OK");
                return;
            }
            if (event.data === "OK") {
                console.log("server HB!");
                //如果获取到消息，心跳检测重置
                //拿到任何消息都说明当前连接是正常的
                heartCheck.reset().start();
                return;
            }
            var resp = JSON.parse(event.data);
            console.log(resp);
            var cmd = resp.cmd;
            if (cmd === 999) {
                window.localStorage.setItem("sKey", resp.content);
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

                ws.send(JSON.stringify(dic));
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
            ta.value = ta.value + '\n' + event.data;

        }
    }

    function reconnect(url) {
        if(lockReconnect) return;
        lockReconnect = true;
        //没连接上会一直重连，设置延迟避免请求过多
        setTimeout(function () {
            createWebSocket(url);
            lockReconnect = false;
        }, 2000);
    }


    //心跳检测
    var heartCheck = {
        timeout: 60000,//60秒
        timeoutObj: null,
        reset: function(){
            clearTimeout(this.timeoutObj);
            return this;
        },
        start: function(){
            this.timeoutObj = setTimeout(function(){
                //这里发送一个心跳，后端收到后，返回一个心跳消息，
                //onmessage拿到返回的心跳就说明连接正常
                ws.send("HB");
            }, this.timeout)
        }
    };

    function send(message) {
        if (!window.WebSocket) {
            return;
        }
        if (ws.readyState === WebSocket.OPEN) {
            ws.send(message);
        } else {
            alert("连接没有开启.");
            window.location.href = "login.html";
        }
    }

    createWebSocket(wsUrl);

</script>
</body>
</html>
