package com.qs.game.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.qs.game.model.sys.Core;
import com.qs.game.service.ICoreService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  核心业务层接口实现类
 */
@Service
public class CoreServiceImpl implements ICoreService {

    @Autowired
    private WxMaService wxService;

    @Override
    public boolean checkParams(Core core) {
        return false;
    }

    public void test() throws WxErrorException {
        WxMaJscode2SessionResult result = wxService.getUserService().getSessionInfo("");
        result.getOpenid();
        result.getSessionKey();
        result.getUnionid();

        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo("", "", "");
        userInfo.getCity();
        userInfo.getOpenId();
        userInfo.getUnionId();

    }

}
