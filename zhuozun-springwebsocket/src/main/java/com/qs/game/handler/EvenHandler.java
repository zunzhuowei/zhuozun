package com.qs.game.handler;

import com.qs.game.model.even.Event;
import com.qs.game.utils.SpringBeanUtil;

/**
 * Created by zun.wei on 2018/11/21 15:13.
 * Description: 事件处理接口
 */
public interface EvenHandler {

    /**
     * 二进制消息请求处理类
     */
    default OnBinaryEvenHandler getOnBinaryEvenHandler(){
        return SpringBeanUtil.getBean(OnBinaryEvenHandler.class);
    }

    /**
     * 关闭事件处理类
     */
    default OnCloseEvenHandler getOnCloseEvenHandler() {
        return SpringBeanUtil.getBean(OnCloseEvenHandler.class);
    }

    /**
     * 发生错误事件处理类
     */
    default OnErrorEvenHandler getOnErrorEvenHandler() {
        return SpringBeanUtil.getBean(OnErrorEvenHandler.class);
    }

    /**
     * 打开连接事件处理类
     */
    default OnOpenEvenHandler getOnOpenEvenHandler() {
        return SpringBeanUtil.getBean(OnOpenEvenHandler.class);
    }

    /**
     *  心跳事件处理类
     */
    default OnPongEvenHandler getOnPongEvenHandler() {
        return SpringBeanUtil.getBean(OnPongEvenHandler.class);
    }

    /**
     * 字符串消息处理类
     */
    default OnStrEvenHandler getOnStrEvenHandler() {
        return SpringBeanUtil.getBean(OnStrEvenHandler.class);
    }

    void handler(Event event) throws Exception;

}
