var socket;
if (typeof(WebSocket) === undefined) {
    console.log("您的浏览器不支持WebSocket");
} else {
    socket = new WebSocket("ws://localhost:8600/websocket/33");

    socket.binaryType = 'arraybuffer';

    //打开事件
    socket.onopen = function () {
        console.log("Socket 已打开");
        socket.send(blob);
        //socket.send("{\"id\":11111,\"passWord\":\"1112311111\",\"sex\":2,\"userName\":\"张三123123\"}");
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

    var ab = new ArrayBuffer(1024); //1024字节

    var str = new TextEncoder("utf-8").encode("我爱天安门");
    var strLen = str.length;
    console.log("strLen ---------::" + strLen);
    var iA = new Int8Array(ab, 0, 1); //
    iA[0] = 97;

    var iB = new Int16Array(ab);
    iB = intTobytes2(n);

    var blob = new Blob([iA, str, iB], {type: "application/octet-binary"});


    //Uint8Array转字符串
    function Uint8ArrayToString(fileData){
        var dataString = "";
        for (var i = 0; i < fileData.length; i++) {
            dataString += String.fromCharCode(fileData[i]);
        }

        return dataString
    }

    //字符串转Uint8Array
    function stringToUint8Array(str){
        var arr = [];
        for (var i = 0, j = str.length; i < j; ++i) {
            arr.push(str.charCodeAt(i));
        }

        var tmpUint8Array = new Uint8Array(arr);
        return tmpUint8Array
    }

    //int转byte[]
    function intTobytes2(n) {
        var bytes = [];

        for (var i = 0; i < 2; i++) {
            bytes[i] = n >> (8 - i * 8);

        }
        return bytes;
    }

    //string转ArrayBuffer
    function str2ab(str) {
        var buf = new ArrayBuffer(str.length * 2); // 每个字符占用2个字节
        var bufView = new Uint16Array(buf);
        for (var i = 0, strLen = str.length; i < strLen; i++) {
            bufView[i] = str.charCodeAt(i);
        }
        return buf;
    }

    //ArrayBuffer转String
    function ab2str(buf) {
        return String.fromCharCode.apply(null, new Uint8Array(buf));
    }

}
