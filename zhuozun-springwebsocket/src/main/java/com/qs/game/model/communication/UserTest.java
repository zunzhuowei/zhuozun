package com.qs.game.model.communication;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by zun.wei on 2018/11/25.
 *
 */
@Data
@Accessors(chain = true)
@ToString
public class UserTest implements Serializable {

    private long id;
    private String userName;
    private String passWord;
    private byte sex;

}
