package com.shk.controller;

import com.shk.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
@Slf4j
public class DemoController extends BaseController {
    //index页面
    public static  final String INDEX = "index";
    @RequestMapping("/index")
    public String index(@RequestParam("token") String token){
     log.info("我的web工程搭建成功 username:{}"+getMbUserEntity(token).getUsername() );
     return INDEX;
    }
}
