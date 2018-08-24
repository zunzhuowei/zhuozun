package com.qs.game.controller;

import com.qs.game.base.basecontroller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

}
