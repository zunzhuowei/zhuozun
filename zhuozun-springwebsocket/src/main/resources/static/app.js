var socket;
if (typeof(WebSocket) === undefined) {
    console.log("您的浏览器不支持WebSocket");
} else {
    socket = new WebSocket("ws://localhost:8600/websocket/33");

    socket.binaryType = 'arraybuffer';

    //打开事件
    socket.onopen = function () {
        console.log("Socket 已打开");
        var byte = new ByteArray();
        var byteNum = Int8Array.of(110);
        var intNum = Int32Array.of(1008611111);
        var shortNum = Int16Array.of(108);

        var str = new TextEncoder().encode("我爱天安门");
        byte.push(byteNum);
        byte.push(str);

        byte.push(intNum);
        byte.push(shortNum);
        byte.push('Q'.charAt(0));
        byte.push('S'.charAt(0));

        socket.send(new Blob(byte.list, {type: "application/octet-binary"}));
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

    function ByteArray(){
        this.list=[];
        this.byteOffset = 0;
        this.length = 0;
    }

    var p = ByteArray.prototype;
    p.push = function (unit8Arr) {
        this.list.push(unit8Arr);
        this.length += unit8Arr.length;
    };
    p.readBytes = function (len) {
        if (len > 0) {
            var rbuf = new Uint8Array(len);
            var rbuf_ind = 0;
            while (rbuf_ind < len) {
                if (this.list.length > 0) {
                    var tmpbuf = this.list.shift();
                    var tmplen = tmpbuf.length;
                    var last_len = len - rbuf_ind;
                    if (tmplen >= last_len) {
                        //足夠了
                        var tmpbuf2 = tmpbuf.subarray(0, last_len);
                        rbuf.set(tmpbuf2, rbuf_ind);
                        rbuf_ind += tmpbuf2.length;
                        if (last_len < tmplen) {
                            var newUint8Array = tmpbuf.subarray(last_len, tmplen);
                            this.list.unshift(newUint8Array);
                        }
                        break;
                    } else {
                        rbuf.set(tmpbuf, rbuf_ind);
                        rbuf_ind += tmplen;
                    }
                } else {
                    rbuf = rbuf.subarray(0, rbuf_ind);
                    break;
                }
            }
            this.length -= rbuf.length;
            return rbuf;
        }
        return null;
    };

    /*
    var byte=new ByteArray();
    byte.push(new Uint8Array([1,2,4,5]));
    byte.push(new Uint8Array([5,3,4,5]));
    byte.readBytes(2);
    byte.readBytes(2);
     */
}
