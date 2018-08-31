package com.qs.game.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Created by zun.wei on 2018/8/15 15:43.
 * Description: 操作日志
 */
@Data
@Accessors(chain = true)//链式的操作方式
@AllArgsConstructor
@NoArgsConstructor
public class OperationLog {

    private String createTime;// 操作时间

    private String content;// 操作内容

    private String opreation;// 操作


}
