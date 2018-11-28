var socket;
if (typeof(WebSocket) === undefined) {
    console.log("您的浏览器不支持WebSocket");
} else {
    socket = new WebSocket("ws://localhost:8600/websocket/33");

    //socket.binaryType = 'arraybuffer';

    //打开事件
    socket.onopen = function () {
        console.log("Socket 已打开");
        socket.send("这是来自客户端的消息" + location.href + new Date());
        socket.send("{\"id\":11111,\"passWord\":\"1112311111\",\"sex\":2,\"userName\":\"张三123123\"}");
    };
    //获得消息事件
    socket.onmessage = function (msg) {
        console.log(msg.data);
        //发现消息进入 开始处理前端触发逻辑
    };
    //关闭事件
    socket.onclose = function () {
        console.log("Socket已关闭");
    };
    //发生了错误事件
    socket.onerror = function () {
        alert("Socket发生了错误");
        //此时可以尝试刷新页面
    };
    //socket.connect();
    //socket.disconnect();
}
