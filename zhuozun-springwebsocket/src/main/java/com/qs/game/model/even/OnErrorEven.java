package com.qs.game.model.even;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by zun.wei on 2018/11/21 14:09.
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OnErrorEven extends Even {

    private Throwable error;

}
