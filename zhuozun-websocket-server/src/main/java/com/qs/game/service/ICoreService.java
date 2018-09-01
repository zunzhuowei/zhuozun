package com.qs.game.service;

import com.qs.game.model.sys.Core;

/**
 * 核心业务层接口
 */
public interface ICoreService {

    /**
     * 校验登录参数
     * @param core
     * @return
     */
    boolean checkParams(Core core);


}
