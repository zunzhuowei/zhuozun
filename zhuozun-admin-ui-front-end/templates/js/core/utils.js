var utils = {}
var MD5 = require("../tools/MD5New")
var CONFIG = require("Config");
//此数组里面存放key，其值为base64加密后的字符串，需要先用+签名，后再把+变成%2B的key
var base64ReplayKey = ["encryptedData","iv","nickName"]

utils.httpjava = function(node,url,params,cb,method,silent,retryTimes,isRc){
    params.sesskey = CONFIG.USER.sesskey || ""
    require('Common').dump('【JavaSend】'+url+' = ',params);
    var self = this;
    if(method){
        submitType = method.toUpperCase()
    }
    var data = self.getParameter(params)
    var obj = new XMLHttpRequest();
    obj.open("POST", url);
    obj.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    // obj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");  // 添加http头，发送信息至服务器时内容编码类型
    obj.send(data); // datat应为'a=a1&b=b1'这种字符串格式，在jq里如果data为对象会自动将对象转成这种字符串格式
    obj.onreadystatechange = function() {
        // console.log("pppppp=============",obj)
        // console.log(obj.readyState,obj.status)
        if (obj.readyState == 4 && (obj.status == 200 || obj.status == 304)) {  // 304未修改
            if (obj.responseText == "") {
                console.log("java return error ",obj)
            } else {
                var cbdata = JSON.parse(obj.responseText);
                require('Common').dump('【JavaReturn】'+url+' = ',cbdata);
                cb.call(this, cbdata);
            }
        }
    };
}

utils.print = function(str){
    var _type = typeof(str);
    // cc.log('type = ' , _type);
    if ('object' == _type){
        var value = '';
        for (const key in str) {
            if (str.hasOwnProperty(key)) {
                const element = str[key];
                this.print(element);
                value += '\n----|' + key + '---->' + element
            }
        }
        cc.log(value);
    }else{
        cc.log(str);
    }
}

//微信加密数据是用base64加密的字符串，里面有加号，会导致java那边过滤器吧+变成别的，报错签名不一致
utils.base64ReplaceAdd = function(text){
    return text.replace(new RegExp("[+]","gm"), '%2B');
}

// 序列化对象为 ASCII 字典序字符串
utils.FormatASCII = function (obj,isBase64ReplaceAdd) {
    var arr = [];
    for(var key in obj){
        if (obj[key] && obj[key] != "") {//签名时空串不参与签名
            if (isBase64ReplaceAdd != undefined && isBase64ReplaceAdd != null && isBase64ReplaceAdd == true) {
                if (base64ReplayKey.indexOf(key) != -1) {
                    obj[key] = utils.base64ReplaceAdd(obj[key])
                }
            }
            arr.push(key + '=' + obj[key]);
        }
    }
    arr.sort()
    return arr.join('&')
}

//拼接参数加密签名字符串
utils.getParameter = function (obj) {
    var self = this;
    obj.signCode = Date.parse(new Date())/1000
    self.SIGN_KEY = "D9%J@#$A$%#@JA&&635"
    var signature = self.FormatASCII(obj)+"&key="+self.SIGN_KEY
    // console.log("--------------llllllll",signature)
    var sign = MD5.md5(signature)
    sign = sign.toUpperCase()
    obj.sign = sign
    var parameter = self.FormatASCII(obj,true)
    console.log("--------------llllllll",parameter)
    return parameter
}

//拼接参数加密签名字符串
utils.getSign = function (obj) {
    var self = this;
    obj.signCode = Date.parse(new Date())/1000
    self.SIGN_KEY = "D9%J@#$A$%#@JA&&635"
    var signature = self.FormatASCII(obj)+"&key="+self.SIGN_KEY
    var sign = MD5.md5(signature)
    sign = sign.toUpperCase()
    obj.sign = sign
    return obj
}


utils.setUserSetting = function(key,val){
    if (val != null){
        let valueType = typeof(val);
        if (valueType != 'string'){
            val = val.toString();
        }
    }
    cc.sys.localStorage.setItem(key, val);
}
/**
 * 
 * @param {*} key 缓存的key名字
 * @param {*} defaultValue 缓存值的默认值
 * @param {*} chooseValueTb 这个key的所有取值可能性，当更新了版本后选项值发生变化了时，这个tb非常有用
 */
utils.getUserSetting = function(key,defaultValue,chooseValueTb){
    let value = cc.sys.localStorage.getItem(key);//这个缓存，key不存在时，小程序默认value = "", h5默认value = null
    if (value == undefined || value == undefined || value == "" ){
        if (defaultValue) {
            value = defaultValue;
        } else {
            value = null;
        }
    }
    let defaultValueType = typeof(defaultValue);
    if(defaultValueType == 'number'){
        value = Number(value);
    }
    //有值，但是这个值不在选择范围内时，修改此选项为默认值
    if (chooseValueTb != undefined && chooseValueTb != null && chooseValueTb.length > 0) {
        var temp = value;
        if (chooseValueTb.indexOf(temp) == -1) {
            value = chooseValueTb[0]
            this.setUserSetting(key,value)
        }
    }
    
    return value
}

utils.getDeviceID = function (){
    //152058958147379895
    var r = utils.getUserSetting("guestAccount"+CONFIG.ServerType)
    if (r == null ) {//生成新的账号
        r = Date.parse(new Date())/1000+""+Math.floor(Math.random()*100000000)
    }
    utils.setUserSetting("guestAccount"+CONFIG.ServerType,r)
    // cc.log("&&&&&&&&&&&&",r)
    return r
}

utils.getUserAgentUrl = function(){
    var signKey = "AGENTKEY:" 
    
    var curTime = new Date().getTime()
    //var time = math.ceil(curTime * 1000) 
    var sitemid = utils.getDeviceID()
    var loginInfo = utils.getUserSetting("loginInfo")
    if (loginInfo) {
        loginInfo = JSON.parse(loginInfo)
        if(loginInfo && loginInfo.sitemid){
            sitemid = loginInfo.sitemid;
        }
    }
    var str = sitemid + curTime + signKey    
    var sign = MD5.md5(str)
    sign = sign.toUpperCase()
    var data = {}
    data.sign = sign
    data.curTime = curTime
    return data
}
//时间戳转时间
utils.timesTampToTime = function(time){
        var date = new Date(time * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        var D = date.getDate() + ' ';
        var h = date.getHours() + ':';
        var m = date.getMinutes() + ':';
        var s = date.getSeconds();
        return Y+M+D+h+m+s;
}



module.exports = utils;