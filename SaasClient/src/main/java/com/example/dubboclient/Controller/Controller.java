package com.example.dubboclient.Controller;

import com.example.demo.domain.User;
import com.example.demo.dubbointerface.DubboService;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@org.springframework.stereotype.Controller

public class Controller {
    @Autowired
    DubboService service;
    @ApiOperation(value="插入数据", notes="")
    @RequestMapping(value="/ppp",method = RequestMethod.POST)
    public  @ResponseBody String hhh(@RequestBody String para) {
        /*Logger logger= Logger.getLogger(String.valueOf(getClass()));*/
        Logger logger= (Logger) LoggerFactory.getLogger(Controller.class);
        logger.debug("这里只是测试一下输出得参数哦 ："+para);
        JSONObject jsonArray= JSONObject.fromObject(para);
        System.out.println(jsonArray);
        User user= (User) JSONObject.toBean(jsonArray,User.class);
        String username=user.getUsername();
        String password=user.getPassword();
        User user1=new User();
        user1.setUsername(username);
        user1.setPassword(password);
        service.insert(user1);
        return "success";
    }
}
