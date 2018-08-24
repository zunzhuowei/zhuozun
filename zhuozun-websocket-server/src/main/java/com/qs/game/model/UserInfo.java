package com.qs.game.model;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Accessors(chain = true)
public class UserInfo implements Serializable {

    public static Map<String, UserInfo> map = new ConcurrentHashMap<>();

    private Long id;

    private String phone;

    private String password;

    private String code;

    private String headImg;

}
