package com.qs.game.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zun.wei on 2018/8/28 19:32.
 * Description:
 */
@Data
@Accessors(chain = true)
public class ReqEntity implements Serializable {

    private int cmd;

    private String token;

    private String sign;

    private Map<String, String> params;

    public static void main(String[] args) {
        ReqEntity reqEntity = new ReqEntity();
        Map<String, String> req = new HashMap<>();
        req.put("user", "zhansgan");
        req.put("passwore", "dasfa");
        req.put("sex", "1");
        reqEntity.setCmd(11).setToken("abcdefg").setSign("aaaa").setParams(req);
        System.out.println("reqEntity = " + JSONObject.toJSONString(reqEntity));
    }

}
