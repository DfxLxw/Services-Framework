package com.example.web.service.impl;

import com.example.demo.dao.UserMapper;
import com.example.demo.domain.User;
import com.example.web.RocketMq.client.InsertMq;
import com.example.web.Util.RedisUtil;
import com.example.web.service.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class serviceImpl implements service {
    @Autowired
    InsertMq insertMq;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisUtil redisUtil;
    @Override
    public int insert(User user) {
       /* redisUtil.set("users",user);
        redisUtil.get("users");
        //测试Mq的应用，此处先不进行插入操作而是发送MQ
        System.out.println(redisUtil.get("users"));*/
        return userMapper.insert(user);
    }
    @Override
    public List<User> search(String username) {

        return userMapper.search(username);
    }
}
