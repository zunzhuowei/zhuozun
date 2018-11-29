package com.qs.game.socket;

import com.alibaba.fastjson.JSON;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created by zun.wei on 2018/11/25.
 */
public class TextDecoder implements Decoder.Text<Object> {


    @Override
    public Object decode(String s) throws DecodeException {
        return JSON.parse(s);
    }

    @Override
    public boolean willDecode(String s) {
        return false;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
