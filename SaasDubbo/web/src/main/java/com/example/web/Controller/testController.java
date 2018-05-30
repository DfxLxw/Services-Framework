package com.example.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
public class testController {
    @ApiIgnore
    @RequestMapping("/sos")
    public String in(){
        return "pratice";
    }
    @ApiIgnore
    @RequestMapping("/test")
    public String out(){
        return "test";
    }




}
