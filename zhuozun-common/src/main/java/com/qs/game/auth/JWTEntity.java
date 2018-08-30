package com.qs.game.auth;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by zun.wei on 2018/8/30 15:43.
 * Description: jwt 实体类
 */
@Data
@Accessors(chain = true)
public class JWTEntity {

    private Date expDate; //过期时间

    private Long uid; //用户id

    private String sKey; //签名秘钥

    private String uName; //用户名称

}
