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
        var shortNum = Int16Array.of(108);

        //protocol char begin
        byte.push(new TextEncoder().encode("Q"));
        byte.push(Int8Array.of(0)); // 0填充
        byte.push(new TextEncoder().encode("S"));
        byte.push(Int8Array.of(0));// 0填充

        // int cmd
        byte.push(Int32Array.of(1002));

        // string param
        var str = new TextEncoder().encode("我爱天安门");
        // string length
        byte.push(Int32Array.of(str.length));
        // string param value
        byte.push(str);


        // int param
        byte.push(Int32Array.of(111));

        // char param
        byte.push(new TextEncoder().encode("A"));
        byte.push(Int8Array.of(0)); // 0填充

        // string param
        var telStr = new TextEncoder().encode("13456102345");
        // stirng length
        byte.push(Int32Array.of(telStr.length));
        // string param value
        byte.push(telStr);


        var blob = new Blob(byte.data(), {type: "application/octet-binary"}); //后端收到低位在前，高位在后
        socket.send(blob);

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
        this.data = function () {
            console.log("------------------------");
            console.log(this.list);
            for (let i = 0; i < this.list.length; i++) {
                //debugger
                var ll = this.list[i];
                console.log(ll)
            }
            console.log("------------------------");
            return this.list;
        }
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
