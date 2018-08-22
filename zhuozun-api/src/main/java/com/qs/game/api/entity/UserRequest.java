package com.qs.game.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by zun.wei on 2018/8/22 13:55.
 * Description: 用户请求类
 */
@Data
@Accessors
public class UserRequest implements Serializable {

    private String username;

    private String password;

    private String remember; //on or null

    private String token;

}
