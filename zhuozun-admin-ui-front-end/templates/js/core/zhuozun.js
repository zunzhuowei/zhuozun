sys = {
    baseUrl: "http://192.168.1.204:7777/qs",
    // baseUrl: "http://192.168.1.104:7777/qs",
    user_api: {
        getUserById: function (id) {
            return sys_utils.sprintf("/user-api/user/get/%d", id)
        },
    },
    admin_ui_api: {
        register: "/adminUi-api/register",
        login: "/adminUi-api/login",
        testlogin: "/adminUi-api/test/login/aa",
    }
};

sys_utils = {
    sprintf: function () {//格式化输出字符串
        var args = arguments,
            string = args[0],
            i = 1;
        return string.replace(/%((%)|s|d)/g, function (m) {
            //m is the matched format, e.g. %s, %d
            var val = null;
            if (m[2]) {
                val = m[2];
            } else {
                val = args[i];
                //A switch statement so that the formatter can be extended. Default is %s
                switch (m) {
                    case '%d':
                        val = parseFloat(val);
                        if (isNaN(val)) {
                            val = 0;
                        }
                        break;
                }
                i++;
            }
            return val;
        });
    },
    storage: window.localStorage,
    //异步post
    ajaxAsynPost: function (url, parameters, successCallBack, errCallBack, completeCallBack) {
        sys_utils.ajax("POST", url, parameters, successCallBack, errCallBack, completeCallBack, true);
    },
    //异步get
    ajaxAsynGet: function (url, parameters, successCallBack, errCallBack, completeCallBack) {
        sys_utils.ajax("GET", url, parameters, successCallBack, errCallBack, completeCallBack, true);
    },
    //同步post
    ajaxSyncPost: function (url, parameters, successCallBack, errCallBack, completeCallBack) {
        sys_utils.ajax("POST", url, parameters, successCallBack, errCallBack, completeCallBack, false);
    },
    //异步get
    ajaxSyncGet: function (url, parameters, successCallBack, errCallBack, completeCallBack) {
        sys_utils.ajax("GET", url, parameters, successCallBack, errCallBack, completeCallBack, false);
    },
    ajax: function (method, url, parameters, successCallBack, errCallBack, completeCallBack, isAsyn) {
        $.ajax({//ajax请求
            type: method,
            url: url,
            data: parameters,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("token", sys_utils.storage.getItem("token"));
            },
            complete: function (xhr, tx) {
                if (completeCallBack) {
                    completeCallBack.call(this, xhr, tx);
                }
            },
            async: isAsyn,
            success: function (result) {
                if (successCallBack) {
                    successCallBack.call(this, result);
                }
            },
            error: function (e) {
                console.log(JSON.stringify(e));
                if (errCallBack) {
                    errCallBack.call(this, e);
                }
            }
        });
    },
};

sys_request = {
    //登录首页
    login: function (parameters,remember,callback) {
        function successCallback(result) {
            console.log(result);
            if (result.success) {
                if (remember) {
                    sys_utils.storage.setItem("token", result.content.token);
                    sys_utils.storage.setItem("userName", result.content.user.username);
                    sys_utils.storage.setItem("password", result.content.user.password);
                    sys_utils.storage.setItem("remember", "on");
                }
                window.location.href = 'index.html';
            }
            callback.call(this, result);
        }

        function completeCallBack(xhr, tx) {
            if (!remember) {
                sys_utils.storage.removeItem("userName");
                sys_utils.storage.removeItem("password");
                sys_utils.storage.removeItem("remember");
            }
        }

        function errCallBack(e) {
            console.log(e);
        }

        sys_utils.ajaxAsynPost(sys.baseUrl + sys.admin_ui_api.login, parameters,
            successCallback, errCallBack, completeCallBack);
    },
    //注册账号
    sign_up: function (parameters) {
        function successCallback(result) {
            console.log(result);
            if (result.success) {
                window.location.href = 'login.html';
            }
        }

        function completeCallBack(xhr, tx) {}

        function errCallBack(e) {console.log(e);}

        sys_utils.ajaxAsynPost(sys.baseUrl + sys.admin_ui_api.register, parameters,
            successCallback, errCallBack, completeCallBack);
    },
};