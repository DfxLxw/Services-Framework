package com.example.web.Controller;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.example.demo.dao.UserMapper;
import com.example.demo.domain.User;
import com.example.web.RocketMq.client.InsertMq;
import com.example.web.RocketMq.server.reciveMq;
import com.example.web.service.impl.serviceImpl;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    reciveMq ReciveMq;
    @Autowired
    InsertMq insertMq;
    @Autowired
    serviceImpl service;
    @Autowired
    UserMapper userMapper;
    @ApiOperation(value="插入数据", notes="")
    @RequestMapping(value="/sss",method = RequestMethod.POST)
    public  @ResponseBody String hhh(@RequestBody String para) throws MQClientException {
        /*Logger logger= Logger.getLogger(String.valueOf(getClass()));*/
        Logger logger= (Logger) LoggerFactory.getLogger(Controller.class);
        logger.debug("这里只是测试一下输出得参数哦 ："+para);
        JSONObject jsonArray=JSONObject.fromObject(para);
        System.out.println(jsonArray);
        User user= (User) JSONObject.toBean(jsonArray,User.class);
        String username=user.getUsername();
        String password=user.getPassword();
        User user1=new User();
        user1.setUsername(username);
        user1.setPassword(password);
        insertMq.defaultMQProducer(jsonArray);
        service.insert(user1);
//        JSONObject returns=JSONObject.fromObject(user1);
//        ReciveMq.consumeMessage(msgs,consumeConcurrentlyContext);
        ReciveMq.defaultMQPushConsumer();
        return "success";
        }
    @ApiOperation(value="查询数据", notes="")
    @RequestMapping(value = "/ooo",method = RequestMethod.POST)
    public @ResponseBody JSONArray OOO(@RequestBody String para){
        System.out.println(para);
        JSONObject jsonObject=JSONObject.fromObject(para);
        User user=(User)JSONObject.toBean(jsonObject,User.class);
        String username=user.getUsername();
        List<User> userList=service.search(username);
        JSONArray returning=JSONArray.fromObject(userList);
        return returning;
    }



}
