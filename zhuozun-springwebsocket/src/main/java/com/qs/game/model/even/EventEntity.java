package com.qs.game.model.even;

import com.qs.game.constant.EvenType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by zun.wei on 2018/12/17 16:51.
 * Description:
 */
@Data
@Accessors(chain = true)
public class EventEntity implements Serializable {

    private Event event;

    private EvenType evenType;

}
