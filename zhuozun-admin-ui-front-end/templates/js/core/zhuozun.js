zhuozun = {
    baseUrl: "http://192.168.1.204:7777/qs",
    // baseUrl: "http://192.168.1.104:7777/qs",
    user_api: {
        getUserById: function (id) {
            return zhuozunUtils.sprintf("/user-api/user/get/%d", id)
        },
    },
    admin_ui_api: {
        register: "/adminUi-api/register",
        login: "/adminUi-api/login",
    }
};

zhuozunUtils = {
    sprintf: function () {
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
    }
};