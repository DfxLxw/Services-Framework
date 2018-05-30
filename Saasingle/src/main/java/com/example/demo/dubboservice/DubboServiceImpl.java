package com.example.demo.dubboservice;

import com.example.demo.dao.UserMapper;
import com.example.demo.domain.User;
import com.example.demo.dubbointerface.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DubboServiceImpl implements DubboService {
    @Autowired
    UserMapper userMapper;
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
