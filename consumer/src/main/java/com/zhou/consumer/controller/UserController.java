package com.zhou.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhou.provider.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Reference
    private UserService userService;

    /**
     * DUBBO的远程调用
     * @return
     */
    @RequestMapping("/getName")
    @ResponseBody
    public String getName(){
        return userService.getName();
    }
}
