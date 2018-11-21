package com.qs.game.controller;

import com.qs.game.server.WebSocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by zun.wei on 2018/11/19 10:54.
 * Description: 消息推送
 */
@Controller
//@RequestMapping("/checkcenter")
public class CheckCenterController {

    //页面请求
  /*  @GetMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid) {
        ModelAndView mav = new ModelAndView("/socket");
        mav.addObject("cid", cid);
        return mav;
    }*/

    //页面请求
    @GetMapping("/socket/{cid}")
    public String socket(@PathVariable String cid, Model model) {
        model.addAttribute("cid", cid);
        return "socket";
    }

    //推送数据接口
   /* @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public String pushToWeb(@PathVariable String cid, String message) {
        try {
            WebSocketServer.sendInfo(message, cid);
        } catch (IOException e) {
            e.printStackTrace();
            return cid + "#" + e.getMessage();
        }
        return cid;
    }*/

}
