package com.qs.game.controller;

import com.qs.game.base.basecontroller.BaseController;
import com.qs.game.model.sys.Core;
import com.qs.game.service.ICoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 核心控制器
 */
@RestController
@RequestMapping("core")
public class CoreController extends BaseController {


    @Autowired
    private ICoreService coreService;


    @PostMapping("game/login.do")
    public Object miniGameLogin(Core core) {
        //1）校验参数
        boolean isBadRequest = coreService.checkParams(core);

        return null;
    }


}
