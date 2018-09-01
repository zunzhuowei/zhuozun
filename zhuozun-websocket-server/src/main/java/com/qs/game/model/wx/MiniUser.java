package com.qs.game.model.wx;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 小程序用户
 */
@Data
@Accessors(chain = true)
public class MiniUser implements Serializable {

    private String openId;

    private String sessionKey;

    private String unionId;

/*
openid	用户唯一标识
session_key	会话密钥
unionid	用户在开放平台的唯一标识符
 */

}
