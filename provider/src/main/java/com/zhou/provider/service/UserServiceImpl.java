package com.zhou.provider.service;

import com.alibaba.dubbo.config.annotation.Service;

/*
* 将服务注册到dubbo的zookeeper中
* */
@Service
public class UserServiceImpl  implements  UserService{
    @Override
    public String getName() {
        return "你好万姣姣";
    }
}
